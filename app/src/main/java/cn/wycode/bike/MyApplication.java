package cn.wycode.bike;

import android.app.Application;

import cn.wycode.bike.net.OKhttpUtils;

/**
 * 主要用作第三方初始化
 * Created by wy on 2015/10/15.
 */
public class MyApplication extends Application  {



    @Override
    public void onCreate() {
        super.onCreate();
        OKhttpUtils.init(this);
    }


}
