package DroidRA;

import csv.ExtractCSVData;
import org.apache.commons.lang3.StringUtils;
import util.LoadApkWithSoot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public class ExtractCSVFiles {
    public static void main(String[] args) {
        String csvFilePath = args[0];
        String standardTimeString = args[1];
        String androidJar = args[2];

        try {
            Stream<Path> paths = Files.walk(Paths.get(csvFilePath));

            List<String> result = paths.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            int apkNums = 0;

            for(String file : result){
                String csvFileString = csvFilePath + "/" + file;

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ",";
                List<String> apks = new ArrayList<>();

                try {
                    br = new BufferedReader(new FileReader(csvFileString));

                    while ((line = br.readLine()) != null && apkNums < 1000) {

                        // use comma as separator
                        String[] apkLineage = line.split(cvsSplitBy);

                        if(apkLineage[0].equals("sha256")){
                            continue;
                        }
                        Date date = formatter.parse(apkLineage[3]);
                        Date standardTime = formatter.parse(standardTimeString);
                        if(date.after(standardTime)){
                            //apks.add(apkLineage[0]);
                            //String apkPath = "/mnt/fit-Knowledgezoo-vault/vault/apks/" + apkLineage[0] + ".apk" + " " + apkLineage[5] + " " + apkLineage[3];
                            String apkPath = "/mnt/fit-Knowledgezoo-vault/vault/apks/" + apkLineage[0] + ".apk";
                            System.out.println(apkPath);

                            FileReader reader = new FileReader(apkPath);
                            ZipFile zf = null;
                            try {
                                zf = new ZipFile(apkPath);
                            } catch (IOException e) {
                                continue;
                            }
                            if(null != reader && null != zf){
                                Boolean hasFragment = LoadApkWithSoot.run(apkPath, androidJar);

                                if(hasFragment){
                                    System.out.println("fragment:"+apkPath);
                                    apkNums ++;
                                }
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("total apk num = "+apkNums);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
