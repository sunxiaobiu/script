package AutoUnitTest.result;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class DistinctAPI {
    public static void main(String[] args) throws IOException {
        String logFile = args[0];
        String csvFile = args[1];
        Set<String> duplicatdAPI = new HashSet<>();
        /**
         * Prepare csv Map
         */
        //String csvFile = "/Users/xsun0035/Desktop/UnitTest-results/improve_success_rate/1k/utNameAPISigCsvPath_1k.csv";

        BufferedReader bufferReader = null;
        String csvLine = "";
        HashMap<String, String> csvMap = new HashMap<>();

        bufferReader = new BufferedReader(new FileReader(csvFile));
        while ((csvLine = bufferReader.readLine()) != null) {
            List<String> line = Arrays.asList(csvLine.split(",", 2));
            csvMap.put(line.get(0).replaceAll("\"", ""), line.get(1).replaceAll("\"", ""));
        }

        /**
         * match TestCase_Name and its corresponding API Signature
         */
        HashMap<String, String> logResMap = new HashMap<>();
        getSDKHashLog(logFile, logResMap, "29");

        for(String key : logResMap.keySet()){
            if(!logResMap.get(key).contains("easymock") && !logResMap.get(key).contains("ExceptionInInitializerError")){
                String API = csvMap.get(key);
                if(API != null){
                    duplicatdAPI.add(csvMap.get(key));
                }
            }
        }
//        for(String key : logResMap.keySet()){
//            if(!logResMap.get(key).equals("success")){
//                String API = csvMap.get(key);
//                if(API != null){
//                    duplicatdAPI.add(csvMap.get(key));
//                }
//            }
//        }

        System.out.println("Distinct API Num:"+duplicatdAPI.size());
        System.out.println(duplicatdAPI);

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
