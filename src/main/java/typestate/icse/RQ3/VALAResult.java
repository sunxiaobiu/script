package typestate.icse.RQ3;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VALAResult {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ3_Comparison/VALA/VALA-output-swap";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        int errorUsageNumber = 0;

        for (String fileName : logFileNames) {
            File theFile = new File(resFile + "/" + fileName);
            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            while (it.hasNext()) {
                String currentLine = it.nextLine();
                List<String> errorList = Regex.getSubUtilSimpleList(currentLine, "(<ErrorUsage)");
                if(CollectionUtils.isNotEmpty(errorList)){
                    errorUsageNumber += errorList.size();
                }
            }
        }

        System.out.println(errorUsageNumber);
    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".xml")) {
                txtFileNames.add(filename);
            }
        });
    }
}


