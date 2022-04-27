package ru.home.fileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileService {

    public String getArrayFromFile(String path) {
        ClassLoader classLoader = FileService.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        String data = readFromInputStream(inputStream);
        return data;
    }


    private String readFromInputStream(InputStream inputStream) {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStringBuilder.toString();
    }
}
