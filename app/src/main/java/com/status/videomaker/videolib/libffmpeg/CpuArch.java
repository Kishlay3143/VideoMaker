package com.status.videomaker.videolib.libffmpeg;

import android.text.TextUtils;

enum CpuArch {
    x86("0dd4dbad305ff197a1ea9e6158bd2081d229e70e"),
    ARMv7("871888959ba2f063e18f56272d0d98ae01938ceb"),
    NONE((String) null);

    private String sha1;

    private CpuArch(String str) {
        this.sha1 = str;
    }

    static CpuArch fromString(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (CpuArch cpuArch : values()) {
                if (str.equalsIgnoreCase(cpuArch.sha1)) {
                    return cpuArch;
                }
            }
        }
        return NONE;
    }

    public String getSha1() {
        return this.sha1;
    }
}
