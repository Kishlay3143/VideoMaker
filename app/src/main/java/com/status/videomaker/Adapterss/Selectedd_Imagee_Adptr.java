package com.status.videomaker.Adapterss;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess.Gallery_Act;
import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.InterFacess.OnItemClickListner;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class Selectedd_Imagee_Adptr extends RecyclerView.Adapter<Selectedd_Imagee_Adptr.Holder> {
    public Gallery_Act activity_obj;
    public MainApplication application_obj;
    public OnItemClickListner<Object> click_Listner_obj;
    public boolean is_expanded_bool = false;
    private RequestManager glide_obj;
    private LayoutInflater inflater_obj;

    public Selectedd_Imagee_Adptr(Gallery_Act activity2) {
        this.activity_obj = activity2;
        this.application_obj = MainApplication.getInstance();
        this.inflater_obj = LayoutInflater.from(activity2);
        this.glide_obj = Glide.with((FragmentActivity) activity2);
    }

    @Override
    public int getItemCount() {
        ArrayList<Folder_Image_Album_Model> list = this.application_obj.getSelectedImages();
        if (this.is_expanded_bool) {
            return list.size();
        }
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        if (this.is_expanded_bool || position < this.application_obj.getSelectedImages().size()) {
            return 0;
        }
        return 1;
    }

    public boolean hide_remove_btn_method() {
        return this.application_obj.getSelectedImages().size() <= 3 && this.activity_obj.is_from_preview_var;
    }

    private Folder_Image_Album_Model get_item_method(int pos) {
        ArrayList<Folder_Image_Album_Model> list = this.application_obj.getSelectedImages();
        if (list.size() <= pos) {
            return new Folder_Image_Album_Model();
        }
        return list.get(pos);
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") final int pos) {
        if (getItemViewType(pos) == 1) {
            holder.parent.setVisibility(4);
            return;
        }
        holder.parent.setVisibility(0);
        final Folder_Image_Album_Model data = get_item_method(pos);
        this.glide_obj.load(data._image_Path_).into(holder.ivThumb);

        if (hide_remove_btn_method()) {
            holder.ivRemove.setVisibility(8);
        } else {
            holder.ivRemove.setVisibility(0);
        }

        holder.ivRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Selectedd_Imagee_Adptr.this.application_obj.getSelectedImages().indexOf(data);

                if (Selectedd_Imagee_Adptr.this.activity_obj.is_from_preview_var) {
                    Selectedd_Imagee_Adptr.this.application_obj.min_pos = Math.min(Selectedd_Imagee_Adptr.this.application_obj.min_pos, Math.max(0, pos - 1));
                }
                Selectedd_Imagee_Adptr.this.application_obj.removeSelectedImage(pos);
                if (Selectedd_Imagee_Adptr.this.click_Listner_obj != null) {
                    Selectedd_Imagee_Adptr.this.click_Listner_obj.onItemClick(v, data);
                }
                if (Selectedd_Imagee_Adptr.this.hide_remove_btn_method()) {
                    Toast.makeText(Selectedd_Imagee_Adptr.this.activity_obj, "At least 3 images require \nif you want to remove this images than add more images.", 1).show();
                }
                Selectedd_Imagee_Adptr.this.notifyDataSetChanged();
                Gallery_Act.folder_img_adap_var.notifyDataSetChanged();
            }
        });
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int pos) {
        View v = this.inflater_obj.inflate(R.layout.selectedd_imagee_itemm, parent, false);
        Holder holder = new Holder(v);
        if (getItemViewType(pos) == 1) {
            v.setVisibility(View.GONE);
        } else {
            v.setVisibility(View.VISIBLE);
        }
        return holder;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView ivRemove;
        public ImageView ivThumb;
        View parent;

        Holder(View v) {
            super(v);
            this.parent = v;
            this.ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
            this.ivRemove = (ImageView) v.findViewById(R.id.ivRemove);
        }

        public void onItemClick(View view, Object item) {
            if (Selectedd_Imagee_Adptr.this.click_Listner_obj != null) {
                Selectedd_Imagee_Adptr.this.click_Listner_obj.onItemClick(view, item);
            }
        }
    }
}
