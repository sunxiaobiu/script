package DroidRA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculateThreadFailed {

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

        int countingThreadPoolExecutorNum = 0;

        for(String file : result) {
            boolean countingThreadPoolExecutorFlag = false;
            if (!file.endsWith(".txt")) {
                continue;
            }
            String filePath = outputPath + "/" + file;
            BufferedReader br = null;
            String line = "";

            br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {
                if(line.contains("CountingThreadPoolExecutor:64 - Worker thread execution failed")){
                    countingThreadPoolExecutorFlag = true;
                }
            }
            if(countingThreadPoolExecutorFlag){
                System.out.println(file);
                countingThreadPoolExecutorNum ++;
            }

        }

        System.out.println("countingThreadPoolExecutorNum:"+countingThreadPoolExecutorNum);
    }
}
