package com.obligatoriobd.entities.constructors;

import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.entities.Circuit;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static com.obligatoriobd.utils.Convertions.convertToDouble;
import static com.obligatoriobd.utils.Convertions.convertToInt;

public class ConstructorStanding implements IDataBaseEntity {

    public static final String TABLE_NAME = "Constructor_Standings";

    private Integer constructorStandingsId;
    private Integer raceId;
    private Integer constructorId;
    private Integer points;
    private Integer position;
    private String positionText;
    private Integer wins;

    private ConstructorStanding(Integer id, Integer aRaceId, Integer aConstructorId, Integer pointsValue, Integer aPosition, String apositionText, Integer winsValue) {
        constructorStandingsId = id;
        raceId = aRaceId;
        constructorId = aConstructorId;
        points = pointsValue;
        position = aPosition;
        positionText = apositionText;
        wins = winsValue;
    }


    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Constructor_Standings (constructor_standings_id, race_id, constructor_id, points, position, position_text, wins) VALUES(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        Field[] objectData = getClass().getDeclaredFields();
        // Starts in 1 to avoid the static field.
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    preparedStatement.setNull(i, Types.INTEGER);
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
     * Method to create a constructor standing object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create the object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return ConstructorStanding object if the data given was ok, null if it not does.
     */
    public static ConstructorStanding createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer constructorStandingsId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        Integer raceId = convertToInt(csvLineDataSplitted[1], dataLineNumber);
        Integer constructorId = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        Integer points = convertToInt(csvLineDataSplitted[3], dataLineNumber);
        Integer position = convertToInt(csvLineDataSplitted[4], dataLineNumber);
        String positionText = csvLineDataSplitted[5].replace("\"", "");
        Integer wins = convertToInt(csvLineDataSplitted[6], dataLineNumber);


        return new ConstructorStanding(constructorStandingsId, raceId, constructorId, points, position, positionText, wins);
    }
}
