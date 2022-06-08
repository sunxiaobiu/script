package JUnitTestGen.RQ1;

import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class PerAPK {

    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> res = new HashMap<>();
        //collect all Tests Names
        File allTestFile = new File("/Users/xsun0035/Desktop/CADroid/RQ1/Figure3/AllTestCases_Class.txt");
        List<String> allFileContent = new ArrayList<>(Files.readAllLines(allTestFile.toPath(), StandardCharsets.UTF_8));
        //collect valid Tests Names
        File validTestFile = new File("/Users/xsun0035/Desktop/CADroid/RQ1/Figure3/AllTestCases_Java.txt");
        List<String> validFileContent = new ArrayList<>(Files.readAllLines(validTestFile.toPath(), StandardCharsets.UTF_8));

//        //output valid test case number for each apk
//        for (int i = 0; i < validFileContent.size(); i++) {
//            String currentLine = validFileContent.get(i);
//            String apkName = Regex.getSubUtilSimple(currentLine, "(TestCase.*_)").replace("_", "");
//            IncrementHashMap.incrementValue(res, apkName);
//        }
//
//        for (String s : res.keySet()) {
//            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/CADroid/RQ1/Figure3/ValidTestNumber.txt", true)));
//            out.println(s + ":" + res.get(s));
//            out.close();
//        }

        //output invalid test case number for each apk
        Set<String> allTestFileSet = new HashSet<>(allFileContent);
        Set<String> validTestFileSet = new HashSet<>(validFileContent);
        allTestFileSet.removeAll(validTestFileSet);
        List<String> invalidTests = new ArrayList<String>();
        invalidTests.addAll(allTestFileSet);
        for (int i = 0; i < invalidTests.size(); i++) {
            String currentLine = invalidTests.get(i);
            String apkName = Regex.getSubUtilSimple(currentLine, "(TestCase.*_)").replace("_", "");
            IncrementHashMap.incrementValue(res, apkName);
        }

        for (String s : res.keySet()) {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/CADroid/RQ1/Figure3/InValidTestNumber.txt", true)));
            out.println(s + ":" + res.get(s));
            out.close();
        }


    }
}
