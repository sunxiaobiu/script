package RemoteTest.chulitinkerapk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchInstrumentUsage {

    public static void main(String[] args) throws IOException {
        String ctsPath = args[0];

        List<String> testFileNames = new ArrayList<>();
        List<String> needDeleteFileNames = new ArrayList<>();

        getFileList(ctsPath, testFileNames, needDeleteFileNames);

        Set<String> res = new HashSet<>();

        for (String fileName : testFileNames) {
            /**
             * replact instrument
             */
            File testFile = new File(fileName);
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(testFile));
            boolean isInstrument = false;

            while ((line = br.readLine()) != null) {
                if (line.contains("InstrumentationRegistry") && !line.startsWith("import") &&
                        !line.contains("InstrumentationRegistry.getContext()")
                        && !line.contains("InstrumentationRegistry.getTargetContext()")
                        && !line.contains("InstrumentationRegistry.getInstrumentation().getTargetContext()")
                        && !line.contains("InstrumentationRegistry.getInstrumentation().getContext()")
                        && !line.contains("InstrumentationRegistry.getTargetContext()")

                ) {
                    System.out.println(line);
                    res.add(fileName);
                }
            }
        }

        System.out.println("=============================");
        for(String s : res){
            System.out.println(s);
        }

    }

    public static void getFileList(String outputFilePath, List<String> txtFileNames, List<String> needDeleteFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith("Test.java") || filename.endsWith("Tests.java") ) {
                txtFileNames.add(filename);
            } else {
                needDeleteFileNames.add(filename);
            }
        });
    }
}
