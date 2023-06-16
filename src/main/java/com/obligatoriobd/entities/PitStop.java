package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class PitStop implements IDataBaseEntity {

    public static final String TABLE_NAME = "Pit_Stop";

    private int raceId;
    private int driverId;
    private int stop;
    private int lap;
    private Time time;
    private String duration;
    private int milliseconds;

    public PitStop(int aRaceId, int aDriverId, int aStopNumber, int aLapNumber, Time aTime, String aDuration, int millisecondsValue) {
        raceId = aRaceId;
        driverId = aDriverId;
        stop = aStopNumber;
        lap = aLapNumber;
        time = aTime;
        duration = aDuration;
        milliseconds = millisecondsValue;
    }
    public static PitStop createFromCsv(String[] csvLineDataSplitted) throws NumberFormatException {
        for (int i = 0; i < csvLineDataSplitted.length; i++) {
            csvLineDataSplitted[i] = csvLineDataSplitted[i].replace("\"", "");
        }

        int raceId = Integer.parseInt(csvLineDataSplitted[0]);
        int driverId = Integer.parseInt(csvLineDataSplitted[1]);
        int stop = Integer.parseInt(csvLineDataSplitted[2]);
        int lap = Integer.parseInt(csvLineDataSplitted[3]);
        Time time = Time.valueOf(csvLineDataSplitted[4]);
        String duration = csvLineDataSplitted[5];
        int milliseconds = Integer.parseInt(csvLineDataSplitted[6]);

        return new PitStop(raceId,driverId,stop,lap,time,duration,milliseconds);
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
        preparedStatement.setString(6, duration);
        preparedStatement.setInt(7, milliseconds);
        return preparedStatement;
    }

}
