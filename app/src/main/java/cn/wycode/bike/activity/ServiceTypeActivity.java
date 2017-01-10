package cn.wycode.bike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.wycode.bike.BaseActivity;
import cn.wycode.bike.R;

/**
 * Created by wy
 * on 2017/1/10.
 */

public class ServiceTypeActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.ll_zhiying)
    LinearLayout llZhiying;

    @BindView(R.id.ll_yikatong)
    LinearLayout llYikatong;

    @BindView(R.id.ll_xian_bank)
    LinearLayout llXiAnBank;

    @BindView(R.id.ll_china_bank)
    LinearLayout llChinaBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithDefaultTitle(R.layout.activity_service_type,"网点类型");
    }

    @Override
    protected void initView() {
        setOnClickListeners(this,llChinaBank,llXiAnBank,llYikatong,llZhiying);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ServiceListActivity.class);
        switch (v.getId()){
            case R.id.ll_zhiying:
                intent.putExtra(ServiceListActivity.SERVICE_TYPE_KEY,ServiceListActivity.SERVICE_ZHIYING);
                break;
            case R.id.ll_yikatong:
                intent.putExtra(ServiceListActivity.SERVICE_TYPE_KEY,ServiceListActivity.SERVICE_YIKATONG);
                break;
            case R.id.ll_xian_bank:
                intent.putExtra(ServiceListActivity.SERVICE_TYPE_KEY,ServiceListActivity.SERVICE_XIAN_BANK);
                break;
            case R.id.ll_china_bank:
                intent.putExtra(ServiceListActivity.SERVICE_TYPE_KEY,ServiceListActivity.SERVICE_CHINA_BANK);
                break;
        }
        startActivity(intent);
    }
}
