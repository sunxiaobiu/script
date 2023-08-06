package permission.literaturereview;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CollectFromPsCout {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/workspace/monash/PScout/results/API_09/allmappings");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        HashMap<String, String> res = new HashMap<>();

        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);
            if (!currentLine.contains("<")) {
                continue;
            }

            String apiString = Regex.getSubUtilSimple(currentLine, "(<.*>)");
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

            String apiSignature = invokerClsMethod + "("+sootMethodArgList.toString() + ")>|";
            //System.out.println(apiSignature);

            //extract permission
            String thisPermission = "";
            for(int j = i-1; j>=0; j--){
                String permissionLine = fileContent.get(j);
                if(permissionLine.contains("android.permission.")){
                    thisPermission = Regex.getSubUtilSimple(permissionLine, "(android.permission.[a-zA-Z_]+)");
                    break;
                }
            }


            if(!res.containsKey(apiSignature)){
                res.put(apiSignature, thisPermission);
            }else{
                if(!res.get(apiSignature).contains(thisPermission)){
                    res.put(apiSignature, res.get(apiSignature)+"|"+thisPermission);
                }
            }
        }

        for (String api : res.keySet()) {
            String key = api.toString();
            String value = res.get(api).toString();
            String outputFile = "/Users/xsun0035/Desktop/pscout_res/API_09/APIPermission_allmappings.txt";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
            out.println(key + value);
            out.close();
            //System.out.println(key + value);
        }
    }
}
