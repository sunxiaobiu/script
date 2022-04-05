package JUnitTestGen.RQ1;

import JUnitTestGen.RQResult.CompatibilityIssues;
import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidRate {

    public static void main(String[] args) throws IOException {
        //log result in SDK30
        HashMap<String, String> sdk30 = new HashMap<>();
        CompatibilityIssues.getSDKHashLog("/Users/xsun0035/Desktop/CADroid", sdk30, "30");

        //Exception line number from execution log
        HashMap<String, Integer> exceptionLineNumber = new HashMap<>();
        File logFile = new File("/Users/xsun0035/Desktop/CADroid/JUnitTestRun_SDK30_log.txt");
        List<String> logFileContent = new ArrayList<>(Files.readAllLines(logFile.toPath(), StandardCharsets.UTF_8));

        //Target API line number from test case source file
        HashMap<String, Integer> targetAPILineNumber = new HashMap<>();

        File exceptionOnEachSDKFile = new File("/Users/xsun0035/Desktop/CADroid/ExceptionOnEachSDK.txt");
        List<String> exceptionOnEachSDKContent = new ArrayList<>(Files.readAllLines(exceptionOnEachSDKFile.toPath(), StandardCharsets.UTF_8));
        for(String testCaseNameException : exceptionOnEachSDKContent){
            String testCaseName = Regex.getSubUtilSimple(testCaseNameException, "(TestCase.*Test)");

            File testCaseFile = new File("/Users/xsun0035/Desktop/CADroid/All_Valid_Tests/"+testCaseName+".java");
            List<String> testCaseContent = new ArrayList<>(Files.readAllLines(testCaseFile.toPath(), StandardCharsets.UTF_8));
            for(int i = 0; i < testCaseContent.size(); i ++){
                String currentLine =  testCaseContent.get(i);
                String outLine = Regex.getSubUtilSimple(currentLine, "(out\\(.*\\);)");
                if(StringUtils.isNotBlank(outLine)){
                    targetAPILineNumber.put(testCaseName, i);
                    break;
                }
            }

            for (int i = 0; i < logFileContent.size(); i++) {
                String currentLine = logFileContent.get(i);
                if(currentLine.contains(testCaseName + ".testCase(" + testCaseName + ".java:")){
                    int lineNumber = Integer.parseInt(Regex.getSubUtilSimple(currentLine, "(Test.java:[0-9]+)").replace("Test.java:", ""));
                    exceptionLineNumber.put(testCaseName, lineNumber);
                }
            }
        }

        for(String testCaseName : exceptionLineNumber.keySet()){
           if(!exceptionLineNumber.get(testCaseName).equals(targetAPILineNumber.get(testCaseName))
                   && !sdk30.get(testCaseName).equals("android.content.pm.PackageManager$NameNotFoundException")
                   && !sdk30.get(testCaseName).equals("FileNotFoundException")
                   && !sdk30.get(testCaseName).equals("android.content.res.Resources$NotFoundException")
                   && !sdk30.get(testCaseName).equals("android.content.ActivityNotFoundException")
                   && !sdk30.get(testCaseName).equals("android.app.PendingIntent$CanceledException")
                   && !sdk30.get(testCaseName).equals("android.view.WindowManager$BadTokenException")
                   && !sdk30.get(testCaseName).equals("android.database.sqlite.SQLiteCantOpenDatabaseException")
                   && !sdk30.get(testCaseName).equals("android.view.ViewRootImpl$CalledFromWrongThreadException")
           ){
               //output invalid test names
               PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/CADroid/RQ1/InAllValidTestNames.txt", true)));
               out.println(testCaseName);
               out.close();
           }else{
               //output valid test names
//               PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/CADroid/RQ1/AllValidTestNames.txt", true)));
//               out.println(testCaseName);
//               out.close();
           }
        }
    }

}
