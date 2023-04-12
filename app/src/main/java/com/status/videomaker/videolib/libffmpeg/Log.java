package com.status.videomaker.videolib.libffmpeg;

class Log {
    private static boolean DEBUG = false;
    private static String TAG = FFmpeg.class.getSimpleName();

    Log() {
    }

    static void setDEBUG(boolean z) {
    }

    static void d(Object obj) {
        if (DEBUG) {
            android.util.Log.d(TAG, obj != null ? obj.toString() : null);
        }
    }

    static void i(Object obj) {
        if (DEBUG) {
            android.util.Log.i(TAG, obj != null ? obj.toString() : null);
        }
    }

    static void e(Object obj, Throwable th) {
        if (DEBUG) {
            android.util.Log.e(TAG, obj != null ? obj.toString() : null, th);
        }
    }

    static void e(Throwable th) {
        if (DEBUG) {
            android.util.Log.e(TAG, "", th);
        }
    }
}
