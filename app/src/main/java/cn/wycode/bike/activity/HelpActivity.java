package cn.wycode.bike.activity;

import android.os.Bundle;

import cn.wycode.bike.BaseActivity;
import cn.wycode.bike.R;

/**
 * Created by wy
 * on 2017/1/9.
 */

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithDefaultTitle(R.layout.activity_help,"帮助");
    }

    @Override
    protected void initView() {

    }
}
