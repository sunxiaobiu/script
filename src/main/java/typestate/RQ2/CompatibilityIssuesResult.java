package typestate.RQ2;

import org.apache.commons.lang3.RegExUtils;
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

public class CompatibilityIssuesResult {

    public static void main(String[] args) throws IOException {
        List<String> resFileNames = new ArrayList<>();
        getFileList("/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/output", resFileNames);
        int totalNum = 0;

        HashMap<String, Integer> res = new HashMap<>();

        for(String fileName : resFileNames){
            File file = new File("/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/output/" + fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String APISig = fileContent.get(i);
                totalNum ++;
                IncrementHashMap.incrementValue(res, Regex.getSubUtilSimple(APISig, "(<.*:)"));
            }
        }

        System.out.println(totalNum);
        Map<String, Integer> sortedRes = IncrementHashMap.sortByValue(res);
        for(String apiSig : sortedRes.keySet()){
            System.out.println(apiSig + "----"+ sortedRes.get(apiSig));
        }

    }

    private static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
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


