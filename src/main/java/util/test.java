package util;

import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.nio.file.Files.readAllLines;

public class test {

    public static void main(String[] args) throws IOException {
        File a = new File("/Users/xsun0035/Desktop/a.txt");
        File b = new File("/Users/xsun0035/Desktop/b.txt");
        List<String> afileContent = new ArrayList<>(readAllLines(a.toPath(), StandardCharsets.UTF_8));
        List<String> bfileContent = new ArrayList<>(readAllLines(b.toPath(), StandardCharsets.UTF_8));

        Set<String> atargetSet = new HashSet<>(afileContent);
        Set<String> btargetSet = new HashSet<>(bfileContent);

        atargetSet.removeAll(btargetSet);
        for(String s : atargetSet){
            System.out.println(s);
        }
    }
}
