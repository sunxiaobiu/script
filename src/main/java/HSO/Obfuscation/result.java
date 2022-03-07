package HSO.Obfuscation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class result {

    public static void main(String[] args) throws IOException {
        File malware_NormalFile = new File("/Users/xsun0035/Desktop/malware_Normal.txt");
        List<String> malwareNormalFileContent = new ArrayList<>(Files.readAllLines(malware_NormalFile.toPath(), StandardCharsets.UTF_8));


        File malware_EncryptionFile = new File("/Users/xsun0035/Desktop/malware_Encryption.txt");
        List<String> malwareEncryptionFileContent = new ArrayList<>(Files.readAllLines(malware_EncryptionFile.toPath(), StandardCharsets.UTF_8));

        File malware_NopFile = new File("/Users/xsun0035/Desktop/malware_Nop.txt");
        List<String> malwareNopFileContent = new ArrayList<>(Files.readAllLines(malware_NopFile.toPath(), StandardCharsets.UTF_8));

        File malware_ReflectionFile = new File("/Users/xsun0035/Desktop/malware_Reflection.txt");
        List<String> malwareReflectionFileContent = new ArrayList<>(Files.readAllLines(malware_ReflectionFile.toPath(), StandardCharsets.UTF_8));

        File malware_RenameFile = new File("/Users/xsun0035/Desktop/malware_Rename.txt");
        List<String> malwareRenameFileContent = new ArrayList<>(Files.readAllLines(malware_RenameFile.toPath(), StandardCharsets.UTF_8));

        File malware_AdvancedReflectionFile = new File("/Users/xsun0035/Desktop/malware_Rename.txt");
        List<String> malwareAdvancedReflectionContent = new ArrayList<>(Files.readAllLines(malware_AdvancedReflectionFile.toPath(), StandardCharsets.UTF_8));

        malwareNormalFileContent.removeAll(malwareReflectionFileContent);
        for(String s : malwareNormalFileContent){
            System.out.println(s);
        }

    }
}
