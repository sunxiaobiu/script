package typestate.icse.RQ2;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractLineageApp {

    public static void main(String[] args) throws IOException, CsvValidationException {
        String strFile = "/Users/xsun0035/Desktop/sha_genre_lineage.csv";

        HashMap<String, String> minLineageApp = new HashMap<>();
        HashMap<String, String> maxLineageApp = new HashMap<>();

        HashMap<String, Integer> maxValue = new HashMap<>();

        CSVReader reader = new CSVReader(new FileReader(strFile));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            List<String> line = Arrays.asList(nextLine);
            String sha256 = line.get(0);
            String currentRank = line.get(2);
            String appName = line.get(4);
            if(currentRank.equals("1")){
                maxValue.put(appName, 1);
                minLineageApp.put(appName, sha256);
            }else{
                if(Integer.parseInt(currentRank) > maxValue.get(appName)){
                    maxValue.put(appName, Integer.parseInt(currentRank));
                    maxLineageApp.put(appName, sha256);
                }
            }
        }

        for(Map.Entry<String, String> entryValue : minLineageApp.entrySet()){
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/minLineageApp.txt", true)));
            out.println(entryValue.getKey() +";"+entryValue.getValue());
            out.close();
        }

        for(Map.Entry<String, String> entryValue : maxLineageApp.entrySet()){
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/maxLineageApp.txt", true)));
            out.println(entryValue.getKey() +";"+entryValue.getValue());
            out.close();
        }
    }


}
