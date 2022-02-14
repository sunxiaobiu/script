package permission;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import util.CollectionIntersection;
import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class PermissionCompatibility {

    public static void main(String[] args) throws IOException {
        String logFolder = args[0];

        Set<String> permissionSdk23 = new HashSet<>();
        Set<String> permissionSdk24 = new HashSet<>();
        Set<String> permissionSdk25 = new HashSet<>();
        Set<String> permissionSdk26 = new HashSet<>();
        Set<String> permissionSdk27 = new HashSet<>();
        Set<String> permissionSdk28 = new HashSet<>();
        Set<String> permissionSdk29 = new HashSet<>();
        Set<String> permissionSdk30 = new HashSet<>();
        Set<String> permissionSdk31 = new HashSet<>();

        //=========================================SDK 23-31==============================================

        getSDKHashLog(logFolder, permissionSdk23, "23");
        getSDKHashLog(logFolder, permissionSdk24, "24");
        getSDKHashLog(logFolder, permissionSdk25, "25");
        getSDKHashLog(logFolder, permissionSdk26, "26");
        getSDKHashLog(logFolder, permissionSdk27, "27");
        getSDKHashLog(logFolder, permissionSdk28, "28");
        getSDKHashLog(logFolder, permissionSdk29, "29");
        getSDKHashLog(logFolder, permissionSdk30, "30");
        getSDKHashLog(logFolder, permissionSdk31, "31");


        Set<String> allPermissions = new HashSet<>();
        allPermissions.addAll(permissionSdk23);
        allPermissions.addAll(permissionSdk24);
        allPermissions.addAll(permissionSdk25);
        allPermissions.addAll(permissionSdk26);
        allPermissions.addAll(permissionSdk27);
        allPermissions.addAll(permissionSdk28);
        allPermissions.addAll(permissionSdk29);
        allPermissions.addAll(permissionSdk30);
        allPermissions.addAll(permissionSdk31);

        //System.out.println(allPermissions);

        Set<String> intersection = CollectionIntersection.findIntersection(new HashSet<>(), permissionSdk23, permissionSdk24, permissionSdk25, permissionSdk26
        , permissionSdk27, permissionSdk28, permissionSdk29, permissionSdk30, permissionSdk31);
        //System.out.println(intersection);

        Set<String> resList = new HashSet<>();
        for(String permission : allPermissions){
            StringBuilder res = new StringBuilder();
            res.append(permission).append(":");
            if(permissionSdk23.contains(permission)){
                res.append(23).append(",");
            }
            if(permissionSdk24.contains(permission)){
                res.append(24).append(",");
            }
            if(permissionSdk25.contains(permission)){
                res.append(25).append(",");
            }
            if(permissionSdk26.contains(permission)){
                res.append(26).append(",");
            }
            if(permissionSdk27.contains(permission)){
                res.append(27).append(",");
            }
            if(permissionSdk28.contains(permission)){
                res.append(28).append(",");
            }
            if(permissionSdk29.contains(permission)){
                res.append(29).append(",");
            }
            if(permissionSdk30.contains(permission)){
                res.append(30).append(",");
            }
            if(permissionSdk31.contains(permission)){
                res.append(31).append(",");
            }
            resList.add(res.toString());
        }

        for(String res : resList){
            System.out.println(res);
        }

    }

    private static void getSDKHashLog(String logFolder, Set<String> sdkHashMap, String sdkVersion) throws IOException {
        File file = new File(logFolder + "/API" + sdkVersion + ".txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String permissionList = Regex.getSubUtilSimple(currentLine, "(android.permission.*)");
            if(permissionList.contains("(")){
                continue;
            }

            List<String> permissions = new ArrayList<>();
            if(permissionList.contains("|")){
                permissions.addAll(Arrays.asList(permissionList.split("\\|")));
            }else if(StringUtils.isNotBlank(permissionList)){
                permissions.add(permissionList);
            }

            sdkHashMap.addAll(permissions);
        }
    }
}
