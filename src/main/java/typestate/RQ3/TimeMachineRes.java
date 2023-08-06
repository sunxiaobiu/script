package typestate.RQ3;

import org.apache.commons.lang3.StringUtils;
import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeMachineRes {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ3/TimeMachine/appTest/timemachine-results";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);
        HashMap<String, Integer> exceptionTypes = new HashMap<>();

        for(String fileName : logFileNames) {
            File file = new File(fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String currentLine = fileContent.get(i);
                if(currentLine.startsWith("--------- beginning of")){
                    String exceptionContent = "";
                    for(int line = i+1; line < fileContent.size(); line++){
                        String followingLine = fileContent.get(line);
                        if(followingLine.startsWith("--------- beginning of") && StringUtils.isNotBlank(exceptionContent)){
                            IncrementHashMap.incrementValue(exceptionTypes, exceptionContent);
                            break;
                        }
                        if(followingLine.contains("Caused by:") && !followingLine.contains("no longer supported")){
                            exceptionContent = Regex.getSubUtilSimple(followingLine, "(Caused by:.*Exception)").replace("Caused by: ", "");
                        }
                    }
                }
            }
        }

        Map<String, Integer> sortedExceptionTypes = IncrementHashMap.sortByValue(exceptionTypes);
        for(String exceptionTypeStr : sortedExceptionTypes.keySet()){
            System.out.println(exceptionTypeStr + "----"+ sortedExceptionTypes.get(exceptionTypeStr));
        }
    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith("crashes.log")) {
                txtFileNames.add(filename);
            }
        });
    }
}
