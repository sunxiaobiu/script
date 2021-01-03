package HSO.fuzzing;

import HSO.RQ1.TriggerNum;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CopyFiles {

    public static void main(String[] args) throws IOException {
        String outputFilePath = args[0];

        ArrayList<String> fileDirectoryList = getFiles(outputFilePath);
        for(String fileDirectory : fileDirectoryList){
            copyFileOutput(fileDirectory);
        }
    }

    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                //System.out.println("文件夹：" + tempList[i]);
                files.add(tempList[i].toString());
            }
        }
        return files;
    }

    private static void copyFileOutput(String originFilePath) throws IOException {
        File originFile = new File(originFilePath + "/" + "covids");

        if(!originFile.exists()){
            return;
        }

        File desDirectory =new File("/Users/xsun0035/Desktop/ella_after"+ "/" + TriggerNum.getSubUtilSimple(originFilePath, "([0-9a-zA-Z_.]+.apk)"));
        if(!desDirectory.exists()) {
            desDirectory.mkdirs();
        }

        File desFile=new File(desDirectory + "/" + "covids");

//        try {
//            if (originFile.renameTo(desFile)) {
//                System.out.println("文件移动成功！目标路径：{"+desFile.getAbsolutePath()+"}");
//            } else {
//                System.out.println("文件移动失败！起始路径：{"+originFile.getAbsolutePath()+"}");
//            }
//        }catch(Exception e) {
//            System.out.println("文件移动出现异常！起始路径：{"+originFile.getAbsolutePath()+"}");
//        }
        FileUtils.copyFile(originFile, desFile);

    }

}
