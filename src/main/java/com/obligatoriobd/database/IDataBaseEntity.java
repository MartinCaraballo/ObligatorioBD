package com.obligatoriobd.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IDataBaseEntity {

    /**
     * Method for the database controller to get the sql query with the object that contains the data to persist.
     * @param dataBaseConnection Connection to the database necessary to get the preparedStatement and build the query.
     * @return                   PreparedStatement ready to execute the query.
     * @throws SQLException      If an error occur with the connection.
     */
    PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException;
}
