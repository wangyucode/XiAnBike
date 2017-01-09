package cn.wycode.bike.activity.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;

import butterknife.BindView;
import cn.wycode.bike.Constants;
import cn.wycode.bike.MyApplication;
import cn.wycode.bike.R;
import cn.wycode.bike.activity.MapActivity;
import cn.wycode.bike.adapter.StationAdapter;
import cn.wycode.bike.model.BikeStation;
import cn.wycode.bike.model.LocationComparator;
import cn.wycode.bike.net.XmlUtils;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 附近
 * Created by wy on 2016/12/22.
 */

public class NearFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_near)
    ListView lvNear;


    private StationAdapter adapter;
    private AMapLocation location;


    @Override
    protected void initView() {
        adapter = new StationAdapter(mContext, MyApplication.stations, R.layout.item_near);
        lvNear.setAdapter(adapter);
        lvNear.setOnItemClickListener(this);

    }

    @Override
    public void getHttpData() {
        RequestBody body = RequestBody.create(MediaType.parse("*/*"), Constants.body);
        OkGo.post(Constants.url).requestBody(body).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

                Log.d("wy", XmlUtils.getJsonString(s));
                MyApplication.stations = XmlUtils.parserJson(XmlUtils.getJsonString(s));
                MyApplication.convert();
                if (location != null) {
                    onLocation(location);
                }
                adapter.setList(MyApplication.stations);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_near);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onLocation(AMapLocation location) {
        this.location = location;
        LatLng now = new LatLng(location.getLatitude(), location.getLongitude());

        for (BikeStation station : MyApplication.stations) {
            LatLng bike = new LatLng(station.getLatitude(), station.getLongitude());
            float distance = AMapUtils.calculateLineDistance(now, bike);
            station.setDistance(distance);
        }
        Collections.sort(MyApplication.stations, new LocationComparator());
        adapter.setList(MyApplication.stations);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(mContext, MapActivity.class);
        i.putExtra(MapActivity.STATION_KEY,MyApplication.stations.get(position));
        startActivity(i);
    }
}
