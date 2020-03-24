package com.mpaas.demo.nebula;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.tiny.menu.TinyAppActionState;
import com.alipay.mobile.nebula.view.AbsTinyOptionMenuView;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.mpaas.demo.R;

public class TinyOptionMenuView extends AbsTinyOptionMenuView {

    private View container;

    private ImageView ivMore;

    private View ivClose;

    private Context context;

    private View bgView;

    public TinyOptionMenuView(Context context) {
        this.context = context;
        ViewGroup parent = null;
        if (context instanceof Activity) {
            parent = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);
        }
        container = LayoutInflater.from(context).inflate(R.layout.layout_tiny_right, parent, false);
        ivClose = container.findViewById(R.id.close);
        ivMore = (ImageView) container.findViewById(R.id.more);
        bgView = container.findViewById(R.id.option_bg);
    }

    @Override
    public View getView() {
        return container;
    }

    @Override
    public void setOptionMenuOnClickListener(View.OnClickListener onClickListener) {
        ivMore.setOnClickListener(onClickListener);
    }

    @Override
    public void setCloseButtonOnClickListener(View.OnClickListener onClickListener) {
        ivClose.setOnClickListener(onClickListener);
    }

    @Override
    public void setCloseButtonOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        ivClose.setOnLongClickListener(onLongClickListener);
    }

    @Override
    public void onStateChanged(TinyAppActionState state) {
        if (state == null) {
            ivMore.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_more));
        } else if (state.getAction().equals(TinyAppActionState.ACTION_LOCATION)) {
            ivMore.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_miniprogram_location));
        }
    }

    @Override
    protected void onTitleChange(final H5TitleView title) {
        super.onTitleChange(title);
        int color = title.getBackgroundColor();
        if ((color & 0xffffff) == 0xffffff) {
            bgView.setBackgroundColor(Color.RED);
        } else {
            bgView.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public void setH5Page(H5Page h5Page) {
        super.setH5Page(h5Page);
        // title becomes available from here.
        if (getTitleBar().getBackgroundColor() == -1) {
            bgView.setBackgroundColor(Color.RED);
        }
    }
}
