package HSO.RQ1;

import HSO.model.HSO;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class table6 {

    public static void main(String[] args)  throws IOException {
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


        HashMap<String, Integer> sensitiveAPIHashMap = new HashMap<>();
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

            Set<String> presentSensitiveAPIs = new HashSet<>();
            if (hso.ifBranchIsHSO) {
                presentSensitiveAPIs.addAll(hso.ifSensitive);
            }
            if (hso.elseBranchIsHSO) {
                presentSensitiveAPIs.addAll(hso.elseSensitive);
            }

            AtomicReference<String> key = new AtomicReference<>("");
//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<com.android.internal.telephony.ISms")
//                                    || matcher.group(1).startsWith("<android.telephony.SmsManager:")
//                                    || matcher.group(1).startsWith("<android.telephony.gsm.SmsManager: void sendDataMessage")
//                                    || matcher.group(1).startsWith("<android.telephony.gsm.SmsManager: void sendTextMessage")
//                    ) {
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                    }
//                }
//            });

//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<android.app.NotificationManager: void notify(int,android.app.Notification)>")
//                                    || matcher.group(1).startsWith("<android.app.Notification")
//                    ) {
//                        //key.set("notify:");
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                    }
//                }
//            });

//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<android.telephony.TelephonyManager")
//                                    || matcher.group(1).startsWith("<android.bluetooth.BluetoothAdapter: java.lang.String getAddress()>")
//                    ) {
//                        //key.set("privacy[telephony || hardware Address]:");
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                    }
//                }
//            });

//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<android.bluetooth")
//                    ) {
//                        put2Map("<android.bluetooth:", sensitiveAPIHashMap);
//                        //key.set("<android.bluetooth");
//                    }
//                }
//            });

//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (
//                            matcher.group(1).startsWith("<android.app.ActivityManager: java.util.List getRunningTasks")
//                                    || matcher.group(1).startsWith("<android.app.ActivityManager: java.util.List getRecentTasks")
//                                    || matcher.group(1).startsWith("<android.app.ActivityManager: void moveTaskToFront")
//                    ) {
//                        //key.set("tasks:");
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                    }
//                }
//            });
//
//
//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.location.LocationManager")
//                    ) {
//                        //key.set("location:");
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                    }
//                }
//            });
//
//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.accounts")
//                    ) {
//                        //key.set("accounts:");
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                    }
//                }
//            });
//
//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.webkit")
//                    ) {
//                        //key.set("android.webkit:");
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                        //System.out.println("======="+ hso.triggerConditionBlock.APIs);
//                    }
//                }
//            });
//
//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.widget.")
//                    ) {
//                        put2Map("android.widget:", sensitiveAPIHashMap);
//                        //key.set("android.widget:");
//                    }
//                }
//            });
//
//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.provider.Settings$System")
//                    ) {
//                        //key.set("Settings$System:");
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                    }
//                }
//            });
//
//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.os.")
//                    ) {
//                        //key.set("android.os:");
//                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);
//                        //System.out.println(hso.file+"===android.os==="+"---------"+hso.sensitiveStmt.APIs);
//                    }
//                }
//            });
            presentSensitiveAPIs.forEach(a -> {
                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(a);
                if (matcher.find()) {
                    if (matcher.group(1).startsWith("<java.net")
                            || matcher.group(1).startsWith("<android.net")
                    ) {
                        //key.set("net:");
                        TriggerNum.put2Map(matcher.group(1), sensitiveAPIHashMap);

                        //System.out.println(hso.file+"===network==="+"---------"+hso.sensitiveStmt.APIs);
                    }
                }
            });
//            presentSensitiveAPIs.forEach(a -> {
//                Pattern pattern = Pattern.compile("(<.*?>)", Pattern.DOTALL);
//                Matcher matcher = pattern.matcher(a);
//                if (matcher.find()) {
//                    if (matcher.group(1).startsWith("<android.media")
//                    ) {
//                        //key.set("android.media:");
//                        put2Map("android.media:", sensitiveAPIHashMap);
//                    }
//                }
//            });

        }

        //sort
        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(sensitiveAPIHashMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        for (Map.Entry<String, Integer> t : list2) {
            System.out.println(t.getKey() + ":" + t.getValue());
        }
    }
}
