package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.lang.reflect.Field;
import java.sql.*;

import static com.obligatoriobd.utils.Convertions.*;

public class PitStop implements IDataBaseEntity {

    public static final String TABLE_NAME = "Pit_Stops";

    private Integer pitStopId;
    private Integer raceId;
    private Integer driverId;
    private Integer stop;
    private Integer lap;
    private Time time;
    private String duration;
    private Integer milliseconds;

    private PitStop(Integer aPitStopId, Integer aRaceId, Integer aDriverId, Integer aStopNumber, Integer aLapNumber, Time aTime, String aDuration, Integer millisecondsValue) {
        pitStopId = aPitStopId;
        raceId = aRaceId;
        driverId = aDriverId;
        stop = aStopNumber;
        lap = aLapNumber;
        time = aTime;
        duration = aDuration;
        milliseconds = millisecondsValue;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Pit_Stops (pit_stop_id, race_id, driver_id, stop, lap, time, duration, milliseconds) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        // Obtaining the data variables of the object.
        Field[] objectData = getClass().getDeclaredFields();
        // Starts in 1 to avoid static field.
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue.getClass().equals(Integer.class)) {
                    if (i == 7) {
                        preparedStatement.setNull(i, Types.VARCHAR);
                    } else {
                        preparedStatement.setInt(i, (int) actualFieldValue);
                    }
                } else if (actualFieldValue.getClass().equals(String.class)) {
                    preparedStatement.setString(i, (String) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(Time.class)) {
                    preparedStatement.setTime(i, (Time) actualFieldValue);
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Error getting data to insert.");
            }

        }
        return preparedStatement;
    }

    /**
     * Method to create a pit stop object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create the object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return PitStop object if the data given was ok, null if it not does.
     */
    public static PitStop createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer raceId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        Integer driverId = convertToInt(csvLineDataSplitted[1], dataLineNumber);
        Integer stop = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        Integer lap = convertToInt(csvLineDataSplitted[3], dataLineNumber);
        Time time = convertToTime(csvLineDataSplitted[4], dataLineNumber);
        String duration = returnStringOrNull(csvLineDataSplitted[5].replace("\"", ""), dataLineNumber);
        Integer milliseconds = convertToInt(csvLineDataSplitted[6], dataLineNumber);

        return new PitStop(dataLineNumber - 1, raceId, driverId, stop, lap, time, duration, milliseconds);
    }

}
