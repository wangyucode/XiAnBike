package cn.wycode.bike.net;

import android.util.Xml;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;

import cn.wycode.bike.model.BikeStation;

/**
 * Created by wy
 * on 2016/12/30.
 */

public class XmlUtils {
    public static String getJsonString(String response){
        int start = response.indexOf("<ns1:out>");
        int end =  response.indexOf("</ns1:out>");

        return response.substring(start+9,end);
    }

    public static List<BikeStation> parserJson(String json){
        Gson gson  = new Gson();
        return gson.fromJson(json, new TypeToken<List<BikeStation>>(){}.getType());
    }


}
