package com.status.videomaker.videolib.libffmpeg;

import android.os.Build;

class CpuArchHelper {
    CpuArchHelper() {
    }

    static String getArm64CpuAbi() {
        return "arm64-v8a";
    }

    static String getArmeabiv7CpuAbi() {
        return "src/main/jniLibs/armeabi-v7a";
    }

    static String getX86_64CpuAbi() {
        return "x86_64";
    }

    static String getx86CpuAbi() {
        return "src/main/jniLibs/x86";
    }

    static CpuArch getCpuArch() {
        if (Build.CPU_ABI.equals(getx86CpuAbi())) {
            return CpuArch.x86;
        }
        if (Build.CPU_ABI.equals(getArmeabiv7CpuAbi())) {
            return CpuArch.ARMv7;
        }
        if (Build.CPU_ABI.equals(getX86_64CpuAbi())) {
            return CpuArch.x86;
        }
        if (Build.CPU_ABI.equals(getArm64CpuAbi())) {
            return CpuArch.ARMv7;
        }
        return CpuArch.NONE;
    }
}
