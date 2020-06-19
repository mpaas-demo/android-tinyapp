package com.mpaas.demo.tinyapp;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.alipay.mobile.nebula.provider.TinyPopMenuItem;
import com.alipay.mobile.nebula.provider.TinyPopMenuProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.demo.R;

import java.util.ArrayList;
import java.util.List;

public class TinyPopMenuProviderImpl implements TinyPopMenuProvider {
    @Override
    public List<TinyPopMenuItem> fetchMenuItems(String s) {
        List<TinyPopMenuItem> items = new ArrayList<>();
        TinyPopMenuItem urlItem = new TinyPopMenuItem.Builder()
                .setId("1000")
                .setIconUrl("https://gw.alipayobjects.com/mdn/rms_8f78e5/afts/img/A*nfszQ4z_FbIAAAAAAAAAAABkARQnAQ")
                .setName("关于")
                .setCallback(new TinyPopMenuItem.TinyPopMenuItemClickListener() {
                    @Override
                    public void onClick(Context context, Bundle bundle) {
                        String appId = bundle.getString("appId");
                        String path = bundle.getString("page");
                        Toast.makeText(context, "应用ID=" + appId + ",页面=" + path, Toast.LENGTH_LONG).show();
                    }
                })
                .build();
        items.add(urlItem);
        // shareItem is just for changing internal share item's position
        TinyPopMenuItem shareItem = new TinyPopMenuItem.Builder()
                .setId("1002")
                .setOverride(true)
                .setName("分享")
                .build();
        items.add(shareItem);
        TinyPopMenuItem localItem = new TinyPopMenuItem.Builder()
                .setId("1001")
                .setIcon(H5Utils.getContext().getResources().getDrawable(R.drawable.smile))
                .setName("启动")
                .setCallback(new TinyPopMenuItem.TinyPopMenuItemClickListener() {
                    @Override
                    public void onClick(Context context, Bundle bundle) {
                        Toast.makeText(context, "启动" + bundle.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .build();

        items.add(localItem);
        return items;
    }
}
