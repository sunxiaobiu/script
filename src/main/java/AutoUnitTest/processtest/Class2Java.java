package AutoUnitTest.processtest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static AutoUnitTest.experimantalsetup.CollectAPK.getSubUtilSimple;

public class Class2Java {

    public static void main(String[] args) throws IOException, InterruptedException {
        String testCasesFilePath = args[0];
        List<String> classFileNames = new ArrayList<>();

        getFileList(testCasesFilePath, classFileNames);

        for (String classFileName : classFileNames) {
            System.out.println("/Users/xsun0035/workspace/release/JUnitTestGen/sootOutput/" + classFileName);
//            Process process = Runtime.getRuntime().exec("java -jar /Users/xsun0035/workspace/monash/fernflower/build/libs/fernflower.jar /Users/xsun0035/workspace/release/JUnitTestGen/sootOutput/" + classFileName + " /Users/xsun0035/Desktop/CADroid/class2java/");
//            InputStream is = process.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//            int exitCode = process.waitFor();
//            is.close();
//            reader.close();
//            process.destroy();
//
//            if (exitCode != 0) {
//                System.out.println("反编译失败：" + classFileName);
//            }
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
