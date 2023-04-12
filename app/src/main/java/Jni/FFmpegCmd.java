package Jni;

import com.status.videomaker.Command.OnEditorListener;

public class FFmpegCmd {
    private static long duration;
    private static OnEditorListener listener;

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("avcodec");
        System.loadLibrary("swresample");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("avfilter");
        System.loadLibrary("avdevice");
        System.loadLibrary("ffmpeg-exec");
    }

    public static native int exec(int i, String[] strArr);

    public static native void exit();

    public static void onExecuted(int i) {
        OnEditorListener onEditorListener = listener;
        if (onEditorListener == null) {
            return;
        }
        if (i == 0) {
            onEditorListener.onProgress(1.0f);
            listener.onSuccess();
            listener = null;
            return;
        }
        onEditorListener.onFailure();
        listener = null;
    }

    public static void onProgress(float f) {
        OnEditorListener onEditorListener = listener;
        if (onEditorListener != null) {
            long j = duration;
            if (j != 0) {
                onEditorListener.onProgress((f / ((float) (j / 1000000))) * 0.95f);
            }
        }
    }

    public static void exec(String[] strArr, long j, OnEditorListener onEditorListener) {
        listener = onEditorListener;
        duration = j;
        exec(strArr.length, strArr);
    }
}
