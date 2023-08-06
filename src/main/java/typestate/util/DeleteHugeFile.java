package typestate.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeleteHugeFile {

    public static void main(String[] args) throws IOException {
        String resFile = "/home/xsun0035/rm46/xiaoyu/TypeState_RQ2/output";
        String resFile2 = "/home/xsun0035/rm46_scratch/TypeStateRQ2_output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);
        getFileList(resFile2, logFileNames);

        for(String fileName : logFileNames){
            //Set<String> existMisuse = new HashSet<>();
            boolean success = false;
            File theFile = new File(resFile+"/"+fileName);
            long fileSizeInBytes = theFile.length();
            long fileSizeInKB = fileSizeInBytes / 1024;
            long fileSizeInMB = fileSizeInKB / 1024;

            if(fileSizeInMB < 1024){
                continue;
            }
            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            try {
                while (it.hasNext()) {
                    String currentLine = it.nextLine();
                    if(currentLine.contains("==>after detectMisusePatterns")){
                        success = true;
                        break;
                    }
                }
            } finally {
                LineIterator.closeQuietly(it);
            }
            if(!success){
                System.out.println(fileName);
            }
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
