package typestate.icse.RQ3;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AsyncChecker {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ3_Comparison/AsyncChecker/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);


        int number = 0;
        for (String fileName : logFileNames) {
            //Set<String> existMisuse = new HashSet<>();
            number++;
            File theFile = new File(fileName);
            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            while (it.hasNext()) {
                String currentLine = it.nextLine();
                System.out.println(currentLine);
            }
        }

        System.out.println(number);
    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt") && !filename.contains("log.txt")) {
                txtFileNames.add(filename);
            }
        });
    }
}
