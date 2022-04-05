package JUnitTestGen.ChuliFile;

import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GenerateToBeDeleteFile {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/JUnitTestBuildLog.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for(String str : fileContent){
            if(str.startsWith("/Users/xsun0035/workspace/monash/ASE_JUnitTestGen/app/src/androidTest/java/")){
                String tobeDeletedFile  = Regex.getSubUtilSimple(str, "(/Users/xsun0035/workspace/monash/ASE_JUnitTestGen/app/src/androidTest/java/.*Test.java)");
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/ToDeleteTests.txt", true)));
                out.println(tobeDeletedFile);
                out.close();
            }
        }
    }
}
