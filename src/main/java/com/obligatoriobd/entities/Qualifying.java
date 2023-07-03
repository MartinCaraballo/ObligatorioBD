package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static com.obligatoriobd.utils.Convertions.*;

public class Qualifying implements IDataBaseEntity {

    public static final String TABLE_NAME = "Qualifying";

    private Integer qualifyId;
    private Integer raceId;
    private Integer driverId;
    private Integer constructorId;
    private Integer number;
    private Integer position;
    private String q1;
    private String q2;
    private String q3;

    private Qualifying(Integer id, Integer aRaceId, Integer aDriverId, Integer aConstructorId, Integer aNumber, Integer aPosition, String aQ1, String aQ2, String aQ3) {
        qualifyId = id;
        raceId = aRaceId;
        driverId = aDriverId;
        constructorId = aConstructorId;
        number = aNumber;
        position = aPosition;
        q1 = aQ1;
        q2 = aQ2;
        q3 = aQ3;
    }


    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Qualifying (qualify_id, race_id, driver_id, constructor_id, number, position, q1, q2, q3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        // Obtaining the data variables of the object.
        Field[] objectData = getClass().getDeclaredFields();
        // Starts in 1 to avoid the static field.
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    switch (i) {
                        case 7, 8, 9 -> preparedStatement.setNull(i, Types.VARCHAR);
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
     * Method to create a qualifying object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create a circuit object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return Qualifying object if the data given was ok, null if it not does.
     */
    public static Qualifying createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer qualifyId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        Integer raceId = convertToInt(csvLineDataSplitted[1], dataLineNumber);
        Integer driverId = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        Integer constructorId = convertToInt(csvLineDataSplitted[3], dataLineNumber);
        Integer number = convertToInt(csvLineDataSplitted[4], dataLineNumber);
        Integer position = convertToInt(csvLineDataSplitted[5], dataLineNumber);
        String q1 = returnStringOrNull(csvLineDataSplitted[6].replace("\"", ""), dataLineNumber);
        String q2 = returnStringOrNull(csvLineDataSplitted[7].replace("\"", ""), dataLineNumber);
        String q3 = returnStringOrNull(csvLineDataSplitted[8].replace("\"", ""), dataLineNumber);

        return new Qualifying(qualifyId, raceId, driverId, constructorId, number, position, q1, q2, q3);
    }
}
