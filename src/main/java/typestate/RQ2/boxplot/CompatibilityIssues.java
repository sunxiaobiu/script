package typestate.RQ2.boxplot;

import org.apache.commons.lang3.StringUtils;
import util.FileUtil;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompatibilityIssues {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);
        List<String> typeStateAPIs = FileUtil.getAllAPIListFromSourceFile("/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/boxplot/AllAPI_Semantic.txt");

        for(String outputFileName : logFileNames){
            collectCompatibilityIssueAPIs(outputFileName, typeStateAPIs);
        }
    }

    public static void collectCompatibilityIssueAPIs(String classFileName, List<String> typeStateAPIs) throws IOException {
        String outputFileName = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/boxplot/semantic_number.txt";
        File file = new File("/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/output" + "/" + classFileName);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        int number = 0;
        for (int i = 0; i < fileContent.size(); i++) {
            if (isAndroidTypeStateAPI(fileContent.get(i), typeStateAPIs)) {
                number++;
            }
        }

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName, true)));
        out.println(number);
        out.close();
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

    public static boolean isAndroidTypeStateAPI(String apiSig, List<String> typeStateAPIs) {
        for (String typestateAPI : typeStateAPIs) {
            String thisClassName = Regex.getSubUtilSimple(apiSig, "(<.*?:)").replace("<", "").replace(":", "");
            String thisMethodName = Regex.getSubUtilSimple(apiSig, "([a-zA-Z<>]+\\()").replace("(", "");

            String thatClassName = Regex.getSubUtilSimple(typestateAPI, "(<.*?:)").replace("<", "").replace(":", "");
            String thatMethodName = Regex.getSubUtilSimple(typestateAPI, "([a-zA-Z<>]+\\()").replace("(", "");

            if (StringUtils.equals(thisClassName, thatClassName) && StringUtils.equals(thisMethodName, thatMethodName)) {
                return true;
            }
        }
        return false;
    }


}
