package com.obligatoriobd.entities;


import com.obligatoriobd.database.IDataBaseEntity;

import java.lang.reflect.Field;
import java.sql.*;

import static com.obligatoriobd.utils.Convertions.*;


public class Race implements IDataBaseEntity {

    public static final String TABLE_NAME = "Races";

    private Integer raceId;
    private Integer year;
    private Integer round;
    private Integer circuitId;
    private String name;
    private Date date;
    private Time time;
    private String url;
    private Date fp1Date;
    private Time fp1Time;
    private Date fp2Date;
    private Time fp2Time;
    private Date fp3Date;
    private Time fp3Time;
    private Date qualificationDate;
    private Time qualificationTime;
    private Date sprintDate;
    private Time sprintTime;

    public Race(Integer raceId, Integer year, Integer round, Integer circuitId, String name, Date date, Time time,
                String url, Date fp1Date, Time fp1Time, Date fp2Date, Time fp2Time, Date fp3Date, Time fp3Time,
                Date qualificationDate, Time qualificationTime, Date sprintDate, Time sprintTime) {
        this.raceId = raceId;
        this.year = year;
        this.round = round;
        this.circuitId = circuitId;
        this.name = name;
        this.date = date;
        this.time = time;
        this.url = url;
        this.fp1Date = fp1Date;
        this.fp1Time = fp1Time;
        this.fp2Date = fp2Date;
        this.fp2Time = fp2Time;
        this.fp3Date = fp3Date;
        this.fp3Time = fp3Time;
        this.qualificationDate = qualificationDate;
        this.qualificationTime = qualificationTime;
        this.sprintDate = sprintDate;
        this.sprintTime = sprintTime;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection databaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Races (race_id, year, round, circuit_id, name, date, time, url, fp1_date, fp1_time, " +
                "fp2_date, fp2_time, fp3_date, fp3_time, qualification_date, qualification_time, sprint_date, sprint_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(baseQuery);
        Field[] objectData = getClass().getDeclaredFields();
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    switch (i) {
                        case 7, 10, 12, 14, 16, 18:
                            preparedStatement.setNull(i, Types.TIME);
                        case 9, 11, 13, 15, 17:
                            preparedStatement.setNull(i, Types.DATE);
                    }
                } else if (actualFieldValue.getClass().equals(Integer.class)) {
                    preparedStatement.setInt(i, (int) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(String.class)) {
                    preparedStatement.setString(i, (String) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(Date.class)) {
                    preparedStatement.setDate(i, (Date) actualFieldValue);
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
     * Method to create a Race object from a CSV line.
     *
     * @param csvLineDataSplitted array containing the CSV line data necessary to create a Race object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return Race object if the data given was ok, null if it does not.
     */
    public static Race createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer raceId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        Integer year = convertToInt(csvLineDataSplitted[1], dataLineNumber);
        Integer round = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        Integer circuitId = convertToInt(csvLineDataSplitted[3], dataLineNumber);
        String name = csvLineDataSplitted[4].replace("\"", "");
        Date date = convertToDate(csvLineDataSplitted[5], dataLineNumber);
        Time time = convertToTime(csvLineDataSplitted[6], dataLineNumber);
        String url = csvLineDataSplitted[7].replace("\"", "");
        Date fp1Date = convertToDate(csvLineDataSplitted[8], dataLineNumber);
        Time fp1Time = convertToTime(csvLineDataSplitted[9], dataLineNumber);
        Date fp2Date = convertToDate(csvLineDataSplitted[10], dataLineNumber);
        Time fp2Time = convertToTime(csvLineDataSplitted[11], dataLineNumber);
        Date fp3Date = convertToDate(csvLineDataSplitted[12], dataLineNumber);
        Time fp3Time = convertToTime(csvLineDataSplitted[13], dataLineNumber);
        Date qualificationDate = convertToDate(csvLineDataSplitted[14], dataLineNumber);
        Time qualificationTime = convertToTime(csvLineDataSplitted[15], dataLineNumber);
        Date sprintDate = convertToDate(csvLineDataSplitted[16], dataLineNumber);
        Time sprintTime = convertToTime(csvLineDataSplitted[17], dataLineNumber);

        return new Race(raceId, year, round, circuitId, name, date, time, url, fp1Date, fp1Time, fp2Date, fp2Time,
                fp3Date, fp3Time, qualificationDate, qualificationTime, sprintDate, sprintTime);
    }

}
