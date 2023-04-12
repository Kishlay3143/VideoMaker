package com.status.videomaker.TxtStrikerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.status.videomaker.R;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImgStickerView extends RelativeLayout {
    private static final String TAG = "StickerView";
    public final List<Sticker> stickers;
    private final float[] bitmapPoints;
    private final Paint borderPaint;
    private final float[] bounds;
    private final boolean bringToFrontCurrentSticker;
    private final PointF currentCenterPoint;
    private final Matrix downMatrix;
    private final List<BitmapStickerIcon> icons;
    private final Matrix moveMatrix;
    private final float[] point;
    private final boolean showBorder;
    private final boolean showIcons;
    private final Matrix sizeMatrix;
    private final RectF stickerRect;
    private final float[] tmp;
    private final int touchSlop;
    public Sticker handlingSticker;
    float scaleFactor;
    private boolean constrained;
    private BitmapStickerIcon currentIcon;
    private int currentMode;
    private float downX;
    private float downY;
    private long lastClickTime;
    private boolean locked;
    private PointF midPoint;
    private int minClickDelayTime;
    private float oldDistance;
    private float oldRotation;
    private OnStickerOperationListener onStickerOperationListener;

    public ImgStickerView(Context context) {
        this(context, null);
    }

    public ImgStickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @SuppressLint({"ResourceType"})
    public ImgStickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.stickers = new ArrayList();
        this.icons = new ArrayList(4);
        this.borderPaint = new Paint();
        this.stickerRect = new RectF();
        this.sizeMatrix = new Matrix();
        this.downMatrix = new Matrix();
        this.moveMatrix = new Matrix();
        this.bitmapPoints = new float[8];
        this.bounds = new float[8];
        this.point = new float[2];
        this.currentCenterPoint = new PointF();
        this.tmp = new float[2];
        this.midPoint = new PointF();
        this.oldDistance = 0.0f;
        this.oldRotation = 0.0f;
        this.currentMode = 0;
        this.lastClickTime = 0;
        this.minClickDelayTime = 200;
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar);
            this.showIcons = typedArray.getBoolean(4, true);
            this.showBorder = typedArray.getBoolean(3, true);
            this.bringToFrontCurrentSticker = typedArray.getBoolean(2, false);
            this.borderPaint.setAntiAlias(true);
            this.borderPaint.setColor(typedArray.getColor(1, -1));
            this.borderPaint.setAlpha(typedArray.getInteger(0, 255));
            configDefaultIcons();
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    public void configDefaultIcons() {
        BitmapStickerIcon bitmapStickerIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_close), 0);
        bitmapStickerIcon.setIconEvent(new DeleteIconEvent());
        BitmapStickerIcon bitmapStickerIcon2 = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_refresh), 3);
        bitmapStickerIcon2.setIconEvent(new ZoomIconEvent());
        this.icons.clear();
        this.icons.add(bitmapStickerIcon);
        this.icons.add(bitmapStickerIcon2);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            RectF rectF = this.stickerRect;
            rectF.left = (float) i;
            rectF.top = (float) i2;
            rectF.right = (float) i3;
            rectF.bottom = (float) i4;
        }
    }

    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawStickers(canvas);
    }

    public void drawStickers(Canvas canvas) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        for (int i = 0; i < this.stickers.size(); i++) {
            Sticker sticker = this.stickers.get(i);
            if (sticker != null) {
                sticker.draw(canvas);
            }
        }
        if (!(this.handlingSticker == null || this.locked)) {
            if (this.showBorder || this.showIcons) {
                getStickerPoints(this.handlingSticker, this.bitmapPoints);
                float[] fArr = this.bitmapPoints;
                float f6 = fArr[0];
                float f7 = fArr[1];
                float f8 = fArr[2];
                float f9 = fArr[3];
                float f10 = fArr[4];
                float f11 = fArr[5];
                float f12 = fArr[6];
                float f13 = fArr[7];
                if (this.showBorder) {
                    f4 = f13;
                    f3 = f12;
                    f2 = f11;
                    f = f10;
                    canvas.drawLine(f6, f7, f8, f9, this.borderPaint);
                    canvas.drawLine(f6, f7, f, f2, this.borderPaint);
                    canvas.drawLine(f8, f9, f3, f4, this.borderPaint);
                    canvas.drawLine(f3, f4, f, f2, this.borderPaint);
                } else {
                    f4 = f13;
                    f3 = f12;
                    f2 = f11;
                    f = f10;
                }
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(ViewCompat.MEASURED_STATE_MASK);
                paint.setAlpha(20);
                float f14 = f8;
                paint.setStrokeWidth((float) ((int) Math.sqrt(Math.pow((double) (f - f6), 2.0d) + Math.pow((double) (f2 - f7), 2.0d))));
                canvas.drawLine((f6 + f) / 2.0f, (f7 + f2) / 2.0f, (f14 + f3) / 2.0f, (f9 + f4) / 2.0f, paint);
                if (this.showIcons) {
                    float calculateRotation = calculateRotation(f3, f4, f, f2);
                    int i2 = 0;
                    while (i2 < this.icons.size()) {
                        BitmapStickerIcon bitmapStickerIcon = this.icons.get(i2);
                        int position = bitmapStickerIcon.getPosition();
                        if (position == 0) {
                            f5 = f14;
                            configIconMatrix(bitmapStickerIcon, f6, f7, calculateRotation);
                        } else if (position != 1) {
                            if (position == 2) {
                                configIconMatrix(bitmapStickerIcon, f, f2, calculateRotation);
                            } else if (position == 3) {
                                configIconMatrix(bitmapStickerIcon, f3, f4, calculateRotation);
                            }
                            f5 = f14;
                        } else {
                            f5 = f14;
                            configIconMatrix(bitmapStickerIcon, f5, f9, calculateRotation);
                        }
                        bitmapStickerIcon.draw(canvas, this.borderPaint);
                        i2++;
                        f14 = f5;
                    }
                }
            }
        }
    }

    public void configIconMatrix(@NonNull BitmapStickerIcon bitmapStickerIcon, float f, float f2, float f3) {
        bitmapStickerIcon.setX(f);
        bitmapStickerIcon.setY(f2);
        bitmapStickerIcon.getMatrix().reset();
        bitmapStickerIcon.getMatrix().postRotate(f3, (float) (bitmapStickerIcon.getWidth() / 2), (float) (bitmapStickerIcon.getHeight() / 2));
        bitmapStickerIcon.getMatrix().postTranslate(f - ((float) (bitmapStickerIcon.getWidth() / 2)), f2 - ((float) (bitmapStickerIcon.getHeight() / 2)));
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.locked) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        if (motionEvent.getAction() != 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        this.downX = motionEvent.getX();
        this.downY = motionEvent.getY();
        return (findCurrentIconTouched() == null && findHandlingSticker() == null) ? false : true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Sticker sticker;
        OnStickerOperationListener onStickerOperationListener2;
        if (this.locked) {
            return super.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                onTouchUp(motionEvent);
            } else if (action != 2) {
                if (action == 5) {
                    this.oldDistance = calculateDistance(motionEvent);
                    this.oldRotation = calculateRotation(motionEvent);
                    this.midPoint = calculateMidPoint(motionEvent);
                    Sticker sticker2 = this.handlingSticker;
                    if (sticker2 != null && isInStickerArea(sticker2, motionEvent.getX(1), motionEvent.getY(1)) && findCurrentIconTouched() == null) {
                        this.currentMode = 2;
                    }
                }
                if (!(this.currentMode != 2 || (sticker = this.handlingSticker) == null || (onStickerOperationListener2 = this.onStickerOperationListener) == null)) {
                    onStickerOperationListener2.onStickerZoomFinished(sticker);
                }
                this.currentMode = 0;
            } else {
                handleCurrentMode(motionEvent);
                invalidate();
            }
        } else if (!onTouchDown(motionEvent)) {
            return false;
        }
        return true;
    }

    public boolean onTouchDown(@NonNull MotionEvent motionEvent) {
        this.currentMode = 1;
        this.downX = motionEvent.getX();
        this.downY = motionEvent.getY();
        this.midPoint = calculateMidPoint();
        this.oldDistance = calculateDistance(this.midPoint.x, this.midPoint.y, this.downX, this.downY);
        this.oldRotation = calculateRotation(this.midPoint.x, this.midPoint.y, this.downX, this.downY);
        this.currentIcon = findCurrentIconTouched();
        BitmapStickerIcon bitmapStickerIcon = this.currentIcon;
        if (bitmapStickerIcon != null) {
            this.currentMode = 3;
            bitmapStickerIcon.onActionDown(this, motionEvent);
        } else {
            this.handlingSticker = findHandlingSticker();
        }
        Sticker sticker = this.handlingSticker;
        if (sticker != null) {
            this.downMatrix.set(sticker.getMatrix());
            if (this.bringToFrontCurrentSticker) {
                this.stickers.remove(this.handlingSticker);
                this.stickers.add(this.handlingSticker);
            }
            OnStickerOperationListener onStickerOperationListener2 = this.onStickerOperationListener;
            if (onStickerOperationListener2 != null) {
                onStickerOperationListener2.onStickerTouchedDown(this.handlingSticker);
            }
        }
        if (this.currentIcon == null && this.handlingSticker == null) {
            return false;
        }
        invalidate();
        return true;
    }

    public void onTouchUp(@NonNull MotionEvent motionEvent) {
        Sticker sticker;
        OnStickerOperationListener onStickerOperationListener2;
        Sticker sticker2;
        OnStickerOperationListener onStickerOperationListener3;
        BitmapStickerIcon bitmapStickerIcon;
        long uptimeMillis = SystemClock.uptimeMillis();
        if (!(this.currentMode != 3 || (bitmapStickerIcon = this.currentIcon) == null || this.handlingSticker == null)) {
            bitmapStickerIcon.onActionUp(this, motionEvent);
        }
        if (this.currentMode == 1 && Math.abs(motionEvent.getX() - this.downX) < ((float) this.touchSlop) && Math.abs(motionEvent.getY() - this.downY) < ((float) this.touchSlop) && (sticker2 = this.handlingSticker) != null) {
            this.currentMode = 4;
            OnStickerOperationListener onStickerOperationListener4 = this.onStickerOperationListener;
            if (onStickerOperationListener4 != null) {
                onStickerOperationListener4.onStickerClicked(sticker2);
            }
            if (uptimeMillis - this.lastClickTime < ((long) this.minClickDelayTime) && (onStickerOperationListener3 = this.onStickerOperationListener) != null) {
                onStickerOperationListener3.onStickerDoubleTapped(this.handlingSticker);
            }
        }
        if (!(this.currentMode != 1 || (sticker = this.handlingSticker) == null || (onStickerOperationListener2 = this.onStickerOperationListener) == null)) {
            onStickerOperationListener2.onStickerDragFinished(sticker);
        }
        this.currentMode = 0;
        this.lastClickTime = uptimeMillis;
    }

    public void handleCurrentMode(@NonNull MotionEvent motionEvent) {
        BitmapStickerIcon bitmapStickerIcon;
        int i = this.currentMode;
        if (i != 1) {
            if (i != 2) {
                if (i == 3 && this.handlingSticker != null && (bitmapStickerIcon = this.currentIcon) != null) {
                    bitmapStickerIcon.onActionMove(this, motionEvent);
                }
            } else if (this.handlingSticker != null) {
                float calculateDistance = calculateDistance(motionEvent);
                float calculateRotation = calculateRotation(motionEvent);
                this.moveMatrix.set(this.downMatrix);
                Matrix matrix = this.moveMatrix;
                float f = calculateDistance / this.oldDistance;
                matrix.postScale(f, f, this.midPoint.x, this.midPoint.y);
                this.moveMatrix.postRotate(calculateRotation - this.oldRotation, this.midPoint.x, this.midPoint.y);
                this.handlingSticker.setMatrix(this.moveMatrix);
            }
        } else if (this.handlingSticker != null) {
            this.moveMatrix.set(this.downMatrix);
            this.moveMatrix.postTranslate(motionEvent.getX() - this.downX, motionEvent.getY() - this.downY);
            this.handlingSticker.setMatrix(this.moveMatrix);
            if (this.constrained) {
                constrainSticker(this.handlingSticker);
            }
        }
    }

    public void zoomAndRotateCurrentSticker(@NonNull MotionEvent motionEvent) {
        zoomAndRotateSticker(this.handlingSticker, motionEvent);
    }

    public void zoomAndRotateSticker(@Nullable Sticker sticker, @NonNull MotionEvent motionEvent) {
        if (sticker != null) {
            float calculateDistance = calculateDistance(this.midPoint.x, this.midPoint.y, motionEvent.getX(), motionEvent.getY());
            float calculateRotation = calculateRotation(this.midPoint.x, this.midPoint.y, motionEvent.getX(), motionEvent.getY());
            this.moveMatrix.set(this.downMatrix);
            Matrix matrix = this.moveMatrix;
            float f = calculateDistance / this.oldDistance;
            matrix.postScale(f, f, this.midPoint.x, this.midPoint.y);
            this.moveMatrix.postRotate(calculateRotation - this.oldRotation, this.midPoint.x, this.midPoint.y);
            this.handlingSticker.setMatrix(this.moveMatrix);
        }
    }

    public void constrainSticker(@NonNull Sticker sticker) {
        int width = getWidth();
        int height = getHeight();
        sticker.getMappedCenterPoint(this.currentCenterPoint, this.point, this.tmp);
        float f = 0.0f;
        float f2 = this.currentCenterPoint.x < 0.0f ? -this.currentCenterPoint.x : 0.0f;
        float f3 = (float) width;
        if (this.currentCenterPoint.x > f3) {
            f2 = f3 - this.currentCenterPoint.x;
        }
        if (this.currentCenterPoint.y < 0.0f) {
            f = -this.currentCenterPoint.y;
        }
        float f4 = (float) height;
        if (this.currentCenterPoint.y > f4) {
            f = f4 - this.currentCenterPoint.y;
        }
        sticker.getMatrix().postTranslate(f2, f);
    }

    @Nullable
    public BitmapStickerIcon findCurrentIconTouched() {
        for (BitmapStickerIcon bitmapStickerIcon : this.icons) {
            float x = bitmapStickerIcon.getX() - this.downX;
            float y = bitmapStickerIcon.getY() - this.downY;
            if (((double) ((x * x) + (y * y))) <= Math.pow((double) (bitmapStickerIcon.getIconRadius() + bitmapStickerIcon.getIconRadius()), 2.0d)) {
                return bitmapStickerIcon;
            }
        }
        return null;
    }

    @Nullable
    public Sticker findHandlingSticker() {
        for (int size = this.stickers.size() - 1; size >= 0; size--) {
            if (isInStickerArea(this.stickers.get(size), this.downX, this.downY)) {
                return this.stickers.get(size);
            }
        }
        return null;
    }

    public boolean isInStickerArea(@NonNull Sticker sticker, float f, float f2) {
        float[] fArr = this.tmp;
        fArr[0] = f;
        fArr[1] = f2;
        return sticker.contains(fArr);
    }

    @NonNull
    public PointF calculateMidPoint(@Nullable MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            this.midPoint.set(0.0f, 0.0f);
            return this.midPoint;
        }
        this.midPoint.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
        return this.midPoint;
    }

    @NonNull
    public PointF calculateMidPoint() {
        Sticker sticker = this.handlingSticker;
        if (sticker == null) {
            this.midPoint.set(0.0f, 0.0f);
            return this.midPoint;
        }
        sticker.getMappedCenterPoint(this.midPoint, this.point, this.tmp);
        return this.midPoint;
    }

    public float calculateRotation(@Nullable MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        return calculateRotation(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
    }

    public float calculateRotation(float f, float f2, float f3, float f4) {
        return (float) Math.toDegrees(Math.atan2((double) (f2 - f4), (double) (f - f3)));
    }

    public float calculateDistance(@Nullable MotionEvent motionEvent) {
        if (motionEvent == null || motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        return calculateDistance(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
    }

    public float calculateDistance(float f, float f2, float f3, float f4) {
        double d = (double) (f - f3);
        double d2 = (double) (f2 - f4);
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d2);
        Double.isNaN(d2);
        Double.isNaN(d2);
        Double.isNaN(d2);
        return (float) Math.sqrt((d * d) + (d2 * d2));
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        for (int i5 = 0; i5 < this.stickers.size(); i5++) {
            Sticker sticker = this.stickers.get(i5);
            if (sticker != null) {
                transformSticker(sticker);
            }
        }
    }

    public void transformSticker(@Nullable Sticker sticker) {
        if (sticker == null) {
            Log.e(TAG, "transformSticker: the bitmapSticker is null or the bitmapSticker bitmap is null");
            return;
        }
        this.sizeMatrix.reset();
        float width = (float) getWidth();
        float height = (float) getHeight();
        float width2 = (float) sticker.getWidth();
        float height2 = (float) sticker.getHeight();
        this.sizeMatrix.postTranslate((width - width2) / 2.0f, (height - height2) / 2.0f);
        float f = (width < height ? width / width2 : height / height2) / 2.8f;
        this.sizeMatrix.postScale(f, f, width / 2.8f, height / 2.8f);
        sticker.getMatrix().reset();
        sticker.setMatrix(this.sizeMatrix);
        invalidate();
    }

    public boolean replace(@Nullable Sticker sticker, boolean z) {
        float f;
        if (this.handlingSticker == null || sticker == null) {
            return false;
        }
        float width = (float) getWidth();
        float height = (float) getHeight();
        if (z) {
            sticker.setMatrix(this.handlingSticker.getMatrix());
            sticker.setFlippedVertically(this.handlingSticker.isFlippedVertically());
            sticker.setFlippedHorizontally(this.handlingSticker.isFlippedHorizontally());
        } else {
            this.handlingSticker.getMatrix().reset();
            sticker.getMatrix().postTranslate((width - ((float) this.handlingSticker.getWidth())) / 2.0f, (height - ((float) this.handlingSticker.getHeight())) / 2.0f);
            if (width < height) {
                f = width / ((float) this.handlingSticker.getDrawable().getIntrinsicWidth());
            } else {
                f = height / ((float) this.handlingSticker.getDrawable().getIntrinsicHeight());
            }
            float f2 = f / 2.0f;
            sticker.getMatrix().postScale(f2, f2, width / 2.0f, height / 2.0f);
        }
        List<Sticker> list = this.stickers;
        list.set(list.indexOf(this.handlingSticker), sticker);
        this.handlingSticker = sticker;
        invalidate();
        return true;
    }

    public boolean remove(@Nullable Sticker sticker) {
        if (this.stickers.contains(sticker)) {
            this.stickers.remove(sticker);
            if (this.handlingSticker == sticker) {
                this.handlingSticker = null;
                invalidate();
            }
            OnStickerOperationListener onStickerOperationListener2 = this.onStickerOperationListener;
            if (onStickerOperationListener2 == null) {
                return true;
            }
            onStickerOperationListener2.onStickerDeleted(sticker);
            invalidate();
            return true;
        }
        Log.d(TAG, "remove: the sticker is not in this StickerView");
        return false;
    }

    public boolean removeCurrentSticker() {
        return remove(this.handlingSticker);
    }

    @NonNull
    public ImgStickerView addSticker(@NonNull Sticker sticker) {
        return addSticker(sticker, 1);
    }

    public ImgStickerView addSticker(@NonNull final Sticker sticker, final int i) {
        if (ViewCompat.isLaidOut(this)) {
            addStickerImmediately(sticker, i);
        } else {
            post(new Runnable() {
                /* class com.MyMovieMaker.videomakerwithmusic.txtSticker.ImgStickerView.AnonymousClass1 */

                public void run() {
                    ImgStickerView.this.addStickerImmediately(sticker, i);
                }
            });
        }
        return this;
    }

    public void addStickerImmediately(@NonNull Sticker sticker, int i) {
        setStickerPosition(sticker, i);
        float width = ((float) getWidth()) / ((float) sticker.getDrawable().getIntrinsicWidth());
        float height = ((float) getHeight()) / ((float) sticker.getDrawable().getIntrinsicHeight());
        if (width > height) {
            width = height;
        }
        this.scaleFactor = width;
        Matrix matrix = sticker.getMatrix();
        float f = this.scaleFactor / 2.0f;
        matrix.postScale(f, f, (float) (getWidth() / 2), (float) (getHeight() / 2));
        this.handlingSticker = sticker;
        this.stickers.add(sticker);
        OnStickerOperationListener onStickerOperationListener2 = this.onStickerOperationListener;
        if (onStickerOperationListener2 != null) {
            onStickerOperationListener2.onStickerAdded(sticker);
        }
        invalidate();
    }

    public void removeAllline() {
        this.handlingSticker = null;
        invalidate();
    }

    public void setStickerPosition(@NonNull Sticker sticker, int i) {
        float width = ((float) getWidth()) - ((float) sticker.getWidth());
        float height = ((float) getHeight()) - ((float) sticker.getHeight());
        sticker.getMatrix().postTranslate((i & 4) > 0 ? width / 4.0f : (i & 8) > 0 ? width * 0.75f : width / 2.0f, (i & 2) > 0 ? height / 4.0f : (i & 16) > 0 ? height * 0.75f : height / 2.0f);
    }

    @NonNull
    public float[] getStickerPoints(@Nullable Sticker sticker) {
        float[] fArr = new float[8];
        getStickerPoints(sticker, fArr);
        return fArr;
    }

    public void getStickerPoints(@Nullable Sticker sticker, @NonNull float[] fArr) {
        if (sticker == null) {
            Arrays.fill(fArr, 0.0f);
            return;
        }
        sticker.getBoundPoints(this.bounds);
        sticker.getMappedPoints(fArr, this.bounds);
    }

    @NonNull
    public Bitmap createBitmap() throws OutOfMemoryError {
        this.handlingSticker = null;
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        draw(new Canvas(createBitmap));
        return createBitmap;
    }

    @NonNull
    public ImgStickerView setLocked(boolean z) {
        this.locked = z;
        invalidate();
        return this;
    }

    @NonNull
    public ImgStickerView setMinClickDelayTime(int i) {
        this.minClickDelayTime = i;
        return this;
    }

    @NonNull
    public ImgStickerView setConstrained(boolean z) {
        this.constrained = z;
        postInvalidate();
        return this;
    }

    @Nullable
    public OnStickerOperationListener getOnStickerOperationListener() {
        return this.onStickerOperationListener;
    }

    @NonNull
    public ImgStickerView setOnStickerOperationListener(@Nullable OnStickerOperationListener onStickerOperationListener2) {
        this.onStickerOperationListener = onStickerOperationListener2;
        return this;
    }

    @Nullable
    public Sticker getCurrentSticker() {
        return this.handlingSticker;
    }

    @NonNull
    public List<BitmapStickerIcon> getIcons() {
        return this.icons;
    }

    @Retention(RetentionPolicy.SOURCE)
    protected @interface ActionMode {
        public static final int CLICK = 4;
        public static final int DRAG = 1;
        public static final int ICON = 3;
        public static final int NONE = 0;
        public static final int ZOOM_WITH_TWO_FINGER = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    protected @interface Flip {
    }

    public interface OnStickerOperationListener {
        void onStickerAdded(@NonNull Sticker sticker);

        void onStickerClicked(@NonNull Sticker sticker);

        void onStickerDeleted(@NonNull Sticker sticker);

        void onStickerDoubleTapped(@NonNull Sticker sticker);

        void onStickerDragFinished(@NonNull Sticker sticker);

        void onStickerTouchedDown(@NonNull Sticker sticker);

        void onStickerZoomFinished(@NonNull Sticker sticker);
    }
}
