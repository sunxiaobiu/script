package AutoUnitTest_Refer2JUnitTestGen.result;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultLogNumber {

    public static void main(String[] args) throws IOException {
        String logFile = args[0];

        int successNum = 0;
        int failNum = 0;

        File file = new File(logFile);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        for (int i = 0; i < fileContent.size(); i++) {
            String successTestCase = getSubUtilSimple(fileContent.get(i), "(TestCase_.*:.)");
            String failTestCase = getSubUtilSimple(fileContent.get(i), "(TestCase_.*:$)");

            if (fileContent.get(i).startsWith("TestCase_") && StringUtils.isNotBlank(successTestCase)) {
                successNum ++;
            }

            if (fileContent.get(i).startsWith("TestCase_") && StringUtils.isNotBlank(failTestCase)) {
                failNum ++;
            }
        }

        System.out.println("successNum:"+successNum);
        System.out.println("failNum:"+failNum);
        System.out.println("total Num:"+ (successNum + failNum));
    }

    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
