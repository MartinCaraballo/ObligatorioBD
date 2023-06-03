package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Status implements IDataBaseEntity {

    public static final String TABLE_NAME = "Status";

    private int statusId;
    private String statusName;

    private Status(int aStatusId, String aStatusName) {
        statusId = aStatusId;
        statusName = aStatusName;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Status (status_id, status) VALUES(?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, statusId);
        preparedStatement.setString(2, statusName);
        return preparedStatement;
    }

    /**
     * Method to create a status object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create a status object.
     * @return Status object if the data given was ok, null if it not does.
     */
    public static Status createFromCsv(String[] csvLineDataSplitted) throws NumberFormatException {
        String statusName = csvLineDataSplitted[1].replace("\"", "");
        return new Status(Integer.parseInt(csvLineDataSplitted[0]), statusName);
    }
}
