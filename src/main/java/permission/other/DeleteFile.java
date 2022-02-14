package permission.other;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeleteFile {

    public static void main(String[] args) throws IOException {
        File needDeleteFile = new File("/Users/xsun0035/Desktop/needDeleteFileName.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(needDeleteFile.toPath(), StandardCharsets.UTF_8));

        Stream<Path> paths = Files.walk(Paths.get("/Users/xsun0035/workspace/monash/PermissionCI/app/src/androidTest/java/com/edu/permission/compatibilityissue"));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());


        for (String s : result) {
            for (String needDeteleFileName : fileContent) {
                if (s.contains(needDeteleFileName)) {
                    File toDelFile = new File(s);
                    if (toDelFile.exists()) {
                        toDelFile.delete();
                    }
                }
            }

        }

    }
}
