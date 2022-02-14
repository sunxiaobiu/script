package permission.other;

import org.apache.commons.io.FileUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeleteDir {

    public static void main(String[] args) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get("/Users/xsun0035/workspace/monash/PermissionCI/app/src/androidTest/java/com/edu/permission/compatibilityissue"));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());


        Set<String> needDelete = new HashSet<>();
        for(String s : result){
            needDelete.add(Regex.getSubUtilSimple(s, "(.*Service\\/)").replace("Service/", "Service"));
        }

        for(String s : needDelete){
            System.out.println(s);
            FileUtils.deleteDirectory(new File(s));
        }
//        String destination = "";
//        FileUtils.deleteDirectory(new File(destination));
    }
}
