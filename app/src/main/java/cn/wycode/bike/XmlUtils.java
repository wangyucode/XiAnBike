package cn.wycode.bike;

import android.util.Xml;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;

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

    class BikeStation{
        public int getEmptynum() {
            return emptynum;
        }

        public void setEmptynum(int emptynum) {
            this.emptynum = emptynum;
        }

        public int getSiteid() {
            return siteid;
        }

        public void setSiteid(int siteid) {
            this.siteid = siteid;
        }

        public int getLocknum() {
            return locknum;
        }

        public void setLocknum(int locknum) {
            this.locknum = locknum;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSitename() {
            return sitename;
        }

        public void setSitename(String sitename) {
            this.sitename = sitename;
        }

        private int emptynum;
        private int siteid;
        private int locknum;
        private double latitude;
        private double longitude;
        private String location;
        private String sitename;


    }
}
