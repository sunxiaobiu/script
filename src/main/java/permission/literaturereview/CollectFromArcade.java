package permission.literaturereview;

import org.apache.commons.collections4.CollectionUtils;
import soot.Type;
import soot.coffi.Util;
import util.JNIResolve;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectFromArcade {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/xsun0035/workspace/monash/permissionMapping/arcade/API23.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        StringBuilder res = new StringBuilder();
        Set<String> resultSet = new HashSet<>();

        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            if(currentLine.startsWith("L")){
                res = new StringBuilder();
                String invokerCls = Regex.getSubUtilSimple(currentLine, "(L.*\\.)").replace(".", "");
                String invokerMethod = Regex.getSubUtilSimple(currentLine, "(\\..*\\()").replace(".", "").replace("(","");

                String sootCls = Util.v().jimpleTypeOfFieldDescriptor(invokerCls+";").toString();
                List<Type> sootMethodArgs = JNIResolve.extractMethodArgs(currentLine);
                Type sootMethodReturnType = JNIResolve.extractMethodReturnType(currentLine);

                StringBuilder sootType = new StringBuilder();
                sootType.append("<").append(sootCls).append(": ").append(sootMethodReturnType).append(" ").append(invokerMethod).append("(");
                if(CollectionUtils.isNotEmpty(sootMethodArgs)){
                    for(int j = 0; j< sootMethodArgs.size(); j++){
                        String parameterStr = sootMethodArgs.get(j).toString();
                        sootType.append(parameterStr.substring(parameterStr.lastIndexOf('.') + 1));
                        if(j != sootMethodArgs.size()-1){
                            sootType.append(",");
                        }
                    }
                }
                sootType.append(")>|");

                res.append(sootType);
                //System.out.println(sootType);
            }else{
                if(currentLine.contains("android.permission.")){
                    List<String> permissionStrList = Regex.getSubUtilSimpleList(currentLine, "(android.permission.[a-zA-Z_]+)");
                    if(CollectionUtils.isNotEmpty(permissionStrList)){
                        for(String permissionStr : permissionStrList){
                            if(!res.toString().contains(permissionStr)){
                                if(res.toString().contains("android.permission.")){
                                    res.append("|");
                                }
                                res.append(permissionStr);
                            }
                        }
                    }

                }
            }

            if(res.toString().contains("android.permission.")){
                resultSet.add(res.toString());
            }

        }

        for(String s : resultSet){
            String outputFile = "/Users/xsun0035/workspace/monash/permissionMapping/arcade/APIPermission23.txt";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
            out.println(s);
            out.close();
        }
    }
}
