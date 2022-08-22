package com.carpool.bnk.CarpoolServer.domain.Map.service;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

@Service
public class MapServiceimpl implements MapService{
    static private String[] keys;

    static private String itCenterLat = "128.857733";
    static private String itCenterLng = "35.137249";

    static {
        try {
            keys = new Scanner(new File("C:\\jjk\\BNK-pjt\\CarpoolServer\\src\\main\\java\\com\\carpool\\bnk\\CarpoolServer\\domain\\Map\\service\\SecretKey"))
                    .nextLine().split(":");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private String Appkey = keys[1];
    static private String AppSecret = keys[3];

    public static JSONObject s2j(String str) throws ParseException, org.json.simple.parser.ParseException {
        JSONObject ret = new JSONObject();
        JSONParser parser = new JSONParser();
        ret = (JSONObject) parser.parse(str);
        return ret;
    }

    @Override
    public String getRoute(String sLng, String sLat) throws Exception {
        StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving");
        urlBuilder.append("?" + URLEncoder.encode("start","UTF-8") + "=" + URLEncoder.encode(sLat+","+sLng, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("goal","UTF-8") + "=" + URLEncoder.encode(itCenterLat+","+itCenterLng, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("option","UTF-8") + "=" + URLEncoder.encode("traoptimal", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", Appkey);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", AppSecret);

        BufferedReader br;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        conn.disconnect();

        JSONObject res = s2j(sb.toString());

        try {
            JSONArray traoptimal = (JSONArray)((JSONObject)res.get("route")).get("traoptimal");
            String path = ((JSONObject)traoptimal.get(0)).get("path").toString();

            return path;
        }catch (Exception e){
            System.err.println("=================get Path From naver Err=================");
            System.err.println(e);
            System.err.println(res);
            return res.get("message").toString();
        }
    }
}