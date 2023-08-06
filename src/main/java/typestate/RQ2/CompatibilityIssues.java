package typestate.RQ2;

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
        String outputFileName = args[0];
        List<String> typeStateAPIs = FileUtil.getAllAPIListFromSourceFile("/home/xsun0035/rm46/xiaoyu/CompatibilityIssues_RQ2/AllAPI.txt");

        collectCompatibilityIssueAPIs(outputFileName, typeStateAPIs);
    }

    public static void collectCompatibilityIssueAPIs(String classFileName, List<String> typeStateAPIs) throws IOException {
        String outputFileName = "/home/xsun0035/rm46/xiaoyu/CompatibilityIssues_RQ2/output/" + classFileName;
        File file = new File("/home/xsun0035/rm46/xiaoyu/CID_RQ2/output" + "/" + classFileName);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).startsWith("==>Problematic_") && !fileContent.get(i).contains("[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,21,22,23,24,25,26,27,28,29,30]")) {
                String apiSig = Regex.getSubUtilSimple(fileContent.get(i), "(<.*>)");

                if (isAndroidTypeStateAPI(apiSig, typeStateAPIs)) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName, true)));
                    out.println(apiSig);
                    out.close();
                }
            }
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
