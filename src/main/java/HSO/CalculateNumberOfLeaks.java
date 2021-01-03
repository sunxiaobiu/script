package HSO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculateNumberOfLeaks {
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

        Integer numberOfLeaks = 0;
        List<String> failedApk = new ArrayList<>();

        for(String file : result) {
            int success = 0;
            int leaksInFile = 0;
            if (!file.endsWith(".txt")) {
                continue;
            }
            String filePath = outputPath + "/" + file;
            BufferedReader br = null;
            String line = "";

            br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("(Found)( [0-9]+ )(leaks)", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    numberOfLeaks += Integer.valueOf(matcher.group(2).trim());
                    leaksInFile += Integer.valueOf(matcher.group(2).trim());
                }

                if(line.contains("==>FINAL TIME")){
                    success = 1;
                }
            }

            if(success == 0){
                failedApk.add(file);
            }
            System.out.println(leaksInFile);
        }

        System.out.println("numberOfLeaks:"+numberOfLeaks);

        System.out.println("=================================");
        for(String s : failedApk){
            System.out.println(s);
        }
    }
}
