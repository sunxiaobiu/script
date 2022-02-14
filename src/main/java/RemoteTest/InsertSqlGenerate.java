package RemoteTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class InsertSqlGenerate {

    private static final Logger logger = LoggerFactory.getLogger(InsertSqlGenerate.class);

    //AOSP_F_ stands for AOSP_framework_core
    public static void main(String[] args) throws IOException {
        String testCase = args[0];
        String outputFile = args[1];
        File testCaseFile = new File(testCase);
        File outputFilePath = new File(outputFile);
        List<String> testCases = new ArrayList<>(Files.readAllLines(testCaseFile.toPath(), StandardCharsets.UTF_8));
//        for(String testCaseName : testCases){
//            testCaseName = "AOSP_F_" + testCaseName;
//            System.out.println("INSERT INTO test_case (`unique_id`, `name`, `deleted`, `create_time`) VALUES ('" + testCaseName +"', '"+testCaseName+"', '0', NOW());");
//            //logger.info("INSERT INTO `RemoteTest`.`test_case` (`unique_id`, `name`, `deleted`, `create_time`) VALUES ('{}', '{}}', '0', NOW());", testCaseName, testCaseName);
//        }

        List<String> fileContent = new ArrayList<>(Files.readAllLines(outputFilePath.toPath(), StandardCharsets.UTF_8));

        for(String testCaseName : testCases){
            fileContent.add("INSERT INTO test_case (`unique_id`, `name`, `deleted`, `create_time`) VALUES ('" + testCaseName +"', '"+testCaseName+"', '0', NOW());");
            Files.write(outputFilePath.toPath(), fileContent, StandardCharsets.UTF_8);
            System.out.println("INSERT INTO test_case (`unique_id`, `name`, `deleted`, `create_time`) VALUES ('" + testCaseName +"', '"+testCaseName+"', '0', NOW());");
            //logger.info("INSERT INTO `RemoteTest`.`test_case` (`unique_id`, `name`, `deleted`, `create_time`) VALUES ('{}', '{}}', '0', NOW());", testCaseName, testCaseName);
        }
    }
}
