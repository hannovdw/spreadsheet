package com.albatros.challenge.core.services.spreadsheet;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpreadSheetServiceImpl implements SpreadSheetService{

    public List<List<String>> produceOutput(List<List<String>> parsedCsvList){

        int rowCounter = 0;
        int sizeOfWidestCell = 0;
        int sizeOfLongestRow = 0;

        for (List<String> row : parsedCsvList){
            if (row.size()>sizeOfLongestRow) sizeOfLongestRow = row.size();
            int columnCounter = 0;
            for (String item : row){
                String processedCellValue;
                if(containsSumSignature(item)){
                    List<String> positionValues = parseAlphaNumericValues(item);
                    List<Double> values = getSpreadSheetValues(positionValues, parsedCsvList);
                    processedCellValue = String.valueOf(sumValues(values));

                }
                else if(containsProdSignature(item)){
                    List<String> positionValues = parseAlphaNumericValues(item);
                    List<Double> values = getSpreadSheetValues(positionValues, parsedCsvList);
                    processedCellValue = String.valueOf(productValues(values));
                }else {
                    processedCellValue = item;
                }

                if(processedCellValue.length()>sizeOfWidestCell) sizeOfWidestCell = processedCellValue.length();

                parsedCsvList.get(rowCounter).set(columnCounter, processedCellValue);
                columnCounter++;
            }
            rowCounter++;

        }

        return formatSpreadSheet(parsedCsvList, sizeOfWidestCell, sizeOfLongestRow);

    }

    private List<List<String>> formatSpreadSheet(List<List<String>> parsedCsvList, Integer sizeOfWidestCell, Integer sizeOfLongestRow) {
        String line = createLine(sizeOfWidestCell);
        String emptyColumn = createEmptyCell(sizeOfWidestCell);

        int rowCounter = 0;
        for (List<String> row : parsedCsvList){
            int columnCounter = 0;
            int sizeOfRow = row.size();
            for (int i =0; i<sizeOfLongestRow; i++){

                if (i < sizeOfRow) {
                    String item = row.get(columnCounter);

                    if (containsLineCell(item)) {
                        parsedCsvList.get(rowCounter).set(columnCounter, line);
                    } else {
                        try {
                            double value = Double.parseDouble(item);
                            StringBuilder itemBuilder = new StringBuilder();
                            for (int j = item.length(); j < sizeOfWidestCell; j++) {
                                itemBuilder.append(" ");
                            }
                            itemBuilder.append(item);
                            item = itemBuilder.toString();
                            parsedCsvList.get(rowCounter).set(columnCounter, item);

                        } catch (NumberFormatException e) {

                            StringBuilder itemBuilder = new StringBuilder(item);
                            for (int j = item.length(); j < sizeOfWidestCell; j++) {
                                itemBuilder.append(" ");
                            }
                            item = itemBuilder.toString();
                            parsedCsvList.get(rowCounter).set(columnCounter, item);

                        }
                    }
                }else {
                    parsedCsvList.get(rowCounter).add(emptyColumn);
                }
                columnCounter++;
            }
            rowCounter++;
        }
        return parsedCsvList;
    }

    private String createEmptyCell(int length) {
        StringBuilder emptyCell = new StringBuilder();
        for (int i =0; i<length; i++){
            emptyCell.append(" ");
        }
        return emptyCell.toString();
    }

    private boolean containsLineCell(String item) {
        return item.equals("#hl");
    }

    private String createLine(Integer length){
        StringBuilder line = new StringBuilder();
        for (int i =0; i<length; i++){
            line.append("-");
        }
        return line.toString();
    }

    private List<Double> getSpreadSheetValues(List<String> positionValues, List<List<String>> parsedCsvList) {

        List<Double> valueList = new ArrayList<>();

        for (String position : positionValues){
            char letter = position.charAt(0);
            int letterValue = Character.toUpperCase(letter) - 'A';
            int digit = Character.getNumericValue(position.charAt(1)) -1;
            try {
                Double valueAtPosition = Double.valueOf(parsedCsvList.get(digit).get(letterValue));
                valueList.add(valueAtPosition);
            }catch (NumberFormatException e){
                String value = parsedCsvList.get(digit).get(letterValue);
                if(containsSumSignature(value)){
                    List<String> positionValuesRecur = parseAlphaNumericValues(value);
                    List<Double> values = getSpreadSheetValues(positionValuesRecur, parsedCsvList);
                    valueList.add(sumValues(values));

                }
                else if(containsProdSignature(value)){
                    List<String> positionValuesRecur = parseAlphaNumericValues(value);
                    List<Double> values = getSpreadSheetValues(positionValuesRecur, parsedCsvList);
                    valueList.add(productValues(values));
                }
            }
        }

        return valueList;

    }

    public Double productValues(List<Double> values){
        Double sum = 1.0;
        for(Double value : values){
            sum *= value;
        }
        return sum;
    }

    public Double sumValues(List<Double> values){
        Double sum = 0.0;

        for(Double value : values){
            sum += value;
        }
        return sum;
    }

    public static List<String> parseAlphaNumericValues(String input) {

        List<String> values = new ArrayList<>();

        input = input.substring(2, input.length() - 1);

        int spaceIndex = input.indexOf(' ');
        String operation = input.substring(0, spaceIndex);

        String valueString = input.substring(spaceIndex + 1);

        String[] tokens = valueString.split(" ");

        for (String token : tokens) {
            if (!token.equalsIgnoreCase(operation)) {
                values.add(token);
            }
        }

        return values;
    }

    private boolean containsSumSignature(String item){
        return item.matches("^#\\(sum((?:\\s*\\w+\\s*)+)?\\)$");
    }

    private boolean containsProdSignature(String item){
        return item.matches("^#\\(prod((?:\\s*\\w+\\s*)+)?\\)$");
    }

}
