package HSO;

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

public class GooglePlay {

    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath, txtFileNames);

        for(String txtFile : txtFileNames) {
            File file = new File(outputFilePath + txtFile);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            String zeroLine = fileContent.get(0);
            if(!zeroLine.contains("Error: App not found")){
                System.out.println(txtFile);
            }
        }
    }

    public static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });
    }
}
