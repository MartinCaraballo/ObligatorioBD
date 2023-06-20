package com.obligatoriobd.entities.constructors;


import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.entities.Circuit;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static com.obligatoriobd.utils.Convertions.convertToDouble;

public class Constructor implements IDataBaseEntity {

    public static final String TABLE_NAME = "Constructors";

    private Integer constructorId;
    private String constructorRef;
    private String name;
    private String nationality;
    private String url;

    public Constructor(Integer aConstructorId, String aConstructorRef, String aName, String aNationality, String aUrl) {
        constructorId = aConstructorId;
        constructorRef = aConstructorRef;
        name = aName;
        nationality = aNationality;
        url = aUrl;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException {
        String baseQuery = "INSERT INTO Constructors (constructor_id, constructor_ref, name, nationality, url) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
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
    public static Constructor createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) throws NumberFormatException {

        Integer constructorId = Integer.parseInt(csvLineDataSplitted[0]);
        String constructorRef = csvLineDataSplitted[1].replace("\"", "");
        String name = csvLineDataSplitted[2].replace("\"", "");
        String nationality = csvLineDataSplitted[3].replace("\"", "");
        String url = csvLineDataSplitted[4].replace("\"", "");

        return new Constructor (constructorId, constructorRef, name, nationality, url);
    }
}
