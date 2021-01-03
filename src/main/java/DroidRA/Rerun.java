package DroidRA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rerun {

    public static void main(String[] args) {
        try {
            String outputFilePath = args[0];

            Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            List<String> jsonFileNames = new ArrayList<>();
            List<String> txtFileNames = new ArrayList<>();
            List<String> needRerunFileNames = new ArrayList<>();

            result.stream().forEach(filename ->{
                if(filename.endsWith(".json")){
                    jsonFileNames.add(filename.replace(".json", ""));
                }
                if(filename.endsWith(".txt")){
                    txtFileNames.add(filename.replace(".txt", ""));
                }
            });

            txtFileNames.forEach(txtFileName ->{
                if(!jsonFileNames.contains(txtFileName)){
                    String needRerunFileName = "/mnt/fit-Knowledgezoo-vault/vault/apks/"+txtFileName+".apk";
                    System.out.println(needRerunFileName);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
