package com.status.videomaker.Maskss;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.internal.view.SupportMenu;

import com.status.videomaker.Activitiess.MainApplication;

import java.lang.reflect.Array;

public class Final_Mask_Bitmap_3D_Class {
    static final Paint paint_obj;
    public static float _ANIMATED_FRAME_VAR = 22.0f;
    public static int _ANIMATED_FRAME_CAL_VAR = ((int) (22.0f - 1.0f));
    public static Camera _camera_obj = new Camera();
    public static int _direction_var = 0;
    public static Matrix _matrix_obj = new Matrix();
    public static int _part_Number = 8;
    public static EFFECT _roll_Mode_obj;
    private static int _average_Height_var;
    private static int _average_Width;
    private static float _axis_X;
    private static float _axis_Y;
    private static Bitmap[][] bitmap_2d_array;
    private static float _rotate_Degree;

    static {
        Paint paint2 = new Paint();
        paint_obj = paint2;
        paint2.setColor(-16777216);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public static void SetRotateDegreeMethod(int i) {
        if (_roll_Mode_obj == EFFECT.RollInTurn_BT || _roll_Mode_obj == EFFECT.RollInTurn_LR || _roll_Mode_obj == EFFECT.RollInTurn_RL || _roll_Mode_obj == EFFECT.RollInTurn_TB) {
            _rotate_Degree = (((((float) (_part_Number - 1)) * 30.0f) + 90.0f) * ((float) i)) / ((float) _ANIMATED_FRAME_CAL_VAR);
        } else if (_roll_Mode_obj == EFFECT.Jalousie_BT || _roll_Mode_obj == EFFECT.Jalousie_LR) {
            _rotate_Degree = (((float) i) * 180.0f) / ((float) _ANIMATED_FRAME_CAL_VAR);
        } else {
            _rotate_Degree = (((float) i) * 90.0f) / ((float) _ANIMATED_FRAME_CAL_VAR);
        }
        int i2 = 180;
        if (_direction_var == 1) {
            float f = _rotate_Degree;
            if (!(_roll_Mode_obj == EFFECT.Jalousie_BT || _roll_Mode_obj == EFFECT.Jalousie_LR)) {
                i2 = 90;
            }
            _axis_Y = (f / ((float) i2)) * ((float) MainApplication.VIDEO_HEIGHT);
            return;
        }
        float f2 = _rotate_Degree;
        if (!(_roll_Mode_obj == EFFECT.Jalousie_BT || _roll_Mode_obj == EFFECT.Jalousie_LR)) {
            i2 = 90;
        }
        _axis_X = (f2 / ((float) i2)) * ((float) MainApplication.VIDEO_WIDTH);
    }

    public static void init_Bitmaps_method(Bitmap bitmap, Bitmap bitmap2, EFFECT effect) {
        Bitmap bitmap3;
        _roll_Mode_obj = effect;
        if (MainApplication.VIDEO_HEIGHT > 0 || MainApplication.VIDEO_WIDTH > 0) {
            bitmap_2d_array = (Bitmap[][]) Array.newInstance(Bitmap.class, new int[]{2, _part_Number});
            _average_Width = MainApplication.VIDEO_WIDTH / _part_Number;
            _average_Height_var = MainApplication.VIDEO_HEIGHT / _part_Number;
            int i = 0;
            while (i < 2) {
                for (int i2 = 0; i2 < _part_Number; i2++) {
                    if (_roll_Mode_obj == EFFECT.Jalousie_BT || _roll_Mode_obj == EFFECT.Jalousie_LR) {
                        if (_direction_var == 1) {
                            bitmap3 = get_Part_Bitmap_method(i == 0 ? bitmap : bitmap2, 0, _average_Height_var * i2, new Rect(0, _average_Height_var * i2, MainApplication.VIDEO_WIDTH, (i2 + 1) * _average_Height_var));
                        } else {
                            int i3 = _average_Width;
                            bitmap3 = get_Part_Bitmap_method(i == 0 ? bitmap : bitmap2, _average_Width * i2, 0, new Rect(i3 * i2, 0, (i2 + 1) * i3, MainApplication.VIDEO_HEIGHT));
                        }
                    } else if (_direction_var == 1) {
                        int i4 = _average_Width;
                        bitmap3 = get_Part_Bitmap_method(i == 0 ? bitmap : bitmap2, _average_Width * i2, 0, new Rect(i4 * i2, 0, (i2 + 1) * i4, MainApplication.VIDEO_HEIGHT));
                    } else {
                        bitmap3 = get_Part_Bitmap_method(i == 0 ? bitmap : bitmap2, 0, _average_Height_var * i2, new Rect(0, _average_Height_var * i2, MainApplication.VIDEO_WIDTH, (i2 + 1) * _average_Height_var));
                    }
                    bitmap_2d_array[i][i2] = bitmap3;
                }
                i++;
            }
        }
    }

    private static Bitmap get_Part_Bitmap_method(Bitmap bitmap, int i, int i2, Rect rect) {
        return Bitmap.createBitmap(bitmap, i, i2, rect.width(), rect.height());
    }

    public static void draw_Roll_Whole_3D_method(Bitmap bitmap, Bitmap bitmap2, Canvas canvas, boolean z) {
        canvas.save();
        if (_direction_var == 1) {
            _camera_obj.save();
            if (z) {
                _camera_obj.rotateX(0.0f);
            } else {
                _camera_obj.rotateX(-_rotate_Degree);
            }
            _camera_obj.getMatrix(_matrix_obj);
            _camera_obj.restore();
            _matrix_obj.preTranslate((float) ((-MainApplication.VIDEO_WIDTH) / 2), 0.0f);
            _matrix_obj.postTranslate((float) (MainApplication.VIDEO_WIDTH / 2), _axis_Y);
            Matrix matrix2 = _matrix_obj;
            Paint paint2 = paint_obj;
            canvas.drawBitmap(bitmap, matrix2, paint2);
            _camera_obj.save();
            if (z) {
                _camera_obj.rotateX(0.0f);
            } else {
                _camera_obj.rotateX(90.0f - _rotate_Degree);
            }
            _camera_obj.getMatrix(_matrix_obj);
            _camera_obj.restore();
            _matrix_obj.preTranslate((float) ((-MainApplication.VIDEO_WIDTH) / 2), (float) (-MainApplication.VIDEO_HEIGHT));
            _matrix_obj.postTranslate((float) (MainApplication.VIDEO_WIDTH / 2), _axis_Y);
            canvas.drawBitmap(bitmap2, _matrix_obj, paint2);
        } else {
            _camera_obj.save();
            if (z) {
                _camera_obj.rotateY(0.0f);
            } else {
                _camera_obj.rotateY(_rotate_Degree);
            }
            _camera_obj.getMatrix(_matrix_obj);
            _camera_obj.restore();
            _matrix_obj.preTranslate(0.0f, (float) ((-MainApplication.VIDEO_HEIGHT) / 2));
            _matrix_obj.postTranslate(_axis_X, (float) (MainApplication.VIDEO_HEIGHT / 2));
            Matrix matrix3 = _matrix_obj;
            Paint paint3 = paint_obj;
            canvas.drawBitmap(bitmap, matrix3, paint3);
            _camera_obj.save();
            if (z) {
                _camera_obj.rotateY(0.0f);
            } else {
                _camera_obj.rotateY(_rotate_Degree - 90.0f);
            }
            _camera_obj.getMatrix(_matrix_obj);
            _camera_obj.restore();
            _matrix_obj.preTranslate((float) (-MainApplication.VIDEO_WIDTH), (float) ((-MainApplication.VIDEO_HEIGHT) / 2));
            _matrix_obj.postTranslate(_axis_X, (float) (MainApplication.VIDEO_HEIGHT / 2));
            canvas.drawBitmap(bitmap2, _matrix_obj, paint3);
        }
        canvas.restore();
    }

    public static void draw_Separt_Conbine_method(Canvas canvas) {
        for (int i = 0; i < _part_Number; i++) {
            Bitmap[][] bitmapArr = bitmap_2d_array;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            canvas.save();
            if (_direction_var == 1) {
                _camera_obj.save();
                _camera_obj.rotateX(-_rotate_Degree);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate((float) ((-bitmap.getWidth()) / 2), 0.0f);
                _matrix_obj.postTranslate((float) ((bitmap.getWidth() / 2) + (_average_Width * i)), _axis_Y);
                Matrix matrix2 = _matrix_obj;
                Paint paint2 = paint_obj;
                canvas.drawBitmap(bitmap, matrix2, paint2);
                _camera_obj.save();
                _camera_obj.rotateX(90.0f - _rotate_Degree);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) (-bitmap2.getHeight()));
                _matrix_obj.postTranslate((float) ((bitmap2.getWidth() / 2) + (_average_Width * i)), _axis_Y);
                canvas.drawBitmap(bitmap2, _matrix_obj, paint2);
            } else {
                _camera_obj.save();
                _camera_obj.rotateY(_rotate_Degree);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate(0.0f, (float) ((-bitmap.getHeight()) / 2));
                _matrix_obj.postTranslate(_axis_X, (float) ((bitmap.getHeight() / 2) + (_average_Height_var * i)));
                Matrix matrix3 = _matrix_obj;
                Paint paint3 = paint_obj;
                canvas.drawBitmap(bitmap, matrix3, paint3);
                _camera_obj.save();
                _camera_obj.rotateY(_rotate_Degree - 90.0f);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate((float) (-bitmap2.getWidth()), (float) ((-bitmap2.getHeight()) / 2));
                _matrix_obj.postTranslate(_axis_X, (float) ((bitmap2.getHeight() / 2) + (_average_Height_var * i)));
                canvas.drawBitmap(bitmap2, _matrix_obj, paint3);
            }
            canvas.restore();
        }
    }

    public static void draw_Roll_In_Turn_method(Canvas canvas) {
        for (int i = 0; i < _part_Number; i++) {
            Bitmap[][] bitmapArr = bitmap_2d_array;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            float f = _rotate_Degree - ((float) (i * 30));
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f > 90.0f) {
                f = 90.0f;
            }
            canvas.save();
            if (_direction_var == 1) {
                float f2 = (f / 90.0f) * ((float) MainApplication.VIDEO_HEIGHT);
                if (f2 > ((float) MainApplication.VIDEO_HEIGHT)) {
                    f2 = (float) MainApplication.VIDEO_HEIGHT;
                }
                if (f2 < 0.0f) {
                    f2 = 0.0f;
                }
                _camera_obj.save();
                _camera_obj.rotateX(-f);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate((float) (-bitmap.getWidth()), 0.0f);
                _matrix_obj.postTranslate((float) (bitmap.getWidth() + (_average_Width * i)), f2);
                Matrix matrix2 = _matrix_obj;
                Paint paint2 = paint_obj;
                canvas.drawBitmap(bitmap, matrix2, paint2);
                _camera_obj.save();
                _camera_obj.rotateX(90.0f - f);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate((float) (-bitmap2.getWidth()), (float) (-bitmap2.getHeight()));
                _matrix_obj.postTranslate((float) (bitmap2.getWidth() + (_average_Width * i)), f2);
                canvas.drawBitmap(bitmap2, _matrix_obj, paint2);
            } else {
                float f3 = (f / 90.0f) * ((float) MainApplication.VIDEO_WIDTH);
                if (f3 > ((float) MainApplication.VIDEO_WIDTH)) {
                    f3 = (float) MainApplication.VIDEO_WIDTH;
                }
                if (f3 < 0.0f) {
                    f3 = 0.0f;
                }
                _camera_obj.save();
                _camera_obj.rotateY(f);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate(0.0f, (float) ((-bitmap.getHeight()) / 2));
                _matrix_obj.postTranslate(f3, (float) ((bitmap.getHeight() / 2) + (_average_Height_var * i)));
                Matrix matrix3 = _matrix_obj;
                Paint paint3 = paint_obj;
                canvas.drawBitmap(bitmap, matrix3, paint3);
                _camera_obj.save();
                _camera_obj.rotateY(f - 90.0f);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate((float) (-bitmap2.getWidth()), (float) ((-bitmap2.getHeight()) / 2));
                _matrix_obj.postTranslate(f3, (float) ((bitmap2.getHeight() / 2) + (_average_Height_var * i)));
                canvas.drawBitmap(bitmap2, _matrix_obj, paint3);
            }
            canvas.restore();
        }
    }

    public static void draw_Jalousie_method(Canvas canvas) {
        for (int i = 0; i < _part_Number; i++) {
            Bitmap[][] bitmapArr = bitmap_2d_array;
            Bitmap bitmap = bitmapArr[0][i];
            Bitmap bitmap2 = bitmapArr[1][i];
            canvas.save();
            if (_direction_var == 1) {
                if (_rotate_Degree < 90.0f) {
                    _camera_obj.save();
                    _camera_obj.rotateX(_rotate_Degree);
                    _camera_obj.getMatrix(_matrix_obj);
                    _camera_obj.restore();
                    _matrix_obj.preTranslate((float) ((-bitmap.getWidth()) / 2), (float) ((-bitmap.getHeight()) / 2));
                    _matrix_obj.postTranslate((float) (bitmap.getWidth() / 2), (float) ((bitmap.getHeight() / 2) + (_average_Height_var * i)));
                    canvas.drawBitmap(bitmap, _matrix_obj, paint_obj);
                } else {
                    _camera_obj.save();
                    _camera_obj.rotateX(180.0f - _rotate_Degree);
                    _camera_obj.getMatrix(_matrix_obj);
                    _camera_obj.restore();
                    _matrix_obj.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) ((-bitmap2.getHeight()) / 2));
                    _matrix_obj.postTranslate((float) (bitmap2.getWidth() / 2), (float) ((bitmap2.getHeight() / 2) + (_average_Height_var * i)));
                    canvas.drawBitmap(bitmap2, _matrix_obj, paint_obj);
                }
            } else if (_rotate_Degree < 90.0f) {
                _camera_obj.save();
                _camera_obj.rotateY(_rotate_Degree);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate((float) ((-bitmap.getWidth()) / 2), (float) ((-bitmap.getHeight()) / 2));
                _matrix_obj.postTranslate((float) ((bitmap.getWidth() / 2) + (_average_Width * i)), (float) (bitmap.getHeight() / 2));
                canvas.drawBitmap(bitmap, _matrix_obj, paint_obj);
            } else {
                _camera_obj.save();
                _camera_obj.rotateY(180.0f - _rotate_Degree);
                _camera_obj.getMatrix(_matrix_obj);
                _camera_obj.restore();
                _matrix_obj.preTranslate((float) ((-bitmap2.getWidth()) / 2), (float) ((-bitmap2.getHeight()) / 2));
                _matrix_obj.postTranslate((float) ((bitmap2.getWidth() / 2) + (_average_Width * i)), (float) (bitmap2.getHeight() / 2));
                canvas.drawBitmap(bitmap2, _matrix_obj, paint_obj);
            }
            canvas.restore();
        }
    }

    public enum EFFECT {
        Whole3D_TB("Whole3D_TB") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.draw_Roll_Whole_3D_method(bitmap, bitmap2, canvas, false);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
            }
        },
        Whole3D_BT("Whole3D_BT") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.draw_Roll_Whole_3D_method(bitmap2, bitmap, canvas, false);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
            }
        },
        Whole3D_LR("Whole3D_LR") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.draw_Roll_Whole_3D_method(bitmap, bitmap2, canvas, false);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
            }
        },
        Whole3D_RL("Whole3D_RL") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.draw_Roll_Whole_3D_method(bitmap2, bitmap, canvas, false);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
            }
        },
        Roll2D_TB("Roll2D_TB") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Roll_Whole_3D_method(bitmap, bitmap2, new Canvas(createBitmap), true);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
            }
        },
        Roll2D_BT("Roll2D_BT") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Roll_Whole_3D_method(bitmap2, bitmap, new Canvas(createBitmap), true);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
            }
        },
        Roll2D_LR("Roll2D_LR") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Roll_Whole_3D_method(bitmap, bitmap2, new Canvas(createBitmap), true);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
            }
        },
        Roll2D_RL("Roll2D_RL") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Roll_Whole_3D_method(bitmap2, bitmap, new Canvas(createBitmap), true);
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
            }
        },
        SepartConbine_TB("SepartConbine_TB") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Separt_Conbine_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap, bitmap2, this);
            }
        },
        SepartConbine_BT("SepartConbine_BT") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Separt_Conbine_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap2, bitmap, this);
            }
        },
        SepartConbine_LR("SepartConbine_LR") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Separt_Conbine_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap, bitmap2, this);
            }
        },
        SepartConbine_RL("SepartConbine_RL") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Separt_Conbine_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap2, bitmap, this);
            }
        },
        RollInTurn_TB("RollInTurn_TB") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Roll_In_Turn_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap, bitmap2, this);
            }
        },
        RollInTurn_BT("RollInTurn_BT") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Roll_In_Turn_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap2, bitmap, this);
            }
        },
        RollInTurn_LR("RollInTurn_LR") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Roll_In_Turn_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap, bitmap2, this);
            }
        },
        RollInTurn_RL("RollInTurn_RL") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Roll_In_Turn_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap2, bitmap, this);
            }
        },
        Jalousie_BT("Jalousie_BT") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_CAL_VAR - i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Jalousie_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 1;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap2, bitmap, this);
            }
        },
        Jalousie_LR("Jalousie_LR") {
            public Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class.SetRotateDegreeMethod(i);
                Bitmap createBitmap = Bitmap.createBitmap(MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
                Final_Mask_Bitmap_3D_Class.draw_Jalousie_method(new Canvas(createBitmap));
                return createBitmap;
            }

            public void initBitmaps(Bitmap bitmap, Bitmap bitmap2) {
                Final_Mask_Bitmap_3D_Class._roll_Mode_obj = this;
                Final_Mask_Bitmap_3D_Class._direction_var = 0;
                Final_Mask_Bitmap_3D_Class.init_Bitmaps_method(bitmap, bitmap2, this);
            }
        };

        String name;

        private EFFECT(String str) {
            this.name = "";
            this.name = str;
        }

        public abstract Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i);

        public abstract void initBitmaps(Bitmap bitmap, Bitmap bitmap2);
    }
}
