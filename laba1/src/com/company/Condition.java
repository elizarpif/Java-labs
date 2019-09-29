package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Condition {

    private String fileName;

    Condition(String s){
        fileName = s;
    }

    public void createCondition(){
        JSONObject obj = new JSONObject();
        obj.put("left", "[");
        obj.put("right", "]");
        JSONArray arr = new JSONArray();
        arr.add(obj);
        JSONObject obj2 = new JSONObject();
        obj2.put("left", "{");
        obj2.put("right", "}");
        arr.add(obj2);
        JSONObject obj3 = new JSONObject();
        obj3.put("bracket", arr);
        try (FileWriter f = new FileWriter(fileName)){
            f.write(obj3.toJSONString());
            f.flush();
            f.close();
        }
        catch(IOException e){
            System.out.println("error");
        }
    }

    public void readCondition( Map<String, String> leftBracket, Map<String, String> rightBracket){
        JSONParser parser = new JSONParser();
        try{
            JSONObject obj = (JSONObject) parser.parse(new FileReader(fileName));
            JSONArray bracket = (JSONArray) obj.get("bracket");


            Iterator<JSONObject> it = bracket.iterator();
            while(it.hasNext()){
                JSONObject ob = it.next();
                String l = (String) ob.get("left");
                String r = (String) ob.get("right");
                if (rightBracket.get(r) == null){
                    leftBracket.put(l,r);
                    rightBracket.put(r,l);
                }
            }

        }
        catch (IOException | ParseException p){
            System.out.println("error in reading");
        }
    }
}
