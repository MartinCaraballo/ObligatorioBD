package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;
import java.lang.reflect.Field;
import java.sql.*;

import static com.obligatoriobd.utils.Convertions.convertToInt;
import static com.obligatoriobd.utils.Convertions.returnStringOrNull;

public class SprintResult implements IDataBaseEntity {

    public static final String TABLE_NAME = "Sprint_Results";

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
    private String fastestLapTime;
    private Integer statusId;

    private SprintResult(Integer aResultId, Integer aRaceId, Integer aDriverId, Integer aConstructorId, Integer aNumber, Integer aGridValue, Integer aPosition,
                        String aPositionText, Integer aPositionOrder, Integer aPointsValue, Integer aLapsValue, String aTime, Integer aMillisecondsValue,
                         Integer aFastestLap, String aFastestLapTime, Integer aStatusId) {
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
                "fastest_lap_time," +
                "status_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        // Obtaining the data variables of the object.
        Field[] objectData = getClass().getDeclaredFields();
        // Starts in 1 to avoid the static field.
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    switch (i) {
                        case 8, 12, 15 -> preparedStatement.setNull(i, Types.VARCHAR);
                        default -> preparedStatement.setNull(i, Types.INTEGER);
                    }

                } else if (actualFieldValue.getClass().equals(Integer.class)) {
                    preparedStatement.setInt(i, (int) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(String.class)) {
                    preparedStatement.setString(i, (String) actualFieldValue);
                }

            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Error getting data to insert.");
            }
        }

        return preparedStatement;
    }

    /**
     * Method to create a sprint result object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create a sprint result object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return SprintResult object if the data given was ok, null if it not does.
     */
    public static SprintResult createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer resultId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        Integer raceId = convertToInt(csvLineDataSplitted[1], dataLineNumber);
        Integer driverId = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        Integer constructorId = convertToInt(csvLineDataSplitted[3], dataLineNumber);
        Integer number = convertToInt(csvLineDataSplitted[4], dataLineNumber);
        Integer grid = convertToInt(csvLineDataSplitted[5], dataLineNumber);
        Integer position = convertToInt(csvLineDataSplitted[6], dataLineNumber);
        String positionText = returnStringOrNull(csvLineDataSplitted[7].replace("\"", ""), dataLineNumber);
        Integer positionOrder = convertToInt(csvLineDataSplitted[8], dataLineNumber);
        Integer points = convertToInt(csvLineDataSplitted[9], dataLineNumber);
        Integer laps = convertToInt(csvLineDataSplitted[10], dataLineNumber);
        String time = returnStringOrNull(csvLineDataSplitted[11].replace("\"", ""), dataLineNumber);
        Integer milliseconds = convertToInt(csvLineDataSplitted[12], dataLineNumber);
        Integer fastestLap = convertToInt(csvLineDataSplitted[13], dataLineNumber);
        String fastestLapTime = returnStringOrNull(csvLineDataSplitted[14].replace("\"", ""), dataLineNumber);
        Integer statusId = convertToInt(csvLineDataSplitted[15], dataLineNumber);
        return new SprintResult(
                resultId,
                raceId,
                driverId,
                constructorId,
                number,
                grid,
                position,
                positionText,
                positionOrder,
                points,
                laps,
                time,
                milliseconds,
                fastestLap,
                fastestLapTime,
                statusId
        );
    }
}
