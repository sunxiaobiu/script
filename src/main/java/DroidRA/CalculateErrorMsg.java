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

public class CalculateErrorMsg {

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

        int insufficientMemoryNum = 0;
        int countingThreadPoolExecutorNum = 0;
        int classResolutionFailedExceptionNum = 0;

        for(String file : result) {
            boolean insufficientMemoryFlag = false;
            boolean countingThreadPoolExecutorFlag = false;
            boolean classResolutionFailedExceptionFlag = false;
            if (!file.endsWith(".txt")) {
                continue;
            }
            String filePath = outputPath + "/" + file;
            BufferedReader br = null;
            String line = "";

            br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {

                if(line.contains("There is insufficient memory for the Java Runtime Environment to continue")){
                    insufficientMemoryFlag = true;
                }
                if(line.contains("heros.solver.CountingThreadPoolExecutor")){
                    countingThreadPoolExecutorFlag = true;
                }
                if(line.contains("ClassResolutionFailedException")){
                    classResolutionFailedExceptionFlag = true;
                }
            }
            if(insufficientMemoryFlag){
                insufficientMemoryNum ++;
            }
            if(countingThreadPoolExecutorFlag){
                countingThreadPoolExecutorNum ++;
            }
            if(classResolutionFailedExceptionFlag){
                classResolutionFailedExceptionNum ++;
            }
        }

        System.out.println("insufficientMemoryNum:"+insufficientMemoryNum+"; countingThreadPoolExecutorNum："+countingThreadPoolExecutorNum+"; classResolutionFailedExceptionNum："+classResolutionFailedExceptionNum);
    }
}
