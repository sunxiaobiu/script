package test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CpFile {

    public static void main(String[] args) throws IOException {
        String originalPath = args[0];
        String desPath = args[1];
        String fileNameFile = args[2];

        File files = new File(fileNameFile);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(files.toPath(), StandardCharsets.UTF_8));

        for(String apkName : fileContent){
            File source = new File(originalPath + "/" + apkName);
            File dest = new File(desPath + "/" + apkName);
            if(source.exists()){
                try {
                    FileUtils.copyFile(source, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Copy file:"+apkName);
            }

        }


    }

}
