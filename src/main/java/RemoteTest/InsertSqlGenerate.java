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

    public static void main(String[] args) throws IOException {
        String testCase = args[0];
        File testCaseFile = new File(testCase);
        List<String> testCases = new ArrayList<>(Files.readAllLines(testCaseFile.toPath(), StandardCharsets.UTF_8));

        for(String testCaseName : testCases){
            System.out.println("INSERT INTO `RemoteTest`.`test_case` (`unique_id`, `name`, `deleted`, `create_time`) VALUES ('" + testCaseName +"', '"+testCaseName+"', '0', NOW());");
            //logger.info("INSERT INTO `RemoteTest`.`test_case` (`unique_id`, `name`, `deleted`, `create_time`) VALUES ('{}', '{}}', '0', NOW());", testCaseName, testCaseName);
        }
    }
}
