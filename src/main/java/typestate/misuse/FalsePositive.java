package typestate.misuse;

import util.Regex;

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

public class FalsePositive {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/TypeState_goodware_webrtc/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        Set<String> misuseAPIs = new HashSet<>();

        for (String fileName : logFileNames) {
            File file = new File(resFile + "/" + fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String currentLine = fileContent.get(i);
                String misuse = Regex.getLastSubUtilSimple(currentLine, "(======Misuse=======.*)");
                misuseAPIs.add(misuse);
            }
        }
        for (String s : misuseAPIs) {
            System.out.println(s);
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
