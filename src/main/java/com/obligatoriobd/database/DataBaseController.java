package com.obligatoriobd.database;

import java.sql.*;

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

    public static DataBaseController getDataBaseController(String dataBaseUrl, String dataBaseUser, String dataBasePasswd) {
        if (dbController == null) {
            dbController = new DataBaseController(dataBaseUrl, dataBaseUser, dataBasePasswd);
        }
        return dbController;
    }

    public static DataBaseController getDataBaseController() {
        return dbController;
    }

    public void insertEntity(IDataBaseEntity dataBaseEntity) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWD);
        PreparedStatement preparedStatement = dataBaseEntity.getInsertStatement(connection);
        preparedStatement.execute();
        connection.close();
    }

    /**
     * Envía una consulta tal cual se manda como parametro.
     *
     * @param query String con la query para enviar
     * @return Devuelve el resultado de la consulta en un String.
     * @throws Exception Si ocurre un error en la consulta.
     */
    public String sendSelectQuery(String query) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWD);
            Statement dbStatement = connection.createStatement();
            ResultSet resultSet = dbStatement.executeQuery(query);
            return getQueryResult(resultSet);
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    /**
     * Devuelve un String con el resultado de un select.
     *
     * @param resultSet Respuesta de la consula select.
     * @return String con el resultado de la consulta.
     * @throws Exception Si ocurre algun error durante el proceso de obtener el resultado.
     */
    public String getQueryResult(ResultSet resultSet) throws Exception {
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
                queryResult.append(value.trim());
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
