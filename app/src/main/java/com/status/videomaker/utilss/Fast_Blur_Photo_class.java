package com.status.videomaker.utilss;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.RequiresApi;
import androidx.core.view.MotionEventCompat;

import com.status.videomaker.Activitiess.MainApplication;

import java.lang.reflect.Array;

public class Fast_Blur_Photo_class {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur_Bitmap_method(Bitmap bitmap, int i, Context context) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript create = RenderScript.create(context);
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
        create2.setRadius((float) i);
        create2.setInput(createFromBitmap);
        create2.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        create.destroy();
        return createBitmap;
    }

    public static Bitmap do_Blur_method(Bitmap bitmap, int i, boolean z) {
        int[] iArr;
        Bitmap bitmap2 = bitmap;
        int i2 = i;
        if (Build.VERSION.SDK_INT >= 17) {
            return blur_Bitmap_method(bitmap2, i2, MainApplication.getInstance());
        }
        if (!z) {
            bitmap2 = bitmap2.copy(bitmap.getConfig(), true);
        }
        if (i2 < 1) {
            return null;
        }
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        int i3 = width * height;
        int[] iArr2 = new int[i3];
        bitmap2.getPixels(iArr2, 0, width, 0, 0, width, height);
        int i4 = width - 1;
        int i5 = height - 1;
        int i6 = i2 + i2 + 1;
        int[] iArr3 = new int[i3];
        int[] iArr4 = new int[i3];
        int[] iArr5 = new int[i3];
        int[] iArr6 = new int[Math.max(width, height)];
        int i7 = (i6 + 1) >> 1;
        int i8 = i7 * i7;
        int i9 = i8 * 256;
        int[] iArr7 = new int[i9];
        for (int i10 = 0; i10 < i9; i10++) {
            iArr7[i10] = i10 / i8;
        }
        int[][] iArr8 = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i6, 3});
        int i11 = i2 + 1;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < height) {
            Bitmap bitmap3 = bitmap2;
            int i15 = height;
            int i16 = 0;
            int i17 = 0;
            int i18 = 0;
            int i19 = 0;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            int i24 = -i2;
            int i25 = 0;
            while (i24 <= i2) {
                int i26 = i5;
                int[] iArr9 = iArr6;
                int i27 = iArr2[Math.min(i4, Math.max(i24, 0)) + i13];
                int[] iArr10 = iArr8[i24 + i2];
                iArr10[0] = (i27 & 16711680) >> 16;
                iArr10[1] = (i27 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr10[2] = i27 & 255;
                int abs = i11 - Math.abs(i24);
                i25 += iArr10[0] * abs;
                i16 += iArr10[1] * abs;
                i17 += iArr10[2] * abs;
                if (i24 > 0) {
                    i21 += iArr10[0];
                    i22 += iArr10[1];
                    i23 += iArr10[2];
                } else {
                    i18 += iArr10[0];
                    i19 += iArr10[1];
                    i20 += iArr10[2];
                }
                i24++;
                i5 = i26;
                iArr6 = iArr9;
            }
            int i28 = i5;
            int[] iArr11 = iArr6;
            int i29 = i25;
            int i30 = i2;
            int i31 = 0;
            while (i31 < width) {
                iArr3[i13] = iArr7[i29];
                iArr4[i13] = iArr7[i16];
                iArr5[i13] = iArr7[i17];
                int i32 = i29 - i18;
                int i33 = i16 - i19;
                int i34 = i17 - i20;
                int[] iArr12 = iArr8[((i30 - i2) + i6) % i6];
                int i35 = i18 - iArr12[0];
                int i36 = i19 - iArr12[1];
                int i37 = i20 - iArr12[2];
                if (i12 == 0) {
                    iArr = iArr7;
                    iArr11[i31] = Math.min(i31 + i2 + 1, i4);
                } else {
                    iArr = iArr7;
                }
                int i38 = iArr2[iArr11[i31] + i14];
                iArr12[0] = (i38 & 16711680) >> 16;
                iArr12[1] = (i38 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                iArr12[2] = i38 & 255;
                int i39 = i21 + iArr12[0];
                int i40 = i22 + iArr12[1];
                int i41 = i23 + iArr12[2];
                i29 = i32 + i39;
                i16 = i33 + i40;
                i17 = i34 + i41;
                i30 = (i30 + 1) % i6;
                int[] iArr13 = iArr8[i30 % i6];
                i18 = i35 + iArr13[0];
                i19 = i36 + iArr13[1];
                i20 = i37 + iArr13[2];
                i21 = i39 - iArr13[0];
                i22 = i40 - iArr13[1];
                i23 = i41 - iArr13[2];
                i13++;
                i31++;
                iArr7 = iArr;
            }
            int[] iArr14 = iArr7;
            i14 += width;
            i12++;
            bitmap2 = bitmap3;
            height = i15;
            i5 = i28;
            iArr6 = iArr11;
        }
        Bitmap bitmap4 = bitmap2;
        int i42 = i5;
        int[] iArr15 = iArr6;
        int i43 = height;
        int[] iArr16 = iArr7;
        int i44 = 0;
        while (i44 < width) {
            int i45 = -i2;
            int i46 = i6;
            int[] iArr17 = iArr2;
            int i47 = 0;
            int i48 = 0;
            int i49 = 0;
            int i50 = 0;
            int i51 = 0;
            int i52 = 0;
            int i53 = 0;
            int i54 = i45;
            int i55 = i45 * width;
            int i56 = 0;
            int i57 = 0;
            while (i54 <= i2) {
                int i58 = width;
                int max = Math.max(0, i55) + i44;
                int[] iArr18 = iArr8[i54 + i2];
                iArr18[0] = iArr3[max];
                iArr18[1] = iArr4[max];
                iArr18[2] = iArr5[max];
                int abs2 = i11 - Math.abs(i54);
                i56 += iArr3[max] * abs2;
                i57 += iArr4[max] * abs2;
                i47 += iArr5[max] * abs2;
                if (i54 > 0) {
                    i51 += iArr18[0];
                    i52 += iArr18[1];
                    i53 += iArr18[2];
                } else {
                    i48 += iArr18[0];
                    i49 += iArr18[1];
                    i50 += iArr18[2];
                }
                int i59 = i42;
                if (i54 < i59) {
                    i55 += i58;
                }
                i54++;
                i42 = i59;
                width = i58;
            }
            int i60 = width;
            int i61 = i42;
            int i62 = i44;
            int i63 = i2;
            int i64 = i43;
            int i65 = 0;
            while (i65 < i64) {
                iArr17[i62] = (iArr17[i62] & -16777216) | (iArr16[i56] << 16) | (iArr16[i57] << 8) | iArr16[i47];
                int i66 = i56 - i48;
                int i67 = i57 - i49;
                int i68 = i47 - i50;
                int[] iArr19 = iArr8[((i63 - i2) + i46) % i46];
                int i69 = i48 - iArr19[0];
                int i70 = i49 - iArr19[1];
                int i71 = i50 - iArr19[2];
                if (i44 == 0) {
                    iArr15[i65] = Math.min(i65 + i11, i61) * i60;
                }
                int i72 = iArr15[i65] + i44;
                iArr19[0] = iArr3[i72];
                iArr19[1] = iArr4[i72];
                iArr19[2] = iArr5[i72];
                int i73 = i51 + iArr19[0];
                int i74 = i52 + iArr19[1];
                int i75 = i53 + iArr19[2];
                i56 = i66 + i73;
                i57 = i67 + i74;
                i47 = i68 + i75;
                i63 = (i63 + 1) % i46;
                int[] iArr20 = iArr8[i63];
                i48 = i69 + iArr20[0];
                i49 = i70 + iArr20[1];
                i50 = i71 + iArr20[2];
                i51 = i73 - iArr20[0];
                i52 = i74 - iArr20[1];
                i53 = i75 - iArr20[2];
                i62 += i60;
                i65++;
                i2 = i;
            }
            i44++;
            i2 = i;
            i42 = i61;
            i43 = i64;
            i6 = i46;
            iArr2 = iArr17;
            width = i60;
        }
        int i76 = width;
        bitmap4.setPixels(iArr2, 0, i76, 0, 0, i76, i43);
        return bitmap4;
    }
}
