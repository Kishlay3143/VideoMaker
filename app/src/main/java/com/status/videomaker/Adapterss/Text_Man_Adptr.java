package com.status.videomaker.Adapterss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.R;

public class Text_Man_Adptr extends RecyclerView.Adapter<Text_Man_Adptr.MyViewHolder> {
    private LayoutInflater inflater_obj;
    private int last_clicked_var;
    private Context listener_context;
    private onRecyclerViewClicked on_recycler_view_clicked;
    private String[] text_str_array;

    public interface onRecyclerViewClicked {
        void onTextClicked(int i);
    }

    public Text_Man_Adptr(Context context, String[] strArr, int i, onRecyclerViewClicked onrecyclerviewclicked) {
        this.text_str_array = strArr;
        this.listener_context = context;
        this.inflater_obj = LayoutInflater.from(context);
        this.last_clicked_var = i;
        this.on_recycler_view_clicked = onrecyclerviewclicked;
    }


    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(this.inflater_obj.inflate(R.layout.recyclee_layoutt_textt, (ViewGroup) null));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint({"RecyclerView"}) final int i) {
        myViewHolder.textView.setTypeface(Typeface.createFromAsset(this.listener_context.getAssets(), this.text_str_array[i]));
        if (this.last_clicked_var == i) {
            myViewHolder.textView.setBackgroundResource(R.drawable.bg_btn);
        } else {
            myViewHolder.textView.setBackgroundResource(R.drawable.textview_unsalect);
        }
        myViewHolder.textView.setText("Abc");
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Text_Man_Adptr.this.last_clicked_var = i;
                Text_Man_Adptr.this.on_recycler_view_clicked.onTextClicked(i);
                Text_Man_Adptr.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.text_str_array.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        MyViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.effect_text);
            this.textView.setBackgroundResource(R.drawable.ic_sb_play_circle);
        }
    }
}
