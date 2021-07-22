package service;

import instance.Company;
import instance.Department;
import java.nio.file.Path;
import java.util.Map;

public class Service {

    public static void main(String[] args) {
        try {
            Company company = new Company();
            Path inputFilePath = Path.of(args[0]),
                    outputFilePath = Path.of(args[1]);
            Map<String, Department> companyMap = InputFileService.uploadEmployeeFromFile(inputFilePath, company);
            if (companyMap.size() != 0) {
                OutputResultService.outputCompanyMap(companyMap);
                OutputFileService.reloadFile(outputFilePath);
               Combinations.getAllPossibleSolutions(companyMap, outputFilePath);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Внимание! Укажите путь к обоим файлам во входных аргументах(args[0] и args[1])");
        }
    }
}
