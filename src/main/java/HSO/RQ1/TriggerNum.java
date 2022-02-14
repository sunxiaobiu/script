package HSO.RQ1;

import HSO.model.HSO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.ApplicationClassFilter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.System;

public class TriggerNum {

    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];
        String neededFilePath = args[1];
        File neededFileFile = new File(neededFilePath);
        List<String> neededFiles = new ArrayList<>(Files.readAllLines(neededFileFile.toPath(), StandardCharsets.UTF_8));

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath, txtFileNames);

        txtFileNames = txtFileNames.stream().filter(txtFileName ->{
            return neededFiles.contains(txtFileName);
        }).collect(Collectors.toList());

        List<HSO> result = new ArrayList<>();

        /**
         * load HSO From File
         */
        loadHSOFromFile(outputFilePath, txtFileNames, result);

        /**
         * filter common cases
         */
        filterCommonCases(result);

        /**
         * filter data dependency
         */
        filterDataDependency(result);

        /**
         * 判断trigger-branch差异
         */
        branchDiff(result);

        /**
         * 判断单个branch是不是hso
         */
        isBrancnHSO(result);

        /**
         * 统计hso总数
         */
//        int hsoNumCCs = 0;
//        int hsoNumNoCCs = 0;
//        for(HSO hso : result){
//            if (
//                    hso.hasCommonCaese == false &&
//                        hso.isHSO) {
//                hsoNumNoCCs++;
//            }
//        }
//        System.out.println("hsoNumNoCCs:"+hsoNumNoCCs);

        /**
         * 统计common cases个数
         */
        int num = 0;
        for (HSO hso : result) {
            if (!hso.isHSO) {
                continue;
            }

            if (
                    hso.hasCommonCaese == true) {
                num++;
            }
        }
        System.out.println("common cases:" + num);

//        /**
//         * 统计所有的trigger数量
//         */
//        int num = 0;
//        for(HSO hso : result){
//            if (!hso.isHSO) {
//                continue;
//            }
//
//           Set<String> triggers = hso.triggerConditionBlock.APIs.stream().map(item->{
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(item);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<java.lang")
//                            || matcher.group(1).startsWith("<android.support.v4")
//                            || matcher.group(1).startsWith("<android.os.Looper")
//                            || matcher.group(1).startsWith("<android.os.Looper")
//                            || matcher.group(1).contains("<android.content.SharedPreferences: ")
//                            || matcher.group(1).contains("<java.net.InetSocketAddress: int getPort()")
//                            || matcher.group(1).contains("<android.net.Proxy: int getPort(android.content.Context)>")
//                            || matcher.group(1).contains("<android.net.Proxy: int getDefaultPort()>")
//                    ) {
//                        return "";
//                    }else{
//                        System.out.println(item);
//                        return getSubUtilSimple(item, "(<.*?:)");
//                    }
//                }
//                return "";
//            }).collect(Collectors.toSet());
//
//            Set<String> res = triggers.stream().filter(a->{
//                return StringUtils.isNotBlank(a.toString());
//            }).collect(Collectors.toSet());
//
//            if(CollectionUtils.isNotEmpty(res)){
//                num += res.size();
//            }
//        }
//
//        System.out.println("Trigger sum:"+num);
        HashMap<String, Integer> apkHSONumber = new HashMap<>();

        /**
         * 分类统计trigger condition
         */
        int hsoNumNoCCs = 0;
        HashMap<String, Integer> tcbHashMap = new HashMap<>();
        int triggerNum = 0;
        for (HSO hso : result) {
            if (hso.hasCommonCaese == true) {
                continue;
            }
            if (!hso.isHSO) {
                continue;
            }

            boolean flag = false;
            AtomicReference<String> keyLocation = new AtomicReference<>("");
            checkLocation(hso, keyLocation);
            if (StringUtils.isNotBlank(keyLocation.get())) {
                put2Map(hso.file, apkHSONumber);
                put2Map(keyLocation, tcbHashMap);
                flag = true;
                triggerNum ++;
            }

            if(flag == false) {
                AtomicReference<String> keyTime = new AtomicReference<>("");
                checkTime(hso, keyTime);
                if (StringUtils.isNotBlank(keyTime.get())) {
                    put2Map(hso.file, apkHSONumber);
                    put2Map(keyTime, tcbHashMap);
                    flag = true;
                    triggerNum++;
                }
            }

            if(flag == false) {
                AtomicReference<String> keySMS = new AtomicReference<>("");
                checkSMS(hso, keySMS);
                if (StringUtils.isNotBlank(keySMS.get())) {
                    put2Map(hso.file, apkHSONumber);
                    put2Map(keySMS, tcbHashMap);
                    flag = true;
                    triggerNum++;
                }
            }

            if(flag == false) {
                AtomicReference<String> keyAdroidOS = new AtomicReference<>("");
                checkAdroidOS(hso, keyAdroidOS);
                if (StringUtils.isNotBlank(keyAdroidOS.get())) {
                    put2Map(hso.file, apkHSONumber);
                    put2Map(keyAdroidOS, tcbHashMap);
                    flag = true;
                    triggerNum++;
                }
            }

            if(flag == false) {
                AtomicReference<String> keyPackage = new AtomicReference<>("");
                checkPackage(hso, keyPackage);
                if (StringUtils.isNotBlank(keyPackage.get())) {
                    put2Map(hso.file, apkHSONumber);
                    put2Map(keyPackage, tcbHashMap);
                    flag = true;
                    triggerNum++;
                }
            }

            if(flag == false) {
                AtomicReference<String> keyHardwareAddress = new AtomicReference<>("");
                checkHardwareAddress(hso, keyHardwareAddress);
                if (StringUtils.isNotBlank(keyHardwareAddress.get())) {
                    put2Map(hso.file, apkHSONumber);
                    put2Map(keyHardwareAddress, tcbHashMap);
                    flag = true;
                    triggerNum++;
                }
            }

            if(flag == false) {
                AtomicReference<String> keyCheckContent = new AtomicReference<>("");
                checkContent(hso, keyCheckContent);
                if (StringUtils.isNotBlank(keyCheckContent.get())) {
                    put2Map(hso.file, apkHSONumber);
                    put2Map(keyCheckContent, tcbHashMap);
                    flag = true;
                    triggerNum ++;
                }
            }

            if(flag == false){
                AtomicReference<String> keyAll = new AtomicReference<>("");
                checkAll(hso, keyAll);
                if (StringUtils.isNotBlank(keyAll.get())) {
                    put2Map(hso.file, apkHSONumber);
                    put2Map(keyAll, tcbHashMap);
                    flag = true;
                    triggerNum ++;
                }
            }

            if(flag == false){
                triggerNum ++;
            }

            if(flag){
                hsoNumNoCCs ++;
            }
        }

        System.out.println("hsoNumNoCCs:"+hsoNumNoCCs);

//        //sort
//        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(tcbHashMap.entrySet());
//        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return (o2.getValue() - o1.getValue());
//            }
//        });
//        for (Map.Entry<String, Integer> t : list) {
//            System.out.println(t.getKey() + ":" + t.getValue());
//        }

        System.out.println("================apkHSONumber================");

        //sort apkHSONumber
        List<Map.Entry<String, Integer>> apkHSONumberList = new ArrayList<Map.Entry<String, Integer>>(apkHSONumber.entrySet());
//        Collections.sort(apkHSONumberList, new Comparator<Map.Entry<String, Integer>>() {
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return (o2.getValue() - o1.getValue());
//            }
//        });
        for (Map.Entry<String, Integer> t : apkHSONumberList) {
            System.out.println(t.getKey() + ":" + t.getValue());
        }

//        /**
//         * 分类统计sensitive apis
//         */
//        System.out.println("-------------sensitive apis-----------");
//        HashMap<String, Integer> sensitiveAPIHashMap = new HashMap<>();
//        HashMap<String, Integer> notification = new HashMap<>();
//        int total = 0;
//        for (HSO hso : result) {
//
//            if (hso.hasCommonCaese == true) {
//                continue;
//            }
//            if (!hso.isHSO) {
//                continue;
//            }
//
//            Set<String> presentSensitiveAPIs = new HashSet<>();
//            if (hso.ifBranchIsHSO) {
//                total += hso.ifSensitive.size();
//                presentSensitiveAPIs.addAll(hso.ifSensitive);
//            }
//            if (hso.elseBranchIsHSO) {
//                total += hso.elseSensitive.size();
//                presentSensitiveAPIs.addAll(hso.elseSensitive);
//            }
//
//            for (String sensitiveAPI : presentSensitiveAPIs) {
//                put2Map(getSubUtilSimple(sensitiveAPI, "(<.*>)"), sensitiveAPIHashMap);
//            }
//        }
//
//        //sort
//        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(sensitiveAPIHashMap.entrySet());
//        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return (o2.getValue() - o1.getValue());
//            }
//        });
//        for (Map.Entry<String, Integer> t : list2) {
//            System.out.println(t.getKey() + ":" + t.getValue());
//        }
//
//        /**
//         * 统计pairs
//         */
//        Set<String> triggerSet = new HashSet<>();
//        Set<String> sensitiveSet = new HashSet<>();
//        HashMap<String, Integer> pairHashMap = new HashMap<>();
//        for (HSO hso : result) {
//
//            if (hso.hasCommonCaese == true) {
//                continue;
//            }
//            if (!hso.isHSO) {
//                continue;
//            }
//
//            Set<String> sensitiveAPIs = new HashSet<>();
//            if (hso.ifBranchIsHSO) {
//                sensitiveAPIs.addAll(hso.ifSensitive);
//                sensitiveSet.addAll(hso.ifSensitive);
//            }
//            if (hso.elseBranchIsHSO) {
//                sensitiveAPIs.addAll(hso.elseSensitive);
//                sensitiveSet.addAll(hso.elseSensitive);
//            }
//
//            Set<String> triggerAPIs = new HashSet<>();
//            hso.triggerConditionBlock.APIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    triggerAPIs.add(matcher.group(1));
//                   if(!matcher.group(1).contains("<android.content.SharedPreferences: ")
//                            && !matcher.group(1).contains("<java.net.InetSocketAddress: int getPort()")
//                            && !matcher.group(1).contains("<android.net.Proxy: int getPort(android.content.Context)>")
//                            && !matcher.group(1).contains("<android.net.Proxy: int getDefaultPort()>")
//                            && !matcher.group(1).contains("<android.content.Context")
//                   ){
//                       triggerSet.add(matcher.group(1));
//                   }
//
//
//                }
//            });
//
//            //for(String key : triggerAPIs){
//                Set<String> categories = getCategory(hso);
//                String category = "";
//                if(CollectionUtils.isEmpty(categories)){
//                    category = "";
//                    Set<String> realTriggers = triggerAPIs.stream().filter(item->{
//                        return !item.contains("<android.content.SharedPreferences: ")
//                         && !item.contains("<java.net.InetSocketAddress: int getPort()")
//                         && !item.contains("<android.net.Proxy: int getPort(android.content.Context)>")
//                         && !item.contains("<android.net.Proxy: int getDefaultPort()>")
//                                ;
//                    }).collect(Collectors.toSet());
//                    for(String key : realTriggers) {
//                        for (String value : sensitiveAPIs) {
//                            String keyValue = "other=======" + key + "------" + value;
//                            if (StringUtils.isNotBlank(keyValue)) {
//                                if (pairHashMap.containsKey(keyValue)) {
//                                    pairHashMap.put(keyValue, pairHashMap.get(keyValue) + 1);
//                                } else {
//                                    pairHashMap.put(keyValue, 1);
//                                }
//                            }
//                        }
//                    }
//                }else{
//                    for(String c : categories){
//                        for(String value : sensitiveAPIs){
//                            String keyValue = c + "======="+ hso.coreAPIInTrigger + "------" +value;
//                            if (StringUtils.isNotBlank(keyValue)) {
//                                if (pairHashMap.containsKey(keyValue)) {
//                                    pairHashMap.put(keyValue, pairHashMap.get(keyValue) + 1);
//                                } else {
//                                    pairHashMap.put(keyValue, 1);
//                                }
//                            }
//                        }
//                    }
//                }
//            //}
//        }
//
//        List<Map.Entry<String, Integer>> pairList = new ArrayList<Map.Entry<String, Integer>>(pairHashMap.entrySet());
//        Collections.sort(pairList, new Comparator<Map.Entry<String, Integer>>() {
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return (o2.getValue() - o1.getValue());
//            }
//        });
//        System.out.println("========================pairHashMap========================");
//        for (Map.Entry<String, Integer> t : pairList) {
//            System.out.println(t.getKey() + ":" + t.getValue());
//        }

    }

    private static Set<String> getCategory(HSO hso){
        Set<String> res = new HashSet<>();
        HashMap<String, Integer> tcbHashMap = new HashMap<>();
        boolean flag = false;
        AtomicReference<String> keyLocation = new AtomicReference<>("");
        checkLocation(hso, keyLocation);
        if (StringUtils.isNotBlank(keyLocation.get())) {
            put2Map(keyLocation, tcbHashMap);
            flag = true;
            res.add(keyLocation.get());
        }

        AtomicReference<String> keyTime = new AtomicReference<>("");
        checkTime(hso, keyTime);
        if (StringUtils.isNotBlank(keyTime.get())) {
            put2Map(keyTime, tcbHashMap);
            flag = true;
            res.add(keyTime.get());
        }

        AtomicReference<String> keySMS = new AtomicReference<>("");
        checkSMS(hso, keySMS);
        if (StringUtils.isNotBlank(keySMS.get())) {
            put2Map(keySMS, tcbHashMap);
            flag = true;
            res.add(keySMS.get());
        }


        //if (keyAccounts.get() == "") {
        AtomicReference<String> keyAdroidOS = new AtomicReference<>("");
        checkAdroidOS(hso, keyAdroidOS);
        if (StringUtils.isNotBlank(keyAdroidOS.get())) {
            put2Map(keyAdroidOS, tcbHashMap);
            flag = true;
            res.add(keyAdroidOS.get());
        }

        //if (key.get() == "") {
        AtomicReference<String> keyPackage = new AtomicReference<>("");
        checkPackage(hso, keyPackage);
        if (StringUtils.isNotBlank(keyPackage.get())) {
            put2Map(keyPackage, tcbHashMap);
            flag = true;
            res.add(keyPackage.get());

        }
        return res;
    }


    public static void checkAll(HSO hso, AtomicReference<String> key) {
        hso.triggerConditionBlock.APIs.forEach(a -> {
            Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(a);
            if (matcher.find()) {
                if (
                        !matcher.group(1).startsWith("<java.lang.")
                                && !matcher.group(1).startsWith("<android.support")
                                && !matcher.group(1).contains("SharedPreferences")
                                && !matcher.group(1).startsWith("<java.util.")
                                && !matcher.group(1).contains("<android.content.Intent:")
                                && !matcher.group(1).contains("Looper")
                                && !matcher.group(1).contains("<android.net.Proxy: int getDefaultPort()>")
                                && !matcher.group(1).contains("<android.net.Proxy: java.lang.String getDefaultHost()>")
                                && !matcher.group(1).contains("<android.app.AlertDialog: boolean isShowing()>")
                                && !matcher.group(1).contains("<android.database.Cursor: long getLong(int)>")
                                && !matcher.group(1).contains("<android.app.Activity: boolean isFinishing()>")
                                && !matcher.group(1).contains("<android.webkit.URLUtil: boolean isValidUrl(java.lang.String)>")
                                && !matcher.group(1).contains("<android.app.Activity: boolean mFinished>")
                                && !matcher.group(1).contains("<android.net.Proxy: int getPort(android.content.Context)>")
                                && !matcher.group(1).contains("<java.net.InetSocketAddress: int getPort()>")
                                && !matcher.group(1).contains("<java.net.InetSocketAddress: java.lang.String getHostName()>")
                                && !matcher.group(1).contains("<android.bluetooth.BluetoothAdapter: android.bluetooth.BluetoothAdapter getDefaultAdapter()>")
                                && !matcher.group(1).contains("<android.app.Activity: int getRequestedOrientation()>")
                                && !matcher.group(1).contains("<android.net.Proxy: java.lang.String getHost(android.content.Context)>")
                                && !matcher.group(1).contains("<android.app.IActivityManager: int getRequestedOrientation(android.os.IBinder)>")
                                && !matcher.group(1).contains("<java.net.InetAddress: java.net.InetAddress getByName(java.lang.String)>")
                                && !matcher.group(1).contains("<com.google.zxing.client.result.WifiParsedResult: java.lang.String getNetworkEncryption()>")
                                && !matcher.group(1).contains("<com.google.android.gms.ads.formats.NativeContentAd: java.util.List getImages()>")
                                && !matcher.group(1).contains("<android.hardware.Camera$CameraInfo: int facing>")
                                && !matcher.group(1).contains("<android.app.WallpaperManager: android.app.WallpaperManager getInstance(android.content.Context)>")
                                && !matcher.group(1).contains("<com.google.android.gms.")
                                && !matcher.group(1).contains("<android.widget.")
                                && !matcher.group(1).contains("<android.bluetooth")
                                && !matcher.group(1).contains("<android.app")

                ) {
                    key.set(matcher.group(1));
                    hso.coreAPIInTrigger = matcher.group(1);
                    System.out.println(hso.coreAPIInTrigger);
                }

            }
        });
    }

    public static void checkHardwareAddress(HSO hso, AtomicReference<String> key) {
        hso.triggerConditionBlock.APIs.forEach(a -> {
            Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(a);
            if (matcher.find()) {
                if (
                        matcher.group(1).startsWith("<android.bluetooth.BluetoothAdapter: java.lang.String getAddress()>")
                                || matcher.group(1).startsWith("<android.bluetooth.BluetoothHeadset: java.util.List getConnectedDevices()>")
                                || matcher.group(1).startsWith("<android.net.wifi.WifiInfo: java.lang.String getMacAddress()>")
                                || matcher.group(1).startsWith("<java.net.NetworkInterface: byte[] getHardwareAddress()>")

                ) {
                    key.set("hardware Address:");
                    hso.coreAPIInTrigger = matcher.group(1);
                    System.out.println(hso.coreAPIInTrigger);
                    //System.out.println("hardware Address:"+hso.file+"======"+hso.declareMethod+"----------"+hso.ifStatement+"---------"+hso.sensitiveStmt.APIs);
                }
            }
        });
    }

    private static void checkContent(HSO hso, AtomicReference<String> key) {
        hso.triggerConditionBlock.APIs.forEach(a -> {
            Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(a);
            if (matcher.find()) {
                if (
                        matcher.group(1).startsWith("<android.content")

                ) {
                    key.set("android content:");
                    hso.coreAPIInTrigger = matcher.group(1);
                    System.out.println(hso.coreAPIInTrigger);
                    //System.out.println("android content:"+hso.file+"======"+hso.declareMethod+"----------"+hso.ifStatement+"---------"+hso.sensitiveStmt.APIs);
                }
            }
        });
    }



    public static void checkPackage(HSO hso, AtomicReference<String> key) {
        hso.triggerConditionBlock.APIs.forEach(a -> {
            Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(a);
            if (matcher.find()) {
                if (
                        matcher.group(1).contains("PackageManager")
                                || matcher.group(1).contains("<android.app.usage.UsageStats: java.lang.String getPackageName()>")
                                || matcher.group(1).contains("<android.content.ComponentName: java.lang.String getPackageName()>")
                                || matcher.group(1).contains("<android.content.Context: java.lang.String getPackageName()>")
                                || matcher.group(1).contains("<android.content.pm.PackageInfo: int versionCode")
                                || matcher.group(1).contains("<android.content.pm.PackageInfo: java.lang.String packageName>")
                                || matcher.group(1).contains("<android.content.pm.PackageManager:")


                ) {
                    key.set("Package Manager");
                    hso.coreAPIInTrigger = matcher.group(1);
                    System.out.println(hso.coreAPIInTrigger);
                    //System.out.println("Package Manager:"+hso.file+"======"+hso.declareMethod+"----------"+hso.ifStatement+"---------"+hso.sensitiveStmt.APIs);
                }
            }
        });
    }

    public static void checkAdroidOS(HSO hso, AtomicReference<String> key) {
        hso.triggerConditionBlock.APIs.forEach(a -> {
            Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(a);
            if (matcher.find()) {
                if (matcher.group(1).startsWith("<android.os.Build: java.lang.String MODEL>")
                        || matcher.group(1).startsWith("<android.os.Build: java.lang.String MANUFACTURER>")
                        || matcher.group(1).startsWith("<android.os.Build: java.lang.String DEVICE")
                        || matcher.group(1).startsWith("<android.os.Build: java.lang.String BOARD")
                        || matcher.group(1).startsWith("<android.os.Build: java.lang.String BRAND")
                        || matcher.group(1).startsWith("<android.os.Build: java.lang.String FINGERPRINT")
                        || matcher.group(1).startsWith("<android.os.Build: java.lang.String TAGS")
                        || matcher.group(1).startsWith("<java.util.Properties:")
                        || matcher.group(1).startsWith("<android.provider.Settings$System")
                        || (matcher.group(1).startsWith("<android.telephony.TelephonyManager") && !matcher.group(1).startsWith("<android.telephony.TelephonyManager: java.lang.String getSimCountryIso()>") && !matcher.group(1).startsWith("<android.telephony.TelephonyManager: android.telephony.CellLocation getCellLocation()>"))
                        || matcher.group(1).startsWith("<android.os.Build$VERSION")
                        || matcher.group(1).startsWith("<android.provider.Settings$Secure: ")

                ) {
                    key.set("System Properties");
                    hso.coreAPIInTrigger = matcher.group(1);
                    //System.out.println(hso.coreAPIInTrigger);

                }
            }
        });
    }


    public static void checkSMS(HSO hso, AtomicReference<String> key) {
        hso.triggerConditionBlock.APIs.forEach(a -> {
            Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(a);
            if (matcher.find()) {
                if (matcher.group(1).startsWith("<android.telephony.SmsManager:")
                        || matcher.group(1).startsWith("<android.os.Message:")
                        || matcher.group(1).startsWith("<android.telephony.SmsMessage:")
                        || matcher.group(1).startsWith("<org.apache.http.message")
                        || matcher.group(1).startsWith("<android.provider.Telephony$Sms")
                        || matcher.group(1).contains("getMessagesFromIntent")
                        || matcher.group(1).startsWith("<java.security.MessageDigest:")

                ) {
                    key.set("SMS");
                    hso.coreAPIInTrigger = matcher.group(1);
                    System.out.println(hso.coreAPIInTrigger);
                    //System.out.println("SMS:"+hso.file+"======"+hso.declareMethod+"----------"+hso.ifStatement+"---------"+hso.sensitiveStmt.APIs);
                }
            }
        });
    }

    public static void checkTime(HSO hso, AtomicReference<String> key) {
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
                        || matcher.group(1).contains("DateUtils")
                ) {
                    key.set("Time");
                    hso.coreAPIInTrigger = matcher.group(1);
                    System.out.println(hso.coreAPIInTrigger);
                    //System.out.println("Time:"+hso.file+"======"+hso.declareMethod+"----------"+hso.ifStatement+"---------"+hso.sensitiveStmt.APIs);
                }
            }
        });
    }

    public static void checkLocation(HSO hso, AtomicReference<String> key) {
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
                        || matcher.group(1).contains("CdmaCellLocation")
                        || matcher.group(1).contains("<android.telephony.CellInfoWcdma:")
                        || matcher.group(1).contains("android.telephony.CellIdentityGsm")
                        || matcher.group(1).contains("android.telephony.CellInfoCdma")
                        || matcher.group(1).contains("<android.telephony.CellLocation:")
                        || matcher.group(1).contains("getSimCountryIso")
                        || matcher.group(1).contains("getCellLocation")
                        || matcher.group(1).toLowerCase().contains("location")
                        || matcher.group(1).contains("<android.telephony.NeighboringCellInfo")
                        || matcher.group(1).contains("<android.telephony.CellIdentityCdma")
                        || matcher.group(1).contains("<android.telephony.CellIdentityLte:")
                        || matcher.group(1).contains("<android.telephony.CellIdentityWcdma:")
                        || matcher.group(1).contains("<android.telephony.CellInfoLte:")
                        || matcher.group(1).contains("<android.telephony.CellInfoGsm:")
                        || matcher.group(1).contains("<android.telephony.TelephonyManager: java.util.List getNeighboringCellInfo()>")
                        || matcher.group(1).contains("<android.telephony.TelephonyManager: java.lang.String getSimCountryIso()>")
                        || matcher.group(1).toLowerCase().contains("gps")
                        || (matcher.group(1).startsWith("<android.location.Location") && !matcher.group(1).startsWith("<android.location.Location: long getTime()>"))
                ) {
                    key.set("Location");
                    hso.coreAPIInTrigger = matcher.group(1);
                    System.out.println(hso.coreAPIInTrigger);
                    //System.out.println("Location:"+hso.file+"======"+hso.declareMethod+"----------"+hso.ifStatement+"---------"+hso.sensitiveStmt.APIs);
                }
            }
        });
    }

    public static void branchDiff(List<HSO> result) {
        for (HSO hso : result) {
            Set<String> isSensitivePackage = hso.ifSensitive.stream().map(a -> {
                return getSubUtilSimple(a, "(<.*:)");
            }).collect(Collectors.toSet());

            Set<String> elseSensitivePackage = hso.elseSensitive.stream().map(a -> {
                return getSubUtilSimple(a, "(<.*:)");
            }).collect(Collectors.toSet());

            Set<String> res = new HashSet<String>();
            res.clear();
            res.addAll(isSensitivePackage);
            res.retainAll(elseSensitivePackage);

            if (CollectionUtils.isNotEmpty(res)) {
                //hso.branchDiff = false;
            }

            //
            Set<String> branchConditionSet = hso.triggerConditionBlock.APIs.stream().map(branchCondition -> {
                return getSubUtilSimple(branchCondition, "(<.*:)");
            }).filter(bc -> {
                return ApplicationClassFilter.isClassInSystemPackage(bc);
            }).collect(Collectors.toSet());

            Set<String> ifBranchInvokeMethodSet = hso.ifStmts.stream().map(ifBranchInvokeMethod -> {
                return getSubUtilSimple(ifBranchInvokeMethod, "(<.*:)");
            }).filter(bc -> {
                return ApplicationClassFilter.isClassInSystemPackage(bc);
            }).collect(Collectors.toSet());

            Set<String> elseBranchInvokeMethodSet = hso.elseStmts.stream().map(ifBranchInvokeMethod -> {
                return getSubUtilSimple(ifBranchInvokeMethod, "(<.*:)");
            }).filter(bc -> {
                return ApplicationClassFilter.isClassInSystemPackage(bc);
            }).collect(Collectors.toSet());

            Set<String> intersection1 = new HashSet<String>(branchConditionSet);
            intersection1.retainAll(ifBranchInvokeMethodSet);
            Set<String> filterIntersection1 = intersection1.stream().filter(item -> {
                return !item.startsWith("<java.lang");
            }).collect(Collectors.toSet());

            Set<String> intersection2 = new HashSet<String>(branchConditionSet);
            intersection2.retainAll(elseBranchInvokeMethodSet);
            Set<String> filterIntersection2 = intersection2.stream().filter(item -> {
                return !item.startsWith("<java.lang");
            }).collect(Collectors.toSet());
            ;

            if (CollectionUtils.isNotEmpty(filterIntersection1)) {
                hso.triggerBranchHasSameAPIInIf = true;
            }

            if (CollectionUtils.isNotEmpty(filterIntersection2)) {
                hso.triggerBranchHasSameAPIInElse = true;
            }
        }
    }

    public static void isBrancnHSO(List<HSO> result) {
        for (HSO hso : result) {
            //判断if分支是否满足hso条件
            if (!hso.ifHasDataDependency && CollectionUtils.isNotEmpty(hso.ifSensitive) && !hso.triggerBranchHasSameAPIInIf) {
                hso.ifBranchIsHSO = true;
            }

            //判断else分支是否满足hso条件
            if (!hso.elseHasDataDependency && CollectionUtils.isNotEmpty(hso.elseSensitive) && !hso.triggerBranchHasSameAPIInElse) {
                hso.elseBranchIsHSO = true;
            }

            hso.isHSO = hso.ifBranchIsHSO || hso.elseBranchIsHSO;
        }
    }

    public static void filterDataDependency(List<HSO> result) {
        for (HSO hso : result) {
            Set<String> intersection1 = new HashSet<>(hso.variableInTrigger);
            intersection1.retainAll(hso.variableInIfBranch);
            boolean ifHasDataDependency = CollectionUtils.isNotEmpty(intersection1);

            Set<String> intersection2 = new HashSet<>(hso.variableInTrigger);
            intersection2.retainAll(hso.variableInElseBranch);
            boolean elseHasDataDependency = CollectionUtils.isNotEmpty(intersection2);

            hso.ifHasDataDependency = ifHasDataDependency;
            hso.elseHasDataDependency = elseHasDataDependency;

            //hso.noDataDependency = !ifHasDataDependency || !elseHasDataDependency;
        }
    }

    public static void filterCommonCases(List<HSO> result) {
        for (HSO hso : result) {

            Set<String> sensitiveAPIs = hso.sensitiveStmt.APIs;
            Set<String> ifSensitiveAPIs = new HashSet<String>(hso.ifSensitive);;
            Set<String> elseSensitiveAPIs = new HashSet<String>(hso.elseSensitive);
            Set<String> triggerAPIs = hso.triggerConditionBlock.APIs;
            List<String> commonCases = hso.triggerConditionBlock.APIs.stream().filter(item -> {
                String tcbAPI = "";
                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(item);
                if (matcher.find()) {
                    tcbAPI = matcher.group(1);
                }
                boolean excepptionCases = false;
                excepptionCases = (checkSpecificAPI("<java.util.Locale", triggerAPIs) || checkSpecificAPI("location", triggerAPIs) || checkSpecificAPI("Address", triggerAPIs) || checkSpecificAPI("getSimCountryIso", triggerAPIs)
                        || checkSpecificAPI("Calendar", triggerAPIs) || checkSpecificAPI("Date", triggerAPIs) || checkSpecificAPI("time", triggerAPIs)) && !(checkSpecificAPI("android.permission.ACCESS_FINE_LOCATION", triggerAPIs) || checkSpecificAPI("android.permission.ACCESS_COARSE_LOCATION", triggerAPIs) || checkSpecificAPI("<android.content.Context: java.lang.Object getSystemService(java.lang.String)>(\"location\")", triggerAPIs) || checkSpecificAPI("<android.location.LocationManager: android.location.Location getLastKnownLocation", triggerAPIs))
                ;

                if (excepptionCases) {
                    return false;
                } else {

                    return
                            tcbAPI.contains("permission") || tcbAPI.contains("Permission") || item.contains("android.permission.")
                                    || (tcbAPI.contains("getSystemService"))
                                    || tcbAPI.contains("<android.os.Build$VERSION: int SDK_INT>")
                                    || tcbAPI.contains("<android.content.pm.ApplicationInfo: int targetSdkVersion>")
                                    || tcbAPI.contains("<android.os.Build$VERSION: java.lang.String SDK>")
                                    || tcbAPI.startsWith("<com.google.android.gms.gcm.GoogleCloudMessaging:")
                                    || tcbAPI.startsWith("<com.google.android.gms.gcm.a:")
                                    || tcbAPI.startsWith("<android.os.Looper")
                                    || (checkSpecificAPI("<android.location.LocationManager: android.location.Location getLastKnownLocation", triggerAPIs) && checkSpecificAPI("<android.location.LocationManager: boolean isProviderEnabled(java.lang.String)>", sensitiveAPIs))

                                    || tcbAPI.startsWith("<android.content.Intent: java.lang.String getAction()>")
                                    || tcbAPI.startsWith("<android.content.Intent:")
                                    || tcbAPI.startsWith("<android.content.SharedPreferences")
                                    //file
                                    || tcbAPI.startsWith("<java.io.File: boolean exists()>")
                                    || (tcbAPI.startsWith("<android.os.Environment: java.io.File getExternalStorageDirectory()>") && checkSpecificAPI("<android.media.RingtoneManager: void setActualDefaultRingtoneUri(android.content.Context,int,android.net.Uri)>", sensitiveAPIs) || checkSpecificAPI("<java.net.URL: java.net.URLConnection openConnection()>", sensitiveAPIs) || checkSpecificAPI("<android.app.DownloadManager: long enqueue(android.app.DownloadManager$Request)>", sensitiveAPIs))
                                    || (tcbAPI.startsWith("<android.content.Context: java.io.File getExternalCacheDir()>") && checkSpecificAPI("<java.net.URL: java.io.InputStream openStream()>", sensitiveAPIs))
                                    || (tcbAPI.startsWith("<java.io.File: long length()>") && checkSpecificAPI("<java.net.URL: java.net.URLConnection openConnection()>", sensitiveAPIs) || checkSpecificAPI("<android.webkit.WebView: void loadUrl", sensitiveAPIs) || checkSpecificAPI("<java.net.HttpURLConnection: void connect()>", sensitiveAPIs) || checkSpecificAPI("<org.apache.http.impl.client.DefaultHttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>", sensitiveAPIs))
//                                    //user input
                                    || tcbAPI.contains("<android.view.MenuItem: int getItemId()>") || tcbAPI.contains("<android.widget.Gallery: int getSelectedItemPosition()>") || tcbAPI.contains("<android.widget.Button: java.lang.CharSequence getText()>") || tcbAPI.contains("<android.widget.EditText: android.text.Editable getText()>")
                                    || (tcbAPI.contains("<android.view.KeyEvent: int getKeyCode()>") && checkSpecificAPI("<android.webkit.WebView: void goBack()>", sensitiveAPIs))
                                    //UI模块
                                    || tcbAPI.contains("<android.view.View: int getId()>")
                                    || (tcbAPI.startsWith("<android.view.") && checkSpecificAPI("<android.webkit.WebView:", sensitiveAPIs) || checkSpecificAPI("<android.net.ConnectivityManager:", sensitiveAPIs) || checkSpecificAPI("<android.media.MediaPlayer:", sensitiveAPIs) || checkSpecificAPI("<android.nfc.NfcAdapter:", sensitiveAPIs) || checkSpecificAPI("<android.widget.VideoView:", sensitiveAPIs) || checkSpecificAPI("<android.media.AsyncPlayer:", sensitiveAPIs))
                                    || tcbAPI.contains("<android.app.ProgressDialog: android.view.Window getWindow()>")
                                    || (tcbAPI.contains("<android.webkit.WebView: boolean canGoForward()>") && (checkSpecificAPI("<android.webkit.WebView: void goForward()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.webkit.WebView: boolean canGoBack()>") && (checkSpecificAPI("<android.webkit.WebView: void goBack()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.webkit.WebView: android.webkit.WebBackForwardList copyBackForwardList()>") && checkSpecificAPI("<android.webkit.WebView:", sensitiveAPIs))
                                    || (tcbAPI.contains("<android.widget.PopupWindow: boolean isShowing()>") && checkSpecificAPI("<android.webkit.WebView: void loadUrl(java.lang.String)>", sensitiveAPIs))
                                    || (checkSpecificAPI("removeJavascriptInterface", sensitiveAPIs))
                                    || (tcbAPI.contains("<android.webkit.WebViewProvider: android.webkit.WebSettings getSettings()>") && checkSpecificAPI("<android.webkit.WebView: void loadUrl(java.lang.String)>", sensitiveAPIs))

//                        //WakeLock
                                    || (tcbAPI.contains("android.app.Service: boolean stopSelfResult") && checkSpecificAPI("android.os.PowerManager$WakeLock: void release()", sensitiveAPIs))
                                    || (checkSpecificAPI("android.os.PowerManager$WakeLock: void acquire", sensitiveAPIs) && checkSpecificAPI("android.os.PowerManager$WakeLock: void release", sensitiveAPIs))
                                    || (tcbAPI.contains("<android.os.PowerManager$WakeLock: boolean isHeld()>"))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager$MulticastLock: boolean isHeld()>") && (checkSpecificAPI("<android.net.wifi.WifiManager$MulticastLock: void release()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager$MulticastLock: boolean isHeld()>") && (checkSpecificAPI("<android.net.wifi.WifiManager$MulticastLock: void acquire()>", sensitiveAPIs)))

//                        //other
                                    || (tcbAPI.contains("vibrate") && checkSpecificAPI("android.os.Vibrator: void vibrate", sensitiveAPIs))
                                    || (item.contains("<android.content.res.Resources: java.lang.String getString(int)>") && (checkSpecificAPI("<android.webkit.WebView: void loadUrl(java.lang.String)>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.app.Notification: android.widget.RemoteViews contentView>") && (checkSpecificAPI("<android.app.NotificationManager: void notify(", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.app.KeyguardManager: boolean inKeyguardRestrictedInputMode()>") && (checkSpecificAPI("<android.app.KeyguardManager$KeyguardLock: void disableKeyguard()>", sensitiveAPIs) || checkSpecificAPI("<android.media.MediaPlayer: void pause()>", sensitiveAPIs) || checkSpecificAPI("<android.app.KeyguardManager$KeyguardLock: void reenableKeyguard()>", sensitiveAPIs)))
                                    || tcbAPI.contains("<android.provider.Settings: boolean canDrawOverlays(android.content.Context)>")
                                    || (tcbAPI.contains("<android.util.DisplayMetrics: int densityDpi>"))
                                    || (tcbAPI.contains("<android.bluetooth.IBluetooth: boolean isEnabled()>") && (checkSpecificAPI("<android.bluetooth.BluetoothAdapter: boolean enable()>", sensitiveAPIs) || checkSpecificAPI("<android.bluetooth.BluetoothAdapter: boolean disable()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.os.PowerManager: boolean isScreenOn()>") && (checkSpecificAPI("<android.os.PowerManager$WakeLock: void acquire(long)>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.os.Looper: android.os.Looper myLooper()>") && (checkSpecificAPI("<android.os.Looper: void loop()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<java.io.BufferedReader: java.lang.String readLine()>") && (checkSpecificAPI("<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()>", sensitiveAPIs)))

                                    //media
                                    || (tcbAPI.contains("<android.media.MediaPlayer: boolean isPlaying()>"))
                                    //webkit
                                    || (tcbAPI.contains("<android.webkit.WebHistoryItem: java.lang.String getUrl()>"))
                                    //TelephonyManager
                                    || (tcbAPI.contains("<android.telephony.TelephonyManager: java.lang.String getDeviceId()>") && checkSpecificAPI("<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()>",sensitiveAPIs))
                                    || (tcbAPI.contains("<android.telephony.TelephonyManager: java.lang.String getSimOperator()>") && checkSpecificAPI("<android.net.wifi.WifiManager: java.util.List getScanResults()>",sensitiveAPIs))
                                    || (tcbAPI.contains("<android.telephony.TelephonyManager: int getNetworkType()>") )
                                    //bundle
                                    || (tcbAPI.contains("<android.os.Bundle:"))
                                    //
                                    || (tcbAPI.contains("<android.provider.Settings$System: int getInt") &&  (checkSpecificAPI("<android.os.Vibrator: void vibrate(long[],int)>", sensitiveAPIs)))
                                    //AccountManager
                                    || (tcbAPI.contains("<android.accounts.AccountManager: android.accounts.Account[] getAccountsByType(java.lang.String)>") &&  (checkSpecificAPI("<android.content.ContentResolver", sensitiveAPIs)))
                                    //android.media
                                    || (tcbAPI.contains("<android.media.AudioManager:"))
                                    //setBackgroundColor
                                    || (checkSpecificAPI("<android.webkit.WebView: void setBackgroundColor(int)>", sensitiveAPIs))
                                    //notify
                                    || (checkSpecificAPI("<android.app.NotificationManager: void notify",sensitiveAPIs))
                                    //prot
                                    || (tcbAPI.contains("<java.net.InetSocketAddress: int getPort()>") && (checkSpecificAPI("<java.net.URL: java.net.URLConnection openConnection()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.Proxy: int getDefaultPort()>") && (checkSpecificAPI("<java.net.URL: java.net.URLConnection openConnection()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.Proxy: int getPort(android.content.Context)>") && (checkSpecificAPI("<java.net.URL: java.net.URLConnection openConnection()>", sensitiveAPIs)))

                            //telephony
                                    || (tcbAPI.contains("<android.telephony.TelephonyManager: int getSimState()>") && (checkSpecificAPI("<android.telephony.TelephonyManager:", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.telephony.TelephonyManager: java.lang.String getSimOperator()>") && (checkSpecificAPI("<android.telephony.TelephonyManager:", sensitiveAPIs)))

                                    //database
                                    || (tcbAPI.contains("<android.database."))
                                    //IO
                                    || (checkSpecificAPI("<java.nio.",triggerAPIs))
                                    || (checkSpecificAPI("<java.io.",triggerAPIs))
                                    //view
                                    || (checkSpecificAPI("<android.view",triggerAPIs))
                                    // IP address
                                    || (checkSpecificAPI("<java.net.InetAddress: java.lang.String getHostAddress()>",triggerAPIs))
                                    || (checkSpecificAPI("<java.net.InetSocketAddress: java.lang.String getHostName()>",triggerAPIs))
                                    || (checkSpecificAPI("<android.net.Proxy: java.lang.String getDefaultHost()>",triggerAPIs))
                                    || (checkSpecificAPI("<android.net.wifi.WifiInfo: int getIpAddress()>",triggerAPIs))
                                    || (checkSpecificAPI("<android.net.Proxy: java.lang.String getHost(android.content.Context)>",triggerAPIs))
                                    || (checkSpecificAPI("getHost",triggerAPIs))
                                    || (checkSpecificAPI("<java.net.Socket: java.net.InetAddress getInetAddress()>",triggerAPIs))
                                    || (checkSpecificAPI("<java.net.InetSocketAddress: int getPort()>",triggerAPIs))
                                    //network
                                    || (checkSpecificAPI("<android.net",triggerAPIs))
                                    || (checkSpecificAPI("<java.net.",triggerAPIs))
                                    || (checkSpecificAPI("<java.net.URL: java.net.URLConnection openConnection", ifSensitiveAPIs) && checkSpecificAPI("<java.net.URL: java.net.URLConnection openConnection", elseSensitiveAPIs) )
                                    || (tcbAPI.contains("android.net.NetworkInfo: boolean isConnected()") && (checkSpecificAPI("connect()", sensitiveAPIs) || checkSpecificAPI("openConnection()", sensitiveAPIs) || checkSpecificAPI("android.net.NetworkInfo getActiveNetworkInfo()", sensitiveAPIs) || checkSpecificAPI("<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()", sensitiveAPIs) || checkSpecificAPI("<android.webkit.WebView: void loadUrl", sensitiveAPIs)))
                                    || (tcbAPI.contains("android.net.NetworkInfo: boolean isConnected()") && (checkSpecificAPI("<android.widget.VideoView: void resume()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.NetworkInfo: boolean isAvailable()>") && (checkSpecificAPI("connect()", sensitiveAPIs) || checkSpecificAPI("openConnection()", sensitiveAPIs) || checkSpecificAPI("android.net.NetworkInfo getActiveNetworkInfo()", sensitiveAPIs) || checkSpecificAPI("<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()", sensitiveAPIs) || checkSpecificAPI("<android.webkit.WebView: void loadUrl", sensitiveAPIs)))
                                    || (tcbAPI.contains("android.webkit.URLUtil: boolean isHttpUrl") && (checkSpecificAPI("<android.webkit.WebView: void loadUrl", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.ConnectivityManager: android.net.NetworkInfo getActiveNetworkInfo()>") && (checkSpecificAPI("<android.net.wifi.WifiManager: boolean isWifiEnabled()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.ConnectivityManager: android.net.NetworkInfo getActiveNetworkInfo()>") && (checkSpecificAPI("<java.net.URL: java.net.URLConnection openConnection(java.net.Proxy)>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<java.net.URL: java.net.URLConnection openConnection()>") && (checkSpecificAPI("<java.net.URLConnection: java.io.InputStream getInputStream()>", sensitiveAPIs) || checkSpecificAPI("<android.webkit.WebView: void loadUrl", sensitiveAPIs) || checkSpecificAPI("<java.net.HttpURLConnection: void connect()", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager: boolean isWifiEnabled()>") && (checkSpecificAPI("<android.net.wifi.WifiManager:", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager$WifiLock: boolean isHeld()>") && (checkSpecificAPI("<android.net.wifi.WifiManager$WifiLock:", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager$MulticastLock: boolean isHeld()>") && (checkSpecificAPI("<android.net.wifi.WifiManager$WifiLock:", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.webkit.WebView: java.lang.String getUrl()>") && checkSpecificAPI("<android.webkit.WebView:", sensitiveAPIs))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager: int getWifiState()>") && checkSpecificAPI("<android.net.wifi.WifiManager:", sensitiveAPIs))
                                    || (tcbAPI.contains("<android.net.NetworkInfo: boolean isConnected()>") && checkSpecificAPI("<android.os.Looper: void loop()>", sensitiveAPIs))
                                    || (tcbAPI.contains("<android.content.pm.ApplicationInfo: int uid>") && checkSpecificAPI("<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()>", sensitiveAPIs))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()>") && (checkSpecificAPI("<android.net.wifi.WifiManager: int getWifiState()>", sensitiveAPIs) || checkSpecificAPI("<android.net.wifi.WifiManager: int getWifiState()>", sensitiveAPIs) || checkSpecificAPI("<android.net.wifi.WifiManager: boolean isWifiEnabled()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager: java.util.List getConfiguredNetworks()>") && (checkSpecificAPI("<android.net.wifi.WifiManager: boolean saveConfiguration()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager: boolean isScanAlwaysAvailable()>()") && (checkSpecificAPI("<android.net.wifi.WifiManager: boolean startScan()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.NetworkInfo$State: android.net.NetworkInfo$State CONNECTED>") && (checkSpecificAPI("<android.net.ConnectivityManager: android.net.NetworkInfo getNetworkInfo", sensitiveAPIs) || checkSpecificAPI("<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.wifi.WifiManager: boolean isScanAlwaysAvailable()>") && (checkSpecificAPI("<android.net.wifi.WifiManager: boolean startScan()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<java.net.ServerSocket: boolean isClosed()>") && (checkSpecificAPI("<java.net.ServerSocket: void bind(java.net.SocketAddress)>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<java.net.URLConnection: java.io.InputStream getInputStream()>()") && (checkSpecificAPI("<android.os.Looper: void loop()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<java.net.URLConnection: java.io.InputStream getInputStream()>()") && (checkSpecificAPI("<android.os.Looper: void loop()>", sensitiveAPIs)))
                                    || (tcbAPI.contains("<android.net.NetworkInfo: int getType()>") && (checkSpecificAPI("<org.apache.http.impl.client.DefaultHttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>", sensitiveAPIs)))
                            ;
                }
            }).collect(Collectors.toList());
            boolean hasCommonCases = CollectionUtils.isNotEmpty(commonCases);
            if (hasCommonCases) {
                hso.hasCommonCaese = true;
            }
        }
    }

    public static boolean containsKeyword(String s, Set<String> set) {
        for (String item : set) {
            if (s.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static void loadHSOFromFile(String outputFilePath, List<String> txtFileNames, List<HSO> result) {
        txtFileNames.forEach(txtFileName -> {
            try {

                File filePath = new File(outputFilePath + "/" + txtFileName + ".txt");

                BufferedReader br = null;
                String line = "";

                br = new BufferedReader(new FileReader(filePath));

                HSO hso = new HSO();
                while ((line = br.readLine()) != null) {
                    if (line.contains("==>FINAL TIME") && CollectionUtils.isNotEmpty(result)) {
                        hso.file = txtFileName;
                        result.add(hso);
                    }
                    if (line.contains("------------------------------Sensitive BranchInvokeMethods----------------------------------")) {
                        if (hso.sensitiveStmt.APIs.size() != 0 && hso.triggerConditionBlock.APIs.size() != 0) {
                            hso.file = txtFileName;
                            result.add(hso);
                        }
                        hso = new HSO();
                    }

                    if (line.startsWith("If Statement:")) {
                        hso.ifStatement = line;
                        String tmp = getSubUtilSimple(line, "if(.*?)goto ");
                        Set<String> vars = variablePattern(tmp);
                        //if(vars.size() > 1){
                        hso.variableInTrigger.addAll(vars);
                        //}
                    }

                    if (line.startsWith("Declare Method:")) {
                        hso.declareMethod = getSubUtilSimple(line, "(<.*>)");
                    }

                    if (line.startsWith("Trigger Condition Block:")) {
                        if (StringUtils.isBlank(hso.triggerConditionStmt)) {
                            hso.triggerConditionStmt = line;
                            if (containsKeyword(line, hso.variableInTrigger)) {
                                hso.variableInTrigger.addAll(varPattern(line));
                            }
                        }

                        String api = getSubUtilSimple(line, "(<.*?>)");
                        if (ApplicationClassFilter.isClassInSystemPackage(api)) {
                            if(api.contains("currentTimeMillis")){
                                hso.triggerConditionBlock.APIs.add(line);
                            }else if (
                                !api.startsWith("<java.lang")&&
                                    !api.startsWith("<java.util.List") && !api.startsWith("<java.util.ArrayList")
                                            && !api.startsWith("<java.util.Map") && !api.startsWith("<java.util.concurrent.")
                                            && !api.startsWith("<java.util.Arrays") && !api.startsWith("<java.util.HashMap")
                                            && !api.startsWith("<java.util.Iterator:") && !api.startsWith("<java.util.Collections:")
                                            && !api.startsWith("<java.util.HashSet:") && !api.startsWith("<java.util.LinkedList:")
                                            && !api.startsWith("<java.util.Hashtable:") && !api.startsWith("<java.util.Set:")
                                            && !api.startsWith("<java.util.LinkedHashMap:") && !api.startsWith("<java.util.BitSet:")
                                            && !api.startsWith("<java.util.Vector") && !api.startsWith("<java.util.Queue:")
                                            && !api.startsWith("<android.util.SparseArray:")
                                            && !api.startsWith("<android.util.Pair:")
                                            && !api.startsWith("<java.text.NumberFormat")
                                            && !api.startsWith("<java.text.SimpleDateFormat:")
                                            && !api.startsWith("<android.text.TextUtils: boolean equals")
                                            && !api.startsWith("<android.util.Log:") && !api.startsWith("<android.text.TextUtils: boolean isEmpty")
                                            && !api.contains("Exception:")
                                            && !api.contains("<init>")
                                            && !api.contains("<android.util.SparseArray:")
                                            && !api.contains("<java.util.Stack:")
                                            && !api.contains("<java.util.regex.Matcher: boolean matches()>")
                                            && !api.contains("<com.google.gson.")
                                            && !api.contains("<java.util.regex.")
                                            && !api.contains("<android.util.Base64")
                                            && !api.contains("<java.util.Collection:")
                                            && !api.contains("<android.util.FloatMath")
                                            && !api.contains("<java.util.SortedMap:")
                                            && !api.contains("<android.util.ArrayMap:")
                            ) {
                                hso.triggerConditionBlock.APIs.add(line);
                            }
                        }
                    }

                    if (line.startsWith("[IF]")) {
                        hso.ifStmts.add(getSubUtilSimple(line, "(<.*?>)"));
                        if (line.contains(hso.declareMethod)) {
                            hso.variableInIfBranch.addAll(variablePattern(line));
                        }
                    }

                    if (line.startsWith("[ELSE]")) {
                        hso.elseStmts.add(getSubUtilSimple(line, "(<.*?>)"));
                        if (line.contains(hso.declareMethod)) {
                            hso.variableInElseBranch.addAll(variablePattern(line));
                        }
                    }

                    if (line.startsWith("[IF-SENSITIVE]")) {
                        hso.ifSensitiveOriginStmt.add(line);
                        if (line.contains(hso.declareMethod)) {
                            hso.variableInIfBranch.addAll(variablePattern(line));
                        }

                        String api = getSubUtilSimple(line, "(<.*?>)");
                        if (ApplicationClassFilter.isClassInSystemPackage(api)) {
                            hso.ifSensitive.add(api);
                            hso.ifStmts.add(getSubUtilSimple(line, "(<.*?>)"));
                            hso.sensitiveStmt.APIs.add(api);
                        }
                    }

                    if (line.startsWith("[ELSE-SENSITIVE]")) {
                        hso.elseSensitiveOriginStmt.add(line);

                        if (line.contains(hso.declareMethod)) {
                            hso.variableInElseBranch.addAll(variablePattern(line));
                        }
                        String api = getSubUtilSimple(line, "(<.*?>)");
                        if (ApplicationClassFilter.isClassInSystemPackage(api)) {
                            hso.elseSensitive.add(api);
                            hso.elseStmts.add(getSubUtilSimple(line, "(<.*?>)"));
                            hso.sensitiveStmt.APIs.add(api);
                        }
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename.replace(".txt", ""));
            }
        });
    }

    private static String convert2PatternMethod(String s) {
        Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(1);

        }
        return s;
    }

    public static Set<String> variablePattern(String s) {
        Set<String> variables = new HashSet<>();
        Pattern pattern = Pattern.compile("(\\$[a-z][0-9]+)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            variables.add(matcher.group());
        }
        return variables;

    }

    public static Set<String> varPattern(String s) {
        Pattern pattern1 = Pattern.compile("(=)(.*)", Pattern.DOTALL);
        Matcher matcher1 = pattern1.matcher(s);
        if (matcher1.find()) {
            s = matcher1.group();
        }

        Set<String> variables = new HashSet<>();
        Pattern pattern = Pattern.compile("(\\$[a-z][0-9]+)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            variables.add(matcher.group());
        }

        return variables;
    }

    public static boolean checkSpecificAPI(String s, Set<String> uniqueSensitiveAPIs) {
        if (CollectionUtils.isEmpty(uniqueSensitiveAPIs)) {
            return false;
        }

        AtomicBoolean flag = new AtomicBoolean(false);
        uniqueSensitiveAPIs.forEach(a -> {
            if (a.toLowerCase().contains(s.toLowerCase())) {
                flag.set(true);
            }
        });

        return flag.get();
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     *
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public static void put2Map(AtomicReference<String> key, HashMap<String, Integer> tcbHashMap) {
        if (StringUtils.isNotBlank(key.get())) {
            if (tcbHashMap.containsKey(key.get())) {
                tcbHashMap.put(key.get(), tcbHashMap.get(key.get()) + 1);
            } else {
                tcbHashMap.put(key.get(), 1);
            }
        }
    }

    public static void put2Map(String key, HashMap<String, Integer> tcbHashMap) {
        if (StringUtils.isNotBlank(key)) {
            if (tcbHashMap.containsKey(key)) {
                tcbHashMap.put(key, tcbHashMap.get(key) + 1);
            } else {
                tcbHashMap.put(key, 1);
            }
        }
    }

}
