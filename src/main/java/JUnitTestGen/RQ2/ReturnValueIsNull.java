package JUnitTestGen.RQ2;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ReturnValueIsNull {

    public static void main(String[] args) throws IOException {
        String logFolder = "/Users/xsun0035/Desktop/CADroid";

        HashMap<String, HashSet<String>> res = new HashMap<>();
        HashMap<String, String> sdk21 = new HashMap<>();
        HashMap<String, String> sdk22 = new HashMap<>();
        HashMap<String, String> sdk23 = new HashMap<>();
        HashMap<String, String> sdk24 = new HashMap<>();
        HashMap<String, String> sdk25 = new HashMap<>();
        HashMap<String, String> sdk26 = new HashMap<>();
        HashMap<String, String> sdk27 = new HashMap<>();
        HashMap<String, String> sdk28 = new HashMap<>();
        HashMap<String, String> sdk29 = new HashMap<>();
        HashMap<String, String> sdk30 = new HashMap<>();

        //=========================================SDK 21-30==============================================

        getSDKResultValueLog(logFolder, sdk21, "21");
        getSDKResultValueLog(logFolder, sdk22, "22");
        getSDKResultValueLog(logFolder, sdk23, "23");
        getSDKResultValueLog(logFolder, sdk24, "24");
        getSDKResultValueLog(logFolder, sdk25, "25");
        getSDKResultValueLog(logFolder, sdk26, "26");
        getSDKResultValueLog(logFolder, sdk27, "27");
        getSDKResultValueLog(logFolder, sdk28, "28");
        getSDKResultValueLog(logFolder, sdk29, "29");
        getSDKResultValueLog(logFolder, sdk30, "30");

        for (String key : sdk30.keySet()) {
            if (key.equals("TestCase_com_application_onlineshoppinginzimbabwe__363742032Test") || key.equals("TestCase_ch_fhnw__562818555Test")) {
                continue;
            }
            HashSet<String> resSet = new HashSet<>();
            resSet.add(sdk30.get(key));

            if (!resSet.contains(sdk29.get(key))) {
                resSet.add(sdk29.get(key));
            }

            if (!resSet.contains(sdk28.get(key))) {
                resSet.add(sdk28.get(key));
            }

            if (!resSet.contains(sdk27.get(key))) {
                resSet.add(sdk27.get(key));
            }

            if (!resSet.contains(sdk26.get(key))) {
                resSet.add(sdk26.get(key));
            }

            if (!resSet.contains(sdk25.get(key))) {
                resSet.add(sdk25.get(key));
            }

            if (!resSet.contains(sdk24.get(key))) {
                resSet.add(sdk24.get(key));
            }

            if (!resSet.contains(sdk23.get(key))) {
                resSet.add(sdk23.get(key));
            }

            if (!resSet.contains(sdk22.get(key))) {
                resSet.add(sdk22.get(key));
            }

            if (!resSet.contains(sdk21.get(key))) {
                resSet.add(sdk21.get(key));
            }

            if (resSet.size() > 1 && !(resSet.contains("null") || resSet.contains("null.") || resSet.contains(null))) {
//                if(resSet.contains(null) || resSet.contains("")){
//                    System.out.println(key + "--------"+ resSet);
//                }
                /**
                 * output all compatibility issues, whose return value is null
                 */
                String outputRes = key + "--------" + resSet;
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/CADroid/RQ2/ReturnValueIsNotNull.txt", true)));
                out.println(outputRes);
                out.close();
            }
        }
    }

    public static void getSDKResultValueLog(String logFolder, HashMap<String, String> sdkHashMap, String sdkVersion) throws IOException {
        File file = new File(logFolder + "/JUnitTestRun_SDK" + sdkVersion + "_log.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String successTestCase = Regex.getSubUtilSimple(currentLine, "(TestCase_.*\\[result\\].*)");

            //success
            if (currentLine.startsWith("TestCase_") && StringUtils.isNotBlank(successTestCase)) {
                String testCaseName = Regex.getSubUtilSimple(successTestCase, "(TestCase_.*Test:\\[result\\])").replace(":[result]", "");

                String resContent = successTestCase.replace(testCaseName, "").replace(":[result]", "");
                String filteredContent = "";
                if(resContent.contains("@")){
                    filteredContent = resContent.replace(Regex.getSubUtilSimple(resContent, "(@.*)"), "");
                }else{
                    filteredContent = resContent;
                }
                sdkHashMap.put(testCaseName, filteredContent);
            }

//            //crash
//            if (currentLine.startsWith("TestCase_") && StringUtils.isNotBlank(crashTestCase)) {
//                sdkHashMap.put(Regex.getSubUtilSimple(crashTestCase, "(TestCase_.*Test)"), "crash");
//            }
        }
    }
}
