package com.status.videomaker.Stickerss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;

public class Image_Utils_File_Sticker {
    public static Bitmap get_Resample_Image_Bitmap(Uri uri, Context context, int i) {
        try {
            return re_sample_Image(get_Real_Path_From_URI_method(uri, context), i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint({"UseValueOf"})
    public static Bitmap re_sample_Image(String str, int i) {
        int exifRotation;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = get_Close_st_Resample_Size(options.outWidth, options.outHeight, i);
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options2);
            if (decodeFile == null) {
                return null;
            }
            Matrix matrix = new Matrix();
            if (decodeFile.getWidth() > i || decodeFile.getHeight() > i) {
                BitmapFactory.Options resampling = get_Re_sampling(decodeFile.getWidth(), decodeFile.getHeight(), i);
                matrix.postScale(((float) resampling.outWidth) / ((float) decodeFile.getWidth()), ((float) resampling.outHeight) / ((float) decodeFile.getHeight()));
            }
            if (new Integer(Build.VERSION.SDK).intValue() > 4 && (exifRotation = Exif_Utils_File_Sticker.get_Exif_Rotation(str)) != 0) {
                matrix.postRotate((float) exifRotation);
            }
            return Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BitmapFactory.Options get_Re_sampling(int i, int i2, int i3) {
        float f;
        float f2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (i <= i2 && i2 > i) {
            f = (float) i3;
            f2 = (float) i2;
        } else {
            f = (float) i3;
            f2 = (float) i;
        }
        float f3 = f / f2;
        options.outWidth = (int) ((((float) i) * f3) + 0.5f);
        options.outHeight = (int) ((((float) i2) * f3) + 0.5f);
        return options;
    }

    public static int get_Close_st_Resample_Size(int i, int i2, int i3) {
        int max = Math.max(i, i2);
        int i4 = 1;
        while (true) {
            if (i4 >= 10000) {
                break;
            } else if (i4 * i3 > max) {
                i4--;
                break;
            } else {
                i4++;
            }
        }
        if (i4 > 0) {
            return i4;
        }
        return 1;
    }

    public static String get_Real_Path_From_URI_method(Uri uri, Context context) {
        try {
            Cursor query = context.getContentResolver().query(uri, null, null, null, null);
            if (query == null) {
                return uri.getPath();
            }
            query.moveToFirst();
            String string = query.getString(query.getColumnIndex("_data"));
            query.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return uri.toString();
        }
    }

    public static Bitmap re_size_Bitmap(Bitmap bitmap, int i, int i2) {
        float f;
        float f2;
        if (bitmap == null) {
            return null;
        }
        float f3 = (float) i;
        float f4 = (float) i2;
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float f5 = width / height;
        float f6 = height / width;
        if (width > f3) {
            f = f3 * f6;
        } else {
            if (height > f4) {
                f2 = f4 * f5;
            } else if (f5 > 0.75f) {
                f = f3 * f6;
            } else if (f6 > 1.5f) {
                f2 = f4 * f5;
            } else {
                f = f3 * f6;
            }
            f4 = f3 * f6;
            return Bitmap.createScaledBitmap(bitmap, (int) f3, (int) f4, false);
        }
        f3 = f4 * f5;
        return Bitmap.createScaledBitmap(bitmap, (int) f3, (int) f4, false);
    }

    public static int dp_To_Px_method(Context context, int i) {
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * ((float) i));
    }

}
