package HSO.RQ1;

import HSO.model.HSO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TriggerSensitivePair {

    public static void main(String[] args) throws IOException {

        String outputFilePath = args[0];

        List<String> txtFileNames = new ArrayList<>();

        TriggerNum.getFileList(outputFilePath, txtFileNames);

        List<HSO> result = new ArrayList<>();

        /**
         * load HSO From File
         */
        TriggerNum.loadHSOFromFile(outputFilePath, txtFileNames, result);

        /**
         * filter common cases
         */
        TriggerNum.filterCommonCases(result);

        /**
         * filter data dependency
         */
        TriggerNum.filterDataDependency(result);

        /**
         * 判断分支差异
         */
        TriggerNum.branchDiff(result);

        /**
         * 判断单个branch是不是hso
         */
        TriggerNum.isBrancnHSO(result);


        /**
         * 统计trigger-sensitive pair
         */
        HashMap<String, Integer> tsPairMap = new HashMap<>();
        for (HSO hso : result) {
            if (hso.hasCommonCaese == true) {
                continue;
            }
            if(!hso.isHSO){
                continue;
            }

            AtomicReference<String> key = new AtomicReference<>("");
            hso.triggerConditionBlock.APIs.forEach(a -> {
                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    if (matcher.group(1).startsWith("<java.util.Locale")
                            || matcher.group(1).startsWith("<android.telephony.CellIdentity")
                            || matcher.group(1).contains("GpsStatus")
                            || matcher.group(1).contains("Geocoder")
                            || matcher.group(1).contains("GpsSatellite")
                            || matcher.group(1).contains("Criteria")
                            || matcher.group(1).contains("android.telephony.gsm.GsmCellLocation")
                            || matcher.group(1).contains("android.telephony.NeighboringCellInfo")
                            || matcher.group(1).contains("Locale")
                            || matcher.group(1).contains("Address")
                            || matcher.group(1).contains("getSimCountryIso")
                            || matcher.group(1).toLowerCase().contains("gps")
                            || (matcher.group(1).startsWith("<android.location.Location") && !matcher.group(1).startsWith("<android.location.Location: long getTime()>"))
                    ) {
                        key.set(matcher.group(1));
                        //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
                    }
                }
            });

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<java.util.Calendar:")
                                || matcher.group(1).startsWith("<java.util.Date:")
                                || matcher.group(1).startsWith("<android.location.Location: long getTime()>")
                                || matcher.group(1).startsWith("<java.util.TimeZone:")
                                || matcher.group(1).contains("Time")
                                || matcher.group(1).contains("time")
                                || matcher.group(1).contains("Calendar")
                        ) {
                            key.set(matcher.group(1));
                            //System.out.println(hso.file);
                            //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.telephony.SmsManager:")
                                || matcher.group(1).startsWith("<android.os.Message:")
                                || matcher.group(1).startsWith("<android.telephony.SmsMessage:")
                                || matcher.group(1).startsWith("<org.apache.http.message")
                        ) {
                            key.set(matcher.group(1));
                            //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
                        }
                    }
                });
            }


            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.accounts.")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<java.util.Random:")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.os.Build: java.lang.String MODEL>")
                                || matcher.group(1).startsWith("<android.os.Build: java.lang.String MANUFACTURER>")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.provider.Settings$System")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<java.util.Properties:")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (
                                matcher.group(1).contains("PackageManager")
                                        ||  matcher.group(1).contains("<android.app.usage.UsageStats: java.lang.String getPackageName()>")
                        ) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.database.")
                        ) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (
                                matcher.group(1).startsWith("<android.telephony.TelephonyManager")
                        ) {
                            key.set(matcher.group(1));
                            //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<java.net.InetAddress: java.lang.String getHostAddress()>")
                                || matcher.group(1).startsWith("<java.net.InetSocketAddress: java.lang.String getHostName()>")
                                || matcher.group(1).startsWith("<android.net.Proxy: java.lang.String getDefaultHost()>")
                                || matcher.group(1).startsWith("<android.net.wifi.WifiInfo: int getIpAddress()>")
                                || matcher.group(1).contains("getHost")
                        ) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (
                                matcher.group(1).startsWith("<android.bluetooth.BluetoothAdapter: java.lang.String getAddress()>")
                                        || matcher.group(1).startsWith("<android.bluetooth.BluetoothHeadset: java.util.List getConnectedDevices()>")
                        ) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<java.security.MessageDigest")
                        ) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).contains("java.io.File")
                        ) {
                            key.set(matcher.group(1));
                            //System.out.println("File"+hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.os.Bundle:")
                        ) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (
                                matcher.group(1).startsWith("<java.nio.ByteBuffer:")
                                        || matcher.group(1).startsWith("<java.io.")
                                        || matcher.group(1).startsWith("<java.nio.")
                        ) {
                            key.set(matcher.group(1));
                            //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.view.")) {
                            key.set(matcher.group(1));
                            //System.out.println(hso.sensitiveStmt.APIs);
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.app.Activity")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }


            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (
                                matcher.group(1).startsWith("<android.net")
                                        || matcher.group(1).startsWith("<java.net.")
                        ) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }


            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (
                                matcher.group(1).startsWith("<android.bluetooth.")
                        ) {
                            key.set(matcher.group(1));
                            //System.out.println("====="+hso.triggerConditionBlock.APIs + "------"+hso.sensitiveStmt.APIs);
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.content.Context: android.content.Context getApplicationContext()>")) {
                            key.set(matcher.group(1));
                            //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.webkit")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.content.Context")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.widget")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.content.Intent:")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.content.SharedPreferences:")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.triggerConditionBlock.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (matcher.group(1).startsWith("<android.media.")) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                hso.sensitiveStmt.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        if (
                                matcher.group(1).startsWith("<android.app.NotificationManager: void notify(int,android.app.Notification)>")
                                        || matcher.group(1).startsWith("<android.app.Notification")
                        ) {
                            key.set(matcher.group(1));
                        }
                    }
                });
            }

            if (key.get() == "") {
                StringBuilder tmp = new StringBuilder();
                hso.sensitiveStmt.APIs.forEach(a -> {
                    Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(a);
                    if (matcher.find()) {
                        tmp.append(matcher.group(1));
                    }
                });
                key.set(tmp.toString());
            }

            String sensitiveAPI = "";
            if(hso.ifBranchIsHSO){
                sensitiveAPI = hso.ifSensitive.get(0);
            }
            else if(hso.elseBranchIsHSO){
                sensitiveAPI = hso.elseSensitive.get(0);
            }

            String keyValue = key + "------" +sensitiveAPI;
            if (StringUtils.isNotBlank(keyValue)) {
                if (tsPairMap.containsKey(keyValue)) {
                    tsPairMap.put(keyValue, tsPairMap.get(keyValue) + 1);
                } else {
                    tsPairMap.put(keyValue, 1);
                }
            }
        }

        //sort
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(tsPairMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        for (Map.Entry<String, Integer> t : list) {
            System.out.println(t.getKey() + ":" + t.getValue());
        }
    }

}
