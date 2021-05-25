package SensorLeak.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HasSensor {

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

        Integer hasSensorUsage = 0;
        List<String> failedApk = new ArrayList<>();

        for(String file : result) {
            if (!file.endsWith(".txt")) {
                continue;
            }
            String filePath = outputPath + "/" + file;
            BufferedReader br = null;
            String line = "";

            br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {

                if(line.contains("GlobalRef.hasSensor:true")){
                    hasSensorUsage++;
                }
            }

        }

        System.out.println("hasSensorUsage Number:"+hasSensorUsage);

    }
}
