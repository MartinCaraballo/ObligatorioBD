package com.obligatoriobd.database;

import java.sql.*;
import java.util.List;

public class DataBaseController {

    private static DataBaseController dbController;

    private final String DATABASE_URL;
    private final String DATABASE_USER;
    private final String DATABASE_PASSWD;

    private DataBaseController(String dataBaseUrl, String dataBaseUser, String dataBasePasswd) {
        DATABASE_URL = dataBaseUrl;
        DATABASE_USER = dataBaseUser;
        DATABASE_PASSWD = dataBasePasswd;
    }

    /**
     * Connects to the database with the credentials indicated if there is not an existing one.
     *
     * @param dataBaseUrl    url to the database.
     * @param dataBaseUser   database's user.
     * @param dataBasePasswd database's password.
     */
    public static Boolean connect(String dataBaseUrl, String dataBaseUser, String dataBasePasswd) throws IllegalStateException {
        if (dbController == null) {
            dbController = new DataBaseController(dataBaseUrl, dataBaseUser, dataBasePasswd);
            return dbController.testConnection();
        }
        throw new IllegalStateException("There is already a connection to a database.");
    }

    /**
     * Disconnects the actual database.
     */
    public static void disconnect() { dbController = null; }

    /**
     * Change the connection if the new one is reachable.
     *
     * @param dataBaseUrl    url to the new database.
     * @param dataBaseUser   database's new user.
     * @param dataBasePasswd database's new password.
     */
    public static void changeConnection(String dataBaseUrl, String dataBaseUser, String dataBasePasswd) throws IllegalAccessException {
        DataBaseController old = dbController;
        dbController = new DataBaseController(dataBaseUrl, dataBaseUser, dataBasePasswd);
        if (dbController.testConnection()) {
            return;
        }
        dbController = old;
        throw new IllegalAccessException("The new database is unreachable. Connection was not changed.");
    }

    public Boolean testConnection() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWD);
            connection.close();
            return true;
        } catch (SQLException connectionAttempt) {
            return false;
        }
    }

    public static DataBaseController getDataBaseController() {
        return dbController;
    }

    public void insertEntity(IDataBaseEntity dataBaseEntity, List<String> errors) {
        Thread thread = new Thread(() -> {
            try {
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWD);
                PreparedStatement preparedStatement = dataBaseEntity.getInsertStatement(connection);
                preparedStatement.execute();
                connection.close();
            } catch (SQLException sqlException) {
                errors.add(sqlException.getMessage());
                System.err.println(sqlException.getMessage());
            }
        });
        thread.start();
    }

    /**
     * Sends select query passed in String.
     *
     * @param query         String with the query.
     * @return              Returns the result of the select query.
     * @throws SQLException If the query can't be sent.
     */
    public String sendSelectQuery(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWD);
        Statement dbStatement = connection.createStatement();
        ResultSet resultSet = dbStatement.executeQuery(query);
        return getQueryResult(resultSet);
    }

    /**
     * Returns the select query result.
     *
     * @param resultSet     Select executeQuery response.
     * @return              String with the response.
     * @throws SQLException If some error occur in the process.
     */
    private String getQueryResult(ResultSet resultSet) throws SQLException {
        StringBuilder queryResult = new StringBuilder();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            queryResult.append(resultSetMetaData.getColumnName(i));
            if (i < columnCount) {
                queryResult.append(", ");
            }
        }
        queryResult.append('\n');
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getString(i);
                if (value == null) {
                    queryResult.append("null");
                } else {
                    queryResult.append(value.trim());
                }
                if (i < columnCount) {
                    queryResult.append(", ");
                }
            }
            queryResult.append('\n');
        }
        resultSet.close();
        return queryResult.toString();
    }

}
