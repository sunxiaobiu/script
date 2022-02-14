package permission.runtest;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CollectAllPermissions {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/allPermission.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for(String s : fileContent){
            if(s.contains("public static final String")){
                String permission = Regex.getSubUtilSimple(s, "(\\\".*\\\")").replace("\"", "");
                System.out.println("<uses-permission android:name=\""+permission+"\" />");
            }
        }
    }
}
