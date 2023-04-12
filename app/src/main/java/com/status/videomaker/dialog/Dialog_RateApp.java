package com.status.videomaker.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.status.videomaker.R;



public class Dialog_RateApp extends Dialog {
    private final Context context;
    ImageView img_star1, img_star2, img_star3, img_star4, img_star5;
    ImageView tv_not;
    LinearLayout tv_ok;
    LinearLayout tv_share;
    SharedPreferences mSharedPrefs;
    TextView tv_review;
    String[] revire = new String[]{"Bad", "Not Good", "Okay", "Very Good", "Grate !!!"};
    int count;
    Animation objAnimation;

    Dialog dialog;


    @SuppressLint("ResourceType")
    public Dialog_RateApp(@NonNull Context context) {
        super(context, R.style.BottomSheetDialogTheme);
        this.context = context;
        setContentView(R.layout.dialogss_ratess);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        count = mSharedPrefs.getInt("SubmitClick", 0);
        objAnimation = AnimationUtils.loadAnimation(this.context, R.anim.viewpush);
        initView();
    }


    protected void initView() {

        img_star1 = (ImageView) findViewById(R.id.iv_star1);
        img_star2 = (ImageView) findViewById(R.id.iv_star2);
        img_star3 = (ImageView) findViewById(R.id.iv_star3);
        img_star4 = (ImageView) findViewById(R.id.iv_star4);
        img_star5 = (ImageView) findViewById(R.id.iv_star5);

        tv_review = (TextView) findViewById(R.id.tv_review);
        tv_review.setText(revire[2]);
        tv_not = (ImageView) findViewById(R.id.now);
        tv_ok = (LinearLayout) findViewById(R.id.ratenow);
        tv_share = (LinearLayout) findViewById(R.id.shareapp);


        img_star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_star();
                img_star1.setImageResource(R.drawable.star1);
            }
        });
        img_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_star();
                img_star1.setImageResource(R.drawable.star1);
                img_star2.setImageResource(R.drawable.star1);


            }
        });
        img_star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_star();
                img_star1.setImageResource(R.drawable.star1);
                img_star2.setImageResource(R.drawable.star1);
                img_star3.setImageResource(R.drawable.star1);

            }
        });
        img_star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_star();
                img_star1.setImageResource(R.drawable.star1);
                img_star2.setImageResource(R.drawable.star1);
                img_star3.setImageResource(R.drawable.star1);
                img_star4.setImageResource(R.drawable.star1);


            }
        });
        img_star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_star();
                img_star1.setImageResource(R.drawable.star1);
                img_star2.setImageResource(R.drawable.star1);
                img_star3.setImageResource(R.drawable.star1);
                img_star4.setImageResource(R.drawable.star1);
                img_star5.setImageResource(R.drawable.star1);
            }
        });


        tv_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share_App(context);
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count < 2) {
                    int cc = count;
                    cc++;
                    SharedPreferences.Editor edit = mSharedPrefs.edit();
                    edit.putInt("SubmitClick", cc);
                    edit.commit();
                }

                Toast.makeText(context,R.string.thanksforrating, Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent("android.intent.action.VIEW");
                intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=" +context.getPackageName()));
                context.startActivity(intent2);
                dismiss();
            }
        });
    }


    public static void Share_App(Context context) {

        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.sharecontent) +
                "\n\n" + "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, "Share"));
    }

    public void change_star() {
        img_star1.setImageResource(R.drawable.star2);
        img_star2.setImageResource(R.drawable.star2);
        img_star3.setImageResource(R.drawable.star2);
        img_star4.setImageResource(R.drawable.star2);
        img_star5.setImageResource(R.drawable.star2);
    }
}
