package JUnitTestGen.CiD;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class OnlyJunitTestGenType {

    public static void main(String[] args) throws IOException {
        String onlyJunitTestGenTypePath = args[0];
        String apiTypePath = args[1];

        File onlyJunitTestGenTypeFile = new File(onlyJunitTestGenTypePath);
        List<String> onlyJunitTestGenTypeContent = new ArrayList<>(Files.readAllLines(onlyJunitTestGenTypeFile.toPath(), StandardCharsets.UTF_8));

        File apiTypeFile = new File(apiTypePath);
        List<String> apiTypeContent = new ArrayList<>(Files.readAllLines(apiTypeFile.toPath(), StandardCharsets.UTF_8));

        for(String onlyJunitTestGenString : onlyJunitTestGenTypeContent){
            for(String apiType : apiTypeContent){
                 if(apiType.startsWith(onlyJunitTestGenString)){
                     String type = Regex.getSubUtilSimple(apiType, "(type.*)");
                     System.out.println(type);
                 }
            }
        }
    }
}
