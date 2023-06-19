package com.obligatoriobd.entities.constructors;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConstructorStanding implements IDataBaseEntity {

    public static final String TABLE_NAME = "Constructor_Standings";

    private int constructorStandingsId;
    private int raceId;
    private int constructorId;
    private int points;
    private int position;
    private String positionText;
    private int wins;

    public ConstructorStanding(int id, int aRaceId, int aConstructorId, int pointsValue, int aPosition, int winsValue) {
        constructorStandingsId = id;
        raceId = aRaceId;
        constructorId = aConstructorId;
        points = pointsValue;
        position = aPosition;
        positionText = String.valueOf(position);
        wins = winsValue;
    }


    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Constructor_Standings (constructor_standings_id, race_id, constructor_id, points, position, position_text, wins) VALUES(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, constructorStandingsId);
        preparedStatement.setInt(2, raceId);
        preparedStatement.setInt(3, constructorId);
        preparedStatement.setInt(4, points);
        preparedStatement.setInt(5, position);
        preparedStatement.setString(6, positionText);
        preparedStatement.setInt(7, wins);
        return preparedStatement;
    }
}
