package com.obligatoriobd.entities.constructors;

import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.entities.Circuit;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static com.obligatoriobd.utils.Convertions.convertToDouble;

public class ConstructorStanding implements IDataBaseEntity {

    public static final String TABLE_NAME = "Constructor_Standings";

    private Integer constructorStandingsId;
    private Integer raceId;
    private Integer constructorId;
    private Integer points;
    private Integer position;
    private String positionText;
    private Integer wins;

    public ConstructorStanding(Integer id, Integer aRaceId, Integer aConstructorId, Integer pointsValue, Integer aPosition, String apositionText, Integer winsValue) {
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
    public static ConstructorStanding createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) throws NumberFormatException {
        Integer constructorStandingsId = Integer.parseInt(csvLineDataSplitted[0]);
        Integer raceId = Integer.parseInt(csvLineDataSplitted[1]);
        Integer constructorId = Integer.parseInt(csvLineDataSplitted[2]);
        Integer points = Integer.parseInt(csvLineDataSplitted[3]);
        Integer position = Integer.parseInt(csvLineDataSplitted[4]);

        String positionText = csvLineDataSplitted[5].replace("\"", "");
        Integer wins = Integer.parseInt(csvLineDataSplitted[6]);


        return new ConstructorStanding(constructorStandingsId, raceId, constructorId, points, position, positionText, wins);
    }
}
