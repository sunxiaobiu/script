package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ClsSet;
import model.CompareResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.ApplicationClassFilter;
import util.ReadJsonFromFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class CompareJson {

    public static CompareResult run(String[] args) {
        CompareResult compareResult = new CompareResult();

        String leftJsonFilePath = args[0];
        String rightJsonFilePath = args[1];

        JSONObject leftJson = ReadJsonFromFile.trigger(leftJsonFilePath);
        JSONObject rightJson = ReadJsonFromFile.trigger(rightJsonFilePath);

        try {

            JSONArray actualLeftJson = (JSONArray) leftJson.get(DroidRAConstants.ITEMS);
            JSONArray actualRightJson = (JSONArray) rightJson.get(DroidRAConstants.ITEMS);

            Map<DroidRAKey, JSONObject> leftJsonObjectMap = convertToMap(actualLeftJson);
            Map<DroidRAKey, JSONObject> rightJsonObjectMap = convertToMap(actualRightJson);

            HashSet<DroidRAKey> missingDroidRAKey = new HashSet<DroidRAKey>(leftJsonObjectMap.keySet());
            missingDroidRAKey.addAll(rightJsonObjectMap.keySet());
            missingDroidRAKey.removeAll(rightJsonObjectMap.keySet());

            HashSet<DroidRAKey> newDroidRAKey = new HashSet<DroidRAKey>(rightJsonObjectMap.keySet());
            newDroidRAKey.addAll(leftJsonObjectMap.keySet());
            newDroidRAKey.removeAll(leftJsonObjectMap.keySet());

            if(missingDroidRAKey.size() > 0){
                System.out.println("-------------missingDroidRAKey:"+missingDroidRAKey);
                System.out.println("-------------newDroidRAKey:"+newDroidRAKey);
                System.out.println("-------------leftJson:"+leftJson);
            }
//            JsonNode leftJsonObj = mapper.readTree(leftJson.toString());
//            JsonNode rightJsonObj = mapper.readTree(rightJson.toString());
//
//            Assert.assertEquals(leftJsonObj, rightJsonObj);

            //construct result
            compareResult.setActualLeftJson(actualLeftJson);
            compareResult.setActualRightJson(actualRightJson);
            compareResult.setMissingDroidRAKey(missingDroidRAKey);
            compareResult.setNewDroidRAKey(newDroidRAKey);
            compareResult.setCompareEnum(CompareEnum.NONE);

            if(missingDroidRAKey.size() == 0 && newDroidRAKey.size() == 0){
                compareResult.setCompareEnum(CompareEnum.EQUAL);
                return compareResult;
            }

            if(missingDroidRAKey.size() > 0 && newDroidRAKey.size() == 0){
                compareResult.setCompareEnum(CompareEnum.DECREASING);
                return compareResult;
            }

            if(missingDroidRAKey.size() == 0 && newDroidRAKey.size()> 0){
                compareResult.setCompareEnum(CompareEnum.INCREASING);
                return compareResult;
            }

            if(missingDroidRAKey.size() > 0 && newDroidRAKey.size() > 0){
                compareResult.setCompareEnum(CompareEnum.BOTH_DEC_INC);
                return compareResult;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return compareResult;
    }

    private static Map<DroidRAKey, JSONObject>  convertToMap(JSONArray json) throws IOException {
        Map<DroidRAKey, JSONObject> jsonObjectMap = new HashMap<DroidRAKey, JSONObject>();

        Iterator iter = json.iterator();
        while (iter.hasNext()){
            JSONObject value = (JSONObject)iter.next();

            if(!ApplicationClassFilter.isApplicationClass(value.get(DroidRAConstants.CLASS_NAME).toString())){
                continue;
            }

//            ObjectMapper mapper = new ObjectMapper();
//            ClsSet[] clsSets = mapper.readValue(value.get(DroidRAConstants.CLS_SET).toString(), ClsSet[].class);
//            if(clsSets.length == 0){
//                continue;
//            }

            DroidRAKey droidRAKey = new DroidRAKey();
            droidRAKey.className = value.get(DroidRAConstants.CLASS_NAME).toString();
            droidRAKey.methodSignature = value.get(DroidRAConstants.METHOD_SIGNATURE).toString();
            droidRAKey.type = value.get(DroidRAConstants.TYPE).toString();
            //droidRAKey.stmtSeq = value.get(DroidRAConstants.STMT_SEQ).toString();

            jsonObjectMap.put(droidRAKey, value);
        }

        return jsonObjectMap;
    }

}
