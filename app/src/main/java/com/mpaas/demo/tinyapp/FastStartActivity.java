package com.mpaas.demo.tinyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mpaas.demo.R;
import com.mpaas.nebula.adapter.api.MPNebula;

public class FastStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.mini_app);
        ((TextView)findViewById(R.id.button)).setText(R.string.start_mpaas_sample);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPNebula.startApp("2018080616290001");
            }
        });
    }
}
