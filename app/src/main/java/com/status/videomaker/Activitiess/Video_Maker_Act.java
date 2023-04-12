package com.status.videomaker.Activitiess;

import static com.status.videomaker.Activitiess.Swapp_Images_Actvty.swapp_img_adapterr;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.status.videomaker.Adapterss.Pager_Adpter;
import com.status.videomaker.Fragmentss.Video_Text_Frag;
import com.status.videomaker.InterFacess.OnProgressReceiver;
import com.status.videomaker.Modelss.All_Music_Data_Model;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.Modelss.Sticker_Text_Info_Model;
import com.status.videomaker.R;
import com.status.videomaker.Servicess.Imagee_Create_Service;
import com.status.videomaker.Servicess.Videoo_Createe_Service;
import com.status.videomaker.Stickerss.Sticker_View_Resizable_Sticker;
import com.status.videomaker.Stickerss.Text_Autofit_Rel_Sticker;
import com.status.videomaker.utilss.THEMES_Video_class;
import com.status.videomaker.utilss.Utils_class;
import com.status.videomaker.videolib.libffmpeg.FileUtils;
import com.status.videomaker.view.StickerImage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Video_Maker_Act extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, OnProgressReceiver, AudioManager.OnAudioFocusChangeListener {
    public static Pager_Adpter pager_Adapter_PA;
    public static Video_Maker_Act video_Maker_act;
    public static int select_Filters_Position;
    public static int select_Frame_Position;
    public static int select_Transition_Position;
    public static MediaPlayer mPlayer_MP;
    public static SeekBar seek_Bar_SB;
    public static TextView end_Time_ET;
    public static int video_duration_VAR = 6;
    public static boolean video_maker_back_bool = false;
    public static boolean _video_maker_boolean_2 = false;
    static RelativeLayout capture_RL;
    public Float[] durationArray = {Float.valueOf(1.5f), Float.valueOf(2.0f), Float.valueOf(2.5f), Float.valueOf(3.0f), Float.valueOf(3.5f), Float.valueOf(4.0f), Float.valueOf(5.0f)};
    public ImageView play_Image_IV;
    public float seconds_VAR = 1.5f;
    public BottomSheetBehavior behavior_BSB;
    public LockRunnable lock_Runnable_VAR = new LockRunnable();
    public Handler handler_VAR = new Handler();
    public View iv_Play_Pause_VAR;
    ArrayList<Folder_Image_Album_Model> last_Data_array = new ArrayList<>();
    LayoutInflater inflater_LI;
    int i = 0;
    int effect_Position;
    TabLayout tab_layout;
    ViewPager view_pager_VP;
    MainApplication application_MA;
    RequestManager glide_RM;
    String door_str;
    TextView start_Time_TV;
    View bottom_Sheet_VIEW;
    View fl_Loader_View;
    boolean is_From_Touch_bool = false;
    boolean is_Save_bool = false;
    LinearLayout ib_Frames_LL;
    LinearLayout edit_Lay_LL;
    ImageView iv_Effect_IV;
    ImageView iv_Frame_IV;
    int frame_Position;
    StickerImage sticker_SI;
    int positions_var = 0;
    int text_Style_position = 0;
    View play_Video_view;
    RelativeLayout save_Video_RL;
    ImageView back_Button_IV;
    Dialog dialog_instance;
    private ArrayList<Folder_Image_Album_Model> arrayList_AL;
    private AudioManager am_object;

    public static void disable_stickers_Method() {
        int childCount = capture_RL.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = capture_RL.getChildAt(i);
            if (childAt instanceof Sticker_View_Resizable_Sticker) {
                ((Sticker_View_Resizable_Sticker) childAt).set_Border_Visibility_method(false);
            }

            if (childAt instanceof Text_Autofit_Rel_Sticker) {
                ((Text_Autofit_Rel_Sticker) childAt).set_Border_Visibility(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_videoo_makerr);

        Log.e("ag--12", "onCreate vm : ");
        Log.e("ag--12", "onCreate vm");

        am_object = (AudioManager) getSystemService(AUDIO_SERVICE);
        request_Audio_Focus_Method();

        Video_Maker_Act.select_Transition_Position = 0;
        Video_Maker_Act.select_Frame_Position = 0;
        Video_Maker_Act.select_Filters_Position = 0;

        application_MA = MainApplication.getInstance();
        application_MA.videoImages.clear();
        glide_RM = Glide.with((FragmentActivity) this);
        MainApplication.isBreak = false;
        Intent intent = new Intent(getApplicationContext(), Imagee_Create_Service.class);
        intent.putExtra(Imagee_Create_Service.EXTRAAA_SELECTED_THEME_STR, this.application_MA.getCurrentTheme());
        startService(intent);

        video_Maker_act = this;
        getWindow().addFlags(128);
        door_str = getIntent().getStringExtra("images");

        if (door_str != null) {
            application_MA.selectedTheme = THEMES_Video_class.Shine;
        }

        int_IDs_Method();
        activity_Perform_Method();



    }

    private void int_IDs_Method() {
        play_Image_IV = (ImageView) findViewById(R.id.previewImageView1);
        seek_Bar_SB = (SeekBar) findViewById(R.id.sbPlayTime);
        end_Time_ET = (TextView) findViewById(R.id.tvEndTime);
        start_Time_TV = (TextView) findViewById(R.id.tvTime);
        fl_Loader_View = (View) findViewById(R.id.flLoader);
        iv_Play_Pause_VAR = findViewById(R.id.ivPlayPause);
        ib_Frames_LL = (LinearLayout) findViewById(R.id.ibFrames);
        edit_Lay_LL = (LinearLayout) findViewById(R.id.lay);
        capture_RL = (RelativeLayout) findViewById(R.id.captureRelativeLayout);
        view_pager_VP = (ViewPager) findViewById(R.id.viewPager);
        tab_layout = (TabLayout) findViewById(R.id.tabLayout);
        iv_Effect_IV = (ImageView) findViewById(R.id.ivEffect);
        iv_Frame_IV = (ImageView) findViewById(R.id.ivFrame);
        play_Video_view = (View) findViewById(R.id.video_clicker);
        save_Video_RL = (RelativeLayout) findViewById(R.id.save_video);
        back_Button_IV = (ImageView) findViewById(R.id.back_Btn);
    }

    @Override
    public void onAudioFocusChange(int i) {
        Log.e("ag--12", "1 : " + i);
        switch (i) {
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.e("ag--12", "2 : ");
                pauseMusic();
                lock_Runnable_VAR.pause();
                Video_Maker_Act.this.iv_Play_Pause_VAR.setVisibility(View.VISIBLE);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.e("ag--12", "3 : ");
                pauseMusic();
                lock_Runnable_VAR.pause();
                Video_Maker_Act.this.iv_Play_Pause_VAR.setVisibility(View.VISIBLE);
                break;
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                Log.e("ag--12", "4 : ");
                Video_Maker_Act.this.iv_Play_Pause_VAR.setVisibility(View.INVISIBLE);
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                Log.e("ag--12", "5 : ");
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                Log.e("ag--12", "6 : ");
                Video_Maker_Act.this.iv_Play_Pause_VAR.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void request_Audio_Focus_Method() {
        if (am_object != null) {
            am_object.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    private void activity_Perform_Method() {
        ArrayList<Folder_Image_Album_Model> selectedImages = application_MA.getSelectedImages();
        seconds_VAR = application_MA.getSecond();
        arrayList_AL = selectedImages;

        seek_Bar_SB.setMax((selectedImages.size() - 1) * 30);

        int size = (int) (((float) (this.arrayList_AL.size() - 1)) * this.seconds_VAR);
        this.end_Time_ET.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(size / 60), Integer.valueOf(size % 60)}));

        if (this.application_MA.getSelectedImages().size() > 0) {
            this.glide_RM.load(this.application_MA.getSelectedImages().get(0)._image_Path_).into(this.play_Image_IV);
        }

        View findViewById = findViewById(R.id.bottom_sheet);
        bottom_Sheet_VIEW = findViewById;
        BottomSheetBehavior from = BottomSheetBehavior.from(findViewById);
        this.behavior_BSB = from;
        from.setState(BottomSheetBehavior.STATE_COLLAPSED);
        this.behavior_BSB.setState(BottomSheetBehavior.STATE_EXPANDED);
        this.behavior_BSB.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            public void onSlide(View view, float f) {
            }

            public void onStateChanged(View view, int i) {
                if (i == 4 && !Video_Maker_Act.this.lock_Runnable_VAR.isPause()) {
                    Video_Maker_Act.this.lock_Runnable_VAR.play();
                    Video_Maker_Act.this.behavior_BSB.setPeekHeight(0);
                }
            }
        });

        setTheme();
        this.lock_Runnable_VAR.play();
        click_Event();
        set_Tab_View_Method();

        save_Video_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disable_stickers_Method();
                Hide_All_Border_Method();
                if (is_Save_bool) {
                    Log.e("rrr--10", "onClick:...saveVideo.... " );
                    lock_Runnable_VAR.stop();
                    capture_RL.setDrawingCacheEnabled(true);
                    capture_RL.buildDrawingCache(true);
                    MainApplication.frame_sticker_pic = Bitmap.createScaledBitmap(capture_RL.getDrawingCache(), MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT, true);
                    handler_VAR.removeCallbacks(lock_Runnable_VAR);
                    new CreateVideos().execute(new Void[0]);
                    is_Save_bool = false;
                } else {

                    Toast.makeText(Video_Maker_Act.this, "Video Creation is in Progress", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_Button_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void click_Event() {
        this.seek_Bar_SB.setOnSeekBarChangeListener(this);
        this.play_Video_view.setOnClickListener(this);
        findViewById(R.id.ibFrames).setOnClickListener(this);
    }

    @Override
    public void onImageProgressFrameUpdate(float f) {
    }

    @Override
    public void onProgressFinish(String str) {
    }

    @Override
    public void onVideoProgressFrameUpdate(float f) {
    }

    public void set_Tab_View_Method() {
        pager_Adapter_PA = new Pager_Adpter(getSupportFragmentManager(), this);
        this.view_pager_VP.setAdapter(pager_Adapter_PA);
        pager_Adapter_PA.getPageTitle(0);

        this.tab_layout.setupWithViewPager(this.view_pager_VP);
        this.tab_layout.getTabAt(0).setIcon(R.drawable.ic_transition);
        this.tab_layout.getTabAt(0).setText("Transition");
        this.tab_layout.getTabAt(1).setIcon(R.drawable.ic_music_us);
        this.tab_layout.getTabAt(1).setText("Music");
        this.tab_layout.getTabAt(2).setIcon(R.drawable.ic_filter_unselected);
        this.tab_layout.getTabAt(2).setText("Filter");
        this.tab_layout.getTabAt(3).setIcon(R.drawable.ic_frame);
        this.tab_layout.getTabAt(3).setText("Frame");
        this.tab_layout.getTabAt(4).setIcon(R.drawable.ic_text_unselected);
        this.tab_layout.getTabAt(4).setText("Text");
        this.tab_layout.getTabAt(5).setIcon(R.drawable.ic_duration);
        this.tab_layout.getTabAt(5).setText("Duration");
        this.tab_layout.getTabAt(6).setIcon(R.drawable.ic_photo_add);
        this.tab_layout.getTabAt(6).setText("Image");

        this.tab_layout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Video_Maker_Act.this.tab_layout.getTabAt(0).setIcon(R.drawable.ic_transition);
                        return;
                    case 1:
                        Video_Maker_Act.this.tab_layout.getTabAt(1).setIcon(R.drawable.ic_music_selected);
                        return;
                    case 2:
                        Video_Maker_Act.this.tab_layout.getTabAt(2).setIcon(R.drawable.ic_filter_selected);
                        return;
                    case 3:
                        Video_Maker_Act.this.tab_layout.getTabAt(3).setIcon(R.drawable.ic_frame_selected);
                        return;
                    case 4:
                        Video_Maker_Act.this.tab_layout.getTabAt(4).setIcon(R.drawable.ic_text_selected);
                        return;
                    case 5:
                        Video_Maker_Act.this.tab_layout.getTabAt(5).setIcon(R.drawable.ic_duration_selected);
                        return;
                    case 6:
                        Video_Maker_Act.this.tab_layout.getTabAt(6).setIcon(R.drawable.ic_photo_selected);
                        return;
                    default:
                        return;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        Video_Maker_Act.this.tab_layout.getTabAt(0).setIcon(R.drawable.transition_us);
                        return;
                    case 1:
                        Video_Maker_Act.this.tab_layout.getTabAt(1).setIcon(R.drawable.ic_music_us);
                        return;
                    case 2:
                        Video_Maker_Act.this.tab_layout.getTabAt(2).setIcon(R.drawable.ic_filter_unselected);
                        return;
                    case 3:
                        Video_Maker_Act.this.tab_layout.getTabAt(3).setIcon(R.drawable.ic_frame);
                        return;
                    case 4:
                        Video_Maker_Act.this.tab_layout.getTabAt(4).setIcon(R.drawable.ic_text_unselected);
                        return;
                    case 5:
                        Video_Maker_Act.this.tab_layout.getTabAt(5).setIcon(R.drawable.ic_duration);
                        return;
                    case 6:
                        Video_Maker_Act.this.tab_layout.getTabAt(6).setIcon(R.drawable.ic_photo_add);
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void Hide_All_Border_Method() {
        Iterator<StickerImage> it = Utils_class.Stickers_list.iterator();
        while (it.hasNext()) {
            it.next().hideButtonAndBorder();
        }
    }

    public synchronized void display_Image_Method() {
        if (this.i >= this.seek_Bar_SB.getMax()) {
            this.i = 0;
            this.lock_Runnable_VAR.stop();
        } else {
            if (this.i > 0 && this.fl_Loader_View.getVisibility() == View.VISIBLE) {
                this.fl_Loader_View.setVisibility(View.GONE);
                MediaPlayer mediaPlayer = this.mPlayer_MP;
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    this.mPlayer_MP.start();
                    request_Audio_Focus_Method();
                }
            }

            this.seek_Bar_SB.setSecondaryProgress(this.application_MA.videoImages.size());

            if (this.seek_Bar_SB.getProgress() < this.seek_Bar_SB.getSecondaryProgress()) {
                this.i %= this.application_MA.videoImages.size();
                ((Glide.with((FragmentActivity) this).asBitmap().load(this.application_MA.videoImages.get(this.i)).signature(new MediaStoreSignature("image/*", System.currentTimeMillis(), 0))).diskCacheStrategy(DiskCacheStrategy.DATA)).into(new SimpleTarget<Bitmap>() {
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        Video_Maker_Act.this.play_Image_IV.setImageBitmap(bitmap);
                    }
                });
                int i = this.i + 1;
                this.i = i;
                if (!this.is_From_Touch_bool) {
                    this.seek_Bar_SB.setProgress(i);
                }
                int i2 = (int) ((((float) this.i) / 30.0f) * this.seconds_VAR);
                this.start_Time_TV.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)}));
                int size = (int) (((float) (this.arrayList_AL.size() - 1)) * this.seconds_VAR);
                this.end_Time_ET.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(size / 60), Integer.valueOf(size % 60)}));

                video_duration_VAR = size;
            }
        }
        return;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.video_clicker) {
            if (this.lock_Runnable_VAR.isPause()) {
                this.lock_Runnable_VAR.play();
            } else {
                this.lock_Runnable_VAR.pause();
            }
            disable_stickers_Method();
            Hide_All_Border_Method();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        this.i = i;
        if (this.is_From_Touch_bool) {
            seekBar.setProgress(Math.min(i, seekBar.getSecondaryProgress()));
            display_Image_Method();
            seek_Media_Player_Method();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.is_From_Touch_bool = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.is_From_Touch_bool = false;
    }

    private void seek_Media_Player_Method() {
        MediaPlayer mediaPlayer = this.mPlayer_MP;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.seekTo(((int) (((((float) this.i) / 30.0f) * this.seconds_VAR) * 1000.0f)) % mediaPlayer.getDuration());
            } catch (ArithmeticException | IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset_Method() {
        MainApplication.isBreak = false;
        this.application_MA.videoImages.clear();
        this.handler_VAR.removeCallbacks(this.lock_Runnable_VAR);
        this.lock_Runnable_VAR.stop();
        Glide.get(this).clearMemory();
        new Thread() {
            public void run() {
                Glide.get(Video_Maker_Act.this).clearDiskCache();
            }
        }.start();
        FileUtils.deleteTempDir();
        this.glide_RM = Glide.with((FragmentActivity) this);
        this.fl_Loader_View.setVisibility(View.VISIBLE);
        setTheme();
    }

    public void start_Activity_For_Music_Method() {
        pauseMusic();
        Intent intent11 = new Intent(Video_Maker_Act.this, Offline_Song_Act.class);
        startActivityForResult(intent11, 101);
    }

    public void start_Activity_For_Default_Music_Method() {
        pauseMusic();
        Intent intent = new Intent(Video_Maker_Act.this, Default_Songs_.class);
        startActivityForResult(intent, 101);
    }

    public void start_Image_Editing_Method() {
        Intent intent = new Intent(Video_Maker_Act.this, Swapp_Images_Actvty.class);
        intent.putExtra(Swapp_Images_Actvty.EXTRA_FROM_PREVIEW_str, true);
        startActivityForResult(intent, 103);
        video_maker_back_bool = true;
    }

    public int get_Selected_Effect_Position() {
        return this.application_MA.getEffectPos();
    }

    public void set_Effect_View_Method(int i) {
        if (i == -1) {
            this.iv_Effect_IV.setImageDrawable(null);
        } else {
            Glide.with((FragmentActivity) this).load(Integer.valueOf(i)).into(this.iv_Effect_IV);
        }
        this.effect_Position = i;
        this.application_MA.setEffectPos(i);
    }

    public int get_Frame_Method() {
        return this.application_MA.getFrame();
    }

    public void set_Frame_View_Method(int i) {
        if (i == -1) {
            this.iv_Frame_IV.setImageDrawable(null);
        } else {
            Glide.with((FragmentActivity) this).load(Integer.valueOf(i)).into(this.iv_Frame_IV);
        }
        this.frame_Position = i;
        this.application_MA.setFrame(i);
    }

    public void set_Sticker_Method(int i, String[] strArr) {
        this.sticker_SI = new StickerImage(getApplicationContext());
        try {
            if (strArr[i] != null && strArr[i].length() > 0) {
                AssetManager assets = getAssets();
                this.sticker_SI.setImageDrawable(Drawable.createFromStream(assets.open("stickers1/" + strArr[i]), null));
                this.sticker_SI.setOwnerId(strArr[i]);
                this.positions_var = this.positions_var + 1;
                this.sticker_SI.setId(this.positions_var);
                Utils_class.Stickers_list.add(this.sticker_SI);
                Utils_class.seekbar_progress_AL.add(255);
                capture_RL.addView(this.sticker_SI);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set_S_TextColor_Method(int i) {
        int childCount = capture_RL.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = capture_RL.getChildAt(i2);
            if (childAt instanceof Text_Autofit_Rel_Sticker) {
                Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) childAt;
                if (textAutofitRelSticker.get_Border_Visibility()) {
                    textAutofitRelSticker.set_Text_Color(i);
                    childAt.invalidate();
                }
            }
        }
    }

    public void setSTextSticker(Sticker_Text_Info_Model stickerTextInfoModel) {
        disable_stickers_Method();
        Text_Autofit_Rel_Sticker textAutofitRelSticker = new Text_Autofit_Rel_Sticker(this);
        capture_RL.addView(textAutofitRelSticker);
        textAutofitRelSticker.setTextInfo_method(stickerTextInfoModel, false);
        if (Build.VERSION.SDK_INT >= 17) {
            textAutofitRelSticker.setId(View.generateViewId());
        }
        textAutofitRelSticker.set_Border_Visibility(true);
    }

    public void setTextFontStyle(int i) {
        int childCount = capture_RL.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = capture_RL.getChildAt(i2);
            if (childAt instanceof Text_Autofit_Rel_Sticker) {
                Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) childAt;
                if (textAutofitRelSticker.get_Border_Visibility()) {
                    this.text_Style_position = i;
                    textAutofitRelSticker.set_Text_Font(Video_Text_Frag.fonts_array[i].substring(Video_Text_Frag.fonts_array[i].lastIndexOf("/") + 1));
                    childAt.invalidate();
                }
            }
        }
    }

    public void setTextUnderline() {
        int childCount = capture_RL.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = capture_RL.getChildAt(i);
            if (childAt instanceof Text_Autofit_Rel_Sticker) {
                Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) childAt;
                if (textAutofitRelSticker.get_Border_Visibility()) {
                    textAutofitRelSticker.set_UnderLine_Font();
                    childAt.invalidate();
                }
            }
        }
    }

    public void setTextBold() {
        int childCount = capture_RL.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = capture_RL.getChildAt(i);
            if (childAt instanceof Text_Autofit_Rel_Sticker) {
                Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) childAt;
                if (textAutofitRelSticker.get_Border_Visibility()) {
                    textAutofitRelSticker.set_Text_Font(Video_Text_Frag.fonts_array[this.text_Style_position].substring(Video_Text_Frag.fonts_array[this.text_Style_position].lastIndexOf("/") + 1));
                    textAutofitRelSticker.set_Bold_Font();
                    childAt.invalidate();
                }
            }
        }
    }

    public void setTextShadow() {
        int childCount = capture_RL.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = capture_RL.getChildAt(i);
            if (childAt instanceof Text_Autofit_Rel_Sticker) {
                Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) childAt;
                if (textAutofitRelSticker.get_Border_Visibility()) {
                    textAutofitRelSticker.set_Text_Shadow_Color(textAutofitRelSticker.get_Text_Color());
                    childAt.invalidate();
                }
            }
        }
    }

    public void setTextItalic() {
        int childCount = capture_RL.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = capture_RL.getChildAt(i);
            if (childAt instanceof Text_Autofit_Rel_Sticker) {
                Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) childAt;
                if (textAutofitRelSticker.get_Border_Visibility()) {
                    textAutofitRelSticker.set_Italic_Font();
                    childAt.invalidate();
                }
            }
        }
    }

    public void setTextCapital() {
        int childCount = capture_RL.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = capture_RL.getChildAt(i);
            if (childAt instanceof Text_Autofit_Rel_Sticker) {
                Text_Autofit_Rel_Sticker textAutofitRelSticker = (Text_Autofit_Rel_Sticker) childAt;
                if (textAutofitRelSticker.get_Border_Visibility()) {
                    textAutofitRelSticker.set_Capital_Font();
                    childAt.invalidate();
                }
            }
        }
    }

    public void changeVideoDuration() {
        this.application_MA.setSecond(this.seconds_VAR);
        seek_Media_Player_Method();
    }

    public void reinitMusic() {
        All_Music_Data_Model allMusicDataModel = this.application_MA.getMusicData();
        if (allMusicDataModel != null && allMusicDataModel.trackk_data_str != null) {
            MediaPlayer create = MediaPlayer.create(this, Uri.parse(allMusicDataModel.trackk_data_str));
            this.mPlayer_MP = create;
            try {
                create.setLooping(true);
                this.mPlayer_MP.prepareAsync();
                request_Audio_Focus_Method();
            } catch (IllegalStateException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void playMusic() {
        MediaPlayer mediaPlayer;
        if (this.fl_Loader_View.getVisibility() != View.VISIBLE && (mediaPlayer = this.mPlayer_MP) != null && !mediaPlayer.isPlaying()) {
            this.mPlayer_MP.start();
            request_Audio_Focus_Method();
        }
    }

    public void pauseMusic() {
        MediaPlayer mediaPlayer = this.mPlayer_MP;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mPlayer_MP.pause();
        }
        iv_Play_Pause_VAR.setVisibility(View.VISIBLE);
    }

    public void setTheme() {


        is_Save_bool = false;


        if (this.application_MA.isFromSdCardAudio) {
            this.lock_Runnable_VAR.play();
            reinitMusic();
        } else {
            new Thread() {
                public void run() {
                    THEMES_Video_class themesVideo = Video_Maker_Act.this.application_MA.selectedTheme;
                    try {
                        FileUtils.TEMP_DIRECTORY_AUDIO.mkdirs();
                        File file = new File(FileUtils.TEMP_DIRECTORY_AUDIO, "temp.mp3");
                        if (file.exists()) {
                            FileUtils.deleteFile(file);
                        }
                        InputStream openRawResource = Video_Maker_Act.this.getResources().openRawResource(themesVideo.getThemeMusic());
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = openRawResource.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(file.getAbsolutePath());
                        mediaPlayer.setAudioStreamType(3);
                        mediaPlayer.prepareAsync();
                        final All_Music_Data_Model allMusicDataModel = new All_Music_Data_Model();
                        allMusicDataModel.trackk_data_str = file.getAbsolutePath();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                allMusicDataModel.trackk_duration_var = (long) mediaPlayer.getDuration();
                                mediaPlayer.stop();
                            }
                        });
                        allMusicDataModel.trackk_Title_str = "temp";
                        Video_Maker_Act.this.application_MA.setMusicData(allMusicDataModel);
                    } catch (Exception unused) {
                    }
                    Video_Maker_Act.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Video_Maker_Act.this.reinitMusic();
                            Video_Maker_Act.this.lock_Runnable_VAR.play();
                        }
                    });
                }
            }.start();
        }
    }

    public void deleteThemeDir(final String str) {
        new Thread() {
            public void run() {
                FileUtils.deleteThemeDir(str);
            }
        }.start();
    }

    public void onActivityResult(int i, int i2, Intent intent) {

        Log.e("ag--12 ", "onActivityResult vm : ");

        super.onActivityResult(i, i2, intent);
        this.application_MA.isEditModeEnable = false;
        if (i2 == -1) {
            switch (i) {
                case 101:
                    Log.e("ag--12 ", "onActivityResult 101 vm : ");
                    this.application_MA.isFromSdCardAudio = true;
                    this.i = 0;
                    reinitMusic();
                    this.lock_Runnable_VAR.stop();
                    return;

                case 102:
                    Log.e("ag--12 ", "onActivityResult 102 vm : ");

                    if (isNeedRestart()) {
                        stopService(new Intent(getApplicationContext(), Imagee_Create_Service.class));
                        this.lock_Runnable_VAR.stop();
                        this.seek_Bar_SB.postDelayed(new Runnable() {
                            public void run() {
                                MainApplication.isBreak = false;
                                Video_Maker_Act.this.application_MA.videoImages.clear();
                                Video_Maker_Act.this.application_MA.min_pos = Integer.MAX_VALUE;
                                Intent intent = new Intent(Video_Maker_Act.this.getApplicationContext(), Imagee_Create_Service.class);
                                intent.putExtra("selected_theme", Video_Maker_Act.this.application_MA.getCurrentTheme());
                                Video_Maker_Act.this.startService(intent);
                            }
                        }, 1000);
                        int size = (int) (((float) (this.arrayList_AL.size() - 1)) * this.seconds_VAR);
                        this.arrayList_AL = this.application_MA.getSelectedImages();
                        this.seek_Bar_SB.setMax((this.application_MA.getSelectedImages().size() - 1) * 30);
                        this.end_Time_ET.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(size / 60), Integer.valueOf(size % 60)}));
                        return;
                    }
                    if (Imagee_Create_Service.is_Image_Complate_bool) {
                        MainApplication.isBreak = false;
                        this.application_MA.videoImages.clear();
                        this.application_MA.min_pos = Integer.MAX_VALUE;
                        Intent intent2 = new Intent(getApplicationContext(), Imagee_Create_Service.class);
                        intent2.putExtra("selected_theme", this.application_MA.getCurrentTheme());
                        startService(intent2);
                        this.i = 0;
                        this.seek_Bar_SB.setProgress(0);
                    }
                    int size2 = (int) (((float) (this.arrayList_AL.size() - 1)) * this.seconds_VAR);
                    this.arrayList_AL = this.application_MA.getSelectedImages();
                    this.seek_Bar_SB.setMax((this.application_MA.getSelectedImages().size() - 1) * 30);
                    this.end_Time_ET.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(size2 / 60), Integer.valueOf(size2 % 60)}));
                    return;

                case 103:
                    Log.e("ag--12 ", "onActivityResult 103 vm : ");

                    this.lock_Runnable_VAR.stop();
                    is_Save_bool = false;
                    if (Imagee_Create_Service.is_Image_Complate_bool || !MainApplication.isMyServiceRunning(this.application_MA, Imagee_Create_Service.class)) {
                        MainApplication.isBreak = false;
                        this.application_MA.videoImages.clear();
                        this.application_MA.min_pos = Integer.MAX_VALUE;
                        Intent intent3 = new Intent(getApplicationContext(), Imagee_Create_Service.class);
                        intent3.putExtra("selected_theme", this.application_MA.getCurrentTheme());
                        startService(intent3);
                    }
                    this.i = 0;
                    this.seek_Bar_SB.setProgress(this.i);
                    this.arrayList_AL = this.application_MA.getSelectedImages();
                    int size3 = (int) (((float) (this.arrayList_AL.size() - 1)) * this.seconds_VAR);
                    this.seek_Bar_SB.setMax((this.application_MA.getSelectedImages().size() - 1) * 30);
                    this.end_Time_ET.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(size3 / 60), Integer.valueOf(size3 % 60)}));
                    return;
                default:
                    return;
            }
        }
    }

    private boolean isNeedRestart() {
        if (this.last_Data_array.size() > this.application_MA.getSelectedImages().size()) {
            MainApplication.isBreak = true;
            Log.e("ag--12", "isNeedRestart size");
            return true;
        }
        int i2 = 0;
        while (i2 < this.last_Data_array.size()) {
            Log.e("ag--12", String.valueOf(this.last_Data_array.get(i2)._image_Path_) + "___ " + this.application_MA.getSelectedImages().get(i2)._image_Path_);
            if (this.last_Data_array.get(i2)._image_Path_.equals(this.application_MA.getSelectedImages().get(i2)._image_Path_)) {
                i2++;
            } else {
                MainApplication.isBreak = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        this.lock_Runnable_VAR.stop();
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setMessage(R.string.exit_in_main_menu);
        builder2.setPositiveButton(R.string.menu_exit1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        swapp_img_adapterr.notifyDataSetChanged();
                        Video_Maker_Act.video_Maker_act.application_MA.isFromSdCardAudio = false;

                        Intent intent = new Intent(Video_Maker_Act.this, Swapp_Images_Actvty.class);
                        startActivity(intent);
                        Video_Maker_Act.this.finish();
            }
        });
        builder2.setNegativeButton(R.string.menu_cancel1, (DialogInterface.OnClickListener) null);
        builder2.show();

    }

    @Override
    protected void onResume() {
        Log.e("ag--12", "onResume : ");
        Log.e("ag--12 ", "onResume vm : ");
        Log.e("ag--12", "onResume vm");

        super.onResume();


    }

    @Override
    protected void onPause() {
        Log.e("ag--12", "onPause: ");
        super.onPause();

        pauseMusic();
        this.lock_Runnable_VAR.stop();

    }



    public class CreateVideos extends AsyncTask<Void, String, Void> {
        public CreateVideos() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public Void doInBackground(Void... voidArr) {
            return null;
        }

        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            startService(new Intent(Video_Maker_Act.this, Videoo_Createe_Service.class));
            Intent intent = new Intent(Video_Maker_Act.this.application_MA, Videoo_Processingg_Act.class);
            startActivity(intent);
            finish();
        }
    }

    class LockRunnable implements Runnable {
        ArrayList<Folder_Image_Album_Model> appList = new ArrayList<>();
        boolean isPause = false;

        LockRunnable() {
        }


        public void run() {
            Video_Maker_Act.this.display_Image_Method();
            if (!this.isPause) {
                Video_Maker_Act.this.handler_VAR.postDelayed(Video_Maker_Act.this.lock_Runnable_VAR, (long) Math.round(Video_Maker_Act.this.seconds_VAR * 50.0f));
            }
        }

        public boolean isPause() {
            return this.isPause;
        }

        public void play() {
            this.isPause = false;
            Video_Maker_Act.this.playMusic();
            Video_Maker_Act.this.handler_VAR.postDelayed(Video_Maker_Act.this.lock_Runnable_VAR, (long) Math.round(Video_Maker_Act.this.seconds_VAR * 50.0f));
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    Video_Maker_Act.this.iv_Play_Pause_VAR.setVisibility(View.VISIBLE);
                }

                public void onAnimationEnd(Animation animation) {
                    Video_Maker_Act.this.iv_Play_Pause_VAR.setVisibility(View.INVISIBLE);
                }
            });
            Video_Maker_Act.this.iv_Play_Pause_VAR.startAnimation(alphaAnimation);
            if (Video_Maker_Act.this.behavior_BSB.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                Video_Maker_Act.this.behavior_BSB.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        public void pause() {
            this.isPause = true;
            Video_Maker_Act.this.pauseMusic();
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(500);
            alphaAnimation.setFillAfter(true);
            Video_Maker_Act.this.iv_Play_Pause_VAR.startAnimation(alphaAnimation);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    Video_Maker_Act.this.iv_Play_Pause_VAR.setVisibility(View.VISIBLE);
                }
            });
        }

        public void stop() {
            pause();
            Video_Maker_Act.this.i = 0;
            if (Video_Maker_Act.this.mPlayer_MP != null) {
                is_Save_bool = true;
                Video_Maker_Act.this.mPlayer_MP.stop();
            }
            Video_Maker_Act.this.reinitMusic();
            Video_Maker_Act.this.seek_Bar_SB.setProgress(Video_Maker_Act.this.i);
        }
    }

    private class DurationAdapter extends RecyclerView.Adapter<DurationAdapter.ViewHolder> {
        public DurationAdapter() {
        }

        DurationAdapter(Video_Maker_Act videoMakeActivity, Video_Maker_Act videoMakeActivity2) {
            this();
        }

        public int getItemCount() {
            return Video_Maker_Act.this.durationArray.length;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            final float floatValue = Video_Maker_Act.this.durationArray[i].floatValue();
            boolean z = true;
            viewHolder.checkedTextView.setText(String.format("%.1f Second", new Object[]{Float.valueOf(floatValue)}));
            CheckedTextView checkedTextView = viewHolder.checkedTextView;
            if (floatValue != Video_Maker_Act.this.seconds_VAR) {
                z = false;
            }
            checkedTextView.setChecked(z);
            viewHolder.checkedTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    float unused = Video_Maker_Act.this.seconds_VAR = floatValue;
                    Video_Maker_Act.this.application_MA.setSecond(Video_Maker_Act.this.seconds_VAR);
                    DurationAdapter.this.notifyDataSetChanged();
                    Video_Maker_Act.this.lock_Runnable_VAR.play();
                }
            });
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(Video_Maker_Act.this.inflater_LI.inflate(R.layout.durationn_listt_itemm, viewGroup, false));
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CheckedTextView checkedTextView;

            @SuppressLint("ResourceType")
            public ViewHolder(View view) {
                super(view);
                this.checkedTextView = (CheckedTextView) view.findViewById(16908308);
            }
        }
    }
}