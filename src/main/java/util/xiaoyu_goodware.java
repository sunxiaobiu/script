package util;

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

import static java.nio.file.Files.readAllLines;

public class xiaoyu_goodware {

    public static void main(String[] args) throws IOException {
        String goodware_NormalPath = "/Users/xsun0035/Desktop/obfuscation/results/goodware_Normal/";
        Set<String> goodware_Normal = getFileList(goodware_NormalPath);
        System.out.println("goodware_Normal size:"+goodware_Normal.size());

        String goodware_NopPath = "/Users/xsun0035/Desktop/obfuscation/results/goodware_Nop/";
        Set<String> goodware_Nop = getFileList(goodware_NopPath);
        System.out.println("goodware_Nop size:"+goodware_Nop.size());

        String goodware_ReflectionPath = "/Users/xsun0035/Desktop/obfuscation/results/goodware_Reflection/";
        Set<String> goodware_Reflection = getFileList(goodware_ReflectionPath);
        System.out.println("goodware_Reflection size:"+goodware_Reflection.size());

        String goodware_RenamePath = "/Users/xsun0035/Desktop/obfuscation/results/goodware_Rename/";
        Set<String> goodware_Rename = getFileList(goodware_RenamePath);
        System.out.println("goodware_Rename size:"+goodware_Rename.size());

        String goodware_ReorderPath = "/Users/xsun0035/Desktop/obfuscation/results/goodware_Reorder/";
        Set<String> goodware_Reorder = getFileList(goodware_ReorderPath);
        System.out.println("goodware_Reorder size:"+goodware_Reorder.size());

        String goodware_EncryptionPath = "/Users/xsun0035/Desktop/obfuscation/results/goodware_Encryption/";
        Set<String> goodware_Encryption = getFileList(goodware_EncryptionPath);
        System.out.println("goodware_Encryption size:"+goodware_Encryption.size());

        String goodware_AdvancedReflectionPath = "/Users/xsun0035/Desktop/obfuscation/results/goodware_AdvancedReflection/";
        Set<String> goodware_AdvancedReflection = getFileList(goodware_AdvancedReflectionPath);
        System.out.println("goodware_AdvancedReflection size:"+goodware_AdvancedReflection.size());

        Set<String> malwareSet = new HashSet<>();
        CollectionIntersection.findIntersection(malwareSet, goodware_Normal, goodware_AdvancedReflection);

        for(String s : malwareSet){
            System.out.println(s);
        }
    }

    public static Set<String> getFileList(String outputFilePath) throws IOException {
        Set<String> validFileName = new HashSet<>();

        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                File file = new File(outputFilePath+filename);
                try {
                    boolean isSuccess = false;
                    List<String> fileContent = new ArrayList<>(readAllLines(file.toPath(), StandardCharsets.UTF_8));
                    for(String s : fileContent){
                        if(s.contains("==>FINAL TIME:")){
                            isSuccess =  true;
                        }
                    }

                    if(isSuccess){
                        validFileName.add(filename.replace(".txt", ""));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        return validFileName;
    }

}
