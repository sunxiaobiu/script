package permission.literaturereview;

import org.apache.commons.collections4.CollectionUtils;
import soot.Type;
import util.JNIResolve;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectFromAxplorer {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/workspace/monash/permissionMapping/axplorer/permissions/api-25/framework-map-25.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));


        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String apiString = Regex.getSubUtilSimple(currentLine, "(.*  ::)").replace("  ::", "");
            String invokerClsMethod = Regex.getSubUtilSimple(apiString, "(.*\\()").replace("(", "");

            String invokerMethod = invokerClsMethod.substring(invokerClsMethod.lastIndexOf('.') + 1);
            String invokerCls = invokerClsMethod.replace("."+invokerMethod, "");

            int paramStart = apiString.indexOf('(');
            int paramEnd = apiString.indexOf(')');

            List<String> sootMethodArgs =  Arrays.asList(apiString.substring(paramStart+1, paramEnd).split(","));
            String sootMethodReturnType = JNIResolve.getReturnTypeDesc(apiString);

            StringBuilder res = new StringBuilder();
            res.append("<").append(invokerCls).append(": ").append(sootMethodReturnType).append(" ").append(invokerMethod).append("(");
            if(CollectionUtils.isNotEmpty(sootMethodArgs)){
                for(int j = 0; j< sootMethodArgs.size(); j++){
                    String parameterStr = sootMethodArgs.get(j).toString();
                    res.append(parameterStr.substring(parameterStr.lastIndexOf('.') + 1));
                    if(j != sootMethodArgs.size()-1){
                        res.append(",");
                    }
                }
            }
            res.append(")>|");

            //extract permission
            String permissionString = Regex.getSubUtilSimple(currentLine, "(::.*)").replace("::  ", "").replace(", ", "|");
            res.append(permissionString);

            String outputFile = "/Users/xsun0035/workspace/monash/permissionMapping/axplorer/permissions/api-25/APIPermission_framework-map-25.txt";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
            out.println(res);
            out.close();
            //System.out.println(res);
        }
    }

}
