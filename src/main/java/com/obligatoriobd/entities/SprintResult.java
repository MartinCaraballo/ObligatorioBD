package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class SprintResult implements IDataBaseEntity {

    private int resultId;
    private int raceId;
    private int driverId;
    private int constructorId;
    private int number;
    private int grid;
    private int position;
    private String positionText;
    private int positionOrder;
    private int points;
    private byte laps;
    private Time time;
    private Time milliseconds;
    private byte fastestLap;
    private Time fastestLapTime;
    private int statusId;

    public SprintResult(int aResultId, int aRaceId, int aDriverId, int aConstructorId, int aNumber, int aGridValue, int aPosition,
                        int aPositionOrder, int aPointsValue, byte aLapsValue, Time aTime, Time aMillisecondsValue, byte aFastestLap,
                        Time aFastestLapTime, int aStatusId) {
        resultId = aResultId;
        raceId = aRaceId;
        driverId = aDriverId;
        constructorId = aConstructorId;
        number = aNumber;
        grid = aGridValue;
        position = aPosition;
        positionText = String.valueOf(aPosition);
        positionOrder = aPositionOrder;
        points = aPointsValue;
        laps = aLapsValue;
        time = aTime;
        milliseconds = aMillisecondsValue;
        fastestLap = aFastestLap;
        fastestLapTime = aFastestLapTime;
        statusId = aStatusId;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Sprint_Results (" +
                "result_id," +
                "race_id," +
                "driver_id," +
                "constructor_id," +
                "number, " +
                "grid," +
                "position," +
                "position_text" +
                "position_order," +
                "points," +
                "laps," +
                "time," +
                "milliseconds," +
                "fastest_lap," +
                "fastest_lap_time," +
                "status_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, resultId);
        preparedStatement.setInt(2, raceId);
        preparedStatement.setInt(3, driverId);
        preparedStatement.setInt(4, constructorId);
        preparedStatement.setInt(5, number);
        preparedStatement.setInt(6, grid);
        preparedStatement.setInt(7, position);
        preparedStatement.setString(8, positionText);
        preparedStatement.setInt(9, positionOrder);
        preparedStatement.setInt(10, points);
        preparedStatement.setInt(11, laps);
        preparedStatement.setTime(12, time);
        preparedStatement.setTime(13, milliseconds);
        preparedStatement.setInt(14, fastestLap);
        preparedStatement.setTime(15, fastestLapTime);
        preparedStatement.setInt(16, statusId);
        return preparedStatement;
    }
}
