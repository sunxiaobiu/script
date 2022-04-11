package AutoUnitTest_Refer2JUnitTestGen.experimantalsetup;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoLineageAPKList {

    public static void main(String[] args) throws IOException {
        String allFilesPath = args[0];

        Set<String> sdk28APKList = new HashSet<>();

        File file = new File(allFilesPath);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            File apkFile = new File(fileContent.get(i));
            if(apkFile.exists()){
                String command = "aapt dump badging "+ fileContent.get(i);
                Process process = Runtime.getRuntime().exec(command);

                InputStream is = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;

                String name = "";
                while((line = reader.readLine())!= null){
                    if(line.contains("package:")){
                        name = getSubUtilSimple(line, "(package:\\ name=.+?')").replaceAll("package: name=", "").replaceAll("'", "");
                    }
                }

                if(!sdk28APKList.contains(name)){
                    sdk28APKList.add(name);
                    System.out.println(apkFile.getName());
                }
            }
        }

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
