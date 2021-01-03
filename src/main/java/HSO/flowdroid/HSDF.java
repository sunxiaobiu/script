package HSO.flowdroid;

import HSO.RQ1.TriggerNum;
import HSO.model.DataLeak;
import HSO.model.HSO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.ApplicationClassFilter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static HSO.RQ1.TriggerNum.containsKeyword;

public class HSDF {

    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];
        String flowDroidFilePath = args[1];

        Set<String> hsoSourceSinks = new HashSet<>(Arrays.asList("<java.lang.ProcessBuilder: java.lang.Process start()>","<android.app.WallpaperManager: void setBitmap(android.graphics.Bitmap)>","<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()>","<android.app.ActivityManager: java.util.List getRunningTasks(int)>","<android.app.ActivityManager: java.util.List getRecentTasks(int,int)>","<android.webkit.WebView: void addJavascriptInterface(java.lang.Object,java.lang.String)>","<android.accounts.AccountManager: java.lang.String getUserData(android.accounts.Account,java.lang.String)>","<android.net.ConnectivityManager: android.net.NetworkInfo getNetworkInfo(int)>","<android.provider.Settings$Secure: boolean putInt(android.content.ContentResolver,java.lang.String,int)>","<java.net.HttpURLConnection: void connect()>","<android.provider.Settings$System: android.net.Uri getUriFor(java.lang.String)>","<android.provider.Settings$System: boolean putLong(android.content.ContentResolver,java.lang.String,long)>","<android.app.WallpaperManager: void setStream(java.io.InputStream)>","<android.telephony.TelephonyManager: java.util.List getNeighboringCellInfo()>","<com.android.internal.telephony.ISms: void sendText(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)>","<android.provider.Settings$System: boolean putInt(android.content.ContentResolver,java.lang.String,int)>","<android.telephony.TelephonyManager: android.telephony.CellLocation getCellLocation()>","<android.accounts.AccountManager: android.accounts.Account[] getAccountsByType(java.lang.String)>","<android.provider.Settings$Secure: boolean putString(android.content.ContentResolver,java.lang.String,java.lang.String)>","<android.net.wifi.WifiManager: java.util.List getScanResults()>","<android.net.wifi.WifiManager: java.util.List getConfiguredNetworks()>","<android.provider.Settings$System: boolean putString(android.content.ContentResolver,java.lang.String,java.lang.String)>","<com.android.internal.telephony.ISms: void sendMultipartText(java.lang.String,java.lang.String,java.util.List,java.util.List,java.util.List)>","<android.app.NotificationManager: void notify(int,android.app.Notification)>","<android.telephony.gsm.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)>","<java.net.URL: java.net.URLConnection openConnection(java.net.Proxy)>","<android.net.ConnectivityManager: android.net.NetworkInfo[] getAllNetworkInfo()>","<java.net.URL: java.net.URLConnection openConnection()>","<android.net.VpnService: android.content.Intent prepare(android.content.Context)>","<android.app.ActivityManager: void restartPackage(java.lang.String)>","<android.app.ActivityManager: void killBackgroundProcesses(java.lang.String)>","<android.media.AudioManager: void setParameters(java.lang.String)>","<android.hardware.Camera: android.hardware.Camera open()>","<android.net.ConnectivityManager: android.net.NetworkInfo getActiveNetworkInfo()>","<android.content.ContentResolver: void addPeriodicSync(android.accounts.Account,java.lang.String,android.os.Bundle,long)>","<android.content.ContentResolver: void setSyncAutomatically(android.accounts.Account,java.lang.String,boolean)>"));

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath, txtFileNames);

        HashMap<String, Integer> sourceMap = new HashMap<>();
        HashMap<String, Integer> sinkMap = new HashMap<>();

        int totalDataLeaks = 0;
        for (String txtFileName : txtFileNames) {

            List<HSO> currenFileHso = new ArrayList<>();

            try {

                File filePath = new File(outputFilePath + "/" + txtFileName + ".txt");

                BufferedReader br = null;
                String line = "";

                br = new BufferedReader(new FileReader(filePath));

                HSO hso = new HSO();
                while ((line = br.readLine()) != null) {
                    if(line.contains("==>FINAL TIME") && CollectionUtils.isNotEmpty(currenFileHso)){
                        currenFileHso.add(hso);
                    }
                    if (line.contains("------------------------------Sensitive BranchInvokeMethods----------------------------------")) {
                        if (hso.sensitiveStmt.APIs.size() != 0 && hso.triggerConditionBlock.APIs.size() != 0) {
                            hso.file = txtFileName;
                            currenFileHso.add(hso);
                        }
                        hso = new HSO();
                    }

                    if (line.startsWith("If Statement:")) {
                        hso.ifStatement = line;
                        String tmp = TriggerNum.getSubUtilSimple(line, "if(.*?)goto ");
                        Set<String> vars = TriggerNum.variablePattern(tmp);
                        //if(vars.size() > 1){
                        hso.variableInTrigger.addAll(vars);
                        //}
                    }

                    if (line.startsWith("Declare Method:")) {
                        hso.declareMethod = TriggerNum.getSubUtilSimple(line, "(<.*>)");
                    }

                    if (line.startsWith("Trigger Condition Block:")) {
                        if (StringUtils.isBlank(hso.triggerConditionStmt)) {
                            hso.triggerConditionStmt = line;
                            if (containsKeyword(line, hso.variableInTrigger)) {
                                hso.variableInTrigger.addAll(TriggerNum.varPattern(line));
                            }
                        }

                        String api = TriggerNum.getSubUtilSimple(line, "(<.*?>)");
                        if (ApplicationClassFilter.isClassInSystemPackage(api)) {
                            if (
                                    !api.startsWith("<java.lang")
                                            && !api.startsWith("<java.util.List") && !api.startsWith("<java.util.ArrayList")
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
                        hso.ifStmts.add(TriggerNum.getSubUtilSimple(line, "(<.*?>)"));
                        if (line.contains(hso.declareMethod)) {
                            hso.variableInIfBranch.addAll(TriggerNum.variablePattern(line));
                        }
                    }

                    if (line.startsWith("[ELSE]")) {
                        hso.elseStmts.add(TriggerNum.getSubUtilSimple(line, "(<.*?>)"));
                        if (line.contains(hso.declareMethod)) {
                            hso.variableInElseBranch.addAll(TriggerNum.variablePattern(line));
                        }
                    }

                    if (line.startsWith("[IF-SENSITIVE]")) {
                        hso.ifSensitiveOriginStmt.add(line);
                        if (line.contains(hso.declareMethod)) {
                            hso.variableInIfBranch.addAll(TriggerNum.variablePattern(line));
                        }

                        String api = TriggerNum.getSubUtilSimple(line, "(<.*?>)");
                        if (ApplicationClassFilter.isClassInSystemPackage(api)) {
                            hso.ifSensitive.add(api);
                            hso.ifStmts.add(TriggerNum.getSubUtilSimple(line, "(<.*?>)"));
                            hso.sensitiveStmt.APIs.add(api);
                        }
                    }

                    if (line.startsWith("[ELSE-SENSITIVE]")) {
                        hso.elseSensitiveOriginStmt.add(line);

                        if (line.contains(hso.declareMethod)) {
                            hso.variableInElseBranch.addAll(TriggerNum.variablePattern(line));
                        }
                        String api = TriggerNum.getSubUtilSimple(line, "(<.*?>)");
                        if (ApplicationClassFilter.isClassInSystemPackage(api)) {
                            hso.elseSensitive.add(api);
                            hso.elseStmts.add(TriggerNum.getSubUtilSimple(line, "(<.*?>)"));
                            hso.sensitiveStmt.APIs.add(api);
                        }
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /**
             * filter common cases
             */
            TriggerNum.filterCommonCases(currenFileHso);

            /**
             * filter data dependency
             */
            TriggerNum.filterDataDependency(currenFileHso);

            /**
             * 判断trigger-branch差异
             */
            TriggerNum.branchDiff(currenFileHso);

            /**
             * 判断单个branch是不是hso
             */
            TriggerNum.isBrancnHSO(currenFileHso);

            /**
             * 获取对应flowDroid 里的source&sinks
             */
            List<DataLeak> dataLeaks = getFlowDroidSourceSinks(flowDroidFilePath, txtFileName);
            if (CollectionUtils.isEmpty(dataLeaks)) {
                continue;
            }

            //从dataLeaks取出source 和 sink
            List<String> sources = new ArrayList<>();
            List<String> sinks = new ArrayList<>();
            for(DataLeak dataLeak : dataLeaks){
                sources.addAll(dataLeak.source);
                sinks.add(dataLeak.sink);
            }

            //删掉hso新增的source 和 sink
            List<String> filterSources = new ArrayList<>();
            List<String> filterSinks = new ArrayList<>();
            for(String source : sources){
                boolean isHiddenSource = false;
                for(String  hsoSourceSink : hsoSourceSinks){
                    if(source.contains(hsoSourceSink)){
                        isHiddenSource = true;
                    }
                }
                if(!isHiddenSource){
                    filterSources.add(source);
                }
            }
            for(String sink : sinks){
                boolean isHiddenSink = false;
                for(String hsoSourceSink : hsoSourceSinks){
                    if(sink.contains(hsoSourceSink)){
                        isHiddenSink = true;
                    }
                }
                if(!isHiddenSink){
                    filterSinks.add(sink);
                }
            }


            /**
             * transfer2sourcesinkFormat
             */
            List<String> sensitiveStmts = new ArrayList<>();
            for (HSO hso : currenFileHso) {
                if (hso.hasCommonCaese == true) {
                    continue;
                }
                if (!hso.isHSO) {
                    continue;
                }
                sensitiveStmts.addAll(transfer2sourcesinkFormat(hso));
            }
            /**
             * 对比FlowDroid结果，统计data leak的数量
             */
            int dataLeakNum = 0;
            for(String sensitiveStmt : sensitiveStmts){
                for(String source : filterSources){
                    if(source.contains(sensitiveStmt)){
                        dataLeakNum ++;
                    }
                }
                for(String sink : filterSinks){
                    if(sink.contains(sensitiveStmt)){
                        dataLeakNum ++;
                    }
                }
            }

            totalDataLeaks += dataLeakNum;
            System.out.println(txtFileName+":"+dataLeakNum);
        }

        System.out.println("totalDataLeaks:"+totalDataLeaks);
    }

    private static List<String> transfer2sourcesinkFormat(HSO hso) {
        List<String> res = new ArrayList<>();

        List<String> allSensitiveStmt = new ArrayList<>();
        allSensitiveStmt.addAll(hso.ifSensitiveOriginStmt);
        allSensitiveStmt.addAll(hso.elseSensitiveOriginStmt);

        for(String sensitiveStmt : allSensitiveStmt){
            String s = sensitiveStmt.replaceAll("\\[IF-SENSITIVE\\]","");
            String s1 = s.replaceAll("\\[ELSE-SENSITIVE\\]","");
            String s2 = s1.replaceAll("-->","in method");
            res.add(s2);
        }
        return res;
    }

    public static List<DataLeak> getFlowDroidSourceSinks(String flowDroidPath, String txtFileName) {
        List<DataLeak> result = new ArrayList<>();
        try {

            //File filePath = new File("/Users/xsun0035/workspace/HSO-related/HSO_FlowDroid/output" + "/" + txtFileName + ".txt");
            File filePath = new File(flowDroidPath + "/" + txtFileName + ".txt");

            if (!filePath.exists()) {
                return null;
            }
            BufferedReader br = null;
            String line = "";

            br = new BufferedReader(new FileReader(filePath));

            DataLeak dataLeak = new DataLeak();
            while ((line = br.readLine()) != null) {
                if(line.contains("- Found") && CollectionUtils.isNotEmpty(result)){
                    result.add(dataLeak);
                }
                if (line.contains(" - The sink")) {
                    if (CollectionUtils.isNotEmpty(dataLeak.source) && StringUtils.isNotBlank(dataLeak.sink)) {
                        dataLeak.file = txtFileName;
                        result.add(dataLeak);
                    }
                    dataLeak = new DataLeak();
                }

                if (line.contains("- The sink ")) {
                    String sink = TriggerNum.getSubUtilSimple(line, "(- The sink .*>)");
                    String tmp = sink.replace("- The sink ", "");
                    dataLeak.sink = tmp;
                }

                if (line.contains("- - ")) {
                    String source = TriggerNum.getSubUtilSimple(line, "( - - .*>)");
                    String tmp = source.replace("- - ", "");
                    dataLeak.source.add(tmp);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename.replace(".txt", ""));
            }
        });
    }

}
