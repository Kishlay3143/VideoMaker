package com.status.videomaker.Activitiess;

import static com.status.videomaker.Activitiess.Video_Maker_Act.video_maker_back_bool;
import static com.status.videomaker.Activitiess.Video_Maker_Act._video_maker_boolean_2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Adapterss.Swapp_Img_Adptr;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.R;
import com.status.videomaker.videolib.libffmpeg.FileUtils;
import com.status.videomaker.view.EmptyRecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Swapp_Images_Actvty extends AppCompatActivity {
    public static final String EXTRA_FROM_PREVIEW_str = "extra_from_preview";
    public static boolean boolean_swapp_img_back = false;
    public static Swapp_Img_Adptr swapp_img_adapterr;
    public boolean is_from_preview = false;
    EmptyRecyclerView recyclerView_ERV;
    MainApplication application_MAIN;
    LinearLayout nextButton_ll;
    RelativeLayout backButton_RL;
    String[] Song_List_str = {"Sad", "Happy", "Joy", "Love",};
    int[] d_song_array = {R.raw._1, R.raw._2, R.raw._3, R.raw._4,};
    ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState == 0) {
                Swapp_Images_Actvty.this.swapp_img_adapterr.notifyDataSetChanged();
            }
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return ItemTouchHelper.Callback.makeFlag(2, 51);
        }

        @Override
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            Swapp_Images_Actvty.this.swapp_img_adapterr.swap_method(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            Swapp_Images_Actvty.this.application_MAIN.min_pos = Math.min(Swapp_Images_Actvty.this.application_MAIN.min_pos, Math.min(fromPos, toPos));
            MainApplication.isBreak = true;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_swapp_images);

        Log.e("ag--12", "onCreate ");

        recyclerView_ERV = findViewById(R.id.recyclerView);
        nextButton_ll = findViewById(R.id.tvstart);
        backButton_RL = findViewById(R.id.backButton);
        application_MAIN = MainApplication.getInstance();

        this.is_from_preview = getIntent().hasExtra(EXTRA_FROM_PREVIEW_str);
        load_Images_List();
        set_Music_Method();

        backButton_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @SuppressLint("WrongConstant")
    private void load_Images_List() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager((Context) this, 3, 1, false);
        this.swapp_img_adapterr = new Swapp_Img_Adptr(this);
        this.recyclerView_ERV.setLayoutManager(gridLayoutManager);
        this.recyclerView_ERV.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView_ERV.setAdapter(this.swapp_img_adapterr);
        new ItemTouchHelper(this._ithCallback).attachToRecyclerView(this.recyclerView_ERV);

        nextButton_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        next_Activity_Method();
            }
        });
    }

    public void set_Music_Method() {
        File pathSDCard = new File(String.valueOf(FileUtils.DirDefaultAudio));
        try {
            if (!pathSDCard.exists()) {
                pathSDCard.mkdirs();
            }
            String absolutePath = String.valueOf(pathSDCard);
            File file = new File(absolutePath);
            if (file.mkdirs() || file.isDirectory()) {
                StringBuilder sb = new StringBuilder();
                sb.append(absolutePath);
                sb.append(File.separator);

                for (int i = 0; i < d_song_array.length; i++) {
                    Copy_RAW_to_SDCard_Method(d_song_array[i], sb.toString() + Song_List_str[i] + ".mp3");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("setMusic: ");
            sb2.append(e.getMessage());
            Log.e("TAG", sb2.toString());
        }
    }

    private void Copy_RAW_to_SDCard_Method(int i, String str) throws IOException {
        InputStream openRawResource = getResources().openRawResource(i);
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = openRawResource.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    return;
                }
            } catch (Exception eee) {
                eee.printStackTrace();
            }
        }
    }

    private void next_Activity_Method() {
        this.application_MAIN.isEditModeEnable = false;
        if (_video_maker_boolean_2) {
            create_Videos_Method();
            _video_maker_boolean_2 = false;
        } else {


            if (this.is_from_preview) {
                setResult(-1);
                finish();
                return;
            }

            if (application_MAIN.getSelectedImages().size() > 2) {
                create_Videos_Method();
            } else {
                if (is_from_preview) {
                    create_Videos_Method();
                } else {
                    Toast.makeText(Swapp_Images_Actvty.this, "select more than 2 images for create video", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void create_Videos_Method() {
        String ss = "loadVideoPlayActivity";
        Intent i = new Intent(Swapp_Images_Actvty.this, Video_Maker_Act.class);
        i.putExtra("images", ss);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("ag--12", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 1001 && data.getExtras() != null) {
            String uriPath = String.valueOf(data.getExtras().get("uri_new"));
            Folder_Image_Album_Model folderImageAlbumModel = new Folder_Image_Album_Model();
            folderImageAlbumModel.set_image_Path_(uriPath);
            this.application_MAIN.ReplaceSelectedImage(folderImageAlbumModel, data.getExtras().getInt("position"));
            Swapp_Img_Adptr swapImageAdapter = this.swapp_img_adapterr;
            if (swapImageAdapter != null) {
                swapImageAdapter.notifyItemChanged(data.getExtras().getInt("position"));
            }
        }
    }

    @Override
    public void onBackPressed() {
                    if (video_maker_back_bool) {
                        next_Activity_Method();
                        video_maker_back_bool = false;
                    } else {
                        startActivity(new Intent(Swapp_Images_Actvty.this, Gallery_Act.class));
            if (video_maker_back_bool) {
                next_Activity_Method();
                video_maker_back_bool = false;
            } else {
                startActivity(new Intent(Swapp_Images_Actvty.this, Gallery_Act.class));
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        swapp_img_adapterr.notifyDataSetChanged();

        Log.e("ag--12", "Swap_Image_back onResume " + boolean_swapp_img_back);
        if (boolean_swapp_img_back) {
            next_Activity_Method();
            boolean_swapp_img_back = false;
        }

        Log.e("ag--12", "onResume  ");

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

}