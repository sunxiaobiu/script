package AutoUnitTest.processtest;

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
            System.out.println("==============Process Java FileName==============" + classFileName);

            File file = new File(testCasesFilePath + "/" + classFileName);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

            for (int i = 0; i < fileContent.size(); i++) {
                //去掉import 中的[]
                if (fileContent.get(i).startsWith("import") && fileContent.get(i).contains("[]")) {
                    fileContent.set(i, fileContent.get(i).replaceAll("\\[]",""));
                }

                //ShapeDrawable var3 = new ShapeDrawable;  添加()到new 语句中
                String newString = getSubUtilSimple(fileContent.get(i), "( = new .*;)");
                if (StringUtils.isNotBlank(newString) && !newString.contains("(") && !newString.contains(")") && !newString.contains("[") && !newString.contains("]")) {
                    fileContent.set(i, fileContent.get(i).replaceAll(";","();"));
                }

                if(fileContent.get(i).contains("(String)null")){
                    fileContent.set(i, fileContent.get(i).replace("(String)null","(String)\"android\""));
                }

                String runnableString = getSubUtilSimple(fileContent.get(i), "(\\(Runnable\\)var[0-9]+)");
                if(StringUtils.isNotBlank(runnableString)){
                    fileContent.set(i, fileContent.get(i).replace(runnableString,"(Runnable)(new Runnable() {public void run() {}})"));
                }

                String throwableString = getSubUtilSimple(fileContent.get(i), "(\\(Throwable\\)var[0-9]+)");
                if(StringUtils.isNotBlank(throwableString)){
                    fileContent.set(i, fileContent.get(i).replace(throwableString,"(Throwable)(new Throwable())"));
                }

                String rectString = getSubUtilSimple(fileContent.get(i), "(\\(Rect\\)null)");
                if(StringUtils.isNotBlank(rectString)){
                    fileContent.set(i, fileContent.get(i).replace(rectString,"(Rect)(new Rect())"));
                }

                String handlerString = getSubUtilSimple(fileContent.get(i), "(new Handler;)");
                if(StringUtils.isNotBlank(handlerString)){
                    fileContent.set(i, fileContent.get(i).replace(handlerString,"new Handler();"));
                }



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
