package com.mpaas.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mpaas.demo.tinyapp.CustomApiActivity;
import com.mpaas.demo.tinyapp.CustomTitleActivity;
import com.mpaas.demo.tinyapp.FastStartActivity;
import com.mpaas.demo.tinyapp.OpenAuthActivity;
import com.mpaas.demo.tinyapp.OptionMenuActivity;
import com.mpaas.demo.tinyapp.PermissionDisplayActivity;
import com.mpaas.demo.tinyapp.PreviewDebugActivity;
import com.mpaas.demo.tinyapp.StartupLoadingActivity;
import com.mpaas.demo.tinyapp.TinyAppShareActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        findViewById(R.id.mini_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, FastStartActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.preview_debug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, PreviewDebugActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.option_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, OptionMenuActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.custom_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, CustomTitleActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.startup_loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, StartupLoadingActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.custom_api).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, CustomApiActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.api_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, PermissionDisplayActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.tinyapp_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, TinyAppShareActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.open_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, OpenAuthActivity.class);
                startActivity(intent);
            }
        });
        checkPermission();
    }

    protected void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
    }
}
