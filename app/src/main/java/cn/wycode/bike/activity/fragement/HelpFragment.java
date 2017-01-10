package cn.wycode.bike.activity.fragement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.wycode.bike.activity.AboutActivity;
import cn.wycode.bike.R;
import cn.wycode.bike.activity.HelpActivity;
import cn.wycode.bike.activity.QuestionListActivity;
import cn.wycode.bike.activity.ServiceTypeActivity;

/**
 * 帮助
 * Created by wy on 2016/12/22.
 */

public class HelpFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_qa)
    LinearLayout llQA;

    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    @BindView(R.id.ll_guide)
    LinearLayout llGuide;

    @BindView(R.id.ll_service)
    LinearLayout llService;

    @BindView(R.id.ll_tel)
    LinearLayout llTel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_help);
    }

    @Override
    protected void initView() {
        setOnClickListeners(this, llAbout,llGuide,llQA,llService,llTel);
    }

    @Override
    public void getHttpData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_tel:
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel://965318"));
                break;
            case R.id.ll_qa:
                intent.setClass(getContext(), QuestionListActivity.class);
                break;
            case R.id.ll_about:
                intent.setClass(getContext(), AboutActivity.class);
                break;
            case R.id.ll_service:
                intent.setClass(getContext(), ServiceTypeActivity.class);
                break;
            case R.id.ll_guide:
                intent.setClass(getContext(), HelpActivity.class);
                intent.putExtra(HelpActivity.TITLE_KEY,"办卡指南");
                intent.putExtra(HelpActivity.HEAD_KEY,getString(R.string.guide_head));
                intent.putExtra(HelpActivity.BODY_KEY,getString(R.string.guide_body));
                break;
        }
        startActivity(intent);
    }
}
