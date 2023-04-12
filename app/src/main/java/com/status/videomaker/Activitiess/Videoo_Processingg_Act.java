package com.status.videomaker.Activitiess;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.status.videomaker.InterFacess.OnProgressReceiver;
import com.status.videomaker.R;

public class Videoo_Processingg_Act extends AppCompatActivity implements OnProgressReceiver {
    public String newPath_str;
    float last_Prog_var = 0.0f;
    ImageView back_Button_IV;
    Dialog dialog_obj;
    boolean video_done_bool;
    boolean pause_bool;
    TextView yess_TV;
    LinearLayout ll_ok_LL;
    RelativeLayout ll_progress_RL;
    String txt_str;
    private MainApplication application_MA;
    private ProgressBar m_progressbar_obj;
    private TextView progress_TV;

    private void bindView() {
    }

    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activityy_videoo_processingg);
        this.application_MA = MainApplication.getInstance();

        bindView();
        init();
        dialog_obj = new Dialog(Videoo_Processingg_Act.this);
        dialog_obj.setContentView(R.layout.videoo_donee_dialogg);
        dialog_obj.getWindow().setGravity(Gravity.CENTER);
        dialog_obj.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_obj.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_obj.setCancelable(false);


    }

    private void init() {
        m_progressbar_obj = (ProgressBar) findViewById(R.id.uploadProgressBar);
        progress_TV = (TextView) findViewById(R.id.txt_upload_progress);
        ll_progress_RL = (RelativeLayout) findViewById(R.id.llprogress);
        back_Button_IV = (ImageView) findViewById(R.id.backButton);

        back_Button_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void done_video_Method(String str) {
        yess_TV = (TextView) dialog_obj.findViewById(R.id.iv_add);
        ll_ok_LL = (LinearLayout) dialog_obj.findViewById(R.id.ll_ok);

        ll_ok_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Videoo_Processingg_Act.this, Share_Actvty.class);
                intent.putExtra("File_Path", str);
                intent.putExtra("value", 100);
                startActivity(intent);
                finish();
            }
        });
    }

    public void show_Finish_Message_Method(String str) {
        video_done_bool = true;
        Toast.makeText(this, "Video Created Successfully", Toast.LENGTH_SHORT).show();
        if (!pause_bool) {
            String ss = "LoadVideoActivity";
            Intent intent = new Intent(Videoo_Processingg_Act.this, Share_Actvty.class);
            intent.putExtra("File_Path", str);
            intent.putExtra("value", 100);
            startActivity(intent);
            finish();
        }
    }

    public synchronized void change_Bg_Color_Method(float f) {
        TextView textView = this.progress_TV;
        StringBuilder sb = new StringBuilder();
        int i = (int) f;
        sb.append(i);
        sb.append("%");
        textView.setText(sb.toString());
        this.m_progressbar_obj.setProgress(i);
        this.last_Prog_var = f;
        textView = this.progress_TV;
        txt_str = sb.toString();
    }

    public void onImageProgressFrameUpdate(final float f) {
        if (this.m_progressbar_obj != null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Videoo_Processingg_Act.this.change_Bg_Color_Method((f * 25.0f) / 100.0f);
                }
            });
        }
    }

    @Override
    public void onProgressFinish(String str) {
        this.newPath_str = str;
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(str)));
        runOnUiThread(new Runnable() {
            public void run() {
                ll_progress_RL.setVisibility(View.INVISIBLE);
                done_video_Method(newPath_str);
                show_Finish_Message_Method(newPath_str);
            }
        });
    }

    public void onVideoProgressFrameUpdate(final float f) {
        if (this.m_progressbar_obj != null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Videoo_Processingg_Act.this.change_Bg_Color_Method(((f * 75.0f) / 100.0f) + 25.0f);
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        pause_bool = false;


        if (video_done_bool && dialog_obj != null) {
            dialog_obj.show();
            video_done_bool = false;
        }

        this.application_MA.setOnProgressReceiver(this);



    }

    @Override
    protected void onPause() {
        super.onPause();
        pause_bool = true;


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Videoo_Processingg_Act.this, "Please wait untill Video is preparing", Toast.LENGTH_SHORT).show();
    }
}
