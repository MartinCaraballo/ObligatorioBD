package com.obligatoriobd.commands;

import com.obligatoriobd.database.DataBaseController;
import com.obligatoriobd.database.IDataBaseEntity;
import com.obligatoriobd.entities.*;
import com.obligatoriobd.entities.drivers.*;
import com.obligatoriobd.entities.constructors.*;
import com.obligatoriobd.utils.FileResolver;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

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

    @ShellMethod(key = "load-from-csv", value = "Insert into the indicated table of the database from a indicated csv. Params: tableName, pathToCsvFile")
    public String loadFromCsv(String tableName, String csvFilePath) {
        try {
            for (Class<? extends IDataBaseEntity> entityClass : ENTITY_CLASS_LIST) {
                try {
                    Field entityTableNameField = entityClass.getField("TABLE_NAME");
                    String entityTableName = (String) entityTableNameField.get(null);
                    if (entityTableName.trim().equalsIgnoreCase(tableName.trim())) {
                        List<String> errors = new LinkedList<>();
                        insertFromCsv(entityClass, FileResolver.readFile(csvFilePath), errors);
                        return getResultMessage(errors);
                    }
                } catch (Exception ignored) {}
            }
            throw new Exception("The entered table name does not exists.");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     *
     * @param target
     * @param fileLinesData
     * @param errors
     * @return
     */
    private void insertFromCsv(Class<? extends IDataBaseEntity> target, String[] fileLinesData, List<String> errors) {
        for (String line : fileLinesData) {
            try {
                String[] lineSplitted = line.split(",");
                Method targetMethod = target.getMethod("createFromCsv", String[].class);
                IDataBaseEntity targetObject = (IDataBaseEntity) targetMethod.invoke(null, (Object[]) lineSplitted);
                DataBaseController dbController = DataBaseController.getDataBaseController();
                if (dbController == null) {
                    throw new Exception("Data base not found or not connected.");
                }
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }
    }

    private String getResultMessage(List<String> errors) {
        StringBuilder result = new StringBuilder("Process finalized with (" + errors.size() + ") error(s).");
        if (errors.size() > 0) {
            result.append('\n').append("The errors are the following:\n\t");
            errors.forEach(error -> {
                result.append("* ").append(error).append('\n');
            });
        }
        return result.toString();
    }

}
