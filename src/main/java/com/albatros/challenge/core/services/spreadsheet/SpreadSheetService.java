package com.albatros.challenge.core.services.spreadsheet;

import java.util.List;

public interface SpreadSheetService {
    public List<List<String>> produceOutput(List<List<String>> parsedCsvList);

}
