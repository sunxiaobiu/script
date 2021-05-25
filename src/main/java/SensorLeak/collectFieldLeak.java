package SensorLeak;

import HSO.RQ1.TriggerNum;
import org.apache.commons.collections4.CollectionUtils;
import util.Regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class collectFieldLeak {

    private final static String fieldRegex = "<(.+):\\s*(.+)\\s+([a-zA-Z_$][a-zA-Z_$0-9]*)\\s*>\\s*(.*?)(\\s+->\\s+(.*))?$";
    private final static String APIRegex = "<(.+):\\s*(.+)\\s+(.+)\\s*\\((.*)\\)>\\s*(.*?)(\\s+->\\s+(.*))?$";

    public static void main(String[] args) throws IOException {
        String logFilePath = args[0];
        String malware_name_file = args[1];
        File malwareFile = new File(malware_name_file);
        List<String> malwareFileContent = new ArrayList<>(Files.readAllLines(malwareFile.toPath(), StandardCharsets.UTF_8));

        List<String> txtFileNames = new ArrayList<>();

        TriggerNum.getFileList(logFilePath, txtFileNames);

        Pattern fieldPattern = Pattern.compile(fieldRegex);
        Pattern apiPattern = Pattern.compile(APIRegex);

        Set<String> dupliatedLeaks = new HashSet<>();
        Set<String> fileNames = new HashSet<>();
        Set<String> allSortOfSensorTypes = new HashSet<>();
        HashMap<String, Integer> sensorTypeMap = new HashMap<>();
        HashMap<String, Integer> sensorAPIMap = new HashMap<>();

        AtomicInteger leakTotalNum= new AtomicInteger();
        malwareFileContent.forEach(txtFileName -> {

                int fileSensorLeakNum = 0;
                int fileSensorFieldLeakNum = 0;
                try {
                    File filePath = new File(logFilePath + "/" + txtFileName + ".txt");
                    BufferedReader br = null;
                    String line = "";
                    br = new BufferedReader(new FileReader(filePath));

                    //output all field-related leaks
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith("[SOURCE]")) {
                            //Matcher fieldMatch = fieldPattern.matcher(line);
                            Matcher apiMatch = apiPattern.matcher(line);
                            String source = Regex.getSubUtilSimple(line, "(<.*>)");
                            if (
                                //fieldMatch.find() &&
                                //apiMatch.find() &&
                                    source.startsWith("<android.hardware")) {
                                fileSensorLeakNum++;
                                leakTotalNum.getAndIncrement();
                                if (sensorAPIMap.containsKey(source)) {
                                    sensorAPIMap.replace(source, sensorAPIMap.get(source) + 1);
                                } else {
                                    sensorAPIMap.put(source, 1);
                                }
                                /**
                                 * output all field-related leaks
                                 */
                                //System.out.println("[FlieName]"+txtFileName+";"+line+";"+br.readLine());
                                /**
                                 * output duplicated field-related leaks
                                 */
                                //dupliatedLeaks.add(source);
                                /**
                                 * output duplicated filenames
                                 */
                                //fileNames.add(txtFileName+".apk");
                                //System.out.println("[FlieName]" + txtFileName + ";" + line + ";" + br.readLine());
                            }


                        } else if (line.startsWith("[Sensor Type]")) {
                            fileSensorFieldLeakNum++;
                            List<String> sensorTypes = Arrays.asList(line.replace("[Sensor Type]", "").replaceAll("\\[", "").replaceAll("]", "").split(","));

                            //System.out.println("[FlieName]" + txtFileName + ";" + line + ";" + br.readLine());

                            allSortOfSensorTypes.addAll(sensorTypes);
                            if (CollectionUtils.isNotEmpty(sensorTypes)) {
                                for (String s : sensorTypes) {
                                    if (sensorTypeMap.containsKey(s)) {
                                        sensorTypeMap.replace(s, sensorTypeMap.get(s) + 1);
                                    } else {
                                        sensorTypeMap.put(s, 1);
                                    }
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

//            if(fileSensorLeakNum != 0){
//                System.out.println(fileSensorLeakNum);
//            }
//            if(fileSensorLeakNum - fileSensorFieldLeakNum != 0){
//                System.out.println(fileSensorLeakNum - fileSensorFieldLeakNum);
//            }
//
//                if (fileSensorFieldLeakNum != 0) {
//                    System.out.println(fileSensorFieldLeakNum);
//                }
        });

//        for(String leak : dupliatedLeaks){
//            System.out.println(leak);
//        }
//        for(String file : fileNames){
//            System.out.println(file);
//        }
//        System.out.println(allSortOfSensorTypes);
//        System.out.println(sensorTypeMap);
        System.out.println("leakTotalNum:"+leakTotalNum);
        System.out.println("=========================="+"Method"+"==========================");
        for(String apiSource : sensorAPIMap.keySet()){
            System.out.println(apiSource +":"+ sensorAPIMap.get(apiSource));
        }
        System.out.println("=========================="+"Field"+"==========================");
        System.out.println(allSortOfSensorTypes);
        System.out.println(sensorTypeMap);
    }

}
