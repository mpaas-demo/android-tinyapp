package com.mpaas.demo.tinyapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.mobile.nebula.provider.H5ViewProvider;
import com.alipay.mobile.nebula.provider.TinyOptionMenuViewProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.AbsTinyOptionMenuView;
import com.alipay.mobile.nebula.view.H5NavMenuView;
import com.alipay.mobile.nebula.view.H5PullHeaderView;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.nebula.view.H5WebContentView;
import com.mpaas.demo.R;
import com.mpaas.nebula.adapter.api.MPNebula;

public class CustomTitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.advance_custom_title);
        ((TextView)findViewById(R.id.button)).setText(R.string.start_mpaas_sample);
        // 设置自定义 title，另外请注意，小程序自定义 title 需要在 custom_config.json 中将 mp_ta_use_orginal_mini_nagivationbar 的 value 设为 NO
        initCustomTitle();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPNebula.startApp("2018080616290001");
            }
        });
    }

    private void initCustomTitle(){
        // 自定义标题栏
        MPNebula.setCustomViewProvider(new H5ViewProvider() {
            @Override
            public H5TitleView createTitleView(Context context) {
                // 返回自定义 title
                return new TinyNavigationBar(context);
            }

            @Override
            public H5NavMenuView createNavMenu() {
                return null;
            }

            @Override
            public H5PullHeaderView createPullHeaderView(Context context, ViewGroup viewGroup) {
                return null;
            }

            @Override
            public H5WebContentView createWebContentView(Context context) {
                return null;
            }
        });
        // 自定义小程序右上角配置栏
        H5Utils.setProvider(TinyOptionMenuViewProvider.class.getName(), new TinyOptionMenuViewProvider() {
            @Override
            public AbsTinyOptionMenuView createView(Context context) {
                return new TinyOptionMenuView(context);
            }
        });
    }
}
