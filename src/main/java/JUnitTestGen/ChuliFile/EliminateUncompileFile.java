package JUnitTestGen.ChuliFile;

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
        String filePath = args[0];

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
