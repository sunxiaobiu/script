package JUnitTestGen.evosuite;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeleteCompileFailFile {
    public static void main(String[] args) throws IOException {
        List<String> fileList = getFileList("/Users/xsun0035/workspace/monash/ASE_EvoSuite/app/src/androidTest/java/");

        File toDeletefile = new File("/Users/xsun0035/Desktop/ToDeleteTests.txt");
        List<String> toDeleteTests = new ArrayList<>(Files.readAllLines(toDeletefile.toPath(), StandardCharsets.UTF_8));

        for (int i = 0; i < fileList.size(); i++) {
            //app/src/androidTest/java/
            File toDelFile = new File(fileList.get(i));
            for(String testName : toDeleteTests){
                if(toDelFile.getName().equals(testName)){
                    System.out.println(toDelFile.getName());
                    toDelFile.delete();
                }
            }
        }
    }

    public static List<String> getFileList(String outputFilePath) throws IOException {
        List<String> txtFileNames = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith("_ESTest.java")) {
                txtFileNames.add(filename);
            }
        });

        return txtFileNames;
    }
}
