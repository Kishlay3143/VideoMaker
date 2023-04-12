package com.status.videomaker.Activitiess;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.status.videomaker.Adapterss.Defaultt_Songg_Adptr;
import com.status.videomaker.Modelss.All_Music_Data_Model;
import com.status.videomaker.R;
import com.status.videomaker.videolib.libffmpeg.FileUtils;

import java.io.File;
import java.io.IOException;

public class Default_Songs_ extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener {
    public static String _posit_;
    public static String _path_SD_Card_;
    public static MediaPlayer _media_player_;
    public MainApplication _application_;
    ListView _list_;
    String[] _song_list_ = {"Sad", "Happy", "Joy", "Love",};
    String[] _main_title_ = {"Sad", "Happy", "Joy", "Love",};
    Dialog _default_dialog_;
    ImageView _cancel_song_;
    ImageView _add_song_;
    ImageView _play_pause_;
    SeekBar _seek_bar_;
    TextView _name_of_music_, _duration_;
    TextView _current_timess_;
    ImageView play_backkk_;
    ImageView playy_forwardd_;
    RelativeLayout add_layout1_;
    int position_Y;
    String current_Fileee_;
    private AudioManager audioManager_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._application_ = MainApplication.getInstance();
        setContentView(R.layout.activityy_defaultt_songg);

        _media_player_ = new MediaPlayer();

        audioManager_ = (AudioManager) getSystemService(AUDIO_SERVICE);
        requestToFocusAudio();

        Defaultt_Songg_Adptr defaulttSonggAdptr = new Defaultt_Songg_Adptr(this, _main_title_);
        _list_ = (ListView) findViewById(R.id.list);
        _list_.setAdapter(defaulttSonggAdptr);

        _list_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _path_SD_Card_ = new File(FileUtils.DirDefaultAudio + "/" + _song_list_[position] + ".mp3").getAbsolutePath();
                open_Dialog_(_path_SD_Card_);
                position_Y = position;
                Log.e("ag--12", "onItemClick: " + position);
            }
        });

        ImageView back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void requestToFocusAudio() {
        if (audioManager_ != null) {
            audioManager_.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    @Override
    public void onAudioFocusChange(int i) {
        Log.e("ag--12", "1 : " + i);
        switch (i) {
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.e("ag--12", "2 : ");
                pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.e("ag--12", "3 : ");
                pause();
                break;
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                Log.e("ag--12", "4 : ");
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                Log.e("ag--12", "5 : ");
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                Log.e("ag--12", "6 : ");
                break;
        }
    }

    public void pause() {
        if (_media_player_.isPlaying()) {
            _media_player_.pause();
            _play_pause_.setBackgroundResource(R.drawable.ic_play);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


        if (_play_pause_ != null) {
            _play_pause_.setBackgroundResource(R.drawable.ic_play);
        }

        if (_media_player_ != null && _media_player_.isPlaying()) {
            _media_player_.pause();
            _play_pause_.setBackgroundResource(R.drawable.ic_play);
        }

    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (_play_pause_ != null) {
            _play_pause_.setBackgroundResource(R.drawable.ic_play);
        }
        if (_media_player_ != null && _media_player_.isPlaying()) {
            _media_player_.pause();

        }
    }

    public void open_Dialog_(String filePath) {
        _default_dialog_ = new Dialog(Default_Songs_.this);
        _default_dialog_.setContentView(R.layout.defaultt_songg_dialogg);
        _default_dialog_.getWindow().setGravity(Gravity.CENTER);
        _default_dialog_.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        _default_dialog_.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        _default_dialog_.setCancelable(true);
        _default_dialog_.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (_media_player_.isPlaying()) {
                    _media_player_.pause();
                }
            }
        });
        _default_dialog_.show();


        createSongMethod(filePath);

        play_backkk_ = _default_dialog_.findViewById(R.id.play_back);
        playy_forwardd_ = _default_dialog_.findViewById(R.id.play_forward);
        _cancel_song_ = _default_dialog_.findViewById(R.id.iv_cancel);
        _add_song_ = _default_dialog_.findViewById(R.id.iv_add);
        _play_pause_ = _default_dialog_.findViewById(R.id.play_pause);
        _name_of_music_ = _default_dialog_.findViewById(R.id.name_of_music);
        _duration_ = _default_dialog_.findViewById(R.id.duration);
        _current_timess_ = _default_dialog_.findViewById(R.id.current_time);
        _seek_bar_ = _default_dialog_.findViewById(R.id.seekbar);

        int mFileDuration = _media_player_.getDuration();
        _seek_bar_.setMax(mFileDuration / 1000);
        Handler mHandler = new Handler();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (_media_player_ != null) {
                    int mCurrentPosition = _media_player_.getCurrentPosition() / 1000;
                    _seek_bar_.setProgress(mCurrentPosition);
                    _posit_ = String.valueOf(mCurrentPosition);
                    _current_timess_.setText("00:" + _posit_);
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        _seek_bar_.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (_media_player_ != null && fromUser) {
                    _media_player_.seekTo(progress * 1000);
                }
            }
        });

        File path = new File(filePath);
        String songName = path.getName();

        _name_of_music_.setText(songName);
        _duration_.setText(getDurationMethod(mFileDuration));

        _media_player_.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                _play_pause_.setBackgroundResource(R.drawable.ic_play);
            }
        });

        _play_pause_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!_media_player_.isPlaying()) {
                    _media_player_.start();
                    requestToFocusAudio();
                    _play_pause_.setBackgroundResource(R.drawable.pause_nn);
                } else {
                    _media_player_.pause();
                    _play_pause_.setBackgroundResource(R.drawable.ic_play);
                }
            }
        });

        play_backkk_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position_Y == 0) {
                    Toast.makeText(_application_, "No More Songs", Toast.LENGTH_SHORT).show();
                } else {
                    position_Y--;
                    String pathSDCard2 = new File(FileUtils.DirDefaultAudio + "/" + _song_list_[position_Y] + ".mp3").getAbsolutePath();

                    if (_media_player_.isPlaying()) {
                        _media_player_.stop();
                    }

                    createSongMethod(pathSDCard2);
                    Log.e("ag--12", "  onClick 662 : " + _song_list_[position_Y]);
                }
            }
        });

        playy_forwardd_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position_Y == 3) {
                    Toast.makeText(_application_, "No More Songs", Toast.LENGTH_SHORT).show();
                } else {
                    position_Y++;
                    Log.e("ag--12", "posY 71 : " + position_Y);
                    String pathSDCard2 = new File(FileUtils.DirDefaultAudio + "/" + _song_list_[position_Y] + ".mp3").getAbsolutePath();

                    Log.e("ag--12", "posY 72 : " + pathSDCard2);
                    if (_media_player_.isPlaying()) {
                        _media_player_.stop();
                    }
                    createSongMethod(pathSDCard2);

                    Log.e("ag--12", "onClick 662 : " + _song_list_[position_Y]);
                    _name_of_music_.setText(_song_list_[position_Y]);
                }
            }
        });

        _cancel_song_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _default_dialog_.dismiss();
                _media_player_.stop();
            }
        });

        _add_song_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _default_dialog_.dismiss();
                _media_player_.stop();
                long length = path.length();
                ContentValues contentValues = new ContentValues();
                contentValues.put("_data", path.getAbsolutePath());
                contentValues.put("title", path.getName());
                contentValues.put("_size", Long.valueOf(length));
                contentValues.put("mime_type", "audio/mpeg");
                contentValues.put("artist", (String) getResources().getText(R.string.artist_name));
                contentValues.put("duration", Integer.valueOf(mFileDuration));
                contentValues.put("is_music", true);
                All_Music_Data_Model selectedMusicData = new All_Music_Data_Model();
                setResult(-1);
                selectedMusicData.trackk_data_str = filePath;
                selectedMusicData.trackk_duration_var = (long) (mFileDuration * 1000);
                MainApplication.getInstance().setMusicData(selectedMusicData);

                Log.e("ag--12", "1 Success 2 : " + selectedMusicData.trackk_data_str);


                finish();
            }
        });
    }

    public void createSongMethod(String str) {
        _media_player_ = new MediaPlayer();
        current_Fileee_ = str;

        try {
            _media_player_.setDataSource(current_Fileee_);
            _media_player_.prepare();
            _media_player_.start();
            requestToFocusAudio();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDurationMethod(long msec) {
        if (msec == 0)
            return "00:00";
        long sec = msec / 1000;
        long min = sec / 60;
        sec = sec % 60;
        String minstr = min + "";
        String secstr = sec + "";
        if (min < 10)
            minstr = "0" + min;
        if (sec < 10)
            secstr = "0" + sec;
        return minstr + ":" + secstr;
    }

}