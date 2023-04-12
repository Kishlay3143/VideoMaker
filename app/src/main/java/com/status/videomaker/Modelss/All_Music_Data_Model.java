package com.status.videomaker.Modelss;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.status.videomaker.Stickerss.Vector_2D_Sticker;

public class All_Music_Data_Model {
    public int counter_var;
    public long trackk_id_var;
    public String trackk_Title_str;
    public String trackk_data_str;
    public String trackk_display_Name_str;
    public long trackk_duration_var;
    private boolean is_selected_bool;

    public String getTrackk_data_str() {
        return this.trackk_data_str;
    }

    public static class DetectorStickerGesture {
        private static final float PRESSURE_THRESHOLD = 0.67f;
        private static final String TAG = "DetectorStickerGesture";
        private final OnScaleGestureListener mListener;
        private boolean mActive0MostRecent;
        private int mActiveId0;
        private int mActiveId1;
        private MotionEvent mCurrEvent;
        private float mCurrFingerDiffX;
        private float mCurrFingerDiffY;
        private float mCurrLen;
        private float mCurrPressure;
        private Vector_2D_Sticker mCurrSpanVector = new Vector_2D_Sticker();
        private float mFocusX;
        private float mFocusY;
        private boolean mGestureInProgress;
        private boolean mInvalidGesture;
        private MotionEvent mPrevEvent;
        private float mPrevFingerDiffX;
        private float mPrevFingerDiffY;
        private float mPrevLen;
        private float mPrevPressure;
        private float mScaleFactor;
        private long mTimeDelta;

        public DetectorStickerGesture(OnScaleGestureListener onScaleGestureListener) {
            this.mListener = onScaleGestureListener;
        }

        public boolean onTouchEvent(View view, MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                reset();
            }
            boolean z = false;
            if (this.mInvalidGesture) {
                return false;
            }
            if (this.mGestureInProgress) {
                if (actionMasked == 1) {
                    reset();
                    return true;
                } else if (actionMasked == 2) {
                    setContext(view, motionEvent);
                    if (this.mCurrPressure / this.mPrevPressure > PRESSURE_THRESHOLD && this.mListener.onScale(view, this)) {
                        this.mPrevEvent.recycle();
                        this.mPrevEvent = MotionEvent.obtain(motionEvent);
                    }
                    return true;
                } else if (actionMasked == 3) {
                    this.mListener.onScaleEnd(view, this);
                    reset();
                    return true;
                } else if (actionMasked == 5) {
                    this.mListener.onScaleEnd(view, this);
                    int i = this.mActiveId0;
                    int i2 = this.mActiveId1;
                    reset();
                    this.mPrevEvent = MotionEvent.obtain(motionEvent);
                    if (!this.mActive0MostRecent) {
                        i = i2;
                    }
                    this.mActiveId0 = i;
                    this.mActiveId1 = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.mActive0MostRecent = false;
                    if (motionEvent.findPointerIndex(this.mActiveId0) < 0 || this.mActiveId0 == this.mActiveId1) {
                        this.mActiveId0 = motionEvent.getPointerId(findNewActiveIndex(motionEvent, this.mActiveId1, -1));
                    }
                    setContext(view, motionEvent);
                    this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                    return true;
                } else if (actionMasked != 6) {
                    return true;
                } else {
                    int pointerCount = motionEvent.getPointerCount();
                    int actionIndex = motionEvent.getActionIndex();
                    int pointerId = motionEvent.getPointerId(actionIndex);
                    if (pointerCount > 2) {
                        int i3 = this.mActiveId0;
                        if (pointerId == i3) {
                            int findNewActiveIndex = findNewActiveIndex(motionEvent, this.mActiveId1, actionIndex);
                            if (findNewActiveIndex >= 0) {
                                this.mListener.onScaleEnd(view, this);
                                this.mActiveId0 = motionEvent.getPointerId(findNewActiveIndex);
                                this.mActive0MostRecent = true;
                                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                                setContext(view, motionEvent);
                                this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                                this.mPrevEvent.recycle();
                                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                                setContext(view, motionEvent);
                            }
                        } else {
                            if (pointerId == this.mActiveId1) {
                                int findNewActiveIndex2 = findNewActiveIndex(motionEvent, i3, actionIndex);
                                if (findNewActiveIndex2 >= 0) {
                                    this.mListener.onScaleEnd(view, this);
                                    this.mActiveId1 = motionEvent.getPointerId(findNewActiveIndex2);
                                    this.mActive0MostRecent = false;
                                    this.mPrevEvent = MotionEvent.obtain(motionEvent);
                                    setContext(view, motionEvent);
                                    this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                                }
                            }
                            this.mPrevEvent.recycle();
                            this.mPrevEvent = MotionEvent.obtain(motionEvent);
                            setContext(view, motionEvent);
                        }
                        z = true;
                        this.mPrevEvent.recycle();
                        this.mPrevEvent = MotionEvent.obtain(motionEvent);
                        setContext(view, motionEvent);
                    } else {
                        z = true;
                    }
                    if (!z) {
                        return true;
                    }
                    setContext(view, motionEvent);
                    int i4 = this.mActiveId0;
                    if (pointerId == i4) {
                        i4 = this.mActiveId1;
                    }
                    int findPointerIndex = motionEvent.findPointerIndex(i4);
                    this.mFocusX = motionEvent.getX(findPointerIndex);
                    this.mFocusY = motionEvent.getY(findPointerIndex);
                    this.mListener.onScaleEnd(view, this);
                    reset();
                    this.mActiveId0 = i4;
                    this.mActive0MostRecent = true;
                    return true;
                }
            } else if (actionMasked == 5) {
                MotionEvent motionEvent2 = this.mPrevEvent;
                if (motionEvent2 != null) {
                    motionEvent2.recycle();
                }
                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                this.mTimeDelta = 0;
                int actionIndex2 = motionEvent.getActionIndex();
                int findPointerIndex2 = motionEvent.findPointerIndex(this.mActiveId0);
                this.mActiveId1 = motionEvent.getPointerId(actionIndex2);
                if (findPointerIndex2 < 0 || findPointerIndex2 == actionIndex2) {
                    this.mActiveId0 = motionEvent.getPointerId(findNewActiveIndex(motionEvent, this.mActiveId1, -1));
                }
                this.mActive0MostRecent = false;
                setContext(view, motionEvent);
                this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                return true;
            } else if (actionMasked == 0) {
                this.mActiveId0 = motionEvent.getPointerId(0);
                this.mActive0MostRecent = true;
                return true;
            } else if (actionMasked != 1) {
                return true;
            } else {
                reset();
                return true;
            }
        }

        private int findNewActiveIndex(MotionEvent motionEvent, int i, int i2) {
            int pointerCount = motionEvent.getPointerCount();
            int findPointerIndex = motionEvent.findPointerIndex(i);
            for (int i3 = 0; i3 < pointerCount; i3++) {
                if (!(i3 == i2 || i3 == findPointerIndex)) {
                    return i3;
                }
            }
            return -1;
        }

        private void setContext(View view, MotionEvent motionEvent) {
            MotionEvent motionEvent2 = this.mCurrEvent;
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            this.mCurrEvent = MotionEvent.obtain(motionEvent);
            this.mCurrLen = -1.0f;
            this.mPrevLen = -1.0f;
            this.mScaleFactor = -1.0f;
            this.mCurrSpanVector.set(0.0f, 0.0f);
            MotionEvent motionEvent3 = this.mPrevEvent;
            int findPointerIndex = motionEvent3.findPointerIndex(this.mActiveId0);
            int findPointerIndex2 = motionEvent3.findPointerIndex(this.mActiveId1);
            int findPointerIndex3 = motionEvent.findPointerIndex(this.mActiveId0);
            int findPointerIndex4 = motionEvent.findPointerIndex(this.mActiveId1);
            if (findPointerIndex < 0 || findPointerIndex2 < 0 || findPointerIndex3 < 0 || findPointerIndex4 < 0) {
                this.mInvalidGesture = true;
                Log.e(TAG, "Invalid MotionEvent stream detected.", new Throwable());
                if (this.mGestureInProgress) {
                    this.mListener.onScaleEnd(view, this);
                    return;
                }
                return;
            }
            float x = motionEvent3.getX(findPointerIndex);
            float y = motionEvent3.getY(findPointerIndex);
            float x2 = motionEvent3.getX(findPointerIndex2);
            float y2 = motionEvent3.getY(findPointerIndex2);
            float x3 = motionEvent.getX(findPointerIndex3);
            float y3 = motionEvent.getY(findPointerIndex3);
            float f = x2 - x;
            float f2 = y2 - y;
            float x4 = motionEvent.getX(findPointerIndex4) - x3;
            float y4 = motionEvent.getY(findPointerIndex4) - y3;
            this.mCurrSpanVector.set(x4, y4);
            this.mPrevFingerDiffX = f;
            this.mPrevFingerDiffY = f2;
            this.mCurrFingerDiffX = x4;
            this.mCurrFingerDiffY = y4;
            this.mFocusX = (x4 * 0.5f) + x3;
            this.mFocusY = (y4 * 0.5f) + y3;
            this.mTimeDelta = motionEvent.getEventTime() - motionEvent3.getEventTime();
            this.mCurrPressure = motionEvent.getPressure(findPointerIndex3) + motionEvent.getPressure(findPointerIndex4);
            this.mPrevPressure = motionEvent3.getPressure(findPointerIndex) + motionEvent3.getPressure(findPointerIndex2);
        }

        private void reset() {
            MotionEvent motionEvent = this.mPrevEvent;
            if (motionEvent != null) {
                motionEvent.recycle();
                this.mPrevEvent = null;
            }
            MotionEvent motionEvent2 = this.mCurrEvent;
            if (motionEvent2 != null) {
                motionEvent2.recycle();
                this.mCurrEvent = null;
            }
            this.mGestureInProgress = false;
            this.mActiveId0 = -1;
            this.mActiveId1 = -1;
            this.mInvalidGesture = false;
        }

        public boolean isInProgress() {
            return this.mGestureInProgress;
        }

        public float getFocusX() {
            return this.mFocusX;
        }

        public float getFocusY() {
            return this.mFocusY;
        }

        public float getCurrentSpan() {
            if (this.mCurrLen == -1.0f) {
                float f = this.mCurrFingerDiffX;
                float f2 = this.mCurrFingerDiffY;
                this.mCurrLen = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
            }
            return this.mCurrLen;
        }

        public Vector_2D_Sticker getCurrentSpanVector() {
            return this.mCurrSpanVector;
        }

        public float getCurrentSpanY() {
            return this.mCurrFingerDiffY;
        }

        public float getPreviousSpan() {
            if (this.mPrevLen == -1.0f) {
                float f = this.mPrevFingerDiffX;
                float f2 = this.mPrevFingerDiffY;
                this.mPrevLen = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
            }
            return this.mPrevLen;
        }

        public float getScaleFactor() {
            if (this.mScaleFactor == -1.0f) {
                this.mScaleFactor = getCurrentSpan() / getPreviousSpan();
            }
            return this.mScaleFactor;
        }


        public interface OnScaleGestureListener {
            boolean onScale(View view, DetectorStickerGesture detectorStickerGesture);

            boolean onScaleBegin(View view, DetectorStickerGesture detectorStickerGesture);

            void onScaleEnd(View view, DetectorStickerGesture detectorStickerGesture);
        }

        public static class SimpleOnScaleGestureListener implements OnScaleGestureListener {
            @Override
            public boolean onScale(View view, DetectorStickerGesture detectorStickerGesture) {
                return false;
            }

            @Override
            public boolean onScaleBegin(View view, DetectorStickerGesture detectorStickerGesture) {
                return true;
            }

            @Override
            public void onScaleEnd(View view, DetectorStickerGesture detectorStickerGesture) {
            }
        }
    }
}
