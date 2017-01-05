package cn.wycode.bike.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.wycode.bike.BaseActivity;
import cn.wycode.bike.R;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithDefaultTitle(R.layout.activity_about, "关于");
        findViewById(R.id.tv_wycode).setOnClickListener(this);
        findViewById(R.id.tv_code).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        Intent wyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView) v).getText().toString()));
        startActivity(wyIntent);

    }
}