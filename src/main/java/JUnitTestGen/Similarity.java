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
        HashMap<String, String> res = new HashMap<>();

        Set<String> apis = new HashSet<>();

        List<String> fileList = getFileList(allFilesPath);
        for (String fileName : fileList) {
            File file = new File(allFilesPath + "/" + fileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            StringBuilder apiInvocationList = new StringBuilder();
            for(int i=0; i< fileContent.size(); i++){
                String currentLine = fileContent.get(i);
                apiInvocationList.append(currentLine);
                if(i == fileContent.size() - 1){
                    apis.add(currentLine);
                }
            }
            res.putIfAbsent(apiInvocationList.toString(), fileName);
        }

        System.out.println("test number:"+fileList.size());
        System.out.println("distinct test number:"+res.size());
        System.out.println("distinct API number:"+apis.size());

        //output API invocations in minContext for simplicity analysis
        String distinctTestCasesFile = "/hci/xiaoyu/DistinctTestCases.txt";
        for (String key : res.keySet()){
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(distinctTestCasesFile, true)));
            out.println(res.get(key).replace(".txt", ""));
            out.close();
        }
    }

    public static List<String> getFileList(String outputFilePath) throws IOException {
        List<String> txtFileNames = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });

        return txtFileNames;
    }

}
