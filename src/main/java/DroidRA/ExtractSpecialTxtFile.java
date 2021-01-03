package DroidRA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtractSpecialTxtFile {
    public static void main(String[] args) {
        try {
            String outputFilePath = args[0];

            Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            AtomicInteger apkProblemNum = new AtomicInteger();
            AtomicInteger apkTooLargeProblem = new AtomicInteger();

            List<String> jsonFileNames = new ArrayList<>();
            List<String> txtFileNames = new ArrayList<>();

            result.stream().forEach(filename ->{
                if(filename.endsWith(".json")){
                    jsonFileNames.add(filename.replace(".json", ""));
                }
                if(filename.endsWith(".txt")){
                    txtFileNames.add(filename.replace(".txt", ""));
                }
            });

            txtFileNames.forEach(txtFileName ->{
                try {
                    if(!jsonFileNames.contains(txtFileName)){

                        File filePath = new File(outputFilePath + "/" + txtFileName+".txt");

                        BufferedReader br = null;
                        String line = "";

                        br = new BufferedReader(new FileReader(filePath));

                        boolean apkProblemNumFlag = false;
                        while ((line = br.readLine()) != null) {

                            if(line.contains("Exception: org.jf.dexlib2.DexFileFactory$UnsupportedFileTypeException:")){
                                apkProblemNumFlag = true;
                            }
                        }

                        if(apkProblemNumFlag){
                            apkProblemNum.getAndIncrement();
                        }else {
                            apkTooLargeProblem.getAndIncrement();
                            System.out.println(filePath);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("apkProblemNum:"+apkProblemNum+";"+"apkTooLargeProblem:"+apkTooLargeProblem);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
