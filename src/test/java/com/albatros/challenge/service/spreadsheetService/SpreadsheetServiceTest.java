package com.albatros.challenge.service.spreadsheetService;

import com.albatros.challenge.core.services.file.FileService;
import com.albatros.challenge.core.services.file.FileServiceImpl;
import com.albatros.challenge.core.services.spreadsheet.SpreadSheetService;
import com.albatros.challenge.core.services.spreadsheet.SpreadSheetServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpreadsheetServiceTest {

    SpreadSheetService spreadSheetService;
    FileService fileService;

    @BeforeEach
     void setup(){
        spreadSheetService = new SpreadSheetServiceImpl();
        fileService = new FileServiceImpl();
    }

    String readOutputToString(String fileName) throws IOException {
        String projectDir = System.getProperty("user.dir"); // Get the project directory
        String filePath = projectDir + "/src/main/java/com/albatros/challenge/core/services/file/output/" + fileName + ".txt";
        FileInputStream fis = new FileInputStream(filePath);
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
            sb.append(new String(buffer));
            buffer = new byte[10];
        }
        fis.close();

        return sb.toString();
    }

    String readExpectedOutputToString(String fileName) throws IOException {
        String projectDir = System.getProperty("user.dir"); // Get the project directory
        String filePath = projectDir + "/src/test/java/com/albatros/challenge/service/spreadsheetService/testOutputExamples/" + fileName + ".txt";
        FileInputStream fis = new FileInputStream(filePath);
        byte[] buffer = new byte[10];
        StringBuilder sb = new StringBuilder();
        while (fis.read(buffer) != -1) {
            sb.append(new String(buffer));
            buffer = new byte[10];
        }
        fis.close();

        return sb.toString();
    }

    @Test
    public void TestProduceOutputExample1() throws IOException {
        List<List<String>> listFromFile = fileService.parseCsvToArray("test1");
        List<List<String>> processedList = spreadSheetService.produceOutput(listFromFile);
        fileService.print2DListToFile(processedList, "test1Output");
        String outputFileAsString = readOutputToString("test1Output");
        String expectedFileAsString = readExpectedOutputToString("test1Expected");
        assertEquals(outputFileAsString, expectedFileAsString);
    }

    @Test
    public void TestProduceOutputExample2() throws IOException {
        List<List<String>> listFromFile = fileService.parseCsvToArray("test2");
        List<List<String>> processedList = spreadSheetService.produceOutput(listFromFile);
        fileService.print2DListToFile(processedList, "test2Output");
        String outputFileAsString = readOutputToString("test2Output");
        String expectedFileAsString = readExpectedOutputToString("test2Expected");
        assertEquals(outputFileAsString, expectedFileAsString);
    }

    @Test
    public void TestProduceOutputExample3() throws IOException {
        List<List<String>> listFromFile = fileService.parseCsvToArray("test3");
        List<List<String>> processedList = spreadSheetService.produceOutput(listFromFile);
        fileService.print2DListToFile(processedList, "test3Output");
        String outputFileAsString = readOutputToString("test3Output");
        String expectedFileAsString = readExpectedOutputToString("test3Expected");
        assertEquals(outputFileAsString, expectedFileAsString);
    }

    @Test
    public void TestProduceOutputEmpty() throws IOException {
        List<List<String>> listFromFile = fileService.parseCsvToArray("testEmpty");
        List<List<String>> processedList = spreadSheetService.produceOutput(listFromFile);
        fileService.print2DListToFile(processedList, "testEmptyOutput");
        String outputFileAsString = readOutputToString("testEmptyOutput");
        String expectedFileAsString = readExpectedOutputToString("testEmptyExpected");
        assertEquals(outputFileAsString, expectedFileAsString);
    }

    @Test
    public void TestProduceOutputAllStrings() throws IOException {
        List<List<String>> listFromFile = fileService.parseCsvToArray("testAllString");
        List<List<String>> processedList = spreadSheetService.produceOutput(listFromFile);
        fileService.print2DListToFile(processedList, "testAllStringOutput");
        String outputFileAsString = readOutputToString("testAllStringOutput");
        String expectedFileAsString = readExpectedOutputToString("testAllStringExpected");
        assertEquals(outputFileAsString, expectedFileAsString);
    }


}
