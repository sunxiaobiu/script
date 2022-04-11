package JUnitTestGen.CiD;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class JunitTestGenRes {

    public static void main(String[] args) throws IOException {
        String testCaseName_TargetAPI_File = "/Users/xsun0035/Desktop/CADroid/RQ3/CID/TestCaseNme_TargetAPI_Map.txt";

        String compatibilityIssuesFile = "/Users/xsun0035/Desktop/CADroid/RQ3/CID/AllCompatibilityIssues_TestCaseName.txt";
        File file = new File(compatibilityIssuesFile);
        List<String> compatibilityIssues_TestCaseName = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        List<String> testCaseName_TargetAPI = new ArrayList<>(Files.readAllLines(new File(testCaseName_TargetAPI_File).toPath(), StandardCharsets.UTF_8));

        HashMap<String, String> testAPIMap = new HashMap<>();
        for(String testCaseName_APIStr : testCaseName_TargetAPI){
            String[] apkLineage = testCaseName_APIStr.split("\\|");
            String testCaseName = apkLineage[0];
            String api = apkLineage[1];
            testAPIMap.put(testCaseName, api);
        }

        List<String> compatibilityIssuesAPIs = new ArrayList<>();
        for(String testName : compatibilityIssues_TestCaseName){
            //output API with has compatibility issues
            compatibilityIssuesAPIs.add(testAPIMap.get(testName));
        }

        for(String s : compatibilityIssuesAPIs){
            System.out.println(s);
        }

    }
}
