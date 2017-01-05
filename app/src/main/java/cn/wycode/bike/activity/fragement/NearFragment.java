package cn.wycode.bike.activity.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.wycode.bike.Constants;
import cn.wycode.bike.R;
import cn.wycode.bike.adapter.StationAdapter;
import cn.wycode.bike.net.XmlUtils;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 附近
 * Created by wy on 2016/12/22.
 */

public class NearFragment extends BaseFragment {

    @BindView(R.id.lv_near)
    ListView lvNear;

    private List<XmlUtils.BikeStation> stations = new ArrayList<>();

    private StationAdapter adapter;


    @Override
    protected void initView() {
        adapter = new StationAdapter(mContext, stations, R.layout.item_near);
        lvNear.setAdapter(adapter);

    }

    @Override
    public void getHttpData() {
        RequestBody body = RequestBody.create(MediaType.parse("*/*"), Constants.body);
        OkGo.post(Constants.url).requestBody(body).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

                Log.d("wy", XmlUtils.getJsonString(s));
                stations = XmlUtils.parserJson(XmlUtils.getJsonString(s));
                adapter.setList(stations);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocation(AMapLocation location) {
        Log.d("wy", location.getAddress());
    }
}