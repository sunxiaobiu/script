package APIUsageRecommend;

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

public class PreliminaryStudy {

    public static void main(String[] args) throws IOException {
        String allFilesPath = "/hci/xiao/ThirdParty/run/sootOutput";
        List<String> allJavaFileNames = getFileList(allFilesPath);

        //key:targetAPI,value:API invocation context
        HashMap<String, List<String>> res = new HashMap<>();

        for (String fileName : allJavaFileNames) {
            File file = new File("/hci/xiao/ThirdParty/run/sootOutput/" + fileName + ".txt");
            if(!file.exists()){
                continue;
            }
            System.out.println(file.getName());
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            String targetAPI = "";
            StringBuilder apiInvocationList = new StringBuilder();
            for(int i=0; i< fileContent.size(); i++){
                String currentLine = fileContent.get(i);
                apiInvocationList.append(currentLine);
                if(i == fileContent.size() - 1){
                    targetAPI = currentLine;
                    if(targetAPI.startsWith("<java.lang.") && targetAPI.toString().contains("valueOf") && fileContent.size() - 2 >=0){
                        targetAPI =  fileContent.get(fileContent.size() - 2);
                    }
                }
            }

            if(res.containsKey(targetAPI)){
                List<String> newList = res.get(targetAPI);
                if(!newList.contains(apiInvocationList.toString())){
                    newList.add(apiInvocationList.toString());
                    res.put(targetAPI, newList);
                }
            }else{
                List<String> newList = new ArrayList<>();
                newList.add(apiInvocationList.toString());
                res.put(targetAPI, newList);
            }
        }

        System.out.println("res number:" + allJavaFileNames.size());

        //output EquivalentTest
        int totalNum = 0;
        String equivalentTestsFile = "/hci/xiao/ThirdParty/run/EquivalentTestCases.txt";
        for (String key : res.keySet()){
            if(res.get(key).size() > 1){
                totalNum += res.get(key).size();
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(equivalentTestsFile, true)));
                out.println(key + "=====" +res.get(key));
                out.close();
            }
        }

        System.out.println("totalNum："+totalNum);
    }

    public static List<String> getFileList(String outputFilePath) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString().substring(0, x.getFileName().toString().lastIndexOf('.'))).collect(Collectors.toList());

        return result;
    }
}
