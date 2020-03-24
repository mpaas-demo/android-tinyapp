package com.mpaas.demo.nebula;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.alipay.android.phone.scancode.export.ScanCallback;
import com.alipay.android.phone.scancode.export.ScanRequest;
import com.alipay.android.phone.scancode.export.ScanService;
import com.alipay.mobile.antui.basic.AUAssistLabelView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.antui.basic.AUToast;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Param;
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
import com.mpaas.nebula.adapter.api.MPTinyHelper;

public class MiniAppActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_app);
        AUTitleBar titleBar = (AUTitleBar) findViewById(R.id.titlebar);
        titleBar.setTitleText("小程序");

        findViewById(R.id.btn_open_mini_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动小程序demo
                Bundle bundle = new Bundle();
                LauncherApplicationAgent.getInstance().getMicroApplicationContext()
                        .startApp(null, "2018080616290001", bundle);
            }
        });

        findViewById(R.id.btn_open_preview_debug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanPreviewOrDebugQRCode();
            }
        });

        findViewById(R.id.btn_open_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiniAppActivity.this, PermissionDisplayActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_custom_start_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPTinyHelper.getInstance().setLoadingViewClass(TinyStartupLoadingView.class);
                Bundle bundle = new Bundle();
                bundle.putString("nbupdate", "synctry");
                LauncherApplicationAgent.getInstance().getMicroApplicationContext()
                        .startApp(null, "2018080616290001", bundle);
            }
        });

        findViewById(R.id.btn_custom_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPNebula.setCustomViewProvider(new H5ViewProvider() {
                    @Override
                    public H5TitleView createTitleView(Context context) {
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
                H5Utils.setProvider(TinyOptionMenuViewProvider.class.getName(), new TinyOptionMenuViewProvider() {
                    @Override
                    public AbsTinyOptionMenuView createView(Context context) {
                        return new TinyOptionMenuView(context);
                    }
                });
            }
        });

        AUAssistLabelView labelView = (AUAssistLabelView) findViewById(R.id.tv_tips);
        labelView.setText(getString(R.string.mini_preview_tips, LoggerFactory.getLogContext().getUserId()));
    }

    private void showScanError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AUToast.makeToast(MiniAppActivity.this, R.string.error_scan_code, 2000).show();
            }
        });
    }

    private void scanPreviewOrDebugQRCode() {
        ScanService service = LauncherApplicationAgent.getInstance().getMicroApplicationContext()
                .findServiceByInterface(ScanService.class.getName());
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setScanType(ScanRequest.ScanType.QRCODE);
        service.scan(this, scanRequest, new ScanCallback() {
            @Override
            public void onScanResult(boolean success, Intent result) {
                if (result == null || !success) {
                    showScanError();
                    return;
                }

                Uri uri = result.getData();
                if (uri == null) {
                    showScanError();
                    return ;
                }

                String scheme = uri.getScheme();
                if ("mpaas".equals(scheme)) {
                    Bundle params = new Bundle();
                    String appId = uri.getQueryParameter("appId");
                    for (String key : uri.getQueryParameterNames()) {
                        if (!"appId".equalsIgnoreCase(key)) {
                            params.putString(key, uri.getQueryParameter(key));
                        }
                    }
                    LauncherApplicationAgent.getInstance().getMicroApplicationContext()
                            .startApp(null, appId, params);
                }

            }
        });
    }
}
