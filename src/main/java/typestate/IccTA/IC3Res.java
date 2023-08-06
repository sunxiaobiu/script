package typestate.IccTA;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class IC3Res {

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/IC3_output/";
        String fileName = "bd.com.elites.dmp";
        File file = new File(filePath+fileName+".txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        int startLineNumber = 0;
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);
            if(currentLine.equals("*****Result*****")){
                startLineNumber = i + 1;
                break;
            }
        }

        for (int i = startLineNumber; i < fileContent.size() && i+2<fileContent.size(); i++) {
            String currentLine = fileContent.get(i);
            String currentLineClazz = fileContent.get(i+2);
            if(currentLine.startsWith(fileName) && currentLineClazz.contains("clazz=")){
                String className = Regex.getSubUtilSimple(currentLine, "(^.*/)").replace("/","");
                String methodName = Regex.getSubUtilSimple(currentLine, "( .*?\\()").replace(" ","").replace("(", "");
                String clazzName = Regex.getSubUtilSimple(currentLineClazz, "(clazz=.*?,)").replace("clazz=","").replace(",","").replace("/", ".");
                if(StringUtils.isNotBlank(className) && StringUtils.isNotBlank(methodName) && StringUtils.isNotBlank(clazzName)){
                    System.out.println(className + "---" + methodName + "===="+clazzName);
                }
            }
        }
    }
}
