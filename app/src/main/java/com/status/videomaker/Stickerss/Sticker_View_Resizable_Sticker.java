package com.status.videomaker.Stickerss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.status.videomaker.Modelss.Information_Component_Model_method;
import com.status.videomaker.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

public class Sticker_View_Resizable_Sticker extends RelativeLayout implements Sticker_Touch_Cmnd_Sticker.TouchCallbackListener {
    public static final String TAG_str = "StickerViewResizable";
    public String field_two_str = "0,0";
    public int he_var;
    public boolean is_Multi_Touch_Enabled_var = true;
    public int left_Margin_var = 0;
    public TouchEventListener listener_obj = null;
    public ImageView main_iv_var;
    public int px_var;
    public int top_Margin_var = 0;
    public int wi_var;
    double a_Double_var = 0.0d;
    double a_Double1_var = 0.0d;
    double a_Double2_var = 0.0d;
    float a_Float_var = 0.0f;
    float a_Float1_var = 0.0f;
    int height_var;
    String img_Type_str;
    int lft_Margin_var;
    float main_Height_var = 0.0f;
    float main_Width_var = 0.0f;
    int r__X;
    int r__Y;
    double rotation_1_var = 0.0d;
    Animation scale_Animation;
    Animation scale_Zoom_In_Animation;
    Animation scale_Zoom_Out_Animation;
    int t0p_Margin_var;
    int width_var;
    private int alpha_Progress = 0;
    private ImageView border_iv_var;
    private Bitmap btmp_obj = null;
    private double center_X_var;
    private double center_Y_var;
    private String color_Type_str = "colored";
    private Context context_obj;
    private ImageView delete_iv_obj;
    private String drawable_Id_str;
    private String field_four_str = "";
    private int field_one_var = 0;
    private String field_three_str = "";
    private ImageView flipp_iv_obj;
    private int hue_Progress = 1;
    private int img_Alpha_var = 255;
    private int img_Color_var = 0;
    private boolean is_Border_Visible = false;
    private boolean is_Color_Filter_Enabled = false;
    private boolean is_Sticker_bool = true;
    private boolean is_Stricker_Edit_Enable = false;
    private OnTouchListener m_Touch_Listener1_obj = new m_On_Touch_Listener1();
    private OnTouchListener r_Touch_Listener_obj = new r_On_Touch_Listener();
    private Uri res_Uri_obj = null;
    private ImageView rotate_iv_var;
    private float rotation__var;
    private int scale_Rotate_Progress = 0;
    private ImageView scale_iv_obj;
    private double scale_original_Height = -1.0d;
    private double scale_original_Width = -1.0d;
    private float scale_original_X = -1.0f;
    private float scale_orginal_Y = -1.0f;
    private int screen_Height_var;
    private int screen_Width_var;
    private String sticker_path_str = "";
    private float this_original_X = -1.0f;
    private float this_original_Y = -1.0f;
    private int x_Rotate_Progress = 0;
    private int y_Rotate_Progress = 0;
    private float y_Rotation_var;
    private int z_Rotate_Progress = 0;

    public Sticker_View_Resizable_Sticker(Context context2) {
        super(context2);
        init(context2);
    }

    public Sticker_View_Resizable_Sticker(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        init(context2);
    }

    public Sticker_View_Resizable_Sticker(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        init(context2);
    }

    public Sticker_View_Resizable_Sticker setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener_obj = touchEventListener;
        return this;
    }

    public void init(Context context2) {
        this.context_obj = context2;
        this.main_iv_var = new ImageView(this.context_obj);
        this.scale_iv_obj = new ImageView(this.context_obj);
        this.border_iv_var = new ImageView(this.context_obj);
        this.flipp_iv_obj = new ImageView(this.context_obj);
        this.rotate_iv_var = new ImageView(this.context_obj);
        this.delete_iv_obj = new ImageView(this.context_obj);
        this.px_var = dpToPx(this.context_obj, 25);
        this.wi_var = dpToPx(this.context_obj, 200);
        this.he_var = dpToPx(this.context_obj, 200);
        this.scale_iv_obj.setImageResource(R.drawable.scale);
        this.border_iv_var.setImageResource(R.drawable.sticker_border_gray);
        this.flipp_iv_obj.setImageResource(R.drawable.horizontal_icon);
        this.rotate_iv_var.setImageResource(R.drawable.ic_refresh);
        this.delete_iv_obj.setImageResource(R.drawable.ic_close);
        LayoutParams layoutParams = new LayoutParams(this.wi_var, this.he_var);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.setMargins(5, 5, 5, 5);
        layoutParams2.addRule(17);
        int i = this.px_var;
        LayoutParams layoutParams3 = new LayoutParams(i, i);
        layoutParams3.addRule(12);
        layoutParams3.addRule(11);
        layoutParams3.setMargins(5, 5, 5, 5);
        int i2 = this.px_var;
        LayoutParams layoutParams4 = new LayoutParams(i2, i2);
        layoutParams4.addRule(10);
        layoutParams4.addRule(11);
        layoutParams4.setMargins(5, 5, 5, 5);
        int i3 = this.px_var;
        LayoutParams layoutParams5 = new LayoutParams(i3, i3);
        layoutParams5.addRule(12);
        layoutParams5.addRule(9);
        layoutParams5.setMargins(5, 5, 5, 5);
        int i4 = this.px_var;
        LayoutParams layoutParams6 = new LayoutParams(i4, i4);
        layoutParams6.addRule(10);
        layoutParams6.addRule(9);
        layoutParams6.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.stickerview_border_gray1);
        addView(this.border_iv_var);
        this.border_iv_var.setLayoutParams(layoutParams7);
        this.border_iv_var.setScaleType(ImageView.ScaleType.FIT_XY);
        this.border_iv_var.setTag("border_iv");
        addView(this.main_iv_var);
        this.main_iv_var.setLayoutParams(layoutParams2);
        addView(this.flipp_iv_obj);
        this.flipp_iv_obj.setLayoutParams(layoutParams4);
        this.flipp_iv_obj.setOnClickListener(new flip_Click_Listener());
        addView(this.rotate_iv_var);
        this.rotate_iv_var.setLayoutParams(layoutParams5);
        this.rotate_iv_var.setOnTouchListener(this.r_Touch_Listener_obj);
        addView(this.delete_iv_obj);
        this.delete_iv_obj.setLayoutParams(layoutParams6);
        this.delete_iv_obj.setOnClickListener(new delete_Click_Listener());
        addView(this.scale_iv_obj);
        this.scale_iv_obj.setLayoutParams(layoutParams3);
        this.scale_iv_obj.setOnTouchListener(this.m_Touch_Listener1_obj);
        this.scale_iv_obj.setTag("scale_iv");
        this.rotation__var = getRotation();
        this.scale_Animation = AnimationUtils.loadAnimation(getContext(), R.anim.stickerview_stickers_scale_animation);
        this.scale_Zoom_Out_Animation = AnimationUtils.loadAnimation(getContext(), R.anim.stickerview_stickerr_scalee_zoom_out);
        this.scale_Zoom_In_Animation = AnimationUtils.loadAnimation(getContext(), R.anim.stickerview_stickerr_scalee_zoom_in);
        this.is_Multi_Touch_Enabled_var = set_Default_Touch_Listener(true);
    }

    public boolean set_Default_Touch_Listener(boolean z) {
        if (z) {
            setOnTouchListener(new Sticker_Touch_Cmnd_Sticker().enable_Rotation(true).set_On_Touch_Callback_Listener(this));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public void set_Border_Visibility_method(boolean z) {
        this.is_Border_Visible = z;
        Log.e("uhi--", "setBorderVisibility: ");
        if (!z) {
            this.border_iv_var.setVisibility(8);
            this.scale_iv_obj.setVisibility(8);
            this.flipp_iv_obj.setVisibility(8);
            this.rotate_iv_var.setVisibility(8);
            this.delete_iv_obj.setVisibility(8);
            setBackgroundResource(0);
            if (this.is_Color_Filter_Enabled) {
                this.main_iv_var.setColorFilter(Color.parseColor("#303828"));
            }
        } else if (this.border_iv_var.getVisibility() != 0) {
            this.border_iv_var.setVisibility(0);
            this.scale_iv_obj.setVisibility(0);
            this.flipp_iv_obj.setVisibility(0);
            this.rotate_iv_var.setVisibility(0);
            this.delete_iv_obj.setVisibility(0);
            setBackgroundResource(R.drawable.stickerview_border_gray1);
            this.main_iv_var.startAnimation(this.scale_Animation);
        }
    }

    public void opecity_Sticker_method(int i) {
        try {
            this.main_iv_var.setImageAlpha(i);
            this.img_Alpha_var = i;
        } catch (Exception unused) {
        }
    }


    public void set_Hue_Progress_method(int i) {
        this.hue_Progress = i;
        int i2 = this.hue_Progress;
        if (i2 == 0) {
            this.main_iv_var.setColorFilter(i);
        } else if (i2 == 100) {
            this.main_iv_var.setColorFilter(i);
        } else {
            this.main_iv_var.setColorFilter(i);
        }
    }

    public int getColor() {
        return this.img_Color_var;
    }

    public void setColor(int i) {
        try {
            this.main_iv_var.setColorFilter(i);
            this.img_Color_var = i;
        } catch (Exception unused) {
        }
    }

    public void setBgDrawable(String str) {
        ((RequestBuilder) ((RequestBuilder) ((RequestBuilder) Glide.with(this.context_obj).load(Integer.valueOf(getResources().getIdentifier(str, "drawable", this.context_obj.getPackageName()))).dontAnimate()).placeholder(R.drawable.stickerview_no_image)).error(R.drawable.stickerview_no_image)).into(this.main_iv_var);
        this.drawable_Id_str = str;
        this.main_iv_var.startAnimation(this.scale_Zoom_Out_Animation);
    }


    public Information_Component_Model_method getComponentInfo() {
        Information_Component_Model_method infoComponentModel = new Information_Component_Model_method();
        infoComponentModel.setPOS_X_VAR(getX());
        infoComponentModel.setPOS_Y_VAR(getY());
        infoComponentModel.setWIDTH_VAR(this.wi_var);
        infoComponentModel.setHEIGHT_VAR(this.he_var);
        infoComponentModel.setRES_ID_VAR(this.drawable_Id_str);
        infoComponentModel.setSTC_COLOR_VAR(this.img_Color_var);
        infoComponentModel.setRES_URI_VAR(this.res_Uri_obj);
        infoComponentModel.setSTC_OPACITY_VAR(this.img_Alpha_var);
        infoComponentModel.setCOLOR_TYPE_STR(this.color_Type_str);
        infoComponentModel.setBITMAP_Obj(this.btmp_obj);
        infoComponentModel.setROTATION_VAR(getRotation());
        infoComponentModel.setY_ROTATION_VAR(this.main_iv_var.getRotationY());
        infoComponentModel.setXRotateProg(this.x_Rotate_Progress);
        infoComponentModel.setYRotateProg(this.y_Rotate_Progress);
        infoComponentModel.setZRotateProg(this.z_Rotate_Progress);
        infoComponentModel.setScale_Prog_VAR(this.scale_Rotate_Progress);
        infoComponentModel.setSTKR_PATH_STR(this.sticker_path_str);
        infoComponentModel.setSTC_HUE_VAR(this.hue_Progress);
        infoComponentModel.setFIELD_ONE_VAR(this.field_one_var);
        infoComponentModel.setFIELD_TWO_STR(this.field_two_str);
        infoComponentModel.setFIELD_THREE_STR(this.field_three_str);
        infoComponentModel.setFIELD_FOUR_STR(this.field_four_str);
        infoComponentModel.setImage_type_str(this.img_Type_str);
        return infoComponentModel;
    }

    public void set_Component_Info_method(Information_Component_Model_method infoComponentModel) {
        this.wi_var = infoComponentModel.getWIDTH_VAR();
        this.he_var = infoComponentModel.getHEIGHT_VAR();
        this.img_Type_str = infoComponentModel.getImage_type_str();
        this.drawable_Id_str = infoComponentModel.getRES_ID_VAR();
        this.res_Uri_obj = infoComponentModel.getRES_URI_VAR();
        this.btmp_obj = infoComponentModel.getBITMAP_Obj();
        this.rotation__var = infoComponentModel.getROTATION_VAR();
        this.img_Color_var = infoComponentModel.getSTC_COLOR_VAR();
        this.y_Rotation_var = infoComponentModel.getY_ROTATION_VAR();
        this.img_Alpha_var = infoComponentModel.getSTC_OPACITY_VAR();
        this.sticker_path_str = infoComponentModel.getSTKR_PATH_STR();
        this.color_Type_str = infoComponentModel.getCOLOR_TYPE_STR();
        this.hue_Progress = infoComponentModel.getSTC_HUE_VAR();
        this.field_two_str = infoComponentModel.getFIELD_TWO_STR();
        if (this.drawable_Id_str.equals("")) {
            this.main_iv_var.setImageBitmap(this.btmp_obj);
        } else {
            setBgDrawable(this.drawable_Id_str);
        }
        if (this.color_Type_str.equals("white")) {
            setColor(this.img_Color_var);
        } else {
            set_Hue_Progress_method(this.hue_Progress);
        }
        setRotation(this.rotation__var);
        opecity_Sticker_method(this.img_Alpha_var);
        if (this.field_two_str.equals("")) {
            getLayoutParams().width = this.wi_var;
            getLayoutParams().height = this.he_var;
            setX(infoComponentModel.getPOS_X_VAR());
            setY(infoComponentModel.getPOS_Y_VAR());
        } else {
            String[] split = this.field_two_str.split(",");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
            ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
            getLayoutParams().width = this.wi_var;
            getLayoutParams().height = this.he_var;
            setX(infoComponentModel.getPOS_X_VAR() + ((float) (parseInt * -1)));
            setY(infoComponentModel.getPOS_Y_VAR() + ((float) (parseInt2 * -1)));
        }
        if (infoComponentModel.getTYPE_STR() == "SHAPE") {
            this.flipp_iv_obj.setVisibility(8);
            this.is_Sticker_bool = false;
        }
        if (infoComponentModel.getTYPE_STR() == "STICKER") {
            this.flipp_iv_obj.setVisibility(0);
            this.is_Sticker_bool = true;
        }
        this.main_iv_var.setRotationY(this.y_Rotation_var);
    }

    public int dpToPx(Context context2, int i) {
        context2.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * ((float) i));
    }

    @Override
    public void onTouchCallback(View view) {
        TouchEventListener touchEventListener = this.listener_obj;
        if (touchEventListener != null) {
            touchEventListener.onTouchDown(view);
        }
    }

    @Override
    public void onTouchUpCallback(View view) {
        TouchEventListener touchEventListener = this.listener_obj;
        if (touchEventListener != null) {
            touchEventListener.onTouchUp(view);
        }
    }

    @Override
    public void onTouchMoveCallback(View view) {
        TouchEventListener touchEventListener = this.listener_obj;
        if (touchEventListener != null) {
            touchEventListener.onTouchMove(view);
        }
    }

    public interface TouchEventListener {
        void onDelete();

        void onEdit(View view, Uri uri);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchUp(View view);
    }

    class m_On_Touch_Listener1 implements OnTouchListener {
        m_On_Touch_Listener1() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Sticker_View_Resizable_Sticker stickerViewResizableSticker = (Sticker_View_Resizable_Sticker) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) Sticker_View_Resizable_Sticker.this.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (stickerViewResizableSticker != null) {
                    stickerViewResizableSticker.requestDisallowInterceptTouchEvent(true);
                }
                if (Sticker_View_Resizable_Sticker.this.listener_obj != null) {
                    Sticker_View_Resizable_Sticker.this.listener_obj.onScaleDown(Sticker_View_Resizable_Sticker.this);
                }
                Sticker_View_Resizable_Sticker.this.invalidate();
                Sticker_View_Resizable_Sticker stickerViewResizableSticker2 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker2.r__X = rawX;
                stickerViewResizableSticker2.r__Y = rawY;
                stickerViewResizableSticker2.width_var = stickerViewResizableSticker2.getWidth();
                Sticker_View_Resizable_Sticker stickerViewResizableSticker3 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker3.height_var = stickerViewResizableSticker3.getHeight();
                Sticker_View_Resizable_Sticker.this.getLocationOnScreen(new int[2]);
                Sticker_View_Resizable_Sticker.this.lft_Margin_var = layoutParams.leftMargin;
                Sticker_View_Resizable_Sticker.this.t0p_Margin_var = layoutParams.topMargin;
            } else if (action == 1) {
                Sticker_View_Resizable_Sticker stickerViewResizableSticker4 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker4.wi_var = stickerViewResizableSticker4.getLayoutParams().width;
                Sticker_View_Resizable_Sticker stickerViewResizableSticker5 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker5.he_var = stickerViewResizableSticker5.getLayoutParams().height;
                Sticker_View_Resizable_Sticker stickerViewResizableSticker6 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker6.left_Margin_var = ((LayoutParams) stickerViewResizableSticker6.getLayoutParams()).leftMargin;
                Sticker_View_Resizable_Sticker stickerViewResizableSticker7 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker7.top_Margin_var = ((LayoutParams) stickerViewResizableSticker7.getLayoutParams()).topMargin;
                Sticker_View_Resizable_Sticker stickerViewResizableSticker8 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker8.field_two_str = String.valueOf(Sticker_View_Resizable_Sticker.this.left_Margin_var) + "," + String.valueOf(Sticker_View_Resizable_Sticker.this.top_Margin_var);
                if (Sticker_View_Resizable_Sticker.this.listener_obj != null) {
                    Sticker_View_Resizable_Sticker.this.listener_obj.onScaleUp(Sticker_View_Resizable_Sticker.this);
                }
            } else if (action == 2) {
                if (stickerViewResizableSticker != null) {
                    stickerViewResizableSticker.requestDisallowInterceptTouchEvent(true);
                }
                if (Sticker_View_Resizable_Sticker.this.listener_obj != null) {
                    Sticker_View_Resizable_Sticker.this.listener_obj.onScaleMove(Sticker_View_Resizable_Sticker.this);
                }
                float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - Sticker_View_Resizable_Sticker.this.r__Y), (double) (rawX - Sticker_View_Resizable_Sticker.this.r__X)));
                if (degrees < 0.0f) {
                    degrees += 360.0f;
                }
                int i = rawX - Sticker_View_Resizable_Sticker.this.r__X;
                int i2 = rawY - Sticker_View_Resizable_Sticker.this.r__Y;
                int i3 = i2 * i2;
                int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - Sticker_View_Resizable_Sticker.this.getRotation()))));
                int sqrt2 = (int) (Math.sqrt((double) ((sqrt * sqrt) + i3)) * Math.sin(Math.toRadians((double) (degrees - Sticker_View_Resizable_Sticker.this.getRotation()))));
                int i4 = (sqrt * 2) + Sticker_View_Resizable_Sticker.this.width_var;
                int i5 = (sqrt2 * 2) + Sticker_View_Resizable_Sticker.this.height_var;
                if (i4 > Sticker_View_Resizable_Sticker.this.px_var) {
                    layoutParams.width = i4;
                    layoutParams.leftMargin = Sticker_View_Resizable_Sticker.this.lft_Margin_var - sqrt;
                }
                if (i5 > Sticker_View_Resizable_Sticker.this.px_var) {
                    layoutParams.height = i5;
                    layoutParams.topMargin = Sticker_View_Resizable_Sticker.this.t0p_Margin_var - sqrt2;
                }
                Sticker_View_Resizable_Sticker.this.setLayoutParams(layoutParams);
                Sticker_View_Resizable_Sticker.this.performLongClick();
            }
            return true;
        }
    }

    class r_On_Touch_Listener implements OnTouchListener {
        r_On_Touch_Listener() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            Sticker_View_Resizable_Sticker stickerViewResizableSticker = (Sticker_View_Resizable_Sticker) view.getParent();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (stickerViewResizableSticker != null) {
                    stickerViewResizableSticker.requestDisallowInterceptTouchEvent(true);
                }
                if (Sticker_View_Resizable_Sticker.this.listener_obj != null) {
                    Sticker_View_Resizable_Sticker.this.listener_obj.onRotateDown(Sticker_View_Resizable_Sticker.this);
                }
                Rect rect = new Rect();
                ((View) view.getParent()).getGlobalVisibleRect(rect);
                Sticker_View_Resizable_Sticker.this.a_Float_var = rect.exactCenterX();
                Sticker_View_Resizable_Sticker.this.a_Float1_var = rect.exactCenterY();
                Sticker_View_Resizable_Sticker.this.rotation_1_var = (double) ((View) view.getParent()).getRotation();
                Sticker_View_Resizable_Sticker stickerViewResizableSticker2 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker2.a_Double2_var = (Math.atan2((double) (stickerViewResizableSticker2.a_Float1_var - motionEvent.getRawY()), (double) (Sticker_View_Resizable_Sticker.this.a_Float_var - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                Sticker_View_Resizable_Sticker stickerViewResizableSticker3 = Sticker_View_Resizable_Sticker.this;
                stickerViewResizableSticker3.a_Double1_var = stickerViewResizableSticker3.rotation_1_var - Sticker_View_Resizable_Sticker.this.a_Double2_var;
            } else if (action != 1) {
                if (action == 2) {
                    if (stickerViewResizableSticker != null) {
                        stickerViewResizableSticker.requestDisallowInterceptTouchEvent(true);
                    }
                    if (Sticker_View_Resizable_Sticker.this.listener_obj != null) {
                        Sticker_View_Resizable_Sticker.this.listener_obj.onRotateMove(Sticker_View_Resizable_Sticker.this);
                    }
                    Sticker_View_Resizable_Sticker stickerViewResizableSticker4 = Sticker_View_Resizable_Sticker.this;
                    stickerViewResizableSticker4.a_Double_var = (Math.atan2((double) (stickerViewResizableSticker4.a_Float1_var - motionEvent.getRawY()), (double) (Sticker_View_Resizable_Sticker.this.a_Float_var - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (Sticker_View_Resizable_Sticker.this.a_Double_var + Sticker_View_Resizable_Sticker.this.a_Double1_var));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                }
            } else if (Sticker_View_Resizable_Sticker.this.listener_obj != null) {
                Sticker_View_Resizable_Sticker.this.listener_obj.onRotateUp(Sticker_View_Resizable_Sticker.this);
            }
            return true;
        }
    }

    public class flip_Click_Listener implements OnClickListener {
        flip_Click_Listener() {
        }

        public void onClick(View view) {
            ImageView imageView = Sticker_View_Resizable_Sticker.this.main_iv_var;
            float f = -180.0f;
            if (Sticker_View_Resizable_Sticker.this.main_iv_var.getRotationY() == -180.0f) {
                f = 0.0f;
            }
            imageView.setRotationY(f);
            Sticker_View_Resizable_Sticker.this.main_iv_var.invalidate();
            Sticker_View_Resizable_Sticker.this.requestLayout();
        }
    }

    public class delete_Click_Listener implements OnClickListener {
        delete_Click_Listener() {
        }

        public void onClick(View view) {
            final ViewGroup viewGroup = (ViewGroup) Sticker_View_Resizable_Sticker.this.getParent();
            Sticker_View_Resizable_Sticker.this.scale_Zoom_In_Animation.setAnimationListener(new Animation.AnimationListener() {
                /* class com.MyMovieMaker.videomakerwithmusic.stickerview.StickerViewResizable.deleteClickListener.AnonymousClass1 */

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(Sticker_View_Resizable_Sticker.this);
                }
            });
            Sticker_View_Resizable_Sticker.this.main_iv_var.startAnimation(Sticker_View_Resizable_Sticker.this.scale_Zoom_In_Animation);
            Sticker_View_Resizable_Sticker.this.set_Border_Visibility_method(false);
            if (Sticker_View_Resizable_Sticker.this.listener_obj != null) {
                Sticker_View_Resizable_Sticker.this.listener_obj.onDelete();
            }
        }
    }

    class dismiss_Lstnr_ implements DialogInterface.OnDismissListener {
        dismiss_Lstnr_() {
        }

        public void onDismiss(DialogInterface dialogInterface) {
        }
    }
}
