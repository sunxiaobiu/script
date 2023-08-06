package typestate.compatibility;

import util.Regex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class CompatibilityIssues {

    public static void main(String[] args) throws IOException {
        HashMap<String, HashSet<String>> res = new HashMap<>();
        HashMap<String, String> sdk1 = new HashMap<>();
        HashMap<String, String> sdk2 = new HashMap<>();
        HashMap<String, String> sdk3 = new HashMap<>();
        HashMap<String, String> sdk4 = new HashMap<>();
        HashMap<String, String> sdk5 = new HashMap<>();
        HashMap<String, String> sdk6 = new HashMap<>();
        HashMap<String, String> sdk7 = new HashMap<>();
        HashMap<String, String> sdk8 = new HashMap<>();
        HashMap<String, String> sdk9 = new HashMap<>();
        HashMap<String, String> sdk10 = new HashMap<>();
        HashMap<String, String> sdk11 = new HashMap<>();
        HashMap<String, String> sdk12 = new HashMap<>();
        HashMap<String, String> sdk13 = new HashMap<>();
        HashMap<String, String> sdk14 = new HashMap<>();
        HashMap<String, String> sdk15 = new HashMap<>();
        HashMap<String, String> sdk16 = new HashMap<>();
        HashMap<String, String> sdk17 = new HashMap<>();
        HashMap<String, String> sdk18 = new HashMap<>();
        HashMap<String, String> sdk19 = new HashMap<>();
        HashMap<String, String> sdk20 = new HashMap<>();
        HashMap<String, String> sdk21 = new HashMap<>();
        HashMap<String, String> sdk22 = new HashMap<>();
        HashMap<String, String> sdk23 = new HashMap<>();
        HashMap<String, String> sdk24 = new HashMap<>();
        HashMap<String, String> sdk25 = new HashMap<>();
        HashMap<String, String> sdk26 = new HashMap<>();
        HashMap<String, String> sdk27 = new HashMap<>();
        HashMap<String, String> sdk28 = new HashMap<>();
        HashMap<String, String> sdk29 = new HashMap<>();
        HashMap<String, String> sdk30 = new HashMap<>();
        HashMap<String, String> sdk31 = new HashMap<>();
        HashMap<String, String> sdk32 = new HashMap<>();


        getIllegalSentence(sdk1, "1");
        getIllegalSentence(sdk2, "2");
        getIllegalSentence(sdk3, "3");
        getIllegalSentence(sdk4, "4");
        getIllegalSentence(sdk5, "5");
        getIllegalSentence(sdk6, "6");
        getIllegalSentence(sdk7, "7");
        getIllegalSentence(sdk8, "8");
        getIllegalSentence(sdk9, "9");
        getIllegalSentence(sdk10, "10");
        getIllegalSentence(sdk11, "11");
        getIllegalSentence(sdk12, "12");
        getIllegalSentence(sdk13, "13");
        getIllegalSentence(sdk14, "14");
        getIllegalSentence(sdk15, "15");
        getIllegalSentence(sdk16, "16");
        getIllegalSentence(sdk17, "17");
        getIllegalSentence(sdk18, "18");
        getIllegalSentence(sdk19, "19");
        getIllegalSentence(sdk20, "20");
        getIllegalSentence(sdk21, "21");
        getIllegalSentence(sdk22, "22");
        getIllegalSentence(sdk23, "23");
        getIllegalSentence(sdk24, "24");
        getIllegalSentence(sdk25, "25");
        getIllegalSentence(sdk26, "26");
        getIllegalSentence(sdk27, "27");
        getIllegalSentence(sdk28, "28");
        getIllegalSentence(sdk29, "29");
        getIllegalSentence(sdk30, "30");
        getIllegalSentence(sdk31, "31");
        getIllegalSentence(sdk32, "32");

        Set<String> apiSet = new HashSet<>();
        apiSet.addAll(sdk1.keySet());
        apiSet.addAll(sdk2.keySet());
        apiSet.addAll(sdk3.keySet());
        apiSet.addAll(sdk4.keySet());
        apiSet.addAll(sdk5.keySet());
        apiSet.addAll(sdk6.keySet());
        apiSet.addAll(sdk7.keySet());
        apiSet.addAll(sdk8.keySet());
        apiSet.addAll(sdk9.keySet());
        apiSet.addAll(sdk10.keySet());
        apiSet.addAll(sdk11.keySet());
        apiSet.addAll(sdk12.keySet());
        apiSet.addAll(sdk13.keySet());
        apiSet.addAll(sdk14.keySet());
        apiSet.addAll(sdk15.keySet());
        apiSet.addAll(sdk16.keySet());
        apiSet.addAll(sdk17.keySet());
        apiSet.addAll(sdk18.keySet());
        apiSet.addAll(sdk19.keySet());
        apiSet.addAll(sdk20.keySet());
        apiSet.addAll(sdk21.keySet());
        apiSet.addAll(sdk22.keySet());
        apiSet.addAll(sdk23.keySet());
        apiSet.addAll(sdk24.keySet());
        apiSet.addAll(sdk25.keySet());
        apiSet.addAll(sdk26.keySet());
        apiSet.addAll(sdk27.keySet());
        apiSet.addAll(sdk28.keySet());
        apiSet.addAll(sdk29.keySet());
        apiSet.addAll(sdk30.keySet());
        apiSet.addAll(sdk31.keySet());
        apiSet.addAll(sdk32.keySet());

        /**
         * print all results
         */
        for (String api : apiSet) {
            HashSet<String> resSet = new HashSet<>();
            HashSet<String> resSetWithSDK = new HashSet<>();

            if(sdk1.get(api) != null){
                resSetWithSDK.add(sdk1.get(api)+"(1)");
            }
            if(sdk2.get(api) != null){
                resSetWithSDK.add(sdk2.get(api)+"(2)");
            }
            if(sdk3.get(api) != null){
                resSetWithSDK.add(sdk3.get(api)+"(3)");
            }
            if(sdk4.get(api) != null){
                resSetWithSDK.add(sdk4.get(api)+"(4)");
            }
            if(sdk5.get(api) != null){
                resSetWithSDK.add(sdk5.get(api)+"(5)");
            }
            if(sdk6.get(api) != null){
                resSetWithSDK.add(sdk6.get(api)+"(6)");
            }
            if(sdk7.get(api) != null){
                resSetWithSDK.add(sdk7.get(api)+"(7)");
            }
            if(sdk8.get(api) != null){
                resSetWithSDK.add(sdk8.get(api)+"(8)");
            }
            if(sdk9.get(api) != null){
                resSetWithSDK.add(sdk9.get(api)+"(9)");
            }
            if(sdk10.get(api) != null){
                resSetWithSDK.add(sdk10.get(api)+"(10)");
            }if(sdk11.get(api) != null){
                resSetWithSDK.add(sdk11.get(api)+"(11)");
            }if(sdk12.get(api) != null){
                resSetWithSDK.add(sdk12.get(api)+"(12)");
            }
            if(sdk13.get(api) != null){
                resSetWithSDK.add(sdk13.get(api)+"(13)");
            }
            if(sdk14.get(api) != null){
                resSetWithSDK.add(sdk14.get(api)+"(14)");
            }
            if(sdk15.get(api) != null){
                resSetWithSDK.add(sdk15.get(api)+"(15)");
            }
            if(sdk16.get(api) != null){
                resSetWithSDK.add(sdk16.get(api)+"(16)");
            }
            if(sdk17.get(api) != null){
                resSetWithSDK.add(sdk17.get(api)+"(17)");
            }
            if(sdk18.get(api) != null){
                resSetWithSDK.add(sdk18.get(api)+"(18)");
            }
            if(sdk19.get(api) != null){
                resSetWithSDK.add(sdk19.get(api)+"(19)");
            }
            if(sdk20.get(api) != null){
                resSetWithSDK.add(sdk20.get(api)+"(20)");
            }
            if(sdk21.get(api) != null){
                resSetWithSDK.add(sdk21.get(api)+"(21)");
            }
            if(sdk22.get(api) != null){
                resSetWithSDK.add(sdk22.get(api)+"(22)");
            }
            if(sdk23.get(api) != null){
                resSetWithSDK.add(sdk23.get(api)+"(23)");
            }
            if(sdk24.get(api) != null){
                resSetWithSDK.add(sdk24.get(api)+"(24)");
            }
            if(sdk25.get(api) != null){
                resSetWithSDK.add(sdk25.get(api)+"(25)");
            }
            if(sdk26.get(api) != null){
                resSetWithSDK.add(sdk26.get(api)+"(26)");
            }
            if(sdk27.get(api) != null){
                resSetWithSDK.add(sdk27.get(api)+"(27)");
            }
            if(sdk28.get(api) != null){
                resSetWithSDK.add(sdk28.get(api)+"(28)");
            }
            if(sdk29.get(api) != null){
                resSetWithSDK.add(sdk29.get(api)+"(29)");
            }
            if(sdk30.get(api) != null){
                resSetWithSDK.add(sdk30.get(api)+"(30)");
            }
            if(sdk31.get(api) != null){
                resSetWithSDK.add(sdk31.get(api)+"(31)");
            }
            if(sdk32.get(api) != null){
                resSetWithSDK.add(sdk32.get(api)+"(32)");
            }


            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/Users/xsun0035/Desktop/TypeState_ICSE2023/compatibility.txt", true)));
            out.println(api+ "--------"+ resSetWithSDK);
            out.close();
        }
        System.out.println("=========================");

        /**
         * print all results
         */
        int num = 0;
        for (String api : apiSet) {
            HashSet<String> resSet = new HashSet<>();
            HashSet<String> resSetWithSDK = new HashSet<>();

            if (!resSet.contains(sdk32.get(api))) {
                resSet.add(sdk32.get(api));
                resSetWithSDK.add(sdk32.get(api)+"(32)");
            }

            if (!resSet.contains(sdk31.get(api))) {
                resSet.add(sdk31.get(api));
                resSetWithSDK.add(sdk31.get(api)+"(31)");
            }

            if (!resSet.contains(sdk30.get(api))) {
                resSet.add(sdk30.get(api));
                resSetWithSDK.add(sdk30.get(api)+"(30)");
            }

            if (!resSet.contains(sdk29.get(api))) {
                resSet.add(sdk29.get(api));
                resSetWithSDK.add(sdk29.get(api)+"(29)");
            }

            if (!resSet.contains(sdk28.get(api))) {
                resSet.add(sdk28.get(api));
                resSetWithSDK.add(sdk28.get(api)+"(28)");
            }

            if (!resSet.contains(sdk27.get(api))) {
                resSet.add(sdk27.get(api));
                resSetWithSDK.add(sdk27.get(api)+"(27)");
            }

            if (!resSet.contains(sdk26.get(api))) {
                resSet.add(sdk26.get(api));
                resSetWithSDK.add(sdk26.get(api)+"(26)");
            }

            if (!resSet.contains(sdk25.get(api))) {
                resSet.add(sdk25.get(api));
                resSetWithSDK.add(sdk25.get(api)+"(25)");
            }

            if (!resSet.contains(sdk24.get(api))) {
                resSet.add(sdk24.get(api));
                resSetWithSDK.add(sdk24.get(api)+"(24)");
            }

            if (!resSet.contains(sdk23.get(api))) {
                resSet.add(sdk23.get(api));
                resSetWithSDK.add(sdk23.get(api)+"(23)");
            }

            if (!resSet.contains(sdk22.get(api))) {
                resSet.add(sdk22.get(api));
                resSetWithSDK.add(sdk22.get(api)+"(22)");
            }

            if (!resSet.contains(sdk21.get(api))) {
                resSet.add(sdk21.get(api));
                resSetWithSDK.add(sdk21.get(api)+"(21)");
            }

            if (!resSet.contains(sdk20.get(api))) {
                resSet.add(sdk20.get(api));
                resSetWithSDK.add(sdk20.get(api)+"(20)");
            }

            if (!resSet.contains(sdk19.get(api))) {
                resSet.add(sdk19.get(api));
                resSetWithSDK.add(sdk19.get(api)+"(19)");
            }

            if (!resSet.contains(sdk18.get(api))) {
                resSet.add(sdk18.get(api));
                resSetWithSDK.add(sdk18.get(api)+"(18)");
            }

            if (!resSet.contains(sdk17.get(api))) {
                resSet.add(sdk17.get(api));
                resSetWithSDK.add(sdk17.get(api)+"(17)");
            }

            if (!resSet.contains(sdk16.get(api))) {
                resSet.add(sdk16.get(api));
                resSetWithSDK.add(sdk16.get(api)+"(16)");
            }

            if (!resSet.contains(sdk15.get(api))) {
                resSet.add(sdk15.get(api));
                resSetWithSDK.add(sdk15.get(api)+"(15)");
            }

            if (!resSet.contains(sdk14.get(api))) {
                resSet.add(sdk14.get(api));
                resSetWithSDK.add(sdk14.get(api)+"(14)");
            }

            if (!resSet.contains(sdk13.get(api))) {
                resSet.add(sdk13.get(api));
                resSetWithSDK.add(sdk13.get(api)+"(13)");
            }

            if (!resSet.contains(sdk12.get(api))) {
                resSet.add(sdk12.get(api));
                resSetWithSDK.add(sdk12.get(api)+"(12)");
            }

            if (!resSet.contains(sdk11.get(api))) {
                resSet.add(sdk11.get(api));
                resSetWithSDK.add(sdk11.get(api)+"(11)");
            }

            if (!resSet.contains(sdk10.get(api))) {
                resSet.add(sdk10.get(api));
                resSetWithSDK.add(sdk10.get(api)+"(10)");
            }

            if (!resSet.contains(sdk9.get(api))) {
                resSet.add(sdk9.get(api));
                resSetWithSDK.add(sdk9.get(api)+"(9)");
            }

            if (!resSet.contains(sdk8.get(api))) {
                resSet.add(sdk8.get(api));
                resSetWithSDK.add(sdk8.get(api)+"(8)");
            }

            if (!resSet.contains(sdk7.get(api))) {
                resSet.add(sdk7.get(api));
                resSetWithSDK.add(sdk7.get(api)+"(7)");
            }

            if (!resSet.contains(sdk6.get(api))) {
                resSet.add(sdk6.get(api));
                resSetWithSDK.add(sdk6.get(api)+"(6)");
            }

            if (!resSet.contains(sdk5.get(api))) {
                resSet.add(sdk5.get(api));
                resSetWithSDK.add(sdk5.get(api)+"(5)");
            }

            if (!resSet.contains(sdk4.get(api))) {
                resSet.add(sdk4.get(api));
                resSetWithSDK.add(sdk4.get(api)+"(4)");
            }

            if (!resSet.contains(sdk3.get(api))) {
                resSet.add(sdk3.get(api));
                resSetWithSDK.add(sdk3.get(api)+"(3)");
            }

            if (!resSet.contains(sdk2.get(api))) {
                resSet.add(sdk2.get(api));
                resSetWithSDK.add(sdk2.get(api)+"(2)");
            }

            if (!resSet.contains(sdk1.get(api))) {
                resSet.add(sdk1.get(api));
                resSetWithSDK.add(sdk1.get(api)+"(1)");
            }

            if (resSet.size() > 1) {
                num ++;
                //System.out.println(api+ "--------"+ resSetWithSDK);
            }
        }

        /**
         * 排除null的结果
         */
//        int num = 0;
//        for (String api : apiSet) {
//            HashSet<String> resSet = new HashSet<>();
//            HashSet<String> resSetWithSDK = new HashSet<>();
//
//            if (!resSet.contains(sdk32.get(api)) && sdk32.get(api) != null) {
//                resSet.add(sdk32.get(api));
//                resSetWithSDK.add(sdk32.get(api)+"(32)");
//            }
//
//            if (!resSet.contains(sdk31.get(api)) && sdk31.get(api) != null) {
//                resSet.add(sdk31.get(api));
//                resSetWithSDK.add(sdk31.get(api)+"(31)");
//            }
//
//            if (!resSet.contains(sdk30.get(api)) && sdk30.get(api) != null) {
//                resSet.add(sdk30.get(api));
//                resSetWithSDK.add(sdk30.get(api)+"(30)");
//            }
//
//            if (!resSet.contains(sdk29.get(api)) && sdk29.get(api) != null) {
//                resSet.add(sdk29.get(api));
//                resSetWithSDK.add(sdk29.get(api)+"(29)");
//            }
//
//            if (!resSet.contains(sdk28.get(api)) && sdk28.get(api) != null) {
//                resSet.add(sdk28.get(api));
//                resSetWithSDK.add(sdk28.get(api)+"(28)");
//            }
//
//            if (!resSet.contains(sdk27.get(api)) && sdk27.get(api) != null) {
//                resSet.add(sdk27.get(api));
//                resSetWithSDK.add(sdk27.get(api)+"(27)");
//            }
//
//            if (!resSet.contains(sdk26.get(api)) && sdk26.get(api) != null) {
//                resSet.add(sdk26.get(api));
//                resSetWithSDK.add(sdk26.get(api)+"(26)");
//            }
//
//            if (!resSet.contains(sdk25.get(api)) && sdk25.get(api) != null) {
//                resSet.add(sdk25.get(api));
//                resSetWithSDK.add(sdk25.get(api)+"(25)");
//            }
//
//            if (!resSet.contains(sdk24.get(api)) && sdk24.get(api) != null) {
//                resSet.add(sdk24.get(api));
//                resSetWithSDK.add(sdk24.get(api)+"(24)");
//            }
//
//            if (!resSet.contains(sdk23.get(api)) && sdk23.get(api) != null) {
//                resSet.add(sdk23.get(api));
//                resSetWithSDK.add(sdk23.get(api)+"(23)");
//            }
//
//            if (!resSet.contains(sdk22.get(api)) && sdk22.get(api) != null) {
//                resSet.add(sdk22.get(api));
//                resSetWithSDK.add(sdk22.get(api)+"(22)");
//            }
//
//            if (!resSet.contains(sdk21.get(api)) && sdk21.get(api) != null) {
//                resSet.add(sdk21.get(api));
//                resSetWithSDK.add(sdk21.get(api)+"(21)");
//            }
//
//            if (!resSet.contains(sdk20.get(api)) && sdk20.get(api) != null) {
//                resSet.add(sdk20.get(api));
//                resSetWithSDK.add(sdk20.get(api)+"(20)");
//            }
//
//            if (!resSet.contains(sdk19.get(api)) && sdk19.get(api) != null) {
//                resSet.add(sdk19.get(api));
//                resSetWithSDK.add(sdk19.get(api)+"(19)");
//            }
//
//            if (!resSet.contains(sdk18.get(api)) && sdk18.get(api) != null) {
//                resSet.add(sdk18.get(api));
//                resSetWithSDK.add(sdk18.get(api)+"(18)");
//            }
//
//            if (!resSet.contains(sdk17.get(api)) && sdk17.get(api) != null) {
//                resSet.add(sdk17.get(api));
//                resSetWithSDK.add(sdk17.get(api)+"(17)");
//            }
//
//            if (!resSet.contains(sdk16.get(api)) && sdk16.get(api) != null) {
//                resSet.add(sdk16.get(api));
//                resSetWithSDK.add(sdk16.get(api)+"(16)");
//            }
//
//            if (!resSet.contains(sdk15.get(api)) && sdk15.get(api) != null) {
//                resSet.add(sdk15.get(api));
//                resSetWithSDK.add(sdk15.get(api)+"(15)");
//            }
//
//            if (!resSet.contains(sdk14.get(api)) && sdk14.get(api) != null) {
//                resSet.add(sdk14.get(api));
//                resSetWithSDK.add(sdk14.get(api)+"(14)");
//            }
//
//            if (!resSet.contains(sdk13.get(api)) && sdk13.get(api) != null) {
//                resSet.add(sdk13.get(api));
//                resSetWithSDK.add(sdk13.get(api)+"(13)");
//            }
//
//            if (!resSet.contains(sdk12.get(api)) && sdk12.get(api) != null) {
//                resSet.add(sdk12.get(api));
//                resSetWithSDK.add(sdk12.get(api)+"(12)");
//            }
//
//            if (!resSet.contains(sdk11.get(api)) && sdk11.get(api) != null) {
//                resSet.add(sdk11.get(api));
//                resSetWithSDK.add(sdk11.get(api)+"(11)");
//            }
//
//            if (!resSet.contains(sdk10.get(api)) && sdk10.get(api) != null) {
//                resSet.add(sdk10.get(api));
//                resSetWithSDK.add(sdk10.get(api)+"(10)");
//            }
//
//            if (!resSet.contains(sdk9.get(api)) && sdk9.get(api) != null) {
//                resSet.add(sdk9.get(api));
//                resSetWithSDK.add(sdk9.get(api)+"(9)");
//            }
//
//            if (!resSet.contains(sdk8.get(api)) && sdk8.get(api) != null) {
//                resSet.add(sdk8.get(api));
//                resSetWithSDK.add(sdk8.get(api)+"(8)");
//            }
//
//            if (!resSet.contains(sdk7.get(api)) && sdk7.get(api) != null) {
//                resSet.add(sdk7.get(api));
//                resSetWithSDK.add(sdk7.get(api)+"(7)");
//            }
//
//            if (!resSet.contains(sdk6.get(api)) && sdk6.get(api) != null) {
//                resSet.add(sdk6.get(api));
//                resSetWithSDK.add(sdk6.get(api)+"(6)");
//            }
//
//            if (!resSet.contains(sdk5.get(api)) && sdk5.get(api) != null) {
//                resSet.add(sdk5.get(api));
//                resSetWithSDK.add(sdk5.get(api)+"(5)");
//            }
//
//            if (!resSet.contains(sdk4.get(api)) && sdk4.get(api) != null) {
//                resSet.add(sdk4.get(api));
//                resSetWithSDK.add(sdk4.get(api)+"(4)");
//            }
//
//            if (!resSet.contains(sdk3.get(api)) && sdk3.get(api) != null) {
//                resSet.add(sdk3.get(api));
//                resSetWithSDK.add(sdk3.get(api)+"(3)");
//            }
//
//            if (!resSet.contains(sdk2.get(api)) && sdk2.get(api) != null) {
//                resSet.add(sdk2.get(api));
//                resSetWithSDK.add(sdk2.get(api)+"(2)");
//            }
//
//            if (!resSet.contains(sdk1.get(api)) && sdk1.get(api) != null) {
//                resSet.add(sdk1.get(api));
//                resSetWithSDK.add(sdk1.get(api)+"(1)");
//            }
//
//            if (resSet.size() > 1) {
//                num ++;
//                System.out.println(api+ "--------"+ resSetWithSDK);
//            }
//        }
        System.out.println(num);


    }

    private static void getIllegalSentence(HashMap<String, String> sdkHashMap, String sdkVersion) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/TypeState_ICSE2023/compatibility/SDK1-32/illegalSentence_All_SDK/illegalSentence_SDK" + sdkVersion + ".txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        String currentAPI = "";
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String api = Regex.getSubUtilSimple(currentLine, "(<.*>::)");
            String illegalSentence = currentLine.replace(api, "");

            sdkHashMap.put(api, illegalSentence);
        }
    }
}
