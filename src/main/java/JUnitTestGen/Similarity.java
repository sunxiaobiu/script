package JUnitTestGen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Similarity {

    public static void main(String[] args) throws IOException {
        String allFilesPath = args[0];
        List<String> allJavaFileNames = getFileList(allFilesPath);

        HashMap<String, String> res = new HashMap<>();
        HashMap<String, String> testCaseAPIMap = new HashMap<>();

        for (String fileName : allJavaFileNames) {
            File file = new File("/hci/xiaoyu/AllTestCases_txt/" + fileName + ".txt");
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            String targetAPI = "";
            StringBuilder apiInvocationList = new StringBuilder();
            for(int i=0; i< fileContent.size(); i++){
                String currentLine = fileContent.get(i);
                apiInvocationList.append(currentLine);
                if(i == fileContent.size() - 1){
                    targetAPI = currentLine;
                }
            }

            if(!res.containsKey(apiInvocationList.toString())){
                testCaseAPIMap.put(fileName, targetAPI);
                res.putIfAbsent(apiInvocationList.toString(), fileName);
            }
        }

        System.out.println("test number:" + allJavaFileNames.size());
        System.out.println("distinct test number:"+res.size());
        System.out.println("distinct API number:"+testCaseAPIMap.size());

        //output API invocations in minContext for simplicity analysis
        String distinctTestCasesFile = "/hci/xiaoyu/DistinctTestCases.txt";
        for (String key : res.keySet()){
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(distinctTestCasesFile, true)));
            out.println(res.get(key));
            out.close();
        }
        //output APIs
        String distinctAPI = "/hci/xiaoyu/DistinctAPI.txt";
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
