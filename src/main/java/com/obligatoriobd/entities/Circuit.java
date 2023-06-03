package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Circuit implements IDataBaseEntity {

    public static final String TABLE_NAME = "Circuit";

    private int circuitId;
    private String circuitRef;
    private String name;
    private String location;
    private String country;
    private double lat;
    private double lng;
    private double alt;
    private String url;

    public Circuit(int aCircuitId, String aCircuitRef, String aName, String aLocation, String aCountry, double latValue, double lngValue, double altValue, String aUrl) {
        circuitId = aCircuitId;
        circuitRef = aCircuitRef;
        name = aName;
        location = aLocation;
        country = aCountry;
        lat = latValue;
        lng = lngValue;
        alt = altValue;
        url = aUrl;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Circuits (circuit_id, circuit_ref, name, location, country, lat, lng, alt, url) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, circuitId);
        preparedStatement.setString(2, circuitRef);
        preparedStatement.setString(3, name);
        preparedStatement.setString(4, location);
        preparedStatement.setString(5, country);
        preparedStatement.setDouble(6, lat);
        preparedStatement.setDouble(7, lng);
        preparedStatement.setDouble(8, alt);
        preparedStatement.setString(9, url);
        return preparedStatement;
    }

}
