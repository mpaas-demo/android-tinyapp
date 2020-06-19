package com.mpaas.demo.tinyapp;

import android.content.Context;

import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.nebula.provider.TinyAppPermissionExternProvider;

import java.util.ArrayList;
import java.util.List;

public class TinyExternalPermissionCheckProvider extends TinyAppPermissionExternProvider {

    private PermissionConfig create(String action, String key, String desc) {
        PermissionConfig config = new PermissionConfig();
        config.action = action;
        config.key = key;
        config.desc = desc;
        return config;
    }

    private List<PermissionConfig> permissionConfigs = new ArrayList<>();

    public TinyExternalPermissionCheckProvider() {
        permissionConfigs.add(create("saveFile", "file", "%s想使用您的文件存储"));
        permissionConfigs.add(create("getFileInfo", "file", "%s想使用您的文件存储"));
    }

    @Override
    public List<PermissionConfig> loadPermissionCheckConfig() {
        return permissionConfigs;
    }

    @Override
    public void showPermissionDialog(Context context, String action, PermissionConfig permissionConfig, final PermissionCheckCallback permissionCheckCallback) {
        AUNoticeDialog dialog = new AUNoticeDialog(context, "授权提醒", permissionConfig.desc, "接受", "拒绝");
        dialog.setPositiveListener(new AUNoticeDialog.OnClickPositiveListener() {
            @Override
            public void onClick() {
                permissionCheckCallback.accept();
            }
        });
        dialog.setNegativeListener(new AUNoticeDialog.OnClickNegativeListener() {
            @Override
            public void onClick() {
                permissionCheckCallback.deny();
            }
        });
        dialog.show();
    }

    @Override
    public boolean shouldHandlePermissionDialog() {
        return true;
    }
}