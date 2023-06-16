package com.obligatoriobd.entities.drivers;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.*;

public class Driver implements IDataBaseEntity {

    public static final String TABLE_NAME = "Drivers";

    private int driverId;
    private String driverRef;
    private int number;
    private String code;
    private String forename;
    private String surname;
    private Date dateOfBirth;
    private String nationality;
    private String url;

    public Driver(int aDriverId, String aDriverRef, int aNumber, String aCode, String aForename, String aSurname, Date aDateOfBirth, String aNationality, String aUrl) {
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
        preparedStatement.setInt(1, driverId);
        preparedStatement.setString(2, driverRef);
        preparedStatement.setInt(3, number);
        preparedStatement.setString(4, code);
        preparedStatement.setString(5, forename);
        preparedStatement.setString(6, surname);
        preparedStatement.setDate(7, dateOfBirth);
        preparedStatement.setString(8, nationality);
        preparedStatement.setString(9, url);
        return preparedStatement;
    }
}
