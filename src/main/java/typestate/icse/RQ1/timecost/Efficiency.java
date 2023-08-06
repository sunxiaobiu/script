package typestate.icse.RQ1.timecost;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Efficiency {

    public static void main(String[] args) throws IOException {
        String outputFilePath = "/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ1_TypeStateMisuse/output";

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

                    if(line.contains("==>after detectMisusePatterns TIME:")){
                        finalTime = new Date(Long.parseLong(line.substring(35)));
                    }
                }

                if(!finalTime.equals(new Date(0)) && !startTime.equals(new Date(0))){
                    String outputFileName = "/Users/xsun0035/Desktop/TypeState_ICSE2024/RQ1_TypeStateMisuse/TimeCost/efficiency.txt";
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName, true)));
                    out.println((finalTime.getTime()-startTime.getTime())/1000);
                    out.close();
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
