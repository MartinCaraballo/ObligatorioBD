package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import static com.obligatoriobd.utils.Convertions.convertToInt;
import static com.obligatoriobd.utils.Convertions.convertToTime;

public class LapTime implements IDataBaseEntity {

    public static final String TABLE_NAME = "Lap_Times";

    private Integer lapTimeId;
    private Integer raceId;
    private Integer driverId;
    private Integer lap;
    private Integer position;
    private String time;
    private Integer milliseconds;

    private LapTime(Integer aLapTimeId, Integer aRaceId, Integer aDriverId, Integer lapNumber, Integer aPosition, String aTime, Integer millisecondsValue) {
        lapTimeId = aLapTimeId;
        raceId = aRaceId;
        driverId = aDriverId;
        lap = lapNumber;
        position = aPosition;
        time = aTime;
        milliseconds = millisecondsValue;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Lap_Times (lap_time_id, race_id, driver_id, lap, position, time, milliseconds) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, lapTimeId);
        preparedStatement.setInt(2, raceId);
        preparedStatement.setInt(3, driverId);
        preparedStatement.setInt(4, lap);
        preparedStatement.setInt(5, position);
        preparedStatement.setString(6, time);
        preparedStatement.setInt(7, milliseconds);
        return preparedStatement;
    }

    /**
     * Method to create a lap time object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create the object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return LapTime object if the data given was ok, null if it not does.
     */
    public static LapTime createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer raceId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        Integer driverId = convertToInt(csvLineDataSplitted[1], dataLineNumber);
        Integer lap = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        Integer position = convertToInt(csvLineDataSplitted[3], dataLineNumber);
        String time = csvLineDataSplitted[4].replace("\"", "");
        Integer milliseconds = convertToInt(csvLineDataSplitted[5], dataLineNumber);

        return new LapTime(dataLineNumber - 1, raceId, driverId, lap, position, time, milliseconds);
    }

}
