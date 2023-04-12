package com.status.videomaker.TxtStrikerView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BitmapStickerIcon extends DrawableSticker implements StickerIconEvent {
    private StickerIconEvent iconEvent;
    private float iconExtraRadius = 10.0f;
    private float iconRadius = 30.0f;
    private int position;
    private float x;
    private float y;

    public BitmapStickerIcon(Drawable drawable, int i) {
        super(drawable);
        this.position = i;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(this.x, this.y, this.iconRadius, paint);
        super.draw(canvas);
    }

    public float getX() {
        return this.x;
    }

    public void setX(float f) {
        this.x = f;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float f) {
        this.y = f;
    }

    public float getIconRadius() {
        return this.iconRadius;
    }

    public void setIconRadius(float f) {
        this.iconRadius = f;
    }

    public float getIconExtraRadius() {
        return this.iconExtraRadius;
    }

    public void setIconExtraRadius(float f) {
        this.iconExtraRadius = f;
    }

    @Override
    public void onActionDown(ImgStickerView imgStickerView, MotionEvent motionEvent) {
        StickerIconEvent stickerIconEvent = this.iconEvent;
        if (stickerIconEvent != null) {
            stickerIconEvent.onActionDown(imgStickerView, motionEvent);
        }
    }

    @Override
    public void onActionMove(ImgStickerView imgStickerView, MotionEvent motionEvent) {
        StickerIconEvent stickerIconEvent = this.iconEvent;
        if (stickerIconEvent != null) {
            stickerIconEvent.onActionMove(imgStickerView, motionEvent);
        }
    }

    @Override
    public void onActionUp(ImgStickerView imgStickerView, MotionEvent motionEvent) {
        StickerIconEvent stickerIconEvent = this.iconEvent;
        if (stickerIconEvent != null) {
            stickerIconEvent.onActionUp(imgStickerView, motionEvent);
        }
    }

    public StickerIconEvent getIconEvent() {
        return this.iconEvent;
    }

    public void setIconEvent(StickerIconEvent stickerIconEvent) {
        this.iconEvent = stickerIconEvent;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Gravity {
    }
}
