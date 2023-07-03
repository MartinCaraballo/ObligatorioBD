package com.obligatoriobd.entities.constructors;


import com.obligatoriobd.database.IDataBaseEntity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static com.obligatoriobd.utils.Convertions.convertToInt;
import static com.obligatoriobd.utils.Convertions.returnStringOrNull;

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
                if (actualFieldValue == null) {
                    preparedStatement.setNull(i, Types.VARCHAR);
                } else if (actualFieldValue.getClass().equals(Integer.class)) {
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
        String constructorRef = returnStringOrNull(csvLineDataSplitted[1].replace("\"", ""), dataLineNumber);
        String name = returnStringOrNull(csvLineDataSplitted[2].replace("\"", ""), dataLineNumber);
        String nationality = returnStringOrNull(csvLineDataSplitted[3].replace("\"", ""), dataLineNumber);
        String url = returnStringOrNull(csvLineDataSplitted[4].replace("\"", ""), dataLineNumber);

        return new Constructor (constructorId, constructorRef, name, nationality, url);
    }
}
