package permission.generatetest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FilterAPIConstructor {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/permission_runtest/generateTests/classWithConstructor.txt");
        List<String> classSigantureList = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        List<String> easyClass = new ArrayList<>();
        List<String> notEasyClass = new ArrayList<>();
        HashMap<String, String> easiestConstructor = new HashMap<>();

        //find the easiest constructor
        for(String classSig : classSigantureList){
            String className =  Regex.getSubUtilSimple(classSig, "(<.*:)");
            if(easiestConstructor.containsKey(className)){
                if(classSig.length() < easiestConstructor.get(className).length()){
                    easiestConstructor.put(className, classSig);
                }
            }else{
                easiestConstructor.put(className, classSig);
            }
        }

        //to check if it easy to initialize the target class
        for(String className : easiestConstructor.keySet()){
            String classSig = easiestConstructor.get(className);
            String paramStr = Regex.getSubUtilSimple(classSig, "(\\(.*\\))").replace("(","").replace(")","");
            List<String> paramList = Arrays.asList(paramStr.split(","));

            boolean isCLassEasy2Generate = true;
            if(CollectionUtils.isNotEmpty(paramList) && StringUtils.isNotBlank(paramStr)){
                for(String paramType : paramList){
                    if(!isBasicType(paramType)){
                        isCLassEasy2Generate = false;
                        break;
                    }
                }
            }

            if(isCLassEasy2Generate){
                easyClass.add(className);
            }else{
                notEasyClass.add(className);
            }
        }


        System.out.println("================================Easy=====================================");
        for(String s : easyClass){
            System.out.println(easiestConstructor.get(s));
        }

        System.out.println("================================Not Easy=====================================");
        for(String s : notEasyClass){
            System.out.println(easiestConstructor.get(s));
        }
    }

    public static boolean isBasicType(String paramType){
        if (paramType.equals("boolean") || paramType.equals("Boolean")) {
            return true;
        } else if (paramType.equals("int") || paramType.equals("Integer")) {
            return true;
        } else if (paramType.equals("char")) {
            return true;
        } else if (paramType.equals("double") || paramType.equals("Double")) {
            return true;
        } else if (paramType.equals("float")) {
            return true;
        } else if (paramType.equals("long") || paramType.equals("Long")) {
            return true;
        } else if (paramType.equals("short") || paramType.equals("Short")) {
            return true;
        } else if (paramType.equals("String")) {
            return true;
        } else if (paramType.equals("Character")) {
            return true;
        } else if (paramType.equals("Context")) {
            return true;
        } else if (paramType.equals("Looper")) {
            return true;
        } else if (paramType.equals("File")) {
            return true;
        } else if (paramType.equals("Executor")) {
            return true;
        }


        return false;
    }
}
