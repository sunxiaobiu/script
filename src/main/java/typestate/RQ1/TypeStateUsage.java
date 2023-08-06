package typestate.RQ1;

import org.apache.commons.lang3.StringUtils;
import util.ApplicationClassFilter;
import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeStateUsage {

    public static void main(String[] args) throws IOException {
        String outputFilePath = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ1/TypestateUsage_RQ1/output";

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath, txtFileNames);

        HashMap<String, Integer> res = new HashMap<>();

        for(String fileName : txtFileNames){
            int totalNum = 0;
            File file = new File(outputFilePath+"/"+fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            int startIndex = 0;
            int endIndex = fileContent.size() - 1;
            for (int i = 0; i < fileContent.size(); i++) {
                String currentLine = fileContent.get(i);
                if (currentLine.contains("==================start=====================")) {
                    startIndex = i;
                }
                if (currentLine.contains("==================end=====================")) {
                    endIndex = i;
                }
            }

            for(int i = startIndex +1; i < endIndex; i++){
                String currentLine = fileContent.get(i);
                String apiSig = Regex.getSubUtilSimple(currentLine, "(<.*?>)");
                if(StringUtils.isNotBlank(apiSig) && (
                        //view
//                        apiSig.contains("android.service.autofill.") ||  apiSig.contains("android.view.") || apiSig.contains("android.widget.")
//                                || apiSig.contains("android.webkit.") || apiSig.contains("android.app.UiAutomation") || apiSig.contains("android.window")
//                                || apiSig.contains("android.app.search.SearchSession")
//                                || apiSig.contains("android.se.omapi.Session")
//                                || apiSig.contains("android.app.smartspace.SmartspaceSession")
                        //graphics
//                        apiSig.contains("android.graphics.")
                        //wifi
//                        apiSig.contains("android.net.wifi")
                        //server
//                        apiSig.contains("server.")
                        //Data
//                        apiSig.contains("android.app.blob.BlobStoreManager.")
                        //nfc
//                        apiSig.contains("nfc.")
                        //database
//                        apiSig.contains("android.database.")
                        //location
//                        apiSig.contains("location")
                        //AccountManager
//                        apiSig.contains("AccountManager")
                        //voice
//                        apiSig.contains("voice.")
                        //hardware
//                        apiSig.contains("hardware.")
                        //media.
//                        apiSig.contains("media.")
                        //system
//                        apiSig.contains("android.os.")
                        //telephony
//                        apiSig.contains("telephony")
                        //others
//                        apiSig.contains("java.security.Signature") ||  apiSig.contains("android.app.ApplicationLoaders") || apiSig.contains("android.app.AppOpsManager")
//                                || apiSig.contains("android.app.prediction.AppPredictor") || apiSig.contains(".AppTarget.") || apiSig.contains("android.content.pm.dex.ArtManager")
//                                || apiSig.contains("AtomicDirectory")
//                                || apiSig.contains("AttributionSource")
//                                || apiSig.contains("BackupDataInpu")
//                                || apiSig.contains("android.se.omapi.Channel")
//                                || apiSig.contains("ClipDescription")
//                                || apiSig.contains("ContentProvider")
//                                || apiSig.contains("ContextWrapper")
//                                || apiSig.contains("DevicePolicyManager")
//                                || apiSig.contains("TelecomManager")
//                                || apiSig.contains("DistanceMeasurement")
//                                || apiSig.contains("javax.crypto.Cipher")
//                                || apiSig.contains("android.app.FragmentTransaction")
//                                || apiSig.contains("android.service.quickaccesswallet.GetWalletCardsCallback")
//                                || apiSig.contains("android.util.JsonReader")
//                                || apiSig.contains("android.security.keystore.KeyProtection.Builder")
//                                || apiSig.contains("ScreenInternalAudioRecorder")
//                                || apiSig.contains("android.service.translation.TranslationService")
                        //Cipher
                        apiSig.contains("Cipher")

                )){
                    totalNum ++;
                    IncrementHashMap.incrementValue(res, apiSig);
                }
            }

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/APIUsage_Cipher.txt", true)));
            out.println(totalNum);
            out.close();
        }

        //System.out.println("totalNum:"+totalNum);

        List<Map.Entry<String, Integer>> resSort = new ArrayList<Map.Entry<String, Integer>>(res.entrySet());
        Collections.sort(resSort, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });

        for (Map.Entry<String, Integer> t : resSort) {
            String outputFileName = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ1/res.txt";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName, true)));
            out.println(t.getKey() + ":" + t.getValue());
            out.close();
        }

    }

    private static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });
    }
}


