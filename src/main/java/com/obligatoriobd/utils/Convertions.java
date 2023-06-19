package com.obligatoriobd.utils;

public class Convertions {

    /**
     * Returns a number from a string if is possible or returns null in case of wrong or empty data.
     * @param number            String with the number to cast.
     * @param dataLineNumber    line in the file of the value.
     * @return Number in the string or null if the value is wrong or empty.
     */
    public static Integer convertToInt(String number, int dataLineNumber) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Error parsing data in line " + dataLineNumber + ". Wrong or empty data.");
            return null;
        }
    }

    /**
     * Returns a number from a string if is possible or returns null in case of wrong or empty data.
     * @param number            String with the number to cast.
     * @param dataLineNumber    line in the file of the value.
     * @return Number in the string or null if the value is wrong or empty.
     */
    public static Double convertToDouble(String number, int dataLineNumber) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException numberFormatException) {
            System.err.println("Error parsing data in line " + dataLineNumber + ". Wrong or empty data.");
            return null;
        }
    }
}
