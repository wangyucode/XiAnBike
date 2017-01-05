package cn.wycode.bike.adapter.baseadapter;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wycode.bike.MyApplication;


/**
 * 通用的ViewHolder缓存
 *
 * @author 王郁
 * @version 1.0
 * @since 2015年8月5日 13:55:56
 */
public class WyViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    /**
     * ViewHolder构造方法，不需要手动构造ViewHolder
     *
     * @param context
     * @param parent
     * @param layoutId
     * @param position
     */
    private WyViewHolder(Context context, ViewGroup parent, int layoutId,
                         int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();

        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);

    }

    /**
     * 得到ViewHolder,已做缓存处理
     *
     * @param context     上下文环境
     * @param convertView itemView
     * @param parent
     * @param layoutId    item布局文件id
     * @param position    当前position
     * @return ViewHolder
     */
    public static WyViewHolder getHolder(Context context, View convertView,
                                         ViewGroup parent, int layoutId, int position) {

        if (convertView == null) {
            return new WyViewHolder(context, parent, layoutId, position);
        } else {
            WyViewHolder holder = (WyViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }

    }

    /**
     * 得到item上的控件
     *
     * @param viewId 控件的id
     * @return 对应的控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;

    }

    /**
     * adapter返回的convertView
     *
     * @return convertView
     */
    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 返回当前holder的position 防止因缓存造成的混乱问题
     *
     * @return 当前holder对应的position
     */
    public int getmPosition() {
        return mPosition;
    }

    /**
     * 为TextView设置Text
     *
     * @param textViewId textView的Id
     * @param text       字符串的Id
     * @return ViewHolder
     */
    public WyViewHolder setTextViewText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        }
        return this;
    }

    /**
     * 为TextView设置Text
     *
     * @param textViewId textView的Id
     * @param strId      字符串的Id
     * @return ViewHolder
     */
    public WyViewHolder setTextViewText(@IdRes int textViewId, @StringRes int strId) {
        TextView tv = getView(textViewId);
        tv.setText(strId);
        return this;
    }

    /**
     * 为TextView设置Text
     *
     * @param textViewId             textView的Id
     * @param spannableStringBuilder 字符串的Builder用于改变字色   字体
     * @return ViewHolder
     */
    public WyViewHolder setTextViewText(@IdRes int textViewId, SpannableStringBuilder spannableStringBuilder) {
        TextView tv = getView(textViewId);
        tv.setText(spannableStringBuilder);
        return this;
    }

    /**
     * 获取TextView的Text
     *
     * @param textViewId textView的Id
     * @return textView的text
     */
    public String getTextViewText(@IdRes int textViewId) {
        TextView tv = getView(textViewId);
        return tv.getText().toString();
    }

    /**
     * 获取TextView的TextColor
     *
     * @param textViewId textView的Id
     * @param color      颜色
     * @return ViewHolder
     */
    public WyViewHolder setTextViewTextColor(@IdRes int textViewId, @ColorInt int color) {
        TextView tv = getView(textViewId);
        tv.setTextColor(color);
        return this;
    }


    /**
     * 为ImageView设置资源图片
     *
     * @param imageViewId ImageView的Id
     * @param drawableId  图片资源Id
     * @return
     */
    public WyViewHolder setImageViewImage(@IdRes int imageViewId, @DrawableRes int drawableId) {
        ImageView imageView = getView(imageViewId);
        imageView.setImageResource(drawableId);
        return this;
    }

    /**
     * 为CheckBox设置是否check
     *
     * @param checkBoxId CheckBox的Id
     * @param isCheck    是否选择
     * @return
     */
    public WyViewHolder setCheckBoxCheck(@IdRes int checkBoxId, boolean isCheck) {
        CheckBox cb = getView(checkBoxId);
        cb.setChecked(isCheck);
        return this;
    }

    /**
     * 为CheckBox设置CheckListener 回调带position
     *
     * @param checkBoxId    CheckBox的Id
     * @param checkListener Listener
     * @return
     */
    public WyViewHolder setCheckBoxCheckChangeListener(@IdRes int checkBoxId, ListenerWithPosition.OnCheckedChangeWithPositionListener checkListener) {
        CheckBox cb = getView(checkBoxId);
        ListenerWithPosition listener = new ListenerWithPosition(mPosition, this);
        cb.setOnCheckedChangeListener(listener);
        listener.setCheckChangeListener(checkListener);
        return this;
    }

    /**
     * 为View设置ClickListener,可传多个ViewId
     *
     * @param viewIds       CheckBox的Id
     * @param clickListener Listener
     * @return
     */
    public WyViewHolder setOnClickListener(ListenerWithPosition.OnClickWithPositionListener clickListener, @IdRes int... viewIds) {
        ListenerWithPosition listener = new ListenerWithPosition(mPosition, this);
        listener.setOnClickListener(clickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 为ImageView设置网络图片
     *
     * @param imageViewId ImageView的Id
     * @param url         图片资源Id
     * @return
     */
    public WyViewHolder setImageViewImage(@IdRes int imageViewId, String url) {
        ImageView imageView = getView(imageViewId);
//        MyApplication.imageLoader.displayImage(url, imageView);
        return this;
    }

    /**
     * 为View设置选择状态
     *
     * @param ViewId     View的Id
     * @param isSelected 是否选择
     * @return
     */
    public WyViewHolder setViewSelect(@IdRes int ViewId, boolean isSelected) {
        View view = getView(ViewId);
        view.setSelected(isSelected);
        return this;
    }

    /**
     * 为View设置显示状态
     *
     * @param ViewId     View的Id
     * @param visibility 是否显示
     * @return
     */
    public WyViewHolder setViewVisibility(@IdRes int ViewId, int visibility) {
        View view = getView(ViewId);
        view.setVisibility(visibility);
        return this;
    }


    /**
     * 为View设置背景
     *
     * @param ViewId     View的Id
     * @param drawableId drawable的Id
     * @return
     */
    public WyViewHolder setViewBackground(@IdRes int ViewId, @DrawableRes int drawableId) {
        View view = getView(ViewId);
        view.setBackgroundResource(drawableId);
        return this;
    }

    /**
     * 为EditView设置TextChangeListener,可传多个ViewId
     *
     * @param viewIds            EditView的Id
     * @param textChangeListener Listener
     * @return
     */
    public WyViewHolder addOnTextChangeListener(ListenerWithPosition.OnTextChangWithPosition textChangeListener, @IdRes int... viewIds) {
        ListenerWithPosition listener = new ListenerWithPosition(mPosition, this);
        listener.addTextChangedListener(textChangeListener);
        for (int id : viewIds) {
            EditText v = getView(id);
            v.addTextChangedListener(listener);
        }
        return this;
    }

}
