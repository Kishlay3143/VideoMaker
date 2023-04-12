package com.status.videomaker.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.R;

public class PreviewPhoto extends ImageView {
    public static int mAspectRatioHeight = 360;
    public static int mAspectRatioWidth = 640;

    public PreviewPhoto(Context context) {
        super(context);
    }

    public PreviewPhoto(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Init(context, attributeSet);
    }

    public PreviewPhoto(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Init(context, attributeSet);
    }

    private void Init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TextAppearance);
        mAspectRatioWidth = obtainStyledAttributes.getInt(0, MainApplication.VIDEO_WIDTH);
        mAspectRatioHeight = obtainStyledAttributes.getInt(1, MainApplication.VIDEO_HEIGHT);
        Log.e("ag--12", "mAspectRatioWidth:" + mAspectRatioWidth + " mAspectRatioHeight:" + mAspectRatioHeight);
        obtainStyledAttributes.recycle();
    }

    public void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int i3 = mAspectRatioHeight;
        int i4 = mAspectRatioWidth;
        int i5 = (int) (((float) (i3 * size)) / ((float) i4));
        if (i5 > size2) {
            size = (int) (((float) (i4 * size2)) / ((float) i3));
        } else {
            size2 = i5;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }
}
