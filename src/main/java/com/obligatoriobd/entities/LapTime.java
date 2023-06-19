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
    private String time;
    private int milliseconds;

    public LapTime(int aRaceId, int aDriverId, int lapNumber, int aPosition, String aTime, int millisecondsValue) {
        raceId = aRaceId;
        driverId = aDriverId;
        lap = lapNumber;
        position = aPosition;
        time = aTime;
        milliseconds = millisecondsValue;
    }
    public static LapTime createFromCsv(String[] csvLineDataSplitted) throws NumberFormatException {
        for (int i = 0; i < csvLineDataSplitted.length; i++) {
            csvLineDataSplitted[i] = csvLineDataSplitted[i].replace("\"", "");
        }

        int raceId = Integer.parseInt(csvLineDataSplitted[0]);
        int driverId = Integer.parseInt(csvLineDataSplitted[1]);

        int lap = Integer.parseInt(csvLineDataSplitted[3]);
        int position = Integer.parseInt(csvLineDataSplitted[3]);
        String time = csvLineDataSplitted[4];

        int milliseconds = Integer.parseInt(csvLineDataSplitted[6]);

        return new LapTime(raceId,driverId,lap,position,time,milliseconds);
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO LapTimes (race_id, driver_id, lap, position, time, milliseconds) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, raceId);
        preparedStatement.setInt(2, driverId);
        preparedStatement.setInt(3, lap);
        preparedStatement.setInt(4, position);
        preparedStatement.setString(5, time);
        preparedStatement.setInt(6, milliseconds);
        return preparedStatement;
    }

}
