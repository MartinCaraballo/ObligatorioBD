package com.obligatoriobd.entities.drivers;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DriverStanding implements IDataBaseEntity {

    public static final String TABLE_NAME = "Driver_Standing";

    private int driverStandingsId;
    private int raceId;
    private int driverId;
    private int points;
    private int position;
    private String positionText;
    private int wins;

    public DriverStanding(int id, int aRaceId, int aDriverId, int pointsValue, int aPosition, int winsValue) {
        driverStandingsId = id;
        raceId = aRaceId;
        driverId = aDriverId;
        points = pointsValue;
        position = aPosition;
        positionText = String.valueOf(position);
        wins = winsValue;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO DriverStandings (driver_standings_id, race_id, driver_id, points, position, position_text, wins) VALUES(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, driverStandingsId);
        preparedStatement.setInt(2, raceId);
        preparedStatement.setInt(3, driverId);
        preparedStatement.setInt(4, points);
        preparedStatement.setInt(5, position);
        preparedStatement.setString(6, positionText);
        preparedStatement.setInt(7, wins);
        return preparedStatement;
    }
}
