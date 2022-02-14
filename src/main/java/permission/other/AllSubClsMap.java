package permission.other;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class AllSubClsMap {

    public static void main(String[] args) throws IOException {
        run();
    }

    public static HashMap<String, String> run() throws IOException {
        File file = new File("/Users/xsun0035/Desktop/AllSubCls.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        HashMap<String, String> res = new HashMap<>();

        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);
            String returnType = Regex.getSubUtilSimple(currentLine, "(: .* )").replace(" ", "").replace(":", "");

            res.put(returnType, currentLine);
        }
        return res;
    }
}

