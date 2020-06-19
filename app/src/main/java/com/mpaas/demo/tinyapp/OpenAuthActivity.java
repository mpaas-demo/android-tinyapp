package com.mpaas.demo.tinyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.mpaas.demo.R;
import com.mpaas.mas.adapter.api.MPLogger;
import com.mpaas.nebula.adapter.alipay.AuthConfig;
import com.mpaas.nebula.adapter.alipay.AuthGlobal;
import com.mpaas.nebula.adapter.alipay.AuthInfo;
import com.mpaas.nebula.adapter.alipay.AuthProvider;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAuthActivity extends AppCompatActivity {

    private static final String TAG = "LauncherApplication";

    private String aliUid;
    private String mcUid;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.open_auth);
        ((TextView) findViewById(R.id.button)).setText(R.string.init_open_auth);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOpenAuth();
                Toast.makeText(OpenAuthActivity.this, "初始化成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initOpenAuth() {
        AuthGlobal.getInstance().setAuthProvider(new AuthProvider() {
            @Override
            public AuthConfig loadConfig() {
                return new AuthConfig.Builder()
                        .setAuthUrl("https://openapi.alipay.com/gateway.do?alipay_sdk=alipay-sdk-java-3.3.49.ALL&app_id=2019040163782051&biz_content=%7B%22auth_type%22%3A%22MY_PASS_OAUTH%22%2C%22scopes%22%3A%5B%22auth_base%22%2C%22auth_user%22%5D%2C%22state%22%3A%22init%22%2C%22is_mobile%22%3A%22true%22%2C%22origin%22%3A%22AMAP%22%7D&charset=UTF-8&format=json&method=alipay.user.info.auth&return_url=http%3A%2F%2Fzhanghutong.yuguozhou.online%2Ffirst&sign=chx5dRvzASnX0jNIaVYK4rAA2ZIfoBIs9FNx76UpjG7KM8wu7BJDJkHuQ1yNKoIJLMP0oWeVr0vdrcphyIfX97QAGl4BJ5PEosbf6%2B38jDFmC1spIQrAtI3J8eXhL45iqcldzBtIYw1w%2FID%2F3sMdVuMmUlkTYPiPh4L9SJ8WHuZ6lgUYOEtiEC8LOf5bFIRBM5SL1VXozy336C9nwAd0M3EMN%2B99PA9dYiX79qpmJ0D7aSX3xUAi5YjWhjDQznkrQbDvB6c1IgdNVjyMIqRdjf1CmswkDakGobg7TfDnTkafwNleUs%2BWA2JMsk6nK6QNW4PV7UxPh9rcO89cXyXZPg%3D%3D&sign_type=RSA2&timestamp=2019-12-11+13%3A53%3A35&version=1.0")
                        .build();
            }

            @Override
            public AuthInfo fetchAuthInfoSync(String authCode) {
                try {
                    URL url = new URL(String.format("https://mpaasauthserver-office.alipay.net/getToken?code=%s&mcId=%s", authCode, mcUid));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        final String resp = readStream(inputStream);
                        if (null != resp) {
                            JSONObject jsonObject = JSON.parseObject(resp);
                            mcUid = MPLogger.getUserId();
                            aliUid = jsonObject.getString("userId");
                            accessToken = jsonObject.getString("accessToken");
                            return new AuthInfo(aliUid, mcUid, accessToken);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public AuthInfo getCachedAuthInfo() {
                return new AuthInfo(aliUid, mcUid, accessToken);
            }
        });
    }

    private String readStream(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            int readLength;
            while ((readLength = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readLength);
            }
            return outputStream.toString();
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn(TAG, e);
        } finally {
            close(inputStream);
            close(outputStream);
        }
        return null;
    }

    private static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn(TAG, e);
        }
    }
}
