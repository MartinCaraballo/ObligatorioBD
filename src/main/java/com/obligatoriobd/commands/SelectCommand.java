package com.obligatoriobd.commands;

import com.obligatoriobd.database.DataBaseController;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.xml.crypto.Data;
import java.sql.SQLException;

@ShellComponent
public class SelectCommand {

    String queryQuestion1 = """
SELECT forename, surname, COUNT(*) AS appearances
FROM (
    SELECT R.year, D.forename, D.surname, DS.points,
           ROW_NUMBER() OVER (PARTITION BY R.year ORDER BY DS.points DESC) AS row_num
    FROM Driver_Standings DS
    INNER JOIN Races R ON DS.race_id = R.race_id
    INNER JOIN Drivers D ON DS.driver_id = D.driver_id
    WHERE DS.points != 0
) AS subquery
WHERE row_num = 1
GROUP BY forename, surname
ORDER BY appearances DESC
LIMIT 2;""";

    String queryQuestion2 = """
SELECT name, COUNT(*) AS appearances
FROM (
    SELECT R.year, C.name, CS.points,
           ROW_NUMBER() OVER (PARTITION BY R.year ORDER BY CS.points DESC) AS row_num
    FROM Constructor_Standings CS
    INNER JOIN Races R ON CS.race_id = R.race_id
    INNER JOIN Constructors C ON CS.constructor_id = C.constructor_id
    WHERE CS.points != 0
) AS subquery
WHERE row_num = 1
GROUP BY name
ORDER BY appearances DESC
LIMIT 1;""";

    String queryQuestion3 = """
SELECT forename, surname, COUNT(*) AS wins
FROM Results R
    INNER JOIN Drivers D on R.driver_id = D.driver_id
    WHERE position = 1
    GROUP BY forename, surname
    ORDER BY COUNT(*) DESC
    LIMIT 1""";

    String queryQuestion4 = """
SELECT year, name FROM Races
    WHERE year BETWEEN 1996 AND 1999;""";

    String queryQuestion5 = """
SELECT forename, surname, RA.year FROM Results R
    INNER JOIN Drivers D ON R.driver_id = D.driver_id
    INNER JOIN Races RA ON R.race_id = RA.race_id
    INNER JOIN Circuits C on RA.circuit_id = C.circuit_id
    WHERE C.name LIKE "%Suzuka%" AND RA.year = 2007 AND R.position = 1;""";

    String queryQuestion6 = """
SELECT forename, surname, COUNT(*) FROM Driver_Standings DS
    INNER JOIN Drivers D on DS.driver_id = D.driver_id
    INNER JOIN Results R on DS.race_id = R.race_id AND DS.driver_id = R.driver_id
    WHERE forename = "Jacques" AND surname = "Villeneuve" AND R.position = 1;""";

    String queryQuestion7 = """
SELECT D.forename, D.surname, COUNT(*) AS total_wins FROM Results R
    INNER JOIN Drivers D ON R.driver_id = D.driver_id
    WHERE R.position = 1 AND R.grid > 1
        GROUP BY D.forename, D.surname
        ORDER BY total_wins DESC
        LIMIT 1;""";

    String queryQuestion8 = """
SELECT D.forename, D.surname, COUNT(*) AS total_wins FROM Results R
    INNER JOIN Drivers D ON R.driver_id = D.driver_id
    WHERE R.position = 1 AND R.grid = 1
        GROUP BY D.forename, D.surname
        ORDER BY total_wins DESC
        LIMIT 1;""";

    @ShellMethod(value = "Get answer of the question with the number indicated.")
    public String getAnswer(int number) {
        try {
            DataBaseController dbController = DataBaseController.getDataBaseController();
            if (dbController == null) {
                return "Data base not found or not connected.";
            }
            return switch (number) {
                case 1 -> dbController.sendSelectQuery(queryQuestion1);
                case 2 -> dbController.sendSelectQuery(queryQuestion2);
                case 3 -> dbController.sendSelectQuery(queryQuestion3);
                case 4 -> dbController.sendSelectQuery(queryQuestion4);
                case 5 -> dbController.sendSelectQuery(queryQuestion5);
                case 6 -> dbController.sendSelectQuery(queryQuestion6);
                case 7 -> dbController.sendSelectQuery(queryQuestion7);
                case 8 -> dbController.sendSelectQuery(queryQuestion8);
                default -> "Not a valid option.";
            };
        } catch (SQLException sqlException) {
            return sqlException.getMessage();
        }
    }

    @ShellMethod (value = "Sends a select query.")
    public String sendSelectQuery(String query) {
        try {
            DataBaseController dbController = DataBaseController.getDataBaseController();
            if (dbController == null) {
                return "Data base not found or not connected.";
            }
            return dbController.sendSelectQuery(query.replace("\"", ""));
        } catch (SQLException sqlException) {
            return sqlException.getMessage();
        }
    }
}
