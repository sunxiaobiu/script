package DroidRA;

import json.CompareEnum;
import json.CompareJson;
import json.DroidRAKey;
import model.CompareResult;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import util.TimeMillisToDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompareCallGraphSize {

    public static void main(String[] args) {
        String originOutputPath = args[0];
        String outputPath = args[1];

        try {
            Stream<Path> paths = Files.walk(Paths.get(originOutputPath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            for(String file : result){
                if(!file.endsWith(".txt")){
                    continue;
                }

                String originFilePath = originOutputPath + "/" + file;
                String filePath = outputPath + "/" + file;

                String originCallGraphSize = "";
                String callGraphSize = "";

                String originLine = "";
                String line = "";
                BufferedReader originBr = new BufferedReader(new FileReader(originFilePath));
                BufferedReader br = new BufferedReader(new FileReader(filePath));

                while ((originLine = originBr.readLine()) != null) {
                    if(originLine.contains("CallGraphSize=")){
                        originCallGraphSize = originLine.substring(14);
                    }
                }

                while ((line = br.readLine()) != null) {
                    if(line.contains("CallGraphSize=")){
                        callGraphSize = line.substring(14);
                    }
                }

                if(StringUtils.isNotBlank(originCallGraphSize) && StringUtils.isNotBlank(callGraphSize)){
                    int originCallGraph = Integer.parseInt(originCallGraphSize);
                    int callGraph = Integer.parseInt(callGraphSize);
                    if(originCallGraph>callGraph){
                        System.out.println(originCallGraphSize+","+callGraphSize);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
