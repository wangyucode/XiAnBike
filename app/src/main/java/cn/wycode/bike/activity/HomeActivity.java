package cn.wycode.bike.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.wycode.bike.BaseActivity;
import cn.wycode.bike.MyApplication;
import cn.wycode.bike.R;
import cn.wycode.bike.activity.fragement.BaseFragment;
import cn.wycode.bike.activity.fragement.HelpFragment;
import cn.wycode.bike.activity.fragement.MapFragment;
import cn.wycode.bike.activity.fragement.NearFragment;
import cn.wycode.bike.net.XmlUtils;
import cn.wycode.bike.view.BottomNavigationLayout;


/**
 *
 */
public class HomeActivity extends BaseActivity implements BottomNavigationLayout.OnTabSelectedListener, AMapLocationListener {

    @BindView(R.id.bomNavLay_home)
    BottomNavigationLayout bomNavLayHome;
    FragmentManager fm;
    private Fragment nearFm, mapFm, helpFm;
    private FragmentTransaction ft;

    private String[] titles = new String[]{"附近", "地图", "帮助"};

    public AMapLocationClient mLocationClient;

    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nearFm = new NearFragment();
        mapFm = new MapFragment();
        helpFm = new HelpFragment();
        setContentViewWithDefaultTitle(R.layout.activity_home, "附近");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        } else {
            mLocationClient = new AMapLocationClient(getApplicationContext());
            mLocationClient.setLocationListener(this);
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取最近3s内精度最高的一次定位结果：
            mLocationOption.setOnceLocationLatest(true);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        //初始化定位
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) return;
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(this);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                MyApplication.location = aMapLocation;
                EventBus.getDefault().post(aMapLocation);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                mLocationClient.startLocation();
            }
        }
    }

    @Override
    protected void initView() {
        mIvTitleBack.setVisibility(View.GONE);
        bomNavLayHome.setOnTabSelectedListener(this);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fl_home, helpFm, "2");
        ft.add(R.id.fl_home, mapFm, "1");
        ft.add(R.id.fl_home, nearFm, "0");
        ft.hide(mapFm);
        ft.hide(helpFm);
        ft.commit();

        int[] unselected = new int[]{R.drawable.ic_near, R.drawable.ic_map, R.drawable.ic_about};
        int[] selected = new int[]{R.drawable.ic_near_selected, R.drawable.ic_map_selected, R.drawable.ic_about_selected};
        bomNavLayHome.setupWithTitle(titles);
        bomNavLayHome.setTabImage(unselected, selected);
    }

    @Override
    public void onTabSelected(int position) {
        mTvTitleName.setText(titles[position]);
        ft = fm.beginTransaction();
        for (Fragment fragment : fm.getFragments()) {
            if (fragment.getTag().equals("" + position)) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }
}
