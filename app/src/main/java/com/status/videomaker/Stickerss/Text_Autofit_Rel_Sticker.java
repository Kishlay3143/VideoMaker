package com.status.videomaker.Stickerss;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;

import com.status.videomaker.Modelss.Sticker_Text_Info_Model;
import com.status.videomaker.R;

import java.util.Random;

public class Text_Autofit_Rel_Sticker extends RelativeLayout implements Sticker_Touch_Cmnd_Sticker.TouchCallbackListener {
    private static final String _TAG_ = "TextAutofitRel";
    public ImageView _backgrnd_iv;
    public String bg_Drawable_str = "0";
    public String field_two_str = "0,0";
    public int he_var;
    public boolean is_Multi_Touch_Enabled_var = true;
    public int left_Margin_var = 0;
    public Touch_Event_Listener_interface listener_obj = null;
    public int px_var;
    public Text_Auto_Resize_View_Sticker text_iv_obj;
    public int top_Margin_var = 0;
    public int wi_var;
    double a_Double_var = 0.0d;
    double a_Double1_var = 0.0d;
    double a_Double2_var = 0.0d;
    float a_Float_var;
    int a_n_Int = 1794;
    int an_Int1_var = 1080;
    float center_X_var = 0.0f;
    float center_Y_var = 0.0f;
    int height_var;
    int lft_Margin_var;
    int p_X_var;
    int p_Y_var;
    int r_X_var;
    int r_Y_var;
    double rotation1_var = 0.0d;
    Animation scale_Animation;
    Animation scale_ZoomIn_Animation;
    Animation scale_ZoomOut_Animation;
    int tpMargin;
    int width_var;
    private int bg_Alpha_var = 255;
    private int bg_Color_var = 0;
    private ImageView borderrr_iv;
    private int capital_Flage_var;
    private Context context;
    private ImageView _delete_iv;
    private String field_four_str = "";
    private int field_one_var = 0;
    private String field_three_str = "";
    private String font_Name_str = "";
    private GestureDetector gesture_Detector_obj = null;
    private boolean is_Bold_bool;
    private boolean is_Border_Visible_bool = false;
    private boolean is_Italic_bool = false;
    private boolean is_UnderLine_bool = false;
    private int left_Right_Shadow_var = 1;
    private OnTouchListener m_TouchListener1_obj = new m_On_Touch_Listener1();
    private int progress_var = 0;
    private OnTouchListener r_TouchListener_obj = new r_On_Touch_Listener();
    private ImageView _rotate_iv;
    private float rotation_var;
    private ImageView _scale_iv;
    private int shadow_Color_var = 0;
    private int shadow_Color_Progress_var = 255;
    private int shadow_Progress = 0;
    private int shadow_x_var = 0;
    private int shadow_y_var = 0;
    private int t_Alpha_var = 100;
    private int t_Color_var = ViewCompat.MEASURED_STATE_MASK;
    private String text_str = "";
    private Path text_Path_obj;
    private int top_Bottom_Shadow_var = 1;
    private int x_Rotate_Progress = 0;
    private int y_Rotate_Progress = 0;
    private int z_Rotate_Progress = 0;

    public Text_Autofit_Rel_Sticker(Context context2) {
        super(context2);
        init(context2);
    }

    public Text_Autofit_Rel_Sticker(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        init(context2);
    }

    public Text_Autofit_Rel_Sticker(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        init(context2);
    }

    public Text_Autofit_Rel_Sticker setOnTouchCallbackListener(Touch_Event_Listener_interface touchEventListenerInterface) {
        this.listener_obj = touchEventListenerInterface;
        return this;
    }

    public void setDrawParams() {
        invalidate();
    }

    public void init(Context context2) {
        this.context = context2;
        Display defaultDisplay = ((Activity) this.context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.p_X_var = point.x;
        this.p_Y_var = point.y;
        this.a_Float_var = ((float) this.p_X_var) / ((float) this.p_Y_var);
        this.text_iv_obj = new Text_Auto_Resize_View_Sticker(this.context);
        this._scale_iv = new ImageView(this.context);
        this.borderrr_iv = new ImageView(this.context);
        this._backgrnd_iv = new ImageView(this.context);
        this._delete_iv = new ImageView(this.context);
        this._rotate_iv = new ImageView(this.context);
        this.px_var = dp_To_Px(this.context, 30);
        this.wi_var = dp_To_Px(this.context, 200);
        this.he_var = dp_To_Px(this.context, 200);
        this._scale_iv.setImageResource(R.drawable.resize);
        this._backgrnd_iv.setImageResource(0);
        this._rotate_iv.setImageResource(R.drawable.ic_refresh);
        this._delete_iv.setImageResource(R.drawable.ic_close);
        LayoutParams layoutParams = new LayoutParams(this.wi_var, this.he_var);
        int i = this.px_var;
        LayoutParams layoutParams2 = new LayoutParams(i, i);
        layoutParams2.addRule(12);
        layoutParams2.addRule(11);
        int i2 = this.px_var;
        LayoutParams layoutParams3 = new LayoutParams(i2, i2);
        layoutParams3.addRule(12);
        layoutParams3.addRule(9);
        LayoutParams layoutParams4 = new LayoutParams(-1, -1);
        layoutParams4.addRule(17);
        int i3 = this.px_var;
        LayoutParams layoutParams5 = new LayoutParams(i3, i3);
        layoutParams5.addRule(10);
        layoutParams5.addRule(9);
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.stickerview_border_gray);
        addView(this._backgrnd_iv);
        this._backgrnd_iv.setLayoutParams(layoutParams7);
        this._backgrnd_iv.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(this.borderrr_iv);
        this.borderrr_iv.setLayoutParams(layoutParams6);
        this.borderrr_iv.setTag("border_iv");
        addView(this.text_iv_obj);
        this.text_iv_obj.setText(this.text_str);
        this.text_iv_obj.setTextColor(this.t_Color_var);
        this.text_iv_obj.setTextSize(400.0f);
        this.text_iv_obj.setLayoutParams(layoutParams4);
        this.text_iv_obj.setGravity(17);
        this.text_iv_obj.set_Min_Text_Size__method(50.0f);
        addView(this._delete_iv);
        this._delete_iv.setLayoutParams(layoutParams5);
        this._delete_iv.setOnClickListener(new delete_Click_Listener());
        addView(this._rotate_iv);
        this._rotate_iv.setLayoutParams(layoutParams3);
        this._rotate_iv.setOnTouchListener(this.r_TouchListener_obj);
        addView(this._scale_iv);
        this._scale_iv.setLayoutParams(layoutParams2);
        this._scale_iv.setTag("scale_iv");
        this._scale_iv.setOnTouchListener(this.m_TouchListener1_obj);
        this.rotation_var = getRotation();
        this.scale_Animation = AnimationUtils.loadAnimation(getContext(), R.anim.stickerview_text_scale_animation);
        this.scale_ZoomOut_Animation = AnimationUtils.loadAnimation(getContext(), R.anim.stickerview_text_scale_zoom_out_anim);
        this.scale_ZoomIn_Animation = AnimationUtils.loadAnimation(getContext(), R.anim.stickerview_text_scale_zoom_in_anim);
        init_GD();
        this.is_Multi_Touch_Enabled_var = set_Default_TouchListener(true);
    }

    public void apply_Letter_Spacing_method(float f) {
        if (this.text_str != null) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < this.text_str.length()) {
                sb.append("" + this.text_str.charAt(i));
                i++;
                if (i < this.text_str.length()) {
                    sb.append(" ");
                }
            }
            SpannableString spannableString = new SpannableString(sb.toString());
            if (sb.toString().length() > 1) {
                for (int i2 = 1; i2 < sb.toString().length(); i2 += 2) {
                    spannableString.setSpan(new ScaleXSpan((1.0f + f) / 10.0f), i2, i2 + 1, 33);
                }
            }
            this.text_iv_obj.setText(spannableString, TextView.BufferType.SPANNABLE);
        }
    }

    public void apply_Line_Spacing_method(float f) {
        this.text_iv_obj.setLineSpacing(f, 1.0f);
    }

    public void set_Bold_Font() {
        if (this.is_Bold_bool) {
            this.is_Bold_bool = false;
            this.text_iv_obj.setTypeface(Typeface.DEFAULT);
            return;
        }
        this.is_Bold_bool = true;
        this.text_iv_obj.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void set_Capital_Font() {
        if (this.capital_Flage_var == 0) {
            this.capital_Flage_var = 1;
            Text_Auto_Resize_View_Sticker textAutoResizeViewSticker = this.text_iv_obj;
            textAutoResizeViewSticker.setText(textAutoResizeViewSticker.getText().toString().toUpperCase());
            return;
        }
        this.capital_Flage_var = 0;
        Text_Auto_Resize_View_Sticker textAutoResizeViewSticker2 = this.text_iv_obj;
        textAutoResizeViewSticker2.setText(textAutoResizeViewSticker2.getText().toString().toLowerCase());
    }

    public void set_UnderLine_Font() {
        if (this.is_UnderLine_bool) {
            this.is_UnderLine_bool = false;
            this.text_iv_obj.setText(Html.fromHtml(this.text_str.replace("<u>", "").replace("</u>", "")));
            return;
        }
        this.is_UnderLine_bool = true;
        Text_Auto_Resize_View_Sticker textAutoResizeViewSticker = this.text_iv_obj;
        textAutoResizeViewSticker.setText(Html.fromHtml("<u>" + this.text_str + "</u>"));
    }

    public void set_Italic_Font() {
        if (this.is_Italic_bool) {
            this.is_Italic_bool = false;
            TextView textView = new TextView(this.context);
            textView.setText(this.text_str);
            if (this.is_Bold_bool) {
                textView.setTypeface(textView.getTypeface(), 1);
            } else {
                textView.setTypeface(textView.getTypeface(), 0);
            }
            this.text_iv_obj.setTypeface(textView.getTypeface());
            return;
        }
        this.is_Italic_bool = true;
        TextView textView2 = new TextView(this.context);
        textView2.setText(this.text_str);
        if (this.is_Bold_bool) {
            textView2.setTypeface(textView2.getTypeface(), 3);
        } else {
            textView2.setTypeface(textView2.getTypeface(), 2);
        }
        this.text_iv_obj.setTypeface(textView2.getTypeface());
    }

    public void set_Left_AlignMent() {
        this.text_iv_obj.setGravity(19);
    }

    public void set_Center_AlignMent() {
        this.text_iv_obj.setGravity(17);
    }

    public void set_Right_AlignMent() {
        this.text_iv_obj.setGravity(21);
    }

    public boolean set_Default_TouchListener(boolean z) {
        if (z) {
            setOnTouchListener(new Sticker_Touch_Cmnd_Sticker().enable_Rotation(true).set_On_Touch_Callback_Listener(this).set_Gesture_Listener(this.gesture_Detector_obj));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public boolean get_Border_Visibility() {
        return this.is_Border_Visible_bool;
    }

    public void set_Border_Visibility(boolean z) {
        this.is_Border_Visible_bool = z;
        if (!z) {
            this.borderrr_iv.setVisibility(8);
            this._scale_iv.setVisibility(8);
            this._delete_iv.setVisibility(8);
            this._rotate_iv.setVisibility(8);
            setBackgroundResource(0);
        } else if (this.borderrr_iv.getVisibility() != 0) {
            this.borderrr_iv.setVisibility(0);
            this._scale_iv.setVisibility(0);
            this._delete_iv.setVisibility(0);
            this._rotate_iv.setVisibility(0);
            setBackgroundResource(R.drawable.stickerview_border_gray);
            this.text_iv_obj.startAnimation(this.scale_Animation);
        }
    }

    public String get_Text_str() {
        return this.text_iv_obj.getText().toString();
    }

    public void set_Text_str(String str) {
        this.text_iv_obj.setText(str);
        this.text_str = str;
        this.text_iv_obj.startAnimation(this.scale_ZoomOut_Animation);
    }

    public void set_Text_Font(String str) {
        try {
            Text_Auto_Resize_View_Sticker textAutoResizeViewSticker = this.text_iv_obj;
            AssetManager assets = this.context.getAssets();
            textAutoResizeViewSticker.setTypeface(Typeface.createFromAsset(assets, "fonts/" + str));
            this.font_Name_str = str;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set_Scale_Increase() {
        Text_Auto_Resize_View_Sticker textAutoResizeViewSticker = this.text_iv_obj;
        textAutoResizeViewSticker.setScaleX(textAutoResizeViewSticker.getScaleX() + 0.05f);
        Text_Auto_Resize_View_Sticker textAutoResizeViewSticker2 = this.text_iv_obj;
        textAutoResizeViewSticker2.setScaleY(textAutoResizeViewSticker2.getScaleY() + 0.05f);
    }

    public void set_Scale_Decrease() {
        Text_Auto_Resize_View_Sticker textAutoResizeViewSticker = this.text_iv_obj;
        textAutoResizeViewSticker.setScaleX(textAutoResizeViewSticker.getScaleX() - 0.05f);
        Text_Auto_Resize_View_Sticker textAutoResizeViewSticker2 = this.text_iv_obj;
        textAutoResizeViewSticker2.setScaleY(textAutoResizeViewSticker2.getScaleY() - 0.05f);
    }

    public String get_Font_Name_str_method() {
        return this.font_Name_str;
    }

    public int get_Text_Color() {
        return this.t_Color_var;
    }

    public void set_Text_Color(int i) {
        SpannableString spannableString = new SpannableString(this.text_iv_obj.getText());
        int i2 = 0;
        while (i2 < this.text_iv_obj.getText().length()) {
            int i3 = i2 + 1;
            spannableString.setSpan(new ForegroundColorSpan(i), i2, i3, 33);
            i2 = i3;
        }
        this.text_iv_obj.getPaint().setShader(null);
        this.text_iv_obj.setText(spannableString);
        invalidate();
    }

    public void set_Multi_color() {
        SpannableString spannableString = new SpannableString(this.text_iv_obj.getText());
        int i = 0;
        while (i < this.text_iv_obj.getText().length()) {
            int i2 = i + 1;
            spannableString.setSpan(new ForegroundColorSpan(get_Random_Color()), i, i2, 33);
            i = i2;
        }
        this.text_iv_obj.getPaint().setShader(null);
        this.text_iv_obj.setText(spannableString);
        invalidate();
    }

    public void set_Gradient_method(int i, int i2, int i3) {
        this.text_iv_obj.getPaint().setShader(new BitmapShader(gradient_genertor_method(i, i2), Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
        this.text_iv_obj.invalidate();
    }

    public Bitmap gradient_genertor_method(int i, int i2) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{i, i2});
        gradientDrawable.setGradientType(0);
        gradientDrawable.setShape(0);
        gradientDrawable.setSize(100, 100);
        return drawable_To_Bitmap_method(gradientDrawable);
    }

    public Bitmap drawable_To_Bitmap_method(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private LinearGradient generate_gradient_colors() {
        return new LinearGradient(0.0f, 0.0f, 0.0f, 50.0f, get_Random_Color(), get_Random_Color(), Shader.TileMode.MIRROR);
    }

    private int get_Random_Color() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public int get_Text_Alpha() {
        return this.t_Alpha_var;
    }

    public void set_Text_Alpha(int i) {
        this.text_iv_obj.setAlpha(((float) i) / 100.0f);
        this.t_Alpha_var = i;
    }

    public int get_Text_Shadow_Color_method() {
        return this.shadow_Color_var;
    }

    public void set_Text_Shadow_Color(int i) {
        this.shadow_Color_var = i;
        this.shadow_Color_var = ColorUtils.setAlphaComponent(this.shadow_Color_var, this.shadow_Color_Progress_var);
        this.text_iv_obj.setShadowLayer((float) this.shadow_Progress, (float) this.left_Right_Shadow_var, (float) this.top_Bottom_Shadow_var, this.shadow_Color_var);
    }

    public void set_Text_Shadow_Opacity(int i) {
        this.shadow_Color_Progress_var = i;
        this.shadow_Color_var = ColorUtils.setAlphaComponent(this.shadow_Color_var, i);
        this.text_iv_obj.setShadowLayer((float) this.shadow_Progress, (float) this.left_Right_Shadow_var, (float) this.top_Bottom_Shadow_var, this.shadow_Color_var);
    }

    public void setLeft_Right_Shadow_var(int i) {
        this.left_Right_Shadow_var = i;
        this.text_iv_obj.setShadowLayer((float) this.shadow_Progress, (float) this.left_Right_Shadow_var, (float) this.top_Bottom_Shadow_var, this.shadow_Color_var);
    }

    public void setTop_Bottom_Shadow_var(int i) {
        this.top_Bottom_Shadow_var = i;
        this.text_iv_obj.setShadowLayer((float) this.shadow_Progress, (float) this.left_Right_Shadow_var, (float) this.top_Bottom_Shadow_var, this.shadow_Color_var);
    }

    public int get_Text_Shadow_Progress() {
        return this.shadow_Progress;
    }

    public void set_Text_Shadow_Progress(int i) {
        this.shadow_Progress = i;
        this.text_iv_obj.setShadowLayer((float) this.shadow_Progress, (float) this.left_Right_Shadow_var, (float) this.top_Bottom_Shadow_var, this.shadow_Color_var);
    }

    public void set_RotationX(int i) {
        this.x_Rotate_Progress = i;
        this.text_iv_obj.setRotationX((float) i);
        this.text_iv_obj.invalidate();
    }

    public void set_Rotation_Y(int i) {
        this.y_Rotate_Progress = i;
        this.text_iv_obj.setRotationY((float) i);
        this.text_iv_obj.invalidate();
    }

    public String getBg_Drawable_str() {
        return this.bg_Drawable_str;
    }

    public void setBg_Drawable_str(String str) {
        this.bg_Drawable_str = str;
        this.bg_Color_var = 0;
        this._backgrnd_iv.setImageBitmap(get_Tiled_Bitmap(this.context, getResources().getIdentifier(str, "drawable", this.context.getPackageName()), this.wi_var, this.he_var));
    }

    public int getBg_Color_var() {
        return this.bg_Color_var;
    }

    public void setBg_Color_var(int i) {
        this.bg_Drawable_str = "0";
        this.bg_Color_var = i;
        this._backgrnd_iv.setImageBitmap(null);
        this._backgrnd_iv.setBackgroundColor(i);
    }

    public int getBg_Alpha_var() {
        return this.bg_Alpha_var;
    }

    public void setBg_Alpha_var(int i) {
        this._backgrnd_iv.setAlpha(((float) i) / 255.0f);
        this.bg_Alpha_var = i;
    }

    public Sticker_Text_Info_Model getTextInfo_method() {
        Sticker_Text_Info_Model stickerTextInfoModel = new Sticker_Text_Info_Model();
        stickerTextInfoModel.set_POS_X_(getX());
        stickerTextInfoModel.set_POSITION_Y_(getY());
        stickerTextInfoModel.set_WIDTH_(this.wi_var);
        stickerTextInfoModel.set_HEIGHT_(this.he_var);
        stickerTextInfoModel.set_TXT_(this.text_str);
        stickerTextInfoModel.set_FONT_NAME_(this.font_Name_str);
        stickerTextInfoModel.set_TXT_COLOR(this.t_Color_var);
        stickerTextInfoModel.set_TXT_ALPHA(this.t_Alpha_var);
        stickerTextInfoModel.set_SHADOWS_COLOR_(this.shadow_Color_var);
        stickerTextInfoModel.set_SHADOW_PROGRESS(this.shadow_Progress);
        stickerTextInfoModel.set_SHADOW_X_(this.left_Right_Shadow_var);
        stickerTextInfoModel.set_SHADOW_Y_(this.top_Bottom_Shadow_var);
        stickerTextInfoModel.set_BG_COLOR_(this.bg_Color_var);
        stickerTextInfoModel.set_BG_DRAWABLE_(this.bg_Drawable_str);
        stickerTextInfoModel.set_BG_ALPHA_(this.bg_Alpha_var);
        stickerTextInfoModel.set_ROTATION_(getRotation());
        stickerTextInfoModel.setXRotateProg(this.x_Rotate_Progress);
        stickerTextInfoModel.setYRotateProg(this.y_Rotate_Progress);
        stickerTextInfoModel.setZRotateProg(this.z_Rotate_Progress);
        stickerTextInfoModel.set_curve_Rotate_Prog(this.progress_var);
        stickerTextInfoModel.set_FIELD_ONE(this.field_one_var);
        stickerTextInfoModel.set_FIELD_TWO_(this.field_two_str);
        stickerTextInfoModel.set_FIELD_THREE_(this.field_three_str);
        stickerTextInfoModel.set_FIELD_FOUR_(this.field_four_str);
        return stickerTextInfoModel;
    }

    public void setTextInfo_method(Sticker_Text_Info_Model stickerTextInfoModel, boolean z) {
        Log.e("set Text value", "" + stickerTextInfoModel.get_POS_X_() + " ," + stickerTextInfoModel.get_POSITION_Y_() + " ," + stickerTextInfoModel.get_WIDTH_() + " ," + stickerTextInfoModel.get_HEIGHT_() + " ," + stickerTextInfoModel.get_FIELD_TWO_());
        this.wi_var = stickerTextInfoModel.get_WIDTH_();
        this.he_var = stickerTextInfoModel.get_HEIGHT_();
        this.text_str = stickerTextInfoModel.get_TXT_();
        this.font_Name_str = stickerTextInfoModel.get_FONT_NAME_();
        this.t_Color_var = stickerTextInfoModel.get_TXT_COLOR();
        this.t_Alpha_var = stickerTextInfoModel.get_TXT_ALPHA();
        this.shadow_Color_var = stickerTextInfoModel.get_SHADOWS_COLOR_();
        this.shadow_Progress = stickerTextInfoModel.get_SHADOW_PROGRESS();
        this.left_Right_Shadow_var = stickerTextInfoModel.get_SHADOW_X_();
        this.top_Bottom_Shadow_var = stickerTextInfoModel.get_SHADOW_Y_();
        this.bg_Color_var = stickerTextInfoModel.get_BG_COLOR_();
        this.bg_Drawable_str = stickerTextInfoModel.get_BG_DRAWABLE_();
        this.bg_Alpha_var = stickerTextInfoModel.get_BG_ALPHA_();
        this.rotation_var = stickerTextInfoModel.get_ROTATION_();
        this.field_two_str = stickerTextInfoModel.get_FIELD_TWO_();
        set_Text_str(this.text_str);
        set_Text_Font(this.font_Name_str);
        set_Text_Color(this.t_Color_var);
        set_Text_Alpha(this.t_Alpha_var);
        set_Text_Shadow_Color(this.shadow_Color_var);
        set_Text_Shadow_Progress(this.shadow_Progress);
        setLeft_Right_Shadow_var(this.left_Right_Shadow_var);
        setTop_Bottom_Shadow_var(this.top_Bottom_Shadow_var);
        int i = this.bg_Color_var;
        if (i != 0) {
            setBg_Color_var(i);
        } else {
            this._backgrnd_iv.setBackgroundColor(0);
        }
        if (this.bg_Drawable_str.equals("0")) {
            this._backgrnd_iv.setImageBitmap(null);
        } else {
            setBg_Drawable_str(this.bg_Drawable_str);
        }
        setBg_Alpha_var(this.bg_Alpha_var);
        setRotation(stickerTextInfoModel.get_ROTATION_());
        if (this.field_two_str.equals("")) {
            getLayoutParams().width = this.wi_var;
            getLayoutParams().height = this.he_var;
            setX(stickerTextInfoModel.get_POS_X_());
            setY(stickerTextInfoModel.get_POSITION_Y_());
            return;
        }
        String[] split = this.field_two_str.split(",");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
        ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
        getLayoutParams().width = this.wi_var;
        getLayoutParams().height = this.he_var;
        setX(stickerTextInfoModel.get_POS_X_() + ((float) (parseInt * -1)));
        setY(stickerTextInfoModel.get_POSITION_Y_() + ((float) (parseInt2 * -1)));
    }

    public void optimize_method(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (((float) this.wi_var) * f);
        getLayoutParams().height = (int) (((float) this.he_var) * f2);
    }

    public void incrX() {
        setX(getX() + 2.0f);
    }

    public void decX() {
        setX(getX() - 2.0f);
    }

    public void incrY() {
        setY(getY() + 2.0f);
    }

    public void decY() {
        setY(getY() - 2.0f);
    }

    public int dp_To_Px(Context context2, int i) {
        context2.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * ((float) i));
    }

    private Bitmap get_Tiled_Bitmap(Context context2, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context2.getResources(), i, new BitmapFactory.Options()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    private void init_GD() {
        this.gesture_Detector_obj = new GestureDetector(this.context, new smpl_Gesture_Listener());
    }

    @Override
    public void onTouchCallback(View view) {
        Touch_Event_Listener_interface touchEventListenerInterface = this.listener_obj;
        if (touchEventListenerInterface != null) {
            touchEventListenerInterface.onTouchDown(view);
        }
    }

    @Override
    public void onTouchUpCallback(View view) {
        Touch_Event_Listener_interface touchEventListenerInterface = this.listener_obj;
        if (touchEventListenerInterface != null) {
            touchEventListenerInterface.onTouchUp(view);
        }
    }

    @Override
    public void onTouchMoveCallback(View view) {
        Touch_Event_Listener_interface touchEventListenerInterface = this.listener_obj;
        if (touchEventListenerInterface != null) {
            touchEventListenerInterface.onTouchMove(view);
        }
    }

    public float get_New_X(float f) {
        return ((float) this.p_X_var) * (f / ((float) this.an_Int1_var));
    }

    public float get_New_Y(float f) {
        return ((float) this.p_Y_var) * (f / ((float) this.a_n_Int));
    }

    public interface Touch_Event_Listener_interface {
        void onDelete();

        void onDoubleTap();

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
            Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) Text_Autofit_Rel_Sticker.this.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (textAutofitRelSticker != null) {
                    textAutofitRelSticker.requestDisallowInterceptTouchEvent(true);
                }
                if (Text_Autofit_Rel_Sticker.this.listener_obj != null) {
                    Text_Autofit_Rel_Sticker.this.listener_obj.onScaleDown(Text_Autofit_Rel_Sticker.this);
                }
                Text_Autofit_Rel_Sticker.this.invalidate();
                Text_Autofit_Rel_Sticker textAutofitRelSticker2 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker2.r_X_var = rawX;
                textAutofitRelSticker2.r_Y_var = rawY;
                textAutofitRelSticker2.width_var = textAutofitRelSticker2.getWidth();
                Text_Autofit_Rel_Sticker textAutofitRelSticker3 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker3.height_var = textAutofitRelSticker3.getHeight();
                Text_Autofit_Rel_Sticker.this.getLocationOnScreen(new int[2]);
                Text_Autofit_Rel_Sticker.this.lft_Margin_var = layoutParams.leftMargin;
                Text_Autofit_Rel_Sticker.this.tpMargin = layoutParams.topMargin;
            } else if (action == 1) {
                Text_Autofit_Rel_Sticker textAutofitRelSticker4 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker4.wi_var = textAutofitRelSticker4.getLayoutParams().width;
                Text_Autofit_Rel_Sticker textAutofitRelSticker5 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker5.he_var = textAutofitRelSticker5.getLayoutParams().height;
                Text_Autofit_Rel_Sticker textAutofitRelSticker6 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker6.left_Margin_var = ((LayoutParams) textAutofitRelSticker6.getLayoutParams()).leftMargin;
                Text_Autofit_Rel_Sticker textAutofitRelSticker7 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker7.top_Margin_var = ((LayoutParams) textAutofitRelSticker7.getLayoutParams()).topMargin;
                Text_Autofit_Rel_Sticker textAutofitRelSticker8 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker8.field_two_str = String.valueOf(Text_Autofit_Rel_Sticker.this.left_Margin_var) + "," + String.valueOf(Text_Autofit_Rel_Sticker.this.top_Margin_var);
                if (Text_Autofit_Rel_Sticker.this.listener_obj != null) {
                    Text_Autofit_Rel_Sticker.this.listener_obj.onScaleUp(Text_Autofit_Rel_Sticker.this);
                }
            } else if (action == 2) {
                if (textAutofitRelSticker != null) {
                    textAutofitRelSticker.requestDisallowInterceptTouchEvent(true);
                }
                if (Text_Autofit_Rel_Sticker.this.listener_obj != null) {
                    Text_Autofit_Rel_Sticker.this.listener_obj.onScaleMove(Text_Autofit_Rel_Sticker.this);
                }
                float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - Text_Autofit_Rel_Sticker.this.r_Y_var), (double) (rawX - Text_Autofit_Rel_Sticker.this.r_X_var)));
                if (degrees < 0.0f) {
                    degrees += 360.0f;
                }
                int i = rawX - Text_Autofit_Rel_Sticker.this.r_X_var;
                int i2 = rawY - Text_Autofit_Rel_Sticker.this.r_Y_var;
                int i3 = i2 * i2;
                int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - Text_Autofit_Rel_Sticker.this.getRotation()))));
                int sqrt2 = (int) (Math.sqrt((double) ((sqrt * sqrt) + i3)) * Math.sin(Math.toRadians((double) (degrees - Text_Autofit_Rel_Sticker.this.getRotation()))));
                int i4 = (sqrt * 2) + Text_Autofit_Rel_Sticker.this.width_var;
                int i5 = (sqrt2 * 2) + Text_Autofit_Rel_Sticker.this.height_var;
                if (i4 > Text_Autofit_Rel_Sticker.this.px_var) {
                    layoutParams.width = i4;
                    layoutParams.leftMargin = Text_Autofit_Rel_Sticker.this.lft_Margin_var - sqrt;
                }
                if (i5 > Text_Autofit_Rel_Sticker.this.px_var) {
                    layoutParams.height = i5;
                    layoutParams.topMargin = Text_Autofit_Rel_Sticker.this.tpMargin - sqrt2;
                }
                Text_Autofit_Rel_Sticker.this.setLayoutParams(layoutParams);
                if (!Text_Autofit_Rel_Sticker.this.bg_Drawable_str.equals("0")) {
                    Text_Autofit_Rel_Sticker textAutofitRelSticker9 = Text_Autofit_Rel_Sticker.this;
                    textAutofitRelSticker9.wi_var = textAutofitRelSticker9.getLayoutParams().width;
                    Text_Autofit_Rel_Sticker textAutofitRelSticker10 = Text_Autofit_Rel_Sticker.this;
                    textAutofitRelSticker10.he_var = textAutofitRelSticker10.getLayoutParams().height;
                    Text_Autofit_Rel_Sticker textAutofitRelSticker11 = Text_Autofit_Rel_Sticker.this;
                    textAutofitRelSticker11.setBg_Drawable_str(textAutofitRelSticker11.bg_Drawable_str);
                }
            }
            return true;
        }
    }

    class r_On_Touch_Listener implements OnTouchListener {
        r_On_Touch_Listener() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) view.getParent();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (textAutofitRelSticker != null) {
                    textAutofitRelSticker.requestDisallowInterceptTouchEvent(true);
                }
                if (Text_Autofit_Rel_Sticker.this.listener_obj != null) {
                    Text_Autofit_Rel_Sticker.this.listener_obj.onRotateDown(Text_Autofit_Rel_Sticker.this);
                }
                Rect rect = new Rect();
                ((View) view.getParent()).getGlobalVisibleRect(rect);
                Text_Autofit_Rel_Sticker.this.center_X_var = rect.exactCenterX();
                Text_Autofit_Rel_Sticker.this.center_Y_var = rect.exactCenterY();
                Text_Autofit_Rel_Sticker.this.rotation1_var = (double) ((View) view.getParent()).getRotation();
                Text_Autofit_Rel_Sticker textAutofitRelSticker2 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker2.a_Double2_var = (Math.atan2((double) (textAutofitRelSticker2.center_Y_var - motionEvent.getRawY()), (double) (Text_Autofit_Rel_Sticker.this.center_X_var - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                Text_Autofit_Rel_Sticker textAutofitRelSticker3 = Text_Autofit_Rel_Sticker.this;
                textAutofitRelSticker3.a_Double1_var = textAutofitRelSticker3.rotation1_var - Text_Autofit_Rel_Sticker.this.a_Double2_var;
            } else if (action != 1) {
                if (action == 2) {
                    if (textAutofitRelSticker != null) {
                        textAutofitRelSticker.requestDisallowInterceptTouchEvent(true);
                    }
                    if (Text_Autofit_Rel_Sticker.this.listener_obj != null) {
                        Text_Autofit_Rel_Sticker.this.listener_obj.onRotateMove(Text_Autofit_Rel_Sticker.this);
                    }
                    Text_Autofit_Rel_Sticker textAutofitRelSticker4 = Text_Autofit_Rel_Sticker.this;
                    textAutofitRelSticker4.a_Double_var = (Math.atan2((double) (textAutofitRelSticker4.center_Y_var - motionEvent.getRawY()), (double) (Text_Autofit_Rel_Sticker.this.center_X_var - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (Text_Autofit_Rel_Sticker.this.a_Double_var + Text_Autofit_Rel_Sticker.this.a_Double1_var));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                }
            } else if (Text_Autofit_Rel_Sticker.this.listener_obj != null) {
                Text_Autofit_Rel_Sticker.this.listener_obj.onRotateUp(Text_Autofit_Rel_Sticker.this);
            }
            return true;
        }
    }

    public class delete_Click_Listener implements OnClickListener {
        delete_Click_Listener() {
        }

        public void onClick(View view) {
            final ViewGroup viewGroup = (ViewGroup) Text_Autofit_Rel_Sticker.this.getParent();
            Text_Autofit_Rel_Sticker.this.scale_ZoomIn_Animation.setAnimationListener(new Animation.AnimationListener() {

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(Text_Autofit_Rel_Sticker.this);
                }
            });
            Text_Autofit_Rel_Sticker.this.text_iv_obj.startAnimation(Text_Autofit_Rel_Sticker.this.scale_ZoomIn_Animation);
            Text_Autofit_Rel_Sticker.this._backgrnd_iv.startAnimation(Text_Autofit_Rel_Sticker.this.scale_ZoomIn_Animation);
            Text_Autofit_Rel_Sticker.this.set_Border_Visibility(false);
            if (Text_Autofit_Rel_Sticker.this.listener_obj != null) {
                Text_Autofit_Rel_Sticker.this.listener_obj.onDelete();
            }
        }
    }

    public class smpl_Gesture_Listener extends GestureDetector.SimpleOnGestureListener {
        smpl_Gesture_Listener() {
        }

        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (Text_Autofit_Rel_Sticker.this.listener_obj == null) {
                return true;
            }
            Text_Autofit_Rel_Sticker.this.listener_obj.onDoubleTap();
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }
    }
}
