package com.status.videomaker.Fragmentss;

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

public class Video_Frame_Frag extends Fragment {
    private Video_Maker_Act activity_obj;
    private FrameAdapter adapter_FA;

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
        View inflate = layoutInflater.inflate(R.layout.fragmentt_framee, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rvFrame);
        this.adapter_FA = new FrameAdapter(this.activity_obj);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, 0, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(this.adapter_FA);
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


    public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.ViewHolder> {
        Video_Maker_Act activity;
        int oldPos = 0;
        private int[] frameList = {-1, R.drawable.fr22, R.drawable.fr23, R.drawable.fr24, R.drawable.fr25, R.drawable.fr26, R.drawable.fr27, R.drawable.fr28, R.drawable.fr1, R.drawable.fr2, R.drawable.fr3, R.drawable.fr4, R.drawable.fr5, R.drawable.fr6, R.drawable.fr7, R.drawable.fr8, R.drawable.fr9, R.drawable.fr10, R.drawable.fr11, R.drawable.fr12, R.drawable.fr13, R.drawable.fr14, R.drawable.fr15, R.drawable.fr16, R.drawable.fr17, R.drawable.fr18, R.drawable.fr19, R.drawable.fr20, R.drawable.fr21};
        private LayoutInflater inflater;
        private MainApplication mainApplication;

        FrameAdapter(Video_Maker_Act videoEditActivity) {
            this.activity = videoEditActivity;
            this.mainApplication = MainApplication.getInstance();
            this.inflater = LayoutInflater.from(this.activity);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(this.inflater.inflate(R.layout.framee_itemm, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

            final int item = getItem(i);


            if (i == 0) {
                Glide.with(this.mainApplication).load(Integer.valueOf(item)).into(viewHolder.ivThumb);
            } else {
                viewHolder.ivThumb.setImageResource(item);
            }
            Glide.with(this.mainApplication).load(Integer.valueOf((int) R.drawable.frame_thumb)).into(viewHolder.ivBg);
            viewHolder.ivThumb.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    if (item != FrameAdapter.this.activity.get_Frame_Method()) {
                        Video_Maker_Act.select_Frame_Position = i;
                        FrameAdapter.this.activity.set_Frame_View_Method(item);
                        if (item != -1) {
                            FrameAdapter frameAdapter = FrameAdapter.this;
                            frameAdapter.notifyItemChanged(frameAdapter.oldPos);
                            FrameAdapter.this.notifyItemChanged(i);
                            FrameAdapter.this.oldPos = i;
                            FileUtils.deleteFile(FileUtils.frameFile);
                        }
                        FrameAdapter.this.notifyDataSetChanged();
                    }
                }
            });
            if (Video_Maker_Act.select_Frame_Position == i) {
                viewHolder.ivSelect.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivSelect.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return this.frameList.length;
        }

        public int getItem(int i) {
            return this.frameList[i];
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
