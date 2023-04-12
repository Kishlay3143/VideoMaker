package com.status.videomaker.Stickerss;

import android.media.ExifInterface;
import android.text.TextUtils;

public class Exif_Utils_File_Sticker {
    private Exif_Utils_File_Sticker() {
    }

    public static int get_Exif_Rotation(String str) {
        try {
            String attribute = new ExifInterface(str).getAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION);
            if (TextUtils.isEmpty(attribute)) {
                return 0;
            }
            int parseInt = Integer.parseInt(attribute);
            if (parseInt == 3) {
                return 180;
            }
            if (parseInt == 6) {
                return 90;
            }
            if (parseInt != 8) {
                return 0;
            }
            return 270;
        } catch (Exception unused) {
            return 0;
        }
    }
}
