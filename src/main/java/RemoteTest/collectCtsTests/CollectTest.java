package RemoteTest.collectCtsTests;

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

public class CollectTest {

    public static void main(String[] args) throws IOException {
        String ctsPath = args[0];
        String needDeleteTests = args[1];

        File needDeleteFile = new File(needDeleteTests);
        List<String> needDeleteTestCases = new ArrayList<>(Files.readAllLines(needDeleteFile.toPath(), StandardCharsets.UTF_8));

        List<String> testFileNames = new ArrayList<>();
        List<String> needDeleteFileNames = new ArrayList<>();

        getFileList(ctsPath, testFileNames, needDeleteFileNames);

        System.out.println("testFileNames.size:" + testFileNames.size());
//
        for (String fileName : testFileNames) {
//
//            File testFile = new File(fileName);
//            String line = "";
//            BufferedReader br = new BufferedReader(new FileReader(testFile));
//            boolean isTestFile = false;
//            String packagePath = "";
//
//            while ((line = br.readLine()) != null) {
//                if(line.startsWith("package")){
//                    packagePath = line.replaceAll("package ", "").replace(";","").replace(".","/");
//                    break;
//                }
//            }
//
//            File theDir = new File("/Users/xsun0035/workspace/monash/CTS/app/src/main/java/"+packagePath);
//            if (!theDir.exists()){
//                theDir.mkdirs();
//            }
//
//
//            File originFile = new File(fileName);
//            FileUtils.copyFileToDirectory(originFile, theDir);
//            System.out.println("copySuccess:"+fileName);

            /**
             * delete v1
             */
            File testFile = new File(fileName);
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(testFile));
            boolean needDelete = false;

            while ((line = br.readLine()) != null) {
                if (line.contains("UiAutomation") || line.contains(".R;") || line.contains("UiDevice")
                        || line.contains("package android.webkit.cts;")
                        || line.contains("package android.video.cts;")
                        || line.contains("Activity>")
                        || line.contains("package android.car.cts;")

                ) {
                    needDelete = true;
                }

                for (String s : needDeleteTestCases) {
                    if (line.contains(s)) {
                        needDelete = true;
                    }
                }
            }

            if (needDelete) {
                File file = new File(fileName);
                file.delete();
                System.out.println("import " + fileName.replace("/Users/xsun0035/workspace/monash/CTS/app/src/main/java/", "").replace("/", ".").replace(".java", ""));
            }

        }


    }

    public static void getFileList(String outputFilePath, List<String> txtFileNames, List<String> needDeleteFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith("Test.java")) {
                txtFileNames.add(filename);
            } else {
                needDeleteFileNames.add(filename);
            }
        });
    }
}
