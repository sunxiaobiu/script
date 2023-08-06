package typestate.icse.RQ2.res;

import org.apache.commons.lang3.StringUtils;
import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class FixedCases_ResourceLeak {
    public static void main(String[] args) throws IOException {
        File fixedCasesFile = new File("/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ2/lineageExperiment/result/ResultDiff_ResourceLeak.txt");
        List<String> fixedCasesLines = new ArrayList<>(Files.readAllLines(fixedCasesFile.toPath(), StandardCharsets.UTF_8));

        int resourceLeakMethodNum = 0;
        int resourceLeakComponentNum = 0;
        int resourceLeakICCNum = 0;
        int resourceLeakCallbackNum = 0;

        Set<String> misuseAPIs = new HashSet<>();
        HashMap<String, Integer> misuseAPIPairs = new HashMap<>();
        HashMap<String, Integer> misuseAPINum = new HashMap<>();
        HashMap<String, Integer> misuseClassNameNum = new HashMap<>();

        for(String fixedLine : fixedCasesLines){
            String currentLine = fixedLine;
            Set<String> apis = new HashSet<>();
            String lackBeforeStr = Regex.getSubUtilSimple(currentLine, "(\\[lack of\\].*\\[before\\])").replace("[lack of]","").replace("[before]","");
            String shouldNotInvokeStr = Regex.getSubUtilSimple(currentLine, "(\\[Should not invoke\\].*\\[before\\])").replace("[Should not invoke]","").replace("[before]","");
            String shouldInvokeStr = Regex.getSubUtilSimple(currentLine, "(\\[Should invoke\\].*\\[after\\])").replace("[Should invoke]","").replace("[after]","");
            if(StringUtils.isNotBlank(lackBeforeStr)){
                apis.addAll(Regex.getSubUtilSimpleList(lackBeforeStr, "(<.*?>)"));
            }
            if(StringUtils.isNotBlank(shouldNotInvokeStr)){
                apis.addAll(Regex.getSubUtilSimpleList(shouldNotInvokeStr, "(<.*?>)"));
            }
            if(StringUtils.isNotBlank(shouldInvokeStr)){
                apis.addAll(Regex.getSubUtilSimpleList(shouldInvokeStr, "(<.*?>)"));
            }

            String misuseStr = Regex.getSubUtilSimple(currentLine, "(:.*; Origin apiCallOrderList is)").replace("; Origin apiCallOrderList is","");

            if(currentLine.contains("======ResourceLeak=======ICC")){
                resourceLeakICCNum ++;
                //existMisuse.add(misuseStr.toString());
                misuseAPIs.addAll(apis);
                for(String api : apis){
                    String className = Regex.getSubUtilSimple(api, "(<.*? )");
                    IncrementHashMap.incrementValue(misuseAPINum, api);
                    IncrementHashMap.incrementValue(misuseClassNameNum, className);
                }
                IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
            }
            if(currentLine.contains("======ResourceLeak========Component")){
                resourceLeakComponentNum ++;
                //existMisuse.add(misuseStr.toString());
                misuseAPIs.addAll(apis);
                for(String api : apis){
                    String className = Regex.getSubUtilSimple(api, "(<.*? )");
                    IncrementHashMap.incrementValue(misuseAPINum, api);
                    IncrementHashMap.incrementValue(misuseClassNameNum, className);
                }
                IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
            }
            if(currentLine.contains("======ResourceLeak=======Callback")){
                resourceLeakCallbackNum ++;
                //existMisuse.add(misuseStr.toString());
                misuseAPIs.addAll(apis);
                for(String api : apis){
                    String className = Regex.getSubUtilSimple(api, "(<.*? )");
                    IncrementHashMap.incrementValue(misuseAPINum, api);
                    IncrementHashMap.incrementValue(misuseClassNameNum, className);
                }
                IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
            }
            if(currentLine.contains("======ResourceLeak=======method")){
                resourceLeakMethodNum ++;
                //existMisuse.add(misuseStr.toString());
                misuseAPIs.addAll(apis);
                for(String api : apis){
                    String className = Regex.getSubUtilSimple(api, "(<.*? )");
                    IncrementHashMap.incrementValue(misuseAPINum, api);
                    IncrementHashMap.incrementValue(misuseClassNameNum, className);
                }
                IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
            }

        }

        System.out.println("resourceLeakMethodNum:"+resourceLeakMethodNum);
        System.out.println("resourceLeakComponentNum:"+resourceLeakComponentNum);
        System.out.println("resourceLeakICCNum:"+resourceLeakICCNum);
        System.out.println("resourceLeakCallbackNum:"+resourceLeakCallbackNum);
        System.out.println("misuseAPIs Num:"+misuseAPIs.size());
        System.out.println("=============misuse APIs=============");
        Map<String, Integer> sortedMisuseAPIs = IncrementHashMap.sortByValue(misuseAPINum);
        for(String misuseAPIStr : sortedMisuseAPIs.keySet()){
            System.out.println(misuseAPIStr + "----"+ sortedMisuseAPIs.get(misuseAPIStr));
        }
        System.out.println("=============misuse classes=============");
        Map<String, Integer> sortedMisuseCls = IncrementHashMap.sortByValue(misuseClassNameNum);
        for(String misuseAPIStr : sortedMisuseCls.keySet()){
            System.out.println(misuseAPIStr + "----"+ sortedMisuseCls.get(misuseAPIStr));
        }
        System.out.println("=============misuseAPIPairs=============");
        Map<String, Integer> sortedHashMap = IncrementHashMap.sortByValue(misuseAPIPairs);
        for(String misuseAPIStr : sortedHashMap.keySet()){
            System.out.println(misuseAPIStr + "----"+ sortedHashMap.get(misuseAPIStr));
        }
    }

}
