package com.mpaas.demo.nebula;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.alipay.mobile.antui.basic.AUSearchBar;
import com.alipay.mobile.antui.tablelist.AUSwitchListItem;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.demo.R;
import com.mpaas.nebula.adapter.api.MPTinyHelper;

import java.util.Map;

public class PermissionDisplayActivity extends BaseFragmentActivity {

    private ViewGroup mScrollView;

    private AUSearchBar mSearchInputBox;

    private Map<String, Boolean> permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mScrollView = (ViewGroup) findViewById(R.id.scrollview);
        mSearchInputBox = (AUSearchBar) findViewById(R.id.search);
        mSearchInputBox.getSearchEditView().setHint("输入APPID");
        mSearchInputBox.getSearchButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.removeAllViews();
                final String appId = mSearchInputBox.getSearchEditView().getText().toString();
                permissions = MPTinyHelper.getInstance().getMiniProgramSetting(appId);
                for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
                    AUSwitchListItem item = new AUSwitchListItem(PermissionDisplayActivity.this);
                    final String key = entry.getKey();
                    item.setLeftText(key);
                    item.getCompoundSwitch().setChecked(entry.getValue());
                    item.getCompoundSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            MPTinyHelper.getInstance().updateMiniProgramSetting(appId, key, isChecked);
                        }
                    });
                    mScrollView.addView(item, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, H5Utils.dip2px(PermissionDisplayActivity.this, 48)));
                }
            }
        });
    }
}