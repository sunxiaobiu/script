package TriggerScope;

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

public class CollectFileName {

    public static void main(String[] args) throws IOException {
        String executedFilePath = args[0];
        String allFilePath = args[1];
        String outputFile = args[2];

        File outputFilePath = new File(outputFile);
        List<String> executedFileName = new ArrayList<>();
        List<String> allFileName = new ArrayList<>();

        List<String> logFileNames = new ArrayList<>();
        getFileList(executedFilePath, logFileNames);
        for (String txtFileName : logFileNames) {
            executedFileName.add(txtFileName.replace("txt", "apk"));
        }

        File file = new File(allFilePath);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < fileContent.size(); i++) {
            allFileName.add(fileContent.get(i).replace("/home/xsun0035/rm46_scratch/goodwareAPK/", ""));
        }

        allFileName.removeAll(executedFileName);
        List<String> resList = new ArrayList<>();
        for(String res: allFileName){
            resList.add("/home/xsun0035/rm46_scratch/goodwareAPK/"+res);
        }

        Files.write(outputFilePath.toPath(), resList, StandardCharsets.UTF_8);
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
