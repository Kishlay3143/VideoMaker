package com.xinlan.imageeditlibrary.editimage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xinlan.imageeditlibrary.BaseActivity;
import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.fragment.AddTextFragment;
import com.xinlan.imageeditlibrary.editimage.fragment.CropFragment;
import com.xinlan.imageeditlibrary.editimage.fragment.FilterListFragment;
import com.xinlan.imageeditlibrary.editimage.fragment.MainMenuFragment;
import com.xinlan.imageeditlibrary.editimage.fragment.PaintFragment;
import com.xinlan.imageeditlibrary.editimage.fragment.RotateFragment;
import com.xinlan.imageeditlibrary.editimage.fragment.StickerFragment;
import com.xinlan.imageeditlibrary.editimage.utils.BitmapUtils;
import com.xinlan.imageeditlibrary.editimage.view.CropImageView;
import com.xinlan.imageeditlibrary.editimage.view.CustomPaintView;
import com.xinlan.imageeditlibrary.editimage.view.CustomViewPager;
import com.xinlan.imageeditlibrary.editimage.view.RotateImageView;
import com.xinlan.imageeditlibrary.editimage.view.StickerView;
import com.xinlan.imageeditlibrary.editimage.view.TextStickerView;
import com.xinlan.imageeditlibrary.editimage.view.imagezoom.ImageViewTouch;
import com.xinlan.imageeditlibrary.editimage.view.imagezoom.ImageViewTouchBase;

import java.io.File;

public class EditImageActivity extends BaseActivity {
    public static final String FILE_PATH = "file_path";
    public static final String EXTRA_OUTPUT = "extra_output";
    public static final int MODE_NONE = 0;
    public static final int MODE_STICKERS = 1;
    public static final int MODE_FILTER = 2;
    public static final int MODE_CROP = 3;
    public static final int MODE_ROTATE = 4;
    public static final int MODE_TEXT = 5;
    public static final int MODE_PAINT = 6;
    public String filePath;
    public String saveFilePath;
    public int mode = MODE_NONE;
    public ImageViewTouch mainImage;
    public ViewFlipper bannerFlipper;
    public StickerView mStickerView;
    public CropImageView mCropPanel;
    public RotateImageView mRotatePanel;
    public TextStickerView mTextStickerView;
    public CustomPaintView mPaintView;
    public CustomViewPager bottomGallery;
    public StickerFragment mStickerFragment;
    public FilterListFragment mFilterListFragment;
    public CropFragment mCropFragment;
    public RotateFragment mRotateFragment;
    public AddTextFragment mAddTextFragment;
    public PaintFragment mPaintFragment;
    protected int mOpTimes = 0;
    protected boolean isBeenSaved = false;
    private int imageWidth, imageHeight;
    private LoadImageTask mLoadImageTask;
    private EditImageActivity mContext;
    private Bitmap mainBitmap;
    private View backBtn;
    private View applyBtn;
    private View saveBtn;
    private BottomGalleryAdapter mBottomGalleryAdapter;
    private MainMenuFragment mMainMenuFragment;
    private SaveImageTask mSaveImageTask;
//    private RedoUndoController mRedoUndoController;

    public static void start(Activity context, final String editImagePath, final String outputPath, final int requestCode) {
        if (TextUtils.isEmpty(editImagePath)) {
            Toast.makeText(context, R.string.no_choose, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent it = new Intent(context, EditImageActivity.class);
        it.putExtra(EditImageActivity.FILE_PATH, editImagePath);
        it.putExtra(EditImageActivity.EXTRA_OUTPUT, outputPath);
        context.startActivityForResult(it, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInitImageLoader();
        setContentView(R.layout.activity_image_edit);
        initView();
        getData();
    }

    public String getUploadVideoPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File ef = context.getExternalFilesDir("/");
            if (ef != null && ef.isDirectory()) {
                return ef.getPath();
            }
        }
        return new File(Environment.getExternalStorageDirectory(), "ve").getPath();
    }

    private void getData() {
//        File file = new File("/storage/emulated/0/Download/Abhishek Video Maker");
        File file = new File(getUploadVideoPath(EditImageActivity.this), "temp_image");

        if (!file.exists()) {
            file.mkdir();
        }

        filePath = getIntent().getStringExtra("editImage");
//        filePath = "/storage/emulated/0/DCIM/Camera/IMG_20220122_164412_HDR.jpg";
//        Log.e("ag--12","filePath : "+filePath);
//        saveFilePath = getIntent().getStringExtra(EXTRA_OUTPUT);
        saveFilePath = file + "/" + System.currentTimeMillis() + ".png";
        Log.e("ag--12", "filePath--" + saveFilePath);
        loadImage(filePath);
    }

    private void initView() {
        mContext = this;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        imageWidth = metrics.widthPixels / 2;
        imageHeight = metrics.heightPixels / 2;

        bannerFlipper = (ViewFlipper) findViewById(R.id.banner_flipper);
        bannerFlipper.setInAnimation(this, R.anim.in_bottom_to_top);
        bannerFlipper.setOutAnimation(this, R.anim.out_bottom_to_top);
        applyBtn = findViewById(R.id.apply);
        applyBtn.setOnClickListener(new ApplyBtnClick());
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new SaveBtnClick());

        mainImage = (ImageViewTouch) findViewById(R.id.main_image);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mStickerView = (StickerView) findViewById(R.id.sticker_panel);
        mCropPanel = (CropImageView) findViewById(R.id.crop_panel);
        mRotatePanel = (RotateImageView) findViewById(R.id.rotate_panel);
        mTextStickerView = (TextStickerView) findViewById(R.id.text_sticker_panel);
        mPaintView = (CustomPaintView) findViewById(R.id.custom_paint_view);

        bottomGallery = (CustomViewPager) findViewById(R.id.bottom_gallery);
        mMainMenuFragment = MainMenuFragment.newInstance();
        mBottomGalleryAdapter = new BottomGalleryAdapter(this.getSupportFragmentManager());
        mStickerFragment = StickerFragment.newInstance();
        mFilterListFragment = FilterListFragment.newInstance();
        mCropFragment = CropFragment.newInstance();
        mRotateFragment = RotateFragment.newInstance();
        mAddTextFragment = AddTextFragment.newInstance();
        mPaintFragment = PaintFragment.newInstance();

        bottomGallery.setAdapter(mBottomGalleryAdapter);

        mainImage.setFlingListener(new ImageViewTouch.OnImageFlingListener() {
            @Override
            public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (velocityY > 1) {
                    closeInputMethod();
                }
            }
        });

//        mRedoUndoController = new RedoUndoController(this, findViewById(R.id.redo_uodo_panel));
    }

    private void closeInputMethod() {
        if (mAddTextFragment.isAdded()) {
            mAddTextFragment.hideInput();
        }
    }

    public void loadImage(String filepath) {
        if (mLoadImageTask != null) {
            mLoadImageTask.cancel(true);
        }
        mLoadImageTask = new LoadImageTask();
        mLoadImageTask.execute(filepath);
    }

    @Override
    public void onBackPressed() {
        switch (mode) {
            case MODE_STICKERS:
                mStickerFragment.backToMain();
                return;
            case MODE_FILTER:
                mFilterListFragment.backToMain();
                return;
            case MODE_CROP:
                mCropFragment.backToMain();
                return;
            case MODE_ROTATE:
                mRotateFragment.backToMain();
                return;
//            case MODE_TEXT:
//                mAddTextFragment.backToMain();
//                return;
            case MODE_PAINT:
                mPaintFragment.backToMain();
                return;
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.exit_without_save).setCancelable(false).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mContext.finish();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    protected void doSaveImage() {
        if (mSaveImageTask != null) {
            mSaveImageTask.cancel(true);
        }
        mSaveImageTask = new SaveImageTask();
        mSaveImageTask.execute(mainBitmap);
    }

    public void changeMainBitmap(Bitmap newBit, boolean needPushUndoStack) {
        if (newBit == null)
            return;

        if (mainBitmap == null || mainBitmap != newBit) {
            if (needPushUndoStack) {
//                mRedoUndoController.switchMainBit(mainBitmap, newBit);
                increaseOpTimes();
            }
            mainBitmap = newBit;
            mainImage.setImageBitmap(mainBitmap);
            mainImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadImageTask != null) {
            mLoadImageTask.cancel(true);
        }

        if (mSaveImageTask != null) {
            mSaveImageTask.cancel(true);
        }

//        if (mRedoUndoController != null) {
//            mRedoUndoController.onDestroy();
//        }
    }

    public void increaseOpTimes() {
        mOpTimes++;
        isBeenSaved = false;
    }

    public void resetOpTimes() {
        isBeenSaved = true;
    }

    public boolean canAutoExit() {
        return isBeenSaved || mOpTimes == 0;
    }

    protected void onSaveTaskDone() {
        Intent intent = new Intent();
        intent.putExtra("uri_new", saveFilePath);
        intent.putExtra("position", getIntent().getIntExtra("position", 0));
        setResult(1001, intent);
        finish();
    }

    public Bitmap getMainBit() {
        return mainBitmap;
    }

    private final class BottomGalleryAdapter extends FragmentPagerAdapter {
        public BottomGalleryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            switch (index) {
                case MainMenuFragment.INDEX:
                    return mMainMenuFragment;
                case StickerFragment.INDEX:
                    return mStickerFragment;
                case FilterListFragment.INDEX:
                    return mFilterListFragment;
                case CropFragment.INDEX:
                    return mCropFragment;
                case RotateFragment.INDEX:
                    return mRotateFragment;
//                case AddTextFragment.INDEX:
//                    return mAddTextFragment;
                case PaintFragment.INDEX:
                    return mPaintFragment;
            }
            return MainMenuFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 8;
        }
    }

    private final class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            return BitmapUtils.getSampledBitmap(params[0], imageWidth, imageHeight);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            changeMainBitmap(result, false);
        }
    }

    private final class ApplyBtnClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (mode) {
                case MODE_STICKERS:
                    mStickerFragment.applyStickers();
                    break;
                case MODE_FILTER:
                    mFilterListFragment.applyFilterImage();
                    break;
                case MODE_CROP:
                    mCropFragment.applyCropImage();
                    break;
                case MODE_ROTATE:
                    mRotateFragment.applyRotateImage();
                    break;
//                case MODE_TEXT:
//                    mAddTextFragment.applyTextImage();
//                    break;
                case MODE_PAINT:
                    mPaintFragment.savePaintImage();
                    break;
                default:
                    break;
            }
        }
    }

    private final class SaveBtnClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            doSaveImage();
        }
    }

    private final class SaveImageTask extends AsyncTask<Bitmap, Void, Boolean> {
        private Dialog dialog;

        @Override
        protected Boolean doInBackground(Bitmap... params) {
            if (TextUtils.isEmpty(saveFilePath)) {
                return false;
            }
            return BitmapUtils.saveBitmap(params[0], saveFilePath);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            dialog.dismiss();
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = EditImageActivity.getLoadingDialog(mContext, R.string.saving_image, false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            dialog.dismiss();

            if (result) {
                resetOpTimes();
                onSaveTaskDone();
                Toast.makeText(mContext, "Image Edited Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, R.string.save_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}