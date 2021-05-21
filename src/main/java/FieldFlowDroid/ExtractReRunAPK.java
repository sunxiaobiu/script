package FieldFlowDroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtractReRunAPK {

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

        Integer successNum = 0;
        List<String> failedApk = new ArrayList<>();

        for(String file : result) {
            int finalSignal = 0;
            int containsLeak = 0;
            if (!file.endsWith(".txt")) {
                continue;
            }
            String filePath = outputPath + "/" + file;
            BufferedReader br = null;
            String line = "";

            br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {

                if(line.contains("==>FINAL TIME")){
                    finalSignal = 1;
                }
                if(line.equals("------------------------------Data Leaks----------------------------------")){
                    containsLeak = 1;
                }
            }

            if(finalSignal == 0 && containsLeak == 0){
                failedApk.add(file);
            }else {
                successNum ++;
            }
        }

        System.out.println("successNum:"+successNum);

        System.out.println("==============failedApk size==================="+ failedApk.size());
//        for(String s : failedApk){
//            File failedFile = new File(outputPath + "/" + s);
//            failedFile.delete();
//            System.out.println("delete:"+s);
//        }
    }
}
