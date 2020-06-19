package com.mpaas.demo.tinyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.mpaas.demo.R;
import com.mpaas.nebula.adapter.api.MPNebula;
import com.mpaas.nebula.adapter.api.MPTinyHelper;

public class StartupLoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.advance_startup_loading);
        ((TextView)findViewById(R.id.button)).setText(R.string.start_mpaas_sample);

        // 设置自定义启动页
        MPTinyHelper.getInstance().setLoadingViewClass(TinyStartupLoadingView.class);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                // 注意：该参数会强制请求远端小程序最新版本，会有一个等待过程，这个等待过程就是自定义启动页
                bundle.putString("nbupdate", "synctry");
                LauncherApplicationAgent.getInstance().getMicroApplicationContext()
                        .startApp(null, "2018080616290001", bundle);
            }
        });
    }
}
