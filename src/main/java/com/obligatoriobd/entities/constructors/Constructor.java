package com.obligatoriobd.entities.constructors;


import com.obligatoriobd.database.IDataBaseEntity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.obligatoriobd.utils.Convertions.convertToInt;

public class Constructor implements IDataBaseEntity {

    public static final String TABLE_NAME = "Constructors";

    private Integer constructorId;
    private String constructorRef;
    private String name;
    private String nationality;
    private String url;

    private Constructor(Integer aConstructorId, String aConstructorRef, String aName, String aNationality, String aUrl) {
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
                if (actualFieldValue.getClass().equals(Integer.class)) {
                    preparedStatement.setInt(i, (int) actualFieldValue);
                } else if (actualFieldValue.getClass().equals(String.class)) {
                    preparedStatement.setString(i, (String) actualFieldValue);
                }
            } catch (IllegalAccessException illegalAccessException) {
                System.err.println("Error getting data to insert.");
            }
        }
        return preparedStatement;
    }

    /**
     * Method to create a constructor object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create the object.
     * @param dataLineNumber      number of the line in the source file to indicate an error if occurred.
     * @return Constructor object if the data given was ok, null if it not does.
     */
    public static Constructor createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) {

        Integer constructorId = convertToInt(csvLineDataSplitted[0], dataLineNumber);
        String constructorRef = csvLineDataSplitted[1].replace("\"", "");
        String name = csvLineDataSplitted[2].replace("\"", "");
        String nationality = csvLineDataSplitted[3].replace("\"", "");
        String url = csvLineDataSplitted[4].replace("\"", "");

        return new Constructor (constructorId, constructorRef, name, nationality, url);
    }
}
