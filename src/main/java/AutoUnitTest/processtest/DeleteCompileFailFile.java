package AutoUnitTest.processtest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DeleteCompileFailFile {

    public static void main(String[] args) throws IOException {
        String needDeleteFilePath = args[0];

        File file = new File(needDeleteFilePath);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            //app/src/androidTest/java/
            File toDelFile = new File("/Users/xsun0035/workspace/monash/BasicUnitAndroidTest/app/src/androidTest/java/"+fileContent.get(i));
            System.out.println(toDelFile.getName());
            if(toDelFile.exists()){
                toDelFile.delete();
            }
        }

    }

}
