package com.status.videomaker.Command;

import android.util.Log;

import java.util.Arrays;

import Jni.FFmpegCmd;

public class Editor {

    public static void execCmd(String[] str, long j, final OnEditorListener onEditorListener) {
        Log.e("ag--12", "execCmd : " + Arrays.toString(str));

        FFmpegCmd.exec(str, j, new OnEditorListener() {
            public void onSuccess() {
                Log.e("ag--12", "execCmd onSuccess : ");
                onEditorListener.onSuccess();
            }

            public void onFailure() {
                Log.e("ag--12", "execCmd onFailure : ");
                onEditorListener.onFailure();
            }

            public void onProgress(float f) {
                Log.e("ag--12", "execCmd onProgress : ");
                onEditorListener.onProgress(f);
            }
        });
    }
}
