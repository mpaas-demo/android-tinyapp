package com.mpaas.demo.tinyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.mpaas.demo.R;
import com.mpaas.framework.adapter.api.MPFramework;
import com.mpaas.nebula.adapter.api.MPNebula;

public class CustomApiActivity extends AppCompatActivity {
    private boolean destroy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.advance_custom_title);
        ((TextView) findViewById(R.id.button)).setText(R.string.start_mpaas_sample);
        // 设置自定义API
        initCustomApi();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开启一个工作线程，模拟客户端向小程序发送事件
                startMockNativePost();
                MPNebula.startApp("2018080616290001");
            }
        });
    }

    private void initCustomApi() {
        /*
         * 第一个参数，自定义 API 类的全路径
         * 第二个参数，BundleName，aar/inside可以直接填 ""
         * 第三个参数，作用于，可以直接填 "page"
         * 第四个参数，作用的 API，将你自定义的 API 以 String[] 的形式传入
         */
        MPNebula.registerH5Plugin(MyJSApiPlugin.class.getName(), "", "page", new String[]{MyJSApiPlugin.TINY_TO_NATIVE});
    }

    private void startMockNativePost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    if (destroy) {
                        break;
                    }
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int index = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            H5Service h5Service = MPFramework.getExternalService(H5Service.class.getName());
                            final H5Page h5Page = h5Service.getTopH5Page();
                            if (null != h5Page) {
                                JSONObject jo = new JSONObject();
                                jo.put("index", index);
                                // native 向小程序发送事件的方法
                                h5Page.getBridge().sendDataWarpToWeb("nativeToTiny", jo, null);
                            }
                        }
                    });
                }

            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        destroy = true;
        super.onDestroy();
    }
}
