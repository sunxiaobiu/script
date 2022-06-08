package JUnitTestGen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestNameAPIMap {

    public static void main(String[] args) throws IOException {
        String allFilesPath = args[0];
        List<String> allJavaFileNames = getFileList(allFilesPath);

        HashMap<String, String> testCaseAPIMap = new HashMap<>();

        for (String fileName : allJavaFileNames) {
            File file = new File("/hci/xiao/AllTestCases_txt/" + fileName + ".txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            String targetAPI = "";
            for(int i=0; i< fileContent.size(); i++){
                String currentLine = fileContent.get(i);
                if(i == fileContent.size() - 1){
                    targetAPI = currentLine;
                    if(targetAPI.startsWith("<java.lang.") && targetAPI.toString().contains("valueOf")){
                        targetAPI =  fileContent.get(fileContent.size() - 2);
                    }
                }
            }

            testCaseAPIMap.put(fileName, targetAPI);
        }

        //output test-API map
        String distinctAPI = "/hci/xiao/TestNameAPIMap.txt";
        for (String testCaseName : testCaseAPIMap.keySet()){
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(distinctAPI, true)));
            out.println(testCaseName + "|" + testCaseAPIMap.get(testCaseName));
            out.close();
        }
    }

    public static List<String> getFileList(String outputFilePath) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString().substring(0, x.getFileName().toString().lastIndexOf('.'))).collect(Collectors.toList());

        return result;
    }

}
