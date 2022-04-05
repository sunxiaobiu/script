package JUnitTestGen.RQ1;

import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerAPK {

    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> res = new HashMap<>();
        //output valid test case number for each apk
//        File validFile = new File("/Users/xsun0035/Desktop/CADroid/RQ1/AllValidTestNames.txt");
//        List<String> validFileContent = new ArrayList<>(Files.readAllLines(validFile.toPath(), StandardCharsets.UTF_8));
//        for (int i = 0; i < validFileContent.size(); i++) {
//            String currentLine = validFileContent.get(i);
//            String apkName = Regex.getSubUtilSimple(currentLine, "(TestCase.*_)").replace("_", "");
//            IncrementHashMap.incrementValue(res, apkName);
//        }
//
//        for(String s : res.keySet()){
//            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/CADroid/RQ1/ValidTestNumber.txt", true)));
//            out.println(s + ":" + res.get(s));
//            out.close();
//        }

        //output invalid test case number for each apk
        File invalidFile = new File("/Users/xsun0035/Desktop/CADroid/RQ1/InAllValidTestNames.txt");
        List<String> invalidFileContent = new ArrayList<>(Files.readAllLines(invalidFile.toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < invalidFileContent.size(); i++) {
            String currentLine = invalidFileContent.get(i);
            String apkName = Regex.getSubUtilSimple(currentLine, "(TestCase.*_)").replace("_", "");
            IncrementHashMap.incrementValue(res, apkName);
        }

        for(String s : res.keySet()){
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/CADroid/RQ1/InvalidTestNumber.txt", true)));
            out.println(s + ":" + res.get(s));
            out.close();
        }

    }
}
