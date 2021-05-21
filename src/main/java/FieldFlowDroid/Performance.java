package FieldFlowDroid;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Performance {
    public static void main(String[] args) throws IOException {
        String testCasesFilePath = args[0];

        List<String> logFileNames = new ArrayList<>();

        getFileList(testCasesFilePath, logFileNames);

        for (String txtFileName : logFileNames) {

            long startTime = 0;
            long endTime = 0;
            File file = new File(testCasesFilePath + txtFileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {
                String line = fileContent.get(i);
                if (line.startsWith("==>START TIME:")) {
                    startTime = Long.parseLong(line.replaceAll("==>START TIME:", ""));
                }

                if (line.startsWith("==>FINAL TIME:")) {
                    endTime = Long.parseLong(line.replaceAll("==>FINAL TIME:", ""));
                }
            }

            if(endTime == 0){
                System.out.println(txtFileName);
            }

            if(startTime != 0 && endTime != 0 && ((endTime - startTime)/1000) !=0){
                //System.out.println((endTime - startTime)/1000);
                //System.out.println(txtFileName);
            }
        }
    }


    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });
    }
}
