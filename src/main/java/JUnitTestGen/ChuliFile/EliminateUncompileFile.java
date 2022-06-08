package JUnitTestGen.ChuliFile;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EliminateUncompileFile {

    public static void main(String[] args) throws IOException {
        String allFilesPath = "/hci/xiao/ToBeExecuted";
        List<String> allJavaFileNames = getFileList(allFilesPath);

        for(String fileName : allJavaFileNames){
            if(StringUtils.isBlank(fileName)){
                continue;
            }

            String filePath = "/hci/xiao/ToBeExecuted/"+fileName+".java";
            File javaFile = new File(filePath);

            List<String> content = new ArrayList<>(Files.readAllLines(javaFile.toPath(), StandardCharsets.UTF_8));
            for(String s : content){
                if(s.contains("Couldn't be decompiled")){
                    javaFile.delete();
                    continue;
                }
            }
        }


    }

    public static List<String> getFileList(String outputFilePath) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString().substring(0, x.getFileName().toString().lastIndexOf('.'))).collect(Collectors.toList());

        return result;
    }

}
