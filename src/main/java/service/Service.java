package service;

import instance.Department;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class Service {

    public static void main(String[] args) {
        if (args.length > 1) {
            Path inputFilePath = Path.of(args[0]),
                    outputFilePath = Path.of(args[1]);
            Map<String, Department> companyMap = InputFileService.uploadEmployeeFromFile(inputFilePath);
            if (companyMap.size() != 0) {
                OutputResultService.outputCompanyMap(companyMap);
                if (Files.exists(outputFilePath)) {
                    try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.TRUNCATE_EXISTING)) {
                        Combinations.getAllPossibleSolutions(companyMap, writer);
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("Внимание! Проверьте путь к файлу: файл для записи результата не найден");
                    }
                }

            }
        } else {
            System.out.println("Внимание! Укажите путь к обоим файлам во входных аргументах(args[0] и args[1])");
        }
    }
}
