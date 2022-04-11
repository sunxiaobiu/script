package AutoUnitTest_Refer2JUnitTestGen.RQ1;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class BoxPlotPerAPI {

    public static void main(String[] args) throws IOException {
        String csvFile = args[0];
        String logFile = args[1];
        /**
         * Prepare csv Map
         */
        BufferedReader bufferReader = null;
        String csvLine = "";
        HashMap<String, List<String>> csvMap = new HashMap<>();

        bufferReader = new BufferedReader(new FileReader(csvFile));
        while ((csvLine = bufferReader.readLine()) != null) {
            List<String> line = Arrays.asList(csvLine.split(",", 2));
            String testCaseName = line.get(0).replaceAll("\"", "");
            String apiSig = line.get(1).replaceAll("\"", "");
            if(csvMap.containsKey(apiSig)){
                csvMap.get(apiSig).add(testCaseName);
            }else{
                List<String> tmp = new ArrayList<>();
                tmp.add(testCaseName);
                csvMap.put(apiSig, tmp);
            }
        }

        /**
         * Prepare log file
         */
        HashMap<String, String> logResMap = new HashMap<>();
        getSDKHashLog(logFile, logResMap, "29");

        for(String api : csvMap.keySet()){
            int successNum = 0;
            int testCaseSize = csvMap.get(api).size();
            for(String testCase : csvMap.get(api)){
                if(logResMap.get(testCase) != null && !logResMap.get(testCase).contains("easymock") && !logResMap.get(testCase).contains("ExceptionInInitializerError")){
                    successNum ++;
                }
            }

            if(successNum != 0 ){
                System.out.println(testCaseSize + ";" + successNum);
            }
        }


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
