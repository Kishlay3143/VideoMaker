package com.status.videomaker.Activitiess;

import static com.status.videomaker.Activitiess.Home_Actvty.appStart;
import static com.status.videomaker.Activitiess.Home_Actvty.spshowTotalHome;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Adapterss.Creationn_Adptr;
import com.status.videomaker.Modelss.Video_Model;
import com.status.videomaker.R;
import com.status.videomaker.dialog.Dialog_RateApp;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class _Creationn_Activityy extends AppCompatActivity {
    public static ArrayList<Video_Model> _mVideos_List_;
    public static String _SAVED_FILES_LOCATION_;
    public static Creationn_Adptr _creationnAdptr_;
    ImageView _backPressed_;
    RelativeLayout _no_item_;
    private RecyclerView _creation_recycleView_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_creationn);

        _creation_recycleView_ = (RecyclerView) findViewById(R.id.recyclerView);
        _backPressed_ = (ImageView) findViewById(R.id.backButton);
        _no_item_ = (RelativeLayout) findViewById(R.id.no_item);
        _SAVED_FILES_LOCATION_ = ("/storage/emulated/0/Download/Status Video Maker/");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_Creationn_Activityy.this);
        _creation_recycleView_.setLayoutManager(linearLayoutManager);
        get_all_videos_();

        _backPressed_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        if (spshowTotalHome != 2) {
            Log.e("fffff", "onCreate:22222 " + spshowTotalHome);
            show_rate();
        }
    }


    public void show_rate() {
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int showCnt = mSharedPrefs.getInt("SubmitClick", 0);
        Log.e("vvvv......", ".............rate.................showCnt :: " + showCnt);
        if (showCnt < 2) {
            final int random = new Random().nextInt(2);// 50%
//            final int random = 0;// 100%
            Log.e("fffff", ".............rate.................random :: " + random);
            if (random == 0) {
                Log.e("vvvv......", ".............rate.................first_open :: " + appStart);
                if (!appStart) {
                    Log.d("VD_event---", "VD_show_rateus");
                    new Dialog_RateApp(this).show();
                    appStart = true;
                }

            }

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        }

        return super.onPrepareOptionsMenu(menu);
    }


    public void get_all_videos_() {
        _mVideos_List_ = new ArrayList();
        Cursor query;
        String[] strArr = new String[]{"_id", "_data", "title", "_size", "date_modified", "duration", "_display_name"};

        query = getContentResolver().query(MediaStore.Files.getContentUri("external"), strArr, MediaStore.Video.Media.DATA + " like ? ", new String[]{"%" + _SAVED_FILES_LOCATION_ + "%"}, MediaStore.Video.Media.DATE_MODIFIED + " DESC");

        if (query != null) {
            while (query.moveToNext()) {
                String string = query.getString(0);
                String string2 = query.getString(1);
                String string3 = query.getString(2);
                long string4 = query.getLong(3);
                String string5 = query.getString(4);
                long j = query.getLong(5);
                String string6 = query.getString(6);
                if (new File(string2).exists()) {
                    File file2 = new File(string2);
                    Log.e("jfgfgfjgjhghj", "get_all_videos_:....0000000... "+file2.getAbsolutePath() );
                    Video_Model wAStatus = new Video_Model();
                    wAStatus.set_id(string);
                    wAStatus.setFileName(file2.getName());
                    wAStatus.setStatusType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file2).toString())));
                    wAStatus.setFilePath(file2.getAbsolutePath());
                    wAStatus.setFileSize(String.valueOf(j));
                    wAStatus.setDate(string5);

                    if ((file2.getName().startsWith("Status ") && file2.getName().endsWith(".mp4") || file2.getName().endsWith(".gif"))) {
                        _mVideos_List_.add(wAStatus);
                    }
                }
            }
            query.close();
        }

        if (_mVideos_List_.size() == 0) {
            _creation_recycleView_.setVisibility(View.GONE);
            _no_item_.setVisibility(View.VISIBLE);
        } else {
            _creation_recycleView_.setVisibility(View.VISIBLE);
            _no_item_.setVisibility(View.GONE);
        }

        if (_mVideos_List_ != null) {
            _creationnAdptr_ = new Creationn_Adptr(_Creationn_Activityy.this, _mVideos_List_);
            _creation_recycleView_.setAdapter(_creationnAdptr_);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1233) {
            Creationn_Adptr.confirm_delete_Method(Creationn_Adptr.pure_var);
            Toast.makeText(_Creationn_Activityy.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }


}