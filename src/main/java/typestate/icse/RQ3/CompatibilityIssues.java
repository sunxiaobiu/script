package typestate.icse.RQ3;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import util.FileUtil;
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

public class CompatibilityIssues {

    public static void main(String[] args) throws IOException {
        String resFile = "/Users/xsun0035/Desktop/TypeState_ICSE2023/RQ2/CompatibilityIssues_RQ2/output/";
        List<String> logFileNames = new ArrayList<>();

        getFileList(resFile, logFileNames);

        int totalNum = 0;

        for (String fileName : logFileNames) {
            File theFile = new File(resFile + "/" + fileName);
            LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
            while (it.hasNext()) {
                String currentLine = it.nextLine();
                if(
                        (currentLine.contains("android.media.MediaCodec") && currentLine.contains("setParameters"))
                                || (currentLine.contains("android.media.MediaCodec") && currentLine.contains("reset"))
                                || (currentLine.contains("android.media.MediaCodec") && currentLine.contains("getName"))
                                || (currentLine.contains("android.media.MediaCodec") && currentLine.contains("setVideoScalingMode"))
                                || (currentLine.contains("android.media.MediaCodec") && currentLine.contains("configure") && currentLine.contains("MediaFormat") && currentLine.contains("Surface") && currentLine.contains("MediaCrypto") && currentLine.contains("int"))
                                || (currentLine.contains("android.media.MediaCodec") && currentLine.contains("stop"))
                                || (currentLine.contains("android.media.tv.TvRecordingClient") && currentLine.contains("startRecording") && currentLine.contains("Uri"))
                                || (currentLine.contains("MediaPlayer") && currentLine.contains("setPlaybackParams") && currentLine.contains("PlaybackParams"))
                                || (currentLine.contains("android.content.Context") && currentLine.contains("startService") && currentLine.contains("Intent"))
                                || (currentLine.contains("android.media.MediaCodec") && currentLine.contains("getCodecInfo"))
                                || (currentLine.contains("android.media.MediaRecorder") && currentLine.contains("start"))
                                || (currentLine.contains("android.service.media.MediaBrowserService") && currentLine.contains("getBrowserRootHints"))
                                || (currentLine.contains("android.media.ImageWriter") && currentLine.contains("dequeueInputImage"))
                                || (currentLine.contains("TaskDrainer") && currentLine.contains("setPlaybackParams"))
                                || (currentLine.contains("MediaPlayer") && currentLine.contains("taskFinished") && currentLine.contains("T"))
                                || (currentLine.contains("CookieSyncManager") && currentLine.contains("getInstance"))
                ){
                    totalNum++;
                }
            }
        }

        System.out.println(totalNum);
    }

    private static void getFileList(String testCasesFilePath, List<String> txtFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(testCasesFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".txt")) {
                txtFileNames.add(filename);
            }
        });
    }
}
