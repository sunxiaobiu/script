package DroidRA;

import json.CompareJson;
import json.CompareEnum;
import json.DroidRAKey;
import model.CompareResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResultAnalysis {

    public static void main(String[] args) {
        String originOutputPath = args[0];
        String outputPath = args[1];

        int equalNum = 0;
        int decreasingNum = 0;
        int increasingNum = 0;
        int bothIncDecNum = 0;
        int bothIncNum = 0;
        int bothDecNum = 0;
        int noneNum = 0;
        int increasingAndNotResolvedNum = 0;

        try {
            Stream<Path> paths = Files.walk(Paths.get(originOutputPath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            for(String file : result){
                File currentFile = new File(outputPath + "/" + file);
                if(!file.endsWith(".json")){
                    continue;
                }
                if(null != currentFile && currentFile.getName().endsWith(".json")){
                    String[] compareArgs = new String[2];
                    compareArgs[0] = originOutputPath + "/" + file;
                    compareArgs[1] = outputPath + "/" + currentFile.getName();

                    System.out.println(compareArgs[0]);

                    CompareResult compareResult = CompareJson.run(compareArgs);
                    JSONArray actualLeftJson = compareResult.getActualLeftJson();
                    JSONArray actualRightJson = compareResult.getActualRightJson();

                    HashSet<DroidRAKey> missingDroidRAKey = compareResult.getMissingDroidRAKey();
                    HashSet<DroidRAKey> newDroidRAKey = compareResult.getNewDroidRAKey();

                    if(compareResult.getCompareEnum() == CompareEnum.EQUAL){
                        equalNum = equalNum + actualLeftJson.size();
                    }
                    if(compareResult.getCompareEnum() == CompareEnum.DECREASING){
                        decreasingNum = decreasingNum + missingDroidRAKey.size();
                    }
                    if(compareResult.getCompareEnum() == CompareEnum.INCREASING){
                        increasingNum = increasingNum + newDroidRAKey.size();

                        for(DroidRAKey droidRAKey : newDroidRAKey){
                            increasingAndNotResolvedNum += actualRightJson.stream().filter(item ->
                                    item.toString().contains(droidRAKey.className)
                                    && item.toString().contains(droidRAKey.methodSignature)
                                    && item.toString().contains(droidRAKey.type)
                                    && item.toString().contains("(.*)")
                            ).count();
                        }
                    }
                    if(compareResult.getCompareEnum() == CompareEnum.BOTH_DEC_INC){
                        bothIncDecNum = bothIncDecNum + 1;
                        bothIncNum = bothIncNum + newDroidRAKey.size();
                        bothDecNum = bothDecNum + missingDroidRAKey.size();

                        for(DroidRAKey droidRAKey : newDroidRAKey){
                            increasingAndNotResolvedNum += actualRightJson.stream().filter(item ->
                                    item.toString().contains(droidRAKey.className)
                                            && item.toString().contains(droidRAKey.methodSignature)
                                            && item.toString().contains(droidRAKey.type)
                                            && item.toString().contains("(.*)")).count();
                        }
                    }
                    if(compareResult.getCompareEnum() == CompareEnum.NONE){
                        System.out.println("none:"+compareArgs[0]+";"+compareArgs[1]);
                        noneNum = noneNum + 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("EQUAL:"+equalNum +", DECREASING:"+ decreasingNum+", INCREASING:"+increasingNum+
                ", BOTH_DEC_INC:"+bothIncDecNum+", BOTH_INC_NUM:"+bothIncNum+", BOTH_DEC_NUM:"+bothDecNum+", NONE:"+noneNum+
                ", increasingAndNotResolvedNum:"+increasingAndNotResolvedNum);

    }
}
