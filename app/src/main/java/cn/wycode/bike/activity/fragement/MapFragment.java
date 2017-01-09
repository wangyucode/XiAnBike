package cn.wycode.bike.activity.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import cn.wycode.bike.MyApplication;
import cn.wycode.bike.R;
import cn.wycode.bike.model.BikeStation;

/**
 * 地图
 * Created by wy on 2016/12/22.
 */
public class MapFragment extends BaseFragment implements LocationSource, AMap.OnCameraChangeListener, AMap.OnMapClickListener {
    @BindView(R.id.map)
    MapView mapView;

    private AMap aMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private OnLocationChangedListener mListener;

    private float radius = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        aMap = mapView.getMap();
        //设置希望展示的地图缩放级别
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(15);
        aMap.moveCamera(mCameraUpdate);
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        mUiSettings.setScaleControlsEnabled(true);
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMapClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocation(AMapLocation location) {
        mListener.onLocationChanged(location);// 显示系统小蓝点
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(15);
        aMap.moveCamera(mCameraUpdate);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        LatLngBounds screenBounds = aMap.getProjection().getMapBounds(latLng, 15);
        radius = AMapUtils.calculateLineDistance(screenBounds.northeast, latLng);
    }

    @Override
    public void getHttpData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        EventBus.getDefault().unregister(this);
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
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        List<BikeStation> stations = MyApplication.getNearStations(radius, cameraPosition.target);
        Log.d("wy", stations.size() + "");
        for (BikeStation station : stations) {
            String content = station.getLocation() + "\n"
                    + "可租：" + station.getLocknum() + "辆\n"
                    + "可还：" + station.getEmptynum() + "辆";
            aMap.addMarker(new MarkerOptions()
                    .position(new LatLng(station.getLatitude(), station.getLongitude()))
                    .title(station.getSitename())
                    .snippet(content));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        for (Marker marker : aMap.getMapScreenMarkers()) {
            if (marker.isInfoWindowShown()) {
                marker.hideInfoWindow();
            }
        }
    }
}