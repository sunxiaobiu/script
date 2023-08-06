package typestate.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CollectAllAPI {

    public static void main(String[] args) throws IOException {
//        String typeStateRulesFile = "/Users/xsun0035/Desktop/TypeStateRules.txt";
//        String resourceLeakRulesFile = "/Users/xsun0035/Desktop/ResourceLeakRules.txt";

        Set<String> apis = new HashSet<>();

        File typeStateRulesFile = new File("/Users/xsun0035/Desktop/TypeStateRules.txt");
        List<String> typeStateRulesContent = new ArrayList<>(Files.readAllLines(typeStateRulesFile.toPath(), StandardCharsets.UTF_8));
        File resourceLeakRulesFile = new File("/Users/xsun0035/Desktop/ResourceLeakRules.txt");
        List<String> resourceLeakRulesContent = new ArrayList<>(Files.readAllLines(resourceLeakRulesFile.toPath(), StandardCharsets.UTF_8));
        typeStateRulesContent.addAll(resourceLeakRulesContent);

        for (int i = 0; i < typeStateRulesContent.size(); i++) {
            List<String> lineList = Lists.newArrayList(typeStateRulesContent.get(i).split(";"));
            for(String lineStr : lineList){
                if(lineStr.contains("|")){
                    List<String> beforeAPIList = Arrays.asList(lineStr.split("\\|"));
                    for(String beforeAPI : beforeAPIList){
                        String api = Regex.getSubUtilSimple(beforeAPI, "(<.*>)");
                        if(StringUtils.isNotBlank(api)){
                            apis.add(api);
                        }
                    }
                }else{
                    String api = Regex.getSubUtilSimple(lineStr, "(<.*>)");
                    if(StringUtils.isNotBlank(api)){
                        apis.add(api);
                    }
                }
            }
        }

        for(String api : apis){
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/AllAPI.txt", true)));
            out.println(api);
            out.close();
        }
    }
}
