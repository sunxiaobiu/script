package JUnitTestGen.evosuite.Result;

import org.apache.commons.lang3.StringUtils;
import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RQ3 {

    public static void main(String[] args) throws IOException {
        String logFolder = "/Users/xsun0035/Desktop/CADroid/RQ3/Evosuite";

        HashMap<String, String> sdk24 = new HashMap<>();
        HashMap<String, String> sdk25 = new HashMap<>();
        HashMap<String, String> sdk26 = new HashMap<>();
        HashMap<String, String> sdk28 = new HashMap<>();
        HashMap<String, String> sdk29 = new HashMap<>();
        HashMap<String, String> sdk30 = new HashMap<>();

        getSDKResultValueLog(logFolder, sdk24, "24");
        getSDKResultValueLog(logFolder, sdk25, "25");
        getSDKResultValueLog(logFolder, sdk26, "26");
        getSDKResultValueLog(logFolder, sdk28, "28");
        getSDKResultValueLog(logFolder, sdk29, "29");
        getSDKResultValueLog(logFolder, sdk30, "30");

        HashMap<String, String> allRes = new HashMap<>();
        allRes.putAll(sdk24);
        allRes.putAll(sdk25);
        allRes.putAll(sdk26);
        allRes.putAll(sdk28);
        allRes.putAll(sdk29);
        allRes.putAll(sdk30);

        HashMap<String, Integer> allExceptions = new HashMap<>();

        for(String s : allRes.keySet()){
            if(allRes.get(s) != null){
                IncrementHashMap.incrementValue(allExceptions, allRes.get(s));
                //System.out.println(s +"======="+ allRes.get(s));
            }
        }


        for(String s : allExceptions.keySet()){
            System.out.println(s + ":" +allExceptions.get(s));
        }


    }

    public static void getSDKResultValueLog(String logFolder, HashMap<String, String> sdkHashMap, String sdkVersion) throws IOException {
        List<String> fileList = getFileList(logFolder+"/"+sdkVersion);
        for(String filePathName : fileList){
            File file = new File(filePathName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for(String oneLineStr : fileContent){
                stringBuilder.append(oneLineStr);
            }

            for(int i=0; i< fileContent.size(); i++){
                String currentLineStr = fileContent.get(i);
                if(currentLineStr.contains("<testcase name") && !stringBuilder.toString().contains("notGeneratedAnyTest")){
                    String testCase = Regex.getSubUtilSimple(currentLineStr, "(<testcase name.*_ESTest\")");

                    String failureStr = fileContent.get(i+1);
                    String msg = Regex.getSubUtilSimple(failureStr, "(message=.*?:)").replace("message=\"", "");

                    sdkHashMap.put(testCase, msg);
                }
            }
        }
    }


    public static List<String> getFileList(String outputFilePath) throws IOException {
        List<String> txtFileNames = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith("ESTest.xml")) {
                txtFileNames.add(filename);
            }
        });

        return txtFileNames;
    }
}
