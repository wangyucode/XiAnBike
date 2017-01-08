package cn.wycode.bike.adapter;

import android.content.Context;

import java.util.List;

import cn.wycode.bike.R;
import cn.wycode.bike.adapter.baseadapter.WyCommonAdapter;
import cn.wycode.bike.adapter.baseadapter.WyViewHolder;
import cn.wycode.bike.model.BikeStation;

/**
 * Created by wy
 * on 2017/1/5.
 */

public class StationAdapter extends WyCommonAdapter<BikeStation> {


    public StationAdapter(Context context, List<BikeStation> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(WyViewHolder holder, BikeStation bikeStation) {
        holder.setTextViewText(R.id.tv_near_title, bikeStation.getSitename());
        holder.setTextViewText(R.id.tv_item_distance, (int)bikeStation.getDistance()+"m");
        holder.setTextViewText(R.id.tv_item_address, bikeStation.getLocation());
        holder.setTextViewText(R.id.tv_item_locknum, "可租" + bikeStation.getLocknum() + "辆");
        holder.setTextViewText(R.id.tv_item_emptynum, "可还" + bikeStation.getEmptynum() + "辆");
    }
}
