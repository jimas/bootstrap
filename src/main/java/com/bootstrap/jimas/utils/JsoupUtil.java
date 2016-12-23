package com.bootstrap.jimas.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JsoupUtil {
    private static final String weatherURL="http://m.weather.com.cn/mweather/";
    private static final String weatherFix=".shtml";
    public static Document getDocument(String cityId){
        Document doc=null;
        try {
            doc=Jsoup.connect(weatherURL+cityId+weatherFix).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
    
    
    public static void main(String[] args) {
        String cityId="101010100";
        Document document = JsoupUtil.getDocument(cityId);
        Element body = document.body();
        Element elementById = document.getElementById("layout");
        System.out.println(elementById);
    }
}
