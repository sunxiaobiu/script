package RemoteTest.compareCiD;

import util.CollectionIntersection;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CiDLazyCowDiff {

    public static void main(String[] args) throws IOException {
        String cidResPath = "/Users/xsun0035/Desktop/CiD.txt";
        String lazyCowPath = "/Users/xsun0035/Desktop/LazyCow.txt";

        File cidResFile = new File(cidResPath);
        List<String> cidResFileContent = new ArrayList<>(Files.readAllLines(cidResFile.toPath(), StandardCharsets.UTF_8));

        File lazyCowFile = new File(lazyCowPath);
        List<String> lazyCowContent = new ArrayList<>(Files.readAllLines(lazyCowFile.toPath(), StandardCharsets.UTF_8));

        System.out.println("cid总数："+cidResFileContent.size());
        System.out.println("lazycow总数："+lazyCowContent.size());

        lazyCowContent.removeAll(cidResFileContent);
        System.out.println("lazyCow独有的（cid的false negative）："+lazyCowContent.size());

        lazyCowContent = new ArrayList<>(Files.readAllLines(lazyCowFile.toPath(), StandardCharsets.UTF_8));

        int cidUnique = 0;
        cidResFileContent.removeAll(lazyCowContent);
        for(String s : cidResFileContent){
            if(!isAndroidUIMethod(s)){
                cidUnique ++;
            }
        }
        System.out.println("CiD独有的（lazyCow的false negative）："+cidUnique);
        cidResFileContent = new ArrayList<>(Files.readAllLines(cidResFile.toPath(), StandardCharsets.UTF_8));

        Set<String> intersection = CollectionIntersection.findIntersection(new HashSet<>(), lazyCowContent, cidResFileContent);
        System.out.println("共有的："+intersection.size());

        for(String s : intersection){
            System.out.println(s);
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
