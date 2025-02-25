package common;
import models.Country;
import with.SourceList;
import with.SourceName;

import java.io.*;
import java.util.ArrayList;

public class Files {

    public static void WriteToFile (String filePath, StringBuilder stringBuilder) {
        // Используем try-with-resources для автоматического закрытия FileWriter
        try (var writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            System.out.println("Произошла ошибка при записи в файл.");
            e.printStackTrace();
        }
    }

    public static ArrayList<String> ReadFromFile(String filePath){
        var rows = new ArrayList<String>();
        try (var reader = new BufferedReader(new FileReader(filePath))) {
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
