package DroidRA;

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

public class CalculateDynamicFragmentNum {

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

        int fragmentNum = 0;
        int appNumbers = 0;

        for(String file : result) {
            boolean appFragmentFlag = false;
            if (!file.endsWith(".txt")) {
                continue;
            }
            String filePath = outputPath + "/" + file;
            BufferedReader br = null;
            String line = "";

            br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {

                if(line.contains("This App Has Fragment, Fragment size=")){
                    //fragmentNum ++;
                    //System.out.println(test);
                    String fragmentSize = line.substring(37);
                    if(!fragmentSize.equals("0")){
                        appFragmentFlag = true;
                    }
                }
            }
            if(appFragmentFlag){
                appNumbers ++;
            }
        }

        System.out.println("appNumbers:"+appNumbers);
    }
}
