package JUnitTestGen.RQResult;

import org.apache.commons.lang3.StringUtils;
import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CompatibilityIssues {

    public static void main(String[] args) throws IOException {
        String logFolder = "/Users/xsun0035/Desktop/CADroid";


        HashMap<String, HashSet<String>> res = new HashMap<>();
        HashMap<String, String> sdk21 = new HashMap<>();
        HashMap<String, String> sdk22 = new HashMap<>();
        HashMap<String, String> sdk23 = new HashMap<>();
        HashMap<String, String> sdk24 = new HashMap<>();
        HashMap<String, String> sdk25 = new HashMap<>();
        HashMap<String, String> sdk26 = new HashMap<>();
        HashMap<String, String> sdk27 = new HashMap<>();
        HashMap<String, String> sdk28 = new HashMap<>();
        HashMap<String, String> sdk29 = new HashMap<>();
        HashMap<String, String> sdk30 = new HashMap<>();

        //=========================================SDK 21-29==============================================

        getSDKHashLog(logFolder, sdk21, "21");
        getSDKHashLog(logFolder, sdk22, "22");
        getSDKHashLog(logFolder, sdk23, "23");
        getSDKHashLog(logFolder, sdk24, "24");
        getSDKHashLog(logFolder, sdk25, "25");
        getSDKHashLog(logFolder, sdk26, "26");
        getSDKHashLog(logFolder, sdk27, "27");
        getSDKHashLog(logFolder, sdk28, "28");
        getSDKHashLog(logFolder, sdk29, "29");
        getSDKHashLog(logFolder, sdk30, "30");

        int type1Num = 0;
        int type2Num = 0;
        int type1_2Num = 0;
        HashMap<String, Integer> exceptionTypes = new HashMap();

        for (String key : sdk30.keySet()) {
            if(key.equals("TestCase_com_application_onlineshoppinginzimbabwe__363742032Test") || key.equals("TestCase_ch_fhnw__562818555Test")){
                continue;
            }
            HashSet<String> resSet = new HashSet<>();
            resSet.add(sdk30.get(key));

            if(!resSet.contains(sdk29.get(key))){
                resSet.add(sdk29.get(key));
            }

            if(!resSet.contains(sdk28.get(key))){
                resSet.add(sdk28.get(key));
            }

            if(!resSet.contains(sdk27.get(key))){
                resSet.add(sdk27.get(key));
            }

            if(!resSet.contains(sdk26.get(key))){
                resSet.add(sdk26.get(key));
            }

            if(!resSet.contains(sdk25.get(key))){
                resSet.add(sdk25.get(key));
            }

            if(!resSet.contains(sdk24.get(key))){
                resSet.add(sdk24.get(key));
            }

            if(!resSet.contains(sdk23.get(key))){
                resSet.add(sdk23.get(key));
            }

            if(!resSet.contains(sdk22.get(key))){
                resSet.add(sdk22.get(key));
            }

            if(!resSet.contains(sdk21.get(key))){
                resSet.add(sdk21.get(key));
            }

            if(resSet.size() > 1){
//                if(resSet.contains(null) || resSet.contains("")){
//                    System.out.println(key + "--------"+ resSet);
//                }
                /**
                 * output all compatibility issues
                 */
//                System.out.println(key + "--------"+ resSet);
                /**
                 * output type
                 */
//                String typeString = appendRes(containsType1Exception(resSet), containsType2Exception(resSet));
//                System.out.println(key + "--------"+ typeString);
//                if(typeString.equals("type1")){
//                    type1Num ++;
//                }
//                if(typeString.equals("type2")){
//                    type2Num ++;
//                }
//                if(typeString.equals("type1+type2")){
//                    type1_2Num ++;
//                }

                /**
                 * output exception type numbers
                 */
                for(String s : resSet){
                    if(!s.equals("success")){
                        IncrementHashMap.incrementValue(exceptionTypes, s);
                    }
                }
            }
        }

        /**
         * [*only for*]output exception type numbers
         */
        Map<String, Integer> sortedHashMap = IncrementHashMap.sortByValue(exceptionTypes);
        for(String exceptionTypeStr : sortedHashMap.keySet()){
            System.out.println(exceptionTypeStr + "----"+ sortedHashMap.get(exceptionTypeStr));
        }

        System.out.println("type1Num:" + type1Num);
        System.out.println("type2Num:" + type2Num);
        System.out.println("type1_2Num:" + type1_2Num);

//
//        //所有结果
//        for (String key : sdk30.keySet()) {
//            if (key.equals("TestCase_com_application_onlineshoppinginzimbabwe__363742032Test") || key.equals("TestCase_ch_fhnw__562818555Test")) {
//                continue;
//            }
//            HashSet<String> resSet = new HashSet<>();
//            resSet.add(sdk30.get(key) + "(30)");
//
//            if (!resSet.contains(sdk29.get(key))) {
//                resSet.add(sdk29.get(key) + "(29)");
//            }
//
//            if (!resSet.contains(sdk28.get(key))) {
//                resSet.add(sdk28.get(key) + "(28)");
//            }
//
//            if (!resSet.contains(sdk27.get(key))) {
//                resSet.add(sdk27.get(key) + "(27)");
//            }
//
//            if (!resSet.contains(sdk26.get(key))) {
//                resSet.add(sdk26.get(key) + "(26)");
//            }
//
//            if (!resSet.contains(sdk25.get(key))) {
//                resSet.add(sdk25.get(key) + "(25)");
//            }
//
//            if (!resSet.contains(sdk24.get(key))) {
//                resSet.add(sdk24.get(key) + "(24)");
//            }
//
//            if (!resSet.contains(sdk23.get(key))) {
//                resSet.add(sdk23.get(key) + "(23)");
//            }
//
//            if (!resSet.contains(sdk22.get(key))) {
//                resSet.add(sdk22.get(key) + "(22)");
//            }
//
//            if (!resSet.contains(sdk21.get(key))) {
//                resSet.add(sdk21.get(key) + "(21)");
//            }
//
//            if (resSet.size() > 1) {
//                String outputRes = key + "--------" + resSet;
//                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/CADroid/AllExecutedRes.txt", true)));
//                out.println(outputRes);
//                out.close();
//            }
//        }


    }

    private static String appendRes(boolean type1, boolean type2) {
        if (type1 == true && type2 == false) {
            return "type1";
        } else if (type1 == false && type2 == true) {
            return "type2";
        } else if (type1 == true && type2 == true) {
            return "type1+type2";
        }
        return "";
    }

    private static boolean containsType1Exception(Set<String> exceptionSet) {
        for (String exception : exceptionSet) {
            if (getType1List().contains(exception)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsType2Exception(Set<String> exceptionSet) {
        for (String exception : exceptionSet) {
            if (getType2List().contains(exception)) {
                return true;
            }
        }
        return false;
    }

    private static Set<String> getType2List() {
        Set<String> res = new HashSet<>();
        res.add("IllegalStateException");
        res.add("SecurityException");
        res.add("RuntimeException");
        res.add("NullPointerException");
        res.add("NoSuchElementException");
        res.add("IndexOutOfBoundsException");
        res.add("IllegalAccessError");
        res.add("EmptyStackException");
        res.add("StringIndexOutOfBoundsException");
        res.add("ArrayIndexOutOfBoundsException");
        res.add("StackOverflowError");
        res.add("UnsupportedOperationException");
        res.add("android.database.sqlite.SQLiteCantOpenDatabaseException");
        res.add("android.view.WindowManager$BadTokenException");
        res.add("android.content.IntentFilter$MalformedMimeTypeException");
        res.add("android.system.ErrnoException");
        res.add("android.util.AndroidRuntimeException");
        res.add("android.view.ViewRootImpl$CalledFromWrongThreadException");
        res.add("SQLiteException");
        res.add("android.net.ParseException");
        res.add("android.security.KeyChainException");
        res.add("java.net.MalformedURLException");
        res.add("android.app.PendingIntent$CanceledException");
        res.add("android.database.CursorIndexOutOfBoundsException");
        res.add("IOException");
        res.add("android.view.InflateException");
        res.add("NumberFormatException");
        res.add("MissingFormatArgumentException");
        res.add("java.security.NoSuchAlgorithmException");
        res.add("android.util.TimeFormatException");
        res.add("android.content.pm.PackageManager$NameNotFoundException");
        res.add("android.content.res.Resources$NotFoundException");
        res.add("android.content.ActivityNotFoundException");
        res.add("FileNotFoundException");
        res.add("crash");
        return res;
    }

    private static Set<String> getType1List() {
        Set<String> res = new HashSet<>();
        res.add("NoSuchFieldError");
        res.add("NoSuchMethodError");
        res.add("NoClassDefFoundError");
        res.add("ClassCastException");
        res.add("IllegalArgumentException");
        return res;
    }

    public static void getSDKHashLog(String logFolder, HashMap<String, String> sdkHashMap, String sdkVersion) throws IOException {
        File file = new File(logFolder + "/JUnitTestRun_SDK" + sdkVersion + "_log.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String successTestCase = Regex.getSubUtilSimple(currentLine, "(TestCase_.*\\[result\\].*)");
            String failTestCase = Regex.getSubUtilSimple(currentLine, "(TestCase_.*Test:)");
            String crashTestCase = Regex.getSubUtilSimple(currentLine, "(TestCase_.*INSTRUMENTATION_)");

            //success
            if (currentLine.startsWith("TestCase_") && StringUtils.isNotBlank(successTestCase)) {
                sdkHashMap.put(Regex.getSubUtilSimple(successTestCase, "(TestCase_.*Test:\\[result\\])").replace(":[result]", ""), "success");
            }

            //crash
            if (currentLine.startsWith("TestCase_") && StringUtils.isNotBlank(crashTestCase)) {
                sdkHashMap.put(Regex.getSubUtilSimple(crashTestCase, "(TestCase_.*Test)"), "crash");
            }

            //fail
            String exceptionContene = "";
            if (currentLine.startsWith("TestCase_") && StringUtils.isNotBlank(failTestCase) && currentLine.endsWith("Test:")) {
                String testCaseName = currentLine.replace(":", "");
                for (int j = i; j < fileContent.size() && !fileContent.get(j).contains(testCaseName + ".testCase(" + testCaseName + ".java:"); j++) {
                    String currentExpLine2 = Regex.getSubUtilSimple(fileContent.get(j), "(java.lang.*?:)");

                    if (fileContent.get(j).startsWith("java.lang.") && StringUtils.isNotBlank(currentExpLine2)) {
                        exceptionContene = currentExpLine2.replaceAll("java.lang.", "").replaceAll(":", "");
                        break;
                    }

                    String currentExpLine3 = Regex.getSubUtilSimple(fileContent.get(j), "(java.util.*?:)");
                    if (fileContent.get(j).startsWith("java.util.") && StringUtils.isNotBlank(currentExpLine3)) {
                        exceptionContene = currentExpLine3.replaceAll("java.util.", "").replaceAll(":", "");
                        break;
                    }

                    String currentExpLine = Regex.getSubUtilSimple(fileContent.get(j), "(java.lang.*)");
                    if (fileContent.get(j).startsWith("java.lang.") && StringUtils.isNotBlank(currentExpLine)) {
                        exceptionContene = currentExpLine.replaceAll("java.lang.", "");
                        break;
                    }

                    String currentExpLine4 = Regex.getSubUtilSimple(fileContent.get(j), "(java.util.*)");
                    if (fileContent.get(j).startsWith("java.util.") && StringUtils.isNotBlank(currentExpLine4)) {
                        exceptionContene = currentExpLine4.replaceAll("java.util.", "");
                        break;
                    }

                    String currentExpLine5 = Regex.getSubUtilSimple(fileContent.get(j), "(android.*Exception:)");
                    if (fileContent.get(j).startsWith("android.") && StringUtils.isNotBlank(currentExpLine5)) {
                        exceptionContene = currentExpLine5.replaceAll(":", "");
                        break;
                    }

                    String currentExpLine6 = Regex.getSubUtilSimple(fileContent.get(j), "(java.io.*Exception:)");
                    if (fileContent.get(j).startsWith("java.io.") && StringUtils.isNotBlank(currentExpLine6)) {
                        exceptionContene = currentExpLine6.replaceAll("java.io.", "").replace(":", "");
                        break;
                    }

                    String currentExpLine7 = Regex.getSubUtilSimple(fileContent.get(j), "(android.*Exception)");
                    if (fileContent.get(j).startsWith("android.") && StringUtils.isNotBlank(currentExpLine7)) {
                        exceptionContene = currentExpLine7;
                        break;
                    }

                    String currentExpLine8 = Regex.getSubUtilSimple(fileContent.get(j), "(java.*Exception:)");
                    if (fileContent.get(j).startsWith("java.") && StringUtils.isNotBlank(currentExpLine8)) {
                        exceptionContene = currentExpLine8.replace(":", "");
                        break;
                    }

                    String currentExpLine9 = Regex.getSubUtilSimple(fileContent.get(j), "(java.*Exception)");
                    if (fileContent.get(j).startsWith("java.") && StringUtils.isNotBlank(currentExpLine9)) {
                        exceptionContene = currentExpLine9;
                        break;
                    }
                }
                sdkHashMap.put(failTestCase.replaceAll(":", ""), exceptionContene);
            }
        }
    }
}
