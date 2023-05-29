package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class PitStop implements IDataBaseEntity {

    private int raceId;
    private int driverId;
    private int stop;
    private int lap;
    private Time time;
    private Time duration;
    private Time milliseconds;

    public PitStop(int aRaceId, int aDriverId, int aStopNumber, int aLapNumber, Time aTime, Time aDuration, Time millisecondsValue) {
        raceId = aRaceId;
        driverId = aDriverId;
        stop = aStopNumber;
        lap = aLapNumber;
        time = aTime;
        duration = aDuration;
        milliseconds = millisecondsValue;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO PitStops (race_id, driver_id, stop, lap, time, duration, milliseconds) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, raceId);
        preparedStatement.setInt(2, driverId);
        preparedStatement.setInt(3, stop);
        preparedStatement.setInt(4, lap);
        preparedStatement.setTime(5, time);
        preparedStatement.setTime(6, duration);
        preparedStatement.setTime(7, milliseconds);
        return preparedStatement;
    }
}
