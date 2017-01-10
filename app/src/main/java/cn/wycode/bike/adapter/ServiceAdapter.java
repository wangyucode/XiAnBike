package cn.wycode.bike.adapter;

import android.content.Context;

import java.util.List;

import cn.wycode.bike.R;
import cn.wycode.bike.adapter.baseadapter.WyCommonAdapter;
import cn.wycode.bike.adapter.baseadapter.WyViewHolder;
import cn.wycode.bike.model.Service;

/**
 * Created by wy
 * on 2017/1/10.
 */

public class ServiceAdapter extends WyCommonAdapter<Service> {

    public ServiceAdapter(Context context, List<Service> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(WyViewHolder holder, Service service) {
        holder.setTextViewText(R.id.tv_name,service.getName());
        holder.setTextViewText(R.id.tv_area,service.getArea());
        holder.setTextViewText(R.id.tv_address,service.getAddress());
    }
}
