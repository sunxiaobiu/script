package TriggerScope;

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

public class result {

    public static void main(String[] args) throws IOException {
        String resFilePath = args[0];

        List<String> logFileNames = new ArrayList<>();
        getFileList(resFilePath, logFileNames);
        int totalNum = 0;
        Set<String> appNames = new HashSet<>();
        Set<String> smsMethodNames = new HashSet<>();
        Set<String> suspiciousStrList = new HashSet<>();
        HashMap<String, Integer> appNameAPINumber = new HashMap<>();
        HashMap<String, Integer> sensitiveAPINumber = new HashMap<>();

        Set<String> sensitiveAPIs = new HashSet<>();
        sensitiveAPIs.add("<android.widget.TextView: void setText(java.lang.CharSequence)>");
        sensitiveAPIs.add("<android.os.Handler: boolean sendEmptyMessage(int)>");
        sensitiveAPIs.add("<android.content.BroadcastReceiver: void abortBroadcast()>");
        sensitiveAPIs.add("<android.content.ContextWrapper: void startActivity(android.content.Intent)>");
        sensitiveAPIs.add("<java.io.File: boolean mkdirs()>");
        sensitiveAPIs.add("<java.io.File: void <init>(java.io.File,java.lang.String)>");
        sensitiveAPIs.add("<android.app.Activity: void finish()>");
        sensitiveAPIs.add("<java.text.DecimalFormat: java.text.DecimalFormatSymbols getDecimalFormatSymbols()>");
        sensitiveAPIs.add("<java.util.UUID: long getLeastSignificantBits()>");

        int time = 0;
        int location = 0;
        int sms = 0;


        for (String txtFileName : logFileNames) {
            try {
                File file = new File(resFilePath + txtFileName);
                List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
                if (fileContent.size() > 1 && fileContent.get(1).startsWith("%")) {
                    List<String> line = Arrays.asList(fileContent.get(0).split(","));
//                    if (!appNames.contains(line.get(1))) {
//                        appNames.add(line.get(1));

                    String suspiciousStr = "";
                    for (int i = 1; i < fileContent.size(); i++) {
                        List<String> thisline = Arrays.asList(fileContent.get(i).split(";"));

                        suspiciousStr = Regex.getSubUtilSimple(fileContent.get(i), "(\\(\\#.*\\))");
//                                if ((fileContent.get(i).contains("(#Suspicious)") && (fileContent.get(i).contains("equals") || fileContent.get(i).contains("startsWith") || fileContent.get(i).contains("contains") || fileContent.get(i).contains("cmp") || fileContent.get(i).contains("after") || fileContent.get(i).contains("before")))
//                                        || fileContent.get(i).contains("(#here"))
                        if(sensitiveAPIs.contains(thisline.get(3))){
                            continue;
                        }
                        if (fileContent.get(i).contains("(#Suspicious)")
                                || fileContent.get(i).contains("(#here")) {
                            suspiciousStrList.add(suspiciousStr);
                            appNames.add(txtFileName);
                            //System.out.println(txtFileName);
                            if (fileContent.get(i).contains("#now")) {
                                time++;
                                totalNum++;
                                IncrementHashMap.incrementValue(appNameAPINumber, txtFileName);
                                IncrementHashMap.incrementValue(sensitiveAPINumber, thisline.get(3));
                            }
                            else if (fileContent.get(i).contains("#sms")) {
                                if(!smsMethodNames.contains(thisline.get(1)+thisline.get(2))){
                                    smsMethodNames.add(thisline.get(1)+thisline.get(2));
                                    sms++;
                                    totalNum++;
                                    IncrementHashMap.incrementValue(appNameAPINumber, txtFileName);
                                    IncrementHashMap.incrementValue(sensitiveAPINumber, thisline.get(3));
                                }
                            }
                            else if (fileContent.get(i).contains("#here")) {
                                location++;
                                totalNum++;
                                IncrementHashMap.incrementValue(appNameAPINumber, txtFileName);
                                IncrementHashMap.incrementValue(sensitiveAPINumber, thisline.get(3));
                            }else{
                                appNames.remove(txtFileName);
                            }

                        }
                    }


//                        //suspiciousStrList.add(suspiciousStr);
//                        if (!suspiciousStr.equals("(#Suspicious)") && !suspiciousStr.equals("(#now)")) {
//                            //System.out.println(txtFileName);
//                            totalNum = totalNum + Integer.valueOf(line.get(6));
//                        }
//                    }
                }

//                for(int i=1; i<fileContent.size();i++){
//                    List<String> line = Arrays.asList(fileContent.get(i).split(";"));
//                    sensitiveAPIs.add(line.get(3));
//                }
            } catch (Exception e) {
                //System.out.println(txtFileName);
                continue;
            }
        }


        System.out.println(totalNum);

        for (String s : suspiciousStrList) {
            System.out.println(s);
        }

        System.out.println("total app number:" + appNames.size());
        System.out.println("time number:" + time);
        System.out.println("location number:" + location);
        System.out.println("sms number:" + sms);
        System.out.println("sms number(duplicate):" + smsMethodNames.size());

        System.out.println("==============app name===============");
        for (String s : appNameAPINumber.keySet()) {
            System.out.println(s + ";"+ appNameAPINumber.get(s));
        }

        System.out.println("==============Sensitive API===============");
        for (String sensitiveAPI : sensitiveAPINumber.keySet()) {
            System.out.println(sensitiveAPI + ";"+ sensitiveAPINumber.get(sensitiveAPI));
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
