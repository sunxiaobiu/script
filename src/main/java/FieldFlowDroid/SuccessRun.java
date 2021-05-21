package FieldFlowDroid;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SuccessRun {

    public static void main(String[] args) throws IOException {
        String fileName = args[0];

        File file = new File(fileName);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        boolean successFlag= false;
        for (int i = 0; i < fileContent.size(); i++) {
            String line = fileContent.get(i);

            if (line.startsWith("==>FINAL TIME:")) {
                successFlag = true;
            }
        }

        if(!successFlag){
            System.out.println(fileName);
        }
    }
}
