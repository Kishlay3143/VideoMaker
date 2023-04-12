package com.status.videomaker.Fragmentss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.Activitiess.Video_Maker_Act;
import com.status.videomaker.R;
import com.status.videomaker.Servicess.Imagee_Create_Service;
import com.status.videomaker.utilss.THEMES_Video_class;
import com.status.videomaker.videolib.libffmpeg.FileUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

public class Video_Transition_Frag extends Fragment {
    FrameLayout ad_container_FL;
    private Video_Maker_Act activity_obj;
    private MainApplication main_application_obj;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity_obj = (Video_Maker_Act) context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_transition, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_themes);
        this.ad_container_FL = (FrameLayout) inflate.findViewById(R.id.ads_container);

        this.main_application_obj = MainApplication.getInstance();
        Theme_Adptr_ themeAdptr = new Theme_Adptr_(this.activity_obj);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(themeAdptr);
        return inflate;
    }

    public class Theme_Adptr_ extends RecyclerView.Adapter<Theme_Adptr_.ViewHolder> {
        public MainApplication application_obj = MainApplication.getInstance();
        Video_Maker_Act activity_obj;
        LayoutInflater layoutInflater_obj;
        ArrayList<THEMES_Video_class> list_AL = new ArrayList<>(Arrays.asList(THEMES_Video_class.values()));

        Theme_Adptr_(Video_Maker_Act videoEditActivity) {
            this.activity_obj = videoEditActivity;
            this.layoutInflater_obj = LayoutInflater.from(videoEditActivity);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(this.layoutInflater_obj.inflate(R.layout.transitionn_itemm, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
            THEMES_Video_class themesVideo = this.list_AL.get(i);
            Glide.with((Context) this.application_obj).load(Integer.valueOf(themesVideo.getThemeDrawable())).into(viewHolder.ivThumb);

            viewHolder.tvThemeName.setText(themesVideo.toString());
            viewHolder.cbSelect.setChecked(themesVideo == this.application_obj.selectedTheme);

            if (Video_Maker_Act.select_Transition_Position == i) {
                viewHolder.bgbg.setBackgroundResource(R.color.colorAccent);
            } else {
                viewHolder.bgbg.setBackgroundResource(R.color.black);
            }

            viewHolder.clickableView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (Theme_Adptr_.this.list_AL.get(i) != Theme_Adptr_.this.application_obj.selectedTheme) {
                        Video_Maker_Act.select_Transition_Position = i;
                        Theme_Adptr_ themeAdptr = Theme_Adptr_.this;
                        themeAdptr.delete_Theme_Dir_method(themeAdptr.application_obj.selectedTheme.toString());
                        Theme_Adptr_.this.application_obj.videoImages.clear();
                        Theme_Adptr_.this.application_obj.selectedTheme = (THEMES_Video_class) Theme_Adptr_.this.list_AL.get(i);
                        Theme_Adptr_.this.application_obj.setCurrentTheme(Theme_Adptr_.this.application_obj.selectedTheme.toString());
                        Theme_Adptr_.this.activity_obj.reset_Method();
                        Intent intent = new Intent(Theme_Adptr_.this.application_obj, Imagee_Create_Service.class);
                        intent.putExtra(Imagee_Create_Service.EXTRAAA_SELECTED_THEME_STR, Theme_Adptr_.this.application_obj.getCurrentTheme());
                        Theme_Adptr_.this.application_obj.startService(intent);
                        Theme_Adptr_.this.notifyDataSetChanged();
                    }
                }
            });
        }

        public void delete_Theme_Dir_method(final String str) {
            new Thread() {
                public void run() {
                    FileUtils.deleteThemeDir(str);
                }
            }.start();
        }

        @Override
        public int getItemCount() {
            return this.list_AL.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvThemeName;
            CheckBox cbSelect;
            View clickableView;
            ImageView ivThumb;
            RelativeLayout bgbg;

            ViewHolder(@NonNull View view) {
                super(view);
                this.cbSelect = (CheckBox) view.findViewById(R.id.cbSelect);
                this.ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
                this.clickableView = view.findViewById(R.id.clickableView);
                this.tvThemeName = (TextView) view.findViewById(R.id.tvThemeName);
                this.bgbg = (RelativeLayout) view.findViewById(R.id.bgh);
            }
        }
    }
}
