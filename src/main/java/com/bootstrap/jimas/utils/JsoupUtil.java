package com.bootstrap.jimas.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
    
}
