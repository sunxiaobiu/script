package DroidRA.pattern;

import HSO.RQ1.TriggerNum;
import HSO.model.HSO;
import org.apache.commons.collections4.CollectionUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionPattern {

    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];

        List<String> txtFileNames = new ArrayList<>();

        getFileList(outputFilePath, txtFileNames);

        HashMap<String, Integer> patternHashMap = new HashMap<>();

        txtFileNames.forEach(txtFileName -> {
            try {

                File filePath = new File(outputFilePath + "/" + txtFileName + ".txt");

                BufferedReader br = null;
                String line = "";

                br = new BufferedReader(new FileReader(filePath));

                HSO hso = new HSO();
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("[")) {
                        String line1 = line.replace("[", "");
                        String patternString = line1.replace("]", "");
                        List<String> patternList = Arrays.asList(patternString.split(">,"));

                        StringBuilder res = new StringBuilder("");
                        for(String pattern : patternList){
                            String tmp = TriggerNum.getSubUtilSimple(pattern, "( [a-zA-Z]+\\()").replace("(", "->");
                            //String tmp = pattern.replace("(", "->");
                            res.append(tmp);
                        }

                        int num = getPatternCount(res.toString(), "->");
                        if(num > 2){
                            TriggerNum.put2Map(res.toString(), patternHashMap);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //sort
        List<Map.Entry<String, Integer>> list2 = new ArrayList<Map.Entry<String, Integer>>(patternHashMap.entrySet());
        Collections.sort(list2, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        for (Map.Entry<String, Integer> t : list2) {
            System.out.println(t.getKey() + ":" + t.getValue());
        }

    }

    public static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename.replace(".txt", ""));
            }
        });
    }

    public static int getPatternCount(String soap, String rgex){
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher matcher = pattern.matcher(soap);
        int count = 0;
        while (matcher.find())
            count++;

        return count;
    }

}
