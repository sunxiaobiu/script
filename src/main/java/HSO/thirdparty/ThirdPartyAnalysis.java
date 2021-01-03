package HSO.thirdparty;

import HSO.RQ1.TriggerNum;
import HSO.model.HSO;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThirdPartyAnalysis {

    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath, txtFileNames);

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
         * 统计含有第三方库的trigger
         */
        int thirdPartyNum = 0;
        for (HSO hso : result) {
            if (hso.hasCommonCaese == true) {
                continue;
            }
            if(!hso.isHSO){
                continue;
            }

            boolean flag = false;
            AtomicReference<String> keyLocation = new AtomicReference<>("");
            TriggerNum.checkLocation(hso, keyLocation);
            if (StringUtils.isNotBlank(keyLocation.get())) {
                flag = true;
            }

            if(flag == false) {
                AtomicReference<String> keyTime = new AtomicReference<>("");
                TriggerNum.checkTime(hso, keyTime);
                if (StringUtils.isNotBlank(keyTime.get())) {
                    flag = true;
                }
            }

            if(flag == false) {
                AtomicReference<String> keySMS = new AtomicReference<>("");
                TriggerNum.checkSMS(hso, keySMS);
                if (StringUtils.isNotBlank(keySMS.get())) {
                    flag = true;
                }
            }

            if(flag == false) {
                AtomicReference<String> keyAdroidOS = new AtomicReference<>("");
                TriggerNum.checkAdroidOS(hso, keyAdroidOS);
                if (StringUtils.isNotBlank(keyAdroidOS.get())) {
                    flag = true;
                }
            }

            if(flag == false) {
                AtomicReference<String> keyPackage = new AtomicReference<>("");
                TriggerNum.checkPackage(hso, keyPackage);
                if (StringUtils.isNotBlank(keyPackage.get())) {
                    flag = true;
                }
            }

            if(flag == false) {
                AtomicReference<String> keyHardwareAddress = new AtomicReference<>("");
                TriggerNum.checkHardwareAddress(hso, keyHardwareAddress);
                if (StringUtils.isNotBlank(keyHardwareAddress.get())) {
                    flag = true;
                }
            }

            if(flag == false){
                AtomicReference<String> keyAll = new AtomicReference<>("");
                TriggerNum.checkAll(hso, keyAll);
                if (StringUtils.isNotBlank(keyAll.get())) {
                    flag = true;
                }
            }

            if(flag){
                String apkPackageName = null;
                try {
                    apkPackageName = getPackageName(hso);
                } catch (Exception e) {
                    continue;
                }
                if(!hso.declareMethod.startsWith("<"+apkPackageName)){
                    System.out.println("HSO declareMethod:"+ hso.declareMethod +"--> apkPackageName:"+apkPackageName);
                    thirdPartyNum ++;
                }
            }
        }

        System.out.println("thirdPartyNum:"+thirdPartyNum);


    }

    private static String getPackageName(HSO hso) throws IOException {
        String apkPackageName = "";
        String apk = "/Users/xsun0035/Desktop/goodware_dataset/" + hso.file + ".apk";
        File file = new File(apk);

        if (file.exists() && file.isFile()) {
            ApkFile apkFile = new ApkFile(file);
            ApkMeta apkMeta = apkFile.getApkMeta();
            apkPackageName = apkMeta.getPackageName();
        }else{
            System.out.println("File not exist:"+file);
        }

        return apkPackageName;
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
