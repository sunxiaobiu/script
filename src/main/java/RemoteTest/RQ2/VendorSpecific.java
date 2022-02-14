package RemoteTest.RQ2;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class VendorSpecific {

    public static void main(String[] args) throws IOException {
        File minExceptionTypeFile = new File("/Users/xsun0035/Desktop/VendorSpecific.txt");
        List<String> minExceptionTypes = new ArrayList<>(Files.readAllLines(minExceptionTypeFile.toPath(), StandardCharsets.UTF_8));

        //emulator结果
        String emulator26 = "/Users/xsun0035/Desktop/AOSPTests26.txt";
        String emulator28 = "/Users/xsun0035/Desktop/AOSPTests28.txt";
        String emulator29 = "/Users/xsun0035/Desktop/AOSPTests29.txt";
        String emulator30 = "/Users/xsun0035/Desktop/AOSPTests30.txt";

        HashMap<String, String> resMap26 = new HashMap<>();
        HashMap<String, String> resMap28 = new HashMap<>();
        HashMap<String, String> resMap29 = new HashMap<>();
        HashMap<String, String> resMap30 = new HashMap<>();
        getEmulatorLog(emulator26, resMap26);
        getEmulatorLog(emulator28, resMap28);
        getEmulatorLog(emulator29, resMap29);
        getEmulatorLog(emulator30, resMap30);


        //for each test case
        for (String s : minExceptionTypes) {
            if(Regex.getSubUtilSimpleList(s, "(\\[.*?\\])").size() !=2){
                continue;
            }

            StringBuilder emulatorExceptionList = new StringBuilder();
            Set<String> needCheckVersions = new HashSet<>();
            String testCaseName =  Regex.getSubUtilSimple(s, "(.*---)").replace("-", "");
            String exceptionType =  Regex.getSubUtilSimple(s, "(----.*:)").replace("-", "").replace(":","");
            List<String> versions = Regex.getSubUtilSimpleList(s, "(\\[[0-9]+\\])");
            for(String version : versions){
                needCheckVersions.add("[emulator]"+version);
            }

            if(null == resMap26.get(testCaseName)){
                needCheckVersions.remove("[emulator][26]");
            }
            if(null == resMap28.get(testCaseName)){
                needCheckVersions.remove("[emulator][28]");
            }
            if(null == resMap29.get(testCaseName)){
                needCheckVersions.remove("[emulator][29]");
            }
            if(null == resMap30.get(testCaseName)){
                needCheckVersions.remove("[emulator][30]");
            }

            if(null != resMap26.get(testCaseName) && resMap26.get(testCaseName).equals(exceptionType)){
                emulatorExceptionList.append("[emulator][26]");
            }
            if(null != resMap28.get(testCaseName) && resMap28.get(testCaseName).equals(exceptionType)){
                emulatorExceptionList.append("[emulator][28]");
            }
            if(null != resMap29.get(testCaseName) && resMap29.get(testCaseName).equals(exceptionType)){
                emulatorExceptionList.append("[emulator][29]");
            }
            if(null != resMap30.get(testCaseName) && resMap30.get(testCaseName).equals(exceptionType)){
                emulatorExceptionList.append("[emulator][30]");
            }

            boolean isVendorSpecific = false;
            for(String needCheckVersion : needCheckVersions){
                //只要有一个特定机器上的报错找不到相同API level上emulator的报错，就说明是vendor specific的
                if(!emulatorExceptionList.toString().contains(needCheckVersion)){
                    isVendorSpecific = true;
                }
            }

            if(isVendorSpecific){
                System.out.println(s);
            }

        }
    }

    private static void getEmulatorLog(String logFile, HashMap<String, String> resMap) throws IOException {
        File file = new File(logFile);

        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String successTestCase = Regex.getSubUtilSimple(currentLine, "(.*:true)");
            String failTestCase = Regex.getSubUtilSimple(currentLine, "(.*:false)");

            //success
            if (StringUtils.isNotBlank(successTestCase)) {
                resMap.put(successTestCase.replace(":true", "").replace("I/System.out: ", "").replace(" ", ""), "success");
            }

            //fail
            String exceptionContene = "";
            if (StringUtils.isNotBlank(failTestCase)) {
                for(int j = i; j < fileContent.size(); j++){
                    String exceptionLine = fileContent.get(j);
                    String currentExpLine2 = Regex.getSubUtilSimple(exceptionLine, "(Caused by:.*?Exception|Caused by:.*?Error|Caused by:.*?Failure)").replace("Caused by: ", "");

//                    if (StringUtils.isEmpty(currentExpLine2)) {
//                        currentExpLine2 = Regex.getSubUtilSimple(exceptionLine, "(java.*Exception|java.*Failure|java.*Error)");
//                    }

                    if (currentExpLine2.equals("java.lang.Exception")) {
                        currentExpLine2 = Regex.getLastSubUtilSimple(exceptionLine, ".*(Caused by:.*Exception).*").replace("Caused by: ", "");
                    }
                    if (currentExpLine2.equals("org.mockito.exceptions.base.MockitoException")) {
                        currentExpLine2 = Regex.getLastSubUtilSimple(exceptionLine, ".*(Caused by:.*Exception).*").replace("Caused by: ", "");
                    }

                    if(StringUtils.isNotBlank(currentExpLine2)){
                        exceptionContene = currentExpLine2;
                        break;
                    }
                }
                resMap.put(failTestCase.replace(":false", "").replace("I/System.out: ", "").replace(" ", ""), exceptionContene);
            }

        }
    }

}
