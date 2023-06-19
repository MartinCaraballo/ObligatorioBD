package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static com.obligatoriobd.utils.Convertions.convertToDouble;
import static com.obligatoriobd.utils.Convertions.convertToInt;

public class Result implements IDataBaseEntity {

    public final String TABLE_NAME = "Results";

    private Integer resultId;
    private Integer raceId;
    private Integer driverId;
    private Integer constructorId;
    private Integer number;
    private Integer grid;
    private Integer position;
    private String positionText;
    private Integer positionOrder;
    private Integer points;
    private Integer laps;
    private String time;
    private Integer milliseconds;
    private Integer fastestLap;
    private Integer resultRank;
    private String fastestLapTime;
    private Double fastestLapSpeed;
    private Integer statusId;

    private Result(Integer aResultId, Integer aRaceId, Integer aDriverId, Integer aConstructorId, Integer aNumber,
                   Integer aGridValue, Integer aPosition, String aPositionText, Integer aPositionOrder, Integer aPointsValue,
                   Integer aLapsValue, String aTimeValue, Integer aMillisecondsValue, Integer aFastestLap, Integer aResultRank,
                   String aFastestLapTime, Double aFastestLapSpeed, Integer aStatusId) {
        resultId = aResultId;
        raceId = aRaceId;
        driverId = aDriverId;
        constructorId = aConstructorId;
        number = aNumber;
        grid = aGridValue;
        position = aPosition;
        positionText = aPositionText;
        positionOrder = aPositionOrder;
        points = aPointsValue;
        laps = aLapsValue;
        time = aTimeValue;
        milliseconds = aMillisecondsValue;
        fastestLap = aFastestLap;
        resultRank = aResultRank;
        fastestLapTime = aFastestLapTime;
        fastestLapSpeed = aFastestLapSpeed;
        statusId = aStatusId;
    }


    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Results (" +
                "result_id," +
                "race_id," +
                "driver_id," +
                "constructor_id," +
                "number," +
                "grid," +
                "position," +
                "position_text," +
                "position_order," +
                "points," +
                "laps," +
                "time," +
                "milliseconds," +
                "fastest_lap," +
                "result_rank," +
                "fastest_lap_time," +
                "fastest_lap_speed," +
                "status_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        // Get the object data
        Field[] objectData = getClass().getDeclaredFields();
        // Starts in 1 to avoid the static field.
        for (int i = 0; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    preparedStatement.setNull(i, Types.INTEGER);
                } else if (actualFieldValue.getClass().equals(Integer.class)) {
                    preparedStatement.setInt(i, (int) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(String.class)) {
                    preparedStatement.setString(i, (String) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(Double.class)) {
                    preparedStatement.setDouble(i, (Double) actualFieldValue);
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Error getting the data to insert.");
            }
        }
        return preparedStatement;
    }

    /**
     * Method to create a result object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create a circuit object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return Result object if the data given was ok, null if it not does.
     */
    public static Result createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer resultId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        Integer raceId = convertToInt(csvLineDataSplitted[1], dataLineNumber);
        Integer driverId = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        Integer constructorId = convertToInt(csvLineDataSplitted[3], dataLineNumber);
        Integer number = convertToInt(csvLineDataSplitted[4], dataLineNumber);
        Integer grid = convertToInt(csvLineDataSplitted[5], dataLineNumber);
        Integer position = convertToInt(csvLineDataSplitted[6], dataLineNumber);
        String positionText = csvLineDataSplitted[7].replace("\"", "");
        Integer positionOrder = convertToInt(csvLineDataSplitted[8], dataLineNumber);
        Integer points = convertToInt(csvLineDataSplitted[9], dataLineNumber);
        Integer laps = convertToInt(csvLineDataSplitted[10], dataLineNumber);
        String time = csvLineDataSplitted[11].replace("\"", "");
        Integer milliseconds = convertToInt(csvLineDataSplitted[12], dataLineNumber);
        Integer fastestLap = convertToInt(csvLineDataSplitted[13], dataLineNumber);
        Integer resultRank = convertToInt(csvLineDataSplitted[14], dataLineNumber);
        String fastestLapTime = csvLineDataSplitted[15].replace("\"", "");
        Double fastestLapSpeed = convertToDouble(csvLineDataSplitted[16], dataLineNumber);
        Integer statusId = convertToInt(csvLineDataSplitted[17], dataLineNumber);

        return new Result(
                resultId, raceId, driverId, constructorId, number, grid, position, positionText,
                positionOrder, points, laps, time, milliseconds, fastestLap, resultRank, fastestLapTime,
                fastestLapSpeed, statusId
        );
    }
}
