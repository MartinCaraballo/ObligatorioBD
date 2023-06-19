package com.obligatoriobd.entities.drivers;

import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.entities.PitStop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import static com.obligatoriobd.utils.Convertions.convertToInt;
import static com.obligatoriobd.utils.Convertions.convertToTime;

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
        String baseQuery = "INSERT INTO DriverStandings (driver_standings_id, race_id, driver_id, points, position, position_text, wins) VALUES(?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, driverStandingsId);
        preparedStatement.setInt(2, raceId);
        preparedStatement.setInt(3, driverId);
        preparedStatement.setInt(4, points);
        preparedStatement.setInt(5, position);
        preparedStatement.setString(6, positionText);
        preparedStatement.setInt(7, wins);
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
        String positionText = csvLineDataSplitted[5].replace("\"", "");
        Integer wins = convertToInt(csvLineDataSplitted[6], dataLineNumber);

        return new DriverStanding(driverStandingId, raceId, driverId, points, position, positionText, wins);
    }
}
