package JUnitTestGen.RQResult;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AllExceptionType {

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

        //=========================================SDK 21-29==============================================

        CompatibilityIssues.getSDKHashLog(logFolder, sdk21, "21");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk22, "22");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk23, "23");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk24, "24");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk25, "25");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk26, "26");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk27, "27");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk28, "28");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk29, "29");
        CompatibilityIssues.getSDKHashLog(logFolder, sdk30, "30");

        HashSet<String> resSet = new HashSet<>();
        for (String key : sdk30.keySet()) {
            if (key.equals("TestCase_com_application_onlineshoppinginzimbabwe__363742032Test") || key.equals("TestCase_ch_fhnw__562818555Test")) {
                continue;
            }
            resSet.add(sdk30.get(key));

            if(!resSet.contains(sdk29.get(key))){
                resSet.add(sdk29.get(key));
            }

            if(!resSet.contains(sdk28.get(key))){
                resSet.add(sdk28.get(key));
            }

            if(!resSet.contains(sdk27.get(key))){
                resSet.add(sdk27.get(key));
            }

            if(!resSet.contains(sdk26.get(key))){
                resSet.add(sdk26.get(key));
            }

            if(!resSet.contains(sdk25.get(key))){
                resSet.add(sdk25.get(key));
            }

            if(!resSet.contains(sdk24.get(key))){
                resSet.add(sdk24.get(key));
            }

            if(!resSet.contains(sdk23.get(key))){
                resSet.add(sdk23.get(key));
            }

            if(!resSet.contains(sdk22.get(key))){
                resSet.add(sdk22.get(key));
            }

            if(!resSet.contains(sdk21.get(key))){
                resSet.add(sdk21.get(key));
            }
        }

        for(String exceptionType : resSet){
            System.out.println(exceptionType);
        }
    }


}
