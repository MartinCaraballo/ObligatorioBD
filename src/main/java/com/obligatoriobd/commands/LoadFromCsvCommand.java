package com.obligatoriobd.commands;

import com.obligatoriobd.database.DataBaseController;
import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.entities.*;
import com.obligatoriobd.entities.drivers.*;
import com.obligatoriobd.entities.constructors.*;
import com.obligatoriobd.utils.FileResolver;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@ShellComponent
public class LoadFromCsvCommand {

    private final List<Class<? extends IDataBaseEntity>> ENTITY_CLASS_LIST = List.of(
            Status.class,
            SprintResult.class,
            Season.class,
            PitStop.class,
            LapTime.class,
            Circuit.class,
            Driver.class,
            DriverStanding.class,
            Constructor.class,
            ConstructorResult.class,
            ConstructorStanding.class
    );

    @ShellMethod(key = "load-from-csv", value = "Params: tableName, pathToCsvFile\n\t\tInsert into the indicated table of the database from a indicated csv.")
    public String loadFromCsv(String tableName, String csvFilePath) {
        try {
            for (Class<? extends IDataBaseEntity> entityClass : ENTITY_CLASS_LIST) {
                try {
                    Field entityTableNameField = entityClass.getField("TABLE_NAME");
                    String entityTableName = (String) entityTableNameField.get(null);
                    if (entityTableName.trim().equalsIgnoreCase(tableName.trim())) {
                        List<String> errors = new LinkedList<>();
                        insertFromCsv(entityClass, Objects.requireNonNull(FileResolver.readFile(csvFilePath)), errors);
                        return getResultMessage(errors);
                    }
                } catch (IOException fileReadingError) {
                    return "Error reading the file in the indicated path.";
                } catch (NoSuchFieldException | SecurityException | IllegalAccessException |
                         NullPointerException ignored) {
                    // Exceptions ignored because the failed search throws it, and we need keep searching through all list.
                }
            }
            throw new Exception("The entered table name does not exists.");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * @param target        Class entity what refers to the table in the database.
     * @param fileLinesData String[] with the lines of the csv file.
     * @param errors        List of Strings to save the errors in case they occur.
     */
    private void insertFromCsv(Class<? extends IDataBaseEntity> target, String[] fileLinesData, List<String> errors) {
        for (int i = 1; i < fileLinesData.length; i++) {
            try {
                String[] lineSplitted = fileLinesData[i].split(",");
                Method targetMethod = target.getMethod("createFromCsv", String[].class);
                IDataBaseEntity targetObject = (IDataBaseEntity) targetMethod.invoke(null, (Object) lineSplitted);
                DataBaseController dbController = DataBaseController.getDataBaseController();
                if (dbController == null) {
                    throw new NullPointerException("Data base not found or not connected.");
                }
                dbController.insertEntity(targetObject);
            } catch (NullPointerException dataBaseNotFoundOrNotConnected) {
                errors.add(dataBaseNotFoundOrNotConnected.getMessage());
                return;
            } catch (NoSuchMethodException | SQLException | InvocationTargetException |
                     IllegalAccessException methodException) {
                errors.add(methodException.getMessage());
            }
        }
    }

    /**
     * Method to return a beauty string with information about the process of the command.
     *
     * @param errors List of string with the errors.
     * @return String with the last information to show in the console.
     */
    private String getResultMessage(List<String> errors) {
        StringBuilder result = new StringBuilder("Load finalized with (" + errors.size() + ") error(s).");
        if (errors.size() > 0) {
            result.append('\n').append("The errors are the following:\n\t");
            errors.forEach(error -> {
                result.append("* ").append(error).append('\n');
            });
        }
        return result.toString();
    }

}
