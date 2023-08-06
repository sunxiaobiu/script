package typestate.icse.RQ2.res;

import org.apache.commons.collections4.CollectionUtils;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultDiff_ResourceLeak {
    public static void main(String[] args) throws IOException {
        String minLineageAppPath = "/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ2/minLineageApp.txt";
        File minLineageAppFile = new File(minLineageAppPath);
        List<String> minLineageLines = new ArrayList<>(Files.readAllLines(minLineageAppFile.toPath(), StandardCharsets.UTF_8));

        String maxLineageAppPath = "/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ2/maxLineageApp.txt";
        File maxLineageAppFile = new File(maxLineageAppPath);
        List<String> maxLineageLines = new ArrayList<>(Files.readAllLines(maxLineageAppFile.toPath(), StandardCharsets.UTF_8));

        int apkNum = 0;
        int misuseNum = 0;
        int minSuccessNum = 0;
        int maxSuccessNum = 0;
        for (int i = 0; i < minLineageLines.size(); i++) {
            String minFileName = Regex.getSubUtilSimple(minLineageLines.get(i), ("(;.*)")).replace(";", "");
            String maxFileName = Regex.getSubUtilSimple(maxLineageLines.get(i), ("(;.*)")).replace(";", "");

            File minFile = new File("/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ2/lineageExperiment/output/" + minFileName + ".txt");
            File maxFile = new File("/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ2/lineageExperiment/output/" + maxFileName + ".txt");


            Set<String> minFileMisuses = extractMisuses(minFile);
            Set<String> maxFileMisuses = extractMisuses(maxFile);

            boolean isMinFileSuccess = false;
            boolean isMaxFileSuccess = false;

            List<String> minfileLines = new ArrayList<>(Files.readAllLines(minFile.toPath(), StandardCharsets.UTF_8));
            for (String singleLine : minfileLines) {
                if(singleLine.contains("==>after detectMisusePattern")){
                    minSuccessNum++;
                    isMinFileSuccess = true;
                }
            }

            List<String> maxfileLines = new ArrayList<>(Files.readAllLines(maxFile.toPath(), StandardCharsets.UTF_8));
            for (String singleLine : maxfileLines) {
                if(singleLine.contains("==>after detectMisusePattern")){
                    maxSuccessNum++;
                    isMaxFileSuccess = true;
                }
            }

            minFileMisuses.removeAll(maxFileMisuses);
            if (CollectionUtils.isNotEmpty(minFileMisuses) && isMinFileSuccess && isMaxFileSuccess) {
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/ResultDiff.txt", true)));
                out.println(Regex.getSubUtilSimple(minLineageLines.get(i), ("(.*;)")).replace(";", ""));
                out.close();
                apkNum++;
                for (String s : minFileMisuses) {
                    PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/ResultDiff.txt", true)));
                    out1.println(s);
                    out1.close();
                    misuseNum++;
                }
            }

        }

        System.out.println("含有fixed的APK数量："+apkNum);
        System.out.println("被fixed的misuse的数量："+misuseNum);
        System.out.println("min分析成功的apk数量："+minSuccessNum);
        System.out.println("max分析成功的apk数量："+maxSuccessNum);
    }


    public static Set<String> extractMisuses(File file) throws IOException {
        Set<String> res = new HashSet<>();
        List<String> fileLines = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for (String singleLine : fileLines) {
            if (singleLine.startsWith("======ResourceLeak=======")) {
                res.add(singleLine);
            }
        }

        return res;
    }
}
