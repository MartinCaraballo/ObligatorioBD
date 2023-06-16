package com.obligatoriobd.entities.constructors;


import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Constructor implements IDataBaseEntity {

    public static final String TABLE_NAME = "Constructors";

    private int constructorId;
    private String constructorRef;
    private String name;
    private String nationality;
    private String url;

    public Constructor(int aConstructorId, String aConstructorRef, String aName, String aNationality, String aUrl) {
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
        preparedStatement.setInt(1, constructorId);
        preparedStatement.setString(2, constructorRef);
        preparedStatement.setString(3, name);
        preparedStatement.setString(4, nationality);
        preparedStatement.setString(5, url);
        return preparedStatement;
    }

}
