package JUnitTestGen.evosuite.Result;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoSuchMethodError {

    public static void main(String[] args) throws IOException {
        String allFilesPath = "/Users/xsun0035/Desktop/CADroid/RQ3/Evosuite";
        List<String> fileList = getFileList(allFilesPath);

        Set<String> testCaseSet = new HashSet<>();

        for(String filePathName : fileList){
            File file = new File(filePathName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for(String oneLineStr : fileContent){
                stringBuilder.append(oneLineStr);
            }

            for(int i=0; i< fileContent.size(); i++){
                String currentLineStr = fileContent.get(i);
                if(currentLineStr.contains("java.lang.NoSuchMethodError") && !stringBuilder.toString().contains("notGeneratedAnyTest")){
                    testCaseSet.add(Regex.getSubUtilSimple(currentLineStr, "(message=\".*?\")"));
                }
            }
        }

        System.out.println(testCaseSet.size());
        for(String s : testCaseSet){
            System.out.println(s);
        }
    }


    public static List<String> getFileList(String outputFilePath) throws IOException {
        List<String> txtFileNames = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith("ESTest.xml")) {
                txtFileNames.add(filename);
            }
        });

        return txtFileNames;
    }
}
