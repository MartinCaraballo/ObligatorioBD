package com.obligatoriobd.entities;



import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.utils.Convertions;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static com.obligatoriobd.utils.Convertions.convertToDouble;




public class Race {
    public static final String TABLE_NAME = "races";

    private Integer raceId;
    private Integer year;
    private Integer round;
    private Integer circuitId;
    private String name;
    private Date date;
    private String time;
    private String url;
    private Date fp1Date;
    private String fp1Time;
    private Date fp2Date;
    private String fp2Time;
    private Date fp3Date;
    private String fp3Time;
    private Date qualificationDate;
    private String qualificationTime;
    private Date sprintDate;
    private String sprintTime;

    public Race(Integer raceId, Integer year, Integer round, Integer circuitId, String name, Date date, String time,
                String url, Date fp1Date, String fp1Time, Date fp2Date, String fp2Time, Date fp3Date, String fp3Time,
                Date qualificationDate, String qualificationTime, Date sprintDate, String sprintTime) {
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
        String baseQuery = "INSERT INTO races (race_id, year, round, circuit_id, name, date, time, url, fp1_date, fp1_time, " +
                "fp2_date, fp2_time, fp3_date, fp3_time, qualification_date, qualification_time, sprint_date, sprint_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, raceId);
        preparedStatement.setInt(2, year);
        preparedStatement.setInt(3, round);
        preparedStatement.setInt(4, circuitId);
        preparedStatement.setString(5, name);
        preparedStatement.setDate(6, date);
        preparedStatement.setString(7, time);
        preparedStatement.setString(8, url);
        preparedStatement.setDate(9, fp1Date);
        preparedStatement.setString(10, fp1Time);
        preparedStatement.setDate(11, fp2Date);
        preparedStatement.setString(12, fp2Time);
        preparedStatement.setDate(13, fp3Date);
        preparedStatement.setString(14, fp3Time);
        preparedStatement.setDate(15, qualificationDate);
        preparedStatement.setString(16, qualificationTime);
        preparedStatement.setDate(17, sprintDate);
        preparedStatement.setString(18, sprintTime);

        return preparedStatement;
    }

    /**
     * Method to create a Race object from a CSV line.
     *
     * @param csvLineDataSplitted array containing the CSV line data necessary to create a Race object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return Race object if the data given was ok, null if it does not.
     */
    public static Race createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) throws NumberFormatException {
        Integer raceId = Integer.parseInt(csvLineDataSplitted[0]);
        Integer year = Integer.parseInt(csvLineDataSplitted[1]);
        Integer round = Integer.parseInt(csvLineDataSplitted[2]);
        Integer circuitId = Integer.parseInt(csvLineDataSplitted[3]);
        String name = csvLineDataSplitted[4].replace("\"", "");
        Date date = convertToDate(csvLineDataSplitted[5], dataLineNumber);
        String time = csvLineDataSplitted[6].replace("\"", "");
        String url = csvLineDataSplitted[7].replace("\"", "");
        Date fp1Date = convertToDate(csvLineDataSplitted[8], dataLineNumber);
        String fp1Time = csvLineDataSplitted[9].replace("\"", "");
        Date fp2Date = convertToDate(csvLineDataSplitted[10], dataLineNumber);
        String fp2Time = csvLineDataSplitted[11].replace("\"", "");
        Date fp3Date = convertToDate(csvLineDataSplitted[12], dataLineNumber);
        String fp3Time = csvLineDataSplitted[13].replace("\"", "");
        Date qualificationDate = convertToDate(csvLineDataSplitted[14], dataLineNumber);
        String qualificationTime = csvLineDataSplitted[15].replace("\"", "");
        Date sprintDate = convertToDate(csvLineDataSplitted[16], dataLineNumber);
        String sprintTime = csvLineDataSplitted[17].replace("\"", "");

        return new Race(raceId, year, round, circuitId, name, date, time, url, fp1Date, fp1Time, fp2Date, fp2Time,
                fp3Date, fp3Time, qualificationDate, qualificationTime, sprintDate, sprintTime);
    }

    private static Date convertToDate(String dateStr, Integer dataLineNumber) {
        // Implement your conversion logic here
        return null;
    }

    private static Time convertToTime(String timeStr, Integer dataLineNumber) {
        // Implement your conversion logic here
        return null;
    }

    private static Date convertToDate(String dateStr, Integer dataLineNumber) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.err.println("Error parsing date at line " + dataLineNumber + ": " + dateStr);
            return null;
        }
    }

}
