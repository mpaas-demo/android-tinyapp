package com.mpaas.demo.tinyapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.android.phone.scancode.export.ScanCallback;
import com.alipay.android.phone.scancode.export.ScanRequest;
import com.alipay.android.phone.scancode.export.ScanService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.mpaas.demo.R;
import com.mpaas.mas.adapter.api.MPLogger;
import com.mpaas.nebula.adapter.api.MPTinyHelper;

public class PreviewDebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.advance_preview_debug);
        ((TextView) findViewById(R.id.button)).setText(R.string.scan_preview_debug);
        // 预览调试需要设置白名单 ID
        setUserId();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanPreviewOrDebugQRCode();
            }
        });
    }

    private void setUserId(){
        // 设置白名单 ID，这里的 ID 可以是任意字符串
        MPLogger.setUserId("mPaaSTest");
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
                    return;
                }
                // 启动预览或调试小程序，第二个参数为小程序启动参数
                MPTinyHelper.getInstance().launchIdeQRCode(uri, new Bundle());
            }
        });
    }

    private void showScanError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PreviewDebugActivity.this, getString(R.string.error_scan_code), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
