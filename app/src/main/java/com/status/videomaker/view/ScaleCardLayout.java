package com.status.videomaker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.cardview.widget.CardView;

import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.R;

public class ScaleCardLayout extends CardView {
    public int mAspectRatioHeight = 360;
    public int mAspectRatioWidth = 640;

    public ScaleCardLayout(Context context) {
        super(context);
    }

    public ScaleCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context, attrs);
    }

    public ScaleCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context, attrs);
    }

    private void Init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PreviewImageView);
        this.mAspectRatioWidth = a.getInt(1, MainApplication.VIDEO_WIDTH);
        this.mAspectRatioHeight = a.getInt(0, MainApplication.VIDEO_HEIGHT);
        a.recycle();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int finalHeight;
        int finalWidth;
        if (this.mAspectRatioHeight == this.mAspectRatioWidth) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        int i = this.mAspectRatioHeight;
        int i2 = this.mAspectRatioWidth;
        int calculatedHeight = (int) (((float) (i * originalWidth)) / ((float) i2));
        if (calculatedHeight > originalHeight) {
            finalWidth = (int) (((float) (i2 * originalHeight)) / ((float) i));
            finalHeight = originalHeight;
        } else {
            finalWidth = originalWidth;
            finalHeight = calculatedHeight;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(finalWidth, 1073741824), MeasureSpec.makeMeasureSpec(finalHeight, 1073741824));
    }
}
