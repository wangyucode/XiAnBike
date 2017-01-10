package cn.wycode.bike.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.wycode.bike.BaseActivity;
import cn.wycode.bike.R;
import cn.wycode.bike.adapter.ServiceAdapter;
import cn.wycode.bike.model.Service;

/**
 * Created by wy
 * on 2017/1/10.
 */

public class ServiceListActivity extends BaseActivity {

    public static final String SERVICE_TYPE_KEY = "SERVICE_TYPE_KEY";
    public static final String SERVICE_ZHIYING = "直营网点";
    public static final String SERVICE_YIKATONG = "一卡通";
    public static final String SERVICE_XIAN_BANK = "西安银行";
    public static final String SERVICE_CHINA_BANK = "中国银行";

    @BindView(R.id.lv_question)
    ListView lvService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithDefaultTitle(R.layout.activity_question_list, "服务网点");
    }

    @Override
    protected void initView() {
        String type = getIntent().getStringExtra(SERVICE_TYPE_KEY);
        List<Service> services = JSON.parseArray(getString(R.string.services), Service.class);
        ArrayList<Service> servicesSelected = new ArrayList<>();
        for (Service service : services) {
            if (type.equals(service.getCompany())) {
                servicesSelected.add(service);
            }
        }
        ServiceAdapter adapter = new ServiceAdapter(this, servicesSelected, R.layout.item_service);
        lvService.setAdapter(adapter);

    }
}
