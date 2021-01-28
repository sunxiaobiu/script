package AutoUnitTest.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Methodology {

    public static void main(String[] args) throws IOException {
        String csvFile = args[0];
        String txtFile = args[1];

        BufferedReader bufferReader = null;
        String csvLine = "";
        HashMap<String, String> csvMap = new HashMap<>();

        bufferReader = new BufferedReader(new FileReader(csvFile));
        while ((csvLine = bufferReader.readLine()) != null) {
            List<String> line = Arrays.asList(csvLine.split(",", 2));
            csvMap.put(line.get(0).replaceAll("\"", ""), line.get(1).replaceAll("\"", ""));
        }

        File file = new File(txtFile);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        for(String testCase : fileContent){
            System.out.println(csvMap.get(testCase));
        }


    }
}
