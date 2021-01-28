package AutoUnitTest.result;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NoCrashTests {

    public static void main(String[] args) throws IOException {
        String logFolder = args[0];

//        HashMap<String, String> sdk21 = new HashMap<>();
//        HashMap<String, String> sdk22 = new HashMap<>();
//        HashMap<String, String> sdk23 = new HashMap<>();
//        HashMap<String, String> sdk24 = new HashMap<>();
//        HashMap<String, String> sdk25 = new HashMap<>();
//        HashMap<String, String> sdk26 = new HashMap<>();
//        HashMap<String, String> sdk27 = new HashMap<>();
//        HashMap<String, String> sdk28 = new HashMap<>();
        HashMap<String, String> sdk29 = new HashMap<>();

        //=========================================SDK 21-29==============================================

//        getSDKHashLog(logFolder, sdk21, "21");
//        getSDKHashLog(logFolder, sdk22, "22");
//        getSDKHashLog(logFolder, sdk23, "23");
//        getSDKHashLog(logFolder, sdk24, "24");
//        getSDKHashLog(logFolder, sdk25, "25");
//        getSDKHashLog(logFolder, sdk26, "26");
//        getSDKHashLog(logFolder, sdk27, "27");
//        getSDKHashLog(logFolder, sdk28, "28");
        getSDKHashLog(logFolder, sdk29, "29");

        for(String key : sdk29.keySet()){
            System.out.println(key+".java");
        }

    }

    private static void getSDKHashLog(String logFolder, HashMap<String, String> sdkHashMap, String sdkVersion) throws IOException {
        //File file = new File(logFolder + "/API" + sdkVersion + "_log.txt");
        File file = new File(logFolder + "220001.txt");

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

                    String currentExpLine2 = Regex.getSubUtilSimple(fileContent.get(j), "(java.lang.*?:)");
                    if(fileContent.get(j).startsWith("java.lang.") && StringUtils.isNotBlank(currentExpLine2)){
                        exceptionContene = currentExpLine2.replaceAll("java.lang.", "").replaceAll(":","");
                        break;
                    }

                    String currentExpLine = Regex.getSubUtilSimple(fileContent.get(j), "(java.lang.*)");
                    if(fileContent.get(j).startsWith("java.lang.") && StringUtils.isNotBlank(currentExpLine)){
                        exceptionContene = currentExpLine.replaceAll("java.lang.", "");
                        break;
                    }
                }
                sdkHashMap.put(failTestCase.replaceAll(":", ""), exceptionContene);
            }
        }
    }
}
