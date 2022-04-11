package JUnitTestGen.CiD;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CiDJunitTestGenDiff {

    public static void main(String[] args) throws IOException {
        String cidResPath = "/Users/xsun0035/Desktop/CADroid/RQ3/CID/AllCiDRes.txt";
        String junitTestGenResPath = "/Users/xsun0035/Desktop/CADroid/RQ3/CID/AllCompatibilityIssues_API_duplicate.txt";

        File cidResFile = new File(cidResPath);
        List<String> cidResFileContent = extractMethodSignature(new ArrayList<>(Files.readAllLines(cidResFile.toPath(), StandardCharsets.UTF_8)));

        File junitTestGenResFile = new File(junitTestGenResPath);
        List<String> junitTestGenResContent = extractMethodSignature(new ArrayList<>(Files.readAllLines(junitTestGenResFile.toPath(), StandardCharsets.UTF_8)));

        junitTestGenResContent.removeAll(cidResFileContent);
        for(String s : junitTestGenResContent){
            System.out.println(s);
        }
//        cidResFileContent.removeAll(junitTestGenResContent);
//        for(String s : cidResFileContent){
//            if(!isAndroidUIMethod(s)){
//                System.out.println(s);
//            }
//        }
    }

    public static List<String> extractMethodSignature(List<String> originList){
        List<String> res = new ArrayList<>();
        for(String s : originList){
            res.add(Regex.getSubUtilSimple(s, "(<.*>)"));
        }
        return res;
    }

    public static boolean isAndroidUIMethod(String unitString) {
        return unitString.startsWith("<android.widget")
                || unitString.startsWith("<android.view")
                || unitString.startsWith("<android.webkit")
                || unitString.startsWith("<android.content.res.Resources")
                || unitString.startsWith("<android.app.Dialog")
                || unitString.startsWith("<android.app.AlertDialog")
                || unitString.startsWith("<android.media")
                ;
    }
}
