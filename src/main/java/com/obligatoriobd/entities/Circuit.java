package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.utils.Convertions;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static com.obligatoriobd.utils.Convertions.convertToDouble;

public class Circuit implements IDataBaseEntity {

    public static final String TABLE_NAME = "Circuits";

    private Integer circuitId;
    private String circuitRef;
    private String name;
    private String location;
    private String country;
    private Double lat;
    private Double lng;
    private Double alt;
    private String url;

    public Circuit(Integer aCircuitId, String aCircuitRef, String aName, String aLocation, String aCountry, Double latValue, Double lngValue, Double altValue, String aUrl) {
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
        // Obtaining the data variables of the object.
        Field[] objectData = getClass().getDeclaredFields();
        // Starts in 1 to avoid the static field.
        for (int i = 1; i < objectData.length; i++) {
            try {
                Object actualFieldValue = objectData[i].get(this);
                if (actualFieldValue == null) {
                    preparedStatement.setNull(i, Types.DOUBLE);
                } else if (actualFieldValue.getClass().equals(Integer.class)) {
                    preparedStatement.setInt(i, (int) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(String.class)) {
                    preparedStatement.setString(i, (String) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(Double.class)) {
                    preparedStatement.setDouble(i, (double) actualFieldValue);
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Error getting data to insert.");
            }
        }
        return preparedStatement;
    }

    /**
     * Method to create a circuit object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create a circuit object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return Circuit object if the data given was ok, null if it not does.
     */
    public static Circuit createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {
        Integer circuitId = Integer.parseInt(csvLineDataSplitted[0]);
        String circuitRef = csvLineDataSplitted[1].replace("\"", "");
        String name = csvLineDataSplitted[2].replace("\"", "");
        String location = csvLineDataSplitted[3].replace("\"", "");
        String country = csvLineDataSplitted[4].replace("\"", "");
        Double latitude = convertToDouble(csvLineDataSplitted[5], dataLineNumber);
        Double longitude = convertToDouble(csvLineDataSplitted[6], dataLineNumber);
        Double altitude = convertToDouble(csvLineDataSplitted[7], dataLineNumber);
        String url = csvLineDataSplitted[8].replace("\"", "");
        return new Circuit(circuitId, circuitRef, name, location, country, latitude, longitude, altitude, url);
    }
}
