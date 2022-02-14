package permission.other;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EasyClassConstructor {

    public static List<String> getClsName() throws IOException {
        File file = new File("/Users/xsun0035/Desktop/easyConstructor.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        return fileContent;
    }

    public static HashMap<String, String> getFullClsSig() throws IOException {
        HashMap<String, String> res = new HashMap<>();

        File file = new File("/Users/xsun0035/Desktop/easyFullClsSig.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        for(String s : fileContent){
            String classPathStr = Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", "");
            res.put(classPathStr, s);
        }
        return res;
    }

}
