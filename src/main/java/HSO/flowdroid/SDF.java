package HSO.flowdroid;

import HSO.RQ1.TriggerNum;
import HSO.model.DataLeak;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.*;

public class SDF {

    public static void main(String[] args)  throws IOException {
        Set<String> hsoSourceSinks = new HashSet<>(Arrays.asList("<java.lang.ProcessBuilder: java.lang.Process start()>","<android.app.WallpaperManager: void setBitmap(android.graphics.Bitmap)>","<android.net.wifi.WifiManager: android.net.wifi.WifiInfo getConnectionInfo()>","<android.app.ActivityManager: java.util.List getRunningTasks(int)>","<android.app.ActivityManager: java.util.List getRecentTasks(int,int)>","<android.webkit.WebView: void addJavascriptInterface(java.lang.Object,java.lang.String)>","<android.accounts.AccountManager: java.lang.String getUserData(android.accounts.Account,java.lang.String)>","<android.net.ConnectivityManager: android.net.NetworkInfo getNetworkInfo(int)>","<android.provider.Settings$Secure: boolean putInt(android.content.ContentResolver,java.lang.String,int)>","<java.net.HttpURLConnection: void connect()>","<android.provider.Settings$System: android.net.Uri getUriFor(java.lang.String)>","<android.provider.Settings$System: boolean putLong(android.content.ContentResolver,java.lang.String,long)>","<android.app.WallpaperManager: void setStream(java.io.InputStream)>","<android.telephony.TelephonyManager: java.util.List getNeighboringCellInfo()>","<com.android.internal.telephony.ISms: void sendText(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)>","<android.provider.Settings$System: boolean putInt(android.content.ContentResolver,java.lang.String,int)>","<android.telephony.TelephonyManager: android.telephony.CellLocation getCellLocation()>","<android.accounts.AccountManager: android.accounts.Account[] getAccountsByType(java.lang.String)>","<android.provider.Settings$Secure: boolean putString(android.content.ContentResolver,java.lang.String,java.lang.String)>","<android.net.wifi.WifiManager: java.util.List getScanResults()>","<android.net.wifi.WifiManager: java.util.List getConfiguredNetworks()>","<android.provider.Settings$System: boolean putString(android.content.ContentResolver,java.lang.String,java.lang.String)>","<com.android.internal.telephony.ISms: void sendMultipartText(java.lang.String,java.lang.String,java.util.List,java.util.List,java.util.List)>","<android.app.NotificationManager: void notify(int,android.app.Notification)>","<android.telephony.gsm.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)>","<java.net.URL: java.net.URLConnection openConnection(java.net.Proxy)>","<android.net.ConnectivityManager: android.net.NetworkInfo[] getAllNetworkInfo()>","<java.net.URL: java.net.URLConnection openConnection()>","<android.net.VpnService: android.content.Intent prepare(android.content.Context)>","<android.app.ActivityManager: void restartPackage(java.lang.String)>","<android.app.ActivityManager: void killBackgroundProcesses(java.lang.String)>","<android.media.AudioManager: void setParameters(java.lang.String)>","<android.hardware.Camera: android.hardware.Camera open()>","<android.net.ConnectivityManager: android.net.NetworkInfo getActiveNetworkInfo()>","<android.content.ContentResolver: void addPeriodicSync(android.accounts.Account,java.lang.String,android.os.Bundle,long)>","<android.content.ContentResolver: void setSyncAutomatically(android.accounts.Account,java.lang.String,boolean)>"));

        String outputFilePath = args[0];
        String flowDroidFilePath = args[1];

        List<String> txtFileNames = new ArrayList<>();

        TriggerNum.getFileList(outputFilePath, txtFileNames);

        int totalDataLeaks = 0;
        for (String txtFileName : txtFileNames) {
            int dataLeakNum = 0;

            List<DataLeak> dataLeaks = HSDF.getFlowDroidSourceSinks(flowDroidFilePath, txtFileName);
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


            dataLeakNum = filterSources.size();
            totalDataLeaks += dataLeakNum;
            System.out.println(txtFileName+":"+dataLeakNum);

        }
        System.out.println("totalDataLeaks:"+totalDataLeaks);
    }
}
