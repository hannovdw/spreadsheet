package com.albatros.challenge.core.services.file;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public List<List<String>> parseCsvToArray(String input);
    public void print2DListToFile(List<List<String>> data, String fileName) throws IOException;

}
