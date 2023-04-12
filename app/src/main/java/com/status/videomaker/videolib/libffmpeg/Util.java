package com.status.videomaker.videolib.libffmpeg;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Util {
    public static final boolean IS_GINGERBREAD_MR1;
    public static final boolean IS_ISC = (Build.VERSION.SDK_INT >= 14);
    public static final boolean IS_JBMR2 = (Build.VERSION.SDK_INT >= 18);
    public static final File project_dir;
    public static final File temp_animated_video_dir;
    public static final File temp_audio_dir;
    public static final File temp_simple_video_dir;

    static {
        boolean z = true;
        File file = new File(Environment.getExternalStorageDirectory(), "/EscrowFFmpeg");
        project_dir = file;
        temp_animated_video_dir = new File(file, "/.temp/anim_video");
        temp_audio_dir = new File(file, "/.temp/audio");
        temp_simple_video_dir = new File(file, "/.temp/video");
        if (Build.VERSION.SDK_INT < 10) {
            z = false;
        }
        IS_GINGERBREAD_MR1 = z;
        makeDirectorys();
    }

    public static void makeDirectorys() {
        File file = project_dir;
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = temp_audio_dir;
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File file3 = temp_simple_video_dir;
        if (!file3.exists()) {
            file3.mkdirs();
        }
        File file4 = temp_animated_video_dir;
        if (!file4.exists()) {
            file4.mkdirs();
        }
    }

    public static boolean isDebug(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationContext().getApplicationInfo();
        int i = applicationInfo.flags & 2;
        applicationInfo.flags = i;
        return i != 0;
    }

    public static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void close(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static String convertInputStreamToString(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return sb.toString();
                }
                sb.append(readLine);
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static void destroyProcess(Process process) {
        if (process != null) {
            process.destroy();
        }
    }

    public static boolean killAsync(AsyncTask<?, ?, ?> asyncTask) {
        return asyncTask != null && !asyncTask.isCancelled() && asyncTask.cancel(true);
    }

    public static boolean isProcessCompleted(Process process) {
        if (process == null) {
            return true;
        }
        try {
            process.exitValue();
            return true;
        } catch (IllegalThreadStateException unused) {
            return false;
        }
    }
}
