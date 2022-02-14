package RemoteTest.RQ2;

import org.apache.commons.lang3.StringUtils;
import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CalculateExceptionType {

    public static void main(String[] args) throws IOException {
        File minExceptionTypeFile = new File("/Users/xsun0035/Desktop/ModelSpecific.txt");
        List<String> minExceptionTypes = new ArrayList<>(Files.readAllLines(minExceptionTypeFile.toPath(), StandardCharsets.UTF_8));


        Set<String> signatureBasedException = new HashSet<>();
        signatureBasedException.add("NoClassDefFoundError");
        signatureBasedException.add("NoSuchMethodError");
        signatureBasedException.add("NoSuchFieldError");
        signatureBasedException.add("IllegalArgumentException");
        signatureBasedException.add("IllegalAccessError");

        Set<String> semanticBasedException = new HashSet<>();
        semanticBasedException.add("UnsupportedOperationException");
        semanticBasedException.add("SecurityException");
        semanticBasedException.add("NoSuchElementException");
        semanticBasedException.add("NullPointerException");
        semanticBasedException.add("RuntimeException");
        semanticBasedException.add("IllegalStateException");
        semanticBasedException.add("AssertionError");
        semanticBasedException.add("ArrayIndexOutOfBoundsException");
        semanticBasedException.add("IOException");
        semanticBasedException.add("AssertionFailedError");
        semanticBasedException.add("AndroidRuntimeException");
        semanticBasedException.add("ComparisonFailure");


        int signatureType = 0;
        int semanticType = 0;
        HashMap<String, Integer> res = new HashMap<>();
        for (String s : minExceptionTypes) {
            s = s.replaceAll("(:=[0-9]+)", "").replaceAll("(:\\[.*\\])", "").replace(";", "");
            String testCaseName = Regex.getSubUtilSimple(s, "(.*----)");
            String exceptionType = Regex.getSubUtilSimple(s.replace(testCaseName, ""), "([^.]+(?!.*.))");
            if(exceptionType.equals("AssertionFailedError: cancelVibrate did not throw SecurityException")){
                exceptionType = "AssertionFailedError";
            }

            if(exceptionType.equals("NoSuchMethodError: No static method throwAnyException")){
                exceptionType = "NoSuchMethodError";
            }

            if(StringUtils.isBlank(exceptionType)){
                System.out.println("jj");
            }
            IncrementHashMap.incrementValue(res, exceptionType);
            if(signatureBasedException.contains(exceptionType)){
                signatureType ++;
            }
            if(semanticBasedException.contains(exceptionType)){
                semanticType ++;
            }
        }

        System.out.println("signatureType num :" +signatureType);
        System.out.println("semanticType num :" +semanticType);
        System.out.println("=======================================================================================================================");
        List<Map.Entry<String, Integer>> resSort = new ArrayList<Map.Entry<String, Integer>>(res.entrySet());
        Collections.sort(resSort, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        for (Map.Entry<String, Integer> t : resSort) {
            System.out.println(t.getKey() + ":" + t.getValue());
        }

    }
}
