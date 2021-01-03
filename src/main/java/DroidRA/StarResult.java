package DroidRA;

import com.fasterxml.jackson.databind.ObjectMapper;
import json.DroidRAConstants;
import model.ClsSet;
import model.ParamSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.ApplicationClassFilter;
import util.ReadJsonFromFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StarResult {


    public static void main(String[] args) {
        try {
            String originOutputPath = args[0];

            Stream<Path> paths = null;

            paths = Files.walk(Paths.get(originOutputPath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            int inaccurateNum = 0;
            int optimized = 0;
            int starSymbol = 0;

            //Optimized
            int fieldCallOptimized = 0;
            int methodCallOptimized = 0;

            //star symbol
            int fieldCallStar = 0;
            int methodCallStar = 0;

            int reachedReflectionNum = 0;
            int resolvedReflectionNum = 0;

            int paramUnresolvedNum = 0;

            for(String file : result) {

                if (!file.endsWith(".json")) {
                    continue;
                }

                String filePath = originOutputPath + "/" + file;
                JSONObject jsonObject = ReadJsonFromFile.trigger(filePath);

                JSONArray jsonObjectArray = (JSONArray) jsonObject.get(DroidRAConstants.ITEMS);

                Iterator iter = jsonObjectArray.iterator();
                while (iter.hasNext()){
                    boolean fieldCallOptimizedFlag = false;
                    boolean methodCallOptimizedFlag = false;
                    boolean fieldCallStarFlag = false;
                    boolean methodCallStarFlag = false;

                    reachedReflectionNum ++;
                    JSONObject value = (JSONObject)iter.next();

//                    if(!ApplicationClassFilter.isApplicationClass(value.get(DroidRAConstants.CLASS_NAME).toString())){
//                        continue;
//                    }

                    ObjectMapper mapper = new ObjectMapper();
                    ClsSet[] clsSets = mapper.readValue(value.get(DroidRAConstants.CLS_SET).toString(), ClsSet[].class);
                    List<String> paramSets = mapper.readValue(value.get(DroidRAConstants.PARAM_SET).toString(), List.class);
                    String type = value.get(DroidRAConstants.TYPE).toString();

                    for(String param: paramSets){
                        if(param.equals("(.*)")){
                            paramUnresolvedNum ++;
                        }
                    }

                    boolean resolvedFlag = false;
                    for(ClsSet clset : clsSets){
                        if(clset.getCls().contains("[OPTIMIZED]")){
                            if(type.equals("FIELD_CALL")){
                                fieldCallOptimizedFlag = true;
                            }else if(type.equals("METHOD_CALL")){
                                methodCallOptimizedFlag = true;
                            }
                            optimized ++;
                        }
                        if(clset.getCls().contains("(.*)")){
                            if(type.equals("FIELD_CALL")){
                                fieldCallStarFlag = true;
                            }else if(type.equals("METHOD_CALL")){
                                methodCallStarFlag = true;
                            }
                            starSymbol ++;
                        }

                        if(!clset.getCls().equals("(.*)")){
                            resolvedFlag = true;
                        }
                    }

                    if(fieldCallOptimizedFlag){
                        fieldCallOptimized ++;
                    }
                    if(methodCallOptimizedFlag){
                        methodCallOptimized ++;
                    }
                    if(fieldCallStarFlag){
                        fieldCallStar ++;
                    }
                    if(methodCallStarFlag){
                        methodCallStar ++;
                    }
                    if(clsSets.length == 0){
                        resolvedFlag = true;
                    }

                    if(resolvedFlag){
                        resolvedReflectionNum ++;
                    }

                }
            }
//            System.out.println("reachedReflectionNum:"+reachedReflectionNum+"resolvedReflectionNum:"+resolvedReflectionNum +", OPTIMIZED REFLECTION NUM:"+ optimized+", STAR NUM:"+ starSymbol+
//                    ",fieldCallOptimized NUM:"+ fieldCallOptimized +
//                    ",methodCallOptimized NUM:"+ methodCallOptimized +
//                    ",fieldCallStar NUM:"+ fieldCallStar +
//                    ",methodCallStar NUM:"+ methodCallStar
//            );
            System.out.println("paramUnresolvedNum:"+paramUnresolvedNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
