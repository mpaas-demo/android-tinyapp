package com.mpaas.demo.nebula;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.alipay.mobile.antui.basic.AUProgressBar;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.mpaas.demo.R;
import com.mpaas.nebula.adapter.api.MPTinyBaseIntermediateLoadingView;

public class TinyStartupLoadingView extends MPTinyBaseIntermediateLoadingView {

    private AUTextView tvAppName;

    private View progressBar;

    private AUTextView tvTips;

    public TinyStartupLoadingView(Context context) {
        super(context);
        init();
    }

    public TinyStartupLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TinyStartupLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.activity_loading, this, true);
        tvAppName = (AUTextView) findViewById(R.id.tv_app);
        progressBar =  findViewById(R.id.progress);
        tvTips = (AUTextView) findViewById(R.id.tv_tips);
        ((AUTitleBar)findViewById(R.id.title)).getBackButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity host = getLoadingActivity();
                if (host != null) {
                    host.finish();
                }
            }
        });
    }

    /**
     * 初始化时调用，会传入小程序的应用ID，其他信息如名称、应用图标、版本，可能为空
     */
    @Override
    public void initView(AppInfo info) {
        tvAppName.setText(info.appName);
    }

    /**
     * 获取小程序失败时调用
     */
    @Override
    public void onError() {
        tvTips.setText("fail");
        tvTips.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
    }

    /**
     * 拉取到小程序应用信息时调用，可获取应用ID，名称、图标和版本信息
     */
    @Override
    public void update(AppInfo info) {
        tvAppName.setText(info.appName);
    }
}
