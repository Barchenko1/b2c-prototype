package com.b2c.prototype.mapper;

public interface Query {
    String SELECT_ALL = "SELECT * FROM %s e;";
    String SELECT_BY_PARAM = "SELECT * FROM %s e where e.%s = '%s'";
}
