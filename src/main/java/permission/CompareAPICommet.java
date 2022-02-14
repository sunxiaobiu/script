package permission;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CompareAPICommet {

    public static void main(String[] args) throws IOException {
        String logFolder = args[0];

        String resFileFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/no_null_res.txt";
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(resFileFile, true)));

        String resALLFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/all_res.txt";
        PrintWriter allout = new PrintWriter(new BufferedWriter(new FileWriter(resALLFile, true)));


        HashMap<String, HashSet<String>> res = new HashMap<>();
        HashMap<String, String> sdk23 = new HashMap<>();
        HashMap<String, String> sdk24 = new HashMap<>();
        HashMap<String, String> sdk25 = new HashMap<>();
        HashMap<String, String> sdk26 = new HashMap<>();
        HashMap<String, String> sdk27 = new HashMap<>();
        HashMap<String, String> sdk28 = new HashMap<>();
        HashMap<String, String> sdk29 = new HashMap<>();
        HashMap<String, String> sdk30 = new HashMap<>();
        HashMap<String, String> sdk31 = new HashMap<>();

        //=========================================SDK 23-31==============================================

        getSDKHashLog(logFolder, sdk23, "23");
        getSDKHashLog(logFolder, sdk24, "24");
        getSDKHashLog(logFolder, sdk25, "25");
        getSDKHashLog(logFolder, sdk26, "26");
        getSDKHashLog(logFolder, sdk27, "27");
        getSDKHashLog(logFolder, sdk28, "28");
        getSDKHashLog(logFolder, sdk29, "29");
        getSDKHashLog(logFolder, sdk30, "30");
        getSDKHashLog(logFolder, sdk31, "31");


//        /**
//         * 所有结果
//         */
//        int num = 0;
//        for (String key : sdk31.keySet()) {
//            HashSet<String> resSet = new HashSet<>();
//            HashSet<String> resSetWithSDK = new HashSet<>();
//            resSet.add(sdk31.get(key));
//            resSetWithSDK.add(sdk31.get(key)+"(31)");
//
//            if (!resSet.contains(sdk30.get(key))) {
//                resSet.add(sdk30.get(key));
//                resSetWithSDK.add(sdk30.get(key)+"(30)");
//            }
//
//            if (!resSet.contains(sdk29.get(key))) {
//                resSet.add(sdk29.get(key));
//                resSetWithSDK.add(sdk29.get(key)+"(29)");
//            }
//
//            if (!resSet.contains(sdk28.get(key))) {
//                resSet.add(sdk28.get(key));
//                resSetWithSDK.add(sdk28.get(key)+"(28)");
//            }
//
//            if (!resSet.contains(sdk27.get(key))) {
//                resSet.add(sdk27.get(key));
//                resSetWithSDK.add(sdk27.get(key)+"(27)");
//            }
//
//            if (!resSet.contains(sdk26.get(key))) {
//                resSet.add(sdk26.get(key));
//                resSetWithSDK.add(sdk26.get(key)+"(26)");
//            }
//
//            if (!resSet.contains(sdk25.get(key))) {
//                resSet.add(sdk25.get(key));
//                resSetWithSDK.add(sdk25.get(key)+"(25)");
//            }
//
//            if (!resSet.contains(sdk24.get(key))) {
//                resSet.add(sdk24.get(key));
//                resSetWithSDK.add(sdk24.get(key)+"(24)");
//            }
//
//            if (!resSet.contains(sdk23.get(key))) {
//                resSet.add(sdk23.get(key));
//                resSetWithSDK.add(sdk23.get(key)+"(23)");
//            }
//
//            if (resSet.size() > 1) {
//                num ++;
//                //System.out.println(key+ "--------"+ resSetWithSDK);
//                allout.println(key+ "--------"+ resSetWithSDK);
//            }
//        }
//        System.out.println(num);


        /**
         * 排除null的结果
         */
        int num = 0;
        for (String key : sdk31.keySet()) {
            HashSet<String> resSet = new HashSet<>();
            HashSet<String> resSetWithSDK = new HashSet<>();
            resSet.add(sdk31.get(key));
            resSetWithSDK.add(sdk31.get(key)+"(31)");

            if (!resSet.contains(sdk30.get(key)) && sdk30.get(key) != null) {
                resSet.add(sdk30.get(key));
                resSetWithSDK.add(sdk30.get(key)+"(30)");
            }

            if (!resSet.contains(sdk29.get(key)) && sdk29.get(key) != null) {
                resSet.add(sdk29.get(key));
                resSetWithSDK.add(sdk29.get(key)+"(29)");
            }

            if (!resSet.contains(sdk28.get(key)) && sdk28.get(key) != null) {
                resSet.add(sdk28.get(key));
                resSetWithSDK.add(sdk28.get(key)+"(28)");
            }

            if (!resSet.contains(sdk27.get(key)) && sdk27.get(key) != null) {
                resSet.add(sdk27.get(key));
                resSetWithSDK.add(sdk27.get(key)+"(27)");
            }

            if (!resSet.contains(sdk26.get(key)) && sdk26.get(key) != null) {
                resSet.add(sdk26.get(key));
                resSetWithSDK.add(sdk26.get(key)+"(26)");
            }

            if (!resSet.contains(sdk25.get(key)) && sdk25.get(key) != null) {
                resSet.add(sdk25.get(key));
                resSetWithSDK.add(sdk25.get(key)+"(25)");
            }

            if (!resSet.contains(sdk24.get(key)) && sdk24.get(key) != null) {
                resSet.add(sdk24.get(key));
                resSetWithSDK.add(sdk24.get(key)+"(24)");
            }

            if (!resSet.contains(sdk23.get(key)) && sdk23.get(key) != null) {
                resSet.add(sdk23.get(key));
                resSetWithSDK.add(sdk23.get(key)+"(23)");
            }

            if (resSet.size() > 1) {
                num ++;
                System.out.println(key+ "--------"+ resSetWithSDK);
            }
        }
        System.out.println(num);


    }

    private static void getSDKHashLog(String logFolder, HashMap<String, String> sdkHashMap, String sdkVersion) throws IOException {
        File file = new File(logFolder + "/API_Comment_" + sdkVersion + ".txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        String currentAPI = "";
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String api = Regex.getSubUtilSimple(currentLine, "(<.*>\\|)").replace("|", "");
            String methodComment = currentLine.replace(api, "");

            if(StringUtils.isNotBlank(api)){
                currentAPI = api;
                sdkHashMap.put(api, methodComment);
            }else{
                String currentMethodComment = sdkHashMap.get(currentAPI);
                sdkHashMap.put(currentAPI, currentMethodComment + currentLine);
            }
        }
    }

}
