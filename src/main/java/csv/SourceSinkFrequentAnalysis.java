package csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.collections4.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SourceSinkFrequentAnalysis {

    public static void main(String[] args) throws IOException, CsvValidationException {
        String strFile = "/Users/xsun0035/Desktop/HSO/实验统计结果/sourceSink.csv";
        FileWriter csvWriter = new FileWriter("/Users/xsun0035/Desktop/HSO/实验统计结果/triggerConditionAfterProProcess.csv");
        Path path = Paths.get("/Users/xsun0035/Desktop/HSO/实验统计结果/sourceSinkAfterProProcess.txt");

        Map<String, Integer> sourceMap = new HashMap();
        Map<String, Integer> sinkMap = new HashMap();

        CSVReader reader = new CSVReader(new FileReader(strFile));
        String[] nextLine;
        List<List<String>> itemSets = new ArrayList<>();
        int lineNumber = 0;
        while ((nextLine = reader.readNext()) != null) {
            lineNumber++;
            System.out.println("Line # " + lineNumber);

            List<String> line = Arrays.asList(nextLine);
            String source = line.get(0);
            String sink = line.get(1);

//            String methodName = "";
//            String[] originStringList = source.split(" ");
//            Pattern pattern1 = Pattern.compile("([<>a-zA-Z]+)", Pattern.DOTALL);
//            Matcher matcher1 = pattern1.matcher(originStringList[2]);
//            if (matcher1.find()) {
//                methodName = matcher1.group(1).replace("<", "").replace(">", "").trim();
//            }
//            source = methodName;


//            if(source.contains("android.net.ConnectivityManager")){
//                source = "<android.net.ConnectivityManager: android.net.NetworkInfo getActiveNetworkInfo()>";
//            }else if(source.contains("java.net.URL")){
//                source = "<java.net.URL: java.net.URLConnection openConnection()>";
//            }else if(source.contains("android.webkit.WebView")){
//                source = "<android.webkit.WebView: void <init>(android.content.Context)>";
//            }else if(source.contains("android.telephony.TelephonyManager")){
//                source = "<android.telephony.TelephonyManager: java.lang.String getDeviceId()>";
//            }else if(source.contains("android.telephony.SmsManager")){
//                source = "<android.telephony.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)>";
//            }else if(source.contains("android.media")){
//                source = "<android.media.MediaPlayer: void start()>";
//            }else if(source.contains("java.net.URLConnection")){
//                source = "<java.net.URLConnection: void connect()>";
//            }else if(source.contains("android.os.PowerManager$WakeLock")){
//                source = "<android.os.PowerManager$WakeLock: void release()>";
//            }else if(source.contains("android.location.LocationManager")){
//                source = "<android.location.LocationManager: android.location.Location getLastKnownLocation(java.lang.String)>";
//            }else if(source.contains("android.net.wifi.WifiManager")){
//                source = "<android.net.wifi.WifiManager: boolean isWifiEnabled()>";
//            }else if(source.contains("android.app.ActivityManager")){
//                source = "<android.app.ActivityManager: void restartPackage(java.lang.String)>";
//            }else if(source.contains("android.os.Vibrator")){
//                source = "<android.os.Vibrator: void vibrate(long)>";
//            }else if(source.contains("android.bluetooth.BluetoothAdapter")){
//                source = "<android.bluetooth.BluetoothAdapter: boolean isEnabled()>";
//            }else if(source.contains("android.pim.vcard.VCardComposer")){
//                source = "<android.pim.vcard.VCardComposer: boolean init()>";
//            }else if(source.contains("android.widget.VideoView")) {
//                source = "<android.widget.VideoView: void start()>";
//            }else if(source.contains("android.accounts.AccountManager")) {
//                source = "<android.accounts.AccountManager: android.accounts.Account[] getAccounts()>";
//            }else if(source.contains("android.app.KeyguardManager")) {
//                source = "<android.app.KeyguardManager$KeyguardLock: void disableKeyguard()>";
//            }
//
//            if(sink.contains("startActivity")){
//                sink = "<android.app.Activity: void startActivity(android.content.Intent)>";
//            }else if(sink.contains("android.util.Log")){
//                sink = "<android.util.Log: int d(java.lang.String,java.lang.String)>";
//            }else if(sink.contains("android.content.Intent putExtra")){
//                sink = "<android.content.Intent: android.content.Intent putExtra(java.lang.String,double[])>";
//            }else if(sink.contains("android.content.Intent registerReceiver")){
//                sink = "<android.content.Context: android.content.Intent registerReceiver(android.content.BroadcastReceiver,android.content.IntentFilter)>";
//            }else if(sink.contains("sendBroadcast")){
//                sink = "<android.app.Activity: void sendBroadcast(android.content.Intent)>";
//            }else if(sink.contains("SharedPreferences$Editor")){
//                sink = "<android.content.SharedPreferences$Editor: android.content.SharedPreferences$Editor putString(java.lang.String,java.lang.String)>";
//            }else if(sink.contains("java.lang.String replace")){
//                sink = "<java.lang.String: java.lang.String replace(java.lang.String,java.lang.String)>";
//            }else if(sink.contains("getInputStream")){
//                sink = "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>";
//            }else if(sink.contains("connect()")){
//                sink = "<java.net.HttpURLConnection: void connect()>";
//            }else if(sink.contains("<android.os.Bundle: void put")){
//                sink = "<android.os.Bundle: void putAll(android.os.Bundle)>";
//            }else if(sink.contains("<java.io.OutputStream: void write")){
//                sink = "<java.io.OutputStream: void write(byte[])>";
//            }else if(sink.contains("setResult(int,android.content.Intent)")){
//                sink = "<android.app.Activity: void setResult(int,android.content.Intent)>";
//            }else if(sink.contains("java.io.FileOutputStream: void write")){
//                sink = "<java.io.FileOutputStream: void write(byte[])>";
//            }else if(sink.contains("startService(android.content.Intent)")){
//                sink = "<android.app.Activity: android.content.ComponentName startService(android.content.Intent)>";
//            }else if(sink.contains("bindService(android.content.Intent,android.content.ServiceConnection,int)")){
//                sink = "<android.content.Context: boolean bindService(android.content.Intent,android.content.ServiceConnection,int)>";
//            }else if(sink.contains("sendMessage(android.os.Message)")){
//                sink = "<android.os.Handler: boolean sendMessage(android.os.Message)>";
//            }else if(sink.contains("java.io.ByteArrayOutputStream: void write")){
//                sink = "<java.io.ByteArrayOutputStream: void write(byte[])>";
//            }else if(sink.contains("void write")){
//                sink = "<java.io.OutputStream: void write(int)>";
//            }else if(sink.contains("java.io.BufferedWriter: java.io.Writer append")){
//                sink = "<java.io.BufferedWriter: java.io.Writer append(java.lang.CharSequence)>";
//            }else if(sink.contains("java.io.OutputStream getOutputStream()")){
//                sink = "<java.net.HttpURLConnection: java.io.OutputStream getOutputStream()>";
//            }else if(sink.contains("org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)")){
//                sink = "<org.apache.http.client.HttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>";
//            }

            if(source.contains("Bluetooth")
                    ||source.contains("bluetooth")){
                incrementValue(sourceMap, source);
                incrementValue(sinkMap, sink);
            }

            List<String> newLine = new ArrayList<>();
//            String newSource = source.replace(",", "|");
//            String newSink = sink.replace(",", "|");

            source = "\""+source+"\"";
            sink = "\""+sink+"\"";
            newLine.add(source);
            newLine.add(sink);
            itemSets.add(newLine);

//            String fileContent = source + "," +sink + "\n";
//            Files.write(path, fileContent.getBytes(), StandardOpenOption.APPEND);

        }

        System.out.println(sortMapByValue(sourceMap));
        System.out.println(sortMapByValue(sinkMap));

//        for (List<String> itemSet : itemSets) {
//            if (CollectionUtils.isNotEmpty(itemSet)) {
//                csvWriter.append(String.join(",", itemSet));
//                csvWriter.append("\n");
//            }
//        }
//
//        csvWriter.flush();
//        csvWriter.close();

    }

    public static List<Map.Entry<String, Integer>> sortMapByValue(Map<String, Integer> map){
        Set<Map.Entry<String, Integer>> set = map.entrySet();

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
                set);

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {

                return o2.getValue().compareTo(o1.getValue());
            }

        });

        return list;
    }

    public static <K> void incrementValue(Map<K, Integer> map, K key) {
        // get value of the specified key
        Integer count = map.get(key);

        // if the map contains no mapping for the key, then
        // map the key with value of 1
        if (count == null) {
            map.put(key, 1);
        }
        // else increment the found value by 1
        else {
            map.put(key, count + 1);
        }
    }
}
