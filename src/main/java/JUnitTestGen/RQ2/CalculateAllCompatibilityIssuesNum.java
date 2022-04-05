package JUnitTestGen.RQ2;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CalculateAllCompatibilityIssuesNum {

    public static void main(String[] args) throws IOException {
        File allCompatibilityIssuesFile = new File("/Users/xsun0035/Desktop/CADroid/RQ2/AllTypeOfCompatibilityIssues/AllReturnValueResult.txt");
        List<String> allCompatibilityIssues = new ArrayList<>(Files.readAllLines(allCompatibilityIssuesFile.toPath(), StandardCharsets.UTF_8));

        Set<String> allcompatibilityIssues = new HashSet<>();

        for(String str : allCompatibilityIssues){
            String testCaseName = Regex.getSubUtilSimple(str, "(TestCase_.*Test)");
            allcompatibilityIssues.add(testCaseName);
        }

        for(String s : allcompatibilityIssues){
            System.out.println(s);
        }
    }
}
