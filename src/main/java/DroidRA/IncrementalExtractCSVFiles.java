package DroidRA;

import model.ApkInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IncrementalExtractCSVFiles {

    public static void main(String[] args) {
        String csvFilePath = args[0];
        try {
            Stream<Path> paths = Files.walk(Paths.get(csvFilePath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            int apkNums = 0;
            Map<String, List<ApkInfo>> apkInfoMap = new HashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Map<String, String> versionMap = new HashMap<>();

            //get 1500 apks and convert to Map<pkgName, List<ApkInfo>>
            for(String file : result){
                String csvFileString = csvFilePath + "/" + file;

                String line = "";
                String cvsSplitBy = ",";

                BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFileString));
                int lines = 0;
                while (bufferedReader.readLine() != null) lines++;
                if(lines <= 2){
                    continue;
                }

                BufferedReader br = new BufferedReader(new FileReader(csvFileString));
                while ((line = br.readLine()) != null && apkNums < 3000) {
                    // use comma as separator
                    String[] apkLineage = line.split(cvsSplitBy);

                    if(apkLineage[0].equals("sha256")){
                        continue;
                    }

                    ApkInfo apkInfo = new ApkInfo();
                    apkInfo.setCreateTime(formatter.parse(apkLineage[3]));
                    apkInfo.setSha256(apkLineage[0]);

                    String pkgName = apkLineage[5];
                    if(null == apkInfoMap.get(pkgName)){
                        List<ApkInfo> apkInfos = new ArrayList<>();
                        apkInfos.add(apkInfo);
                        apkInfoMap.put(pkgName, apkInfos);
                    }else {
                        apkInfoMap.get(pkgName).add(apkInfo);
                    }

                    apkNums ++;
                }
            }

            //sort by time
            apkInfoMap.forEach((apkInfokey, apkInfoVlaue) ->{
                Collections.sort(apkInfoVlaue);

                //generate parameters of incremental analysis: <k,v>, k refers to apk sha256, v refers to last apk version's sha256
                for(int i = 0; i< apkInfoVlaue.size() - 1; i++){
                    versionMap.put(apkInfoVlaue.get(i).getSha256(), apkInfoVlaue.get(i+1).getSha256());
                }
            });

            versionMap.forEach((k,v) ->{
                System.out.println("/mnt/fit-Knowledgezoo-vault/vault/apks/" +k + ".apk" + " " + "/mnt/fit-Knowledgezoo-vault/vault/apks/"+ v + ".apk");
            });

            System.out.println("total num:"+apkNums);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
