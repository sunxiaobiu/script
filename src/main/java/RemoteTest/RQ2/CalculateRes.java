package RemoteTest.RQ2;

import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CalculateRes {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/CrowdTest/RQ/RQ2/compatibility_issues.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        Set<String> signatureBasedException = new HashSet<>();
        signatureBasedException.add("NoClassDefFoundError");
        signatureBasedException.add("NoSuchMethodError");
        signatureBasedException.add("NoSuchFieldError");
        signatureBasedException.add("Resources$NotFoundException");
        signatureBasedException.add("IllegalArgumentException");
        signatureBasedException.add("PackageManager$NameNotFoundException");
        signatureBasedException.add("FileNotFoundException");

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
        semanticBasedException.add("IllegalAccessError");
        semanticBasedException.add("WindowManager$BadTokenException");
        semanticBasedException.add("ComparisonFailure");

        HashMap<String, Integer> res = new HashMap<>();
        HashMap<String, String> apiException = new HashMap<>();

        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String API = Regex.getSubUtilSimple(currentLine, "(.*---)");
            String exceptionStr = Regex.getSubUtilSimple(currentLine, "(\\[.*\\])").replace("[", "").replace("]", "");
            List<String> exceptionList = Arrays.asList(exceptionStr.split(","));

            boolean isSignatureBasedException = false;
            boolean isSemanticBasedException = false;

            for (String exceptionItem : exceptionList) {
                String exceptionNameStr = Regex.getSubUtilSimple(exceptionItem, "([^.]+(?!.*.))");

                if (signatureBasedException.contains(exceptionNameStr)) {
                    isSignatureBasedException = true;
                }

                if (semanticBasedException.contains(exceptionNameStr)) {
                    isSemanticBasedException = true;
                }
            }

            if (isSignatureBasedException && !isSemanticBasedException) {
                IncrementHashMap.incrementValue(res, "SignatureBasedException");
            }
            if (isSemanticBasedException && !isSignatureBasedException) {
                IncrementHashMap.incrementValue(res, "SemanticBasedException");
            }

            if (isSemanticBasedException && isSignatureBasedException) {
                IncrementHashMap.incrementValue(res, "Both");
            }

        }

        for(String s : res.keySet()){
            System.out.println(s + ":" +res.get(s));
        }
    }
}
