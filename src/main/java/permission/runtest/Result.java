package permission.runtest;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Result {

    public static void main(String[] args) throws IOException {
        String resFilePath = "/Users/xsun0035/Desktop/permission_runtest/";

        HashMap<String, String> log21 = new HashMap<String, String>();
        HashMap<String, String> log22 = new HashMap<String, String>();
        HashMap<String, String> log23 = new HashMap<String, String>();
        HashMap<String, String> log24 = new HashMap<String, String>();
        HashMap<String, String> log25 = new HashMap<String, String>();
        HashMap<String, String> log26 = new HashMap<String, String>();
        HashMap<String, String> log27 = new HashMap<String, String>();
        HashMap<String, String> log28 = new HashMap<String, String>();
        HashMap<String, String> log29 = new HashMap<String, String>();
        HashMap<String, String> log30 = new HashMap<String, String>();

        List<String> logFileNames = new ArrayList<>();

        collectRes(resFilePath+"output21/", log21, logFileNames);
        collectRes(resFilePath+"output22/", log22, logFileNames);
        collectRes(resFilePath+"output23/", log23, logFileNames);
        collectRes(resFilePath+"output24/", log24, logFileNames);
        collectRes(resFilePath+"output25/", log25, logFileNames);
        collectRes(resFilePath+"output26/", log26, logFileNames);
        collectRes(resFilePath+"output27/", log27, logFileNames);
        collectRes(resFilePath+"output28/", log28, logFileNames);
        collectRes(resFilePath+"output29/", log29, logFileNames);
        collectRes(resFilePath+"output30/", log30, logFileNames);

//
//        for(String key : log21.keySet()){
//            System.out.println(key+"========"+log21.get(key));
//        }

        for (String key : log30.keySet()) {
            HashSet<String> resSet = new HashSet<>();
            resSet.add(log30.get(key)+"(30)");

            if(!resSet.contains(log29.get(key))){
                resSet.add(log29.get(key)+"(29)");
            }

            if(!resSet.contains(log28.get(key))){
                resSet.add(log28.get(key)+"(28)");
            }

            if(!resSet.contains(log27.get(key))){
                resSet.add(log27.get(key)+"(27)");
            }

            if(!resSet.contains(log26.get(key))){
                resSet.add(log26.get(key)+"(26)");
            }

            if(!resSet.contains(log25.get(key))){
                resSet.add(log25.get(key)+"(25)");
            }

            if(!resSet.contains(log24.get(key))){
                resSet.add(log24.get(key)+"(24)");
            }

            if(!resSet.contains(log23.get(key))){
                resSet.add(log23.get(key)+"(23)");
            }

            if(!resSet.contains(log22.get(key))){
                resSet.add(log22.get(key)+"(22)");
            }

            if(!resSet.contains(log21.get(key))){
                resSet.add(log21.get(key)+"(21)");
            }

            if(resSet.size() > 1 &&
                    (resSet.contains("java.lang.SecurityException:(21)")) ||  (resSet.contains("java.lang.SecurityException:(22)")) ||  (resSet.contains("java.lang.SecurityException:(23)")) ||  (resSet.contains("java.lang.SecurityException:(24)")) ||  (resSet.contains("java.lang.SecurityException:(25)")) ||  (resSet.contains("java.lang.SecurityException:(26)")) ||  (resSet.contains("java.lang.SecurityException:(27)")) ||  (resSet.contains("java.lang.SecurityException:(28)")) ||  (resSet.contains("java.lang.SecurityException:(29)")) ||  (resSet.contains("java.lang.SecurityException:(30)"))
                    ){
                System.out.println(key + "--------"+ resSet);
            }
        }
    }

    private static void collectRes(String resFilePath, HashMap<String, String> log21, List<String> logFileNames) throws IOException {
        getFileList(resFilePath, logFileNames);

        for(String logFile : logFileNames) {
            File file = new File(resFilePath + logFile);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            boolean isSuccess = false;
            String exceptionContent = "";
            for (int i = 0; i < fileContent.size(); i++) {
                String currentLine = fileContent.get(i);
                if(currentLine.startsWith("INSTRUMENTATION_STATUS_CODE:")){
                    isSuccess = currentLine.replace("INSTRUMENTATION_STATUS_CODE: ", "").equals("0");
                }
                if(currentLine.startsWith("java.lang.")){
                    exceptionContent = Regex.getSubUtilSimple(currentLine, "(java.lang..+?:)");
                }
                if(currentLine.contains("Failed to grant permissions")){
                    exceptionContent = Regex.getSubUtilSimple(currentLine, "(junit.framework..+?,)");
                }
            }

            if(isSuccess){
                exceptionContent = "success";
            }
            log21.put(logFile.replace(".txt", ""), exceptionContent);
        }
    }

    public static void getFileList(String resFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(resFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });
    }
}
