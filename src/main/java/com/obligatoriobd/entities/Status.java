package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Status implements IDataBaseEntity {

    private int statusId;
    private String statusName;

    public Status(int aStatusId, String aStatusName) {
        statusId = aStatusId;
        statusName = aStatusName;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Status (status_id, status_name) VALUES(?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, statusId);
        preparedStatement.setString(2, statusName);
        return preparedStatement;
    }
}
