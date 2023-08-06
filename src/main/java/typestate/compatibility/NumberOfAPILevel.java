package typestate.compatibility;

import util.Regex;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumberOfAPILevel {

    public static void main(String[] args) throws IOException {
        List<String> sdk1 = new ArrayList<>();
        List<String> sdk2 = new ArrayList<>();
        List<String> sdk3 = new ArrayList<>();
        List<String> sdk4 = new ArrayList<>();
        List<String> sdk5 = new ArrayList<>();
        List<String> sdk6 = new ArrayList<>();
        List<String> sdk7 = new ArrayList<>();
        List<String> sdk8 = new ArrayList<>();
        List<String> sdk9 = new ArrayList<>();
        List<String> sdk10 = new ArrayList<>();
        List<String> sdk11 = new ArrayList<>();
        List<String> sdk12 = new ArrayList<>();
        List<String> sdk13 = new ArrayList<>();
        List<String> sdk14 = new ArrayList<>();
        List<String> sdk15 = new ArrayList<>();
        List<String> sdk16 = new ArrayList<>();
        List<String> sdk17 = new ArrayList<>();
        List<String> sdk18 = new ArrayList<>();
        List<String> sdk19 = new ArrayList<>();
        List<String> sdk20 = new ArrayList<>();
        List<String> sdk21 = new ArrayList<>();
        List<String> sdk22 = new ArrayList<>();
        List<String> sdk23 = new ArrayList<>();
        List<String> sdk24 = new ArrayList<>();
        List<String> sdk25 = new ArrayList<>();
        List<String> sdk26 = new ArrayList<>();
        List<String> sdk27 = new ArrayList<>();
        List<String> sdk28 = new ArrayList<>();
        List<String> sdk29 = new ArrayList<>();
        List<String> sdk30 = new ArrayList<>();
        List<String> sdk31 = new ArrayList<>();
        List<String> sdk32 = new ArrayList<>();

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

        System.out.println("API level 1:"+sdk1.size()+";0");
        System.out.println("API level 2:"+getAddNum(sdk1, sdk2)+";"+getRemoveNum(sdk1, sdk2));
        System.out.println("API level 3:"+getAddNum(sdk2, sdk3)+";"+getRemoveNum(sdk2, sdk3));
        System.out.println("API level 4:"+getAddNum(sdk3, sdk4)+";"+getRemoveNum(sdk3, sdk4));
        System.out.println("API level 5:"+getAddNum(sdk4, sdk5)+";"+getRemoveNum(sdk4, sdk5));
        System.out.println("API level 6:"+getAddNum(sdk5, sdk6)+";"+getRemoveNum(sdk5, sdk6));
        System.out.println("API level 7:"+getAddNum(sdk6, sdk7)+";"+getRemoveNum(sdk6, sdk7));
        System.out.println("API level 8:"+getAddNum(sdk7, sdk8)+";"+getRemoveNum(sdk7, sdk8));
        System.out.println("API level 9:"+getAddNum(sdk8, sdk9)+";"+getRemoveNum(sdk8, sdk9));
        System.out.println("API level 10:"+getAddNum(sdk9, sdk10)+";"+getRemoveNum(sdk9, sdk10));
        System.out.println("API level 11:"+getAddNum(sdk10, sdk11)+";"+getRemoveNum(sdk10, sdk11));
        System.out.println("API level 12:"+getAddNum(sdk11, sdk12)+";"+getRemoveNum(sdk11, sdk12));
        System.out.println("API level 13:"+getAddNum(sdk12, sdk13)+";"+getRemoveNum(sdk12, sdk13));
        System.out.println("API level 14:"+getAddNum(sdk13, sdk14)+";"+getRemoveNum(sdk13, sdk14));
        System.out.println("API level 15:"+getAddNum(sdk14, sdk15)+";"+getRemoveNum(sdk14, sdk15));
        System.out.println("API level 16:"+getAddNum(sdk15, sdk16)+";"+getRemoveNum(sdk15, sdk16));
        System.out.println("API level 17:"+getAddNum(sdk16, sdk17)+";"+getRemoveNum(sdk16, sdk17));
        System.out.println("API level 18:"+getAddNum(sdk17, sdk18)+";"+getRemoveNum(sdk17, sdk18));
        System.out.println("API level 19:"+getAddNum(sdk18, sdk19)+";"+getRemoveNum(sdk18, sdk19));
        System.out.println("API level 20:"+getAddNum(sdk19, sdk20)+";"+getRemoveNum(sdk19, sdk20));
        System.out.println("API level 21:"+getAddNum(sdk20, sdk21)+";"+getRemoveNum(sdk20, sdk21));
        System.out.println("API level 22:"+getAddNum(sdk21, sdk22)+";"+getRemoveNum(sdk21, sdk22));
        System.out.println("API level 23:"+getAddNum(sdk22, sdk23)+";"+getRemoveNum(sdk22, sdk23));
        System.out.println("API level 24:"+getAddNum(sdk23, sdk24)+";"+getRemoveNum(sdk23, sdk24));
        System.out.println("API level 25:"+getAddNum(sdk24, sdk25)+";"+getRemoveNum(sdk24, sdk25));
        System.out.println("API level 26:"+getAddNum(sdk25, sdk26)+";"+getRemoveNum(sdk25, sdk26));
        System.out.println("API level 27:"+getAddNum(sdk26, sdk27)+";"+getRemoveNum(sdk26, sdk27));
        System.out.println("API level 28:"+getAddNum(sdk27, sdk28)+";"+getRemoveNum(sdk27, sdk28));
        System.out.println("API level 29:"+getAddNum(sdk28, sdk29)+";"+getRemoveNum(sdk28, sdk29));
        System.out.println("API level 30:"+getAddNum(sdk29, sdk30)+";"+getRemoveNum(sdk29, sdk30));
        System.out.println("API level 31:"+getAddNum(sdk30, sdk31)+";"+getRemoveNum(sdk30, sdk31));
        System.out.println("API level 32:"+getAddNum(sdk31, sdk32)+";"+getRemoveNum(sdk31, sdk32));

    }

    private static void getIllegalSentence(List<String> apiList, String sdkVersion) throws IOException {
        File file = new File("/Users/xsun0035/Desktop/TypeState_ICSE2023/compatibility/SDK1-32/illegalSentence_All_SDK/illegalSentence_SDK" + sdkVersion + ".txt");
        List<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));

        String currentAPI = "";
        for (int i = 0; i < fileContent.size(); i++) {
            String currentLine = fileContent.get(i);

            String api = Regex.getSubUtilSimple(currentLine, "(<.*>)");
            String illegalSentence = currentLine.replace(api, "");

            apiList.add(api);
        }
    }

    //统计afterSDK有，且， beforeSDK没有的
    private static int getAddNum(List<String> beforeSDK, List<String> afterSDK){
        int num  = 0;
        for(String afterStr : afterSDK){
            if(!beforeSDK.contains(afterStr)){
                num ++;
            }
        }
        return num;
    }

    //统计afterSDK没有，且， beforeSDK有的
    private static int getRemoveNum(List<String> beforeSDK, List<String> afterSDK){
        int num  = 0;
        for(String beforeStr : beforeSDK){
            if(!afterSDK.contains(beforeStr)){
                num ++;
            }
        }
        return num;
    }
}
