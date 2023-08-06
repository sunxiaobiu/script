package typestate.icse.RQ1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeStateMisuses {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ1_TypeStateMisuse/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        int misuseMethodNum = 0;
        int misuseComponentNum = 0;
        int misuseICCNum = 0;
        int misuseCallbackNum = 0;
        int successNum = 0;

        Set<String> misuseAPIs = new HashSet<>();
        HashMap<String, Integer> misuseAPIPairs = new HashMap<>();
        HashMap<String, Integer> misuseAPINum = new HashMap<>();
        HashMap<String, Integer> misuseClassNameNum = new HashMap<>();

        for (String fileName : logFileNames) {
            //Set<String> existMisuse = new HashSet<>();
            boolean success = false;
            File theFile = new File(resFile + "/" + fileName);
            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            while (it.hasNext()) {
                String currentLine = it.nextLine();
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

                if(currentLine.contains("======Misuse=======ICC")){
                    misuseICCNum ++;
                    //existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                    for(String api : apis){
                        String className = Regex.getSubUtilSimple(api, "(<.*? )");
                        IncrementHashMap.incrementValue(misuseAPINum, api);
                        IncrementHashMap.incrementValue(misuseClassNameNum, className);
                    }
                    IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
                }
                if(currentLine.contains("======Misuse=======Component")){
                    misuseComponentNum ++;
                    //existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                    for(String api : apis){
                        String className = Regex.getSubUtilSimple(api, "(<.*? )");
                        IncrementHashMap.incrementValue(misuseAPINum, api);
                        IncrementHashMap.incrementValue(misuseClassNameNum, className);
                    }
                    IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
                }
                if(currentLine.contains("======Misuse=======Callback")){
                    misuseCallbackNum ++;
                    //existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                    for(String api : apis){
                        String className = Regex.getSubUtilSimple(api, "(<.*? )");
                        IncrementHashMap.incrementValue(misuseAPINum, api);
                        IncrementHashMap.incrementValue(misuseClassNameNum, className);
                    }
                    IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
                }
                if(currentLine.contains("======Misuse=======method")){
                    misuseMethodNum ++;
                    //existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                    for(String api : apis){
                        String className = Regex.getSubUtilSimple(api, "(<.*? )");
                        IncrementHashMap.incrementValue(misuseAPINum, api);
                        IncrementHashMap.incrementValue(misuseClassNameNum, className);
                    }
                    IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
                }

                if(currentLine.contains("==>after detectMisusePatterns")){
                    success = true;
                }
            }
            if(success){
                successNum ++;
            }else {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/Rerun.txt", true)));
                out.println(fileName.replace(".txt", ".apk"));
                out.close();
            }
        }

        System.out.println("successNum:"+successNum);
        System.out.println("misuseMethodNum:"+misuseMethodNum);
        System.out.println("misuseComponentNum:"+misuseComponentNum);
        System.out.println("misuseICCNum:"+misuseICCNum);
        System.out.println("misuseCallbackNum:"+misuseCallbackNum);
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
}
