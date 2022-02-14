package RemoteTest.compareCiD.FalsePositives;

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

public class CheckCiDFalsePositive {

    //=========================================SDK 21-29==============================================
    public static void main(String[] args) throws IOException, ParseException {
        String cidCI = "/Users/xsun0035/Desktop/CiDTestName.txt";
        File file = new File(cidCI);
        List<String> cidContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

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

        //===================所有机器
        for (String key : cidContent) {

            HashSet<String> resSet = new HashSet<>();
            HashSet<String> detailResSet = new HashSet<>();
            resSet.add(sdkd391d5103d38d41d_unknown_R2.get(key));
            detailResSet.add("d391d5103d38d41d_unknown_R2:"+sdkd391d5103d38d41d_unknown_R2.get(key));

            if(sdk695d0c214199bc16_unknown_R2.get(key) != null && !resSet.contains(sdk695d0c214199bc16_unknown_R2.get(key))){
                resSet.add(sdk695d0c214199bc16_unknown_R2.get(key));
                detailResSet.add("695d0c214199bc16_unknown_R2:"+sdk695d0c214199bc16_unknown_R2.get(key));
            }

            if(sdkcc1bf9bf6ba11e1d_unknown_R2.get(key) != null &&!resSet.contains(sdkcc1bf9bf6ba11e1d_unknown_R2.get(key))){
                resSet.add(sdkcc1bf9bf6ba11e1d_unknown_R2.get(key));
                detailResSet.add("cc1bf9bf6ba11e1d_unknown_R2:"+sdkcc1bf9bf6ba11e1d_unknown_R2.get(key));
            }
            if(sdkf86c4e9ed953d71e_unknown_R2.get(key) != null &&!resSet.contains(sdkf86c4e9ed953d71e_unknown_R2.get(key))){
                resSet.add(sdkf86c4e9ed953d71e_unknown_R2.get(key));
                detailResSet.add("f86c4e9ed953d71e_unknown_R2:"+sdkf86c4e9ed953d71e_unknown_R2.get(key));
            }
            if(sdk663a8e971844dd17_unknown_R2.get(key) != null &&!resSet.contains(sdk663a8e971844dd17_unknown_R2.get(key))){
                resSet.add(sdk663a8e971844dd17_unknown_R2.get(key));
                detailResSet.add("663a8e971844dd17_unknown_R2:"+sdk663a8e971844dd17_unknown_R2.get(key));
            }
            if(sdkccb713aa29889ffa_unknown_R2.get(key) != null &&!resSet.contains(sdkccb713aa29889ffa_unknown_R2.get(key))){
                resSet.add(sdkccb713aa29889ffa_unknown_R2.get(key));
                detailResSet.add("ccb713aa29889ffa_unknown_R2:"+sdkccb713aa29889ffa_unknown_R2.get(key));
            }
            if(sdkd3e1b931852840fe_unknown_R2.get(key) != null &&!resSet.contains(sdkd3e1b931852840fe_unknown_R2.get(key))){
                resSet.add(sdkd3e1b931852840fe_unknown_R2.get(key));
                detailResSet.add("d3e1b931852840fe_unknown_R2:"+sdkd3e1b931852840fe_unknown_R2.get(key));
            }
            if(sdk7612a41004afe2f6_unknown_R2.get(key) != null &&!resSet.contains(sdk7612a41004afe2f6_unknown_R2.get(key))){
                resSet.add(sdk7612a41004afe2f6_unknown_R2.get(key));
                detailResSet.add("7612a41004afe2f6_unknown_R2:"+sdk7612a41004afe2f6_unknown_R2.get(key));
            }
            if(sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2.get(key) != null &&!resSet.contains(sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2.get(key))){
                resSet.add(sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2.get(key));
                detailResSet.add("c63e132fc8e57732_52009ddc8d7eb5e5_R2:"+sdkc63e132fc8e57732_52009ddc8d7eb5e5_R2.get(key));
            }
            if(sdk7737d78741a5e0e0_unknown_R2.get(key) != null &&!resSet.contains(sdk7737d78741a5e0e0_unknown_R2.get(key))){
                resSet.add(sdk7737d78741a5e0e0_unknown_R2.get(key));
                detailResSet.add("7737d78741a5e0e0_unknown_R2:"+sdk7737d78741a5e0e0_unknown_R2.get(key));
            }
            if(sdk034b30fce2e95297_unknown_R2.get(key) != null &&!resSet.contains(sdk034b30fce2e95297_unknown_R2.get(key))){
                resSet.add(sdk034b30fce2e95297_unknown_R2.get(key));
                detailResSet.add("034b30fce2e95297_unknown_R2:"+sdk034b30fce2e95297_unknown_R2.get(key));
            }

            if(resSet.size() >= 1){
                System.out.println(key + "--------"+ detailResSet);
                for(String s : resSet){
                    IncrementHashMap.incrementValue(exceptionRes, s);
                }
            }
        }

        Map<String, Integer> sortedHashMap = IncrementHashMap.sortByValue(exceptionRes);
        for (String s : sortedHashMap.keySet()) {
            System.out.println(s + ";" + sortedHashMap.get(s));
        }
    }


}
