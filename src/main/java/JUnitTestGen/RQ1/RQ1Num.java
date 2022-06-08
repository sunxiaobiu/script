package JUnitTestGen.RQ1;

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

public class RQ1Num {

    public static void main(String[] args) throws IOException {
        String allFilesPath = args[0];
        List<String> allJavaFileNames = getFileList(allFilesPath);

        //key:targetAPI,value:API invocation context
        HashMap<String, List<String>> res = new HashMap<>();

        for (String fileName : allJavaFileNames) {
            File file = new File("/hci/xiao/AllTestCases_txt/" + fileName + ".txt");
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

            if(res.containsKey(targetAPI)){
                List<String> newList = res.get(targetAPI);
                newList.add(apiInvocationList.toString());
                res.put(targetAPI, newList);
            }else{
                List<String> newList = new ArrayList<>();
                newList.add(apiInvocationList.toString());
                res.put(targetAPI, newList);
            }
        }

        System.out.println("res number:" + allJavaFileNames.size());

        //output EquivalentTest
        int totalNum = 0;
        for (String key : res.keySet()){
            if(res.get(key).size() > 1){
                totalNum += res.get(key).size();
            }
        }

        System.out.println("totalNum:"+totalNum);

    }

    public static List<String> getFileList(String outputFilePath) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString().substring(0, x.getFileName().toString().lastIndexOf('.'))).collect(Collectors.toList());

        return result;
    }
}
