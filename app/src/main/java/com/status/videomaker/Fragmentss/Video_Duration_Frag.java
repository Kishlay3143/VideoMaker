package com.status.videomaker.Fragmentss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.Activitiess.Video_Maker_Act;
import com.status.videomaker.R;

public class Video_Duration_Frag extends Fragment {
    private Video_Maker_Act activity_obj;
    private Timerr_Adapterr adapter_TA;
    private Float[] time_Text_array = {Float.valueOf(1.5f), Float.valueOf(2.0f), Float.valueOf(2.5f), Float.valueOf(3.0f), Float.valueOf(3.5f), Float.valueOf(4.0f), Float.valueOf(5.0f)};

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
        View inflate = layoutInflater.inflate(R.layout.fragment_time, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_time);
        this.adapter_TA = new Timerr_Adapterr(getContext(), new Timerr_Adapterr.OnItemClickListener() {

            @Override
            public void OnItemClick(int i) {
                Video_Duration_Frag.this.adapter_TA.refresh_Position(i);
                Video_Duration_Frag.this.activity_obj.seconds_VAR = Video_Duration_Frag.this.time_Text_array[i].floatValue();
                Video_Duration_Frag.this.activity_obj.changeVideoDuration();
                Video_Maker_Act.seek_Bar_SB.setProgress(0);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(this.adapter_TA);
        return inflate;
    }

    public static class Timerr_Adapterr extends RecyclerView.Adapter<Timerr_Adapterr.ViewHolder> {
        OnItemClickListener onItem_Click_Listener;
        private Context context_obj;
        private String[] list_array;
        private int position_var = 0;

        Timerr_Adapterr(Context context2, OnItemClickListener onItemClickListener2) {
            this.context_obj = context2;
            this.list_array = new String[]{"1.5", "1.0", "0.5", "0", "-0.5", "-1.0", "-1.5"};
            this.onItem_Click_Listener = onItemClickListener2;
            set_position_method(MainApplication.getInstance().getSecond());
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timee_itemm, viewGroup, false));
        }

        @SuppressLint({"SetTextI18n"})
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            TextView textView = viewHolder.tvTime;
            textView.setText(this.list_array[i] + "" + this.context_obj.getResources().getString(R.string.second2));
            if (this.position_var == i) {
                viewHolder.ivCheck.setImageResource(R.drawable.bg_btn_dur);
            } else {
                viewHolder.ivCheck.setImageResource(R.drawable.duration_bg_unsalected);
            }
        }

        @Override
        public int getItemCount() {
            return this.list_array.length;
        }

        public void refresh_Position(int i) {
            this.position_var = i;
            notifyDataSetChanged();
        }

        private void set_position_method(float f) {
            double d = (double) f;
            if (d == 1.5d) {
                this.position_var = 0;
            } else if (d == 2.0d) {
                this.position_var = 1;
            } else if (d == 2.5d) {
                this.position_var = 2;
            } else if (d == 3.0d) {
                this.position_var = 3;
            } else if (d == 3.5d) {
                this.position_var = 4;
            } else if (d == 4.0d) {
                this.position_var = 5;
            } else if (d == 5.0d) {
                this.position_var = 6;
            }
        }

        public interface OnItemClickListener {
            void OnItemClick(int i);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivCheck;
            TextView tvTime;

            ViewHolder(@NonNull View view) {
                super(view);
                this.ivCheck = (ImageView) view.findViewById(R.id.iv_check);
                this.tvTime = (TextView) view.findViewById(R.id.tv_time);
                view.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        Timerr_Adapterr.this.onItem_Click_Listener.OnItemClick(ViewHolder.this.getAdapterPosition());
                    }
                });
            }
        }
    }
}
