package com.mpaas.demo.tinyapp;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;

public class ShareTinyMsgPlugin extends H5SimplePlugin {

    public static final String ACTION_SHARE = "shareTinyAppMsg";

    @Override
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(ACTION_SHARE);
    }

    @Override
    public boolean handleEvent(H5Event event, final H5BridgeContext context) {
        String action = event.getAction();
        if (ACTION_SHARE.equals(action)) {
            JSONObject param = event.getParam();
            String title = param.getString("title");
            String desc = param.getString("desc");
            String myprop = param.getString("myprop");
            String path = param.getString("page");
            String appId = event.getH5page().getParams().getString("appId");

            // TODO 在此处可调用分享组件，实现后续功能

            String message = "应用ID： " + appId + "\n"
                    + "title: " + title + "\n"
                    + "desc: " + desc + "\n"
                    + "myprop: " + myprop + "\n"
                    + "path: " + path + "\n";
            AUNoticeDialog dialog = new AUNoticeDialog(event.getActivity(),
                    "分享结果", message, "分享成功", "分享失败");
            dialog.setPositiveListener(new AUNoticeDialog.OnClickPositiveListener() {
                @Override
                public void onClick() {
                    JSONObject result = new JSONObject();
                    result.put("success", true);
                    context.sendBridgeResult(result);
                }
            });
            dialog.setNegativeListener(new AUNoticeDialog.OnClickNegativeListener() {
                @Override
                public void onClick() {
                    context.sendError(11, "分享失败");
                }
            });
            dialog.show();
            //
            return true;
        }
        return false;
    }
}
