package util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class xiaoyu {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/system.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        Set<String> fileSet = new HashSet<>(fileContent);

        for(String s : fileSet){
            String classPathStr = Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", "");
            String classNameStr = Regex.getSubUtilSimple(classPathStr, "([^.]+(?!.*.))");
            String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

            if(!StringUtils.isBlank(method)){
                System.out.println(classPathStr+"\\#"+method +" \\\\");
            }


        }

    }
}
