package HSO.RQ2;

import HSO.model.HSO;
import util.ApplicationClassFilter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Performance {

    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath, txtFileNames);

        txtFileNames.forEach(txtFileName -> {
            try {

                Date startTime = new Date(0);
                Date afterInitTime = new Date(0);
                Date finalTime = new Date(0);

                File filePath = new File(outputFilePath + "/" + txtFileName + ".txt");

                BufferedReader br = null;
                String line = "";

                br = new BufferedReader(new FileReader(filePath));
                while ((line = br.readLine()) != null) {
                    if(line.contains("==>START TIME:")){
                        startTime = new Date(Long.parseLong(line.substring(14)));
                    }

                    if(line.contains("==>AFTER INIT TIME:")){
                        afterInitTime = new Date(Long.parseLong(line.substring(19)));
                    }

                    if(line.contains("==>ATFER HSB ANALYSIS TIME:")){
                        finalTime = new Date(Long.parseLong(line.substring(27)));
                    }
                }

                if(!finalTime.equals(new Date(0)) && !startTime.equals(new Date(0))){
                    System.out.println((finalTime.getTime()-startTime.getTime())/1000);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename.replace(".txt", ""));
            }
        });
    }
}
