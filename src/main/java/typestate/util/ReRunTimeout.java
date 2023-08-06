package typestate.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReRunTimeout {

    public static void main(String[] args) throws IOException {
        File allAPKFile = new File("/Users/xsun0035/Desktop/IC3/TypeState_Goodware.txt");
        List<String> allAPKs = new ArrayList<>(Files.readAllLines(allAPKFile.toPath(), StandardCharsets.UTF_8));

        String resFile = "/Users/xsun0035/Desktop/IC3";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        Set<String> executedAPKs = new HashSet<>();

        for (String fileName : logFileNames) {
            File file = new File(resFile + "/" + fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String currentLine = fileContent.get(i);
                if(currentLine.endsWith(".apk")){
                    executedAPKs.add(currentLine);
                }
            }
        }

        allAPKs.removeAll(executedAPKs);

        for(String apkName : allAPKs){
            System.out.println(apkName);
        }


    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".out")) {
                txtFileNames.add(filename);
            }
        });
    }
}
