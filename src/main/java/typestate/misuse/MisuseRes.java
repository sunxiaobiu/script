package typestate.misuse;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MisuseRes {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/TypeState_goodware_webrtc/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        int misuseMethodNum = 0;
        int misuseComponentNum = 0;
        int misuseCallbackNum = 0;
        int successNum = 0;
        Set<String> misuseAPIs = new HashSet<>();

        for(String fileName : logFileNames){
            Set<String> existMisuse = new HashSet<>();
            boolean success = false;
            File file = new File(resFile+"/"+fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String currentLine = fileContent.get(i);
                List<String> apis = Regex.getSubUtilSimpleList(currentLine, "(<.*?>)");
                StringBuilder misuseStr = new StringBuilder();
                for(String api : apis){
                    misuseStr.append(api);
                }
                if(!existMisuse.contains(misuseStr.toString()) && currentLine.contains("======Misuse=======method") && apis.size() >=2){
                    misuseMethodNum ++;
                    existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                }
                if(!existMisuse.contains(misuseStr.toString()) && currentLine.contains("======Misuse=======Component")&& apis.size() >=2){
                    misuseComponentNum ++;
                    existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                }
                if(!existMisuse.contains(misuseStr.toString()) && currentLine.contains("======Misuse=======Callback")&& apis.size() >=2){
                    misuseCallbackNum ++;
                    existMisuse.add(misuseStr.toString());
                    misuseAPIs.addAll(apis);
                }

                if(currentLine.contains("==>after detectMisusePatterns")){
                    success = true;
                }
            }
            if(success){
                successNum ++;
            }
        }

        System.out.println("successNum:"+successNum);
        System.out.println("misuseMethodNum:"+misuseMethodNum);
        System.out.println("misuseComponentNum:"+misuseComponentNum);
        System.out.println("misuseCallbackNum:"+misuseCallbackNum);
        for(String s : misuseAPIs){
            System.out.println(s);
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
