package typestate.pre_chuli;

import org.xmlpull.v1.XmlPullParserException;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectUniqueAPK {

    public static void main(String[] args) throws IOException, XmlPullParserException {
        List<String> apkFileNames = new ArrayList<>();
        getFileList("/home/xsun0035/rm46_scratch/google_benign", apkFileNames);

        int totalNum = 0;
        Set<String> apkNameList = new HashSet<>();
        Set<String> res = new HashSet<>();

        for(String apkFileName : apkFileNames){
            ProcessManifest manifest = null;
            try {
                manifest = new ProcessManifest("/home/xsun0035/rm46_scratch/google_benign/" + apkFileName);
            } catch (Exception e) {
                continue;
            }

            if(apkNameList.contains(manifest.getPackageName())){
                continue;
            }
            if(totalNum >= 10000){
                break;
            }
            totalNum++;
            apkNameList.add(manifest.getPackageName());
            res.add(apkFileName);
        }

        for(String s : res){
            String outputFileName = "/home/xsun0035/rm46_scratch/TypeState_Goodware" + ".txt";
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName, true)));
            out.println(s);
            out.close();
        }
    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".apk")) {
                txtFileNames.add(filename);
            }
        });
    }

}
