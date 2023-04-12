package com.status.videomaker.Adapterss;

import static com.status.videomaker.Activitiess.Gallery_Act.tv_count_var;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.InterFacess.OnItemClickListner;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class Folder_Image_Adapter extends RecyclerView.Adapter<Folder_Image_Adapter.Holder> {
    public MainApplication application = MainApplication.getInstance();
    public OnItemClickListner<Object> clickListner;
    int position = -1;
    private RequestManager glide;
    private LayoutInflater inflater;

    public Folder_Image_Adapter(Context activity) {
        this.inflater = LayoutInflater.from(activity);
        this.glide = Glide.with(activity);
    }
    public void setOnItemClickListner(OnItemClickListner<Object> clickListner2) {
        this.clickListner = clickListner2;
    }

    @Override
    public int getItemCount() {
        MainApplication mainApplication = this.application;
        return mainApplication.getImageByAlbum(mainApplication.getSelectedFolderId()).size();
    }

    private Folder_Image_Album_Model getItem(int pos) {
        MainApplication mainApplication = this.application;
        return mainApplication.getImageByAlbum(mainApplication.getSelectedFolderId()).get(pos);
    }

    public void onBindViewHolder(final Holder holder, @SuppressLint("RecyclerView") final int pos) {
        final Folder_Image_Album_Model data = getItem(pos);
        holder.textView.setSelected(true);

        tv_count_var.setText("Selected " + application.selectedImages.size() + " Images");

        if (data._image_Count_ != 0) {
            String.valueOf(data._image_Count_);
        }

        this.glide.load(data._image_Path_).into(holder.imageView);

        if (data._image_Count_ == 0) {
            holder.textView.setBackgroundColor(0);
        } else {
            holder.textView.setBackgroundResource(R.drawable.select_image_bg);
        }
        holder.clickableView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (data._image_Count_ == 1) {
                    Toast.makeText(Folder_Image_Adapter.this.application, "This Image is Already Selected", Toast.LENGTH_SHORT).show();
                } else {
                    if (holder.imageView.getDrawable() == null) {
                        Toast.makeText(Folder_Image_Adapter.this.application, "Image corrupted or not supported.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (application.getSelectedImages().size() == 20) {
                        Toast.makeText(Folder_Image_Adapter.this.application, "You can select up to 20 images only", Toast.LENGTH_SHORT).show();
                    } else {
                        position = pos;
                        Folder_Image_Adapter.this.application.addSelectedImage(data);
                        Folder_Image_Adapter.this.notifyItemChanged(pos);
                        if (Folder_Image_Adapter.this.clickListner != null) {
                            Folder_Image_Adapter.this.clickListner.onItemClick(v, data);
                        }
                    }
                }
            }
        });
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int pos) {
        return new Holder(this.inflater.inflate(R.layout.folderr_imagee_itemm, parent, false));
    }

    public class Holder extends RecyclerView.ViewHolder {
        View clickableView;
        ImageView imageView;
        TextView textView;
        TextView textView2;

        Holder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView1);
            this.textView = (TextView) view.findViewById(R.id.textView1);
            this.textView2 = (TextView) view.findViewById(R.id.textView2);
            this.clickableView = view.findViewById(R.id.clickableView);
        }
    }
}
