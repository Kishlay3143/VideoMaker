package com.status.videomaker.Stickerss;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

public class Text_Auto_Resize_View_Sticker extends AppCompatTextView {
    public static float _min_Text_Size_var;
    private final RectF _available_Space_Rect;
    private final SizeTester _size_Tester_obj;
    public TextPaint _paint_obj;
    public float _spacing_Add_var;
    public float _spacing_Mult_var;
    public int _width_Limit_var;
    private boolean _initialized_bool;
    private int _max_Lines_var;
    private float _max_Text_Size_var;

    public Text_Auto_Resize_View_Sticker(Context context) {
        this(context, null, 16842884);
    }

    public Text_Auto_Resize_View_Sticker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public Text_Auto_Resize_View_Sticker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this._available_Space_Rect = new RectF();
        this._spacing_Mult_var = 1.0f;
        this._spacing_Add_var = 0.0f;
        this._initialized_bool = false;
        _min_Text_Size_var = TypedValue.applyDimension(2, 12.0f, getResources().getDisplayMetrics());
        this._max_Text_Size_var = getTextSize();
        this._paint_obj = new TextPaint(getPaint());
        if (this._max_Lines_var == 0) {
            this._max_Lines_var = -1;
        }
        this._size_Tester_obj = new sTester1();
        this._initialized_bool = true;
    }

    public boolean is_Valid_Word_Wrap_method(char c, char c2) {
        return c == ' ' || c == '-';
    }

    public void setAllCaps(boolean z) {
        super.setAllCaps(z);
        adjust_Text_Size_method();
    }

    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        adjust_Text_Size_method();
    }

    public void setTextSize(float f) {
        this._max_Text_Size_var = f;
        adjust_Text_Size_method();
    }

    public int getMaxLines() {
        return this._max_Lines_var;
    }

    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this._max_Lines_var = i;
        adjust_Text_Size_method();
    }

    public void setSingleLine() {
        super.setSingleLine();
        this._max_Lines_var = 1;
        adjust_Text_Size_method();
    }

    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this._max_Lines_var = 1;
        } else {
            this._max_Lines_var = -1;
        }
        adjust_Text_Size_method();
    }

    public void setLines(int i) {
        super.setLines(i);
        this._max_Lines_var = i;
        adjust_Text_Size_method();
    }

    @Override
    public void setTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        this._max_Text_Size_var = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        adjust_Text_Size_method();
    }

    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this._spacing_Mult_var = f2;
        this._spacing_Add_var = f;
    }

    public void set_Min_Text_Size__method(float f) {
        _min_Text_Size_var = f;
        adjust_Text_Size_method();
    }

    private void adjust_Text_Size_method() {
        if (this._initialized_bool) {
            int i = (int) _min_Text_Size_var;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            this._width_Limit_var = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            if (this._width_Limit_var > 0) {
                this._paint_obj = new TextPaint(getPaint());
                RectF rectF = this._available_Space_Rect;
                rectF.right = (float) this._width_Limit_var;
                rectF.bottom = (float) measuredHeight;
                super_Set_TextSize_method(i);
            }
        }
    }

    private void super_Set_TextSize_method(int i) {
        super.setTextSize(0, (float) binary_Search_method(i, (int) this._max_Text_Size_var, this._size_Tester_obj, this._available_Space_Rect));
    }

    private int binary_Search_method(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            int i5 = (i + i3) >>> 1;
            int onTestSize = sizeTester.onTestSize(i5, rectF);
            if (onTestSize < 0) {
                int i6 = i5 + 1;
                i4 = i;
                i = i6;
            } else if (onTestSize <= 0) {
                return i5;
            } else {
                i4 = i5 - 1;
                i3 = i4;
            }
        }
        return i4;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        adjust_Text_Size_method();
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            adjust_Text_Size_method();
        }
    }

    public interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    class sTester1 implements SizeTester {
        final RectF rectF = new RectF();

        sTester1() {
        }

        @Override
        @TargetApi(16)
        public int onTestSize(int i, RectF rectF2) {
            String str;
            Text_Auto_Resize_View_Sticker.this._paint_obj.setTextSize((float) i);
            TransformationMethod transformationMethod = Text_Auto_Resize_View_Sticker.this.getTransformationMethod();
            if (transformationMethod != null) {
                str = transformationMethod.getTransformation(Text_Auto_Resize_View_Sticker.this.getText(), Text_Auto_Resize_View_Sticker.this).toString();
            } else {
                str = Text_Auto_Resize_View_Sticker.this.getText().toString();
            }
            if (Text_Auto_Resize_View_Sticker.this.getMaxLines() == 1) {
                this.rectF.bottom = Text_Auto_Resize_View_Sticker.this._paint_obj.getFontSpacing();
                this.rectF.right = Text_Auto_Resize_View_Sticker.this._paint_obj.measureText(str);
            } else {
                StaticLayout staticLayout = new StaticLayout(str, Text_Auto_Resize_View_Sticker.this._paint_obj, Text_Auto_Resize_View_Sticker.this._width_Limit_var, Layout.Alignment.ALIGN_NORMAL, Text_Auto_Resize_View_Sticker.this._spacing_Mult_var, Text_Auto_Resize_View_Sticker.this._spacing_Add_var, true);
                if (Text_Auto_Resize_View_Sticker.this.getMaxLines() != -1 && staticLayout.getLineCount() > Text_Auto_Resize_View_Sticker.this.getMaxLines()) {
                    return 1;
                }
                this.rectF.bottom = (float) staticLayout.getHeight();
                int lineCount = staticLayout.getLineCount();
                int i2 = -1;
                for (int i3 = 0; i3 < lineCount; i3++) {
                    int lineEnd = staticLayout.getLineEnd(i3);
                    if (i3 < lineCount - 1 && lineEnd > 0 && !Text_Auto_Resize_View_Sticker.this.is_Valid_Word_Wrap_method(str.charAt(lineEnd - 1), str.charAt(lineEnd))) {
                        return 1;
                    }
                    if (((float) i2) < staticLayout.getLineRight(i3) - staticLayout.getLineLeft(i3)) {
                        i2 = ((int) staticLayout.getLineRight(i3)) - ((int) staticLayout.getLineLeft(i3));
                    }
                }
                this.rectF.right = (float) i2;
            }
            this.rectF.offsetTo(0.0f, 0.0f);
            if (rectF2.contains(this.rectF)) {
                return -1;
            }
            return 1;
        }
    }
}
