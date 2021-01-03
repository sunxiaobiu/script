package DroidRA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BothSuccessApks {

    public static void main(String[] args) {
        try {
            //get origin output Files
            String originOutputFilePath = args[0];

            Stream<Path> paths = Files.walk(Paths.get(originOutputFilePath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

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

            //get optimized output Files
            String outputFilePath = args[1];

            Stream<Path> paths1 = Files.walk(Paths.get(outputFilePath));

            List<String> result1 = paths1.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            List<String> jsonFileNames1 = new ArrayList<>();
            List<String> txtFileNames1 = new ArrayList<>();

            result1.stream().forEach(filename ->{
                if(filename.endsWith(".json")){
                    jsonFileNames1.add(filename.replace(".json", ""));
                }
                if(filename.endsWith(".txt")){
                    txtFileNames1.add(filename.replace(".txt", ""));
                }
            });

            AtomicInteger successApkNumber = new AtomicInteger();
            jsonFileNames.forEach(jsonFileName->{
                if(jsonFileNames1.contains(jsonFileName)){
                    successApkNumber.getAndIncrement();
                    String successFileName = jsonFileName+".json";
                    System.out.println(successFileName);
                }
            });

            System.out.println("BothSuccessApkNumber:"+successApkNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
