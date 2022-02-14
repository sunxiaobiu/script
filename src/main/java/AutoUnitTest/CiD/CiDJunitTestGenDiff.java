package AutoUnitTest.CiD;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CiDJunitTestGenDiff {

    public static void main(String[] args) throws IOException {
        String cidResPath = args[0];
        String junitTestGenResPath = args[1];

        File cidResFile = new File(cidResPath);
        List<String> cidResFileContent = new ArrayList<>(Files.readAllLines(cidResFile.toPath(), StandardCharsets.UTF_8));

        File junitTestGenResFile = new File(junitTestGenResPath);
        List<String> junitTestGenResContent = new ArrayList<>(Files.readAllLines(junitTestGenResFile.toPath(), StandardCharsets.UTF_8));

//        junitTestGenResContent.removeAll(cidResFileContent);
//        for(String s : junitTestGenResContent){
//            System.out.println(s);
//        }
        cidResFileContent.removeAll(junitTestGenResContent);
        for(String s : cidResFileContent){
            if(!isAndroidUIMethod(s)){
                System.out.println(s);
            }

        }
    }

    public static boolean isAndroidUIMethod(String unitString) {
        return unitString.startsWith("<android.widget")
                || unitString.startsWith("<android.view")
                || unitString.startsWith("<android.webkit")
                || unitString.startsWith("<android.content.res.Resources")
                || unitString.startsWith("<android.app.Dialog")
                || unitString.startsWith("<android.app.AlertDialog")
                ;
    }
}
