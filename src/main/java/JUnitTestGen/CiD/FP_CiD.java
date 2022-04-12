package JUnitTestGen.CiD;

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

public class FP_CiD {

    public static void main(String[] args) throws IOException {
        String cidResPath = "/Users/xsun0035/Desktop/CADroid/RQ3/CID/AllCiDRes.txt";
        String junitTestGenPath = "/Users/xsun0035/Desktop/CADroid/RQ3/CID/TestCaseNme_TargetAPI_Map.txt";
        String junitTestCIPath = "/Users/xsun0035/Desktop/CADroid/RQ3/CID/AllCompatibilityIssues_API.txt";

        File cidResFile = new File(cidResPath);
        List<String> cidResFileContent = extractMethodSignature(new ArrayList<>(Files.readAllLines(cidResFile.toPath(), StandardCharsets.UTF_8)));

        File junitTestGenPathFile = new File(junitTestGenPath);
        List<String> junitTestAllContent = extractMethodSignature(new ArrayList<>(Files.readAllLines(junitTestGenPathFile.toPath(), StandardCharsets.UTF_8)));

        File junitTestCIFile = new File(junitTestCIPath);
        List<String> junitTestCIContent = extractMethodSignature(new ArrayList<>(Files.readAllLines(junitTestCIFile.toPath(), StandardCharsets.UTF_8)));

        junitTestAllContent.removeAll(junitTestCIContent);
        List<String> trueNegativeOfJUnit = junitTestAllContent;

        Set<String> intersection = CollectionIntersection.findIntersection(new HashSet<>(), trueNegativeOfJUnit, cidResFileContent);

        for(String s : intersection){
            System.out.println(s);
        }


    }

    public static List<String> extractMethodSignature(List<String> originList){
        List<String> res = new ArrayList<>();
        for(String s : originList){
            res.add(Regex.getSubUtilSimple(s, "(<.*>)"));
        }
        return res;
    }
}
