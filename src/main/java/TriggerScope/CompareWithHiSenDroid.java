package TriggerScope;


import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CompareWithHiSenDroid {
    public static void main(String[] args) throws IOException {
        File triggerScopeFile = new File("/Users/xsun0035/Desktop/triggerscope.txt");
        File hisendroidFile = new File("/Users/xsun0035/Desktop/HiSenDroid.txt");

        List<String> triggerScopeFileContent = new ArrayList<>(Files.readAllLines(triggerScopeFile.toPath(), StandardCharsets.UTF_8));
        List<String> hisendroidFileContent = new ArrayList<>(Files.readAllLines(hisendroidFile.toPath(), StandardCharsets.UTF_8));

//        triggerScopeFileContent.removeAll(hisendroidFileContent);
//        for(String s : triggerScopeFileContent){
//            System.out.println(s);
//        }
//
//        triggerScopeFileContent.retainAll(hisendroidFileContent);
//        for (String s : hisendroidFileContent) {
//            System.out.println(s);
//        }

        List<String> intersectionList = (List<String>) CollectionUtils.intersection(triggerScopeFileContent, hisendroidFileContent);
        for (String s : intersectionList) {
            System.out.println(s);
        }
    }
}
