package typestate.RQ2;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeStateMisuses {

    public static void main(String[] args) throws IOException {
        //String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/TypeState_RQ2/output";
        String resFile = "/home/xsun0035/rm46_scratch/TypeStateRQ2_output";
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
            //Set<String> existMisuse = new HashSet<>();
            boolean success = false;
            File theFile = new File(resFile+"/"+fileName);
            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            try {
                while (it.hasNext()) {
                    String currentLine = it.nextLine();
                    Set<String> apis = new LinkedHashSet<String>(Regex.getSubUtilSimpleList(currentLine, "(<.*?>)"));
                    StringBuilder misuseStr = new StringBuilder();
                    for(String api : apis){
                        misuseStr.append(api);
                    }
                    if(belong2SameClass(apis) && currentLine.contains("======Misuse=======Component")){
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
            } finally {
                LineIterator.closeQuietly(it);
            }
            if(success){
                successNum ++;
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
