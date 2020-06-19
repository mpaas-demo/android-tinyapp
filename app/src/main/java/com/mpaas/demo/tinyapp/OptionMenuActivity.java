package com.mpaas.demo.tinyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.mobile.nebula.provider.TinyPopMenuProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.demo.R;
import com.mpaas.nebula.adapter.api.MPNebula;

public class OptionMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.advance_option_menu);
        ((TextView) findViewById(R.id.button)).setText(R.string.start_mpaas_sample);

        // 设置小程序自定义菜单
        H5Utils.setProvider(TinyPopMenuProvider.class.getName(), new TinyPopMenuProviderImpl());
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPNebula.startApp("2018080616290001");
            }
        });
    }
}
