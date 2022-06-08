package JUnitTestGen.RQ1;

import util.CollectionIntersection;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquivalentTargetAPI {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/EquivalentTestCases.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        File executedTestsFile = new File("/Users/xsun0035/Desktop/CADroid/DistinctAPI.txt");
        List<String> executedTestsContent = new ArrayList<>(Files.readAllLines(executedTestsFile.toPath(), StandardCharsets.UTF_8));

        Set<String> EquivalentTestCases = new HashSet<>();
        for(String s : fileContent){
            String currentLine = s;
            String targetAPI = Regex.getSubUtilSimple(currentLine, "(<.*>=====)").replace("=====", "");
            EquivalentTestCases.add(targetAPI);
        }

        Set<String> executedTests = new HashSet<>();
        for(String s : executedTestsContent){
            String currentLine = s;
            String targetAPI = Regex.getSubUtilSimple(currentLine, "(<.*>)");
            executedTests.add(targetAPI);
        }

        Set<String> intersectionSet = new HashSet<>();
        CollectionIntersection.findIntersection(intersectionSet, EquivalentTestCases, executedTests);

        for(String s : intersectionSet){
            System.out.println(s);
        }
    }
}
