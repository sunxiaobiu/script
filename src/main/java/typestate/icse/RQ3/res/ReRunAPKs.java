package typestate.icse.RQ3.res;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReRunAPKs {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2024/CrySL_TypeStateMisuse_Compare/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        File typeState_GoodwareFile = new File("/Users/xsun0035/Desktop/TypeState_ICSE2024/CrySL_TypeStateMisuse_Compare/TypeState_Goodware.txt");
        List<String> allAPK = new ArrayList<>(Files.readAllLines(typeState_GoodwareFile.toPath(), StandardCharsets.UTF_8));

        allAPK.removeAll(logFileNames);

        for(String s : allAPK){
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/Rerun.txt", true)));
            out.println(s);
            out.close();
        }
    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename.replace(".txt",".apk"));
            }
        });
    }
}
