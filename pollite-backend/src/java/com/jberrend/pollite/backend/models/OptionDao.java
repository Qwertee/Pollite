package com.jberrend.pollite.backend.models;

import org.jdbi.v3.sqlobject.customizer.BindMap;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Map;

public interface OptionDao {
    @SqlUpdate("INSERT INTO option (poll_id, uuid, text) " +
            "values (:poll_id, :uuid, :text)")
//    @RegisterBeanMapper(value = Option.class, prefix = "o")
    public boolean insert(@BindMap Map<String, ?> map);
}
