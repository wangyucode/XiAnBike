package cn.wycode.bike;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.tv_wycode).setOnClickListener(this);
        findViewById(R.id.tv_code).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent wyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView) v).getText().toString()));
        startActivity(wyIntent);

    }
}