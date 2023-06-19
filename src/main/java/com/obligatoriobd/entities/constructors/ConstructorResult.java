package com.obligatoriobd.entities.constructors;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConstructorResult implements IDataBaseEntity {

    public static final String TABLE_NAME = "Constructor_Results";

    private int constructorResultsId;
    private int raceId;
    private int constructorId;
    private int points;
    private char status;

    public ConstructorResult(int aConstructorResultId, int aRaceId, int aConstructorId, int pointsValue, char aStatusLetter) {
        constructorResultsId = aConstructorResultId;
        raceId = aRaceId;
        constructorId = aConstructorId;
        points = pointsValue;
        status = aStatusLetter;
    }


    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Constructor_Results (constructor_results_id, race_id, constructor_id, points, status) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, constructorResultsId);
        preparedStatement.setInt(2, raceId);
        preparedStatement.setInt(3, constructorId);
        preparedStatement.setInt(4, points);
        preparedStatement.setString(5, String.valueOf(status));
        return preparedStatement;
    }
}
