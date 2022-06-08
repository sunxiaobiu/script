package JUnitTestGen.CiD;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class AllCompatibilityIssues_API {

    public static void main(String[] args) throws IOException {
        File testAPIFile = new File("/Users/xsun0035/Desktop/CADroid/RQ3/CID/TestCaseNme_TargetAPI_Map.txt");
        List<String> testAPIMap = new ArrayList<>(Files.readAllLines(testAPIFile.toPath(), StandardCharsets.UTF_8));

        HashMap<String, String> testNameAPIMap = new HashMap<>();
        for(String s : testAPIMap){
            String[] testNameAPI = s.split("\\|");
            String testCaseName = testNameAPI[0];
            String api = testNameAPI[1];
            testNameAPIMap.put(testCaseName, api);
        }

        File AllCompatibilityIssues_TestCaseName_File = new File("/Users/xsun0035/Desktop/CADroid/RQ3/CID/AllCompatibilityIssues_TestCaseName.txt");
        List<String> AllCompatibilityIssues_TestCaseNames = new ArrayList<>(Files.readAllLines(AllCompatibilityIssues_TestCaseName_File.toPath(), StandardCharsets.UTF_8));

        for(String testName : AllCompatibilityIssues_TestCaseNames){
            if(testNameAPIMap.get(testName) == null){
                System.out.println("j");
            }
            System.out.println(testNameAPIMap.get(testName));
        }

    }
}
