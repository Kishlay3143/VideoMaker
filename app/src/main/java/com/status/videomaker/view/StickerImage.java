package com.status.videomaker.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class StickerImage extends StickerView {
    String val;
    private ImageView iv_main;
    private String owner_id;

    public StickerImage(Context context) {
        super(context);
    }

    public StickerImage(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StickerImage(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public String getOwnerId() {
        return this.owner_id;
    }

    public void setOwnerId(String str) {
        this.owner_id = str;
    }

    @Override
    public View getMainView() {
        if (this.iv_main == null) {
            this.iv_main = new ImageView(getContext());
            this.iv_main.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return this.iv_main;
    }

    public String getVal() {
        return this.val;
    }

    public void HideAll() {
        hideButtonAndBorder();
    }

    public void setTransparency(int i) {
        this.iv_main.setAlpha(i);
    }

    public void setImageResource(int i) {
        this.iv_main.setImageResource(i);
    }

    public void setImageDrawable(Drawable drawable) {
        this.iv_main.setImageDrawable(drawable);
    }

    public Bitmap getImageBitmap() {
        return ((BitmapDrawable) this.iv_main.getDrawable()).getBitmap();
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.iv_main.setImageBitmap(bitmap);
    }
}
