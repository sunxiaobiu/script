package RemoteTest.chulitest;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessJavaTestFile {

    public static void main(String[] args) throws IOException {
        String testCasesFilePath = args[0];
        List<String> classFileNames = new ArrayList<>();

        getFileList(testCasesFilePath, classFileNames);

        for (String classFileName : classFileNames) {
            //System.out.println("==============Process Java FileName==============" + classFileName);

            File file = new File(testCasesFilePath + "/" + classFileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {

//                if(fileContent.get(i).contains("InstrumentationRegistry.getTargetContext();")){
//                    fileContent.set(i, fileContent.get(i).replace("InstrumentationRegistry.getTargetContext();","GlobalRef.applicationContext;"));
//                }
//
                if(fileContent.get(i).contains("ActivityTestRule var1 = new ActivityTestRule(MyActivity.class);")){
                    System.out.println(classFileName);
                    break;
                }

                if(fileContent.get(i).contains("// $FF: Couldn't be decompiled")){
                    System.out.println(classFileName);
                    break;
                }

                if(fileContent.get(i).contains("new ActivityTestRule(Activity.class);")){
                    System.out.println(classFileName);
                    break;
                }

                if(fileContent.get(i).contains("WakeLock")){
                    System.out.println(classFileName);
                    break;
                }

                if(fileContent.get(i).contains("Looper")){
                    System.out.println(classFileName);
                    break;
                }

                if(fileContent.get(i).contains("glGetIntegerv")){
                    System.out.println(classFileName);
                    break;
                }



//                //去掉import 中的[]
//                if (fileContent.get(i).startsWith("import") && fileContent.get(i).contains("[]")) {
//                    fileContent.set(i, fileContent.get(i).replaceAll("\\[]",""));
//                }
//
//                //ShapeDrawable var3 = new ShapeDrawable;  添加()到new 语句中
//                String newString = getSubUtilSimple(fileContent.get(i), "( = new .*;)");
//                if (StringUtils.isNotBlank(newString) && !newString.contains("(") && !newString.contains(")") && !newString.contains("[") && !newString.contains("]")) {
//                    fileContent.set(i, fileContent.get(i).replaceAll(";","();"));
//                }
//
//                if(fileContent.get(i).contains("(String)null")){
//                    fileContent.set(i, fileContent.get(i).replace("(String)null","(String)\"android\""));
//                }
//
//                String runnableString = getSubUtilSimple(fileContent.get(i), "(\\(Runnable\\)var[0-9]+)");
//                if(StringUtils.isNotBlank(runnableString)){
//                    fileContent.set(i, fileContent.get(i).replace(runnableString,"(Runnable)(new Runnable() {public void run() {}})"));
//                }
//
//                String throwableString = getSubUtilSimple(fileContent.get(i), "(\\(Throwable\\)var[0-9]+)");
//                if(StringUtils.isNotBlank(throwableString)){
//                    fileContent.set(i, fileContent.get(i).replace(throwableString,"(Throwable)(new Throwable())"));
//                }
//
//                String rectString = getSubUtilSimple(fileContent.get(i), "(\\(Rect\\)null)");
//                if(StringUtils.isNotBlank(rectString)){
//                    fileContent.set(i, fileContent.get(i).replace(rectString,"(Rect)(new Rect())"));
//                }
//
//                String handlerString = getSubUtilSimple(fileContent.get(i), "(new Handler;)");
//                if(StringUtils.isNotBlank(handlerString)){
//                    fileContent.set(i, fileContent.get(i).replace(handlerString,"new Handler();"));
//                }
//
//                if(fileContent.get(i).equals("import com.example.android.testing.unittesting.basicunitandroidtest.MyActivity;")){
//                    fileContent.set(i, "import tinker.sample.android.app.MyActivity;");
//                }
//
//                if(fileContent.get(i).equals("import com.example.android.testing.unittesting.basicunitandroidtest.MyService;")){
//                    fileContent.set(i, "import tinker.sample.android.service.MyService;");
//                }
//
//                if(fileContent.get(i).contains("import android.support.design.widget.")){
//                    fileContent.set(i, fileContent.get(i).replace("import android.support.design.widget.","import com.google.android.material.tabs."));
//                }
//
//                if(fileContent.get(i).contains("import android.support.v4.view.")){
//                    fileContent.set(i, fileContent.get(i).replace("import android.support.v4.view.","import androidx.core.view."));
//                }
//
//                if(fileContent.get(i).contains("import android.support.v4.widget.")){
//                    fileContent.set(i, fileContent.get(i).replace("import android.support.v4.widget.","import androidx.slidingpanelayout.widget."));
//                }
//
//                if(fileContent.get(i).contains("import android.support.v4.content.")){
//                    fileContent.set(i, fileContent.get(i).replace("import android.support.v4.content.","import androidx.legacy.content."));
//                }
//
//                if(fileContent.get(i).contains("import com.google.android.material.tabs.")){
//                    fileContent.set(i, fileContent.get(i).replace("import com.google.android.material.tabs.","import com.google.android.material.appbar."));
//                }
//
//                if(fileContent.get(i).contains("import com.google.android.material.appbar.TabLayout;")){
//                    fileContent.set(i, fileContent.get(i).replace("iimport com.google.android.material.appbar.TabLayout;","import com.google.android.material.tabs.TabLayout;"));
//                }
//
//                if(fileContent.get(i).contains("import android.support.v4.util.")){
//                    fileContent.set(i, fileContent.get(i).replace("import android.support.v4.util.","import androidx.collection."));
//                }
//
//                if(fileContent.get(i).contains("import com.google.android.material.appbar.TabLayout;")){
//                    fileContent.set(i, fileContent.get(i).replace("import com.google.android.material.appbar.TabLayout;","import com.google.android.material.tabs.TabLayout;"));
//                }
//
//                if(fileContent.get(i).contains("import android.support.v4.provider.")){
//                    fileContent.set(i, fileContent.get(i).replace("import android.support.v4.provider.","import androidx.documentfile.provider."));
//                }
//                if (fileContent.get(i).contains(".getWorkDatabase")) {
//                    System.out.println(classFileName);
//                }
            }

            Files.write(file.toPath(), fileContent, StandardCharsets.UTF_8);
        }

    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".java")) {
                txtFileNames.add(filename);
            }
        });
    }

    public static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
