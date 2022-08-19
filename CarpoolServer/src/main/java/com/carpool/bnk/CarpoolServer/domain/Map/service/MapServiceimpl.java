package com.carpool.bnk.CarpoolServer.domain.Map.service;

import com.carpool.bnk.CarpoolServer.domain.Map.request.MapRouteReq;
import org.apache.tomcat.util.json.ParseException;
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

    static {
        try {
            keys = new Scanner(new File("/Users/dean/Desktop/BNK-pjt/CarpoolServer/src/main/java/com/carpool/bnk/CarpoolServer/domain/Map/service/SecretKey"))
                    .nextLine().split(":");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private String Appkey = keys[1];
    static private String AppSecret = keys[3];

    @Override
    public void test() throws Exception {
        System.out.println(Appkey+" " + AppSecret);

        MapRouteReq a = new MapRouteReq();
        a.setSLat("128.991215");
        a.setSLng("35.160786");
        a.setDLat("128.988500");
        a.setDLng("35.178021");

        StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving");
        urlBuilder.append("?" + URLEncoder.encode("start","UTF-8") + "=" + URLEncoder.encode(a.getSLat()+","+a.getSLng(), "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("goal","UTF-8") + "=" + URLEncoder.encode(a.getDLat()+","+a.getDLng(), "UTF-8"));
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

        JSONObject json = s2j(sb.toString());
        System.out.println(json.toString());





    }
    public static JSONObject s2j(String str) throws ParseException, org.json.simple.parser.ParseException {
        JSONObject ret = new JSONObject();
        JSONParser parser = new JSONParser();
        ret = (JSONObject) parser.parse(str);
        return ret;
    }
}
