package DroidRA;

import model.IncrementalResult;
import util.TimeMillisToDate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IncrementalResultAnalysis {
    public static void main(String[] args) {
        String originOutputPath = args[0];
        String outputPath = args[1];

        Stream<Path> originPaths = null;
        try {
            originPaths = Files.walk(Paths.get(originOutputPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> result = originPaths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        List<Long> originIncrementalResults = new ArrayList<>();
        List<Long> incrementalResults = new ArrayList<>();

        for(String file : result) {
            if (!file.endsWith(".txt")) {
                continue;
            }

            String filePath = outputPath + "/" + file;
            BufferedReader br = null;
            String line = "";

            //originOutput
            String originFilePath = originOutputPath + "/" + file;
            BufferedReader originBr = null;
            String originLine = "";

            try {
                IncrementalResult incrementalResult = new IncrementalResult();
                IncrementalResult originIncrementalResult = new IncrementalResult();

                br = new BufferedReader(new FileReader(filePath));

                while ((line = br.readLine()) != null) {

                    if(line.contains("==>afterDummyMain TIME:")){
                        incrementalResult.setSha256(file.replaceAll(".txt", ""));
                        incrementalResult.setAfterDummyMain(TimeMillisToDate.convert(Long.parseLong(line.substring(23))));
                    }

                    if(line.contains("==>afterRA TIME:")){
                        incrementalResult.setAfterRA(TimeMillisToDate.convert(Long.parseLong(line.substring(16))));
                    }
                }

                //original data processing
                originBr = new BufferedReader(new FileReader(originFilePath));

                while ((originLine = originBr.readLine()) != null) {

                    if(originLine.contains("==>afterDummyMain TIME:")){
                        originIncrementalResult.setSha256(file.replaceAll(".txt", ""));
                        originIncrementalResult.setAfterDummyMain(TimeMillisToDate.convert(Long.parseLong(originLine.substring(23))));
                    }

                    if(originLine.contains("==>afterRA TIME:")){
                        originIncrementalResult.setAfterRA(TimeMillisToDate.convert(Long.parseLong(originLine.substring(16))));
                    }
                }

                //in milliseconds
                if(null != originIncrementalResult.getAfterDummyMain() && null != originIncrementalResult.getAfterRA()
                        && null != incrementalResult.getAfterDummyMain() && null != incrementalResult.getAfterRA()){
                    long diff = incrementalResult.getAfterRA().getTime() - incrementalResult.getAfterDummyMain().getTime();
                    long originDiff = originIncrementalResult.getAfterRA().getTime() - originIncrementalResult.getAfterDummyMain().getTime();

                    long diffSeconds = diff / 1000 % 60;
                    long originDiffSeconds = originDiff / 1000 % 60;

                    originIncrementalResults.add(originDiff);
                    incrementalResults.add(diff);

                    //System.out.println(incrementalResult.getSha256() + ":" + Long.toString(originDiffSeconds - diffSeconds));
                }
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("originIncrementalResults:");
        originIncrementalResults.stream().forEach(originIncrementalResult ->{
            System.out.println(originIncrementalResult.toString() +  ",");
        });

        System.out.println("incrementalResults:");
        incrementalResults.stream().forEach(incrementalResult ->{
            System.out.println(incrementalResult.toString() +  ",");
        });

    }

}
