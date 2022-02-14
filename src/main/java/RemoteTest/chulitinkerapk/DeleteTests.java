package RemoteTest.chulitinkerapk;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeleteTests {


    public static void main(String[] args) throws IOException {
        String ctsPath = args[0];

//        List<String> testFileNames = new ArrayList<>();
//        List<String> needDeleteFileNames = new ArrayList<>();
//
//        getFileList(ctsPath, testFileNames, needDeleteFileNames);
//
//        System.out.println("testFileNames.size:" + testFileNames.size());
        File file = new File(ctsPath);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));


//
//        for (String fileName : testFileNames) {
//            File originFile = new File(fileName);
//            File desFile = new File("/Users/xsun0035/Desktop/AOSP_framework_tests/"+originFile.getName());
//            FileUtils.copyFile(originFile, desFile);
//            originFile.delete();
//            System.out.println("success:"+fileName);
//        }
        for (String fileName : fileContent) {
            File originFile = new File("/Users/xsun0035/workspace/monash/LazyCow/app/src/main/java/tinker/sample/android/androidtest/"+fileName);
            originFile.delete();
            System.out.println("delete success:"+fileName);
        }
    }

    public static void getFileList(String outputFilePath, List<String> txtFileNames, List<String> needDeleteFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            //if (filename.endsWith(".java")) {
                txtFileNames.add(filename);
            //}
        });
    }


}
