package com.jberrend.pollite_backend.models;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface OptionDao {
    @SqlUpdate("INSERT INTO option (poll_id, text, created_at, updated_at) " +
            "values (0, ?, null, null)")
//    @RegisterBeanMapper(value = Option.class, prefix = "o")
    public void insertOption(String text);
}
