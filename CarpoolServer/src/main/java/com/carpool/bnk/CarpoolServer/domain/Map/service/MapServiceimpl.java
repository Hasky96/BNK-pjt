package com.carpool.bnk.CarpoolServer.domain.Map.service;

import com.carpool.bnk.CarpoolServer.domain.Map.dto.Location;
import com.carpool.bnk.CarpoolServer.domain.Map.dto.Station;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.json.ParseException;
import org.apache.xmlbeans.impl.regex.Match;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MapServiceimpl implements MapService{
    static private String[] keys;

    static private String itCenterLat = "128.857733";
    static private String itCenterLng = "35.137249";

    static {
        try {
            keys = new Scanner(new File(System.getProperty("user.dir")+"/src/main/resources/static/SecretKey"))
                    .nextLine().split(":");
        } catch (Exception e) {
            System.err.println();
            e.printStackTrace();
        }
    }

    static private Map<String, Station> stMap;

    static{
        try{
            String filePath = System.getProperty("user.dir")+"/src/main/resources/static/metroInfo.xlsx";
            InputStream inputStream = new FileInputStream(filePath);

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet("data");
            int lastRow = sheet.getLastRowNum();

            stMap = new HashMap<>();
            for (int i = 0; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                stMap.put(row.getCell(1).getStringCellValue(),
                        new Station(getCell(row.getCell(10)),
                                getCell(row.getCell(9)),
                                getCell(row.getCell(12)).replace("지하 ", "").replace("지하","")));
            }
//            System.out.println(" st " + stMap.toString());

        }catch (Exception e){
            System.err.println(e);
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

    @Override
    public List<Location> getLocations(String query) throws Exception {
//        System.err.println(query);
        List<Location> list = new ArrayList<>();

        Pattern pattern = Pattern.compile(".*\\s?[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|0-9]+역\\s?.*");
        Matcher matcher = pattern.matcher(query);

        while(matcher.find()){
            Station temp = stMap.get(matcher.group().trim().replace("역", ""));
            if(temp==null) continue;
            query = temp.getLoca();
        }

        StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode");
        urlBuilder.append("?" + URLEncoder.encode("query","UTF-8") + "=" + URLEncoder.encode(query, "UTF-8"));

        URL url = new URL(urlBuilder.toString());
//        System.err.println(url.toString());
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
//        System.out.println(res);

        try {
            JSONArray locas = (JSONArray) res.get("addresses");
            for (Object obj:locas) {
                JSONObject temp = (JSONObject)obj;
                list.add(new Location(temp.get("roadAddress").toString(), (String) temp.get("jibunAddress"), (String) temp.get("x"), (String) temp.get("y")));
            }

        }catch (Exception e){
            System.err.println("=================get Geocode From naver Err=================");
            System.err.println(e);
            System.err.println(res.get("errorMessage"));
        }


        return list;
    }

    public static String getCell(Cell cell){
        if(cell == null) return "";
        switch (cell.getCellType()){
            case BLANK:
                return " ";
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            default:
                return "";
        }
    }
}
