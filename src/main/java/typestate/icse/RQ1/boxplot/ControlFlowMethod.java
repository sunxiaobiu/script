package typestate.icse.RQ1.boxplot;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControlFlowMethod {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ1_TypeStateMisuse/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);



        for (String fileName : logFileNames) {
            int misuseMethodNum = 0;
            int misuseComponentNum = 0;
            int misuseICCNum = 0;
            int misuseCallbackNum = 0;

            //Set<String> existMisuse = new HashSet<>();
            File theFile = new File(resFile + "/" + fileName);
            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            while (it.hasNext()) {
                String currentLine = it.nextLine();

                if(currentLine.contains("======Misuse=======ICC") || currentLine.contains("======ResourceLeak=======ICC")){
                    misuseICCNum ++;
                }
                if(currentLine.contains("======Misuse=======Component") || currentLine.contains("======ResourceLeak========Component")){
                    misuseComponentNum ++;
                }
                if(currentLine.contains("======Misuse=======Callback") || currentLine.contains("======ResourceLeak=======Callback")){
                    misuseCallbackNum ++;
                }
                if(currentLine.contains("======Misuse=======method") || currentLine.contains("======ResourceLeak=======method")){
                    misuseMethodNum ++;
                }
            }

            PrintWriter outMethod = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/BoxPlot-Method.txt", true)));
            outMethod.println(misuseMethodNum);
            outMethod.close();

            PrintWriter outCallback = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/BoxPlot-Callback.txt", true)));
            outCallback.println(misuseCallbackNum);
            outCallback.close();

            PrintWriter outComponent = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/BoxPlot-Component.txt", true)));
            outComponent.println(misuseComponentNum);
            outComponent.close();

            PrintWriter outICC = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/BoxPlot-ICC.txt", true)));
            outICC.println(misuseICCNum);
            outICC.close();
        }
    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });
    }
}
