package typestate.RQ3;

import org.apache.commons.lang3.StringUtils;
import util.FileUtil;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CompatibilityIssues_fifty {

    public static void main(String[] args) throws IOException {
        List<String> typeStateAPIs = FileUtil.getAllAPIListFromSourceFile("/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/AllAPI.txt");
        String apkNameFileStr = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ3/TimeMachine/apk.txt";
        File apkNameFile = new File(apkNameFileStr);
        List<String> apkNames = new ArrayList<>(Files.readAllLines(apkNameFile.toPath(), StandardCharsets.UTF_8));

        int compatibilityIssuesNum = 0;

        for(String apkName : apkNames){
            File file = new File("/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/output/" + apkName.replace(".apk", ".txt"));
            if(!file.exists()){
                continue;
            }
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            for(String api : fileContent){
                if(isAndroidTypeStateAPI(api, typeStateAPIs)){
                    compatibilityIssuesNum ++;
                }
            }
        }
        System.out.println(compatibilityIssuesNum);
    }

    public static boolean isAndroidTypeStateAPI(String apiSig, List<String> typeStateAPIs) {
        for (String typestateAPI : typeStateAPIs) {
            String thisClassName = Regex.getSubUtilSimple(apiSig, "(<.*?:)").replace("<", "").replace(":", "");
            String thisMethodName = Regex.getSubUtilSimple(apiSig, "([a-zA-Z<>]+\\()").replace("(", "");

            String thatClassName = Regex.getSubUtilSimple(typestateAPI, "(<.*?:)").replace("<", "").replace(":", "");
            String thatMethodName = Regex.getSubUtilSimple(typestateAPI, "([a-zA-Z<>]+\\()").replace("(", "");

            if (StringUtils.equals(thisClassName, thatClassName) && StringUtils.equals(thisMethodName, thatMethodName)) {
                return true;
            }
        }
        return false;
    }
}
