package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.*;

public class Season implements IDataBaseEntity {

    public static final String TABLE_NAME = "Season";

    private int year;
    private String url;

    public Season(int seasonYear, String seasonInfoURL) {
        year = seasonYear;
        url = seasonInfoURL;
    }

    @Override
    public PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException{
        String baseQuery = "INSERT INTO Seasons (year, url) VALUES(?, ?);";
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(baseQuery);
        preparedStatement.setInt(1, year);
        preparedStatement.setString(2, url);
        return preparedStatement;
    }

}
