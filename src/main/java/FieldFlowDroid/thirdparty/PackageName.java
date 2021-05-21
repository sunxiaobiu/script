package FieldFlowDroid.thirdparty;

import java.io.*;

import static AutoUnitTest.experimantalsetup.CollectAPK.getSubUtilSimple;

public class PackageName {

    public static void main(String[] args) throws IOException, InterruptedException {
        String fileName = args[0];

        File apkFile = new File(fileName);
        if(apkFile.exists()){
            String command = "aapt dump badging "+ fileName;
            Process process = Runtime.getRuntime().exec(command);

            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine())!= null){
                if(line.contains("package: name=")){
                    System.out.println(getSubUtilSimple(line, "(package: name='.+?' )").replace("package: name=", "").replace("\'", ""));
                }
            }
            process.waitFor();
            is.close();
            reader.close();
            process.destroy();
        }
    }
}
