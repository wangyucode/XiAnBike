package cn.wycode.bike.activity;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import butterknife.BindView;
import cn.wycode.bike.BaseActivity;
import cn.wycode.bike.MyApplication;
import cn.wycode.bike.R;
import cn.wycode.bike.model.BikeStation;

/**
 * Created by wy
 * on 2017/1/9.
 */

public class MapActivity extends BaseActivity implements LocationSource {

    public static final String STATION_KEY = "STATION_KEY";

    @BindView(R.id.map)
    MapView mapView;

    private AMap aMap;

    private BikeStation station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithDefaultTitle(R.layout.fragment_map, "地图");
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        station = (BikeStation) getIntent().getSerializableExtra(STATION_KEY);
        aMap = mapView.getMap();
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        if (MyApplication.location != null) {
            onLocationChangedListener.onLocationChanged(MyApplication.location);
        }
        CameraUpdate mCameraUpdate;
        if (station != null) {
            String content = station.getLocation() + "\n"
                    + "可租：" + station.getLocknum() + "辆\n"
                    + "可还：" + station.getEmptynum() + "辆";
            LatLng position = new LatLng(station.getLatitude(), station.getLongitude());
            aMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(station.getSitename())
                    .snippet(content));
            mCameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 15);
        } else {
            mCameraUpdate = CameraUpdateFactory.zoomTo(15);
        }
        aMap.moveCamera(mCameraUpdate);
    }

    @Override
    public void deactivate() {

    }
}
