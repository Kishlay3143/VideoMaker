package com.status.videomaker.Command;

public class Draw {
    private boolean isAnimation;
    private String picFilter;
    private float picHeight;
    private String picPath;
    private float picWidth;
    private int picX;
    private int picY;
    private String time = "";

    public Draw(String str, int i, int i2, float f, float f2, boolean z) {
        this.picPath = str;
        this.picX = i;
        this.picY = i2;
        this.picWidth = f;
        this.picHeight = f2;
        this.isAnimation = z;
    }

    public Draw(String str, int i, int i2, float f, float f2, boolean z, int i3, int i4) {
        this.picPath = str;
        this.picX = i;
        this.picY = i2;
        this.picWidth = f;
        this.picHeight = f2;
        this.isAnimation = z;
        this.time = ":enable=between(t\\," + i3 + "\\," + i4 + ")";
    }

    public String getPicPath() {
        return this.picPath;
    }

    public int getPicX() {
        return this.picX;
    }

    public int getPicY() {
        return this.picY;
    }

    public float getPicWidth() {
        return this.picWidth;
    }

    public float getPicHeight() {
        return this.picHeight;
    }

    public boolean isAnimation() {
        return this.isAnimation;
    }

    public String getPicFilter() {
        if (this.picFilter == null) {
            return "";
        }
        return this.picFilter + ",";
    }

    public String getTime() {
        return this.time;
    }

    public void setPicFilter(String str) {
        this.picFilter = str;
    }
}
