package typestate.RQ2.boxplot;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import util.Regex;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeStateMisuses {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/TypeState_RQ2/output";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        for(String fileName : logFileNames){
            int number=0;
            File theFile = new File(resFile+"/"+fileName);
            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            try {
                while (it.hasNext()) {
                    String currentLine = it.nextLine();
                    Set<String> apis = new LinkedHashSet<String>(Regex.getSubUtilSimpleList(currentLine, "(<.*?>)"));
                    StringBuilder misuseStr = new StringBuilder();
                    for(String api : apis){
                        misuseStr.append(api);
                    }
                    if(belong2SameClass(apis) && currentLine.contains("======Misuse=======Component")){
                        number ++;
                    }
                    if(belong2SameClass(apis) && currentLine.contains("======Misuse=======Callback")){
                        number ++;
                    }
                    if(belong2SameClass(apis) && currentLine.contains("======Misuse=======method")){
                        number ++;
                    }
                }
            } finally {
                LineIterator.closeQuietly(it);
            }
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/boxplot/TypeStateMisuses.txt", true)));
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

    private static boolean belong2SameClass(Set<String> apis){
        if(CollectionUtils.isEmpty(apis)){
            return false;
        }
        Set<String> className = new HashSet<>();
        for(String api : apis){
            className.add(Regex.getSubUtilSimple(api, "(<.*?:)"));
        }
        return className.size() == 1;
    }
}
