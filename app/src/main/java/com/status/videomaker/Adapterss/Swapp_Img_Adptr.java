package com.status.videomaker.Adapterss;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess.Gallery_Act;
import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.Activitiess.Swapp_Images_Actvty;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.R;
import com.status.videomaker.utilss.Utils_class;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Swapp_Img_Adptr extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Activity activity_obj;
    public MainApplication application_obj = MainApplication.getInstance();
    private LayoutInflater layoutInflater;
    private RequestManager glide_obj;
    private int m_add_pos;

    public Swapp_Img_Adptr(Activity activity2) {
        this.layoutInflater = LayoutInflater.from(activity2);
        this.glide_obj = Glide.with(activity2);
        this.activity_obj = activity2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("ag--12", " onCreateViewHolder:position : " + viewType);

        if (viewType == 2) {
            return new Swapp_Img_Adptr.AddHolder(this.layoutInflater.inflate(R.layout.swap_image_item_add, parent, false));
        } else {
            View v = this.layoutInflater.inflate(R.layout.swapp_imagee_itemm, parent, false);
            Holder holder = new Holder(v);
            if (getItemViewType(viewType) == 1) {
                v.setVisibility(View.INVISIBLE);
            } else {
                v.setVisibility(View.VISIBLE);
            }
            return holder;
        }
    }

    public Folder_Image_Album_Model getItem(int pos) {
        ArrayList<Folder_Image_Album_Model> list = this.application_obj.getSelectedImages();
        Log.e("ag--12", "getItem : " + list);
        if (list.size() <= pos) {
            return new Folder_Image_Album_Model();
        }
        return list.get(pos);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, @SuppressLint("RecyclerView") int position) {
        if (holder1 instanceof Swapp_Img_Adptr.Holder) {
            Swapp_Img_Adptr.Holder holder = (Swapp_Img_Adptr.Holder) holder1;
            holder.parent.setVisibility(View.VISIBLE);

            this.glide_obj.load(getItem(position)._image_Path_).into(holder.ivThumb);
            if (getItemCount() <= 2) {
                holder.ivRemove.setVisibility(View.GONE);
            } else {
                holder.ivRemove.setVisibility(View.VISIBLE);
            }

            holder.ivRemove.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Swapp_Img_Adptr.this.application_obj.min_pos = Math.min(Swapp_Img_Adptr.this.application_obj.min_pos, Math.max(0, position - 1));
                    Log.e("ag--12", "getItem 2 : " + application_obj.selectedImages.size());

                    MainApplication.isBreak = true;
                    Swapp_Img_Adptr.this.application_obj.removeSelectedImage(position);
                    Swapp_Img_Adptr.this.notifyDataSetChanged();
                    Gallery_Act.folder_img_adap_var.notifyDataSetChanged();
                }
            });

            holder.ivEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(Swapp_Img_Adptr.this.activity_obj, EditImageActivity.class);
                    Utils_class.temp_File_STR = Swapp_Img_Adptr.this.getItem(position).get_image_Path_();
                    i.putExtra("editImage", Swapp_Img_Adptr.this.getItem(position).get_image_Path_());
                    i.putExtra("position", position);
                    Swapp_Img_Adptr.this.activity_obj.startActivityForResult(new Intent(i), 100);
                }
            });

        } else if (holder1 instanceof Swapp_Img_Adptr.AddHolder) {
            Swapp_Img_Adptr.AddHolder holderadd = (Swapp_Img_Adptr.AddHolder) holder1;
            holderadd.ad_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Swapp_Img_Adptr.this.activity_obj, Gallery_Act.class);
                    Swapp_Img_Adptr.this.activity_obj.startActivity(intent);
                    Swapp_Images_Actvty.boolean_swapp_img_back = true;
                    Log.e("ag--12", " onClick :  position : " + position);
                }
            });
        }
    }

    public synchronized void swap_method(int fromPosition, int toPosition) {
        try {
            Collections.swap(this.application_obj.getSelectedImages(), fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @Override
    public int getItemCount() {
        if (this.application_obj.getSelectedImages().size() < 20) {
            m_add_pos = this.application_obj.getSelectedImages().size() + 1;
            return m_add_pos;
        }
        return this.application_obj.getSelectedImages().size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (!this.application_obj.getSelectedImages().isEmpty()) {
            if (this.application_obj.getSelectedImages().size() < 20) {
                int ab = this.application_obj.getSelectedImages().size();
                if (ab == position) {
                    Log.e("ag--12", " ab : " + ab + " position : " + position);
                    return 2;
                }
            }
            return 1;
        } else {
            return 1;
        }
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView ivRemove;
        public ImageView ivThumb;
        RelativeLayout ivEdit;
        View parent;
        private View clickableView;

        Holder(View view) {
            super(view);
            this.parent = view;
            this.ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
            this.ivRemove = (ImageView) view.findViewById(R.id.ivRemove);
            this.clickableView = view.findViewById(R.id.clickableView);
            this.ivEdit = (RelativeLayout) view.findViewById(R.id.ivEdit);
        }
    }

    public class AddHolder extends RecyclerView.ViewHolder {
        RelativeLayout ad_Image;

        AddHolder(View view) {
            super(view);
            this.ad_Image = (RelativeLayout) view.findViewById(R.id.ad_Image);
        }
    }
}
