package com.obligatoriobd.entities.drivers;

import com.obligatoriobd.database.IDataBaseEntity;
import static com.obligatoriobd.utils.Convertions.convertToInt;

import java.lang.reflect.Field;
import java.sql.*;

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

    public Driver(Integer aDriverId, String aDriverRef, Integer aNumber, String aCode, String aForename, String aSurname, Date aDateOfBirth, String aNationality, String aUrl) {
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
                    if (i == 2) {
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
    public static Driver createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) throws NumberFormatException {
        Integer driverId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        String driverRef = csvLineDataSplitted[1].replace("\"", "");
        Integer number = convertToInt(csvLineDataSplitted[2], dataLineNumber);
        String code = csvLineDataSplitted[3].replace("\"", "");
        String forename = csvLineDataSplitted[4].replace("\"", "");
        String surname = csvLineDataSplitted[5].replace("\"", "");
        int dateLength = csvLineDataSplitted[6].length() - 1;
        String[] dateSplitted = csvLineDataSplitted[6].substring(1, dateLength).split("-");
        Date dateOfBirth = new Date(Integer.parseInt(dateSplitted[0]), Integer.parseInt(dateSplitted[1]), Integer.parseInt(dateSplitted[2]));
        String nationality = csvLineDataSplitted[7].replace("\"", "");
        String url = csvLineDataSplitted[8].replace("\"", "");
        return new Driver(driverId, driverRef, number, code, forename, surname, dateOfBirth, nationality, url);
    }


}
