package RemoteTest.extractgoogletest;

import HSO.RQ1.TriggerNum;
import HSO.model.HSO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectTest {

    public static void main(String[] args) throws IOException {
        String ctsPath = args[0];

        List<String> testFileNames = new ArrayList<>();
        List<String> needDeleteFileNames = new ArrayList<>();

        getFileList(ctsPath, testFileNames, needDeleteFileNames);

        System.out.println("testFileNames.size:" + testFileNames.size());
//
        for(String fileName : testFileNames){

            File testFile = new File(fileName);
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(testFile));
            boolean isTestFile = false;
            String packagePath = "";

            while ((line = br.readLine()) != null) {
                if(line.startsWith("package")){
                    packagePath = line.replaceAll("package ", "").replace(";","").replace(".","/");
                    break;
                }
            }

            File theDir = new File("/Users/xsun0035/workspace/monash/LazyCow/app/src/main/java/"+packagePath);
            if (!theDir.exists()){
                theDir.mkdirs();
            }


            File originFile = new File(fileName);
            FileUtils.copyFileToDirectory(originFile, theDir);
            System.out.println("copySuccess:"+fileName);


        /**
         * delete v2
         */
//        File testFile = new File(fileName);
//        String line = "";
//        BufferedReader br = new BufferedReader(new FileReader(testFile));
//        boolean needDelete = false;
//
//        while ((line = br.readLine()) != null) {
//            if (line.contains("import com.android.frameworks.coretests.R;")
//                || line.contains("package android.view")
//                || line.contains("package android.widget")
//                || line.contains("extends InstrumentationTestCase")
//                || line.contains("package android.uwb")
//                || line.contains("IBinderWorkSourceService")
//                || line.contains("import com.google.caliper")
//                || line.contains("import libcore.")
//                || line.contains("import static android.text.format.RelativeDateTimeFormatter.")
//                || line.contains("import com.android.internal.util.test.")
//                || line.contains("import android.support.test.uiautomator.UiDevice;")
//                || line.contains("public class BatteryStatsTests {")
//                || line.contains("package com.android.bandwidthtest.util;")
//                || line.contains("import com.android.frameworks.coretests.aidl")
//                || line.contains("extends Activity")
//                || line.contains("BaseCmdReceiver")
//                || line.contains("package android.app.activity;")
//                || line.contains("import android.system.suspend.internal.")
//                || line.contains("BinderTransactionNameResolver")
//                || line.contains("import android.view.MockView;")
//                || line.contains("import android.app.EmptyActivity;")
//                || line.contains("IBinderThreadPriorityService")
//                || line.contains("MockWebServer")
//                || line.contains("AbstractProxyTest")
//                || line.contains("EmptyLayoutActivity")
//                || line.contains("ICrossUserContentService")
//                || line.contains("import com.android.frameworks.coretests.enabled_app.DisabledActivity;")
//                || line.contains("package android.nfc;")
//                || line.contains("package android.animation;")
//                || line.contains("DownloadManagerBaseTest")
//                || line.contains("import static com.android.dx.mockito.")
//                || line.contains("IRemoteMemoryIntArray")
//                || line.contains("import static org.hamcrest.Matchers.aMapWithSize;")
//                || line.contains("import static android.text.format.DateIntervalFormat.")
//                || line.contains("package android.os;")
//                || line.contains("import android.os.test.TestLooper;")
//                || line.contains("AbstractCrossUserContentResolverTest")
//                || line.contains("StartProgramListUpdatesFanoutTest")
//                || line.contains("R.")
//                || line.contains("package com.android.multidexlegacyandexception.tests;")
//                || line.contains("public class Binder")
//                || line.contains("package com.android.connectivitymanagertest.functional;")
//                || line.contains("ConnectivityManagerTestBase")
//                || line.contains("ACTION_MESSAGE_READ_STATUS_CHANGED")
//                || line.contains("package com.android.overlaytest")
//                || line.contains("PROCESS_STATE_IMPORTANT_BACKGROUND")
//                || line.contains("package com.android.internal.http.multipart;")
//                || line.contains("package com.android.os.bugreports.tests;")
//                || line.contains("public final class StringPoolTest")
//                || line.contains("GnssTimeSuggestionTest")
//                || line.contains("extends InstrumentationTestRunner")
//                || line.contains("SimPhonebookContractTest")
//                || line.contains("extends Instrumentation")
//                || line.contains("package com.android.internal.util.test;")
//                || line.contains("set24HourTimePref")
//                || line.contains("package android.app.timezone;")
//                || line.contains("WifiConfigurationHelper")
//                || line.contains("package android.app.usage;")
//                || line.contains("package com.android.multidexlegacyandexception;")
//                || line.contains("package androidx.media.filterfw.samples.simplecamera;")
//                || line.contains("package android.net.vcn")
//                || line.contains("package android.wm;")
//                || line.contains("package android.windowanimationjank;")
//                || line.contains("package android.security.keystore;")
//                || line.contains("package android.processor.view.inspector;")
//                || line.contains("package android.security.keystore2;")
//                || line.contains("package android.testing;")
//                || line.contains("import android.perftests.utils.")
//                || line.contains("import android.testing.")
//                || line.contains("package com.android.tests.sysmem.host;")
//                || line.contains("import com.android.test.protoinputstream")
//                || line.contains("WatchdogEventLogger")
//                || line.contains("import com.android.cts.")
//                || line.contains("com.android.powermodel.")
//                || line.contains("AbandonSessionsRule")
//                || line.contains("package com.android.location.fused.tests;")
//                || line.contains("import org.robolectric")
//                || line.contains("import com.android.mediaframeworktest")
//                || line.contains("package com.android.media.")
//                || line.contains("UiAutomation")
//                || line.contains("import static com.android.shell.")
//                    || line.contains("package com.android.systemui.")
//                    || line.contains("WapPushTest")
//                    || line.contains("package com.android.test.taskembed;")
//                    || line.contains("WifiTrackerTest")
//                    || line.contains("package android.gameperformance;")
//                    || line.contains("package android.surfacecomposition;")
//                    || line.contains("TextPerfUtils")
//                    || line.contains("package com.example.android.camera2.cameratoo;")
//                    || line.contains("package com.android.codegentest;")
//                    || line.contains("import android.support.test.InstrumentationRegistry;")
//                    || line.contains("TestPackageParser2")
//                    || line.contains("import static com.android.server.devicepolicy.DpmTestUtils")
//                    || line.contains("OptionalClassRunner")
//                    || line.contains("package com.android.test.layout;")
//                    || line.contains("package com.android.settingslib.inputmethod;")
//                    || line.contains("package com.android.settingslib.utils;")
//                    || line.contains("package com.android.settingslib.bluetooth;")
//                    || line.contains("AppRestrictionsHelper")
//                    || line.contains("package com.android.providers.settings;")
//                    || line.contains("package android.test.suitebuilder;")
//                    || line.contains("import static android.text.format.Formatter.FLAG_SI_UNITS;")
//                    || line.contains("ExternalStorageProvider")
//                    || line.contains("package com.android.wm.shell.tests;")
//                    || line.contains("CsvParser")
//                    || line.contains("PackageSettingBuilder")
//                    || line.contains("android.provider.settings.backup.")
//                    || line.contains("import android.net.http.Headers;")
//                    || line.contains("EmptyActivity")
//                    || line.contains("TestLooper")
//                    || line.contains("BatterySavingStatsTest")
//                    || line.contains("package com.android.server.apphibernation;")
//                    || line.contains("package com.android.server.webkit;")
//                    || line.contains("BluetoothAirplaneModeListenerTest")
//                    || line.contains("BatteryServiceTest")
//                    || line.contains("import static android.os.RecoverySystem.")
//                    || line.contains("InputMethodUtilsTest")
//                    || line.contains("extends UiServiceTestCase ")
//                    || line.contains("ApplicationInfoBuilder")
//                    || line.contains("extends DpmTestBase")
//                    || line.contains("package com.android.server.wm.")
//                    || line.contains("package com.android.server.am")
//                    || line.contains("package com.android.server.adb;")
//                    || line.contains("MockableLocationProviderTest")
//                    || line.contains("LocationFudgerTest")
//                    || line.contains("PriorityDumpTest")
//                    || line.contains("package com.android.server.vcn.util;")
//                    || line.contains("FakeCryptoBackupServerTest")
//                    || line.contains("package com.android.server.testables;")
//                    || line.contains("package com.android.server.usbtest;")
//                    || line.contains("import static android.app.AppOpsManager.")
//                    || line.contains("package com.android.server.usb;")
//                    || line.contains("SoundTriggerMiddlewareImplTest")
//                    || line.contains("import com.android.server.locksettings.recoverablekeystore.TestData;")
//                    || line.contains("TestData.")
//                    || line.contains("KeyValueEncrypter")
//                    || line.contains("import static com.android.server.integrity.utils.")
//                    || line.contains("import static com.android.server.testutils")
//                    || line.contains("TestHandler.")
//                    || line.contains("import static com.android.servicestests.")
//                    || line.contains("MockUtils.")
//                    || line.contains("BaseLockSettingsServiceTests")
//                    || line.contains("IntermediateEncryptingTransportManager.")
//                    || line.contains("import static com.android.server.people.data.TestUtils")
//                    || line.contains("PrioritySchedulingTest")
//                    || line.contains("MockScheduledExecutorService")
//                    || line.contains("AccessibilityInputFilterTest")
//                    || line.contains("import com.android.server.accessibility.test")
//                    || line.contains("package com.android.server.uri;")
//                    || line.contains("package com.android.server.blob;")
//                    || line.contains("GnssTimeSuggestion")
//                    || line.contains("TestCaseUtil.")
//                    || line.contains("TestClass.")
//                    || line.contains("extends TestActivity")
//                    || line.contains("RawBatteryStatsTest")
//                    || line.contains("package com.android.settingslib.applications;")
//                    || line.contains("KeySetStrings.")
//                    || line.contains("package com.android.frameworks.perftests.am.tests")
//                    || line.contains("package com.android.bluetoothmidiservice")
//                    || line.contains("package com.android.server.audio;")
//                    || line.contains("AccountManagerServiceTest")
//                    || line.contains("MediaPlayerStateErrors")
//                    || line.contains("AesEncryptionUtil.")
//                    || line.contains("package com.android.server.tv.tunerresourcemanager")
//                    || line.contains("SharesheetModelScorerTest")
//                    || line.contains("package com.android.server.job;")
//                    || line.contains("TestUtils.")
//                    || line.contains("package com.android.apkverity;")
//                    || line.contains("WallpaperBackupAgentTest")
//                    || line.contains("import com.android.systemui.")
//                    || line.contains("SELINUX_R_CHANGES")
//                    || line.contains("AesEncryptionUtil")
//                    || line.contains("LockSettingsShellCommandTest")
//                    || line.contains("MediaRecorderStateUnitTestTemplate")
//                    || line.contains("package com.android.audiopolicytest;")
//                    || line.contains("MediaRecorderStateErrors")
//                    || line.contains("WrappedKeyTest")
//                    || line.contains("TimeZoneDetectorStrategyImplTest")
//                    || line.contains("AndroidNetIpSecIkeUpdater")
//                    || line.contains("CameraBinderTestUtils")
//                    || line.contains("RoutingSessionInfoTest")
//                    || line.contains("VersionedPackage")
//                    || line.contains("RecoverableKeyGeneratorTest")
//                    || line.contains("EntropyMixerTest")
//                    || line.contains("CameraMetadataTest")
//                    || line.contains("TimeDetectorStrategyTest")
//                    || line.contains("RecoverableKeyGeneratorTest")
//                    || line.contains("import com.android.tradefed")
//                    || line.contains("EqualsTester")
//                    || line.contains(".containsAtLeast")
//                    || line.contains("EqualsTester")
//                    || line.contains("EqualsTester")
//                    || line.contains("EqualsTester")
//                    || line.contains("EqualsTester")
//                    || line.contains("EqualsTester")
//                    || line.contains("EqualsTester")
//
//
//
//            ) {
//                needDelete = true;
//            }
//        }
//
//        if (needDelete) {
//            File file = new File(fileName);
//            file.delete();
//        }
    }


}

    public static void getFileList(String outputFilePath, List<String> txtFileNames, List<String> needDeleteFileNames) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(outputFilePath));

        List<String> result = paths.filter(Files::isRegularFile)
                .map(x -> x.toAbsolutePath().toString()).collect(Collectors.toList());

        result.stream().forEach(filename -> {
            if (filename.endsWith(".java")) {
                txtFileNames.add(filename);
            } else {
                needDeleteFileNames.add(filename);
            }
        });
    }
}
