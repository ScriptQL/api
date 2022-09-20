package com.scriptql.api.domain;

public enum SpecMatcher {

    /**
     * Represents an equality.
     */
    EQUALS,
    /**
     * Represents a full text search operation.
     */
    SEARCH,
    /**
     * Represents a mathematical bigger than.
     */
    BIGGER,
    /**
     * Represents a mathematical less than.
     */
    LOWER,
    /**
     * Represents a same-day filter.
     */
    DAY,
    /**
     * Represents a same-hour filter.
     */
    HOUR

}
