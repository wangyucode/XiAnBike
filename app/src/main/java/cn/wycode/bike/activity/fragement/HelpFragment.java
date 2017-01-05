package cn.wycode.bike.activity.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.wycode.bike.activity.AboutActivity;
import cn.wycode.bike.R;

/**
 * 帮助
 * Created by wy on 2016/12/22.
 */

public class HelpFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_help);
    }

    @Override
    protected void initView() {
        setOnClickListeners(this, llAbout);
    }

    @Override
    public void getHttpData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_about:
                intent.setClass(getContext(), AboutActivity.class);
        }
        startActivity(intent);
    }
}