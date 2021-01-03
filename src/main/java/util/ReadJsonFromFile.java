package util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class ReadJsonFromFile {

    public static JSONObject trigger(String leftJsonFilePath) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try
        {
            FileReader reader = new FileReader(leftJsonFilePath);

            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            //System.out.println(leftJson);
            return jsonObject;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }

}
