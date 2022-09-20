package com.scriptql.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.Transient;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

public class SpecBuilder<T> {

    private final Map<String, Function<Object, Specification<T>>> map = new HashMap<>();

    public void addMatcher(String field, SpecMatcher matcher) {
        switch (matcher) {
            case EQUALS -> map.put(field, (arg) -> this.equals(field, arg));
            //case SEARCH -> map.put(field, (arg) -> this.fts(field, String.valueOf(arg)));
            case SEARCH -> map.put(field, (arg) -> this.like(field, String.valueOf(arg)));
            case BIGGER -> map.put(field, (arg) -> this.bigger(field, (Number) arg));
            case LOWER -> map.put(field, (arg) -> this.lower(field, (Number) arg));
            case DAY -> map.put(field, (arg) -> this.sameDay(field, (Long) arg));
            case HOUR -> map.put(field, (arg) -> this.sameHour(field, (Long) arg));
        }
    }

    public Specification<T> create(T instance, boolean isOr) {
        Set<String> fields = this.getFieldNames(instance);
        if (fields.isEmpty()) {
            return null;
        }
        Queue<Specification<T>> specs = new ArrayDeque<>();
        for (String field : fields) {
            Object value = this.getValue(field, instance);
            if (value != null) {
                if (map.containsKey(field)) {
                    specs.add(map.get(field).apply(value));
                } else {
                    specs.add(this.equals(field, value));
                }
            }
        }
        if (specs.isEmpty()) {
            return null;
        }
        Specification<T> spec = specs.poll();
        for (Specification<T> other : specs) {
            spec = isOr ? spec.or(other) : spec.and(other);
        }
        return spec;
    }

    public Specification<T> createFromDTO(Object dto, boolean isOr) {
        Set<String> fields = this.getFieldNames(dto);
        if (fields.isEmpty()) {
            return null;
        }
        Queue<Specification<T>> specs = new ArrayDeque<>();
        for (String field : fields) {
            Object value = this.getValue(field, dto);
            if (value != null) {
                if (map.containsKey(field)) {
                    specs.add(map.get(field).apply(value));
                } else {
                    specs.add(this.equals(field, value));
                }
            }
        }
        if (specs.isEmpty()) {
            return null;
        }
        Specification<T> spec = specs.poll();
        for (Specification<T> other : specs) {
            spec = isOr ? spec.or(other) : spec.and(other);
        }
        return spec;
    }

    private Specification<T> like(String fieldName, String search) {
        return (root, query, builder) -> {
            Path<String> path = this.getPath(fieldName, root);
            return builder.like(path, "%" + search + "%");
        };
    }

    // Full text search!
    private Specification<T> fts(String fieldName, String search) {
        return (root, query, builder) -> {
            Path<String> path = this.getPath(fieldName, root);
            return builder.equal(
                    builder.function("fts", Boolean.class, path, builder.literal(search)), true);
        };
    }

    private Specification<T> equals(String fieldName, Object search) {
        return (root, query, builder) -> {
            Path<String> path = this.getPath(fieldName, root);
            return builder.equal(path, search); // literal?
        };
    }

    private Specification<T> bigger(String field, Number than) {
        return (root, query, builder) -> {
            Path<Number> path = this.getPath(field, root);
            return builder.gt(path, than);
        };
    }

    private Specification<T> lower(String field, Number than) {
        return (root, query, builder) -> {
            Path<Number> path = this.getPath(field, root);
            return builder.lt(path, than);
        };
    }

    private Specification<T> sameDay(String field, Long time) {
        return (root, query, builder) -> {
            Path<Number> path = this.getPath(field, root);
            long start = Instant.ofEpochMilli(time)
                    .truncatedTo(ChronoUnit.DAYS)
                    .toEpochMilli();
            long end = Instant.ofEpochMilli(time)
                    .truncatedTo(ChronoUnit.DAYS)
                    .plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.SECONDS)
                    .toEpochMilli();
            return builder.and(builder.ge(path, start), builder.le(path, end));
        };
    }

    private Specification<T> sameHour(String field, Long time) {
        return (root, query, builder) -> {
            Path<Number> path = this.getPath(field, root);
            long start = Instant.ofEpochMilli(time)
                    .truncatedTo(ChronoUnit.HOURS)
                    .toEpochMilli();
            long end = Instant.ofEpochMilli(time)
                    .truncatedTo(ChronoUnit.HOURS)
                    .plus(1, ChronoUnit.HOURS).minus(1, ChronoUnit.SECONDS)
                    .toEpochMilli();
            return builder.and(builder.ge(path, start), builder.le(path, end));
        };
    }

    private <V> Path<V> getPath(String fieldName, Root<?> root) {
        Path<V> path;
        if (fieldName.contains(".")) {
            path = null;
            for (String name : fieldName.split("\\.")) {
                path = Objects.requireNonNullElse(path, root).get(name);
            }
        } else {
            path = root.get(fieldName);
        }
        return path;
    }

    private Set<String> getFieldNames(Object instance) {
        Set<String> fields = new HashSet<>();
        for (Field field : getDeclaredFields(instance.getClass())) {
            if (field.isAnnotationPresent(JsonIgnore.class)) {
                continue;
            } else if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            if (isPrimitive(field.getType()) || field.getType().isPrimitive()) {
                fields.add(field.getName());
            } else if (field.getType().isEnum()) {
                fields.add(field.getName());
            } else {
                Object f = this.getValue(field.getName(), instance);
                if (f != null) {
                    this.getFieldNames(f).stream()
                            .map(name -> field.getName() + "." + name)
                            .forEach(fields::add);
                }
            }
        }
        return fields;
    }

    public List<Field> getDeclaredFields(Class<?> tClass) {
        List<Field> fields = new LinkedList<>();
        while (tClass != null) {
            fields.addAll(Arrays.asList(tClass.getDeclaredFields()));
            tClass = tClass.getSuperclass();
        }
        return fields;
    }

    private boolean isPrimitive(Class<?> clazz) {
        Set<Class<?>> primitives = new HashSet<>();
        primitives.add(Boolean.class);
        primitives.add(String.class);
        primitives.add(Double.class);
        primitives.add(Integer.class);
        primitives.add(Float.class);
        primitives.add(Long.class);
        primitives.add(Short.class);
        primitives.add(Byte.class);
        primitives.add(Character.class);
        return primitives.contains(clazz);
    }

    private Object getValue(String fieldName, Object instance) {
        if (fieldName.contains(".")) {
            String name = fieldName.substring(0, fieldName.indexOf("."));
            String rest = fieldName.substring(fieldName.indexOf(".") + 1);
            Object object;
            try {
                Method method = instance.getClass()
                        .getMethod("get" + StringUtils.capitalize(name));
                object = method.invoke(instance);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                return null;
            }
            return this.getValue(rest, object);
        }
        try {
            Method method = instance.getClass()
                    .getMethod("get" + StringUtils.capitalize(fieldName));
            return method.invoke(instance);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

}
