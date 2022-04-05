package JUnitTestGen.RunTests;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DeleteExecutedTest {

    public static void main(String[] args) throws IOException {

        File executedTestCaseFile = new File("/Users/xsun0035/Desktop/CADroid/JUnitTestRun_SDK21_log.txt");
        List<String> executedTestCases = new ArrayList<>(Files.readAllLines(executedTestCaseFile.toPath(), StandardCharsets.UTF_8));

        for(String str : executedTestCases){
            String testCaseName = Regex.getSubUtilSimple(str, "(TestCase_.*Test:)").replace(":", "");
            if(StringUtils.isNotBlank(testCaseName)){
                File toDelFile = new File("/Users/xsun0035/workspace/monash/ASE_JUnitTestGen/app/src/androidTest/java/"+testCaseName+".java");
                if(toDelFile.exists()){
                    System.out.println(toDelFile.getName());
                    toDelFile.delete();
                }
            }

        }
//        List<String> executedTestCases = new ArrayList<>();
//        executedTestCases.add("TestCase_com_ehawk_proxy_freevpn__1266487917Test");
//        executedTestCases.add("TestCase_com_rambooster_totalcleaner_376736081Test");
//        executedTestCases.add("TestCase_com_rambooster_totalcleaner_1209839776Test");
//        executedTestCases.add("TestCase_com_cooler_smartcooler5D1CD023FDF6D5F0BE3D671072D5CCD520B5D537__574350176Test");
//        executedTestCases.add("TestCase_app_prayerlibrary__1076362941Test");
//        executedTestCases.add("TestCase_app_prayerlibrary_620002909Test");
//        executedTestCases.add("TestCase_com_rambooster_totalcleaner__1461729724Test");
//        executedTestCases.add("TestCase_com_ehawk_proxy_freevpn_1423499243Test");
//        executedTestCases.add("TestCase_com_cooler_smartcooler5D1CD023FDF6D5F0BE3D671072D5CCD520B5D537_1284445547Test");
//        executedTestCases.add("TestCase_com_ehawk_proxy_freevpn_434105144Test");
//        executedTestCases.add("TestCase_com_cootek_smartinputv5_celldict_en_us_San_Francisco_353458004Test");
//        executedTestCases.add("TestCase_com_amazon_dee_app__1762116589Test");
//        executedTestCases.add("TestCase_com_facebook_mlite_593688661Test");
//
//        for (String str : executedTestCases) {
//            File toDelFile = new File("/Users/xsun0035/Desktop/CADroid/All_Valid_Tests/" + str + ".java");
//            if (toDelFile.exists()) {
//                System.out.println(toDelFile.getName());
//                toDelFile.delete();
//            }
//        }


    }

}
