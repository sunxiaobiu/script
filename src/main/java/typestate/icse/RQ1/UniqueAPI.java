package typestate.icse.RQ1;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueAPI {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/test.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        Set<String> APIs = new HashSet<>();
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);
            String className = Regex.getSubUtilSimple(currentLine, "(<.*? )").replace(":","");
            String methodName = Regex.getSubUtilSimple(currentLine, "( [a-zA-Z0-9<>]+?\\()").replace(" ","");
            String res = className + methodName;
            APIs.add(res);
        }

        System.out.println(APIs.size());

    }
}
