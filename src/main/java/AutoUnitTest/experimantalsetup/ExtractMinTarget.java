package AutoUnitTest.experimantalsetup;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtractMinTarget {

    public static void main(String[] args) throws IOException {
        String allFilesPath = args[0];

        for(int sDKVersion = 28; sDKVersion<=28; sDKVersion ++){
            File file = new File(allFilesPath);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            int num = 0;

            ///mal/APK/google_benign/aasuited.net.contrepetrie.apk,16,-,26
            for (int i = 0; i < fileContent.size(); i++) {
                List<String> line = Arrays.asList(fileContent.get(i).split(","));
                if(line.get(3).equals(String.valueOf(sDKVersion))){
                    System.out.println(line.get(0));
                    num ++;
                }
            }

            System.out.println("targetSDKVersion_"+sDKVersion + ":"+num);
        }

    }
}
