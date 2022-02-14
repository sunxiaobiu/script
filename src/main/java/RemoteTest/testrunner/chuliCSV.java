package RemoteTest.testrunner;

import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.impl.StreamingRow;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.IncrementHashMap;
import util.Regex;
import util.String2Date;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;

public class chuliCSV {

    public static void main(String[] args) throws IOException, ParseException {
        Set<String> avaliableDeviceName = new HashSet<>();
        avaliableDeviceName.add("d391d5103d38d41d_unknown");
        avaliableDeviceName.add("d391d5103d38d41d_unknown_R2");
        avaliableDeviceName.add("695d0c214199bc16_unknown");
        avaliableDeviceName.add("695d0c214199bc16_unknown_R2");
        avaliableDeviceName.add("cc1bf9bf6ba11e1d_unknown");
        avaliableDeviceName.add("cc1bf9bf6ba11e1d_unknown_R2");
        avaliableDeviceName.add("f86c4e9ed953d71e_unknown");
        avaliableDeviceName.add("f86c4e9ed953d71e_unknown_R2");
        avaliableDeviceName.add("663a8e971844dd17_unknown");
        avaliableDeviceName.add("663a8e971844dd17_unknown_R2");
        avaliableDeviceName.add("ccb713aa29889ffa_unknown");
        avaliableDeviceName.add("ccb713aa29889ffa_unknown_R2");
        avaliableDeviceName.add("d3e1b931852840fe_unknown");
        avaliableDeviceName.add("d3e1b931852840fe_unknown_R2");
        avaliableDeviceName.add("7612a41004afe2f6_unknown");
        avaliableDeviceName.add("7612a41004afe2f6_unknown_R2");
        avaliableDeviceName.add("c63e132fc8e57732_52009ddc8d7eb5e5");
        avaliableDeviceName.add("c63e132fc8e57732_52009ddc8d7eb5e5_R2");
        avaliableDeviceName.add("7737d78741a5e0e0_unknown");
        avaliableDeviceName.add("7737d78741a5e0e0_unknown_R2");
        avaliableDeviceName.add("034b30fce2e95297_unknown");
        avaliableDeviceName.add("034b30fce2e95297_unknown_R2");


        Map<String, Integer> exceptionMap = new HashMap();
        Map<String, Integer> executedTestNames = new HashMap();
        Map<String, Date> deviceNameLatestTimeMap = new HashMap();
        Map<String, Integer> deviceNameExecutedTimeMap = new HashMap();
        Set<String> testNameRecord = new HashSet<>();

        List<Long> macTimeCount = new ArrayList<>();

        InputStream is = new FileInputStream(new File("/Users/xsun0035/Desktop/CrowdTest/test_runner.xlsx"));
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(200000)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(200000)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);            // InputStream or File for XLSX file (required)

        for (Sheet sheet : workbook){
            for (Row r : sheet) {
                String deviceName = r.getCell(2).getStringCellValue();
                String testCaseName = Regex.getSubUtilSimple(r.getCell(1).getStringCellValue(), "(.*?\\.)").replace(".","");
                String deviceTestCaseName = deviceName + "_" + testCaseName;
                if(!avaliableDeviceName.contains(deviceName)){
                    continue;
                }
                if(!deviceName.contains("_R2")){
                    continue;
                }
                if(!r.getCell(7).getStringCellValue().equals("500")){
                    continue;
                }
                /**
                 * 统计时间
                 */
                String rowTimeString = r.getCell(3).getStringCellValue();
                Date rowTime = String2Date.fromString(rowTimeString);
                if(deviceNameLatestTimeMap.get(deviceName) == null){
                    deviceNameLatestTimeMap.put(deviceName, rowTime);
                    deviceNameExecutedTimeMap.put(deviceName, new Long(0).intValue());
                }

                Date latestTimeString = deviceNameLatestTimeMap.get(deviceName);
                Integer seconds = new Long((rowTime.getTime()-latestTimeString.getTime())/1000).intValue();

//                if(seconds > 2){
//                    macTimeCount.add(seconds);
//                }

                if(seconds == 0){
                    continue;
                }else if(seconds <= 1000 && seconds >0){
                    deviceNameLatestTimeMap.put(deviceName, rowTime);
                    deviceNameExecutedTimeMap.put(deviceName, deviceNameExecutedTimeMap.get(deviceName) + seconds);
                }else {
                    deviceNameLatestTimeMap.put(deviceName, rowTime);
                }
//
//                /**
//                 * 统计数量
//                 */
//                if(testNameRecord.contains(deviceTestCaseName)){
//                    continue;
//                }else {
//                    testNameRecord.add(deviceTestCaseName);
//                }
//
//                IncrementHashMap.incrementValue(executedTestNames, r.getCell(2).getStringCellValue());

            }
        }

//        /**
//         * 数量结果
//         */
////        Map<String, Integer> sortedHashMap = IncrementHashMap.sortByValue(executedTestNames);
//        for(String s :executedTestNames.keySet()){
//            System.out.println(s+";"+executedTestNames.get(s));
//        }

        /**
         * 时间结果
         */
        for(String s :deviceNameExecutedTimeMap.keySet()){
            System.out.println(s+";"+deviceNameExecutedTimeMap.get(s));
        }

    }
}
