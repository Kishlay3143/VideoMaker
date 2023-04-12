package com.status.videomaker.Command;

import java.util.ArrayList;

public class MVideo {
    private float clipDuration;
    private float clipStart;
    private ArrayList<Draw> epPics;
    private StringBuilder filter;
    private boolean isClip = false;
    private Crop mCrop;
    private String videoPath;

    public MVideo(String str) {
        this.videoPath = str;
        this.epPics = new ArrayList<>();
    }

    private StringBuilder getFilter() {
        StringBuilder sb = this.filter;
        if (sb == null || sb.toString().equals("")) {
            this.filter = new StringBuilder();
        } else {
            this.filter.append(",");
        }
        return this.filter;
    }


    @Deprecated
    public MVideo addText(int i, int i2, float f, String str, String str2, String str3) {
        StringBuilder filter2 = getFilter();
        this.filter = filter2;
        filter2.append("drawtext=fontfile=" + str2 + ":fontsize=" + f + ":fontcolor=" + str + ":x=" + i + ":y=" + i2 + ":text='" + str3 + "'");
        return this;
    }

    public class Crop {
        float height;
        float width;
        float x;
        float y;

        public Crop(float f, float f2, float f3, float f4) {
            this.width = f;
            this.height = f2;
            this.x = f3;
            this.y = f4;
        }

        public float getWidth() {
            return this.width;
        }

        public float getHeight() {
            return this.height;
        }

        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }
    }
}
