package common;
import models.Country;
import with.SourceList;
import with.SourceName;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Files {

    public static void WriteToFile (String filePath, StringBuilder stringBuilder) {
        // Используем try-with-resources для автоматического закрытия FileWriter
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {
             writer.write(stringBuilder.toString());
        } catch (IOException e) {
            System.out.println("Произошла ошибка при записи в файл.");
            e.printStackTrace();
        }
    }

    public static ArrayList<String> ReadFromFile(String filePath){
        var rows = new ArrayList<String>();
        try (var fis = new FileInputStream(filePath);
             var isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             var reader = new BufferedReader(isr)) {
             String line;
             while ((line = reader.readLine()) != null) { // Чтение построчно
                rows.add(line);
             }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return rows;
    }






}
