package com.status.videomaker.Command;

public interface OnEditorListener {
    void onFailure();

    void onProgress(float f);

    void onSuccess();
}
