package AutoUnitTest_Refer2JUnitTestGen.result;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ExcutableRate {

    public static void main(String[] args) throws IOException {
        //   "/Users/xsun0035/Desktop/UnitTest-results/excutable_rate/allSootOutput.txt"
        String sootOutputFilePath = args[0];
        //   "/Users/xsun0035/Desktop/UnitTest-results/excutable_rate/sdk29.txt"
        String sdk29File = args[1];

        File sdk29FilePath = new File(sdk29File);
        List<String> sdk29FileContent = new ArrayList<>(Files.readAllLines(sdk29FilePath.toPath(), StandardCharsets.UTF_8));

        File file = new File(sootOutputFilePath);
        List<String> sootOutputFileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        FileWriter resWriter = new FileWriter("/Users/xsun0035/Desktop/UnitTest-results/excutable_rate/res.txt");

        for(String fileName : sootOutputFileContent){
            for(String sdk29FileName : sdk29FileContent){
                if(fileName.contains(sdk29FileName)){
                    resWriter.write(fileName.replaceAll(".class", ".java"));
                    resWriter.write("\n");
                }
            }
        }

        resWriter.close();


    }
}
