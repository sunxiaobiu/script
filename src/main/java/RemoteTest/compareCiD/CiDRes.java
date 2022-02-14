package RemoteTest.compareCiD;

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

public class CiDRes {

    public static void main(String[] args) throws IOException {
        String testCasesFilePath = "/Users/xsun0035/Desktop/CiD";
        Set<String> res = new HashSet<>();

//        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output21"));
//        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output22"));
//        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output23"));
//        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output24"));
//        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output25"));
        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output26"));
//        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output27"));
        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output28"));
        res.addAll(collectCompatibilityIssueAPIs(testCasesFilePath+"/output29"));

        for(String s : res){
            System.out.println(s);
        }
    }

    public static Set<String> collectCompatibilityIssueAPIs(String testCasesFilePath) throws IOException {
        List<String> classFileNames = new ArrayList<>();
        getFileList(testCasesFilePath, classFileNames);

        Set<String> res = new HashSet<>();

        for (String classFileName : classFileNames) {
            File file = new File(testCasesFilePath + "/" + classFileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).startsWith("==>Problematic_")) {
                    String api = Regex.getSubUtilSimple(fileContent.get(i), "(<.*>.*)");
                    int leftValue = Integer.valueOf(Regex.getSubUtilSimple(api, "(:\\[[0-9]+)").replace(":[", ""));
                    int rightValue = Integer.valueOf(Regex.getSubUtilSimple(api, "([0-9]+\\])").replace("]", ""));

                    boolean flag = true;
                    if(rightValue < 21){
                        flag = false;
                    }

//                    if(!(leftValue == 26 || leftValue == 28 || leftValue == 29 || leftValue == 30)){
//                        flag = false;
//                    }
//                    if(!(rightValue == 26 || rightValue == 28 || rightValue == 29 || rightValue == 30)){
//                        flag = false;
//                    }

                    if(isAndroidSystemAPI(api) && !api.contains("<init>") && flag && !isAndroidUIMethod(api)){
                        res.add(api);
                    }

                }
            }
        }

        return res;
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

    public static boolean isAndroidSystemAPI(String className) {
        return className.startsWith("<android.")
                || className.startsWith("<com.android.")
                || className.startsWith("<androidx.");
    }

    public static boolean isAndroidUIMethod(String unitString) {
        return unitString.startsWith("<android.widget")
                || unitString.startsWith("<android.view")
                || unitString.startsWith("<android.webkit")
                || unitString.startsWith("<android.content.res.Resources")
                || unitString.startsWith("<android.app.Dialog")
                || unitString.startsWith("<android.app.AlertDialog")
                ;
    }
}
