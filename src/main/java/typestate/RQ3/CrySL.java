package typestate.RQ3;

import org.apache.commons.lang3.StringUtils;
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

public class CrySL {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ3/CrySL_RQ3/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);
        int totalNum = 0;
        Set<String> cryCLAPIs = new HashSet<>();

        for(String fileName : logFileNames) {
            Set<String> existMisuse = new HashSet<>();
            File file = new File(resFile + "/" + fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String currentLine = fileContent.get(i);
                if(
//                        !existMisuse.contains(currentLine) &&
                                currentLine.contains("[xiaoyu-Errors]")
                           &&     currentLine.contains("Unexpected call to method")

                        && StringUtils.isNotBlank(Regex.getSubUtilSimple(currentLine, "(<.*?>)"))
                        //&& currentLine.contains("Cipher")

                ){
                    cryCLAPIs.add(Regex.getSubUtilSimple(currentLine, "(<.*?>)"));
                    System.out.println(currentLine);
                    existMisuse.add(currentLine);

                }
            }

            totalNum += existMisuse.size();
        }

        System.out.println(totalNum);
//        for(String s : cryCLAPIs){
//            System.out.println(s);
//        }

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
