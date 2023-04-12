package com.xinlan.imageeditlibrary.editimage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.fragment.StickerFragment;

public class StickerTypeAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final int[] typeIcon = {R.drawable.emoji, R.drawable.sticker};
    public static final String[] stickerPath = {"sticker", "stickers1"};
    private StickerFragment mStickerFragment;
    private ImageClick mImageClick = new ImageClick();

    public StickerTypeAdapter(StickerFragment fragment) {
        super();
        this.mStickerFragment = fragment;
    }

    @Override
    public int getItemCount() {
        return typeIcon.length;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_sticker_type_item, parent, false);
        ImageHolder holer = new ImageHolder(v);
        return holer;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageHolder image_holder = (ImageHolder) holder;
        image_holder.icon.setImageResource(typeIcon[position]);
        image_holder.text.setTag(stickerPath[position]);
        image_holder.text.setOnClickListener(mImageClick);
    }

    public class ImageHolder extends ViewHolder {
        public ImageView icon;
        public TextView text;

        public ImageHolder(View itemView) {
            super(itemView);
            this.icon = itemView.findViewById(R.id.icon);
            this.text = itemView.findViewById(R.id.text);
        }
    }

    private final class ImageClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            String data = (String) v.getTag();
            mStickerFragment.swipToStickerDetails(data);
        }
    }
}