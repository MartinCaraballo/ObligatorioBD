package com.obligatoriobd.entities.drivers;

import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.entities.PitStop;

import java.lang.reflect.Field;
import java.sql.*;

import static com.obligatoriobd.utils.Convertions.*;

public class DriverStanding implements IDataBaseEntity {

    public static final String TABLE_NAME = "Driver_Standings";

    private Integer driverStandingsId;
    private Integer raceId;
    private Integer driverId;
    private Integer points;
    private Integer position;
    private String positionText;
    private Integer wins;

    private DriverStanding(Integer id, Integer aRaceId, Integer aDriverId, Integer pointsValue, Integer aPosition, String aPositionText, Integer winsValue) {
        driverStandingsId = id;
        raceId = aRaceId;
        driverId = aDriverId;
        points = pointsValue;
        position = aPosition;
        positionText = aPositionText;
        wins = winsValue;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Driver_Standings (driver_standings_id, race_id, driver_id, points, position, position_text, wins) VALUES(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        Field[] objectData = getClass().getDeclaredFields();
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    if (i == 6) {
                        preparedStatement.setNull(i, Types.VARCHAR);
                    } else {
                        preparedStatement.setNull(i, Types.INTEGER);
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
     * Method to create a driver standing object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create the object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return DriverStanding object if the data given was ok, null if it not does.
     */
    public static DriverStanding createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer driverStandingId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        Integer raceId = convertToInt(csvLineDataSplitted[1], dataLineNumber);
        Integer driverId = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        Integer points = convertToInt(csvLineDataSplitted[3], dataLineNumber);
        Integer position = convertToInt(csvLineDataSplitted[4], dataLineNumber);
        String positionText = returnStringOrNull(csvLineDataSplitted[5].replace("\"", ""), dataLineNumber);
        Integer wins = convertToInt(csvLineDataSplitted[6], dataLineNumber);

        return new DriverStanding(driverStandingId, raceId, driverId, points, position, positionText, wins);
    }
}
