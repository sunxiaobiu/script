package permission.other;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculateTotalTestNum {

    public static void main(String[] args) throws IOException {
        String outputFilePath1 = "/Users/xsun0035/workspace/monash/PermissionCI/app/src/androidTest/java/com/edu/permission/compatibilityissue";
        String outputFilePath2 = "/Users/xsun0035/workspace/monash/PermissionCI2/app/src/androidTest/java/com/edu/permission/permissionci2";
        String outputFilePath3 = "/Users/xsun0035/workspace/monash/PermissionCI3/app/src/androidTest/java/com/edu/permission/permissionci3";

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath1, txtFileNames);
        getFileList(outputFilePath2, txtFileNames);
        getFileList(outputFilePath3, txtFileNames);

        System.out.println(txtFileNames.size());
    }

    public static void getFileList(String resFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(resFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            txtFileNames.add(filename);
        });
    }
}
