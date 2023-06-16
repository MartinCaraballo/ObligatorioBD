package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class LapTime implements IDataBaseEntity {

    public static final String TABLE_NAME = "Lap_Times";

    private int raceId;
    private int driverId;
    private int lap;
    private int position;
    private Time time;
    private Time milliseconds;

    public LapTime(int aRaceId, int aDriverId, int lapNumber, int aPosition, Time aTime, Time millisecondsValue) {
        raceId = aRaceId;
        driverId = aDriverId;
        lap = lapNumber;
        position = aPosition;
        time = aTime;
        milliseconds = millisecondsValue;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO LapTimes (race_id, driver_id, lap, position, time, milliseconds) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, raceId);
        preparedStatement.setInt(2, driverId);
        preparedStatement.setInt(3, lap);
        preparedStatement.setInt(4, position);
        preparedStatement.setTime(5, time);
        preparedStatement.setTime(6, milliseconds);
        return preparedStatement;
    }

}
