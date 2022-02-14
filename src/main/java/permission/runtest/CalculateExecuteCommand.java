package permission.runtest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculateExecuteCommand {

    public static void main(String[] args) throws IOException {
        String testCasePath = "/Users/xsun0035/workspace/monash/PermissionCI/app/src/androidTest/java/com/edu/permission/compatibilityissue";

        List<String> testCaseFileNames = new ArrayList<>();

        getTestList(testCasePath, testCaseFileNames);

        String testCasePath2 = "/Users/xsun0035/workspace/monash/PermissionCI/app/src/androidTest/java/com/edu/permission/compatibilityissue.";

        for(String testCaseName : testCaseFileNames){
            if(testCaseName.contains(testCasePath2)){
                continue;
            }
            String outputFile = "/Users/xsun0035/Desktop/permission_runtest/runtestCaseExample.sh";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
            out.println("adb shell am instrument -w -r -e debug false -e class "+testCaseName+" com.edu.permission.compatibilityissue.test/androidx.test.runner.AndroidJUnitRunner > /Users/xsun0035/Desktop/permission_runtest/output/"+testCaseName+".txt 2>&1");
            out.close();
        }

    }



    public static void getTestList(String outputFilePath, List<String> txtFileNames) throws IOException {
        String testCasePath = "/Users/xsun0035/workspace/monash/PermissionCI/app/src/androidTest/java/com/edu/permission/compatibilityissue/";

        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> ("com.edu.permission.compatibilityissue."+x.getParent().toString().replace(testCasePath, "") + "."+x.getFileName().toString().replace(".java", ""))).collect(Collectors.toList());

        result.stream().forEach(filename -> {
                txtFileNames.add(filename);
        });
    }
}
