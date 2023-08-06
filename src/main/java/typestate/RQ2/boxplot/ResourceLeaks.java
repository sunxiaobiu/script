package typestate.RQ2.boxplot;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceLeaks {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/TypeState_RQ2/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        for(String fileName : logFileNames){
            File theFile = new File(resFile+"/"+fileName);
            int number  = 0;

            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            try {
                while (it.hasNext()) {
                    String currentLine = it.nextLine();
                    Set<String> apis = new LinkedHashSet<String>(Regex.getSubUtilSimpleList(currentLine, "(<.*?>)"));
                    StringBuilder misuseStr = new StringBuilder();
                    for(String api : apis){
                        misuseStr.append(api);
                    }
                    if(currentLine.contains("======ResourceLeak=======Component")){
                        number++;
                    }
                    if( currentLine.contains("======ResourceLeak=======Callback")){
                        number++;
                    }
                }
            } finally {
                LineIterator.closeQuietly(it);
            }
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/boxplot/ResourceLeaks.txt", true)));
            out.println(number);
            out.close();
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
