package JUnitTestGen.evosuite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculateTestNumber {

    public static void main(String[] args) throws IOException {
        String allFilesPath = args[0];
        List<String> fileList = getFileList(allFilesPath);

        System.out.println(fileList.size());
    }

    public static List<String> getFileList(String outputFilePath) throws IOException {
        List<String> txtFileNames = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith("_ESTest.java")) {
                txtFileNames.add(filename);
            }
        });

        return txtFileNames;
    }
}
