package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExtractCSVData {

    public static void run(String csvFile){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int apkNums = 0;
        List<String> apks = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(csvFile));

            while ((line = br.readLine()) != null && apkNums < 300) {

                // use comma as separator
                String[] apkLineage = line.split(cvsSplitBy);

                if(apkLineage[0].equals("sha256")){
                    continue;
                }
                Date date = formatter.parse(apkLineage[3]);
                Date standardTime = formatter.parse("2013-01-01 00:00:00");
                if(date.after(standardTime)){
                    //apks.add(apkLineage[0]);
                    System.out.println("sha256= " + apkLineage[0] + " , dex_date=" + apkLineage[3] + "");
                    apkNums ++;
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
}
