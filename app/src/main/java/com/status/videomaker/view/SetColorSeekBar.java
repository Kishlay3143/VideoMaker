package com.status.videomaker.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.SeekBar;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;

public class SetColorSeekBar extends AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener {
    private OnColorSeekBarChangeListener mOnColorSeekbarChangeListener;

    public SetColorSeekBar(Context context) {
        super(context);
        setOnSeekBarChangeListener(this);
    }

    public SetColorSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnSeekBarChangeListener(this);
    }

    public SetColorSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOnSeekBarChangeListener(this);
    }

    public void setOnColorSeekbarChangeListener(OnColorSeekBarChangeListener onColorSeekBarChangeListener) {
        this.mOnColorSeekbarChangeListener = onColorSeekBarChangeListener;
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        init();
    }

    public void init() {
        LinearGradient linearGradient = Build.VERSION.SDK_INT >= 16 ? new LinearGradient(0.0f, 0.0f, (float) (getMeasuredWidth() - getThumb().getIntrinsicWidth()), 0.0f, new int[]{ViewCompat.MEASURED_STATE_MASK, -16776961, -16711936, -16711681, SupportMenu.CATEGORY_MASK, -65281, InputDeviceCompat.SOURCE_ANY, -1}, (float[]) null, Shader.TileMode.CLAMP) : null;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.getPaint().setShader(linearGradient);
        setProgressDrawable(shapeDrawable);
        setProgress(600);
        setMax(1791);
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        int i2;
        int i3;
        if (this.mOnColorSeekbarChangeListener != null) {
            int i4 = 0;
            if (i < 256) {
                i3 = i;
                i2 = 0;
            } else if (i < 512) {
                i2 = i % 256;
                i3 = 256 - i2;
            } else if (i < 768) {
                i3 = i % 256;
                i2 = 255;
            } else if (i < 1024) {
                i4 = i % 256;
                i2 = 256 - i4;
                i3 = i2;
            } else {
                if (i < 1280) {
                    i3 = i % 256;
                    i2 = 0;
                } else if (i < 1536) {
                    int i5 = i % 256;
                    i3 = 256 - i5;
                    i2 = i5;
                } else if (i < 1792) {
                    i3 = i % 256;
                    i2 = 255;
                } else {
                    i2 = 0;
                    i3 = 0;
                }
                i4 = 255;
            }
            try {
                this.mOnColorSeekbarChangeListener.onColorChanged(seekBar, Color.argb(255, i4, i2, i3), z);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        OnColorSeekBarChangeListener onColorSeekBarChangeListener = this.mOnColorSeekbarChangeListener;
        if (onColorSeekBarChangeListener != null) {
            try {
                onColorSeekBarChangeListener.onStartTrackingTouch(seekBar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        OnColorSeekBarChangeListener onColorSeekBarChangeListener = this.mOnColorSeekbarChangeListener;
        if (onColorSeekBarChangeListener != null) {
            try {
                onColorSeekBarChangeListener.onStopTrackingTouch(seekBar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnColorSeekBarChangeListener {
        void onColorChanged(SeekBar seekBar, int i, boolean z);

        void onStartTrackingTouch(SeekBar seekBar);

        void onStopTrackingTouch(SeekBar seekBar);
    }
}
