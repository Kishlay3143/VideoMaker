package com.status.videomaker.Stickerss;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.status.videomaker.Modelss.All_Music_Data_Model;

public class Sticker_Touch_Cmnd_Sticker implements View.OnTouchListener {
    public boolean is_Rotate_Enabled_bool = true;
    public boolean is_Rotation_Enabled_bool = false;
    public boolean is_Translate_Enabled_bool = true;
    public float maximum_Scale_var = 8.0f;
    public float minimum_Scale_var = 0.5f;
    GestureDetector gesture_Detector_obj = null;
    private TouchCallbackListener listener_obj = null;
    private int m_Active_Pointer_Id_var = -1;
    private float m_Prev_X;
    private float m_Prev_Y;
    private All_Music_Data_Model.DetectorStickerGesture m_Detector_Sticker_Gesture = new All_Music_Data_Model.DetectorStickerGesture(new Scale_Gesture_Listener());

    private static float adjust_Angle_method(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    private static void adjust_Translation_method(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private static void compute_Render_Offset_method(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - (fArr2[0] - fArr[0]));
            view.setTranslationY(view.getTranslationY() - f3);
        }
    }

    public Sticker_Touch_Cmnd_Sticker set_Gesture_Listener(GestureDetector gestureDetector2) {
        this.gesture_Detector_obj = gestureDetector2;
        return this;
    }

    public Sticker_Touch_Cmnd_Sticker set_On_Touch_Callback_Listener(TouchCallbackListener touchCallbackListener) {
        this.listener_obj = touchCallbackListener;
        return this;
    }

    public Sticker_Touch_Cmnd_Sticker enable_Rotation(boolean z) {
        this.is_Rotation_Enabled_bool = z;
        return this;
    }

    public void move_method(View view, TransformInfo transformInfo) {
        if (this.is_Rotation_Enabled_bool) {
            view.setRotation(adjust_Angle_method(view.getRotation() + transformInfo.deltaAngle));
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.m_Detector_Sticker_Gesture.onTouchEvent(view, motionEvent);
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        GestureDetector gestureDetector2 = this.gesture_Detector_obj;
        if (gestureDetector2 != null) {
            gestureDetector2.onTouchEvent(motionEvent);
        }
        if (this.is_Translate_Enabled_bool) {
            int action = motionEvent.getAction();
            int actionMasked = motionEvent.getActionMasked() & action;
            int i = 0;
            if (actionMasked == 6) {
                int i2 = (65280 & action) >> 8;
                if (motionEvent.getPointerId(i2) == this.m_Active_Pointer_Id_var) {
                    if (i2 == 0) {
                        i = 1;
                    }
                    this.m_Prev_X = motionEvent.getX(i);
                    this.m_Prev_Y = motionEvent.getY(i);
                    this.m_Active_Pointer_Id_var = motionEvent.getPointerId(i);
                }
            } else if (actionMasked == 0) {
                if (relativeLayout != null) {
                    relativeLayout.requestDisallowInterceptTouchEvent(true);
                }
                TouchCallbackListener touchCallbackListener = this.listener_obj;
                if (touchCallbackListener != null) {
                    touchCallbackListener.onTouchCallback(view);
                }
                view.bringToFront();
                if (view instanceof Text_Autofit_Rel_Sticker) {
                    ((Text_Autofit_Rel_Sticker) view).set_Border_Visibility(true);
                }
                this.m_Prev_X = motionEvent.getX();
                this.m_Prev_Y = motionEvent.getY();
                this.m_Active_Pointer_Id_var = motionEvent.getPointerId(0);
            } else if (actionMasked == 1) {
                this.m_Active_Pointer_Id_var = -1;
                TouchCallbackListener touchCallbackListener2 = this.listener_obj;
                if (touchCallbackListener2 != null) {
                    touchCallbackListener2.onTouchUpCallback(view);
                }
                float rotation = view.getRotation();
                if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 90.0f : -90.0f;
                }
                if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 0.0f : -0.0f;
                }
                if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
                    rotation = rotation > 0.0f ? 180.0f : -180.0f;
                }
                view.setRotation(rotation);
                Log.i("testing", "Final Rotation : " + rotation);
            } else if (actionMasked == 2) {
                if (relativeLayout != null) {
                    relativeLayout.requestDisallowInterceptTouchEvent(true);
                }
                TouchCallbackListener touchCallbackListener3 = this.listener_obj;
                if (touchCallbackListener3 != null) {
                    touchCallbackListener3.onTouchMoveCallback(view);
                }
                int findPointerIndex = motionEvent.findPointerIndex(this.m_Active_Pointer_Id_var);
                if (findPointerIndex != -1) {
                    float x = motionEvent.getX(findPointerIndex);
                    float y = motionEvent.getY(findPointerIndex);
                    if (!this.m_Detector_Sticker_Gesture.isInProgress()) {
                        adjust_Translation_method(view, x - this.m_Prev_X, y - this.m_Prev_Y);
                    }
                }
            } else if (actionMasked == 3) {
                this.m_Active_Pointer_Id_var = -1;
            }
        }
        return true;
    }

    public interface TouchCallbackListener {
        void onTouchCallback(View view);

        void onTouchMoveCallback(View view);

        void onTouchUpCallback(View view);
    }

    private class Scale_Gesture_Listener extends All_Music_Data_Model.DetectorStickerGesture.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector_2D_Sticker mPrevSpanVector;

        private Scale_Gesture_Listener() {
            this.mPrevSpanVector = new Vector_2D_Sticker();
        }

        @Override
        public boolean onScaleBegin(View view, All_Music_Data_Model.DetectorStickerGesture detectorStickerGesture) {
            this.mPivotX = detectorStickerGesture.getFocusX();
            this.mPivotY = detectorStickerGesture.getFocusY();
            this.mPrevSpanVector.set(detectorStickerGesture.getCurrentSpanVector());
            return true;
        }

        @Override
        public boolean onScale(View view, All_Music_Data_Model.DetectorStickerGesture detectorStickerGesture) {
            TransformInfo transformInfo = new TransformInfo();
            float f = 0.0f;
            transformInfo.deltaAngle = Sticker_Touch_Cmnd_Sticker.this.is_Rotate_Enabled_bool ? Vector_2D_Sticker.get_Angle_method(this.mPrevSpanVector, detectorStickerGesture.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = Sticker_Touch_Cmnd_Sticker.this.is_Translate_Enabled_bool ? detectorStickerGesture.getFocusX() - this.mPivotX : 0.0f;
            if (Sticker_Touch_Cmnd_Sticker.this.is_Translate_Enabled_bool) {
                f = detectorStickerGesture.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = Sticker_Touch_Cmnd_Sticker.this.minimum_Scale_var;
            transformInfo.maximumScale = Sticker_Touch_Cmnd_Sticker.this.maximum_Scale_var;
            Sticker_Touch_Cmnd_Sticker.this.move_method(view, transformInfo);
            return false;
        }
    }

    public class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }
}
