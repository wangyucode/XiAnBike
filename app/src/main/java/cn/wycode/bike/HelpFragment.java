package cn.wycode.bike;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 帮助
 * Created by wy on 2016/12/22.
 */

public class HelpFragment extends Fragment implements View.OnClickListener{

    private LinearLayout llAbout;

    public HelpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_help, container, false);
        llAbout = (LinearLayout) rootView.findViewById(R.id.ll_about);

        llAbout.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.ll_about:
                intent.setClass(getContext(),AboutActivity.class);
        }
        startActivity(intent);
    }
}