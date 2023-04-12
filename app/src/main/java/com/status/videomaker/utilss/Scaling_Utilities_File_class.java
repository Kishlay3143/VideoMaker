package com.status.videomaker.utilss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;

import java.io.IOException;

public class Scaling_Utilities_File_class {

    public static Bitmap Convert_Same_Size_New_method(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
        Bitmap doBlur = Fast_Blur_Photo_class.do_Blur_method(bitmap2, 25, true);
        Canvas canvas = new Canvas(doBlur);
        Paint paint = new Paint();
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float f = (float) i;
        float f2 = f / width;
        float f3 = (float) i2;
        float f4 = f3 / height;
        float f5 = (f3 - (height * f2)) / 2.0f;
        float f6 = 0.0f;

        if (f5 < 0.0f) {
            f6 = (f - (width * f4)) / 2.0f;
            f2 = f4;
            f5 = 0.0f;
        }

        Matrix matrix = new Matrix();
        matrix.postTranslate(f6, f5);
        matrix.preScale(f2, f2);
        canvas.drawBitmap(bitmap, matrix, paint);
        return doBlur;
    }

    public static Bitmap scale_Center_Crop_method(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == i && height == i2) {
            return bitmap;
        }
        float f = (float) i;
        float f2 = (float) width;
        float f3 = (float) i2;
        float f4 = (float) height;
        float max = Math.max(f / f2, f3 / f4);
        float f5 = f2 * max;
        float f6 = max * f4;
        float f7 = (f - f5) / 2.0f;
        float f8 = (f3 - f6) / 2.0f;
        RectF rectF = new RectF(f7, f8, f5 + f7, f6 + f8);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, (Rect) null, rectF, (Paint) null);
        return createBitmap;
    }

    public static Bitmap check_Bitmap_method(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        int i = 1;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        Bitmap decodeFile = BitmapFactory.decodeFile(str, new BitmapFactory.Options());
        try {
            String attribute = new ExifInterface(str).getAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION);
            if (attribute != null) {
                i = Integer.parseInt(attribute);
            }
            int i2 = 0;
            if (i == 6) {
                i2 = 90;
            }
            if (i == 3) {
                i2 = 180;
            }
            if (i == 8) {
                i2 = 270;
            }
            Matrix matrix = new Matrix();
            matrix.setRotate((float) i2, ((float) decodeFile.getWidth()) / 2.0f, ((float) decodeFile.getHeight()) / 2.0f);
            return Bitmap.createBitmap(decodeFile, 0, 0, options.outWidth, options.outHeight, matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
