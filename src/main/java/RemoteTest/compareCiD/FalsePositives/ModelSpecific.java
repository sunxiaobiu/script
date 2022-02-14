package RemoteTest.compareCiD.FalsePositives;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ModelSpecific {

    public static void main(String[] args) throws IOException {
        File minExceptionTypeFile = new File("/Users/xsun0035/Desktop/allLazycow.txt");
        List<String> minExceptionTypes = new ArrayList<>(Files.readAllLines(minExceptionTypeFile.toPath(), StandardCharsets.UTF_8));

        File onlyLazyCowFile = new File("/Users/xsun0035/Desktop/onlyLazyCow.txt");
        List<String> onlyLazyCow = new ArrayList<>(Files.readAllLines(onlyLazyCowFile.toPath(), StandardCharsets.UTF_8));

        //for each test case
        for (String s : minExceptionTypes) {
            if(Regex.getSubUtilSimpleList(s, "(\\[.*?\\])").size() !=2){
                continue;
            }
            String testCaseName =  Regex.getSubUtilSimple(s, "(.*---)").replace("-", "");
            if(!onlyLazyCow.contains(testCaseName.replace("-", "").replace(".testCase", ""))){
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
