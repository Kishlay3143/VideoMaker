package com.status.videomaker.Activitiess;

import static com.status.videomaker.Activitiess.MainApplication.allAlbum;
import static com.status.videomaker.Activitiess.MainApplication.allFolder;
import static com.status.videomaker.Activitiess.Swapp_Images_Actvty.boolean_swapp_img_back;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Adapterss.Folderr_Adptr;
import com.status.videomaker.Adapterss.Folder_Image_Adapter;
import com.status.videomaker.Adapterss.Selectedd_Imagee_Adptr;
import com.status.videomaker.InterFacess.OnItemClickListner;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.R;
import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.util.ArrayList;

public class Gallery_Act extends AppCompatActivity {
    public static final String EXTRA_FROM_PREVIEW_var = "extra_from_preview";
    public static String path_dir_var;
    public static boolean open_folder_var = false;
    public static TextView text_view_var;
    public static TextView tv_count_var;
    public static TextView tv_clear_var;
    public static Folderr_Adptr folder_adap_var;
    public static Folder_Image_Adapter folder_img_adap_var;
    public static Selectedd_Imagee_Adptr selected_img_adap_var;
    public static RecyclerView selected_img_recyc_var;
    public static RelativeLayout selected_rl_var;
    public boolean is_from_preview_var = false;
    MainApplication main_application_var;
    RecyclerView folder_recycler_var;
    RecyclerView folder_img_recycler_var;
    RelativeLayout next_button_var;
    RelativeLayout back_press_var;
    RelativeLayout no_item_var;
    TextView please_select_var;
    MainApplication application_var;
    LottieAnimationView lot_animation_var;

    public static String getUploadVideoPathMethod(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File ef = context.getExternalFilesDir("/");
            if (ef != null && ef.isDirectory()) {
                return ef.getPath();
            }
        }
        return new File(Environment.getExternalStorageDirectory(), "ve").getPath();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_galleryy);

        Log.e("ag--12", "Gallery_Activity onCreate: ");

        folder_recycler_var = findViewById(R.id.folder);
        folder_img_recycler_var = findViewById(R.id.gallery);
        selected_img_recyc_var = findViewById(R.id.selectPhoto);
        selected_rl_var = findViewById(R.id.selectLayout);
        next_button_var = findViewById(R.id.next);
        back_press_var = findViewById(R.id.backButton);
        no_item_var = findViewById(R.id.no_item);
        please_select_var = findViewById(R.id.pleaseAdd);
        main_application_var = MainApplication.getInstance();
        this.is_from_preview_var = getIntent().hasExtra(EXTRA_FROM_PREVIEW_var);
        text_view_var = findViewById(R.id.textView);
        tv_count_var = findViewById(R.id.tv_count);
        tv_clear_var = findViewById(R.id.tv_clear);
        lot_animation_var = findViewById(R.id.lot_anim);
        application_var = MainApplication.getInstance();
        text_view_var.setSelected(true);

        path_dir_var = getUploadVideoPathMethod(Gallery_Act.this);
        next_button_var.setVisibility(View.INVISIBLE);
        lot_animation_var.setVisibility(View.VISIBLE);

        back_press_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ag--12", "onClick: ");
                onBackPressed();
            }
        });


    }

    @SuppressLint("WrongConstant")
    private void folderDataMethod() {
        folder_adap_var = new Folderr_Adptr(this);
        folder_img_adap_var = new Folder_Image_Adapter(this);
        selected_img_adap_var = new Selectedd_Imagee_Adptr(this);

        folder_recycler_var.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        folder_recycler_var.setItemAnimator(new DefaultItemAnimator());
        folder_recycler_var.setAdapter(this.folder_adap_var);

        folder_img_recycler_var.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        folder_img_recycler_var.setItemAnimator(new DefaultItemAnimator());
        folder_img_recycler_var.setAdapter(this.folder_img_adap_var);

        selected_img_recyc_var.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
        folder_recycler_var.setItemAnimator(new DefaultItemAnimator());
        selected_img_recyc_var.setAdapter(this.selected_img_adap_var);

        folder_adap_var.setOnItemClickListner(new OnItemClickListner<Object>() {
            public void onItemClick(View view, Object o) {
                open_folder_var = true;
                folder_img_adap_var.notifyDataSetChanged();
                folder_recycler_var.setVisibility(View.GONE);
                folder_img_recycler_var.setVisibility(View.VISIBLE);
                selected_rl_var.setVisibility(View.VISIBLE);
                next_button_var.setVisibility(View.VISIBLE);
            }
        });

        folder_img_adap_var.setOnItemClickListner(new OnItemClickListner<Object>() {
            @Override
            public void onItemClick(View view, Object o) {
                selected_img_adap_var.notifyDataSetChanged();
                try {
                    selected_img_recyc_var.scrollToPosition(application_var.selectedImages.size() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        next_button_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (boolean_swapp_img_back) {
                    Gallery_Act.super.onBackPressed();
                } else if (main_application_var.getSelectedImages().size() > 2) {
                    loadDoneMethod();
                } else {
                    if (is_from_preview_var) {
                        loadDoneMethod();
                    } else {
                        Toast.makeText(Gallery_Act.this, "Select more than 2 images for Video Creation", 0).show();
                    }
                }

            }
        });
    }

    public boolean loadDoneMethod() {
        if (this.is_from_preview_var) {
            Folder_Image_Album_Model imageData = null;
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.main_application_var.selectedImages);
            this.main_application_var.selectedImages.clear();
            for (int i = 0; i < arrayList.size(); i++) {
                this.main_application_var.selectedImages.add((Folder_Image_Album_Model) arrayList.get(i));
            }
            if (imageData != null) {
                this.main_application_var.selectedImages.add(imageData);
            }
            setResult(-1);
            return true;
        }

        Intent intent = new Intent(Gallery_Act.this, Swapp_Images_Actvty.class);
        intent.putExtra("isFromCameraNotification", false);
        intent.putExtra("KEY", "FromImageSelection");
        startActivity(intent);
        return false;
    }

    @Override
    public void onBackPressed() {
        Home_Actvty.home_click_var = true;
        Log.e("ag--12", "Close_IsAds : " + boolean_swapp_img_back);
        if (no_item_var.getVisibility() == View.VISIBLE) {
            Log.e("gffggfgg", "onBackPressed:..99999... ");

            startActivity(new Intent(Gallery_Act.this, Home_Actvty.class));

        } else if (folder_recycler_var.getVisibility() == View.VISIBLE) {
            if (boolean_swapp_img_back) {
                Log.e("gffggfgg", "onBackPressed:..10... ");

                Gallery_Act.super.onBackPressed();
            } else {
                Log.e("gffggfgg", "onBackPressed:..88888... ");

                startActivity(new Intent(Gallery_Act.this, Home_Actvty.class));
            }
        } else if (folder_img_recycler_var.getVisibility() == View.VISIBLE) {

            Log.e("gffggfgg", "onBackPressed:..77777... ");

            folder_recycler_var.setVisibility(View.VISIBLE);
            folder_img_recycler_var.setVisibility(View.GONE);
            selected_rl_var.setVisibility(View.GONE);
            next_button_var.setVisibility(View.INVISIBLE);
            text_view_var.setText("Album");
        } else {
            Log.e("gffggfgg", "onBackPressed:..66666... ");

            startActivity(new Intent(Gallery_Act.this, Home_Actvty.class));
        }


        if (no_item_var.getVisibility() == View.VISIBLE) {
            Log.e("gffggfgg", "onBackPressed:..00000... ");

            startActivity(new Intent(Gallery_Act.this, Home_Actvty.class));
        } else if (folder_recycler_var.getVisibility() == View.VISIBLE) {
            Log.e("gffggfgg", "onBackPressed:..11111... ");

            if (boolean_swapp_img_back) {
                Log.e("gffggfgg", "onBackPressed:..******... ");

                Gallery_Act.super.onBackPressed();
            } else {
                Log.e("gffggfgg", "onBackPressed:..22222... ");

            }
        } else if (folder_img_recycler_var.getVisibility() == View.VISIBLE) {
            Log.e("gffggfgg", "onBackPressed:..33333... ");

            folder_recycler_var.setVisibility(View.VISIBLE);
            folder_img_recycler_var.setVisibility(View.GONE);
            selected_rl_var.setVisibility(View.GONE);
            next_button_var.setVisibility(View.INVISIBLE);
            text_view_var.setText("Album");
        } else {
            Log.e("gffggfgg", "onBackPressed:..44444... ");
            startActivity(new Intent(Gallery_Act.this, Home_Actvty.class));
        }

    }

    @Override
    public void onResume() {
        Log.e("ag--12", "Gallery_Activity onResume : ");
        super.onResume();

        if (allAlbum.size() > 0) {
            folderDataMethod();
            lot_animation_var.setVisibility(View.GONE);
            return;
        } else if (allAlbum.size() == 0) {
            no_item_var.setVisibility(View.VISIBLE);
            lot_animation_var.setVisibility(View.GONE);
        }

        try {
            Home_Actvty.m_Refresh_var = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    Log.e("ag--12", "handleMessage 1 : " + msg.what);
                    Log.e("ag--12", "handleMessage 2 : " + allAlbum.size());
                    Log.e("ag--12", "handleMessage 3 : " + allFolder.size());
                    if (msg.what == 10) {
                        msg.what = 0;
                        if (allAlbum.size() > 0) {

                            folderDataMethod();

                            lot_animation_var.setVisibility(View.GONE);
                            folder_adap_var.notifyDataSetChanged();
                            folder_img_adap_var.notifyDataSetChanged();
                            selected_img_adap_var.notifyDataSetChanged();
                        } else if (allAlbum.size() == 0) {
                            no_item_var.setVisibility(View.VISIBLE);
                            lot_animation_var.setVisibility(View.GONE);
                        }
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onPause() {
        super.onPause();

    }

}