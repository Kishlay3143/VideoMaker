package com.status.videomaker.Fragmentss;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess.Video_Maker_Act;
import com.status.videomaker.Adapterss.Text_Man_Adptr;
import com.status.videomaker.Modelss.Sticker_Text_Info_Model;
import com.status.videomaker.R;
import com.status.videomaker.Stickerss.Image_Utils_File_Sticker;
import com.status.videomaker.view.SetColorSeekBar;

public class Video_Text_Frag extends Fragment implements Text_Man_Adptr.onRecyclerViewClicked, View.OnClickListener {
    public static String[] fonts_array;
    SetColorSeekBar colors_seek_obj;
    private Video_Maker_Act activity_obj;
    private int colors_var = -16711936;
    private DisplayMetrics display_Metrics_obj;
    private EditText edit_text_Dialog_ET;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity_obj = (Video_Maker_Act) context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragmentt_textt, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.text_scroll);
        ((ImageView) inflate.findViewById(R.id.bold)).setOnClickListener(this);
        ((ImageView) inflate.findViewById(R.id.underline)).setOnClickListener(this);
        ((ImageView) inflate.findViewById(R.id.shadow)).setOnClickListener(this);
        ((ImageView) inflate.findViewById(R.id.italic)).setOnClickListener(this);
        ((ImageView) inflate.findViewById(R.id.capital)).setOnClickListener(this);
        colors_seek_obj = inflate.findViewById(R.id.text_color);
        colors_seek_obj.setProgress(600);

        this.display_Metrics_obj = getResources().getDisplayMetrics();
        fonts_array = getResources().getStringArray(R.array.FontFamily);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new Text_Man_Adptr(getActivity(), fonts_array, 0, this));

        ((ImageView) inflate.findViewById(R.id.text_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Video_Maker_Act.disable_stickers_Method();
                final Dialog dialog = new Dialog(Video_Text_Frag.this.getActivity());
                dialog.setContentView(R.layout.customm_dialogg_texttt);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Video_Text_Frag.this.edit_text_Dialog_ET = (EditText) dialog.findViewById(R.id.edittext_dialog);
                Video_Text_Frag.this.showw_key_board_method();
                dialog.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Video_Text_Frag.this.closee_keyboard_method();
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.donetext_button).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        colors_seek_obj.setProgress(600);
                        if (Video_Text_Frag.this.edit_text_Dialog_ET.getText().toString().equals("")) {
                            Toast.makeText(Video_Text_Frag.this.getActivity(), "Fields are empty...", 0).show();
                            return;
                        }
                        Video_Text_Frag.this.closee_keyboard_method();
                        dialog.dismiss();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Video_Text_Frag.this.addd_text_sticker_method(Video_Text_Frag.this.edit_text_Dialog_ET.getText().toString().trim(), Video_Text_Frag.this.colors_var);
                    }
                });
                dialog.show();
            }
        });

        colors_seek_obj.setOnColorSeekbarChangeListener(new SetColorSeekBar.OnColorSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onColorChanged(SeekBar seekBar, int i, boolean z) {
                Video_Text_Frag.this.activity_obj.set_S_TextColor_Method(i);
            }
        });
        return inflate;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bold:
                this.activity_obj.setTextBold();
                return;
            case R.id.capital:
                this.activity_obj.setTextCapital();
                return;
            case R.id.italic:
                this.activity_obj.setTextItalic();
                return;
            case R.id.shadow:
                this.activity_obj.setTextShadow();
                return;
            case R.id.underline:
                this.activity_obj.setTextUnderline();
                return;
            default:
                return;
        }
    }

    private void showw_key_board_method() {
        ((InputMethodManager) getActivity().getSystemService("input_method")).toggleSoftInput(2, 1);
    }

    private void closee_keyboard_method() {
        ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.edit_text_Dialog_ET.getWindowToken(), 0);
    }

    private void addd_text_sticker_method(String str, int i) {
        Sticker_Text_Info_Model stickerTextInfoModel = new Sticker_Text_Info_Model();
        stickerTextInfoModel.set_POS_X_(((float) (this.display_Metrics_obj.widthPixels / 2)) - ((float) Image_Utils_File_Sticker.dp_To_Px_method(getActivity(), 100)));
        stickerTextInfoModel.set_POSITION_Y_(((float) (this.display_Metrics_obj.widthPixels / 2)) - ((float) Image_Utils_File_Sticker.dp_To_Px_method(getActivity(), 100)));
        stickerTextInfoModel.set_WIDTH_(Image_Utils_File_Sticker.dp_To_Px_method(getActivity(), 200));
        stickerTextInfoModel.set_HEIGHT_(Image_Utils_File_Sticker.dp_To_Px_method(getActivity(), 100));
        stickerTextInfoModel.set_TXT_(str);
        stickerTextInfoModel.set_FONT_NAME_("galette-med.otf");
        stickerTextInfoModel.set_TXT_COLOR(i);
        stickerTextInfoModel.set_TXT_ALPHA(255);
        this.activity_obj.setSTextSticker(stickerTextInfoModel);
    }

    @Override
    public void onTextClicked(int i) {
        this.activity_obj.setTextFontStyle(i);
    }
}
