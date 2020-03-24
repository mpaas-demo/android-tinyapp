package com.mpaas.demo.nebula;

import com.alipay.mobile.nebula.appcenter.H5PresetInfo;
import com.alipay.mobile.nebula.appcenter.H5PresetPkg;
import com.alipay.mobile.nebula.provider.H5AppCenterPresetProvider;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by omg on 2018/8/6.
 */

public class H5AppCenterPresetProviderImpl implements H5AppCenterPresetProvider {
    private static final String TAG = "H5AppCenterPresetProviderImpl";

    // 设置小程序专用资源包
    private static final String TINY_COMMON_APP = "66666692";

    // 预置包的asset目录
    private final static String NEBULA_APPS_PRE_INSTALL = "nebulaPreset" + File.separator;

    // 预置包集合
    private static final Map<String, H5PresetInfo> NEBULA_LOCAL_PACKAGE_APP_IDS = new HashMap<>();

    static {

        H5PresetInfo h5PresetInfo2 = new H5PresetInfo();
        // 内置目录的文件名称
        h5PresetInfo2.appId = TINY_COMMON_APP;
        h5PresetInfo2.version = "1.0.0.0";
        h5PresetInfo2.downloadUrl = "";

        NEBULA_LOCAL_PACKAGE_APP_IDS.put(TINY_COMMON_APP, h5PresetInfo2);

    }

    @Override
    public Set<String> getCommonResourceAppList() {
        Set<String> appIdList = new HashSet<String>();
        appIdList.add(getTinyCommonApp());
        appIdList.add("10000001");
        return appIdList;
    }

    @Override
    public H5PresetPkg getH5PresetPkg() {
        H5PresetPkg h5PresetPkg = new H5PresetPkg();
        h5PresetPkg.setPreSetInfo(NEBULA_LOCAL_PACKAGE_APP_IDS);
        h5PresetPkg.setPresetPath(NEBULA_APPS_PRE_INSTALL);
        return h5PresetPkg;
    }

    @Override
    public Set<String> getEnableDegradeApp() {
        return null;
    }

    @Override
    public String getTinyCommonApp() {
        return TINY_COMMON_APP;
    }

    @Override
    public InputStream getPresetAppInfo() {
        return null;
    }

    @Override
    public InputStream getPresetAppInfoObject() {
        return null;
    }
}
