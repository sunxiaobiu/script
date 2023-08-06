package typestate.compatibility;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.CollectionIntersection;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AddSdk2Rules {

    public static void main(String[] args) throws IOException {
        /**
         * collect minCompatibilityRes  &  maxCompatibilityRes
         */
        HashMap<String, Integer> minCompatibilityRes = new HashMap<>();
        HashMap<String, Integer> maxCompatibilityRes = new HashMap<>();
        File compatibilityFile = new File("/Users/xsun0035/Desktop/TypeState_ICSE2023/compatibility.txt");
        List<String> compatibilityFileContent = new ArrayList<>(Files.readAllLines(compatibilityFile.toPath(), StandardCharsets.UTF_8));

        for (int i = 0; i < compatibilityFileContent.size(); i++) {
            String currentLine = compatibilityFileContent.get(i);

            String api = Regex.getSubUtilSimple(currentLine, "(<.*>::)").replace("::", "");

            int maxSDK = 0;
            int minSDK = 32;
            List<String> sdkNums = Regex.getSubUtilSimpleList(currentLine, "(\\([0-9]+\\))");
            if(CollectionUtils.isNotEmpty(sdkNums)){
                for(String sdkNum : sdkNums){
                    int currentVersion = Integer.parseInt(sdkNum.replace("(","").replace(")",""));
                    if(currentVersion > maxSDK){
                        maxSDK = currentVersion;
                    }
                    if(currentVersion < minSDK){
                        minSDK = currentVersion;
                    }
                }
            }
            if(minSDK != 0){
                minCompatibilityRes.put(api, minSDK);
                //System.out.println(api+"===min=="+minSDK);
            }
            if(maxSDK != 0){
                maxCompatibilityRes.put(api, maxSDK);
                //System.out.println(api+"===max=="+maxSDK);
            }
        }

        File ruleFile = new File("/Users/xsun0035/Desktop/TypeStateRules.txt");
        List<String> ruleFileContent = new ArrayList<>(Files.readAllLines(ruleFile.toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < ruleFileContent.size(); i++) {
            String currentLine = ruleFileContent.get(i);
            List<String> apis = Regex.getSubUtilSimpleList(currentLine, "(<.*?>)");

            List<Integer> minSDKList = new ArrayList<>();
            List<Integer> maxSDKList = new ArrayList<>();
            for(String api : apis){
                minSDKList.add(minCompatibilityRes.get(api));
                maxSDKList.add(maxCompatibilityRes.get(api));
            }

            Integer minSDKValue;
            if(CollectionIntersection.maxValue(minSDKList) == Integer.MIN_VALUE){
                minSDKValue = 1;
            }else{
                minSDKValue = CollectionIntersection.maxValue(minSDKList);
            }

            Integer maxSDKValue;
            if(CollectionIntersection.minValue(maxSDKList) == Integer.MAX_VALUE){
                maxSDKValue = 32;
            }else{
                maxSDKValue = CollectionIntersection.minValue(maxSDKList);
            }

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/RulesWithSDK.txt", true)));
            out.println("[SDKVersion>=" + minSDKValue+ ",SDKVersion<="+maxSDKValue+"];"+ currentLine);
            out.close();
        }
    }
}
