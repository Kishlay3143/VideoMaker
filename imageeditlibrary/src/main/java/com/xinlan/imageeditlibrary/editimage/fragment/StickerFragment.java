package com.xinlan.imageeditlibrary.editimage.fragment;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinlan.imageeditlibrary.BaseActivity;
import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;
import com.xinlan.imageeditlibrary.editimage.ModuleConfig;
import com.xinlan.imageeditlibrary.editimage.adapter.StickerAdapter;
import com.xinlan.imageeditlibrary.editimage.adapter.StickerTypeAdapter;
import com.xinlan.imageeditlibrary.editimage.model.StickerBean;
import com.xinlan.imageeditlibrary.editimage.task.StickerTask;
import com.xinlan.imageeditlibrary.editimage.view.StickerItem;
import com.xinlan.imageeditlibrary.editimage.view.StickerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class StickerFragment extends BaseEditFragment {
    public static final int INDEX = ModuleConfig.INDEX_STICKER;

    public static final String TAG = StickerFragment.class.getName();
    public static final String STICKER_FOLDER = "stickers";

    private View mainView;
    private ViewFlipper flipper;
    private View backToMenu;
    private RecyclerView typeList;
    private RecyclerView stickerList;
    private View backToType;
    private StickerView mStickerView;
    private StickerAdapter mStickerAdapter;

    private LoadStickersTask mLoadStickersTask;
    private List<StickerBean> stickerBeanList = new ArrayList<StickerBean>();

    private SaveStickersTask mSaveTask;
    int position=0;

    public static StickerFragment newInstance() {
        StickerFragment fragment = new StickerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.fragment_edit_image_sticker_type, null);
        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mStickerView = activity.mStickerView;
        flipper = (ViewFlipper) mainView.findViewById(R.id.flipper);
        flipper.setInAnimation(activity, R.anim.in_bottom_to_top);
        flipper.setOutAnimation(activity, R.anim.out_bottom_to_top);

        backToMenu = mainView.findViewById(R.id.back_to_main);
        typeList = (RecyclerView) mainView.findViewById(R.id.stickers_type_list);
        typeList.setHasFixedSize(true);

//        try {
//            this.mImages = getActivity().getAssets().list("sticker");
//            this.mDrawables = new Drawable[this.mImages.length];
//            for (int i = 0; i < this.mImages.length; i++) {
//                AssetManager assets = getActivity().getAssets();
//                this.mDrawables[i] = Drawable.createFromStream(assets.open("sticker/" + this.mImages[i]), null);
//            }
//
//            Log.e("ag--12", "onActivityCreated 1 : "+this.mImages.length );
//
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        typeList.setLayoutManager(mLayoutManager);
//        typeList.setAdapter(new StickerTypeAdapter(this));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        typeList.setLayoutManager(mLayoutManager);
        typeList.setAdapter(new StickerTypeAdapter(this));

        backToType = mainView.findViewById(R.id.back_to_type);// back按钮
        stickerList = (RecyclerView) mainView.findViewById(R.id.stickers_list);
        stickerList.setHasFixedSize(true);
        LinearLayoutManager stickerListLayoutManager = new LinearLayoutManager(activity);
        stickerListLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        stickerList.setLayoutManager(stickerListLayoutManager);

        mStickerAdapter = new StickerAdapter(this);
        stickerList.setAdapter(mStickerAdapter);

        backToMenu.setOnClickListener(new BackToMenuClick());
        backToType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " );
                flipper.showPrevious();
                stickerList.setAdapter(mStickerAdapter);
                StickerAdapter.pathList.get(position);
            }
        });
    }

    @Override
    public void onShow() {
        activity.mode = EditImageActivity.MODE_STICKERS;
        activity.mStickerFragment.getmStickerView().setVisibility(View.VISIBLE);
        activity.bannerFlipper.showNext();
    }

    private void loadStickersData() {
        if (mLoadStickersTask != null) {
            mLoadStickersTask.cancel(true);
        }
        mLoadStickersTask = new LoadStickersTask();
        mLoadStickersTask.execute(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoadStickersTask != null) {
            mLoadStickersTask.cancel(true);
        }
    }

    public void swipToStickerDetails(String path) {
        mStickerAdapter.addStickerImages(path);
        flipper.showNext();
    }

    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void selectedStickerItem(String path) {
        mStickerView.addBitImage(getImageFromAssetsFile(path));
    }

    public StickerView getmStickerView() {
        return mStickerView;
    }

    public void setmStickerView(StickerView mStickerView) {
        this.mStickerView = mStickerView;
    }

    @Override
    public void backToMain() {
        activity.mode = EditImageActivity.MODE_NONE;
        activity.bottomGallery.setCurrentItem(0);
        mStickerView.setVisibility(View.GONE);
        activity.bannerFlipper.showPrevious();
    }

    public void applyStickers() {
        if (mSaveTask != null) {
            mSaveTask.cancel(true);
        }
        mSaveTask = new SaveStickersTask((EditImageActivity) getActivity());
        mSaveTask.execute(activity.getMainBit());
    }

    private final class LoadStickersTask extends AsyncTask<Integer, Void, Void> {
        private Dialog loadDialog;

        public LoadStickersTask() {
            super();
            loadDialog = BaseActivity.getLoadingDialog(getActivity(), R.string.saving_image, false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadDialog.show();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            stickerBeanList.clear();
            AssetManager assetManager = getActivity().getAssets();
            try {
                String[] lists = assetManager.list(STICKER_FOLDER);
                for (String parentPath : lists) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            loadDialog.dismiss();
        }
    }

    private final class BackToMenuClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            backToMain();
        }
    }

    private final class SaveStickersTask extends StickerTask {
        public SaveStickersTask(EditImageActivity activity) {
            super(activity);
        }

        @Override
        public void handleImage(Canvas canvas, Matrix m) {
            LinkedHashMap<Integer, StickerItem> addItems = mStickerView.getBank();
            for (Integer id : addItems.keySet()) {
                StickerItem item = addItems.get(id);
                item.matrix.postConcat(m);
                canvas.drawBitmap(item.bitmap, item.matrix, null);
            }
        }

        @Override
        public void onPostResult(Bitmap result) {
            mStickerView.clear();
            activity.changeMainBitmap(result, true);
            backToMain();
        }
    }
}
