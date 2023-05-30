package com.obligatoriobd.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IDataBaseEntity {

    /**
     * Método para que el controlador de la base de datos obtenga la consulta sql con los datos del objeto que se quiere persistir.
     * @param dataBaseConnection Conexión a la base de datos para poder crear el preparedStatement.
     * @return                   PreparedStatement listo para que el controlador pueda ejecutar la consulta.
     * @throws SQLException      Si ocurre algún error durante la consulta.
     */
    PreparedStatement getInsertStatement(Connection dataBaseConnection) throws SQLException;
}
