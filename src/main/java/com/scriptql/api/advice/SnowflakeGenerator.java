package com.scriptql.api.advice;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeGenerator implements IdentifierGenerator {

    private final Snowflake snowflake;

    public SnowflakeGenerator() {
        this.snowflake = Snowflake.init();
    }

    @Override
    public Serializable generate(
            SharedSessionContractImplementor session,
            Object object) throws HibernateException {
        return this.snowflake.next();
    }

}
