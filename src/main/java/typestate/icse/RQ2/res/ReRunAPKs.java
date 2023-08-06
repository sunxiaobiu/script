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

public class ReRunAPKs {

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

            boolean minFileSuccess = false;
            List<String> minfileLines = new ArrayList<>(Files.readAllLines(minFile.toPath(), StandardCharsets.UTF_8));
            for (String singleLine : minfileLines) {
                if(singleLine.contains("==>after detectMisusePattern")){
                    minFileSuccess = true;
                }
            }
            if(!minFileSuccess){
                PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/ReRun.txt", true)));
                out1.println(maxFileName+".apk");
                out1.close();
            }

            boolean maxFileSuccess = false;
            List<String> maxfileLines = new ArrayList<>(Files.readAllLines(maxFile.toPath(), StandardCharsets.UTF_8));
            for (String singleLine : maxfileLines) {
                if(singleLine.contains("==>after detectMisusePattern")){
                    maxFileSuccess = true;
                }
            }

            if(!maxFileSuccess){
                PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/ReRun.txt", true)));
                out1.println(maxFileName+".apk");
                out1.close();
            }
        }

        System.out.println(apkNum);
        System.out.println(misuseNum);
        System.out.println(minSuccessNum);
        System.out.println(maxSuccessNum);
    }


    public static Set<String> extractMisuses(File file) throws IOException {
        Set<String> res = new HashSet<>();
        List<String> fileLines = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for (String singleLine : fileLines) {
            if (singleLine.startsWith("======Misuse=======") || (singleLine.startsWith("======ResourceLeak======="))) {
                res.add(singleLine);
            }
        }

        return res;
    }
}
