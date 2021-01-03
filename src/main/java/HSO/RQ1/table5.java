package HSO.RQ1;

import HSO.model.HSO;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class table5 {

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


        HashMap<String, Integer> categoryHashMap = new HashMap<>();
        for (HSO hso : result) {
            if (hso.hasCommonCaese == true) {
                continue;
            }
            if (!hso.isHSO) {
                continue;
            }

            if (!hso.branchDiff) {
                continue;
            }

//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<java.util.Locale")
//                            || matcher.group(1).startsWith("<android.telephony.CellIdentity")
//                            || matcher.group(1).contains("GpsStatus")
//                            || matcher.group(1).contains("Geocoder")
//                            || matcher.group(1).contains("GpsSatellite")
//                            || matcher.group(1).contains("Criteria")
//                            || matcher.group(1).contains("android.telephony.gsm.GsmCellLocation")
//                            || matcher.group(1).contains("CdmaCellLocation")
//                            || matcher.group(1).contains("<android.telephony.CellInfoWcdma:")
//                            || matcher.group(1).contains("android.telephony.CellIdentityGsm")
//                            || matcher.group(1).contains("android.telephony.CellInfoCdma")
//                            || matcher.group(1).contains("<android.telephony.CellLocation:")
//                            || matcher.group(1).contains("getSimCountryIso")
//                            || matcher.group(1).contains("getCellLocation")
//                            || matcher.group(1).toLowerCase().contains("location")
//                            || matcher.group(1).contains("<android.telephony.NeighboringCellInfo")
//                            || matcher.group(1).contains("<android.telephony.CellIdentityCdma")
//                            || matcher.group(1).contains("<android.telephony.CellIdentityLte:")
//                            || matcher.group(1).contains("<android.telephony.CellIdentityWcdma:")
//                            || matcher.group(1).contains("<android.telephony.CellInfoLte:")
//                            || matcher.group(1).contains("<android.telephony.CellInfoGsm:")
//                            || matcher.group(1).contains("<android.telephony.TelephonyManager: java.util.List getNeighboringCellInfo()>")
//                            || matcher.group(1).contains("<android.telephony.TelephonyManager: java.lang.String getSimCountryIso()>")
//                            || matcher.group(1).toLowerCase().contains("gps")
//                            || (matcher.group(1).startsWith("<android.location.Location") && !matcher.group(1).startsWith("<android.location.Location: long getTime()>"))
//                    ) {
//                        TriggerNum.put2Map(matcher.group(1), categoryHashMap);
//                        //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
//                    }
//                }
//            });

//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<java.util.Calendar:")
//                            || matcher.group(1).startsWith("<java.util.Date:")
//                            || matcher.group(1).startsWith("<android.location.Location: long getTime()>")
//                            || matcher.group(1).startsWith("<java.util.TimeZone:")
//                            || matcher.group(1).contains("Time")
//                            || matcher.group(1).contains("time")
//                            || matcher.group(1).contains("Calendar")
//                    ) {
//                        TriggerNum.put2Map(matcher.group(1), categoryHashMap);
//                        //System.out.println(hso.file);
//                        //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.telephony.SmsManager:")
//                            || matcher.group(1).startsWith("<android.os.Message:")
//                            || matcher.group(1).startsWith("<android.telephony.SmsMessage:")
//                            || matcher.group(1).startsWith("<org.apache.http.message")
//                            || matcher.group(1).startsWith("<android.provider.Telephony$Sms")
//                            || matcher.group(1).contains("getMessagesFromIntent")
//                            || matcher.group(1).startsWith("<java.security.MessageDigest:")
//
//                    ) {
//                        TriggerNum.put2Map(matcher.group(1), categoryHashMap);
//                        //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
//                    }
//                }
//            });

//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.accounts.")) {
//                        TriggerNum.put2Map(matcher.group(1), categoryHashMap);
//                    }
//                }
//            });
//
            hso.triggerConditionBlock.APIs.forEach(a -> {
                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    if (matcher.group(1).startsWith("<android.os.Build: java.lang.String MODEL>")
                            || matcher.group(1).startsWith("<android.os.Build: java.lang.String MANUFACTURER>")
                            || matcher.group(1).startsWith("<android.os.Build: java.lang.String DEVICE")
                            || matcher.group(1).startsWith("<android.os.Build: java.lang.String BRAND")
                            || matcher.group(1).startsWith("<android.os.Build: java.lang.String FINGERPRINT")
                            || matcher.group(1).startsWith("<java.util.Properties:")
                            || matcher.group(1).startsWith("<android.provider.Settings$System")
                    ) {
                        TriggerNum.put2Map(matcher.group(1), categoryHashMap);
                    }
                }
            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.provider.Settings$System")) {
//                        key.set("Settings$System:");
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<java.util.Properties:")) {
//                        key.set("Properties:");
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).contains("PackageManager")
//                                    || matcher.group(1).contains("<android.app.usage.UsageStats: java.lang.String getPackageName()>")
//                                    || matcher.group(1).contains("<android.content.ComponentName: java.lang.String getPackageName()>")
//                                    || matcher.group(1).contains("<android.content.Context: java.lang.String getPackageName()>")
//                                    || matcher.group(1).contains("<android.content.pm.PackageInfo: int versionCode")
//                                    || matcher.group(1).contains("<android.content.pm.PackageInfo: java.lang.String packageName>")
//                                    || matcher.group(1).contains("<android.content.pm.PackageManager:")
//
//
//                    ) {
//                        key.set("PackageManager || getPackageName:");
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.database.")
//                    ) {
//                        key.set("database:");
//                        //System.out.println(hso.file+"======"+hso.declareMethod);
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<android.telephony.TelephonyManager")
//                    ) {
//                        TriggerNum.put2Map(matcher.group(1), categoryHashMap);
//                        //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<java.net.InetAddress: java.lang.String getHostAddress()>")
//                            || matcher.group(1).startsWith("<java.net.InetSocketAddress: java.lang.String getHostName()>")
//                            || matcher.group(1).startsWith("<android.net.Proxy: java.lang.String getDefaultHost()>")
//                            || matcher.group(1).startsWith("<android.net.wifi.WifiInfo: int getIpAddress()>")
//                            || matcher.group(1).startsWith("<android.net.Proxy: java.lang.String getHost(android.content.Context)>")
//                            || matcher.group(1).contains("getHost")
//                            || matcher.group(1).startsWith("<java.net.Socket: java.net.InetAddress getInetAddress()>")
//                            || matcher.group(1).startsWith("<java.net.InetSocketAddress: int getPort()>")
//
//                    ) {
//                        key.set("IP Address:");
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<android.bluetooth.BluetoothAdapter: java.lang.String getAddress()>")
//                                    || matcher.group(1).startsWith("<android.bluetooth.BluetoothHeadset: java.util.List getConnectedDevices()>")
//                                    || matcher.group(1).startsWith("<android.net.wifi.WifiInfo: java.lang.String getMacAddress()>")
//
//                    ) {
//                        key.set("hardware Address:");
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<java.nio.ByteBuffer:")
//                                    || matcher.group(1).startsWith("<java.io.")
//                                    || matcher.group(1).startsWith("<java.nio.")
//                    ) {
//                        key.set("IO:");
//                        //System.out.println(hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).contains("java.io.File")
//                    ) {
//                        key.set("File:");
//                        //System.out.println("File"+hso.file+"======"+hso.triggerConditionBlock.APIs+"---------"+hso.sensitiveStmt.APIs);
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.os.Bundle:")
//                    ) {
//                        //key.set("Bundle:");
//                    }
//                }
//            });
//
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<android.net")
//                                    || matcher.group(1).startsWith("<java.net.")
//                    ) {
//                        key.set("net:");
//                    }
//                }
//            });


        }
        //sort
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(categoryHashMap.entrySet());
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
