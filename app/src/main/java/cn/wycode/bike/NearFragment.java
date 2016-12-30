package cn.wycode.bike;

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

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 附近
 * Created by wy on 2016/12/22.
 */

public class NearFragment extends Fragment {

    private ListView lvNear;

    private List<XmlUtils.BikeStation> stations = new ArrayList<>();

    private NearAdapter adapter;

    public NearFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near, container, false);
        lvNear = (ListView) rootView.findViewById(R.id.lv_near);
        adapter = new NearAdapter();
        lvNear.setAdapter(adapter);
        RequestBody body = RequestBody.create(MediaType.parse("*/*"),Constants.body);
        OkGo.post(Constants.url).requestBody(body).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

                Log.d("wy",XmlUtils.getJsonString(s));
                stations = XmlUtils.parserJson(XmlUtils.getJsonString(s));
                adapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocation(AMapLocation location){
        Log.d("wy",location.getAddress());
    }

    class NearAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stations.size();
        }

        @Override
        public Object getItem(int position) {
            return stations.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_near, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.tv_near_title);
                holder.address = (TextView) convertView.findViewById(R.id.tv_item_address);
                holder.lock = (TextView) convertView.findViewById(R.id.tv_item_locknum);
                holder.empty = (TextView) convertView.findViewById(R.id.tv_item_emptynum);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            XmlUtils.BikeStation station = stations.get(position);
            holder.title.setText(station.getSitename());
            holder.address.setText(station.getLocation());
            holder.lock.setText("可租"+station.getLocknum()+"辆");
            holder.empty.setText("可还"+station.getEmptynum()+"辆");
            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView address;
            TextView lock;
            TextView empty;
        }
    }
}