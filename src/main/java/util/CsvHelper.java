package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvHelper {

    public static List<String> readLinesFromCSVFile(String path) throws IOException, IOException {
        Path filePath = Paths.get(path);
        List<String> results = new ArrayList<>();

        try(BufferedReader buffer = Files.newBufferedReader(filePath, Charset.defaultCharset())){
            String line = "";
            while((line = buffer.readLine()) != null){
                results.add(line);
            }

        }
        return results;
    }
}