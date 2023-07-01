package com.obligatoriobd.commands;

import com.obligatoriobd.database.DataBaseController;
import com.obligatoriobd.utils.FileResolver;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;

@ShellComponent
public class DataBaseCommands {

    @ShellMethod(value = "Connect with the last credentials used.")
    public String connect() {
        try {
            String[] lastCredentials = FileResolver.readFile("./src/main/java/com/obligatoriobd/connection.env");
            if (lastCredentials[0].isEmpty()) {
                return "No saved credentials found.";
            }
            return connectNew(lastCredentials[0], lastCredentials[1], lastCredentials[2]);
        } catch (IOException fileReadError) {
            return "The last credentials used are not available.";
        }
    }

    @ShellMethod(value = "Params: user, passwd, url\n\t\tConnects to the database with the indicated credentials.")
    public String connectNew(@ShellOption String user, String passwd, String url) {
        try {
            Boolean connectionResult = DataBaseController.connect(url, user, passwd);
            if (connectionResult) {
                String[] toWrite = { user, passwd, url };
                FileResolver.writeFile("./src/main/java/com/obligatoriobd/connection.env", toWrite, false);
            }
            return (connectionResult) ? "Connection successful." : "Connection failed.";
        } catch (IllegalStateException connectState) {
            return connectState.getMessage();
        }
    }

    @ShellMethod(key = "disconnect", value = "Disconnect the actual connected database.")
    public String disconnect() {
        DataBaseController.disconnect();
        return (DataBaseController.getDataBaseController() == null) ? "Disconnected." : "Can't disconnect.";
    }

    @ShellMethod(key = "change", value = "Params: user, passwd, url\n\t\tChange the actual connection if the new one is reachable.")
    public String changeDataBase(String user, String passwd, String url) {
        try {
            DataBaseController.changeConnection(url, user, passwd);
            return "Connection changed successfully";
        } catch (IllegalAccessException connectionError) {
            return connectionError.getMessage();
        }
    }

    @ShellMethod(value = "Test the connection with the database.")
    public String testConnection() {
        DataBaseController dbController = DataBaseController.getDataBaseController();
        if (dbController == null) {
            return "Data base not found or not connected.";
        }
        boolean result = dbController.testConnection();
        return result ? "Connection OK" : "Connection FAIL, re-connect.";
    }
}
