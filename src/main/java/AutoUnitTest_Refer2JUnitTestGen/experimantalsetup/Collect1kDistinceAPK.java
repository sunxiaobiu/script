package AutoUnitTest_Refer2JUnitTestGen.experimantalsetup;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Collect1kDistinceAPK {

    public static void main(String[] args) throws IOException {
        String allFilesPath = args[0];
        String targetSDKVersion = args[1];
        String outputFilePath = args[2];
        int targetSDKVersionInt = Integer.parseInt(targetSDKVersion);

        File file = new File(allFilesPath);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        HashMap<String, String> res = new HashMap<>();

        for (int i = 0; i < fileContent.size() && res.size() < 1000; i++) {
            try {
                File apkFile = new File(fileContent.get(i));
                if(apkFile.exists()){
                    String command = "aapt dump badging "+ fileContent.get(i) + " | grep targetSdkVersion:";
                    Process process = Runtime.getRuntime().exec(command);

                    InputStream is = process.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    String name = "";
                    String fileName = "";
                    while((line = reader.readLine())!= null){
                        if(line.contains("targetSdkVersion:") && Integer.parseInt(getSubUtilSimple(line, "('[0-9]+')").replaceAll("'", "")) == targetSDKVersionInt){
                            //System.out.println(fileContent.get(i));
                            fileName = fileContent.get(i);
                        }
                        if(line.contains("package:")){
                            name = getSubUtilSimple(line, "(package:\\ name=.+?')").replaceAll("package: name=", "").replaceAll("'", "");
                        }
                    }

                    if(StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(name)){
                        res.put(name, fileName);
                    }

                    process.waitFor();
                    is.close();
                    reader.close();
                    process.destroy();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        for(String key : res.keySet()){
            File outputFile = new File(outputFilePath);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
            printWriter.println(res.get(key));
            printWriter.close();
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
