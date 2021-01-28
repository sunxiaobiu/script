package AutoUnitTest.RQ1;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoxPlotPerAPK {

    public static void main(String[] args) throws IOException {
        String testCasesFilePath = args[0];
        String logFile = args[1];
        List<String> logFileNames = new ArrayList<>();

        getFileList(testCasesFilePath, logFileNames);

        //match TestCase_Name and its corresponding test cases
        HashMap<String, String> logResMap = new HashMap<>();
        getSDKHashLog(logFile, logResMap, "29");

        for (String txtFileName : logFileNames) {
            int totalNum = 0;
            int exceptionNum = 0;
            int successNum = 0;
            File file = new File(testCasesFilePath + txtFileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {
                String line = fileContent.get(i);
                if(line.startsWith("GlobalRef.apiTestCaseModelList Num:")){
                    totalNum = Integer.parseInt(line.replaceAll("GlobalRef.apiTestCaseModelList Num:", ""));
                }

                if(line.startsWith("exceptionNum:")){
                    exceptionNum = Integer.parseInt(line.replaceAll("exceptionNum:", ""));
                }
            }

            for(String key : logResMap.keySet()){
                if(!logResMap.get(key).contains("easymock") && !logResMap.get(key).contains("ExceptionInInitializerError")){
                    if(key.contains(txtFileName.replaceAll(".txt", "").replace(".","_"))){
                        successNum ++;
                    }
                }
            }

            //System.out.println(totalNum);
            if(totalNum-exceptionNum >= 0 && totalNum-exceptionNum >= successNum && totalNum-exceptionNum !=0){
                System.out.println(totalNum-exceptionNum+";"+successNum);
            }

        }
    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });
    }

    private static void getSDKHashLog(String logFile, HashMap<String, String> sdkHashMap, String sdkVersion) throws IOException {
        //File file = new File(logFolder + "/API" + sdkVersion + "_log.txt");
        File file = new File(logFile);

        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String successTestCase = Regex.getSubUtilSimple(currentLine, "(TestCase_.*:.)");
            String failTestCase = Regex.getSubUtilSimple(currentLine, "(TestCase_.*:$)");

            //success
            if (currentLine.startsWith("TestCase_") && StringUtils.isNotBlank(successTestCase)) {
                sdkHashMap.put(successTestCase.replaceAll(":.", ""), "success");
            }

            //fail
            String exceptionContene = "";
            if (currentLine.startsWith("TestCase_") && StringUtils.isNotBlank(failTestCase)) {
                for(int j = i; j < fileContent.size(); j++){

                    String currentExpLine2 = Regex.getSubUtilSimple(fileContent.get(j), "(java.lang.*)");
                    if(fileContent.get(j).startsWith("java.lang.") && StringUtils.isNotBlank(currentExpLine2)){
                        exceptionContene = currentExpLine2;
                        break;
                    }

                    String currentExpLine3 = Regex.getSubUtilSimple(fileContent.get(j), "(java.util.*)");
                    if(fileContent.get(j).startsWith("java.util.") && StringUtils.isNotBlank(currentExpLine3)){
                        exceptionContene = currentExpLine3;
                        break;
                    }
                }
                sdkHashMap.put(failTestCase.replaceAll(":", ""), exceptionContene);
            }

        }
    }
}
