package permission.literaturereview;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.JNIResolve;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectFromNatidroid {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/workspace/monash/permissionMapping/natidroid/API29.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));


        for (int i = 0; i < fileContent.size(); i++) {
            StringBuilder res = new StringBuilder();
            String currentLine = fileContent.get(i);
            if(!currentLine.contains("  ::")){
                continue;
            }

            String apiString = Regex.getSubUtilSimple(currentLine, "(.*  ::)").replace("  ::", "");
            String invokerClsMethod = Regex.getSubUtilSimple(apiString, "(.*\\()").replace("(", "");

            int paramStart = apiString.indexOf('(');
            int paramEnd = apiString.indexOf(')');

            List<String> sootMethodArgs = Arrays.asList(apiString.substring(paramStart + 1, paramEnd).split(","));
            StringBuilder sootMethodArgList = new StringBuilder();
            if(CollectionUtils.isNotEmpty(sootMethodArgs)){
                for(String arg : sootMethodArgs){
                    if(StringUtils.isNotBlank(sootMethodArgList.toString())){
                        sootMethodArgList.append(",");
                    }
                    sootMethodArgList.append(arg.substring(arg.lastIndexOf('.') + 1));
                }
            }
            if(file.getName().contains("29")){
                res.append(invokerClsMethod + "("+sootMethodArgList.toString() + ")>|");
            }else{
                res.append("<"+invokerClsMethod + "("+sootMethodArgList.toString() + ")>|");
            }

            //extract permission
            String permissionString = Regex.getSubUtilSimple(currentLine, "(::.*)").replace("::  ", "");
            List<String> permissionList = Regex.getSubUtilSimpleList(permissionString, "(android.permission.[a-zA-Z_]+)");
            if(CollectionUtils.isNotEmpty(permissionList)){
                for(String permissionStr : permissionList){
                    if(!res.toString().contains(permissionStr)){
                        if(res.toString().contains("android.permission.")){
                            res.append("|");
                        }
                        res.append(permissionStr);
                    }
                }
            }

            String outputFile = "/Users/xsun0035/workspace/monash/permissionMapping/natidroid/APIPermission_API29.txt";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
            out.println(res);
            out.close();
        }

    }
}
