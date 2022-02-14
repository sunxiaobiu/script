package RemoteTest.RQ2;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelSpecific {

    public static void main(String[] args) throws IOException {
        File minExceptionTypeFile = new File("/Users/xsun0035/Desktop/ModelSpecific.txt");
        List<String> minExceptionTypes = new ArrayList<>(Files.readAllLines(minExceptionTypeFile.toPath(), StandardCharsets.UTF_8));

        //for each test case
        for (String s : minExceptionTypes) {
            if(Regex.getSubUtilSimpleList(s, "(\\[.*?\\])").size() !=2){
                continue;
            }

            boolean isModelSpecific = false;

            if((s.contains("[huawei][28]") && !s.contains("[HUAWEI][28]")) || (!s.contains("[huawei][28]") && s.contains("[HUAWEI][28]"))){
                isModelSpecific = true;
            }

            List<String> xiaomi29 = Regex.getSubUtilSimpleList(s, "(\\[Xiaomi\\]\\[29\\])");
            if(xiaomi29.size() == 1){
                isModelSpecific = true;
            }

            if(isModelSpecific){
                System.out.println(s);
            }


        }


    }
}
