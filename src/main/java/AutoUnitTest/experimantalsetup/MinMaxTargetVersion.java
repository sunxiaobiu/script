package AutoUnitTest.experimantalsetup;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinMaxTargetVersion {

    public static void main(String[] args) throws IOException {
        String allFilesPath = args[0];

        File file = new File(allFilesPath);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            try {
                File apkFile = new File(fileContent.get(i));
                if(apkFile.exists()){
                    String command = "aapt dump badging "+ fileContent.get(i) + " | grep targetSdkVersion:";
                    Process process = Runtime.getRuntime().exec(command);

                    InputStream is = process.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    StringBuilder minMaxTarget = new StringBuilder();
                    minMaxTarget.append(fileContent.get(i) + ",");

                    Integer min = null;
                    Integer max = null;
                    Integer target = null;
                    while((line = reader.readLine())!= null){
                        if(line.contains("sdkVersion:")){
                            min = Integer.parseInt(getSubUtilSimple(line, "('[0-9]+')").replaceAll("'", ""));
                        }
                        if(line.contains("maxSdkVersion:")){
                            max = Integer.parseInt(getSubUtilSimple(line, "('[0-9]+')").replaceAll("'", ""));
                        }
                        if(line.contains("targetSdkVersion:")){
                            target = Integer.parseInt(getSubUtilSimple(line, "('[0-9]+')").replaceAll("'", ""));
                        }
                    }

                    if(min != null){
                        minMaxTarget.append(min + ",");
                    }else{
                        minMaxTarget.append("-" + ",");
                    }

                    if(max != null){
                        minMaxTarget.append(max + ",");
                    }else{
                        minMaxTarget.append("-" + ",");
                    }

                    if(target != null){
                        minMaxTarget.append(target);
                    }else{
                        minMaxTarget.append("-");
                    }

                    System.out.println(minMaxTarget);

                    process.waitFor();
                    is.close();
                    reader.close();
                    process.destroy();
                }
            } catch (Exception e) {
                continue;
            }
        }
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
}
