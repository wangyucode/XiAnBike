package cn.wycode.bike.net;

import com.alibaba.fastjson.JSON;

import java.util.List;

import cn.wycode.bike.model.BikeStation;

/**
 * Created by wy
 * on 2016/12/30.
 */

public class XmlUtils {
    public static String getJsonString(String response) {
        int start = response.indexOf("<ns1:out>");
        int end = response.indexOf("</ns1:out>");

        return response.substring(start + 9, end);
    }

    public static List<BikeStation> parserJson(String json) {
        return JSON.parseArray(json, BikeStation.class);
    }


}
