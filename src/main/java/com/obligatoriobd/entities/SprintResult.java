package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class SprintResult implements IDataBaseEntity {

    public static final String TABLE_NAME = "Sprint_Result";

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
    private int laps;
    private  String time;
    private int milliseconds;
    private int fastestLap;
    private String fastestLapTime;
    private int statusId;

    public SprintResult(int aResultId, int aRaceId, int aDriverId, int aConstructorId, int aNumber, int aGridValue, int aPosition,
                        int aPositionOrder, int aPointsValue, int aLapsValue, String aTime, int aMillisecondsValue, int aFastestLap,
                        String aFastestLapTime, int aStatusId) {
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
    public static SprintResult createFromCsv(String[] csvLineDataSplitted) throws NumberFormatException {
        for (int i = 0; i < csvLineDataSplitted.length; i++) {
            csvLineDataSplitted[i] = csvLineDataSplitted[i].replace("\"", "");
        }

        int resultId = Integer.parseInt(csvLineDataSplitted[0]);
        int raceId = Integer.parseInt(csvLineDataSplitted[1]);
        int driverId = Integer.parseInt(csvLineDataSplitted[2]);
        int constructorId = Integer.parseInt(csvLineDataSplitted[3]);
        int number = Integer.parseInt(csvLineDataSplitted[4]);
        int grid = Integer.parseInt(csvLineDataSplitted[5]);
        int position = Integer.parseInt(csvLineDataSplitted[6]);

        int positionOrder = Integer.parseInt(csvLineDataSplitted[8]);
        int points = Integer.parseInt(csvLineDataSplitted[9]);
        int laps = Integer.parseInt(csvLineDataSplitted[10]);
        String time = csvLineDataSplitted[11];
        int milliseconds = Integer.parseInt(csvLineDataSplitted[12]);
        int fastestLap = Integer.parseInt(csvLineDataSplitted[13]);
        String fastestLapTime = csvLineDataSplitted[14];
        int statusId = Integer.parseInt(csvLineDataSplitted[15]);
        return new SprintResult(resultId,raceId,driverId,constructorId,number,grid,position,positionOrder,
                points,laps,time,milliseconds,fastestLap,fastestLapTime,statusId);
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
        preparedStatement.setString(12, time);
        preparedStatement.setInt(13, milliseconds);
        preparedStatement.setInt(14, fastestLap);
        preparedStatement.setString(15, fastestLapTime);
        preparedStatement.setInt(16, statusId);
        return preparedStatement;
    }

}
