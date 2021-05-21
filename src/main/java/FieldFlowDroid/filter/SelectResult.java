package FieldFlowDroid.filter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectResult {

    public static void main(String[] args) throws IOException {
        String outputPath = args[0];
        Stream<Path> originPaths = null;
        try {
            originPaths = Files.walk(Paths.get(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> result = originPaths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        Integer leakAPKNum = 0;
        List<String> needDelete = new ArrayList<>();

        for(String file : result) {
            int containsLeak = 0;
            if (!file.endsWith(".txt")) {
                continue;
            }
            String filePath = outputPath + "/" + file;
            BufferedReader br = null;
            String line = "";

            br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {

                if(line.contains("[SOURCE]") && line.contains("<android.hardware.")){
                    containsLeak = 1;
                }
            }

            if(containsLeak == 0){
                needDelete.add(file);
            }else if(containsLeak == 1){
                leakAPKNum ++;
                System.out.println(file);
            }
        }

        System.out.println("leakAPKNum:"+leakAPKNum);



    }
}
