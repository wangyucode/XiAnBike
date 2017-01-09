package cn.wycode.bike;

import android.app.Application;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import cn.wycode.bike.model.BikeStation;
import cn.wycode.bike.net.OKhttpUtils;

/**
 * 主要用作第三方初始化
 * Created by wy on 2015/10/15.
 */
public class MyApplication extends Application {


    public static List<BikeStation> stations = new ArrayList<>();
    public static CoordinateConverter converter;

    public static AMapLocation location;
    
    @Override
    public void onCreate() {
        super.onCreate();
        OKhttpUtils.init(this);
        converter = new CoordinateConverter(this).from(CoordinateConverter.CoordType.BAIDU);
    }

    public static List<BikeStation> getNearStations(float radius, LatLng target) {
        List<BikeStation> stationsNear = new ArrayList<>();
        for (BikeStation station : stations) {
            float distance = AMapUtils.calculateLineDistance(target, new LatLng(station.getLatitude(), station.getLongitude()));
            if (distance < radius) {
                stationsNear.add(station);
            }
        }
        return stationsNear;
    }


    public static void convert() {
        for (BikeStation station : stations) {
            LatLng latlng = converter.coord(new LatLng(station.getLatitude(),station.getLongitude())).convert();
            station.setLatitude(latlng.latitude);
            station.setLongitude(latlng.longitude);
        }
    }
}
