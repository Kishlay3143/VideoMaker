package com.status.videomaker.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.status.videomaker.R;
import com.status.videomaker.utilss.Utils_class;

import java.util.Iterator;

public abstract class StickerView extends FrameLayout {
    public static final String TAG = "com.knef.stickerView";
    private double centerX;
    private double centerY;
    private BorderView iv_border;
    private ImageView iv_delete;
    private ImageView iv_flip;
    private ImageView iv_scale;
    private float move_orgX = -1.0f;
    private float move_orgY = -1.0f;
    private float scale_orgX = -1.0f;
    private float scale_orgY = -1.0f;
    private OnTouchListener mTouchListener = new OnTouchListener() {

        @TargetApi(11)
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int i;
            if (view.getTag().equals("DraggableViewGroup")) {
                Utils_class.view_id = view.getId();
                int action = motionEvent.getAction();
                if (action == 0) {
                    StickerView.this.hideButtonAndBorder();
                    Log.v(StickerView.TAG, "sticker view action down");
                    StickerView.this.move_orgX = motionEvent.getRawX();
                    StickerView.this.move_orgY = motionEvent.getRawY();
                    return true;
                } else if (action == 1) {
                    StickerView.this.HideAllBorder();
                    StickerView.this.ShowButtonAndBorder();
                    return true;
                } else if (action != 2) {
                    return true;
                } else {
                    Log.v(StickerView.TAG, "sticker view action move");
                    float rawX = motionEvent.getRawX() - StickerView.this.move_orgX;
                    float rawY = motionEvent.getRawY() - StickerView.this.move_orgY;
                    StickerView stickerView = StickerView.this;
                    stickerView.setX(stickerView.getX() + rawX);
                    StickerView stickerView2 = StickerView.this;
                    stickerView2.setY(stickerView2.getY() + rawY);
                    StickerView.this.move_orgX = motionEvent.getRawX();
                    StickerView.this.move_orgY = motionEvent.getRawY();
                    return true;
                }
            } else if (!view.getTag().equals("iv_scale")) {
                return true;
            } else {
                int action2 = motionEvent.getAction();
                if (action2 == 0) {
                    StickerView.this.scale_orgX = motionEvent.getRawX();
                    StickerView.this.scale_orgY = motionEvent.getRawY();
                    StickerView stickerView3 = StickerView.this;
                    stickerView3.centerX = (double) (stickerView3.getX() + ((View) StickerView.this.getParent()).getX() + (((float) StickerView.this.getWidth()) / 2.0f));
                    int identifier = StickerView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    double dimensionPixelSize = (double) (identifier > 0 ? StickerView.this.getResources().getDimensionPixelSize(identifier) : 0);
                    StickerView stickerView4 = StickerView.this;
                    double y = (double) (stickerView4.getY() + ((View) StickerView.this.getParent()).getY());
                    Double.isNaN(y);
                    Double.isNaN(dimensionPixelSize);
                    double d = y + dimensionPixelSize;
                    double height = (double) (((float) StickerView.this.getHeight()) / 2.0f);
                    Double.isNaN(height);
                    stickerView4.centerY = d + height;
                    return true;
                } else if (action2 == 1) {
                    Log.v(StickerView.TAG, "iv_scale action up");
                    return true;
                } else if (action2 != 2) {
                    return true;
                } else {
                    double atan2 = Math.atan2((double) (motionEvent.getRawY() - StickerView.this.scale_orgY), (double) (motionEvent.getRawX() - StickerView.this.scale_orgX));
                    double d2 = (double) StickerView.this.scale_orgY;
                    double d3 = StickerView.this.centerY;
                    Double.isNaN(d2);
                    double d4 = d2 - d3;
                    double d5 = (double) StickerView.this.scale_orgX;
                    double d6 = StickerView.this.centerX;
                    Double.isNaN(d5);
                    double abs = (Math.abs(atan2 - Math.atan2(d4, d5 - d6)) * 180.0d) / 3.141592653589793d;
                    StickerView stickerView5 = StickerView.this;
                    double length = stickerView5.getLength(stickerView5.centerX, StickerView.this.centerY, (double) StickerView.this.scale_orgX, (double) StickerView.this.scale_orgY);
                    StickerView stickerView6 = StickerView.this;
                    double length2 = stickerView6.getLength(stickerView6.centerX, StickerView.this.centerY, (double) motionEvent.getRawX(), (double) motionEvent.getRawY());
                    int convertDpToPixel = StickerView.convertDpToPixel(100.0f, StickerView.this.getContext());
                    if (length2 > length && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                        double round = (double) Math.round(Math.max((double) Math.abs(motionEvent.getRawX() - StickerView.this.scale_orgX), (double) Math.abs(motionEvent.getRawY() - StickerView.this.scale_orgY)));
                        ViewGroup.LayoutParams layoutParams = StickerView.this.getLayoutParams();
                        double d7 = (double) layoutParams.width;
                        Double.isNaN(d7);
                        Double.isNaN(round);
                        layoutParams.width = (int) (d7 + round);
                        ViewGroup.LayoutParams layoutParams2 = StickerView.this.getLayoutParams();
                        double d8 = (double) layoutParams2.height;
                        Double.isNaN(d8);
                        Double.isNaN(round);
                        layoutParams2.height = (int) (d8 + round);
                        StickerView.this.onScaling(true);
                    } else if (length2 < length && ((abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d) && StickerView.this.getLayoutParams().width > (i = convertDpToPixel / 2) && StickerView.this.getLayoutParams().height > i)) {
                        double round2 = (double) Math.round(Math.max((double) Math.abs(motionEvent.getRawX() - StickerView.this.scale_orgX), (double) Math.abs(motionEvent.getRawY() - StickerView.this.scale_orgY)));
                        ViewGroup.LayoutParams layoutParams3 = StickerView.this.getLayoutParams();
                        double d9 = (double) layoutParams3.width;
                        Double.isNaN(d9);
                        Double.isNaN(round2);
                        layoutParams3.width = (int) (d9 - round2);
                        ViewGroup.LayoutParams layoutParams4 = StickerView.this.getLayoutParams();
                        double d10 = (double) layoutParams4.height;
                        Double.isNaN(d10);
                        Double.isNaN(round2);
                        layoutParams4.height = (int) (d10 - round2);
                        StickerView.this.onScaling(false);
                    }
                    double rawY2 = (double) motionEvent.getRawY();
                    double d11 = StickerView.this.centerY;
                    Double.isNaN(rawY2);
                    double d12 = rawY2 - d11;
                    double rawX2 = (double) motionEvent.getRawX();
                    double d13 = StickerView.this.centerX;
                    Double.isNaN(rawX2);
                    StickerView.this.setRotation(((float) ((Math.atan2(d12, rawX2 - d13) * 180.0d) / 3.141592653589793d)) - 45.0f);
                    StickerView.this.onRotating();
                    StickerView.this.scale_orgX = motionEvent.getRawX();
                    StickerView.this.scale_orgY = motionEvent.getRawY();
                    StickerView.this.postInvalidate();
                    StickerView.this.requestLayout();
                    return true;
                }
            }
        }
    };


    public StickerView(Context context) {
        super(context);
        init(context);
    }


    public StickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public StickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public static int convertDpToPixel(float f, Context context) {
        return (int) (f * (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f));
    }

    public abstract View getMainView();

    public void onRotating() {
    }

    public void onScaling(boolean z) {
    }

    private void init(Context context) {
        this.iv_border = new BorderView(context);
        this.iv_scale = new ImageView(context);
        this.iv_delete = new ImageView(context);
        this.iv_flip = new ImageView(context);
        this.iv_scale.setImageResource(R.drawable.ic_refresh);
        this.iv_delete.setImageResource(R.drawable.ic_close);
        this.iv_flip.setImageResource(R.drawable.horizontal_icon);
        setTag("DraggableViewGroup");
        this.iv_border.setTag("iv_border");
        this.iv_scale.setTag("iv_scale");
        this.iv_delete.setTag("iv_delete");
        this.iv_flip.setTag("iv_flip");
        int convertDpToPixel = convertDpToPixel(30.0f, getContext()) / 2;
        int convertDpToPixel2 = convertDpToPixel(100.0f, getContext());
        LayoutParams layoutParams = new LayoutParams(convertDpToPixel2, convertDpToPixel2);
        layoutParams.gravity = 17;
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.setMargins(convertDpToPixel, convertDpToPixel, convertDpToPixel, convertDpToPixel);
        LayoutParams layoutParams3 = new LayoutParams(-1, -1);
        layoutParams3.setMargins(convertDpToPixel, convertDpToPixel, convertDpToPixel, convertDpToPixel);
        LayoutParams layoutParams4 = new LayoutParams(convertDpToPixel(30.0f, getContext()), convertDpToPixel(30.0f, getContext()));
        layoutParams4.gravity = 85;
        LayoutParams layoutParams5 = new LayoutParams(convertDpToPixel(30.0f, getContext()), convertDpToPixel(30.0f, getContext()));
        layoutParams5.gravity = 53;
        LayoutParams layoutParams6 = new LayoutParams(convertDpToPixel(30.0f, getContext()), convertDpToPixel(30.0f, getContext()));
        layoutParams6.gravity = 51;
        this.iv_border.setVisibility(8);
        setLayoutParams(layoutParams);
        addView(getMainView(), layoutParams2);
        addView(this.iv_border, layoutParams3);
        addView(this.iv_scale, layoutParams4);
        addView(this.iv_delete, layoutParams5);
        addView(this.iv_flip, layoutParams6);
        setOnTouchListener(this.mTouchListener);
        this.iv_scale.setOnTouchListener(this.mTouchListener);
        this.iv_delete.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                if (StickerView.this.getParent() != null) {
                    ((ViewGroup) StickerView.this.getParent()).removeView(StickerView.this);
                    if (Utils_class.Stickers_list.contains(view)) {
                        Utils_class.Stickers_list.remove(view);
                        Utils_class.seekbar_progress_AL.remove(Utils_class.view_id - 1);
                        Utils_class.view_id = 0;
                    }
                }
            }
        });
        this.iv_flip.setOnClickListener(new OnClickListener() {

            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                Log.v(StickerView.TAG, "flip the view");
                View mainView = StickerView.this.getMainView();
                float f = -180.0f;
                if (mainView.getRotationY() == -180.0f) {
                    f = 0.0f;
                }
                mainView.setRotationY(f);
                mainView.invalidate();
                StickerView.this.requestLayout();
            }
        });
    }

    @SuppressLint({"NewApi"})
    public boolean isFlip() {
        return getMainView().getRotationY() == -180.0f;
    }

    public void hideButtonAndBorder() {
        this.iv_border.setVisibility(8);
        this.iv_delete.setVisibility(8);
        this.iv_flip.setVisibility(8);
        this.iv_scale.setVisibility(8);
    }

    public void ShowButtonAndBorder() {
        this.iv_border.setVisibility(8);
        this.iv_delete.setVisibility(0);
        this.iv_flip.setVisibility(0);
        this.iv_scale.setVisibility(0);
    }

    private void HideAllBorder() {
        Iterator<StickerImage> it = Utils_class.Stickers_list.iterator();
        while (it.hasNext()) {
            it.next().hideButtonAndBorder();
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private double getLength(double d, double d2, double d3, double d4) {
        return Math.sqrt(Math.pow(d4 - d2, 2.0d) + Math.pow(d3 - d, 2.0d));
    }

    public View getImageViewFlip() {
        return this.iv_flip;
    }

    public class BorderView extends View {
        public BorderView(Context context) {
            super(context);
        }

        public BorderView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public BorderView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            Rect rect = new Rect();
            rect.left = getLeft() - layoutParams.leftMargin;
            rect.top = getTop() - layoutParams.topMargin;
            rect.right = getRight() - layoutParams.rightMargin;
            rect.bottom = getBottom() - layoutParams.bottomMargin;
            Paint paint = new Paint();
            paint.setStrokeWidth(6.0f);
            paint.setColor(-1);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rect, paint);
        }
    }
}
