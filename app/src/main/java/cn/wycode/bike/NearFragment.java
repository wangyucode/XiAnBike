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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

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



    public NearFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_near, container, false);
        lvNear = (ListView) rootView.findViewById(R.id.lv_near);
        lvNear.setAdapter(new NearAdapter());
        RequestBody body = RequestBody.create(MediaType.parse("*/*"),Constants.body);
        OkGo.post(Constants.url).requestBody(body).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

                Log.d("wy",XmlUtils.getJsonString(s));
                List<XmlUtils.BikeStation> stations = XmlUtils.parserJson(XmlUtils.getJsonString(s));
                Log.d("wy",s);
            }
        });
        return rootView;
    }

    class NearAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return new Object();
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

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText("融侨馨苑南");
            return convertView;
        }

        class ViewHolder {
            TextView title;
        }
    }
}