package SensorLeak.preprocess;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectAvaliableAPK {

    public static void main(String[] args) throws IOException {

        String logFilePath = args[0];

        List<String> apkFileNames = new ArrayList<>();

        getFileList(logFilePath, apkFileNames);

        Set<String> packageNameList = new HashSet<>();

        for (String apkFile : apkFileNames) {
            String packageName = "";
            try {
                File sourceFilePath = new File(logFilePath + "/" + apkFile + ".apk");
                packageName = getPackageName(sourceFilePath.getAbsolutePath());

                if(StringUtils.isBlank(packageName)){
                    sourceFilePath.delete();
                }
                if (packageNameList.contains(packageName)) {
                    sourceFilePath.delete();
                }else{
                    packageNameList.add(packageName);
                }

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private static String getPackageName(String apkPath) throws IOException {
        String name = "";
        String command = "aapt dump badging " + apkPath;
        Process process = Runtime.getRuntime().exec(command);
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            if(line.contains("package:")){
                name = getSubUtilSimple(line, "(package:\\ name=.+?')").replaceAll("package: name=", "").replaceAll("'", "");
            }
        }
        return name;
    }

    public static void getFileList(String outputFilePath, List<String> apkFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".apk")) {
                apkFileNames.add(filename.replace(".apk", ""));
            }
        });
    }

    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

}


