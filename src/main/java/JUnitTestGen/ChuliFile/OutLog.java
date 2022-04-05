package JUnitTestGen.ChuliFile;

import org.apache.commons.io.FileUtils;
import util.FileUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OutLog {

    public static void main(String[] args) throws IOException {
        List<String> javaFileList = getFileList("/Users/xsun0035/workspace/monash/ASE_JUnitTestGen/app/src/androidTest/java");

        for (String javaFileName : javaFileList) {
            FileUtil.replaceSelected(javaFileName, "var1.append(var2);", "var1.append(var0);");
        }
    }

    public static List<String> getFileList(String outputFilePath) throws IOException {
        List<String> txtFileNames = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".java")) {
                txtFileNames.add(filename);
            }
        });

        return txtFileNames;
    }
}
