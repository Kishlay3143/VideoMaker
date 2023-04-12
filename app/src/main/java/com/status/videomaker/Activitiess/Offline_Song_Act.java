package com.status.videomaker.Activitiess;

import static com.status.videomaker.Activitiess.Gallery_Act.path_dir_var;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Modelss.All_Music_Data_Model;
import com.status.videomaker.R;
import com.status.videomaker.videolib.libffmpeg.FileUtils;
import com.airbnb.lottie.LottieAnimationView;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Offline_Song_Act extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener {
    public static ArrayList<All_Music_Data_Model> m_all_music_data;
    public static All_Music_Data_Model selected_all_music_data;
    public static long start_from_sec, end_at_sec, prevStartSec = -1;
    public static boolean pause_cheker_ = false;
    public static MusicAdapter music_adapter_var;
    public static boolean bool_player_var = false;
    public static SearchView search_View_var;
    public static EditText search_edit_text_var;
    public boolean song_play_var = false;
    public MediaPlayer player_MP = null;
    String m_Filename_STR;
    Offline_Song_Act activity_var;
    File path_var;
    int m_file_duration;
    String name_str;
    private RecyclerView mMusicList_recview;
    private String currentFile_str = "";
    private AudioManager am_var;
    private MediaPlayer.OnErrorListener onError = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_offlinee_songg);


        Log.e("ag--12", "onCreate: off : ");

        am_var = (AudioManager) getSystemService(AUDIO_SERVICE);
        requestAudioFocusMethod();

        this.mMusicList_recview = findViewById(R.id.rvMusicList);
        activity_var = this;
        new LoadMusics().execute(new Void[0]);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        player_MP = new MediaPlayer();

        search_View_var = (androidx.appcompat.widget.SearchView) findViewById(R.id.searchView);
        search_edit_text_var = (EditText) search_View_var.findViewById(R.id.search_src_text);
        search_edit_text_var.setTextColor(getResources().getColor(R.color.black));
        search_edit_text_var.setHintTextColor(getResources().getColor(R.color.text2));
        ImageView closeButton = (ImageView) search_View_var.findViewById(R.id.search_close_btn);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_View_var.clearFocus();
                search_View_var.setQuery("", false);
                hideKeyboardMethod();
            }
        });

        search_View_var.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_View_var.onActionViewExpanded();
            }
        });

        search_View_var.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    newText = newText.toLowerCase();
                    ArrayList<All_Music_Data_Model> newList = new ArrayList<>();
                    for (All_Music_Data_Model productDetailPojo : m_all_music_data) {
                        String name = productDetailPojo.trackk_Title_str.toLowerCase().trim();

                        if (name.contains(newText))
                            newList.add(productDetailPojo);
                    }
                    music_adapter_var.setFilter(newList);
                    mMusicList_recview.setAdapter(music_adapter_var);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


    }

    private void requestAudioFocusMethod() {
        if (am_var != null) {
            am_var.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    @Override
    public void onAudioFocusChange(int i) {
        Log.e("ag--12", "1 : " + i);
        switch (i) {
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.e("ag--12", "2 : ");
                pause();
                bool_player_var = true;
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.e("ag--12", "3 : ");
                pause();
                bool_player_var = true;
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
        if (player_MP.isPlaying()) {
            player_MP.pause();
            music_adapter_var.notifyDataSetChanged();
        }
    }

    void hideKeyboardMethod() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void setUpRecyclerViewMethod() {
        this.music_adapter_var = new MusicAdapter(this, this.m_all_music_data);
        this.mMusicList_recview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        this.mMusicList_recview.setItemAnimator(new DefaultItemAnimator());
        this.mMusicList_recview.setAdapter(this.music_adapter_var);
    }

    private boolean isAudioFileMethod(String str) {
        return !TextUtils.isEmpty(str) && str.endsWith(".mp3");
    }

    public ArrayList<All_Music_Data_Model> getMusicFilesMethod() {
        ArrayList<All_Music_Data_Model> arrayList = new ArrayList<>();
        Cursor query = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "title", "_data", "_display_name", "duration"}, "is_music != 0", (String[]) null, "date_modified DESC");
        int columnIndex = query.getColumnIndex("_id");
        int columnIndex2 = query.getColumnIndex("title");
        int columnIndex3 = query.getColumnIndex("_display_name");
        int columnIndex4 = query.getColumnIndex("_data");
        int columnIndex5 = query.getColumnIndex("duration");
        while (query.moveToNext()) {
            String string = query.getString(columnIndex4);
            if (isAudioFileMethod(string)) {
                All_Music_Data_Model allMusicDataModel = new All_Music_Data_Model();
                allMusicDataModel.trackk_id_var = query.getLong(columnIndex);
                allMusicDataModel.trackk_Title_str = query.getString(columnIndex2);
                allMusicDataModel.trackk_data_str = string;
                allMusicDataModel.trackk_duration_var = query.getLong(columnIndex5);
                allMusicDataModel.trackk_display_Name_str = query.getString(columnIndex3);
                arrayList.add(allMusicDataModel);
            }
        }
        return arrayList;
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

    public void CreateSongMethod(String str) {
        player_MP = new MediaPlayer();
        currentFile_str = str;
        path_var = new File(currentFile_str);
        name_str = new File(currentFile_str).getName();
        try {
            player_MP.setDataSource(currentFile_str);
            player_MP.prepare();
            player_MP.start();
            requestAudioFocusMethod();
            bool_player_var = false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        player_MP.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.e("ag--12", "onCompletion : ");
                if (!player_MP.isPlaying()) {
                    bool_player_var = true;
                    music_adapter_var.notifyDataSetChanged();
                }
            }
        });
    }

    private String makeRingtoneFilenameMethod(String str) {
        File file = new File(path_dir_var);
        String sb = "temp" + str;
        File file2 = new File(file, sb);
        if (file2.exists()) {
            FileUtils.deleteFile(file2);
        }
        return file2.getAbsolutePath();
    }

    private String getExtensionFromFilenameMethod(String str) {
        return str.substring(str.lastIndexOf(46), str.length());
    }

    long getByteNoFromSecNoMethod(long startSec, long totalDuration, long noOfBytes) {
        return startSec * noOfBytes / totalDuration;
    }

    @Override
    protected void onPause() {
        super.onPause();


        if (player_MP.isPlaying()) {
            player_MP.pause();
            pause_cheker_ = true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


        Log.e("ag--12", "onResume: off : ");


        if (pause_cheker_) {
            if (m_all_music_data != null && !m_all_music_data.isEmpty() && music_adapter_var != null) {
                music_adapter_var.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
                    if (search_edit_text_var.getText().length() != 0) {
                        search_edit_text_var.getText().clear();
                    } else {
                        Offline_Song_Act.super.onBackPressed();
                    }

            if (search_edit_text_var.getText().length() != 0) {
                search_edit_text_var.getText().clear();
            } else {
                super.onBackPressed();
            }

        if (player_MP.isPlaying()) {
            player_MP.pause();
        }

    }



    @SuppressLint("StaticFieldLeak")
    public class LoadMusics extends AsyncTask<Void, Void, Void> {
        Dialog progressDialog;

        public LoadMusics() {
        }

        public void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Offline_Song_Act.this);
            progressDialog.setContentView(R.layout.dialogg_loaderr);
            progressDialog.getWindow().setGravity(Gravity.CENTER);
            progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        public Void doInBackground(Void... voidArr) {
            m_all_music_data = getMusicFilesMethod();
            try {
                All_Music_Data_Model selectedAllMusicDataModel = m_all_music_data.get(0);
                m_Filename_STR = selectedAllMusicDataModel.getTrackk_data_str();
                return null;
            } catch (Exception unused4) {
                return null;
            }
        }

        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            setUpRecyclerViewMethod();
            progressDialog.dismiss();
        }
    }

    public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {
        public boolean checkPlay = false;
        SparseBooleanArray booleanArray = new SparseBooleanArray();
        int mSelectedChoice = 0;
        int mSelectedPos = -1;
        Context context;
        long duration;
        private ArrayList<All_Music_Data_Model> allMusicDatailModels;

        public MusicAdapter(Context context1, ArrayList<All_Music_Data_Model> arrayList) {
            context = context1;
            this.allMusicDatailModels = arrayList;
            this.booleanArray.put(0, true);
        }

        public void setFilter(List<All_Music_Data_Model> songList) {
            allMusicDatailModels = new ArrayList<>();
            allMusicDatailModels.addAll(songList);
            notifyDataSetChanged();
        }

        public int getItemCount() {
            return this.allMusicDatailModels.size();
        }

        @SuppressLint("NewApi")
        public void onBindViewHolder(final Holder holder, @SuppressLint("RecyclerView") final int i) {
            if (bool_player_var) {
                holder.play_pause.setImageResource(R.drawable.ic_play);
                pause_cheker_ = true;
            }

            holder.radioMusicName.setText(((All_Music_Data_Model) this.allMusicDatailModels.get(i)).trackk_display_Name_str);
            int drt = Math.toIntExact(((All_Music_Data_Model) this.allMusicDatailModels.get(i)).trackk_duration_var);
            holder.name_of_music.setText(((All_Music_Data_Model) this.allMusicDatailModels.get(i)).trackk_display_Name_str);
            holder.textViewEnd.setText(getDurationMethod(allMusicDatailModels.get(i).trackk_duration_var));

            String dty = timeConversion(drt);
            holder.duration_TV.setText(dty);
            duration = player_MP.getDuration();
            m_file_duration = player_MP.getDuration();

            if (pause_cheker_) {
                holder.play_pause.setImageResource(R.drawable.ic_play);
            } else {
                holder.play_pause.setImageResource(R.drawable.pause_nn);
            }

            if (mSelectedPos == i) {
                holder.cutter.setVisibility(View.VISIBLE);
                checkPlay = true;
            } else {
                holder.cutter.setVisibility(View.GONE);
            }

            search_edit_text_var.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() >= 0) {
                        player_MP.pause();
                        mSelectedPos = -1;
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.playSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pause_cheker_ = false;
                    mSelectedPos = i;
                    if (player_MP.isPlaying()) {
                        player_MP.pause();
                    }

                    MusicAdapter.this.booleanArray.clear();
                    MusicAdapter.this.booleanArray.put(i, true);
                    MusicAdapter.this.playMusic(i);
                    File file11 = new File(((All_Music_Data_Model) allMusicDatailModels.get(i)).trackk_data_str);
                    String path = file11.getAbsolutePath();
                    MusicAdapter.this.booleanArray.put(i, true);
                    CreateSongMethod(path);
                    checkPlay = true;
                    notifyDataSetChanged();
                }
            });

            holder.play_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkPlay) {
                        if (!player_MP.isPlaying()) {
                            player_MP.start();
                            requestAudioFocusMethod();
                            holder.play_pause.setImageResource(R.drawable.pause_nn);
                        } else {
                            player_MP.pause();
                            holder.play_pause.setImageResource(R.drawable.ic_play);
                        }
                    }
                }
            });

            holder.backward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectedChoice == 0) {
                        Toast.makeText(context, "No More Songs", Toast.LENGTH_SHORT).show();
                    } else {
                        mSelectedPos--;
                        if (player_MP.isPlaying()) {
                            player_MP.pause();
                        }
                        MusicAdapter.this.booleanArray.clear();
                        MusicAdapter.this.booleanArray.put(mSelectedPos, true);
                        MusicAdapter.this.playMusic(mSelectedPos);
                        File file11 = new File(((All_Music_Data_Model) allMusicDatailModels.get(mSelectedPos)).trackk_data_str);
                        String path = file11.getAbsolutePath();
                        MusicAdapter.this.booleanArray.put(mSelectedPos, true);
                        CreateSongMethod(path);
                        checkPlay = true;
                        notifyDataSetChanged();
                    }
                }
            });

            holder.forward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectedChoice == allMusicDatailModels.size() - 1) {
                        Toast.makeText(context, "No More Songs", Toast.LENGTH_SHORT).show();
                    } else {
                        mSelectedPos++;
                        if (player_MP.isPlaying()) {
                            player_MP.pause();
                        }
                        MusicAdapter.this.booleanArray.clear();
                        MusicAdapter.this.booleanArray.put(mSelectedPos, true);
                        MusicAdapter.this.playMusic(mSelectedPos);
                        File file11 = new File(((All_Music_Data_Model) allMusicDatailModels.get(mSelectedPos)).trackk_data_str);
                        String path = file11.getAbsolutePath();
                        MusicAdapter.this.booleanArray.put(mSelectedPos, true);
                        CreateSongMethod(path);
                        checkPlay = true;
                        notifyDataSetChanged();
                    }
                }
            });

            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player_MP.pause();
                    holder.cutter.setVisibility(View.GONE);
                }
            });

            holder.rangeSeekbar.setMinStartValue(0);
            holder.rangeSeekbar.setMaxValue(duration);

            player_MP.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    holder.textViewEnd.setText(getDurationMethod(player_MP.getDuration()));
                }
            });

            holder.rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue, Number maxValue) {
                    start_from_sec = (long) minValue;
                    end_at_sec = (long) maxValue;
                    holder.textViewStart.setText(getDurationMethod((long) minValue));
                    holder.textViewEnd.setText(getDurationMethod((long) maxValue));

                }
            });

            holder.rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                @Override
                public void finalValue(Number minValue, Number maxValue) {
                    if (player_MP.isPlaying()) {
                        player_MP.pause();
                        holder.play_pause.setImageResource(R.drawable.ic_play);
                    }
                }
            });

            holder.useSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (allMusicDatailModels.get(i).trackk_duration_var < 600000) {

                        holder.useSong.setClickable(false);
                        holder.useSong.setEnabled(false);

                        Log.e("ag--12", "useSong 1 : ");

                        holder.anim.setVisibility(View.VISIBLE);

                        File file = new File(currentFile_str);
                        final long noOfBytes = file.length();
                        player_MP.pause();

                        long vid_dur = Video_Maker_Act.video_duration_VAR * 1000;
                        long dur_song = start_from_sec + (vid_dur);
                        long diffrence = end_at_sec - start_from_sec;

                        long endTmp;
                        if (diffrence < vid_dur) {
                            endTmp = end_at_sec;
                        } else {
                            endTmp = dur_song;
                        }

                        endTmp = endTmp + endTmp;

                        long bytesToSkip = getByteNoFromSecNoMethod(start_from_sec, duration, noOfBytes);
                        long lastByteNo = getByteNoFromSecNoMethod(endTmp, duration, noOfBytes);
                        long bytesToRead = lastByteNo - bytesToSkip;

                        Log.e("ag--12", "useSong 2 : " + allMusicDatailModels.get(i).trackk_duration_var);
                        Log.e("ag--12", "useSong 3 : " + noOfBytes);
                        Log.e("ag--12", "useSong 4 : " + start_from_sec);
                        Log.e("ag--12", "useSong 5 : " + bytesToSkip);
                        Log.e("ag--12", "useSong 6 : " + endTmp);
                        Log.e("ag--12", "useSong 7 : " + lastByteNo);
                        Log.e("ag--12", "useSong 8 : " + bytesToRead);

                        try {
                            String pathNewFile = makeRingtoneFilenameMethod(getExtensionFromFilenameMethod(name_str));
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                            bufferedInputStream.skip(bytesToSkip);
                            File newFile = new File(pathNewFile);
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));

                            Log.e("ag--12", "useSong 10 : " + pathNewFile);
                            Log.e("ag--12", "useSong 11 : " + bufferedInputStream);
                            Log.e("ag--12", "useSong 12 : " + newFile);
                            Log.e("ag--12", "useSong 13 : " + bufferedOutputStream);

                            while (bytesToRead-- > 0) {
                                bufferedOutputStream.write(bufferedInputStream.read());
                            }

                            bufferedInputStream.close();
                            bufferedOutputStream.close();

                            player_MP.stop();
                            song_play_var = false;

                            long length = path_var.length();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("_data", path_var.getAbsolutePath());
                            contentValues.put("title", path_var.getName());
                            contentValues.put("_size", Long.valueOf(length));
                            contentValues.put("mime_type", "audio/mpeg");
                            contentValues.put("artist", (String) getResources().getText(R.string.artist_name));
                            contentValues.put("duration", dur_song);
                            contentValues.put("is_music", true);

                            Log.e("ag--12", "useSong 14 : " + length);
                            Log.e("ag--12", "useSong 15 : " + path_var.getAbsolutePath());
                            Log.e("ag--12", "useSong 16 : " + path_var.getName());
                            Log.e("ag--12", "useSong 17 : " + Long.valueOf(length));
                            Log.e("ag--12", "useSong 17 2 : " + dur_song);
                            Log.e("ag--12", "useSong 18 : " + (String) getResources().getText(R.string.artist_name));
                            Log.e("ag--12", "useSong 19 : " + Integer.valueOf(m_file_duration));
                            Log.e("ag--12", "useSong 19 2 : " + diffrence);
                            Log.e("ag--12", "useSong 20 : " + true);

                            setResult(-1);
                            All_Music_Data_Model selectedAllMusicDataModel = new All_Music_Data_Model();
                            selectedAllMusicDataModel.trackk_data_str = pathNewFile;
                            selectedAllMusicDataModel.trackk_duration_var = diffrence;
                            MainApplication.getInstance().setMusicData(selectedAllMusicDataModel);

                            Log.e("ag--12", "useSong 21 : " + selectedAllMusicDataModel);
                            Log.e("ag--12", "useSong 22 : " + selectedAllMusicDataModel.trackk_data_str);
                            Log.e("ag--12", "useSong 23 : " + selectedAllMusicDataModel.trackk_duration_var);
                            Log.e("ag--12", "useSong 24 : " + m_file_duration);
                            Log.e("ag--12", "useSong 25 : " + (m_file_duration) * 1000);
                            Log.e("ag--12", "useSong 26 : " + selectedAllMusicDataModel);

                            finish();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (allMusicDatailModels.get(i).trackk_duration_var >= 600000 && start_from_sec < 6000) {
                        Toast.makeText(context, "Song is too long, Please trim the Song from both Sides.", Toast.LENGTH_SHORT).show();
                    } else if (allMusicDatailModels.get(i).trackk_duration_var >= 600000 && start_from_sec >= 6000) {
                        holder.useSong.setClickable(false);
                        holder.useSong.setEnabled(false);

                        Log.e("ag--12", "useSong 1 : ");

                        holder.anim.setVisibility(View.VISIBLE);

                        File file = new File(currentFile_str);
                        final long noOfBytes = file.length();
                        player_MP.pause();

                        long vid_dur = Video_Maker_Act.video_duration_VAR * 1000;
                        long dur_song = start_from_sec + (vid_dur);
                        long diffrence = end_at_sec - start_from_sec;

                        long endTmp;
                        if (diffrence < vid_dur) {
                            endTmp = end_at_sec;
                        } else {
                            endTmp = dur_song;
                        }

                        long bytesToSkip = getByteNoFromSecNoMethod(start_from_sec, duration, noOfBytes);
                        long lastByteNo = getByteNoFromSecNoMethod(endTmp, duration, noOfBytes);
                        long bytesToRead = lastByteNo - bytesToSkip;

                        Log.e("ag--12", "useSong 2 : " + allMusicDatailModels.get(i).trackk_duration_var);
                        Log.e("ag--12", "useSong 3 : " + noOfBytes);
                        Log.e("ag--12", "useSong 4 : " + start_from_sec);
                        Log.e("ag--12", "useSong 5 : " + bytesToSkip);
                        Log.e("ag--12", "useSong 6 : " + endTmp);
                        Log.e("ag--12", "useSong 7 : " + lastByteNo);
                        Log.e("ag--12", "useSong 8 : " + bytesToRead);

                        try {
                            String pathNewFile = makeRingtoneFilenameMethod(getExtensionFromFilenameMethod(name_str));
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                            bufferedInputStream.skip(bytesToSkip);
                            File newFile = new File(pathNewFile);
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));

                            Log.e("ag--12", "useSong 10 : " + pathNewFile);
                            Log.e("ag--12", "useSong 11 : " + bufferedInputStream);
                            Log.e("ag--12", "useSong 12 : " + newFile);
                            Log.e("ag--12", "useSong 13 : " + bufferedOutputStream);

                            while (bytesToRead-- > 0) {
                                bufferedOutputStream.write(bufferedInputStream.read());
                            }

                            bufferedInputStream.close();
                            bufferedOutputStream.close();

                            player_MP.stop();
                            song_play_var = false;

                            long length = path_var.length();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("_data", path_var.getAbsolutePath());
                            contentValues.put("title", path_var.getName());
                            contentValues.put("_size", Long.valueOf(length));
                            contentValues.put("mime_type", "audio/mpeg");
                            contentValues.put("artist", (String) getResources().getText(R.string.artist_name));
                            contentValues.put("duration", dur_song);
                            contentValues.put("is_music", true);

                            Log.e("ag--12", "useSong 14 : " + length);
                            Log.e("ag--12", "useSong 15 : " + path_var.getAbsolutePath());
                            Log.e("ag--12", "useSong 16 : " + path_var.getName());
                            Log.e("ag--12", "useSong 17 : " + Long.valueOf(length));
                            Log.e("ag--12", "useSong 17 2 : " + dur_song);
                            Log.e("ag--12", "useSong 18 : " + (String) getResources().getText(R.string.artist_name));
                            Log.e("ag--12", "useSong 19 : " + Integer.valueOf(m_file_duration));
                            Log.e("ag--12", "useSong 19 2 : " + diffrence);
                            Log.e("ag--12", "useSong 20 : " + true);

                            setResult(-1);
                            All_Music_Data_Model selectedAllMusicDataModel = new All_Music_Data_Model();
                            selectedAllMusicDataModel.trackk_data_str = pathNewFile;
                            selectedAllMusicDataModel.trackk_duration_var = diffrence;
                            MainApplication.getInstance().setMusicData(selectedAllMusicDataModel);

                            Log.e("ag--12", "useSong 21 : " + selectedAllMusicDataModel);
                            Log.e("ag--12", "useSong 22 : " + selectedAllMusicDataModel.trackk_data_str);
                            Log.e("ag--12", "useSong 23 : " + selectedAllMusicDataModel.trackk_duration_var);
                            Log.e("ag--12", "useSong 24 : " + m_file_duration);
                            Log.e("ag--12", "useSong 25 : " + (m_file_duration) * 1000);
                            Log.e("ag--12", "useSong 26 : " + selectedAllMusicDataModel);

                            finish();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offlinee_songg_itemm, viewGroup, false));
        }

        public void playMusic(int i) {
            selected_all_music_data = (All_Music_Data_Model) m_all_music_data.get(i);
            m_Filename_STR = selected_all_music_data.getTrackk_data_str();
            this.mSelectedChoice = i;

            Log.e("ag--12", "playMusic : " + this.mSelectedChoice);
        }

        public String timeConversion(long value) {
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

        public class Holder extends RecyclerView.ViewHolder {
            public TextView radioMusicName;
            public TextView name_of_music;
            public ImageView play_pause;
            public RelativeLayout cutter;
            ImageView tower;
            CrystalRangeSeekbar rangeSeekbar;
            TextView textViewStart;
            TextView textViewEnd;
            RelativeLayout playSong;
            ImageView useSong;
            ImageView cancel;
            TextView duration_TV;
            ImageView forward;
            ImageView backward;
            LottieAnimationView anim;

            public Holder(View view) {
                super(view);
                this.radioMusicName = view.findViewById(R.id.tv_name);
                this.name_of_music = view.findViewById(R.id.music_name);
                this.play_pause = view.findViewById(R.id.play_pause);
                this.cutter = view.findViewById(R.id.cutter);
                this.rangeSeekbar = view.findViewById(R.id.rangeSeekbar);
                this.tower = view.findViewById(R.id.tower);
                this.playSong = view.findViewById(R.id.list);
                this.useSong = view.findViewById(R.id.iv_add);
                this.cancel = view.findViewById(R.id.iv_cancel);
                this.textViewStart = view.findViewById(R.id.textViewStart);
                this.textViewEnd = view.findViewById(R.id.textViewEnd);
                this.duration_TV = view.findViewById(R.id.tv_duratoin);
                this.forward = view.findViewById(R.id.play_forward);
                this.backward = view.findViewById(R.id.play_back);
                this.anim = view.findViewById(R.id.anim);
            }
        }
    }
}