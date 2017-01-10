package cn.wycode.bike.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.BindView;
import cn.wycode.bike.BaseActivity;
import cn.wycode.bike.R;

/**
 * Created by wy
 * on 2017/1/9.
 */

public class HelpActivity extends BaseActivity {

    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String HEAD_KEY = "HEAD_KEY";
    public static final String BODY_KEY = "BODY_KEY";

    private String title, head, body;

    @BindView(R.id.tv_head)
    TextView tvHead;

    @BindView(R.id.tv_body)
    TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra(TITLE_KEY);
        head = getIntent().getStringExtra(HEAD_KEY);
        body = getIntent().getStringExtra(BODY_KEY);
        setContentViewWithDefaultTitle(R.layout.activity_help, title == null ? "帮助" : title);
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(head)) {
            tvHead.setText(head);
        }
        if (!TextUtils.isEmpty(body)) {
            tvBody.setText(body);
        }
    }
}
