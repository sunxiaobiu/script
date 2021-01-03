package DroidRA;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import json.DroidRAConstants;
import json.DroidRAKey;
import model.ClsSet;
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

public class CompareSootResultAnalysis {

    public static void main(String[] args) {
        try {
            String originOutputPath = args[0];

            Stream<Path> paths = null;

            paths = Files.walk(Paths.get(originOutputPath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            int inaccurateNum = 0;
            int optimized = 0;
            int allReachedReflectionJsonNum = 0;
            int allResolvedReflectionJsonNum = 0;
            int starSymbol = 0;

            //Optimized
            int fieldCallOptimized = 0;
            int methodCallOptimized = 0;

            //star symbol
            int fieldCallStar = 0;
            int methodCallStar = 0;

            for(String file : result) {

                int reachedReflectionNum = 0;
                int resolvedReflectionNum = 0;

                if (!file.endsWith(".json")) {
                    continue;
                }

                String filePath = originOutputPath + "/" + file;
                JSONObject jsonObject = ReadJsonFromFile.trigger(filePath);

                JSONArray jsonObjectArray = (JSONArray) jsonObject.get(DroidRAConstants.ITEMS);

                Iterator iter = jsonObjectArray.iterator();
                while (iter.hasNext()){
                    allReachedReflectionJsonNum ++;
                    reachedReflectionNum ++;
                    JSONObject value = (JSONObject)iter.next();

                    if(!ApplicationClassFilter.isApplicationClass(value.get(DroidRAConstants.CLASS_NAME).toString())){
                        continue;
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    ClsSet[] clsSets = mapper.readValue(value.get(DroidRAConstants.CLS_SET).toString(), ClsSet[].class);
                    String type = value.get(DroidRAConstants.TYPE).toString();

                    boolean resolvedFlag = false;
                    int methodCallOptimizedNumInClset = 0;
                    int fieldCallOptimizedNumInClset = 0;
                    boolean optimizedFlag = false;
                    for(ClsSet clset : clsSets){

                        methodCallOptimizedNumInClset = 0;
                        fieldCallOptimizedNumInClset = 0;

                        if(clset.getCls().contains("[INACCURATE]")){
                            inaccurateNum ++;
                        }
                        if(clset.getCls().contains("[OPTIMIZED]")){
                            if(type.equals("FIELD_CALL")){
                                fieldCallOptimized ++;
                                fieldCallOptimizedNumInClset ++;
                            }else if(type.equals("METHOD_CALL")){
                                methodCallOptimizedNumInClset ++;
                                methodCallOptimized ++;
                            }
                            optimized ++;
                            optimizedFlag = true;
                        }
                        if(clset.getCls().contains("(.*)")){
                            if(type.equals("FIELD_CALL")){
                                fieldCallStar ++;
                            }else if(type.equals("METHOD_CALL")){
                                if(clsSets.length <= 1){
                                    //System.out.println("star METHOD_CALL length <= 1:"+filePath);
                                }
                                //System.out.println("star METHOD_CALL :"+filePath);
                                methodCallStar ++;
                            }
                            starSymbol ++;
                        }

                        if(!clset.getCls().equals("(.*)")){
                            resolvedFlag = true;
                        }
                    }

//                    if(optimizedFlag){
//                        resolvedFlag = false;
//                    }
//                    if(fieldCallOptimizedNumInClset == 1){
//                        System.out.println("fieldCallOptimizedNumInClset:"+filePath);
//                    }
//                    if(methodCallOptimizedNumInClset == 1){
//                        System.out.println("methodCallOptimizedNumInClset:"+filePath);
//                    }
                    if(clsSets.length == 0){
                        resolvedFlag = true;
                    }

                    if(resolvedFlag){
                        resolvedReflectionNum ++;
                    }

                }
                System.out.println(reachedReflectionNum+","+resolvedReflectionNum);
                allResolvedReflectionJsonNum += resolvedReflectionNum;

            }
            System.out.println("allReachedReflectionJsonNum:"+allReachedReflectionJsonNum+"allResolvedReflectionJsonNum:"+allResolvedReflectionJsonNum+", INACCURATE NUM:"+inaccurateNum +", OPTIMIZED NUM:"+ optimized+", STAR NUM:"+ starSymbol+
                    ",fieldCallOptimized NUM:"+ fieldCallOptimized +
                    ",methodCallOptimized NUM:"+ methodCallOptimized +
                    ",fieldCallStar NUM:"+ fieldCallStar +
                    ",methodCallStar NUM:"+ methodCallStar
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
