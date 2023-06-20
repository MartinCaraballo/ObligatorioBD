package com.obligatoriobd.entities.constructors;

import com.obligatoriobd.database.IDataBaseEntity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ConstructorResult implements IDataBaseEntity {

    public static final String TABLE_NAME = "Constructor_Results";

    private Integer constructorResultsId;
    private Integer raceId;
    private Integer constructorId;
    private Integer points;
    private String status;

    public ConstructorResult(Integer aConstructorResultId, Integer aRaceId, Integer aConstructorId, Integer pointsValue, String aStatusLetter) {
        constructorResultsId = aConstructorResultId;
        raceId = aRaceId;
        constructorId = aConstructorId;
        points = pointsValue;
        status = aStatusLetter;
    }


    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Constructor_Results (constructor_results_id, race_id, constructor_id, points, status) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        Field[] objectData = getClass().getDeclaredFields();
        // Starts in 1 to avoid the static field.
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    preparedStatement.setNull(i, Types.DOUBLE);
                } else if (actualFieldValue.getClass().equals(Integer.class)) {
                    preparedStatement.setInt(i, (int) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(String.class)) {
                    preparedStatement.setString(i, (String) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(Double.class)) {
                    preparedStatement.setDouble(i, (double) actualFieldValue);
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Error getting data to insert.");
            }
        }
        return preparedStatement;
    }
    public static ConstructorResult createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) throws NumberFormatException {
        Integer constructorResultsId = Integer.parseInt(csvLineDataSplitted[0]);
        Integer raceId = Integer.parseInt(csvLineDataSplitted[1]);
        Integer constructorId = Integer.parseInt(csvLineDataSplitted[2]);
        Integer points = Integer.parseInt(csvLineDataSplitted[3]);
        String status = csvLineDataSplitted[4].replace("\"", "");



        return new ConstructorResult(constructorResultsId, raceId, constructorId, points,status );
    }
}
