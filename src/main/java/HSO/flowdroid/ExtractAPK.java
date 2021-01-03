package HSO.flowdroid;

import HSO.RQ1.TriggerNum;
import HSO.model.HSO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtractAPK {
    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath, txtFileNames);

        List<HSO> result = new ArrayList<>();

        /**
         * load HSO From File
         */
        TriggerNum.loadHSOFromFile(outputFilePath, txtFileNames, result);

        /**
         * filter common cases
         */
        TriggerNum.filterCommonCases(result);

        /**
         * filter data dependency
         */
        TriggerNum.filterDataDependency(result);

        /**
         * 判断分支差异
         */
        TriggerNum.branchDiff(result);

        /**
         * 判断单个branch是不是hso
         */
        TriggerNum.isBrancnHSO(result);

        /**
         * 输出含有hso的apk file name
         */
        Set<String> fileWithHSO = new HashSet<>();
        for (HSO hso : result) {
            if (hso.hasCommonCaese == true) {
                //continue;
            }
            if(!hso.isHSO){
                continue;
            }

            fileWithHSO.add(hso.file);
        }

        fileWithHSO.stream().forEach(item->{
            System.out.println(item);
        });

        System.out.println("sum:"+fileWithHSO.size());

//        /**
//         * 统计每个file含有的hso的数量，用来画RQ1的图
//         */
//
//        HashMap<String, Integer> triggerHashMap = new HashMap<>();
//        for (HSO hso : result) {
//            if (hso.hasCommonCaese == true) {
//                continue;
//            }
//            if(!hso.isHSO){
//                continue;
//            }
//
//            if (triggerHashMap.containsKey(hso.file)) {
//                triggerHashMap.put(hso.file, triggerHashMap.get(hso.file) + 1);
//            } else {
//                triggerHashMap.put(hso.file, 1);
//            }
//        }
//        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(triggerHashMap.entrySet());
//        for (Map.Entry<String, Integer> t : list) {
//            System.out.println(t.getKey() + ":" + t.getValue());
//        }
    }

    private static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename.replace(".txt", ""));
            }
        });
    }

}
