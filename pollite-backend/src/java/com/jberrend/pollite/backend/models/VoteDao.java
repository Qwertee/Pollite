package com.jberrend.pollite.backend.models;

import org.jdbi.v3.sqlobject.customizer.BindMap;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Map;

public interface VoteDao {
    @SqlUpdate("INSERT INTO vote (option_id, uuid, fingerprint) VALUES (:option_id, :uuid, :fingerprint)")
    public boolean insert(@BindMap Map<String, ?> map);
}
