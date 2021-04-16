package FieldFlowDroid;

import HSO.RQ1.TriggerNum;
import org.apache.commons.collections4.CollectionUtils;
import util.Regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class collectFieldLeak {

    private final static String fieldRegex = "<(.+):\\s*(.+)\\s+([a-zA-Z_$][a-zA-Z_$0-9]*)\\s*>\\s*(.*?)(\\s+->\\s+(.*))?$";

    public static void main(String[] args) throws IOException {
        String logFilePath = args[0];

        List<String> txtFileNames = new ArrayList<>();

        TriggerNum.getFileList(logFilePath, txtFileNames);

        Pattern fieldPattern = Pattern.compile(fieldRegex);

        Set<String> dupliatedLeaks = new HashSet<>();
        Set<String> fileNames = new HashSet<>();
        Set<String> allSortOfSensorTypes = new HashSet<>();
        HashMap<String, Integer> sensorTypeMap = new HashMap<>();

        txtFileNames.forEach(txtFileName -> {
            try {
                File filePath = new File(logFilePath + "/" + txtFileName + ".txt");
                BufferedReader br = null;
                String line = "";
                br = new BufferedReader(new FileReader(filePath));

                //output all field-related leaks
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("[SOURCE]")) {
                        //System.out.println("[FlieName]"+txtFileName+";"+line);
                        Matcher fieldMatch = fieldPattern.matcher(line);
                        String source = Regex.getSubUtilSimple(line, "(<.*>)");
                        if(fieldMatch.find() && source.startsWith("<android.hardware.")){
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
                        }
                    }else if(line.startsWith("[Sensor Type]")){
                        List<String> sensorTypes = Arrays.asList(line.replace("[Sensor Type]", "").replaceAll("\\[","").replaceAll("]","").split(","));
                        allSortOfSensorTypes.addAll(sensorTypes);
                        if(CollectionUtils.isNotEmpty(sensorTypes)){
                            for(String s : sensorTypes){
                                if(sensorTypeMap.containsKey(s)){
                                    sensorTypeMap.replace(s, sensorTypeMap.get(s) +1);
                                }else{
                                    sensorTypeMap.put(s, 1);
                                }
                            }
                        }

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        for(String leak : dupliatedLeaks){
//            System.out.println(leak);
//        }
//        for(String file : fileNames){
//            System.out.println(file);
//        }
        System.out.println(allSortOfSensorTypes);
        System.out.println(sensorTypeMap);
    }

}
