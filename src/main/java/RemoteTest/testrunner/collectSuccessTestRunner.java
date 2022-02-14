package RemoteTest.testrunner;

import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class collectSuccessTestRunner {

    public static void main(String[] args) throws IOException {
        String txtTestRunnerFile = args[0];
        String txtTestCaseFile = args[1];

        Set<String> txtTestRunnerRecord = new HashSet<>();
        //read txtTestRunnerFile
        File txtTestRunner = new File(txtTestRunnerFile);
        List<String> txtTestRunnerFileContent = new ArrayList<>(Files.readAllLines(txtTestRunner.toPath(), StandardCharsets.UTF_8));
        for(String testRunnerRecord : txtTestRunnerFileContent){
            txtTestRunnerRecord.add(Regex.getSubUtilSimple(testRunnerRecord, "(^.*\\.)").replace(".", ""));
        }


        //read csvTestCaseFile
        File txtTestCase = new File(txtTestCaseFile);
        List<String> txtTestCaseFileContent = new ArrayList<>(Files.readAllLines(txtTestCase.toPath(), StandardCharsets.UTF_8));
        Set<String> txtTestCaseFileContentSet = new HashSet<String>(txtTestCaseFileContent);

        txtTestCaseFileContentSet.removeAll(txtTestRunnerRecord);

        for(String s : txtTestCaseFileContentSet){
            System.out.println(s);
        }


    }
}
