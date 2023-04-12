package com.status.videomaker.TxtStrikerView;

import android.view.MotionEvent;

public interface StickerIconEvent {
    void onActionDown(ImgStickerView imgStickerView, MotionEvent motionEvent);

    void onActionMove(ImgStickerView imgStickerView, MotionEvent motionEvent);

    void onActionUp(ImgStickerView imgStickerView, MotionEvent motionEvent);
}
