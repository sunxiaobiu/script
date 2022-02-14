package RemoteTest.RQ2;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CalculateMinExceptionType {

    public static void main(String[] args) throws IOException {
        File minExceptionTypeFile = new File("/Users/xsun0035/Desktop/minExceptionType.txt");
        List<String> minExceptionTypes = new ArrayList<>(Files.readAllLines(minExceptionTypeFile.toPath(), StandardCharsets.UTF_8));

        for (String s : minExceptionTypes) {
            HashMap<String, Integer> res = new HashMap<>();
            String typeListStr = s.replace(Regex.getSubUtilSimple(s, "(.*--)"), "");
            List<String> typeList = Arrays.asList(typeListStr.split("#"));
            for (String type : typeList) {
                String exceptionType = Regex.getSubUtilSimple(type, "(.*:)");
                Integer number = Integer.valueOf(Regex.getSubUtilSimple(type, "([0-9]+)"));
                res.put(exceptionType, number);
            }

            //sort
            List<Map.Entry<String, Integer>> resSort = new ArrayList<Map.Entry<String, Integer>>(res.entrySet());
            Collections.sort(resSort, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (o2.getValue() - o1.getValue());
                }
            });
            System.out.println(Regex.getSubUtilSimple(s, "(.*--)") + resSort.get(resSort.size()-1));
        }

    }

    public static HashMap<String, String> get() throws IOException {
        HashMap<String, String> testCaseException = new HashMap<>();

        File minExceptionTypeFile = new File("/Users/xsun0035/Desktop/minExceptionType.txt");
        List<String> minExceptionTypes = new ArrayList<>(Files.readAllLines(minExceptionTypeFile.toPath(), StandardCharsets.UTF_8));

        for (String s : minExceptionTypes) {
            HashMap<String, Integer> res = new HashMap<>();
            String typeListStr = s.replace(Regex.getSubUtilSimple(s, "(.*--)"), "");
            List<String> typeList = Arrays.asList(typeListStr.split("#"));
            for (String type : typeList) {
                String exceptionType = Regex.getSubUtilSimple(type, "(.*:)").replace(":", "");
                Integer number = Integer.valueOf(Regex.getSubUtilSimple(type, "([0-9]+)"));
                res.put(exceptionType, number);
            }

            //sort
            List<Map.Entry<String, Integer>> resSort = new ArrayList<Map.Entry<String, Integer>>(res.entrySet());
            Collections.sort(resSort, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (o2.getValue() - o1.getValue());
                }
            });

            testCaseException.put(Regex.getSubUtilSimple(s, "(.*--)").replace("-", ""), resSort.get(resSort.size()-1).getKey());
        }

        return testCaseException;
    }
}
