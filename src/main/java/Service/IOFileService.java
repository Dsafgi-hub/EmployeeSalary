package Service;

import Instance.Company;
import Instance.Department;
import Instance.Employee;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class IOFileService {

    public static Map<String, Department> uploadEmployeeFromFile(Path inputFilePath, Company company) {
        Map<String, Department> companyMap = company.getCompanyMap();
        try (BufferedReader reader = Files.newBufferedReader(inputFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (checkValidData(line)) {
                    String[] params = line.split(";");
                    companyMap.putIfAbsent(params[1], new Department(params[1]));
                    companyMap.get(params[1]).getEmployees().add(new Employee(params[0], new BigDecimal(params[2])));
                }
            }
        } catch (IOException e) {
            System.out.println("Внимание! Проверьте путь к файлу: исходный файл не найден");
        }
        return companyMap;
    }

    static boolean checkValidData (String line) {
        boolean flag = true;
        String[] fields = line.split(";");
        String commonPart = "Внимание! Проверьте строку " + line.trim() + ".";
        try {
            if (fields.length != 3) {
                throw new InputDataException( commonPart + " В ней должно быть ровно 3 поля");
            }

            if (fields[0].trim().equals("")
                    || fields[1].trim().equals("")
                    || fields[2].trim().equals("")) {
                throw new InputDataException( commonPart + " Не должно содержаться пустых полей");
            }

            String name = fields[0],
                    department = fields[1],
                    antiPattern = ".*[!@#$%^&*()?/|\";:{}'~<>,.+=_\\[\\]]+.*";
            if (!name.matches("\\D+")
                || name.matches(antiPattern)
                || !department.matches("\\D+")
                || department.matches(antiPattern)) {
                throw new InputDataException(commonPart + " В полях ФИО и отдел должны содержаться только буквы и тире");
            }

            String salary = fields[2];
            if (!salary.matches("\\d+[.,]?\\d{0,2}")) {
                throw new InputDataException(commonPart + " Зарплата должна быть положительным числом с точностью до сотых");
            }
        } catch (InputDataException e) {
            flag = false;
            System.out.println(e.getMessage());
        }
        return flag;
    }

    static void writeResultToFile(StringBuilder result, Path filename)  {
        try (BufferedWriter writer = Files.newBufferedWriter(filename)) {
            writer.write(String.valueOf(result));
            writer.flush();
        } catch (IOException e) {
            System.out.println("Внимание! Проверьте путь к файлу: файл для записи результата не найден");
        }
    }
}

class InputDataException extends Exception {
    InputDataException(String message) {
        super(message);
    }
}