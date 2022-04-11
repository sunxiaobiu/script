package AutoUnitTest_Refer2JUnitTestGen.processtest;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ReadCsv {

    public static void main(String[] args) throws IOException {
        String crunchifyCSVFile = "/Users/xsun0035/Desktop/UI-UnitTest/utNameAPISigCsvPath.csv";

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/UI-UnitTest/utNameAPISigCsvPath.txt", true)));

        BufferedReader crunchifyBufferReader = null;
        String crunchifyLine = "";
        HashSet<String> crunchifyAllAPIs = new HashSet<>();

        try {
            crunchifyBufferReader = new BufferedReader(new FileReader(crunchifyCSVFile));
            while ((crunchifyLine = crunchifyBufferReader.readLine()) != null) {
                List<String> line = Arrays.asList(crunchifyLine.split(",", 2));
                if (crunchifyAllAPIs.add(line.get(1))) {
                    String classFileName = line.get(0)+".class";
                    out.println(classFileName.replaceAll("\"", ""));
                } else if (!crunchifyIsNullOrEmpty(line.get(1))) {
                    crunchifyLog("--------------- Skipped line: " + crunchifyLine);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (crunchifyBufferReader != null) {
                try {
                    crunchifyBufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out.close();
        }
    }

    public static boolean crunchifyIsNullOrEmpty(String crunchifyString) {
        if (crunchifyString != null && !crunchifyString.trim().isEmpty())
            return false;
        return true;
    }

    private static void crunchifyLog(String s) {
        System.out.println(s);
    }
}
