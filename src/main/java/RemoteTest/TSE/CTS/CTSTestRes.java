package RemoteTest.TSE.CTS;

import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CTSTestRes {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Downloads/2023.01.26_16.26.10/test_result.xml");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        Set<String> testCaseRes = new HashSet<>();

        for (String currentLine : fileContent) {
            if(currentLine.contains("<Test result=\"fail\"") && currentLine.contains("name=")){
                String testName = Regex.getSubUtilSimple(currentLine, "(name=\".*\")").replace("name=", "").replace("\"","");
                testCaseRes.add(testName);
            }
        }

        System.out.println(testCaseRes.size());

    }
}
