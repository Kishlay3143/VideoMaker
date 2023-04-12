package com.status.videomaker.Activitiess;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.Manifest;


import android.provider.Settings;

import android.util.Log;

import android.view.Gravity;
import android.view.View;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.status.videomaker.Modelss.All_Music_Data_Model;
import com.status.videomaker.R;
import com.status.videomaker.dialog.Dialog_RateApp;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;

public class Home_Actvty extends AppCompatActivity {
    public static Handler m_Refresh_var = new Handler();
    public static boolean check_var = false;
    MainApplication application_var;
    int variable_var;
    LinearLayout gallery_iv;
    LinearLayout creation_iv;
    LinearLayout share_app_ll;
    LinearLayout rate_app_ll;
    LinearLayout privacy_ll;
    AppUpdateManager appp_date_manager_var;
    RelativeLayout load_data_rl;
    ProgressBar progress_pbar;
    InstallStateUpdatedListener listener = state -> {
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            long bytesDownloaded = state.bytesDownloaded();
            long totalBytesToDownload = state.totalBytesToDownload();
        }
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdateMethod();
        }
    };


    public static int spshowTotalHome;
    public static boolean appStart;
    public static SharedPreferences sp_prefsHome;
    public static SharedPreferences.Editor sp_editorHome;

    public static boolean home_click_var = false;
    public Dialog setting_dialog_var;
    public Dialog permission_dialog_var;

    private boolean opendialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_homee);

        Log.e("ag--12", "onCreate: ");

        gallery_iv = findViewById(R.id.iv_create);
        creation_iv = findViewById(R.id.iv_creation);
        share_app_ll = findViewById(R.id.iv_share);
        rate_app_ll = findViewById(R.id.iv_rate);
        privacy_ll = findViewById(R.id.iv_privacy);
        load_data_rl = findViewById(R.id.rl_load_data);
        progress_pbar = findViewById(R.id.progress_h);
        application_var = MainApplication.getInstance();

        imageMethod();

        exitDialog();



        new com.status.videomaker.Activitiess.TestAsync(Home_Actvty.this).execute();


        //===================== Rate Dialog===================

        if (spshowTotalHome == 2) {
            homeShowRating();
            new Dialog_RateApp(Home_Actvty.this).show();
        }

    }

    private void homeShowRating() {
        sp_prefsHome = getPreferences(Context.MODE_PRIVATE);
        sp_editorHome = sp_prefsHome.edit();
        spshowTotalHome = sp_prefsHome.getInt("counterHome", 0);
        spshowTotalHome++;
        sp_editorHome.putInt("counterHome", spshowTotalHome);
        sp_editorHome.commit();
    }

    private void imageMethod() {
        gallery_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (permissionAlreadyGrantedMethod()) {

                    All_Music_Data_Model selectedAllMusicDataModel = new All_Music_Data_Model();
                    MainApplication.isBreak = false;
                    MainApplication.getInstance().setMusicData(selectedAllMusicDataModel);
                    application_var.frame = -1;
                    application_var.frameStickerPic = -1;
                    application_var.second = 3.0f;
                    variable_var = 2;

                    Home_Actvty.this.application_var.getSelectedImages().clear();
                    startActivity(new Intent(getApplicationContext(), Gallery_Act.class));

                } else {
                    requestPermissionMethod();
                }


            }
        });

        creation_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (permissionAlreadyGrantedMethod()) {
                    startActivity(new Intent(getApplicationContext(), _Creationn_Activityy.class));
                } else {
                    requestPermissionMethod();
                }


            }
        });

        share_app_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage = getString(R.string.share_app) + "\n\n" + "https://play.google.com/store/apps/details?id=" + getPackageName();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                }
            }
        });

        rate_app_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        privacy_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://doc-hosting.flycricket.io/status-video-maker-privacy-policy/19dc31b0-66b8-4410-904a-86c0f14df423/privacy")));
                } catch (android.content.ActivityNotFoundException e) {
                }
            }
        });
    }



    @Override
    public void onBackPressed() {


        exitDialog_show();


    }

    //    ==================================exit inter==================================


    public static final String PLAY_STORE_LINK1 = "market://details?id=";
    public static final String PLAY_STORE_LINK2 = "http://play.google.com/store/apps/details?id=";

    View dialogView;
    Dialog builder;
    public void exitDialog() {
        builder = new Dialog(this, R.style.BottomSheetDialogTheme);
        dialogView = getLayoutInflater().inflate(R.layout.exitt_dialogss, null);
        builder.setContentView(dialogView);
        builder.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        builder.getWindow().setGravity(Gravity.BOTTOM);
        builder.setCanceledOnTouchOutside(false);
        ImageView tvNo = dialogView.findViewById(R.id.no);
        TextView tvYes = dialogView.findViewById(R.id.yes);
        TextView rate = dialogView.findViewById(R.id.rate);

        tvNo.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                builder.dismiss();

            }
        });


        tvYes.setOnClickListener(new View.OnClickListener() {
            public final Dialog f_1;

            {
                this.f_1 = builder;
            }

            public final void onClick(View view) {

                builder.dismiss();
                finishAffinity();
            }
        });


        rate.setOnClickListener(new View.OnClickListener() {
            public final Dialog f_1;


            {
                this.f_1 = builder;
            }

            public final void onClick(View view) {
                String str = "android.intent.action.VIEW";
                builder.dismiss();
                try {
                    startActivity(new Intent(str, Uri.parse(PLAY_STORE_LINK1.concat(getPackageName()))));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(str, Uri.parse(PLAY_STORE_LINK2.concat(getPackageName()))));
                }
            }
        });
    }


    public void exitDialog_show() {

        if (builder != null && !builder.isShowing()) {
            builder.show();
        }

    }


    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (opendialog) {
            opendialog = false;
            if (permissionAlreadyGrantedMethod()) {

            } else {
                requestPermissionMethod();
            }
        }

        Log.e("ag--12", "onResume: ");


    }

    private void popupSnackbarForCompleteUpdateMethod() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.mainvie), "App Update Alomost done.", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", view -> appp_date_manager_var.completeUpdate());
        snackbar.setActionTextColor(getResources().getColor(R.color.purple_200));
        snackbar.show();
    }


    private boolean permissionAlreadyGrantedMethod() {
        if (Build.VERSION.SDK_INT >= 33) {
            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES);
            int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO);
            int result5 = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);

            if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {

            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        }
    }

    private void requestPermissionMethod() {

        if (Build.VERSION.SDK_INT >= 33) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_VIDEO)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.POST_NOTIFICATIONS}, 1011);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1011);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (permissionAlreadyGrantedMethod()) {

            } else {
                requestPermissionMethod();
            }
        }
    }

    private void openSettingsDialogMethod() {

        setting_dialog_var = new Dialog(Home_Actvty.this, R.style.dialog_theme1);
        setting_dialog_var.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setting_dialog_var.setContentView(R.layout.dialogg_requestt_permissionn);
        setting_dialog_var.setCanceledOnTouchOutside(false);
        setting_dialog_var.setCancelable(true);
        setting_dialog_var.show();

        TextView tv_cancel = (TextView) setting_dialog_var.findViewById(R.id.tv_cancel);
        TextView tv_setting = (TextView) setting_dialog_var.findViewById(R.id.tv_setting);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_dialog_var.cancel();
                finish();
            }
        });


        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting_dialog_var.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 1001);
            }
        });


    }

    // Notification Methods
    private void requestForSpecificPermission() {

        Log.e("yyyyyy", "onCreate:....notifaction...777777.. ");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
    }

    public void sendnoti() {
        int MyVersion = Build.VERSION.SDK_INT;
        Log.e("yyyyyy", "onCreate:....notifaction2..... ");

        Log.e("TAGrr", "sendnoti:--------MyVersion------- " + MyVersion);
        Log.e("TAGrr", "sendnoti:--------Build.VERSION_CODES.TIRAMISU------- " + Build.VERSION_CODES.TIRAMISU);
        if (MyVersion > Build.VERSION_CODES.S_V2) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            } else {
                homeShowRating();
                if (spshowTotalHome == 2) {
                    new Dialog_RateApp(Home_Actvty.this).show();
                }
            }
        } else {
            homeShowRating();
            if (spshowTotalHome == 2) {
                new Dialog_RateApp(Home_Actvty.this).show();
            }
        }
    }


    private boolean checkIfAlreadyhavePermission() {

        Log.e("yyyyyy", "checkIfAlreadyhavePermission:....notifaction33333 ");
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            Log.e("yyyyyy", "checkIfAlreadyhavePermission:....notifaction44444 ");

            return true;
        } else {
            Log.e("yyyyyy", "checkIfAlreadyhavePermission:....notifaction55555");
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1011) {
            if (grantResults.length == 0) {
                Toast.makeText(Home_Actvty.this, "Permission Granted Successfully", Toast.LENGTH_LONG).show();
                return;
            } else {
                if (Build.VERSION.SDK_INT >= 33) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED
                            && grantResults[2] == PackageManager.PERMISSION_GRANTED) {


                    } else {
                        boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES);
                        boolean showRationale1 = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_VIDEO);

                        boolean showRationale5 = shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS);

                        if (!showRationale && !showRationale1 && !showRationale5) {
                            openSettingsDialogMethod();
                        } else {
                            requestPermissionMethod();
                        }
                    }
                } else {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                    } else {
                        boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        boolean showRationale1 = shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);

                        if (!showRationale && !showRationale1) {
                            openSettingsDialogMethod();
                        } else {
                            requestPermissionMethod();
                        }
                    }
                }
            }
        }


    }

}