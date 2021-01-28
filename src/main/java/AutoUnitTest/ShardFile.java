package AutoUnitTest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShardFile {

    public static void main(String[] args) throws IOException {
        String testCasesFilePath = args[0];
        List<String> classFileNames = new ArrayList<>();

        getFileList(testCasesFilePath, classFileNames);

        for(int i=220001; i<=classFileNames.size()-1; i++){
            String classFileName = classFileNames.get(i);
            System.out.println("==============Process Java FileName==============" + classFileName);

            File source = new File(testCasesFilePath + "/" + classFileName);
            File dest = new File("/Users/xsun0035/workspace/monash/BasicUnitAndroidTest/app/src/androidTest/java/"+classFileName);
            try {
                FileUtils.copyFile(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        for(int j=2001; j<=4000; j++){
//            String classFileName = classFileNames.get(j);
//            System.out.println("==============Process Java FileName==============" + classFileName);
//
//            File source = new File(testCasesFilePath + "/" + classFileName);
//            File dest = new File("/Users/xsun0035/workspace/monash/BasicUnitAndroidTest/app/src/androidTest/java/TestCase2k/"+classFileName);
//            try {
//                FileUtils.copyFile(source, dest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for(int j=4001; j<=6000; j++){
//            String classFileName = classFileNames.get(j);
//            System.out.println("==============Process Java FileName==============" + classFileName);
//
//            File source = new File(testCasesFilePath + "/" + classFileName);
//            File dest = new File("/Users/xsun0035/workspace/monash/BasicUnitAndroidTest/app/src/androidTest/java/TestCase3k/"+classFileName);
//            try {
//                FileUtils.copyFile(source, dest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for(int j=6001; j<=8000; j++){
//            String classFileName = classFileNames.get(j);
//            System.out.println("==============Process Java FileName==============" + classFileName);
//
//            File source = new File(testCasesFilePath + "/" + classFileName);
//            File dest = new File("/Users/xsun0035/workspace/monash/BasicUnitAndroidTest/app/src/androidTest/java/TestCase4k/"+classFileName);
//            try {
//                FileUtils.copyFile(source, dest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for(int j=8001; j<=classFileNames.size()-1; j++){
//            String classFileName = classFileNames.get(j);
//            System.out.println("==============Process Java FileName==============" + classFileName);
//
//            File source = new File(testCasesFilePath + "/" + classFileName);
//            File dest = new File("/Users/xsun0035/workspace/monash/BasicUnitAndroidTest/app/src/androidTest/java/TestCase5k/"+classFileName);
//            try {
//                FileUtils.copyFile(source, dest);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".java")) {
                txtFileNames.add(filename);
            }
        });
    }
}
