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

public class EasyMockErrorNum {

    public static void main(String[] args) throws IOException {
        String logFile = args[0];
        HashMap<String, String> sdk29 = new HashMap<>();

        getSDKHashLog(logFile, sdk29, "29");
        int easyMockErrorNum = 0;
        for(String key : sdk29.keySet()){
            if(sdk29.get(key).contains("easymock") || sdk29.get(key).contains("ExceptionInInitializerError")){
                easyMockErrorNum ++;
            }
        }

        System.out.println("Total Num:"+ sdk29.size());
        //System.out.println("easyMockErrorNum:"+easyMockErrorNum);
        System.out.println("success invoke num:"+ (sdk29.size() - easyMockErrorNum));

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
