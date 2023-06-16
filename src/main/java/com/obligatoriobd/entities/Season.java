package com.obligatoriobd.entities;

import com.obligatoriobd.database.IDataBaseEntity;

import java.sql.*;

public class Season implements IDataBaseEntity {

    public static final String TABLE_NAME = "Seasons";

    private int year;
    private String url;

    private Season(int seasonYear, String seasonInfoURL) {
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

    /**
     * Method to create a season object from a csv line.
     *
     * @param csvLineDataSplitted csv line containing the data necessary to create a season object.
     * @return Season object if the data given was ok, null if it not does.
     */
    public static Season createFromCsv(String[] csvLineDataSplitted, Integer dataLineNumber) throws NumberFormatException {
        try {
            String seasonUrl = csvLineDataSplitted[1].replace("\"", "");
            return new Season(Integer.parseInt(csvLineDataSplitted[0]), seasonUrl);
        } catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException("Error parsing data in line " + dataLineNumber + ". Wrong or empty data.");
        }

    }

}
