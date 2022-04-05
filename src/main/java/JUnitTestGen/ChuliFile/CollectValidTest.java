package JUnitTestGen.ChuliFile;

import org.apache.commons.io.FileUtils;

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

public class CollectValidTest {

    public static void main(String[] args) throws IOException {
        File testCasesFile = new File("/hci/xiaoyu/DistinctTestCases.txt");
        List<String> testCasesNames = new ArrayList<>(Files.readAllLines(testCasesFile.toPath(), StandardCharsets.UTF_8));

        List<String> javaFileList = getFileList("/hci/xiaoyu/AllTestCases_Java");
        for(String javaFileName : javaFileList){
            if(testCasesNames.contains(javaFileName)){
                File source = new File("/hci/xiaoyu/AllTestCases_Java/" + javaFileName + ".java");
                File dest = new File("/hci/xiaoyu/ToBeExecuted/"+ javaFileName + ".java");
                try {
                    FileUtils.copyFile(source, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static List<String> getFileList(String outputFilePath) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString().substring(0, x.getFileName().toString().lastIndexOf('.'))).collect(Collectors.toList());

        return result;
    }
}
