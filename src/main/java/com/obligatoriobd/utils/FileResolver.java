package com.obligatoriobd.utils;

import java.io.*;
import java.util.ArrayList;

public class FileResolver {
    public static String[] readFile(String nombreCompletoArchivo) throws IOException {
        FileReader fr;
        ArrayList<String> listaLineasArchivo = new ArrayList<>();
        fr = new FileReader(nombreCompletoArchivo);
        BufferedReader br = new BufferedReader(fr);
        String lineaActual = br.readLine();
        while (lineaActual != null) {
            listaLineasArchivo.add(lineaActual);
            lineaActual = br.readLine();
        }
        return listaLineasArchivo.toArray(new String[0]);
    }

    public static void writeFile(String nombreCompletoArchivo, String[] listaLineasArchivo) {
        FileWriter fw;
        try {
            fw = new FileWriter(nombreCompletoArchivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < listaLineasArchivo.length; i++) {
                String lineaActual = listaLineasArchivo[i];
                bw.write(lineaActual);
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo " + nombreCompletoArchivo);
        }
    }
}
