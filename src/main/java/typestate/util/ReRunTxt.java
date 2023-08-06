package typestate.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReRunTxt {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ1/TypestateUsage_RQ1/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        String goodwareAllApk = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ1/TypestateUsage_RQ1/TypeState_Goodware.txt";
        List<String> goodwarefiles = new ArrayList<>(Files.readAllLines(new File(goodwareAllApk).toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < goodwarefiles.size(); i++) {
            String apkFileName = goodwarefiles.get(i).replace(".apk", "");
            if(!logFileNames.contains(apkFileName)){
                String outputFileName = "/Users/xsun0035/Desktop/RQ1_txt.txt";
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName, true)));
                out.println(apkFileName+".apk");
                out.close();
            }
        }

    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename.replace(".txt", ""));
            }
        });
    }
}
