package com.status.videomaker.TxtStrikerView;

import android.view.MotionEvent;

public class ZoomIconEvent implements StickerIconEvent {
    @Override
    public void onActionDown(ImgStickerView imgStickerView, MotionEvent motionEvent) {
    }

    @Override
    public void onActionMove(ImgStickerView imgStickerView, MotionEvent motionEvent) {
        imgStickerView.zoomAndRotateCurrentSticker(motionEvent);
    }

    @Override
    public void onActionUp(ImgStickerView imgStickerView, MotionEvent motionEvent) {
        if (imgStickerView.getOnStickerOperationListener() != null) {
            imgStickerView.getOnStickerOperationListener().onStickerZoomFinished(imgStickerView.getCurrentSticker());
        }
    }
}
