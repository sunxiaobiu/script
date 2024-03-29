package RemoteTest.compareCiD;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CiDTestCase {

    public static void main(String[] args) throws IOException {
        String crunchifyCSVFile = "/Users/xsun0035/Desktop/utNameAPISigCsvPath.csv";

        String cidFile = "/Users/xsun0035/Desktop/CiD.txt";
        File file = new File(cidFile);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        String crunchifyLine = "";

        try {
            for(String ciString : fileContent){
                BufferedReader crunchifyBufferReader = new BufferedReader(new FileReader(crunchifyCSVFile));
                while ((crunchifyLine = crunchifyBufferReader.readLine()) != null) {
                    List<String> line = Arrays.asList(crunchifyLine.split(",", 2));
                    if(ciString.equals(line.get(1).replace("\"", ""))){
                        System.out.println(line.get(0).replaceAll("\"", ""));
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
