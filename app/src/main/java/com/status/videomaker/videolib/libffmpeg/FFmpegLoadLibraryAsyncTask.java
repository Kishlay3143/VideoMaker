package com.status.videomaker.videolib.libffmpeg;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;

class FFmpegLoadLibraryAsyncTask extends AsyncTask<Void, Void, Boolean> {
    private final Context context;
    private final String cpuArchNameFromAssets;
    private final FFmpegLoadBinaryResponseHandler ffmpegLoadBinaryResponseHandler;

    FFmpegLoadLibraryAsyncTask(Context context2, String str, FFmpegLoadBinaryResponseHandler fFmpegLoadBinaryResponseHandler) {
        this.context = context2;
        this.cpuArchNameFromAssets = str;
        this.ffmpegLoadBinaryResponseHandler = fFmpegLoadBinaryResponseHandler;
    }

    public Boolean doInBackground(Void... voidArr) {
        File file = new File(FileUtils.getFFmpeg(this.context));
        if (file.exists() && isDeviceFFmpegVersionOld() && !file.delete()) {
            return false;
        }
        if (!file.exists()) {
            Context context2 = this.context;
            if (FileUtils.copyBinaryFromAssetsToData(context2, this.cpuArchNameFromAssets + File.separator + "ffmpeg", "ffmpeg")) {
                if (file.canExecute()) {
                    Log.d("FFmpeg is executable");
                    return true;
                }
                Log.d("FFmpeg is not executable, trying to make it executable ...");
                if (file.setExecutable(true)) {
                    return true;
                }
            }
        }
        return file.exists() && file.canExecute();
    }

    public void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
        if (this.ffmpegLoadBinaryResponseHandler != null) {
            if (bool.booleanValue()) {
                this.ffmpegLoadBinaryResponseHandler.onSuccess();
                this.ffmpegLoadBinaryResponseHandler.onSuccess(this.cpuArchNameFromAssets);
            } else {
                this.ffmpegLoadBinaryResponseHandler.onFailure();
                this.ffmpegLoadBinaryResponseHandler.onFailure(this.cpuArchNameFromAssets);
            }
            this.ffmpegLoadBinaryResponseHandler.onFinish();
        }
    }

    private boolean isDeviceFFmpegVersionOld() {
        return CpuArch.fromString(FileUtils.SHA1(FileUtils.getFFmpeg(this.context))).equals(CpuArch.NONE);
    }
}
