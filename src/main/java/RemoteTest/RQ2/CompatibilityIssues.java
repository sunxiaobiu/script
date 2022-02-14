package RemoteTest.RQ2;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;

public class CompatibilityIssues {

    //=========================================SDK 21-29==============================================
    public static void main(String[] args) throws IOException, ParseException {
        Set<String> avaliableDeviceName = new HashSet<>();
        //所有机器
        avaliableDeviceName.add("d391d5103d38d41d_unknown_R2"); //meizu 30
        avaliableDeviceName.add("695d0c214199bc16_unknown_R2"); //HUAWEI 28
        avaliableDeviceName.add("cc1bf9bf6ba11e1d_unknown_R2"); //HUAWEI 29
        avaliableDeviceName.add("f86c4e9ed953d71e_unknown_R2"); //HONOR 29
        avaliableDeviceName.add("663a8e971844dd17_unknown_R2"); //samsung 30
        avaliableDeviceName.add("ccb713aa29889ffa_unknown_R2"); //HUAWEI 28
        avaliableDeviceName.add("d3e1b931852840fe_unknown_R2"); //Xiaomi 29
        avaliableDeviceName.add("7612a41004afe2f6_unknown_R2"); //ONEPLUS 28
        avaliableDeviceName.add("c63e132fc8e57732_52009ddc8d7eb5e5_R2"); //samsung 26
        avaliableDeviceName.add("7737d78741a5e0e0_unknown_R2"); //Xiaomi 29
        avaliableDeviceName.add("034b30fce2e95297_unknown_R2"); //samsung 29


        HashMap<String, HashSet<String>> res = new HashMap<>();
        HashMap<String, String> sdkd391d5103d38d41d_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdk695d0c214199bc16_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdkcc1bf9bf6ba11e1d_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdkf86c4e9ed953d71e_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdk663a8e971844dd17_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdkccb713aa29889ffa_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdkd3e1b931852840fe_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdk7612a41004afe2f6_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2 = new HashMap<>();
        HashMap<String, String> sdk7737d78741a5e0e0_unknown_R2 = new HashMap<>();
        HashMap<String, String> sdk034b30fce2e95297_unknown_R2 = new HashMap<>();

        HashMap<String, Integer> exceptionRes = new HashMap<>();

        InputStream is = new FileInputStream(new File("/Users/xsun0035/Desktop/CrowdTest/test_runner.xlsx"));
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(200000)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(200000)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);            // InputStream or File for XLSX file (required)

        for (Sheet sheet : workbook) {
            for (Row r : sheet) {
                String deviceName = r.getCell(2).getStringCellValue();
                //String testCaseName = Regex.getSubUtilSimple(r.getCell(1).getStringCellValue(), "(.*?\\.)").replace(".","");
                String testCaseName = r.getCell(1).getStringCellValue();
                if (!avaliableDeviceName.contains(deviceName)) {
                    continue;
                }

                String resultString = r.getCell(6).getStringCellValue();
                String resString = "";
                if (resultString.equals("success")) {
                    resString = "success";
                } else {
                    resString = Regex.getSubUtilSimple(resultString, "(Caused by:.*?Exception|Caused by:.*?Error|Caused by:.*?Failure)").replace("Caused by: ", "");
                    //resString = Regex.getLastSubUtilSimple(resultString, "(Caused by:.*?Exception|Caused by:.*?Error|Caused by:.*?Failure)").replace("Caused by: ", "");
                }

                if (StringUtils.isEmpty(resString)) {
                    resString = Regex.getSubUtilSimple(resultString, "(java.*Exception|java.*Failure|java.*Error)");
                }

                if (resString.equals("java.lang.Exception")) {
                    resString = Regex.getLastSubUtilSimple(resultString, ".*(Caused by:.*Exception).*").replace("Caused by: ", "");
                    ;
                }
                if (resString.equals("org.mockito.exceptions.base.MockitoException")) {
                    resString = Regex.getLastSubUtilSimple(resultString, ".*(Caused by:.*Exception).*").replace("Caused by: ", "");
                    ;
                }

                if (deviceName.equals("d391d5103d38d41d_unknown_R2")) {
                    sdkd391d5103d38d41d_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("695d0c214199bc16_unknown_R2")) {
                    sdk695d0c214199bc16_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("cc1bf9bf6ba11e1d_unknown_R2")) {
                    sdkcc1bf9bf6ba11e1d_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("f86c4e9ed953d71e_unknown_R2")) {
                    sdkf86c4e9ed953d71e_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("663a8e971844dd17_unknown_R2")) {
                    sdk663a8e971844dd17_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("ccb713aa29889ffa_unknown_R2")) {
                    sdkccb713aa29889ffa_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("d3e1b931852840fe_unknown_R2")) {
                    sdkd3e1b931852840fe_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("7612a41004afe2f6_unknown_R2")) {
                    sdk7612a41004afe2f6_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("c63e132fc8e57732_52009ddc8d7eb5e5_R2")) {
                    sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("7737d78741a5e0e0_unknown_R2")) {
                    sdk7737d78741a5e0e0_unknown_R2.putIfAbsent(testCaseName, resString);
                }
                if (deviceName.equals("034b30fce2e95297_unknown_R2")) {
                    sdk034b30fce2e95297_unknown_R2.putIfAbsent(testCaseName, resString);
                }
            }
        }

        //compatibility issue test cases
        Set<String> effectiveTestCases = new HashSet<>();
        File lazyCowExceptions = new File("/Users/xsun0035/Desktop/LazyCow.txt");
        List<String> lazyCowExceptionContent = new ArrayList<>(Files.readAllLines(lazyCowExceptions.toPath(), StandardCharsets.UTF_8));
        for(String s : lazyCowExceptionContent){
            String testCaseName = Regex.getSubUtilSimple(s, "(.*-----)").replace("-", "");
            effectiveTestCases.add(testCaseName);
        }

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

        //获取出现次数最少的报错类型
        HashMap<String, String> minExceptionTypeMap = CalculateMinExceptionType.get();

        //===================所有机器
        for(String testCaseName : effectiveTestCases){
            HashSet<String> detailResSet = new HashSet<>();

            //group res
            Set<String> exceptionType = new HashSet<>();
            exceptionType.add(sdkd391d5103d38d41d_unknown_R2.get(testCaseName));
            exceptionType.add(sdk695d0c214199bc16_unknown_R2.get(testCaseName));
            exceptionType.add(sdkcc1bf9bf6ba11e1d_unknown_R2.get(testCaseName));
            exceptionType.add(sdkf86c4e9ed953d71e_unknown_R2.get(testCaseName));
            exceptionType.add(sdk663a8e971844dd17_unknown_R2.get(testCaseName));
            exceptionType.add(sdkccb713aa29889ffa_unknown_R2.get(testCaseName));
            exceptionType.add(sdkd3e1b931852840fe_unknown_R2.get(testCaseName));
            exceptionType.add(sdk7612a41004afe2f6_unknown_R2.get(testCaseName));
            exceptionType.add(sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2.get(testCaseName));
            exceptionType.add(sdk7737d78741a5e0e0_unknown_R2.get(testCaseName));
            exceptionType.add(sdk034b30fce2e95297_unknown_R2.get(testCaseName));
            exceptionType.add(resMap26.get(testCaseName));
            exceptionType.add(resMap28.get(testCaseName));
            exceptionType.add(resMap29.get(testCaseName));
            exceptionType.add(resMap30.get(testCaseName));


            HashMap<String, String> resMap = new HashMap<>();
            for(String et : exceptionType){
                int numOfException = 0;
                if(StringUtils.isBlank(et)){
                    continue;
                }
                if(et.equals("success")){
                    continue;
                }
                if(!et.equals(minExceptionTypeMap.get(testCaseName))){
                    continue;
                }

                StringBuilder exceptionValue = new StringBuilder();
                if(null != sdkd391d5103d38d41d_unknown_R2.get(testCaseName) && sdkd391d5103d38d41d_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[Meizu][30];");
                }
                if(null != sdk695d0c214199bc16_unknown_R2.get(testCaseName) && sdk695d0c214199bc16_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[huawei][28];");
                }
                if(null != sdkcc1bf9bf6ba11e1d_unknown_R2.get(testCaseName) && sdkcc1bf9bf6ba11e1d_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[huawei][29];");
                }
                if(null != sdkf86c4e9ed953d71e_unknown_R2.get(testCaseName) && sdkf86c4e9ed953d71e_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[HONOR][29];");
                }
                if(null != sdk663a8e971844dd17_unknown_R2.get(testCaseName) && sdk663a8e971844dd17_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[samsung][30];");
                }
                if(null != sdkccb713aa29889ffa_unknown_R2.get(testCaseName) && sdkccb713aa29889ffa_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[HUAWEI][28];");
                }
                if(null != sdkd3e1b931852840fe_unknown_R2.get(testCaseName) && sdkd3e1b931852840fe_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[Xiaomi][29];");
                }
                if(null != sdk7612a41004afe2f6_unknown_R2.get(testCaseName) && sdk7612a41004afe2f6_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[ONEPLUS][28];");
                }
                if(null != sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2.get(testCaseName) && sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[samsung][26];");
                }
                if(null != sdk7737d78741a5e0e0_unknown_R2.get(testCaseName) && sdk7737d78741a5e0e0_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[Xiaomi][29];");
                }
                if(null != sdk034b30fce2e95297_unknown_R2.get(testCaseName) && sdk034b30fce2e95297_unknown_R2.get(testCaseName).equals(et)){
                    exceptionValue.append("[samsung][29];");
                }
                if(null != resMap26.get(testCaseName) && resMap26.get(testCaseName).equals(et)){
                    exceptionValue.append("[emulator][26];");
                }
                if(null != resMap28.get(testCaseName) && resMap28.get(testCaseName).equals(et)){
                    exceptionValue.append("[emulator][28];");
                }
                if(null != resMap29.get(testCaseName) && resMap29.get(testCaseName).equals(et)){
                    exceptionValue.append("[emulator][29];");
                }
                if(null != resMap30.get(testCaseName) && resMap30.get(testCaseName).equals(et)){
                    exceptionValue.append("[emulator][30];");
                }
                resMap.put(et, exceptionValue.toString());
            }

            String allException = "";
            for(String s : resMap.keySet()){
                allException += s + ":" +resMap.get(s);
            }
            System.out.println(testCaseName + "--------"+ allException);
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
                resMap.put(successTestCase.replace(":true", "").replace("I/System.out: ", ""), "success");
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
                resMap.put(failTestCase.replace(":false", "").replace("I/System.out: ", ""), exceptionContene);
            }

        }
    }


}
