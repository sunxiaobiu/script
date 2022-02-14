package RemoteTest.testrunner;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class chuliLog {

    public static void main(String[] args) throws IOException {
        String logTxt = args[0];

        File file = new File(logTxt);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            String testCaseString = Regex.getSubUtilSimple(fileContent.get(i), "(tinker.sample.android.androidtest.*;)").replace("tinker.sample.android.androidtest.", "").replace(";", "");
            System.out.println("delete from test_case where unique_id='"+testCaseString +"';");
        }
    }
}
