package permission.literaturereview;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import permission.other.*;
import util.IncrementHashMap;
import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CompareDiff {

    public static void main(String[] args) throws IOException {
        Set<String> res = new HashSet<>();
        Set<String> resClassName = new HashSet<>();
        Set<String> methodPermissionMap = new HashSet<>();
        HashMap<String, Integer> resClassMethodNamePermissionSize = new HashMap<>();
        HashMap<String, Integer> methodIncrementalSet = new HashMap<>();


        Set<String> Arcade = new HashSet<>();
        getPermission("/Users/xsun0035/workspace/monash/permissionMapping/arcade/APIPermission24.txt", Arcade);
        System.out.println("Arcade Num:" + Arcade.size());

        Set<String> Axplorer = new HashSet<>();
        getPermission("/Users/xsun0035/workspace/monash/permissionMapping/axplorer/permissions/api-24/APIPermission_framework-map-24.txt", Axplorer);
        getPermission("/Users/xsun0035/workspace/monash/permissionMapping/axplorer/permissions/api-24/APIPermission_sdk-map-24.txt", Axplorer);
        System.out.println("Axplorer Num:" + Axplorer.size());

        Set<String> Natidroid = new HashSet<>();
        getPermission("/Users/xsun0035/workspace/monash/permissionMapping/natidroid/APIPermission_API24.txt", Natidroid);
        System.out.println("Natidroid Num:" + Natidroid.size());

        Set<String> Mevol = new HashSet<>();
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/API24.txt", Mevol);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/API25.txt", Mevol);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/API26.txt", Mevol);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/API27.txt", Mevol);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/API28.txt", Mevol);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/API29.txt", Mevol);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/API30.txt", Mevol);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/API31.txt", Mevol);
        System.out.println("Mevol Num:" + Mevol.size());

        //collect hidden APIs
        Set<String> hiddenAPIs = new HashSet<>();
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/HideAPI24.txt", hiddenAPIs);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/HideAPI25.txt", hiddenAPIs);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/HideAPI26.txt", hiddenAPIs);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/HideAPI27.txt", hiddenAPIs);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/HideAPI28.txt", hiddenAPIs);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/HideAPI29.txt", hiddenAPIs);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/HideAPI30.txt", hiddenAPIs);
        getPermission("/Users/xsun0035/Desktop/AOSP_API_Permission/HideAPI31.txt", hiddenAPIs);
        System.out.println("hiddenAPIs Num:" + hiddenAPIs.size());

        res.addAll(Arcade);
        res.addAll(Axplorer);
        res.addAll(Natidroid);
        res.addAll(Mevol);
        System.out.println("res Num:" + res.size());
        System.out.println("=============================================");
        Set<String> leftClass = new HashSet<>();
        for (String s : res) {
            if (hiddenAPIs.contains(Regex.getSubUtilSimple(s, "(<.*>)"))) {
                continue;
            }
            resClassName.add(Regex.getSubUtilSimple(s, "(<.*:)"));
            IncrementHashMap.incrementValue(resClassMethodNamePermissionSize, Regex.getSubUtilSimple(s, "(<.*>)"));

            //processTelephonyManager(methodPermissionMap, methodIncrementalSet, s);
            //processBluetoothManager(methodPermissionMap, methodIncrementalSet, s);
            //processBluetoothGatt(methodPermissionMap, methodIncrementalSet, s);
            //processMediaRecorder(methodPermissionMap, methodIncrementalSet, s);
            //processTelecomManager(methodPermissionMap, methodIncrementalSet, s);
            //processAudioManager(methodPermissionMap, methodIncrementalSet, s);
            //processConnectivityManager(methodPermissionMap, methodIncrementalSet, s);

            String classPathStr = Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", "");
            String classNameStr = Regex.getSubUtilSimple(classPathStr, "([^.]+(?!.*.))");
            HashMap<String, String> managerServiceMap = ManagerServiceMap.getConstantKeyForOtherService();
            HashMap<String, String> managerServiceMapKey = ManagerServiceMap.getConstantKey();

            List<String> easyConstructorClass = EasyClassConstructor.getClsName();
            List<String> notEasyConstructorClass = NotEasyClassConstructor.getClsName();

            /**
             * manager 类
             */
            if (Regex.getSubUtilSimple(s, "(<.*:)").endsWith("Manager:")) {
//                processManager(methodPermissionMap, methodIncrementalSet, s);
            }

            /**
             * service 类
             */
            else if (Regex.getSubUtilSimple(s, "(<.*:)").endsWith("Service:") && !Regex.getSubUtilSimple(s, "(<.*:)").contains("$")) {
//                processService(methodPermissionMap, methodIncrementalSet, s);
            }

            /**
             * process 不是service 和 manager 结尾的，其余的service类（能match ManagerServiceMap 中的值）
             */
            else if(StringUtils.isNotBlank(classPathStr) && (managerServiceMap.get(classPathStr) != null || managerServiceMap.get(classNameStr+"Manager") != null)){
                //processOtherService(methodPermissionMap, methodIncrementalSet, s);
            }
            else if(easyConstructorClass.contains(Regex.getSubUtilSimple(s, "(<.*:)"))){
                //processReflectionEasy(methodPermissionMap, methodIncrementalSet, s);
            }else if(notEasyConstructorClass.contains(Regex.getSubUtilSimple(s, "(<.*:)")) && !Regex.getSubUtilSimple(s, "(<.*:)").contains("$")){
                //processReflectionNotEasy(methodPermissionMap, methodIncrementalSet, s);
            }
            else if(s.contains("<android.net.wifi.WifiManager$") && managerServiceMapKey.get(Regex.getSubUtilSimple(s, "(<.*\\$)").replace("<", "").replace("$", "")) != null){
                //processWifiManager(methodPermissionMap, methodIncrementalSet, s);
            }
            else{
                leftClass.add(Regex.getSubUtilSimple(s, "(<.*:)"));
            }
        }

        System.out.println("leftClass:"+leftClass.size());
        System.out.println("exclusive class size:" + resClassName.size());
        System.out.println("resClassMethodName size:" + resClassMethodNamePermissionSize.size());
        for (String s : leftClass) {
            System.out.println(s);
        }

//        for (String s : resClassName) {
//            System.out.println(s);
//        }

    }

    private static void processReflectionNotEasy(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        String classPathStr = Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", "");
        String classNameStr = Regex.getSubUtilSimple(classPathStr, "([^.]+(?!.*.))");
        HashMap<String, String> notEasyFullClsSig = NotEasyClassConstructor.getFullClsSig();

        List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
        if (CollectionUtils.isNotEmpty(permissionList)) {
            for (String permission : permissionList) {
                String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                //check if method-permission exist
                if (methodPermissionMap.contains(method + permission)) {
                    continue;
                } else {
                    methodPermissionMap.add(method + permission);
                }

                IncrementHashMap.incrementValue(methodIncrementalSet, method);

                List<String> unitTestRes = new ArrayList<>();

                unitTestRes.add("package com.edu.permission.permissionci2." + classNameStr + ";");
                unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                unitTestRes.add("import android.Manifest;");
                unitTestRes.add("import android.content.Context;");
                unitTestRes.add("import androidx.test.core.app.ApplicationProvider;");
                unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                unitTestRes.add("import androidx.test.rule.ActivityTestRule;");
                unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                unitTestRes.add("import org.junit.After;");
                unitTestRes.add("import org.junit.Before;");
                unitTestRes.add("import org.junit.Rule;");
                unitTestRes.add("import org.junit.Test;");
                unitTestRes.add("import org.junit.runner.RunWith;");
                unitTestRes.add("import java.lang.reflect.Constructor;");
                unitTestRes.add("import java.lang.reflect.Method;");
                unitTestRes.add("import android.*;");
                unitTestRes.add("import java.util.List;");
                unitTestRes.add("");
                unitTestRes.add("");
                unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                unitTestRes.add("private Context mContext;");
                unitTestRes.add("");
                unitTestRes.add("@Rule");
                unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                unitTestRes.add("");
                unitTestRes.add("@Before");
                unitTestRes.add("public void setUp() throws Exception {");
                unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                unitTestRes.add("}");
                unitTestRes.add("");
                unitTestRes.add("@Test");
                unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");
                unitTestRes.add("String targetClassName = \"" +classPathStr + "\";");
                unitTestRes.add("Class<?> targetClass = Class.forName(targetClassName);");

                String clsSig = notEasyFullClsSig.get(classPathStr);
                System.out.println(classPathStr);
                String paramString = Regex.getSubUtilSimple(clsSig, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> params = Arrays.asList(paramString.split(","));
                String clsParamTypes = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(clsParamTypes)) {
                            clsParamTypes = clsParamTypes + ",";
                        }
                        clsParamTypes = clsParamTypes + convertParam2ClassTypeWithClassType(param, classNameStr);
                    }
                }
                String paramClassValues = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(paramClassValues)) {
                            paramClassValues = paramClassValues + ",";
                        }
                        paramClassValues = paramClassValues + convertParam2Value(param);
                    }
                }

                unitTestRes.add("Constructor<?> c = targetClass.getDeclaredConstructor("+clsParamTypes+");");
                unitTestRes.add("Object targetObject = c.newInstance("+paramClassValues+");");


                String methodParamString = Regex.getSubUtilSimple(s, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> methodParams = Arrays.asList(methodParamString.split(","));
                String methodParamValues = "";
                if (StringUtils.isNotBlank(methodParamString) && CollectionUtils.isNotEmpty(methodParams)) {
                    for (String param : methodParams) {
                        if (StringUtils.isNotBlank(methodParamValues)) {
                            methodParamValues = methodParamValues + ",";
                        }
                        methodParamValues = methodParamValues + convertParam2Value(param);
                    }
                }
                String methodParamTypes = "";
                if (StringUtils.isNotBlank(methodParamString) && CollectionUtils.isNotEmpty(methodParams)) {
                    for (String param : methodParams) {
                        if (StringUtils.isNotBlank(methodParamTypes)) {
                            methodParamTypes = methodParamTypes + ",";
                        }
                        methodParamTypes = methodParamTypes + convertParam2ClassTypeWithClassType(param, classNameStr);
                    }
                }

                if(StringUtils.isNotBlank(methodParamTypes)){
                    String getMethodStmt = "Method targetMethod = targetClass.getMethod(\""+method+"\", "+methodParamTypes+");";
                    unitTestRes.add(getMethodStmt);
                    unitTestRes.add("targetMethod.setAccessible(true);");
                    unitTestRes.add("targetMethod.invoke(targetObject, "+methodParamValues+");");
                }else{
                    String getMethodStmt = "Method targetMethod = targetClass.getMethod(\""+method+"\");";
                    unitTestRes.add(getMethodStmt);
                    unitTestRes.add("targetMethod.setAccessible(true);");
                    unitTestRes.add("targetMethod.invoke(targetObject);");
                }
                unitTestRes.add("}");
                unitTestRes.add("\n");

                unitTestRes.add("");
                unitTestRes.add("@After");
                unitTestRes.add("public void afterMethod() throws Exception {");
                unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                unitTestRes.add("}");
                unitTestRes.add("}");

                String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/NotEasy/" + classNameStr + "/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                File outputJavaFile = new File(outputFile);
                outputJavaFile.getParentFile().mkdirs();
                for (String unitTestStr : unitTestRes) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                    out.println(unitTestStr);
                    out.close();
                }
            }
        }
    }


    private static void processReflectionEasy(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        String classPathStr = Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", "");
        String classNameStr = Regex.getSubUtilSimple(classPathStr, "([^.]+(?!.*.))");
        HashMap<String, String> easyFullClsSig = EasyClassConstructor.getFullClsSig();

        List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
        if (CollectionUtils.isNotEmpty(permissionList)) {
            for (String permission : permissionList) {
                String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                //check if method-permission exist
                if (methodPermissionMap.contains(method + permission)) {
                    continue;
                } else {
                    methodPermissionMap.add(method + permission);
                }

                IncrementHashMap.incrementValue(methodIncrementalSet, method);

                List<String> unitTestRes = new ArrayList<>();

                unitTestRes.add("package com.edu.permission.permissionci2." + classNameStr + ";");
                unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                unitTestRes.add("import android.Manifest;");
                unitTestRes.add("import android.content.Context;");
                unitTestRes.add("import androidx.test.core.app.ApplicationProvider;");
                unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                unitTestRes.add("import androidx.test.rule.ActivityTestRule;");
                unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                unitTestRes.add("import org.junit.After;");
                unitTestRes.add("import org.junit.Before;");
                unitTestRes.add("import org.junit.Rule;");
                unitTestRes.add("import org.junit.Test;");
                unitTestRes.add("import org.junit.runner.RunWith;");
                unitTestRes.add("import java.lang.reflect.Constructor;");
                unitTestRes.add("import java.lang.reflect.Method;");
                unitTestRes.add("import android.*;");
                unitTestRes.add("import java.util.List;");
                unitTestRes.add("");
                unitTestRes.add("");
                unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                unitTestRes.add("private Context mContext;");
                unitTestRes.add("");
                unitTestRes.add("@Rule");
                unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                unitTestRes.add("");
                unitTestRes.add("@Before");
                unitTestRes.add("public void setUp() throws Exception {");
                unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                unitTestRes.add("}");
                unitTestRes.add("");
                unitTestRes.add("@Test");
                unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");
                unitTestRes.add("String targetClassName = \"" +classPathStr + "\";");
                unitTestRes.add("Class<?> targetClass = Class.forName(targetClassName);");

                String clsSig = easyFullClsSig.get(classPathStr);
                String paramString = Regex.getSubUtilSimple(clsSig, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> params = Arrays.asList(paramString.split(","));
                String clsParamTypes = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(clsParamTypes)) {
                            clsParamTypes = clsParamTypes + ",";
                        }
                        clsParamTypes = clsParamTypes + convertParam2ClassType(param);
                    }
                }
                String paramClassValues = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(paramClassValues)) {
                            paramClassValues = paramClassValues + ",";
                        }
                        paramClassValues = paramClassValues + convertParam2ClassType(param);
                    }
                }

                unitTestRes.add("Constructor<?> c = targetClass.getDeclaredConstructor("+clsParamTypes+");");
                unitTestRes.add("Object targetObject = c.newInstance("+paramClassValues+");");


                String methodParamString = Regex.getSubUtilSimple(s, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> methodParams = Arrays.asList(methodParamString.split(","));
                String methodParamValues = "";
                if (StringUtils.isNotBlank(methodParamString) && CollectionUtils.isNotEmpty(methodParams)) {
                    for (String param : methodParams) {
                        if (StringUtils.isNotBlank(methodParamValues)) {
                            methodParamValues = methodParamValues + ",";
                        }
                        methodParamValues = methodParamValues + convertParam2Value(param);
                    }
                }
                String methodParamTypes = "";
                if (StringUtils.isNotBlank(methodParamString) && CollectionUtils.isNotEmpty(methodParams)) {
                    for (String param : methodParams) {
                        if (StringUtils.isNotBlank(methodParamTypes)) {
                            methodParamTypes = methodParamTypes + ",";
                        }
                        methodParamTypes = methodParamTypes + convertParam2ClassType(param);
                    }
                }

                if(StringUtils.isNotBlank(methodParamTypes)){
                    String getMethodStmt = "Method targetMethod = targetClass.getMethod(\""+method+"\", "+methodParamTypes+");";
                    unitTestRes.add(getMethodStmt);
                    unitTestRes.add("targetMethod.setAccessible(true);");
                    unitTestRes.add("targetMethod.invoke(targetObject, "+methodParamValues+");");
                }else{
                    String getMethodStmt = "Method targetMethod = targetClass.getMethod(\""+method+"\");";
                    unitTestRes.add(getMethodStmt);
                    unitTestRes.add("targetMethod.setAccessible(true);");
                    unitTestRes.add("targetMethod.invoke(targetObject);");
                }
                unitTestRes.add("}");
                unitTestRes.add("\n");

                unitTestRes.add("");
                unitTestRes.add("@After");
                unitTestRes.add("public void afterMethod() throws Exception {");
                unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                unitTestRes.add("}");
                unitTestRes.add("}");

                String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/Easy/" + classNameStr + "/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                File outputJavaFile = new File(outputFile);
                outputJavaFile.getParentFile().mkdirs();
                for (String unitTestStr : unitTestRes) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                    out.println(unitTestStr);
                    out.close();
                }
            }
        }
    }


    private static void processOtherService(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        String classPathStr = Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", "");
        String classNameStr = Regex.getSubUtilSimple(classPathStr, "([^.]+(?!.*.))");
        HashMap<String, String> managerServiceMap = ManagerServiceMap.getConstantKeyForOtherService();

        List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
        if (CollectionUtils.isNotEmpty(permissionList)) {
            for (String permission : permissionList) {
                String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                //check if method-permission exist
                if (methodPermissionMap.contains(method + permission)) {
                    continue;
                } else {
                    methodPermissionMap.add(method + permission);
                }

                IncrementHashMap.incrementValue(methodIncrementalSet, method);

                List<String> unitTestRes = new ArrayList<>();

                unitTestRes.add("package com.edu.permission.compatibilityissue." + classNameStr + ";");
                unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                unitTestRes.add("import android.Manifest;");
                unitTestRes.add("import android.content.Context;");
                unitTestRes.add("import " + classPathStr + ";");
                unitTestRes.add("import androidx.test.core.app.ApplicationProvider;");
                unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                unitTestRes.add("import org.junit.After;");
                unitTestRes.add("import org.junit.Before;");
                unitTestRes.add("import org.junit.Rule;");
                unitTestRes.add("import org.junit.Test;");
                unitTestRes.add("import org.junit.runner.RunWith;");
                unitTestRes.add("");
                unitTestRes.add("");
                unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                unitTestRes.add("private Context mContext;");
                unitTestRes.add("private " + classNameStr + " m" + classNameStr + ";");
                unitTestRes.add("");
                unitTestRes.add("@Rule");
                unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                unitTestRes.add("");
                unitTestRes.add("@Before");
                unitTestRes.add("public void setUp() throws Exception {");
                unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                unitTestRes.add("m" + classNameStr + " = (" + classNameStr + ") mContext.getSystemService(Context." + managerServiceMap.get(classPathStr) + ");");
                unitTestRes.add("}");
                unitTestRes.add("");
                unitTestRes.add("@Test");
                unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                String invocation = "m" + classNameStr + "." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> params = Arrays.asList(paramString.split(","));
                String paramValues = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(paramValues)) {
                            paramValues = paramValues + ",";
                        }
                        paramValues = paramValues + convertParam2Value(param);
                    }
                }
                invocation = invocation.replace(paramString, paramValues);
                unitTestRes.add(invocation);
                unitTestRes.add("}");
                unitTestRes.add("\n");

                unitTestRes.add("");
                unitTestRes.add("@After");
                unitTestRes.add("public void afterMethod() throws Exception {");
                unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                unitTestRes.add("}");
                unitTestRes.add("}");

                String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/OtherService/" + classNameStr + "/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                File outputJavaFile = new File(outputFile);
                outputJavaFile.getParentFile().mkdirs();
                for (String unitTestStr : unitTestRes) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                    out.println(unitTestStr);
                    out.close();
                }
            }
        }
    }

    private static void processWifiManager(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        String subCls = Regex.getSubUtilSimple(s, "(\\$.*\\:)").replace("$", "").replace(":", "");

        String classPathStr = Regex.getSubUtilSimple(s, "(<.*\\$)").replace("<", "").replace("$", "");
        String classNameStr = Regex.getSubUtilSimple(classPathStr, "([a-zA-Z]+Manager)");
        HashMap<String, String> managerServiceMap = ManagerServiceMap.getConstantKey();

        List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
        if (CollectionUtils.isNotEmpty(permissionList)) {
            for (String permission : permissionList) {
                String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                //check if method-permission exist
                if (methodPermissionMap.contains(method + permission)) {
                    continue;
                } else {
                    methodPermissionMap.add(method + permission);
                }

                IncrementHashMap.incrementValue(methodIncrementalSet, method);

                List<String> unitTestRes = new ArrayList<>();

                unitTestRes.add("package com.edu.permission.compatibilityissue." + classNameStr +"_" + subCls + ";");
                unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                unitTestRes.add("import android.Manifest;");
                unitTestRes.add("import android.content.Context;");
                unitTestRes.add("import " + classPathStr + ";");
                unitTestRes.add("import androidx.test.core.app.ApplicationProvider;");
                unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                unitTestRes.add("import org.junit.After;");
                unitTestRes.add("import org.junit.Before;");
                unitTestRes.add("import org.junit.Rule;");
                unitTestRes.add("import org.junit.Test;");
                unitTestRes.add("import org.junit.runner.RunWith;");
                unitTestRes.add("");
                unitTestRes.add("");
                unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                unitTestRes.add("private Context mContext;");
                unitTestRes.add("private " + classNameStr + " m" + classNameStr + ";");
                unitTestRes.add("");
                unitTestRes.add("@Rule");
                unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                unitTestRes.add("");
                unitTestRes.add("@Before");
                unitTestRes.add("public void setUp() throws Exception {");
                unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                unitTestRes.add("m" + classNameStr + " = (" + classNameStr + ") mContext.getSystemService(Context." + managerServiceMap.get(classPathStr) + ");");
                unitTestRes.add("}");
                unitTestRes.add("");
                unitTestRes.add("@Test");
                unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");

                //initialize subCls object
                HashMap<String, String> allSubClsMap = AllSubClsMap.run();
                String constructorMethodOfSubCls = allSubClsMap.get(Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", ""));
                String subClsMethodName = Regex.getSubUtilSimple(constructorMethodOfSubCls, "([^ ]+\\()").replace("(", "").replace(" ", "");
                String subClsParamString = Regex.getSubUtilSimple(constructorMethodOfSubCls, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> subClsParams = Arrays.asList(subClsParamString.split(","));
                String subClsParamValues = "";
                if (StringUtils.isNotBlank(subClsParamString) && CollectionUtils.isNotEmpty(subClsParams)) {
                    for (String param : subClsParams) {
                        String shortParam = Regex.getSubUtilSimple(param, "([^.]+(?!.*.))");
                        if (StringUtils.isNotBlank(subClsParamValues)) {
                            subClsParamValues = subClsParamValues + ",";
                        }
                        subClsParamValues = subClsParamValues + convertParam2Value(shortParam);
                    }
                }
                unitTestRes.add("WifiManager."+ subCls + " m"+subCls+" = "+"m" + classNameStr+"."+subClsMethodName+"("+subClsParamValues+");");


                //target method invocation
                String invocation = "m" + subCls + "." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> params = Arrays.asList(paramString.split(","));
                String paramValues = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(paramValues)) {
                            paramValues = paramValues + ",";
                        }
                        paramValues = paramValues + convertParam2Value(param);
                    }
                }
                invocation = invocation.replace(paramString, paramValues);
                unitTestRes.add(invocation);
                unitTestRes.add("}");
                unitTestRes.add("\n");

                unitTestRes.add("");
                unitTestRes.add("@After");
                unitTestRes.add("public void afterMethod() throws Exception {");
                unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                unitTestRes.add("}");
                unitTestRes.add("}");

                String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/WifiManager$/" + subCls + "/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                File outputJavaFile = new File(outputFile);
                outputJavaFile.getParentFile().mkdirs();
                for (String unitTestStr : unitTestRes) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                    out.println(unitTestStr);
                    out.close();
                }
            }
        }
    }


    private static void processService(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        String classPathStr = Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", "");
        String classNameStr = Regex.getSubUtilSimple(classPathStr, "([a-zA-Z]+Service)");
        HashMap<String, String> managerServiceMap = ManagerServiceMap.getConstantValue();

        boolean managerServiceMapContainsStr = false;
        String classNameManagerStr = "";
        String managerClassNameConstant = "";
        String managerClassNameStr = "";
        if(classNameStr.replace("Service", "").endsWith("Manager")){
            for(String managerServiceStr : managerServiceMap.keySet()){
                if(managerServiceStr.contains(classNameStr.replace("Service", ""))){
                    managerServiceMapContainsStr = true;
                    managerClassNameConstant = managerServiceMap.get(managerServiceStr);
                    managerClassNameStr = managerServiceStr;
                    classNameManagerStr = classNameStr.replace("Service", "");
                    break;
                }
            }
        }else{
            for(String managerServiceStr : managerServiceMap.keySet()){
                if(managerServiceStr.contains(classNameStr.replace("Service", "Manager"))){
                    managerServiceMapContainsStr = true;
                    managerClassNameConstant = managerServiceMap.get(managerServiceStr);
                    managerClassNameStr = managerServiceStr;
                    classNameManagerStr = classNameStr.replace("Service", "Manager");
                    break;
                }
            }
        }

        if (managerServiceMapContainsStr == false) {
            return;
        }


        List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
        if (CollectionUtils.isNotEmpty(permissionList)) {
            for (String permission : permissionList) {
                String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                //check if method-permission exist
                if (methodPermissionMap.contains(method + permission)) {
                    continue;
                } else {
                    methodPermissionMap.add(method + permission);
                }

                IncrementHashMap.incrementValue(methodIncrementalSet, method);

                List<String> unitTestRes = new ArrayList<>();

                if(StringUtils.isBlank(classNameStr)){
                    classNameStr = "Service";
                }

                unitTestRes.add("package com.edu.permission.compatibilityissue." + classNameStr + ";");
                unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                unitTestRes.add("import android.Manifest;");
                unitTestRes.add("import android.content.Context;");
                unitTestRes.add("import android.os.IBinder;");
                unitTestRes.add("import androidx.test.core.app.ApplicationProvider;");
                unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                unitTestRes.add("import org.junit.After;");
                unitTestRes.add("import org.junit.Before;");
                unitTestRes.add("import org.junit.Rule;");
                unitTestRes.add("import org.junit.Test;");
                unitTestRes.add("import org.junit.runner.RunWith;");
                unitTestRes.add("import java.lang.reflect.Method;");
                unitTestRes.add("import android.*;");
                unitTestRes.add("import java.util.List;");
                unitTestRes.add("");
                unitTestRes.add("");
                unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                unitTestRes.add("private Context mContext;");
                unitTestRes.add("");
                unitTestRes.add("@Rule");
                unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                unitTestRes.add("");
                unitTestRes.add("@Before");
                unitTestRes.add("public void setUp() throws Exception {");
                unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                unitTestRes.add("}");
                unitTestRes.add("");
                unitTestRes.add("@Test");
                unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");
                unitTestRes.add("Class serviceManagerClass = Class.forName(\"android.os.ServiceManager\");");
                unitTestRes.add("Method getService = serviceManagerClass.getMethod(\"getService\", String.class);");
                unitTestRes.add("IBinder retbinder = (IBinder) getService.invoke(serviceManagerClass,\""+managerClassNameConstant+"\");");
                unitTestRes.add("Class targetClass = Class.forName(retbinder.getInterfaceDescriptor());");
                unitTestRes.add("Object targetObject = targetClass.getClasses()[0].getMethod(\"asInterface\", IBinder.class).invoke(null, new Object[] { retbinder });");

                //params
                String invocation = "m" + classNameStr + "." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> params = Arrays.asList(paramString.split(","));
                String paramValues = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(paramValues)) {
                            paramValues = paramValues + ",";
                        }
                        paramValues = paramValues + convertParam2Value(param);
                    }
                }
                String paramClassTypes = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(paramClassTypes)) {
                            paramClassTypes = paramClassTypes + ",";
                        }
                        paramClassTypes = paramClassTypes + convertParam2ClassType(param);
                    }
                }

                if(StringUtils.isNotBlank(paramClassTypes)){
                    String getMethodStmt = "Method targetMethod = targetClass.getMethod(\""+method+"\", "+paramClassTypes+");";
                    unitTestRes.add(getMethodStmt);
                    unitTestRes.add("targetMethod.setAccessible(true);");
                    unitTestRes.add("targetMethod.invoke(targetObject, "+paramValues+");");
                }else{
                    String getMethodStmt = "Method targetMethod = targetClass.getMethod(\""+method+"\");";
                    unitTestRes.add(getMethodStmt);
                    unitTestRes.add("targetMethod.setAccessible(true);");
                    unitTestRes.add("targetMethod.invoke(targetObject);");
                }

                unitTestRes.add("}");
                unitTestRes.add("\n");

                unitTestRes.add("");
                unitTestRes.add("@After");
                unitTestRes.add("public void afterMethod() throws Exception {");
                unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                unitTestRes.add("}");
                unitTestRes.add("}");

                String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/service/" + classNameStr + "/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                File outputJavaFile = new File(outputFile);
                outputJavaFile.getParentFile().mkdirs();
                for (String unitTestStr : unitTestRes) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                    out.println(unitTestStr);
                    out.close();
                }
            }
        }
    }


    private static void processManager(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        String classPathStr = Regex.getSubUtilSimple(s, "(<.*:)").replace("<", "").replace(":", "");
        String classNameStr = Regex.getSubUtilSimple(classPathStr, "([a-zA-Z]+Manager)");
        HashMap<String, String> managerServiceMap = ManagerServiceMap.getConstantKey();

        if (managerServiceMap.get(classPathStr) == null) {
            return;
        }

        List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
        if (CollectionUtils.isNotEmpty(permissionList)) {
            for (String permission : permissionList) {
                String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                //check if method-permission exist
                if (methodPermissionMap.contains(method + permission)) {
                    continue;
                } else {
                    methodPermissionMap.add(method + permission);
                }

                IncrementHashMap.incrementValue(methodIncrementalSet, method);

                List<String> unitTestRes = new ArrayList<>();

                unitTestRes.add("package com.edu.permission.compatibilityissue." + classNameStr + ";");
                unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                unitTestRes.add("import android.Manifest;");
                unitTestRes.add("import android.content.Context;");
                unitTestRes.add("import " + classPathStr + ";");
                unitTestRes.add("import androidx.test.core.app.ApplicationProvider;");
                unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                unitTestRes.add("import org.junit.After;");
                unitTestRes.add("import org.junit.Before;");
                unitTestRes.add("import org.junit.Rule;");
                unitTestRes.add("import org.junit.Test;");
                unitTestRes.add("import org.junit.runner.RunWith;");
                unitTestRes.add("");
                unitTestRes.add("");
                unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                unitTestRes.add("private Context mContext;");
                unitTestRes.add("private " + classNameStr + " m" + classNameStr + ";");
                unitTestRes.add("");
                unitTestRes.add("@Rule");
                unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                unitTestRes.add("");
                unitTestRes.add("@Before");
                unitTestRes.add("public void setUp() throws Exception {");
                unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                unitTestRes.add("m" + classNameStr + " = (" + classNameStr + ") mContext.getSystemService(Context." + managerServiceMap.get(classPathStr) + ");");
                unitTestRes.add("}");
                unitTestRes.add("");
                unitTestRes.add("@Test");
                unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                String invocation = "m" + classNameStr + "." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                List<String> params = Arrays.asList(paramString.split(","));
                String paramValues = "";
                if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        if (StringUtils.isNotBlank(paramValues)) {
                            paramValues = paramValues + ",";
                        }
                        paramValues = paramValues + convertParam2Value(param);
                    }
                }
                invocation = invocation.replace(paramString, paramValues);
                unitTestRes.add(invocation);
                unitTestRes.add("}");
                unitTestRes.add("\n");

                unitTestRes.add("");
                unitTestRes.add("@After");
                unitTestRes.add("public void afterMethod() throws Exception {");
                unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                unitTestRes.add("}");
                unitTestRes.add("}");

                String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/manager/" + classNameStr + "/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                File outputJavaFile = new File(outputFile);
                outputJavaFile.getParentFile().mkdirs();
                for (String unitTestStr : unitTestRes) {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                    out.println(unitTestStr);
                    out.close();
                }
            }
        }
    }


    private static void processConnectivityManager(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        if (s.contains("android.net.ConnectivityManager")) {

            List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (String permission : permissionList) {
                    String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                    //check if method-permission exist
                    if (methodPermissionMap.contains(method + permission)) {
                        continue;
                    } else {
                        methodPermissionMap.add(method + permission);
                    }

                    IncrementHashMap.incrementValue(methodIncrementalSet, method);

                    List<String> unitTestRes = new ArrayList<>();

                    unitTestRes.add("package com.edu.permission.compatibilityissue.ConnectivityManager;");
                    unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                    unitTestRes.add("import android.Manifest;");
                    unitTestRes.add("import android.content.Context;");
                    unitTestRes.add("import android.net.ConnectivityManager;");
                    unitTestRes.add("import androidx.test.core.app.ApplicationProvider;");
                    unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                    unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                    unitTestRes.add("import org.junit.After;");
                    unitTestRes.add("import org.junit.Before;");
                    unitTestRes.add("import org.junit.Rule;");
                    unitTestRes.add("import org.junit.Test;");
                    unitTestRes.add("import org.junit.runner.RunWith;");
                    unitTestRes.add("");
                    unitTestRes.add("");
                    unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                    unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                    unitTestRes.add("private Context mContext;");
                    unitTestRes.add("private ConnectivityManager mConnectivityManager;");
                    unitTestRes.add("");
                    unitTestRes.add("@Rule");
                    unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                    unitTestRes.add("");
                    unitTestRes.add("@Before");
                    unitTestRes.add("public void setUp() throws Exception {");
                    unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                    unitTestRes.add("mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);");
                    unitTestRes.add("}");
                    unitTestRes.add("");
                    unitTestRes.add("@Test");
                    unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                    String invocation = "mConnectivityManager." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                    String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                    List<String> params = Arrays.asList(paramString.split(","));
                    String paramValues = "";
                    if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                        for (String param : params) {
                            if (StringUtils.isNotBlank(paramValues)) {
                                paramValues = paramValues + ",";
                            }
                            paramValues = paramValues + convertParam2Value(param);
                        }
                    }
                    invocation = invocation.replace(paramString, paramValues);
                    unitTestRes.add(invocation);
                    unitTestRes.add("}");
                    unitTestRes.add("\n");

                    unitTestRes.add("");
                    unitTestRes.add("@After");
                    unitTestRes.add("public void afterMethod() throws Exception {");
                    unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                    unitTestRes.add("}");
                    unitTestRes.add("}");

                    String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/ConnectivityManager/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                    for (String unitTestStr : unitTestRes) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                        out.println(unitTestStr);
                        out.close();
                    }
                }
            }
        }
    }

    private static void processAudioManager(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        if (s.contains("android.media.AudioManager")) {

            List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (String permission : permissionList) {
                    String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                    //check if method-permission exist
                    if (methodPermissionMap.contains(method + permission)) {
                        continue;
                    } else {
                        methodPermissionMap.add(method + permission);
                    }

                    IncrementHashMap.incrementValue(methodIncrementalSet, method);

                    List<String> unitTestRes = new ArrayList<>();

                    unitTestRes.add("package com.edu.permission.compatibilityissue.AudioManager;");
                    unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                    unitTestRes.add("import android.Manifest;");
                    unitTestRes.add("import android.content.Context;");
                    unitTestRes.add("import android.media.AudioManager;");
                    unitTestRes.add("import androidx.test.InstrumentationRegistry;");
                    unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                    unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                    unitTestRes.add("import org.junit.After;");
                    unitTestRes.add("import org.junit.Before;");
                    unitTestRes.add("import org.junit.Rule;");
                    unitTestRes.add("import org.junit.Test;");
                    unitTestRes.add("import org.junit.runner.RunWith;");
                    unitTestRes.add("");
                    unitTestRes.add("");
                    unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                    unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                    unitTestRes.add("private Context mContext;");
                    unitTestRes.add("private AudioManager mAudioManager;");
                    unitTestRes.add("");
                    unitTestRes.add("@Rule");
                    unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                    unitTestRes.add("");
                    unitTestRes.add("@Before");
                    unitTestRes.add("public void setUp() throws Exception {");
                    unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                    unitTestRes.add("mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);");
                    unitTestRes.add("}");
                    unitTestRes.add("");
                    unitTestRes.add("@Test");
                    unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                    String invocation = "mAudioManager." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                    String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                    List<String> params = Arrays.asList(paramString.split(","));
                    String paramValues = "";
                    if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                        for (String param : params) {
                            if (StringUtils.isNotBlank(paramValues)) {
                                paramValues = paramValues + ",";
                            }
                            paramValues = paramValues + convertParam2Value(param);
                        }
                    }
                    invocation = invocation.replace(paramString, paramValues);
                    unitTestRes.add(invocation);
                    unitTestRes.add("}");
                    unitTestRes.add("\n");

                    unitTestRes.add("");
                    unitTestRes.add("@After");
                    unitTestRes.add("public void afterMethod() throws Exception {");
                    unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                    unitTestRes.add("}");
                    unitTestRes.add("}");

                    String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/AudioManager/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                    for (String unitTestStr : unitTestRes) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                        out.println(unitTestStr);
                        out.close();
                    }
                }
            }
        }
    }

    private static void processTelecomManager(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        if (s.contains("android.telecom.TelecomManager")) {

            List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (String permission : permissionList) {
                    String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                    //check if method-permission exist
                    if (methodPermissionMap.contains(method + permission)) {
                        continue;
                    } else {
                        methodPermissionMap.add(method + permission);
                    }

                    IncrementHashMap.incrementValue(methodIncrementalSet, method);

                    List<String> unitTestRes = new ArrayList<>();

                    unitTestRes.add("package com.edu.permission.compatibilityissue.TelecomManager;");
                    unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                    unitTestRes.add("import android.Manifest;");
                    unitTestRes.add("import android.content.Context;");
                    unitTestRes.add("import android.telecom.TelecomManager;");
                    unitTestRes.add("import androidx.test.InstrumentationRegistry;");
                    unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                    unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                    unitTestRes.add("import org.junit.After;");
                    unitTestRes.add("import org.junit.Before;");
                    unitTestRes.add("import org.junit.Rule;");
                    unitTestRes.add("import org.junit.Test;");
                    unitTestRes.add("import org.junit.runner.RunWith;");
                    unitTestRes.add("");
                    unitTestRes.add("");
                    unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                    unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                    unitTestRes.add("private Context mContext;");
                    unitTestRes.add("private TelecomManager mTelecomManager;");
                    unitTestRes.add("");
                    unitTestRes.add("@Rule");
                    unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                    unitTestRes.add("");
                    unitTestRes.add("@Before");
                    unitTestRes.add("public void setUp() throws Exception {");
                    unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                    unitTestRes.add("mTelecomManager = (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);");
                    unitTestRes.add("}");
                    unitTestRes.add("");
                    unitTestRes.add("@Test");
                    unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                    String invocation = "mTelecomManager." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                    String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                    List<String> params = Arrays.asList(paramString.split(","));
                    String paramValues = "";
                    if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                        for (String param : params) {
                            if (StringUtils.isNotBlank(paramValues)) {
                                paramValues = paramValues + ",";
                            }
                            paramValues = paramValues + convertParam2Value(param);
                        }
                    }
                    invocation = invocation.replace(paramString, paramValues);
                    unitTestRes.add(invocation);
                    unitTestRes.add("}");
                    unitTestRes.add("\n");

                    unitTestRes.add("");
                    unitTestRes.add("@After");
                    unitTestRes.add("public void afterMethod() throws Exception {");
                    unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                    unitTestRes.add("}");
                    unitTestRes.add("}");

                    String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/TelecomManager/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                    for (String unitTestStr : unitTestRes) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                        out.println(unitTestStr);
                        out.close();
                    }
                }
            }
        }
    }

    private static void processMediaRecorder(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        if (s.contains("android.media.MediaRecorder")) {

            List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (String permission : permissionList) {
                    String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                    //check if method-permission exist
                    if (methodPermissionMap.contains(method + permission)) {
                        continue;
                    } else {
                        methodPermissionMap.add(method + permission);
                    }

                    IncrementHashMap.incrementValue(methodIncrementalSet, method);

                    List<String> unitTestRes = new ArrayList<>();

                    unitTestRes.add("package com.edu.permission.compatibilityissue.MediaRecorder;");
                    unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                    unitTestRes.add("import android.Manifest;");
                    unitTestRes.add("import android.content.Context;");
                    unitTestRes.add("import android.media.MediaRecorder;");
                    unitTestRes.add("import androidx.test.InstrumentationRegistry;");
                    unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                    unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                    unitTestRes.add("import org.junit.After;");
                    unitTestRes.add("import org.junit.Before;");
                    unitTestRes.add("import org.junit.Rule;");
                    unitTestRes.add("import org.junit.Test;");
                    unitTestRes.add("import org.junit.runner.RunWith;");
                    unitTestRes.add("");
                    unitTestRes.add("");
                    unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                    unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                    unitTestRes.add("private Context mContext;");
                    unitTestRes.add("private MediaRecorder mMediaRecorder;");
                    unitTestRes.add("");
                    unitTestRes.add("@Rule");
                    unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                    unitTestRes.add("");
                    unitTestRes.add("@Before");
                    unitTestRes.add("public void setUp() throws Exception {");
                    unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                    unitTestRes.add("mMediaRecorder = new MediaRecorder();");
                    unitTestRes.add("}");
                    unitTestRes.add("");
                    unitTestRes.add("@Test");
                    unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                    String invocation = "mMediaRecorder." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                    String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                    List<String> params = Arrays.asList(paramString.split(","));
                    String paramValues = "";
                    if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                        for (String param : params) {
                            if (StringUtils.isNotBlank(paramValues)) {
                                paramValues = paramValues + ",";
                            }
                            paramValues = paramValues + convertParam2Value(param);
                        }
                    }
                    invocation = invocation.replace(paramString, paramValues);
                    unitTestRes.add(invocation);
                    unitTestRes.add("}");
                    unitTestRes.add("\n");

                    unitTestRes.add("");
                    unitTestRes.add("@After");
                    unitTestRes.add("public void afterMethod() throws Exception {");
                    unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                    unitTestRes.add("}");
                    unitTestRes.add("}");

                    String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/MediaRecorder/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                    for (String unitTestStr : unitTestRes) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                        out.println(unitTestStr);
                        out.close();
                    }
                }
            }
        }
    }

    private static void processBluetoothGatt(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        if (s.contains("android.bluetooth.BluetoothManager")) {

            List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (String permission : permissionList) {
                    String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                    //check if method-permission exist
                    if (methodPermissionMap.contains(method + permission)) {
                        continue;
                    } else {
                        methodPermissionMap.add(method + permission);
                    }

                    IncrementHashMap.incrementValue(methodIncrementalSet, method);

                    List<String> unitTestRes = new ArrayList<>();

                    unitTestRes.add("package com.edu.permission.compatibilityissue.BluetoothManager;");
                    unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                    unitTestRes.add("import android.Manifest;");
                    unitTestRes.add("import android.content.Context;");
                    unitTestRes.add("import android.bluetooth.BluetoothManager;");
                    unitTestRes.add("import androidx.test.InstrumentationRegistry;");
                    unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                    unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                    unitTestRes.add("import org.junit.After;");
                    unitTestRes.add("import org.junit.Before;");
                    unitTestRes.add("import org.junit.Rule;");
                    unitTestRes.add("import org.junit.Test;");
                    unitTestRes.add("import org.junit.runner.RunWith;");
                    unitTestRes.add("");
                    unitTestRes.add("");
                    unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                    unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                    unitTestRes.add("private Context mContext;");
                    unitTestRes.add("private BluetoothManager mBluetoothManager;");
                    unitTestRes.add("");
                    unitTestRes.add("@Rule");
                    unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                    unitTestRes.add("");
                    unitTestRes.add("@Before");
                    unitTestRes.add("public void setUp() throws Exception {");
                    unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                    unitTestRes.add("mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);");
                    unitTestRes.add("}");
                    unitTestRes.add("");
                    unitTestRes.add("@Test");
                    unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                    String invocation = "mBluetoothManager." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                    String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                    List<String> params = Arrays.asList(paramString.split(","));
                    String paramValues = "";
                    if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                        for (String param : params) {
                            if (StringUtils.isNotBlank(paramValues)) {
                                paramValues = paramValues + ",";
                            }
                            paramValues = paramValues + convertParam2Value(param);
                        }
                    }
                    invocation = invocation.replace(paramString, paramValues);
                    unitTestRes.add(invocation);
                    unitTestRes.add("}");
                    unitTestRes.add("\n");

                    unitTestRes.add("");
                    unitTestRes.add("@After");
                    unitTestRes.add("public void afterMethod() throws Exception {");
                    unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                    unitTestRes.add("}");
                    unitTestRes.add("}");

                    String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/BluetoothManagerTest/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                    for (String unitTestStr : unitTestRes) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                        out.println(unitTestStr);
                        out.close();
                    }
                }
            }
        }
    }

    private static void processBluetoothManager(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        if (s.contains("android.bluetooth.BluetoothManager")) {

            List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (String permission : permissionList) {
                    String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                    //check if method-permission exist
                    if (methodPermissionMap.contains(method + permission)) {
                        continue;
                    } else {
                        methodPermissionMap.add(method + permission);
                    }

                    IncrementHashMap.incrementValue(methodIncrementalSet, method);

                    List<String> unitTestRes = new ArrayList<>();

                    unitTestRes.add("package com.edu.permission.compatibilityissue.BluetoothManager;");
                    unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                    unitTestRes.add("import android.Manifest;");
                    unitTestRes.add("import android.content.Context;");
                    unitTestRes.add("import android.bluetooth.BluetoothManager;");
                    unitTestRes.add("import androidx.test.InstrumentationRegistry;");
                    unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                    unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                    unitTestRes.add("import org.junit.After;");
                    unitTestRes.add("import org.junit.Before;");
                    unitTestRes.add("import org.junit.Rule;");
                    unitTestRes.add("import org.junit.Test;");
                    unitTestRes.add("import org.junit.runner.RunWith;");
                    unitTestRes.add("");
                    unitTestRes.add("");
                    unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                    unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                    unitTestRes.add("private Context mContext;");
                    unitTestRes.add("private BluetoothManager mBluetoothManager;");
                    unitTestRes.add("");
                    unitTestRes.add("@Rule");
                    unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                    unitTestRes.add("");
                    unitTestRes.add("@Before");
                    unitTestRes.add("public void setUp() throws Exception {");
                    unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                    unitTestRes.add("mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);");
                    unitTestRes.add("}");
                    unitTestRes.add("");
                    unitTestRes.add("@Test");
                    unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                    String invocation = "mBluetoothManager." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                    String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                    List<String> params = Arrays.asList(paramString.split(","));
                    String paramValues = "";
                    if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                        for (String param : params) {
                            if (StringUtils.isNotBlank(paramValues)) {
                                paramValues = paramValues + ",";
                            }
                            paramValues = paramValues + convertParam2Value(param);
                        }
                    }
                    invocation = invocation.replace(paramString, paramValues);
                    unitTestRes.add(invocation);
                    unitTestRes.add("}");
                    unitTestRes.add("\n");

                    unitTestRes.add("");
                    unitTestRes.add("@After");
                    unitTestRes.add("public void afterMethod() throws Exception {");
                    unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                    unitTestRes.add("}");
                    unitTestRes.add("}");

                    String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/BluetoothManagerTest/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                    for (String unitTestStr : unitTestRes) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                        out.println(unitTestStr);
                        out.close();
                    }
                }
            }
        }
    }

    private static void processTelephonyManager(Set<String> methodPermissionMap, HashMap<String, Integer> methodIncrementalSet, String s) throws IOException {
        if (s.contains("android.telephony.TelephonyManager")) {

            List<String> permissionList = Regex.getSubUtilSimpleList(s, "(android.permission.[a-zA-Z_]+)");
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (String permission : permissionList) {
                    String method = Regex.getSubUtilSimple(s, "([^ ]+\\()").replace("(", "").replace(" ", "");

                    //check if method-permission exist
                    if (methodPermissionMap.contains(method + permission)) {
                        continue;
                    } else {
                        methodPermissionMap.add(method + permission);
                    }

                    IncrementHashMap.incrementValue(methodIncrementalSet, method);

                    List<String> unitTestRes = new ArrayList<>();

                    unitTestRes.add("package com.edu.permission.compatibilityissue.TelephonyManager;");
                    unitTestRes.add("import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;");
                    unitTestRes.add("import android.Manifest;");
                    unitTestRes.add("import android.content.Context;");
                    unitTestRes.add("import android.telephony.TelephonyManager;");
                    unitTestRes.add("import androidx.test.InstrumentationRegistry;");
                    unitTestRes.add("import androidx.test.ext.junit.runners.AndroidJUnit4;");
                    unitTestRes.add("import androidx.test.rule.GrantPermissionRule;");
                    unitTestRes.add("import org.junit.After;");
                    unitTestRes.add("import org.junit.Before;");
                    unitTestRes.add("import org.junit.Rule;");
                    unitTestRes.add("import org.junit.Test;");
                    unitTestRes.add("import org.junit.runner.RunWith;");
                    unitTestRes.add("");
                    unitTestRes.add("");
                    unitTestRes.add("@RunWith(AndroidJUnit4.class)");
                    unitTestRes.add("public class " + method + "_" + methodIncrementalSet.get(method) + " {");
                    unitTestRes.add("private Context mContext;");
                    unitTestRes.add("private TelephonyManager mTelephonyManager;");
                    unitTestRes.add("");
                    unitTestRes.add("@Rule");
                    unitTestRes.add("public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(" + permission.replace("android.permission.", "Manifest.permission.") + ");");
                    unitTestRes.add("");
                    unitTestRes.add("@Before");
                    unitTestRes.add("public void setUp() throws Exception {");
                    unitTestRes.add("mContext = ApplicationProvider.getApplicationContext();");
                    unitTestRes.add("mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);");
                    unitTestRes.add("}");
                    unitTestRes.add("");
                    unitTestRes.add("@Test");
                    unitTestRes.add("public void " + method + "_" + methodIncrementalSet.get(method) + "() throws Exception {");


                    String invocation = "mTelephonyManager." + Regex.getSubUtilSimple(s, "([^ ]+\\))") + ";";
                    String paramString = Regex.getSubUtilSimple(invocation, "(\\(.*\\))").replace("(", "").replace(")", "");
                    List<String> params = Arrays.asList(paramString.split(","));
                    String paramValues = "";
                    if (StringUtils.isNotBlank(paramString) && CollectionUtils.isNotEmpty(params)) {
                        for (String param : params) {
                            if (StringUtils.isNotBlank(paramValues)) {
                                paramValues = paramValues + ",";
                            }
                            paramValues = paramValues + convertParam2Value(param);
                        }
                    }
                    invocation = invocation.replace(paramString, paramValues);
                    unitTestRes.add(invocation);
                    unitTestRes.add("}");
                    unitTestRes.add("\n");

                    unitTestRes.add("");
                    unitTestRes.add("@After");
                    unitTestRes.add("public void afterMethod() throws Exception {");
                    unitTestRes.add("getInstrumentation().getUiAutomation().executeShellCommand(\"pm reset-permissions \" +  mContext.getPackageName());");
                    unitTestRes.add("}");
                    unitTestRes.add("}");

                    String outputFile = "/Users/xsun0035/Desktop/AOSP_API_Permission/test/" + method + "_" + methodIncrementalSet.get(method) + ".java";
                    for (String unitTestStr : unitTestRes) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
                        out.println(unitTestStr);
                        out.close();
                    }
                }
            }
        }
    }

    private static String convertParam2Value(String paramType) {
        if (paramType.equals("boolean") || paramType.equals("Boolean")) {
            return "true";
        } else if (paramType.equals("int") || paramType.equals("Integer")) {
            return "1";
        } else if (paramType.equals("char")) {
            return "\"a\"";
        } else if (paramType.equals("double") || paramType.equals("Double")) {
            return "1.0";
        } else if (paramType.equals("float")) {
            return "1";
        } else if (paramType.equals("long") || paramType.equals("Long")) {
            return "1";
        } else if (paramType.equals("short") || paramType.equals("Short")) {
            return "(short)1";
        } else if (paramType.equals("String")) {
            return "\"xxx\"";
        } else if (paramType.equals("Character")) {
            return "\"a\"";
        }
        return "(Object)null";
    }

    private static String convertParam2ClassType(String paramType) throws IOException {
        if (paramType.equals("boolean") || paramType.equals("Boolean")) {
            return paramType+".class";
        } else if (paramType.equals("int") || paramType.equals("Integer") || paramType.equals("int[]")) {
            return paramType+".class";
        } else if (paramType.equals("[int")) {
            return "int[].class";
        } else if (paramType.equals("char")) {
            return paramType+".class";
        } else if (paramType.equals("byte") || paramType.equals("byte[]") ) {
            return paramType+".class";
        }else if (paramType.equals("[byte")) {
            return "byte[].class";
        } else if (paramType.equals("double") || paramType.equals("Double")) {
            return paramType+".class";
        } else if (paramType.equals("float")) {
            return paramType+".class";
        } else if (paramType.equals("long") || paramType.equals("Long") || paramType.equals("long[]")) {
            return paramType+".class";
        } else if (paramType.equals("short") || paramType.equals("Short")) {
            return paramType+".class";
        } else if (paramType.equals("String") || paramType.equals("String[]")) {
            return paramType+".class";
        } else if (paramType.equals("Character")) {
            return paramType+".class";
        } else if (paramType.equals("[float")) {
            return "float[].class";
        }

        HashMap<String, String> allAPIMap = AllAndroidAPIMap.run();
        if(allAPIMap.get(paramType) != null){
            return "Class.forName(\""+allAPIMap.get(paramType)+"\")";
        }else if(paramType.contains("List<")){
            return "List.class";
        }else if(paramType.contains("[]")){
            return "List.class";
        }else{
            System.out.println("[not find]"+paramType);
            return paramType+".class";
        }

    }

    private static String convertParam2ClassTypeWithClassType(String paramType, String clsType) throws IOException {
        if (paramType.equals("boolean") || paramType.equals("Boolean")) {
            return paramType+".class";
        } else if (paramType.equals("int") || paramType.equals("Integer") || paramType.equals("int[]")) {
            return paramType+".class";
        } else if (paramType.equals("[int")) {
            return "int[].class";
        } else if (paramType.equals("char")) {
            return paramType+".class";
        } else if (paramType.equals("byte") || paramType.equals("byte[]") ) {
            return paramType+".class";
        }else if (paramType.equals("[byte")) {
            return "byte[].class";
        } else if (paramType.equals("double") || paramType.equals("Double")) {
            return paramType+".class";
        } else if (paramType.equals("float")) {
            return paramType+".class";
        } else if (paramType.equals("long") || paramType.equals("Long") || paramType.equals("long[]")) {
            return paramType+".class";
        } else if (paramType.equals("short") || paramType.equals("Short")) {
            return paramType+".class";
        } else if (paramType.equals("String") || paramType.equals("String[]")) {
            return paramType+".class";
        } else if (paramType.equals("Character")) {
            return paramType+".class";
        } else if (paramType.equals("[float")) {
            return "float[].class";
        }

        HashMap<String, String> allAPIMap = AllAndroidAPIMap.run();
        if(allAPIMap.get(paramType) != null){
            return "Class.forName(\""+allAPIMap.get(paramType)+"\")";
        }else if(paramType.contains("List<")){
            return "List.class";
        }else if(paramType.contains("[]")){
            return "List.class";
        }else{
            System.out.println("[not find]"+paramType);
            return clsType + "." +paramType+".class";
        }

    }


    private static void getPermission(String filePath, Set<String> APIPermissionList) throws IOException {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        String currentAPI = "";
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            APIPermissionList.add(currentLine);
        }
    }
}
