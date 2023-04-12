package com.status.videomaker.Stickerss;

import android.graphics.PointF;

public class Vector_2D_Sticker extends PointF {
    public static float get_Angle_method(Vector_2D_Sticker vector2DSticker, Vector_2D_Sticker vector2D2Sticker) {
        vector2DSticker.normalize_method();
        vector2D2Sticker.normalize_method();
        return (float) ((Math.atan2((double) vector2D2Sticker.y, (double) vector2D2Sticker.x) - Math.atan2((double) vector2DSticker.y, (double) vector2DSticker.x)) * 57.29577951308232d);
    }

    public void normalize_method() {
        float sqrt = (float) Math.sqrt((double) ((this.x * this.x) + (this.y * this.y)));
        this.x /= sqrt;
        this.y /= sqrt;
    }
}
