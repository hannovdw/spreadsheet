package com.albatros.challenge.core.services.file;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FileServiceImpl implements FileService {

    @Override
    public List<List<String>> parseCsvToArray(String fileName) {
        String path = fileName + ".csv";
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(path).getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                ArrayList<String> row = new ArrayList<>(Arrays.asList(values));
                records.add(row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records;
    }

    public void print2DListToFile(List<List<String>> data, String fileName) throws IOException {
        String projectDir = System.getProperty("user.dir"); // Get the project directory
        String filePath = projectDir + "/src/main/java/com/albatros/challenge/core/services/file/output/" + fileName + ".txt";

        Path outputPath = Paths.get(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
            for (List<String> row : data) {
                int numColumns = row.size();
                int columnIndex = 0;

                for (String cell : row) {
                    writer.write(cell);
                    if (columnIndex < numColumns - 1) {
                        writer.write(" | ");
                    }
                    columnIndex++;
                }

                writer.newLine();
            }
        }
    }

}
