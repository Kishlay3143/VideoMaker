package com.status.videomaker.videolib.libffmpeg;

import android.content.Context;
import android.text.TextUtils;

import com.status.videomaker.videolib.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.status.videomaker.videolib.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.lang.reflect.Array;
import java.util.Map;

import kotlin.jvm.internal.LongCompanionObject;

public class FFmpeg implements FFmpegInterface {
    private static final long MINIMUM_TIMEOUT = 10000;
    public static FFmpeg instance;
    private static int[] $SWITCH_TABLE$com$videolib$libffmpeg$CpuArch = null;
    private final Context context;
    private FFmpegExecuteAsyncTask ffmpegExecuteAsyncTask;
    private FFmpegLoadLibraryAsyncTask ffmpegLoadLibraryAsyncTask;
    private long timeout = LongCompanionObject.MAX_VALUE;

    private FFmpeg(Context context2) {
        Context applicationContext = context2.getApplicationContext();
        this.context = applicationContext;
        Log.setDEBUG(Util.isDebug(applicationContext));
    }

    static int[] $SWITCH_TABLE$com$videolib$libffmpeg$CpuArch() {
        int[] var0 = $SWITCH_TABLE$com$videolib$libffmpeg$CpuArch;
        int[] var1 = var0;
        if (var0 == null) {
            var1 = new int[CpuArch.values().length];

            try {
                var1[CpuArch.ARMv7.ordinal()] = 2;
            } catch (NoSuchFieldError var4) {
            }

            try {
                var1[CpuArch.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError var3) {
            }

            try {
                var1[CpuArch.x86.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
            }

            $SWITCH_TABLE$com$videolib$libffmpeg$CpuArch = var1;
        }

        return var1;
    }

    public static FFmpeg getInstance(Context context2) {
        if (instance == null) {
            instance = new FFmpeg(context2);
        }
        return instance;
    }

    public String getLibraryFFmpegVersion() {
        return "n2.4.2";
    }

    public void loadBinary(FFmpegLoadBinaryResponseHandler fFmpegLoadBinaryResponseHandler) throws FFmpegNotSupportedException {
        String str;
        Log.d(CpuArchHelper.getCpuArch());
        int i = $SWITCH_TABLE$com$videolib$libffmpeg$CpuArch()[CpuArchHelper.getCpuArch().ordinal()];
        if (i == 1) {
            str = "src/main/jniLibs/x86";
        } else if (i == 2) {
            str = "src/main/jniLibs/armeabi-v7a";
        } else if (i != 3) {
            str = null;
        } else {
            throw new FFmpegNotSupportedException("Device not supported");
        }
        if (!TextUtils.isEmpty(str)) {
            FFmpegLoadLibraryAsyncTask fFmpegLoadLibraryAsyncTask = new FFmpegLoadLibraryAsyncTask(this.context, str, fFmpegLoadBinaryResponseHandler);
            this.ffmpegLoadLibraryAsyncTask = fFmpegLoadLibraryAsyncTask;
            fFmpegLoadLibraryAsyncTask.execute(new Void[0]);
            return;
        }
        throw new FFmpegNotSupportedException("Device not supported");
    }

    public void execute(Map<String, String> map, String[] strArr, FFmpegExecuteResponseHandler fFmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException {
        FFmpegExecuteAsyncTask fFmpegExecuteAsyncTask = this.ffmpegExecuteAsyncTask;
        if (fFmpegExecuteAsyncTask != null && !fFmpegExecuteAsyncTask.isProcessCompleted()) {
            throw new FFmpegCommandAlreadyRunningException("FFmpeg command is already running, you are only allowed to run single command at a time");
        } else if (strArr.length != 0) {
            FFmpegExecuteAsyncTask fFmpegExecuteAsyncTask2 = new FFmpegExecuteAsyncTask((String[]) concatenate(new String[]{FileUtils.getFFmpeg(this.context, map)}, strArr), this.timeout, fFmpegExecuteResponseHandler);
            this.ffmpegExecuteAsyncTask = fFmpegExecuteAsyncTask2;
            fFmpegExecuteAsyncTask2.execute(new Void[0]);
        } else {
            throw new IllegalArgumentException("shell command cannot be empty");
        }
    }

    public Object[] concatenate(Object[] var1, Object[] var2) {
        int var3 = var1.length;
        int var4 = var2.length;
        Object[] var5 = (Object[]) Array.newInstance(var1.getClass().getComponentType(), var3 + var4);
        System.arraycopy(var1, 0, var5, 0, var3);
        System.arraycopy(var2, 0, var5, var3, var4);
        return (Object[]) var5;
    }


    public void execute(String[] strArr, FFmpegExecuteResponseHandler fFmpegExecuteResponseHandler) throws FFmpegCommandAlreadyRunningException {
        execute((Map<String, String>) null, strArr, fFmpegExecuteResponseHandler);
    }

    public String getDeviceFFmpegVersion() throws FFmpegCommandAlreadyRunningException {
        CommandResult runWaitFor = new ShellCommand().runWaitFor(new String[]{FileUtils.getFFmpeg(this.context), "-version"});
        return runWaitFor.success ? runWaitFor.output.split(" ")[2] : "";
    }

    public boolean isFFmpegCommandRunning() {
        FFmpegExecuteAsyncTask fFmpegExecuteAsyncTask = this.ffmpegExecuteAsyncTask;
        return fFmpegExecuteAsyncTask != null && !fFmpegExecuteAsyncTask.isProcessCompleted();
    }

    public boolean killRunningProcesses() {
        return Util.killAsync(this.ffmpegLoadLibraryAsyncTask) || Util.killAsync(this.ffmpegExecuteAsyncTask);
    }

    public void setTimeout(long j) {
        if (j >= 10000) {
            this.timeout = j;
        }
    }
}
