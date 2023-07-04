package com.obligatoriobd.entities.drivers;

import com.obligatoriobd.database.IDataBaseEntity;

import java.lang.reflect.Field;
import java.sql.*;

import static com.obligatoriobd.utils.Convertions.*;

public class Driver implements IDataBaseEntity {

    public static final String TABLE_NAME = "Drivers";

    private Integer driverId;
    private String driverRef;
    private Integer number;
    private String code;
    private String forename;
    private String surname;
    private Date dateOfBirth;
    private String nationality;
    private String url;

    private Driver(Integer aDriverId, String aDriverRef, Integer aNumber, String aCode, String aForename, String aSurname, Date aDateOfBirth, String aNationality, String aUrl) {
        driverId = aDriverId;
        driverRef = aDriverRef;
        number = aNumber;
        code = aCode;
        forename = aForename;
        surname = aSurname;
        dateOfBirth = aDateOfBirth;
        nationality = aNationality;
        url = aUrl;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Drivers (driver_id, driver_ref, number, code, forename, surname, date_of_birth, nationality, url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        Field[] objectData = getClass().getDeclaredFields();
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    if (i == 3) {
                        preparedStatement.setNull(i, Types.INTEGER);
                    } else {
                        preparedStatement.setNull(i, Types.VARCHAR);
                    }
                } else if (actualFieldValue.getClass().equals(Integer.class)) {
                    preparedStatement.setInt(i, (int) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(String.class)) {
                    preparedStatement.setString(i, (String) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(Date.class)) {
                    preparedStatement.setDate(i, (Date) actualFieldValue);
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Error getting data to insert.");
            }
        }
        return preparedStatement;
    }

    /**
     * Method to create a driver object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create the object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return Driver object if the data given was ok, null if it not does.
     */
    public static Driver createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer driverId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        String driverRef = returnStringOrNull(csvLineDataSplitted[1].replace("\"", ""), dataLineNumber);
        Integer number = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        String code = returnStringOrNull(csvLineDataSplitted[3].replace("\"", ""), dataLineNumber);
        String forename = returnStringOrNull(csvLineDataSplitted[4].replace("\"", ""), dataLineNumber);
        String surname = returnStringOrNull(csvLineDataSplitted[5].replace("\"", ""), dataLineNumber);
        Date dateOfBirth = convertToDate(csvLineDataSplitted[6], dataLineNumber);
        String nationality = returnStringOrNull(csvLineDataSplitted[7].replace("\"", ""), dataLineNumber);
        String url = returnStringOrNull(csvLineDataSplitted[8].replace("\"", ""), dataLineNumber);

        return new Driver(driverId, driverRef, number, code, forename, surname, dateOfBirth, nationality, url);
    }


}
