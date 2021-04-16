package FieldFlowDroid;

import HSO.RQ1.TriggerNum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetSensorAPI {

    public static void main(String[] args) throws IOException {
        String logFilePath = args[0];

        List<String> txtFileNames = new ArrayList<>();

        TriggerNum.getFileList(logFilePath, txtFileNames);

        Set<String> dupliatedSensorAPIs = new HashSet<>();

        txtFileNames.forEach(txtFileName -> {
            try {
                File filePath = new File(logFilePath + "/" + txtFileName + ".txt");
                BufferedReader br = null;
                String line = "";
                br = new BufferedReader(new FileReader(filePath));

                //output all field-related leaks
                while ((line = br.readLine()) != null) {
                    if (line.contains("<android.hardware.Sensor")) {
                        dupliatedSensorAPIs.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        for (String api : dupliatedSensorAPIs) {
            System.out.println(api);
        }
    }

}
