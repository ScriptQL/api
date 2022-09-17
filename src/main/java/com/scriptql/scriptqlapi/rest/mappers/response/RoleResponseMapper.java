package com.scriptql.scriptqlapi.rest.mappers.response;

import com.scriptql.scriptqlapi.domain.entities.Role;
import lombok.Data;

@Data
public class RoleResponseMapper {
    private long id;
    private String name;

    public static RoleResponseMapper fromRole(Role role) {
        var mapper = new RoleResponseMapper();

        mapper.setId(role.getId());
        mapper.setName(role.getName());

        return mapper;
    }
}
