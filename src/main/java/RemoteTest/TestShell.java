package RemoteTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestShell {

    public static String run(String cmds) throws IOException, InterruptedException {
//        String[] cmds = new String[]{"generateTestAPK"};
//        Process process = Runtime.getRuntime().exec(cmds, null, new File("/Users/xsun0035/workspace/monash/BasicUnitAndroidTest"));
//        int exitCode = process.waitFor();
//        System.out.println("===========exitCode==========="+exitCode);


        List<String> commands = Arrays.asList(cmds.split(" "));

        // execute the command
        SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands, "qtfrmszSXY199231", "/Users/xsun0035/workspace/monash/tinker/tinker-sample-android");
        int result = commandExecutor.executeCommand();

        // get the stdout and stderr from the command that was run
        StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
        StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();

        // print the stdout and stderr

        String res = "The numeric result of the command was: " + result + "STDOUT:" + stdout + "STDERR:" + stderr;
        return res;
//        System.out.println("The numeric result of the command was: " + result);
//        System.out.println("STDOUT:");
//        System.out.println(stdout);
//        System.out.println("STDERR:");
//        System.out.println(stderr);


    }
}
