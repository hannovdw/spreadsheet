package com.albatros.challenge;

import com.albatros.challenge.core.services.file.FileService;
import com.albatros.challenge.core.services.spreadsheet.SpreadSheetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class MainClass implements CommandLineRunner {

    private final SpreadSheetService spreadSheetService;
    private final FileService fileService;

    public MainClass(SpreadSheetService spreadSheetService, FileService fileService) {
        this.spreadSheetService = spreadSheetService;
        this.fileService = fileService;
    }


    @Override
    public void run(String... args) throws IOException {

        System.out.println("========================================================================================");
        System.out.println("\n" +
                "███████╗██████╗ ██████╗ ███████╗ █████╗ ██████╗       ██╗████████╗\n" +
                "██╔════╝██╔══██╗██╔══██╗██╔════╝██╔══██╗██╔══██╗      ██║╚══██╔══╝\n" +
                "███████╗██████╔╝██████╔╝█████╗  ███████║██║  ██║█████╗██║   ██║   \n" +
                "╚════██║██╔═══╝ ██╔══██╗██╔══╝  ██╔══██║██║  ██║╚════╝██║   ██║   \n" +
                "███████║██║     ██║  ██║███████╗██║  ██║██████╔╝      ██║   ██║   \n" +
                "╚══════╝╚═╝     ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝       ╚═╝   ╚═╝   \n");
        System.out.println("Please put your csv files in the project resource folder");
        System.out.println("Output will be in the" + "/src/main/java/com/albatros/challenge/core/services/file/output/ Folder");

        System.out.println("========================================================================================");

        System.out.println("Select input file name : ");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String inputName = reader.readLine();

        System.out.println("Select output file name : ");
        String outputName = reader.readLine();

        List<List<String>> parsedCsvList = fileService.parseCsvToArray(inputName);
        List<List<String>> processedSpreadSheet = spreadSheetService.produceOutput(parsedCsvList);
        fileService.print2DListToFile(processedSpreadSheet, outputName);

    }
}
