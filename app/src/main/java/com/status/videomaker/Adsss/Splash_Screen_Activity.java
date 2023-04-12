package com.status.videomaker.Adsss;


import android.Manifest;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.status.videomaker.Activitiess.Home_Actvty;
import com.status.videomaker.Activitiess.Permission_Activity;
import com.status.videomaker.R;


public class Splash_Screen_Activity extends AppCompatActivity {

    RelativeLayout splash_RL;
    Handler mHandler_obj;
    Runnable runnable_obj;

    public int milli_sec_VAR = 2500;
    public static boolean is_network_no_bool = false;

    private int request_code_var = -1;
    private ActivityResultLauncher<Intent> result_handler_obj = null;


    public static Splash_Screen_Activity splash_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityy_splashh);
        is_network_no_bool = false;
        splash_activity = this;
        splash_RL = findViewById(R.id.splash);

        show_internet_dialog_method();


        if (is_connected_method(Splash_Screen_Activity.this)) {
            start_timer_method(milli_sec_VAR);
        } else {
            show_dialog_method();
        }
        try {
            try {
                NetworkRequest networkRequest = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    networkRequest = new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                            .build();
                }
                ConnectivityManager connectivityManager11 =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    connectivityManager11.requestNetwork(networkRequest, network_callback);
                }
            } catch (Exception e) {
                e.printStackTrace();
                registerForActivityResult();

            }
        } catch (Exception e) {
            e.printStackTrace();
            registerForActivityResult();

        }
    }

    private final void registerForActivityResult() {
        this.result_handler_obj = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback() {

                    public void onActivityResult(Object var1) {
                        this.onActivityResult((ActivityResult) var1);
                    }

                    public final void onActivityResult(ActivityResult result) {
                        Splash_Screen_Activity.this.onActivityResult(request_code_var, result.getResultCode(), result.getData());
                        request_code_var = -1;
                    }
                });

    }

    private ConnectivityManager.NetworkCallback network_callback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            Log.e("network", "onAvailable: " + network);

            if (!isFinishing()) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        dismiss_dialog_method();
                        is_showing_dismiss_bool = false;

                    }
                });

            }

        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            Log.e("network", "onLost: " + network);
            if (!isFinishing()) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("TAG", "showdialog");

                        show_dialog_method();

                    }
                });
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            final boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
            Log.e("network", "onCapabilitiesChanged: " + unmetered);
        }
    };

    AlertDialog alert_dialog_obj;
    boolean is_showing_dismiss_bool;

    private void show_internet_dialog_method() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.no_internettt, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alert_dialog_obj = builder.create();
        alert_dialog_obj.setCanceledOnTouchOutside(false);
        alert_dialog_obj.getWindow().setGravity(Gravity.CENTER);
        alert_dialog_obj.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert_dialog_obj.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView btn_wifi_on = dialogView.findViewById(R.id.wifi);
        ImageView close = dialogView.findViewById(R.id.img_close);
        TextView btn_mobile_data_on = dialogView.findViewById(R.id.data);


        btn_wifi_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert_dialog_obj.dismiss();
                turn_on_wifi_method(Splash_Screen_Activity.this);

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss_dialog_method();
                is_showing_dismiss_bool = false;
            }
        });

        btn_mobile_data_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert_dialog_obj.dismiss();
                turn_on_mobile_data_method(Splash_Screen_Activity.this);
            }
        });


        alert_dialog_obj.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.e("splashscreen", "pau_res:::isshowingdismiss: " + is_showing_dismiss_bool);



                if (!is_showing_dismiss_bool) {
                    is_showing_dismiss_bool = false;
                    if (is_connected_method(Splash_Screen_Activity.this)) {
                        start_timer_method(milli_sec_VAR);
                    } else {
                        Log.e("splashscreen", "alertDialog---mHandler------" + mHandler_obj);
                        Log.e("splashscreen", "alertDialog---runnable------" + runnable_obj);


                        if (mHandler_obj == null || runnable_obj == null) {
                            mHandler_obj = new Handler();
                            runnable_obj = new Runnable() {
                                @Override
                                public void run() {

                                    if (is_connected_method(Splash_Screen_Activity.this)) {
                                        Log.e("splashscreen", "alertDialog---33------" + runnable_obj);
                                        start_timer_method(milli_sec_VAR);
                                    } else {
                                        Log.e("splashscreen", "alertDialog---0000------" + runnable_obj);

                                        if (!isFinishing()) {
                                            Log.e("splashscreen", "alertDialog---1111------" + runnable_obj);

                                            show_dialog_method();
                                        }
                                    }
                                }
                            };
                        }
                        mHandler_obj.postDelayed(runnable_obj, 3000);

                    }
                }

            }
        });


    }


    public void dismiss_dialog_method() {
        if (alert_dialog_obj != null && alert_dialog_obj.isShowing()) {
            is_network_no_bool = false;
            alert_dialog_obj.dismiss();
            is_showing_dismiss_bool = true;
        }

    }


    public void turn_on_wifi_method(Context context) {
        try {
            context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, getString(R.string.itcannot), Toast.LENGTH_LONG).show();
        }
    }

    public void turn_on_mobile_data_method(Context context) {
        try {
            context.startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, R.string.itcannot, Toast.LENGTH_LONG).show();
        }
    }


    public boolean is_connected_method(Context context) {
        ConnectivityManager cm;
        NetworkInfo netInfo = null;
        try {
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            netInfo = cm.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void start_timer_method(int milli) {

        mHandler_obj = new Handler();
        runnable_obj = new Runnable() {
            @Override
            public void run() {

                if (is_connected_method(Splash_Screen_Activity.this)) {
                    Log.e("splashscreen", "alertDialog---33------" + runnable_obj);

                    start_main_activity_method();

                } else {
                    Log.e("splashscreen", "alertDialog---0000--0000----" + runnable_obj);

                    if (!isFinishing()) {
                        Log.e("splashscreen", "alertDialog---1111--111----" + runnable_obj);

                        show_dialog_method();
                    }
                }

            }
        };
        Log.e("splashscreen", "starttimer:::63636363::");

        mHandler_obj.postDelayed(runnable_obj, milli);
    }

    public void show_dialog_method() {
        if (alert_dialog_obj != null && !alert_dialog_obj.isShowing()) {
            is_network_no_bool = true;
            alert_dialog_obj.show();
        }
    }

    private void start_main_activity_method() {

        if (permission_already_Granted_method()) {
            startActivity(new Intent(Splash_Screen_Activity.this, Home_Actvty.class));
            finish();
        } else {

            startActivity(new Intent(Splash_Screen_Activity.this, Permission_Activity.class));
            finish();
        }
    }

    public static boolean permission_already_Granted_method() {
        if (Build.VERSION.SDK_INT >= 33) {
            int result = ContextCompat.checkSelfPermission(splash_activity, Manifest.permission.READ_MEDIA_IMAGES);
            int result1 = ContextCompat.checkSelfPermission(splash_activity, Manifest.permission.READ_MEDIA_VIDEO);
            int result5 = ContextCompat.checkSelfPermission(splash_activity, Manifest.permission.POST_NOTIFICATIONS);

            if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {

            int result = ContextCompat.checkSelfPermission(splash_activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(splash_activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}