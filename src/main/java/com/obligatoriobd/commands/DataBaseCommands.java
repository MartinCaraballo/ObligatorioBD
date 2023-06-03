package com.obligatoriobd.commands;

import com.obligatoriobd.database.DataBaseController;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.util.Map;

@ShellComponent
public class DataBaseCommands {

    @ShellMethod(key = "connect", value = "Params: user, passwd, url\n\t\tConnects to the database with the indicated credentials.")
    public String connect(String user, String passwd, String url) {
        try {
            Boolean connectionResult = DataBaseController.connect(url, user, passwd);
            if (connectionResult) {

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
}
