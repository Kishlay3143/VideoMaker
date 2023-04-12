package com.status.videomaker.Fragmentss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.Activitiess.Video_Maker_Act;
import com.status.videomaker.R;
import com.status.videomaker.videolib.libffmpeg.FileUtils;
import com.bumptech.glide.Glide;

public class Video_Filter_Frag extends Fragment {
    private Video_Maker_Act activity_obj;

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
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragmentt_filterr, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rvFrame);
        Effectt_Adptr effecttAdptr = new Effectt_Adptr(this.activity_obj);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, 0, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(effecttAdptr);
        return inflate;
    }

    @Override
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (z) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
    }

    public class Effectt_Adptr extends RecyclerView.Adapter<Effectt_Adptr.ViewHolder> {
        Video_Maker_Act activity_obj;
        int old_pos1_var = 0;
        private int[] frame_List1_array = {-1, R.drawable.e1, R.drawable.e2, R.drawable.e3, R.drawable.e4, R.drawable.e5, R.drawable.e6, R.drawable.e7, R.drawable.e8, R.drawable.e9, R.drawable.e10, R.drawable.e11, R.drawable.e12, R.drawable.e13, R.drawable.e14, R.drawable.e15, R.drawable.e16};
        private LayoutInflater inflater_LI;
        private MainApplication main_Application_obj;

        Effectt_Adptr(Video_Maker_Act videoEditActivity) {
            this.activity_obj = videoEditActivity;
            this.main_Application_obj = MainApplication.getInstance();
            this.inflater_LI = LayoutInflater.from(this.activity_obj);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(this.inflater_LI.inflate(R.layout.filterr_itemm, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
            final int i2 = this.frame_List1_array[i];
            if (i == 0) {
                Glide.with(this.main_Application_obj).load(Integer.valueOf(this.frame_List1_array[i])).into(viewHolder.ivThumb);
            } else {
                viewHolder.ivThumb.setImageResource(this.frame_List1_array[i]);
            }
            Glide.with(this.main_Application_obj).load(Integer.valueOf((int) R.drawable.frame_thumb)).into(viewHolder.ivBg);
            viewHolder.ivThumb.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    if (i2 != Effectt_Adptr.this.activity_obj.get_Selected_Effect_Position()) {
                        Video_Maker_Act.select_Filters_Position = i;
                        Effectt_Adptr.this.activity_obj.set_Effect_View_Method(i2);
                        if (i2 != -1) {
                            Effectt_Adptr effecttAdptr = Effectt_Adptr.this;
                            effecttAdptr.notifyItemChanged(effecttAdptr.old_pos1_var);
                            Effectt_Adptr.this.notifyItemChanged(i);
                            Effectt_Adptr.this.old_pos1_var = i;
                            FileUtils.deleteFile(FileUtils.effectFile);
                        }
                        Effectt_Adptr.this.notifyDataSetChanged();
                    }
                }
            });
            if (Video_Maker_Act.select_Filters_Position == i) {
                viewHolder.ivSelect.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivSelect.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return this.frame_List1_array.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivBg;
            ImageView ivSelect;
            ImageView ivThumb;

            ViewHolder(@NonNull View view) {
                super(view);
                this.ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
                this.ivSelect = (ImageView) view.findViewById(R.id.iv_select);
                this.ivBg = (ImageView) view.findViewById(R.id.ivBg);
            }
        }
    }
}
