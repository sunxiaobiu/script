package AutoUnitTest.processtest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Class2Java {

    public static void main(String[] args) throws IOException, InterruptedException {
        String testCasesFilePath = args[0];
        List<String> classFileNames = new ArrayList<>();

        getFileList(testCasesFilePath, classFileNames);

        for (String classFileName : classFileNames) {
            System.out.println("==============Process classFileName=============="+classFileName);
            Process process = Runtime.getRuntime().exec("java -jar /Users/xsun0035/workspace/monash/intellij-community/plugins/java-decompiler/engine/build/libs/fernflower.jar " +
                    "-hes=0 /Users/xsun0035/Desktop/CADroid/sootOutput/"+ classFileName +
                    " /Users/xsun0035/Desktop/CADroid/class2java/"
            );
            int exitCode = process.waitFor();
            OutputStream os = process.getOutputStream();
            if (exitCode != 0) {
                System.out.println("反编译失败："+ classFileName);
            }
        }

    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".class")) {
                txtFileNames.add(filename);
            }
        });
    }
}
