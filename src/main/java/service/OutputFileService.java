package service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OutputFileService {
    static void writeResultToFile(StringBuilder result, Path filename)  {
        try (BufferedWriter writer = Files.newBufferedWriter(filename)) {
            writer.write(String.valueOf(result));
            writer.flush();
        } catch (IOException e) {
            System.out.println("Внимание! Проверьте путь к файлу: файл для записи результата не найден");
        }
    }
}
