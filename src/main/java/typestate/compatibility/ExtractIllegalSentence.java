package typestate.compatibility;

import org.apache.commons.lang3.StringUtils;
import util.ClassHelp;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtractIllegalSentence {

    /**
     * format the IllegalStateException for each API
     * @param args
     */
    public static void main(String[] args) throws IOException {
        for(int sdkNum=1; sdkNum<=32; sdkNum++){
            List<String> logFileNames = new ArrayList<>();
            String illegalStateExceptionClassesPath = "/Users/xsun0035/Desktop/TypeState/IllegalStateExceptionClasses_SDK"+sdkNum;
            getFileList(illegalStateExceptionClassesPath, logFileNames);

            for(String logFileName : logFileNames){
                String absoluteFileName = illegalStateExceptionClassesPath + "/" + logFileName;
                File file = new File(absoluteFileName);
                List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

                for (int i = 0; i < fileContent.size(); i++) {
                    String currentLine = fileContent.get(i);
                    String commentSentenceList = "";

                    /**
                     * extract method comment
                     */
                    if (StringUtils.isNotBlank(Regex.getSubUtilSimple(currentLine, "(<.*>::)"))) {
                        commentSentenceList = fileContent.get(i + 1);
                    }

                    if (!ClassHelp.isTypeStateClass(commentSentenceList)) {
                        continue;
                    }

                    String illegalSentence = preProcessing(commentSentenceList);
                    String apiSignature = Regex.getSubUtilSimple(currentLine, "(<.*>::)");

                    if(StringUtils.isNotBlank(illegalSentence) && StringUtils.isNotBlank(apiSignature)){
                        String outputFileName = "/Users/xsun0035/Desktop/TypeState/illegalSentence_SDK" + sdkNum + ".txt";
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFileName, true)));
                        out.println(apiSignature + illegalSentence);
                        out.close();

                    }
                }
            }
        }
    }

    /**
     * split sentences(separate sentences start with @throws/@param/@return)
     *
     * @param singleSentence
     * @return
     */
    public static String preProcessing(String singleSentence) {
        if (!ClassHelp.containsAnnotate(singleSentence)) {
            return singleSentence;
        }

        StringBuilder stringBuilder = new StringBuilder();

        while (ClassHelp.containsAnnotate(singleSentence)) {
            int largestIndex = 0;
            if (singleSentence.lastIndexOf("@param") != -1 && singleSentence.lastIndexOf("@param") > largestIndex) {
                largestIndex = singleSentence.lastIndexOf("@param");
            }
            if (singleSentence.lastIndexOf("@throws") != -1 && singleSentence.lastIndexOf("@throws") > largestIndex) {
                largestIndex = singleSentence.lastIndexOf("@throws");
            }
            if (singleSentence.lastIndexOf("@return") != -1 && singleSentence.lastIndexOf("@return") > largestIndex) {
                largestIndex = singleSentence.lastIndexOf("@return");
            }

            String currentPart = singleSentence.substring(largestIndex).replaceAll("(\\s+$)", "");
            if (StringUtils.isNotBlank(currentPart)) {
                //only need "@throws IllegalStateException" sentences
                if (currentPart.contains("@throws") && currentPart.contains("IllegalStateException")) {
                    if (currentPart.endsWith(".")) {
                        stringBuilder.append(currentPart.replace("@", ""));
                    } else {
                        stringBuilder.append(currentPart.replace("@", "") + ".");
                    }
                }
            }

            singleSentence = singleSentence.substring(0, largestIndex);
        }

        return stringBuilder.toString();
    }

    private static void getFileList(String outputFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });
    }
}
