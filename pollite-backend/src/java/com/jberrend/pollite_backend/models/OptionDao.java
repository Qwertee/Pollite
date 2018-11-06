package com.jberrend.pollite_backend.models;

import org.jdbi.v3.sqlobject.customizer.BindMap;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Map;

public interface OptionDao {
    @SqlUpdate("INSERT INTO option (poll_id, text, created_at, updated_at) " +
            "values (0, :text, :created_at, :updated_at)")
//    @RegisterBeanMapper(value = Option.class, prefix = "o")
    public void insertOption(@BindMap Map<String, ?> map);
}
