package com.bootcamp.schema;

import net.corda.core.node.ServiceHub;
import net.corda.core.serialization.SingletonSerializeAsToken;

import java.sql.SQLException;
import java.text.MessageFormat;

public class SchemaInitializer extends SingletonSerializeAsToken {

    private ServiceHub serviceHub;

    public SchemaInitializer(ServiceHub serviceHub) {
        this.serviceHub = serviceHub;
    }

    public void init() throws SQLException {

        String tableName = "token_child_states";
        String query = MessageFormat.format("create table if not exists ${}()", tableName);

        java.sql.Connection session = serviceHub.jdbcSession();
        java.sql.PreparedStatement preparedStatements = session.prepareStatement(query);

        try {
            preparedStatements.execute();
        } catch (Error SQLException) {
            throw SQLException;
        } finally {
            session.close();
            System.out.println( MessageFormat.format("Created table ${}", tableName));
        }

    }

}