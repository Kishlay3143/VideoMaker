package com.status.videomaker.Activitiess;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.status.videomaker.R;


public class Permission_Activity extends AppCompatActivity {


    RelativeLayout permission_RL;

    private boolean opendialog = false;
    private Dialog req_permission, required_dialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_permissionn);
        percnt = 0;

        permission_RL=findViewById(R.id.iv_permission);


        permission_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (permissionAlreadyGranted()) {
                    inittt();
                    return;
                } else {
                    requestPermission();

                }


            }
        });
    }

    private void inittt() {

        startActivity(new Intent(Permission_Activity.this, Home_Actvty.class));
        finish();
    }

    public static int percnt = 0;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1011) {
            if (grantResults.length == 0) {
                return;
            } else {
                if (Build.VERSION.SDK_INT >= 33) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED
                            && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                        startActivity(new Intent(Permission_Activity.this, Home_Actvty.class));
                        finish();

                    } else {
                        boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES);
                        boolean showRationale1 = shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_VIDEO);

                        boolean showRationale5 = shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS);

                        if (!showRationale && !showRationale1 && !showRationale5) {
                            openSettingsDialog();
                        } else {
                            requestPermission();
                        }
                    }
                } else {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        startActivity(new Intent(Permission_Activity.this, Home_Actvty.class));
                        finish();

                    } else {
                        boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        boolean showRationale1 = shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);

                        if (!showRationale && !showRationale1) {
                            openSettingsDialog();
                        } else {
                            requestPermission();
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (permissionAlreadyGranted()) {
                startActivity(new Intent(Permission_Activity.this, Home_Actvty.class));
                finish();
            } else {
                requestPermission();
            }
        }
    }


    private void openSettingsDialog() {


        req_permission = new Dialog(Permission_Activity.this);
        req_permission.setContentView(R.layout.req_permission);
        req_permission.getWindow().setBackgroundDrawableResource(17170445);
        req_permission.getWindow().setLayout(-1, -1);
        req_permission.setCancelable(false);

        TextView setting = req_permission.findViewById(R.id.settings);
        TextView cancel = req_permission.findViewById(R.id.cancel);


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                req_permission.cancel();
                importane = true;
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                req_permission.cancel();
                inittt();

            }
        });
        req_permission.show();


    }

    public boolean importane = false;

    public boolean permissionAlreadyGranted() {
        Log.e("vvvv...per", "permissionAlreadyGranted ::   ");


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

    private void requestPermission() {
        Log.e("vvvv...per", "requestPermission ::   ");

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


    @SuppressLint("LongLogTag")
    @Override
    public void onBackPressed() {

            inittt();


    }
    @SuppressLint("LongLogTag")
    @Override
    protected void onResume() {
        super.onResume();

        if (opendialog) {
            opendialog = false;
            if (permissionAlreadyGranted()) {
                startActivity(new Intent(Permission_Activity.this, Home_Actvty.class));
                finish();
            } else {
                requestPermission();
            }
        }

    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onPause() {
        super.onPause();



    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}