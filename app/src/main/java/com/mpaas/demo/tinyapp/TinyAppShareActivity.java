package com.mpaas.demo.tinyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mpaas.demo.R;
import com.mpaas.nebula.adapter.api.MPNebula;

public class TinyAppShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.tinyapp_share);
        ((TextView)findViewById(R.id.button)).setText(R.string.register_tinyapp_share);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 注册分享插件
                MPNebula.registerH5Plugin(ShareTinyMsgPlugin.class.getName(), "", "page", new String[] {
                        ShareTinyMsgPlugin.ACTION_SHARE
                });
                Toast.makeText(TinyAppShareActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
