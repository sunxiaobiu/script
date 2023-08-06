package typestate.RQ2;

import org.apache.commons.collections4.CollectionUtils;
import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterMisuseAPKNames {

    public static void main(String[] args) throws IOException {
        //String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/TypeState_RQ2/output";
        String resFile = "/Users/xsun0035/Desktop/TypeState_RQ2/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        int misuseMethodNum = 0;
        int misuseComponentNum = 0;
        int misuseCallbackNum = 0;
        int successNum = 0;
        Set<String> misuseAPIs = new HashSet<>();
        HashMap<String, Integer> misuseAPIPairs = new HashMap<>();
        HashMap<String, Integer> misuseAPINum = new HashMap<>();
        HashMap<String, Integer> misuseClassNameNum = new HashMap<>();

        for(String fileName : logFileNames){
            boolean containsMiuse = false;
            boolean success = false;
            File file = new File(resFile+"/"+fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String currentLine = fileContent.get(i);
                Set<String> apis = new LinkedHashSet<String>(Regex.getSubUtilSimpleList(currentLine, "(<.*?>)"));
                StringBuilder misuseStr = new StringBuilder();
                for(String api : apis){
                    misuseStr.append(api);
                }
                if(belong2SameClass(apis) && currentLine.contains("======Misuse=======Component")){
                    containsMiuse = true;
                    misuseComponentNum ++;
                    //existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                    for(String api : apis){
                        String className = Regex.getSubUtilSimple(api, "(<.*?:)");
                        IncrementHashMap.incrementValue(misuseAPINum, api);
                        IncrementHashMap.incrementValue(misuseClassNameNum, className);
                    }
                    IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
                }
                if(belong2SameClass(apis) && currentLine.contains("======Misuse=======Callback")){
                    containsMiuse = true;
                    misuseCallbackNum ++;
                    //existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                    for(String api : apis){
                        String className = Regex.getSubUtilSimple(api, "(<.*?:)");
                        IncrementHashMap.incrementValue(misuseAPINum, api);
                        IncrementHashMap.incrementValue(misuseClassNameNum, className);
                    }
                    IncrementHashMap.incrementValue(misuseAPIPairs, misuseStr.toString());
                }
                if(belong2SameClass(apis) && currentLine.contains("======Misuse=======method")){
                    containsMiuse = true;
                    misuseMethodNum ++;
                    //existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                    for(String api : apis){
                        String className = Regex.getSubUtilSimple(api, "(<.*?:)");
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
            }
            if(containsMiuse){
                System.out.println(fileName.replace(".txt", ".apk"));
            }
        }

        System.out.println("successNum:"+successNum);
        System.out.println("misuseMethodNum:"+misuseMethodNum);
        System.out.println("misuseComponentNum:"+misuseComponentNum);
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

    private static boolean containsAPIOrder(Set<String> existMisuses, String misuse){
        if(CollectionUtils.isEmpty(existMisuses)){
            return false;
        }
        for(String existMisuse : existMisuses){
            if(misuse.contains(existMisuse)){
                return true;
            }
        }
        return false;
    }

    private static boolean belong2SameClass(Set<String> apis){
        if(CollectionUtils.isEmpty(apis)){
            return false;
        }
        Set<String> className = new HashSet<>();
        for(String api : apis){
            className.add(Regex.getSubUtilSimple(api, "(<.*?:)"));
        }
        return className.size() == 1;
    }

//    private static List<String> getMisuseLines(List<String> fileContent){
//        List<String> misuseLines = new ArrayList<>();
//        HashMap<String, Integer> misuseHashmap = new HashMap<>();
//        for (int i = 0; i < fileContent.size(); i++) {
//            String currentLine = fileContent.get(i);
//            if(currentLine.contains("======Misuse=======")){
//                misuseHashmap.put(currentLine, currentLine.length());
//            }
//        }
//        Map<String, Integer> sortedHashMap = IncrementHashMap.sortByOrder(misuseHashmap);
//        for(String s : sortedHashMap.keySet()){
//            misuseLines.add(s);
//        }
//        return misuseLines;
//    }
}
