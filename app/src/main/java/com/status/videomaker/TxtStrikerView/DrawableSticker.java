package com.status.videomaker.TxtStrikerView;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class DrawableSticker extends Sticker {
    public Drawable drawable;
    private int height;
    private Rect realBounds = new Rect(0, 0, 0, 0);
    private int width;

    public DrawableSticker(Drawable drawable2) {
        this.drawable = drawable2;
    }

    @Override
    @NonNull
    public Drawable getDrawable() {
        return this.drawable;
    }

    @Override
    public DrawableSticker setDrawable(@NonNull Drawable drawable2) {
        this.drawable = drawable2;
        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.concat(getMatrix());
        this.drawable.setBounds(this.realBounds);
        this.drawable.draw(canvas);
        canvas.restore();
    }

    @Override
    @NonNull
    @SuppressLint({"Range"})
    public void setAlpha(int i) {
        this.drawable.setAlpha(i);
    }

    @Override
    public int getWidth() {
        return this.drawable.getIntrinsicWidth();
    }

    @Override
    public void setWidth(int i) {
        this.width = i;
    }

    @Override
    public int getHeight() {
        return this.drawable.getIntrinsicHeight();
    }

    @Override
    public void setHeight(int i) {
        this.height = i;
    }

    @Override
    public void release() {
        super.release();
        if (this.drawable != null) {
            this.drawable = null;
        }
    }
}
