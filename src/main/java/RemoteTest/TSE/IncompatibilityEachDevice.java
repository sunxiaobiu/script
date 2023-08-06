package RemoteTest.TSE;

import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class IncompatibilityEachDevice {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/test.txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        Set<String> signatureBasedException = new HashSet<>();
        signatureBasedException.add("NoClassDefFoundError");
        signatureBasedException.add("NoSuchMethodError");
        signatureBasedException.add("NoSuchFieldError");
        signatureBasedException.add("IllegalArgumentException");
        signatureBasedException.add("IllegalAccessError");

        HashMap<String, Integer> signatureCompatibilityIssues = new HashMap<>();
        HashMap<String, Integer> semanticCompatibilityIssues = new HashMap<>();

        for (String incompatibility : fileContent) {
            List<String> resList = Regex.getSubUtilSimpleList(incompatibility, "([a-zA-Z.]+:(\\[Meizu\\]\\[30\\];|\\[huawei\\]\\[28\\];|\\[Xiaomi\\]\\[29\\];|\\[huawei\\]\\[29\\];|\\[HONOR\\]\\[29\\];|\\[samsung\\]\\[30\\];|\\[HUAWEI\\]\\[28\\];|\\[Xiaomi\\]\\[29\\];|\\[ONEPLUS\\]\\[28\\];|\\[samsung\\]\\[26\\];|\\[samsung\\]\\[29\\];|\\[emulator\\]\\[26\\];|\\[emulator\\]\\[28\\];|\\[emulator\\]\\[29\\];|\\[emulator\\]\\[30\\];)+)");
            for(String exceptionList : resList){
                String exception = Regex.getSubUtilSimple(exceptionList, "(.[a-zA-Z]+:)").replace(":","").replace(".","");
                List<String> devices = Regex.getSubUtilSimpleList(exceptionList, "(\\[[a-zA-Z]+\\]\\[[0-9]+\\])");

                if(signatureBasedException.contains(exception) && !exception.equals("success")){
                    for(String device : devices){
                        IncrementHashMap.incrementValue(signatureCompatibilityIssues, device);
                    }
                }else if(!exception.equals("success")){
                    for(String device : devices){
                        IncrementHashMap.incrementValue(semanticCompatibilityIssues, device);
                    }
                }
            }
        }

        System.out.println("=========signatureCompatibilityIssues=======");
        Map<String, Integer> sortedResSig = IncrementHashMap.sortByValue(signatureCompatibilityIssues);
        for(String apiSig : sortedResSig.keySet()){
            System.out.println(apiSig + "----"+ sortedResSig.get(apiSig));
        }

        System.out.println("=========semanticCompatibilityIssues=======");
        Map<String, Integer> sortedResSem = IncrementHashMap.sortByValue(semanticCompatibilityIssues);
        for(String apiSig : sortedResSem.keySet()){
            System.out.println(apiSig + "----"+ sortedResSem.get(apiSig));
        }
    }
}
