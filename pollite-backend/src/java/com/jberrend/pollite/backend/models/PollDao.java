package com.jberrend.pollite.backend.models;

import org.jdbi.v3.sqlobject.customizer.BindMap;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Map;

public interface PollDao {
    @SqlUpdate("INSERT INTO poll (prompt, hash) VALUES (:prompt, :hash)")
    public boolean insert(@BindMap Map<String, ?> map);
}
