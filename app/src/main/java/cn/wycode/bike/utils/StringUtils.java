package cn.wycode.bike.utils;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/18.
 */
public class StringUtils {
    /**
     * 字符串判空
     * @param mTvTextView
     * @param content
     */
    public static void setTextNotEmpty(TextView mTvTextView, String content) {
        if (!TextUtils.isEmpty(content)) {
            mTvTextView.setText(content);
        }
    }
}
