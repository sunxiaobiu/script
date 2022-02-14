package permission.other;

import org.apache.commons.lang3.StringUtils;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagerServiceMap {

    public static HashMap<String, String> getConstantKey() throws IOException {
        File file = new File("/Users/xsun0035/Desktop/managerService.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        HashMap<String, String> res = new HashMap<>();

        StringBuilder annotation = new StringBuilder();
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            if(currentLine.startsWith("    public ")){
                String value = Regex.getSubUtilSimple(currentLine, "([A-Z]+\\_[A-Z]+.* =)").replace(" =", "");
                if(value.equals("VIBRATOR_SERVICE")){
                    res.put("android.os.VibratorManager", "VIBRATOR_SERVICE");
                    annotation = new StringBuilder();
                }else{
                    res.put(Regex.getSubUtilSimple(annotation.toString(), "(android.*?Manager)"), value);
                    annotation = new StringBuilder();
                }
            }else{
                annotation.append(currentLine);
            }
        }

        return res;
    }

    public static HashMap<String, String> getConstantKeyForOtherService() throws IOException {
        File file = new File("/Users/xsun0035/Desktop/managerService.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        HashMap<String, String> res = new HashMap<>();

        StringBuilder annotation = new StringBuilder();
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            if(currentLine.startsWith("    public ")){
                String value = Regex.getSubUtilSimple(currentLine, "([A-Z]+\\_[A-Z]+.* =)").replace(" =", "");
                if(value.equals("VIBRATOR_SERVICE")){
                    res.put("android.os.VibratorManager", "VIBRATOR_SERVICE");
                    annotation = new StringBuilder();
                }else{
                    String key = Regex.getSubUtilSimple(annotation.toString(), "(@link android.+?\\})").replace("@link ", "").replace("}", "");
                    res.put(key, value);
                    annotation = new StringBuilder();
                }
            }else{
                annotation.append(currentLine);
            }
        }

        res.put("TelephonyRegistryManager", "TELEPHONY_REGISTRY_SERVICE");

        return res;
    }

    public static HashMap<String, String> getConstantValue() throws IOException {
        File file = new File("/Users/xsun0035/Desktop/managerService.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        HashMap<String, String> res = new HashMap<>();

        StringBuilder annotation = new StringBuilder();
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            if(currentLine.startsWith("    public ")){
                String value = Regex.getSubUtilSimple(currentLine, "(\".*\")").replaceAll("\"", "");
                if(value.equals("VIBRATOR_SERVICE")){
                    res.put("android.os.VibratorManager", "vibrator");
                    annotation = new StringBuilder();
                }else{
                    res.put(Regex.getSubUtilSimple(annotation.toString(), "(android.*?Manager)"), value);
                    annotation = new StringBuilder();
                }
            }else{
                annotation.append(currentLine);
            }
        }

        return res;
    }
}

