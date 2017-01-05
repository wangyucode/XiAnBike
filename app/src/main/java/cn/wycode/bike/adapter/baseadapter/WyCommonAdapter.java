package cn.wycode.bike.adapter.baseadapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class WyCommonAdapter<T> extends BaseAdapter {

    public List<T> mData;
    protected Context mContext;
    private int layoutId;

    protected WyCommonAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mData = data == null ? new ArrayList<T>() : data;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WyViewHolder holder = WyViewHolder.getHolder(mContext, convertView,
                parent, layoutId, position);

        convert(holder, getItem(position));
        return holder.getmConvertView();
    }

    public abstract void convert(WyViewHolder holder, T t);

    public void setList(List<T> datas) {
        this.mData.clear();
        this.mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.mData.clear();
        notifyDataSetChanged();
    }

    public void addList(List<T> datas) {
        this.mData.clear();
        this.mData.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 通过类名启动Activity
     *
     * @param cls 需要跳转的类
     */
    protected void openActivity(Class<?> cls) {
        Intent i = new Intent(mContext, cls);
        mContext.startActivity(i);
    }

}
