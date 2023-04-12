package com.status.videomaker.Adapterss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess.Gallery_Act;
import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.InterFacess.OnItemClickListner;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class Folderr_Adptr extends RecyclerView.Adapter<Folderr_Adptr.Holder> {
    public MainApplication application_instance = MainApplication.getInstance();
    public OnItemClickListner<Object> click_listner_obj;
    private ArrayList<String> folder_Id_ArrayList = new ArrayList(this.application_instance.allFolder);

    private boolean is_Checked_In_bool = false;
    private RequestManager glide_obj;
    private LayoutInflater inflater_obj;
    private int check_Position_var = 0;

    public Folderr_Adptr(Context activity) {
        this.glide_obj = Glide.with(activity);

        if (this.folder_Id_ArrayList.size() > 0) {
            this.application_instance.setSelectedFolderId(this.folder_Id_ArrayList.get(0));
        }
        this.inflater_obj = LayoutInflater.from(activity);
    }

    public void setOnItemClickListner(OnItemClickListner<Object> clickListner2) {
        this.click_listner_obj = clickListner2;
    }

    @Override
    public int getItemCount() {
        return this.folder_Id_ArrayList.size();
    }

    private String getItem(int pos) {
        return this.folder_Id_ArrayList.get(pos);
    }

    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") int pos) {
        String currentFolderId = "";
        Folder_Image_Album_Model data = null;



        try {
            currentFolderId = getItem(pos);
            data = this.application_instance.getImageByAlbum(currentFolderId).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null) {
            return;
        }

        holder.textView.setSelected(true);
        holder.textView.setText(data.folder_Name_str);
        if (data.folder_Name_str == null) {
            holder.textView.setText("All");
        }

        this.glide_obj.load(data._image_Path_).into(holder.imageView);
        holder.textView2.setSelected(true);
        String str = String.valueOf(this.application_instance.getImageByAlbum(currentFolderId).size());
        holder.textView2.setText(str + " Files");

        String finalCurrentFolderId = currentFolderId;
        Folder_Image_Album_Model finalData = data;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Folderr_Adptr.this.application_instance.setSelectedFolderId(finalCurrentFolderId);
                set_Check_button_Method(pos, true);
                check_Position_var = pos;
                if (Folderr_Adptr.this.click_listner_obj != null) {
                    Folderr_Adptr.this.click_listner_obj.onItemClick(view, finalData);
                    Gallery_Act.text_view_var.setText(finalData.folder_Name_str);

                    if (finalData.folder_Name_str == null) {
                        Gallery_Act.text_view_var.setText("All");
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    public void set_Check_button_Method(int i, boolean check) {
        is_Checked_In_bool = check;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int pos) {
        return new Holder(this.inflater_obj.inflate(R.layout.folder_item, parent, false));
    }

    public class Holder extends RecyclerView.ViewHolder {
        View parent;
        ImageView imageView;
        TextView textView;
        TextView textView2;

        Holder(View v) {
            super(v);
            this.parent = v;
            this.imageView = v.findViewById(R.id.imageView);
            this.textView = v.findViewById(R.id.textView1);
            this.textView2 = v.findViewById(R.id.textView2);
        }

        public void onItemClick(View view, Object item) {
            if (Folderr_Adptr.this.click_listner_obj != null) {
                Folderr_Adptr.this.click_listner_obj.onItemClick(view, item);
            }
        }
    }
}
