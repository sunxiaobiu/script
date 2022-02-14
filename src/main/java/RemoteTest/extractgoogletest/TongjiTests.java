package RemoteTest.extractgoogletest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TongjiTests {

    public static void main(String[] args) throws IOException {
        String ctsPath = args[0];

        List<String> testFileNames = new ArrayList<>();
        List<String> needDeleteFileNames = new ArrayList<>();

        getFileList(ctsPath, testFileNames, needDeleteFileNames);

        System.out.println("testFileNames.size:" + testFileNames.size());
        int testNum = 0;

        for(String fileName : testFileNames){
            File testFile = new File(fileName);
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(testFile));

            while ((line = br.readLine()) != null) {
                if (line.contains("@Test")
                || line.contains("@SmallTest")
                || line.contains("@LargeTest")
                || line.contains("@MediumTest")
                ){
                    testNum++;
                }
            }
        }

        System.out.println("testNum:"+testNum);
    }


    public static void getFileList(String outputFilePath, List<String> txtFileNames, List<String> needDeleteFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".java")) {
                txtFileNames.add(filename);
            } else {
                needDeleteFileNames.add(filename);
            }
        });
    }
}
