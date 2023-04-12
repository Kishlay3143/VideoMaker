package com.status.videomaker.Activitiess;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.status.videomaker.R;

import java.io.File;

public class Share_Actvty extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener {

    ImageView backPress_IV;
    ImageView home_Button_IV;
    CardView mainCard_CV;
    TextView textView_TV;
    VideoView videoLoader_VV;
    LinearLayout whatsapp_LL;
    LinearLayout facebook_LL;
    LinearLayout instagram_LL;
    LinearLayout more_LL;
    File shareFile_var;
    RelativeLayout player_Button_RL;
    SeekBar mSeekBar_SB;
    Handler m_Handler, handler_;
    double current_pos_var, total_duration_var;
    String sharePath_str;
    int indexValue_var;
    MainApplication application_var;
    private ImageView Play_View_IV;
    private ImageView pause_view_IV;
    private TextView current_TV;
    private TextView total_TV;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activityy_sharee);

        backPress_IV = findViewById(R.id.backPress);
        home_Button_IV = findViewById(R.id.homeButton);
        mainCard_CV = findViewById(R.id.main_card);
        textView_TV = findViewById(R.id.textView);
        videoLoader_VV = findViewById(R.id.video_loader);
        Play_View_IV = findViewById(R.id.icon_video_play);
        pause_view_IV = findViewById(R.id.icon_video_pause);
        current_TV = findViewById(R.id.textTime2);
        total_TV = findViewById(R.id.textTimeSelection2);
        player_Button_RL = findViewById(R.id.player_button);
        mSeekBar_SB = findViewById(R.id.seekbarter);
        whatsapp_LL = findViewById(R.id.iv_whatsapp);
        facebook_LL = findViewById(R.id.iv_facebook);
        instagram_LL = findViewById(R.id.iv_instagram);
        more_LL = findViewById(R.id.iv_more);
        this.application_var = MainApplication.getInstance();

        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        initializer_Method();
        clickListener();


    }


    private void initializer_Method() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        final String stringExtra = getIntent().getStringExtra("File_Path");
        indexValue_var = getIntent().getIntExtra("value", 0);
        Log.e("ag--12", ": 0000-------" + indexValue_var);
        this.videoLoader_VV.setVideoURI(Uri.parse(stringExtra));
        sharePath_str = stringExtra;
        shareFile_var = new File(stringExtra);

        this.videoLoader_VV.start();
        m_Handler = new Handler();
        handler_ = new Handler();

        if (!videoLoader_VV.isPlaying()) {
            this.pause_view_IV.setVisibility(VISIBLE);
            this.Play_View_IV.setVisibility(GONE);
        } else {
            this.pause_view_IV.setVisibility(VISIBLE);
            this.Play_View_IV.setVisibility(GONE);
        }

        videoLoader_VV.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                set_Video_Progress_Method();
                mp.setScreenOnWhilePlaying(true);
            }
        });

        player_Button_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoLoader_VV.isPlaying()) {
                    pouse();
                } else {
                    play_Method();
                }
            }
        });

        this.videoLoader_VV.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pause_view_IV.setVisibility(GONE);
                Play_View_IV.setVisibility(VISIBLE);
            }
        });

        this.home_Button_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Home_Actvty.home_click_var = true;
                pouse();
                        Intent intent = new Intent(Share_Actvty.this, Home_Actvty.class);
                        startActivity(intent);
                        finishAffinity();

                Video_Maker_Act.video_maker_back_bool = false;
                Swapp_Images_Actvty.boolean_swapp_img_back = false;
                Video_Maker_Act._video_maker_boolean_2 = false;

                File file = new File(Gallery_Act.getUploadVideoPathMethod(Share_Actvty.this), "temp_image");
                File file2 = new File(file + ("/") + "temp_image");
                if (!file2.exists()) {
                    file2.mkdir();
                } else {
                    file2.delete();
                }
            }
        });

        whatsapp_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(Share_Actvty.this, getPackageName() + ".provider", new File(sharePath_str));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.setType("video/mp4");
                intent.putExtra("android.intent.extra.STREAM", uri);
                String shareMessage = getString(R.string.share_app) + "\n\n" + "https://play.google.com/store/apps/details?id=" + getPackageName();
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                try {
                    intent.setPackage("com.whatsapp");
                    startActivity(intent);
                    return;
                } catch (Exception unused3) {
                    Toast.makeText(Share_Actvty.this, "WhatsApp does not installed", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        facebook_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(Share_Actvty.this, getPackageName() + ".provider", new File(sharePath_str));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.setType("video/mp4");
                intent.putExtra("android.intent.extra.STREAM", uri);
                try {
                    intent.setPackage("com.facebook.katana");
                    startActivity(intent);
                    return;
                } catch (Exception unused2) {
                    Toast.makeText(Share_Actvty.this, "Facebook does not installed", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        instagram_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(Share_Actvty.this, getPackageName() + ".provider", new File(sharePath_str));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.setType("video/mp4");
                intent.putExtra("android.intent.extra.STREAM", uri);
                try {
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                    return;
                } catch (Exception unused2) {
                    Toast.makeText(Share_Actvty.this, "Instagram does not installed", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        more_LL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pouse();

                Uri uri = Uri.fromFile(shareFile_var);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = getString(R.string.share_app) + "\n\n" + "https://play.google.com/store/apps/details?id=" + getPackageName();
                share.putExtra(Intent.EXTRA_TEXT, shareMessage);
                share.setType("video/*");

                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share Sound File"));
            }
        });
    }


    public void set_Video_Progress_Method() {
        current_pos_var = videoLoader_VV.getCurrentPosition();
        total_duration_var = videoLoader_VV.getDuration();

        total_TV.setText(time_Conversion_Method((long) total_duration_var));
        current_TV.setText(time_Conversion_Method((long) current_pos_var));
        mSeekBar_SB.setMax((int) total_duration_var);

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos_var = videoLoader_VV.getCurrentPosition();
                    current_TV.setText(time_Conversion_Method((long) current_pos_var));
                    mSeekBar_SB.setProgress((int) current_pos_var);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

        mSeekBar_SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos_var = seekBar.getProgress();
                videoLoader_VV.seekTo((int) current_pos_var);
            }
        });
    }

    public String time_Conversion_Method(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }

    private void clickListener() {
        this.backPress_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        this.Play_View_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_Method();
            }
        });

        this.pause_view_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pouse();
            }
        });
    }

    private void pouse() {
        pause_view_IV.setVisibility(GONE);
        Play_View_IV.setVisibility(VISIBLE);
        videoLoader_VV.pause();
    }

    private void play_Method() {
        Play_View_IV.setVisibility(GONE);
        pause_view_IV.setVisibility(VISIBLE);
        videoLoader_VV.start();
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                pause_view_IV.setVisibility(GONE);
            }
        };
        handler.postDelayed(runnable, 1000);
    }


    @Override
    public void onAudioFocusChange(int i) {
        Log.e("ag--12-", "i-----: " + i);
        switch (i) {
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                pouse();
                Log.e("ag--12", "AUDIOFOCUS_LOSS_TRANSIENT: ");
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.e("ag--12", "AUDIOFOCUS_LOSS: ");
                pouse();
                break;
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                Log.e("ag--12", "AUDIOFOCUS_GAIN_TRANSIENT: ");
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                Log.e("ag--12", "AUDIOFOCUS_REQUEST_FAILED: ");
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                Log.e("ag--12", "AUDIOFOCUS_REQUEST_GRANTED: ");
                break;
        }
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();


    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
