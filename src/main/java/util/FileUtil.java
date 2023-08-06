package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static void replaceSelected(String filePath, String replaceForm, String replaceTo) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(filePath));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();

            System.out.println(inputStr); // display the original file for debugging

            // logic to replace lines in the string (could use regex here to be generic)
            inputStr = inputStr.replace(replaceForm, replaceTo);

            // display the new file for debugging
            System.out.println("----------------------------------\n" + inputStr);

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(filePath);
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    public static List<String> getAllAPIListFromSourceFile(String absoluteFilePath) {
        List<String> res = new ArrayList<>();
        File file = new File(absoluteFilePath);
        if (!file.exists()) {
            return res;
        }

        List<String> fileContent = null;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

}
