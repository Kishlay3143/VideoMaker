package com.status.videomaker.Activitiess;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.status.videomaker.InterFacess.OnProgressReceiver;
import com.status.videomaker.Modelss.All_Music_Data_Model;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.R;
import com.status.videomaker.utilss.THEMES_Video_class;

import java.util.ArrayList;
import java.util.HashMap;

public class MainApplication extends Application {
    public static final ArrayList<Folder_Image_Album_Model> selectedImages = new ArrayList();
    public static Bitmap frame_sticker_pic = null;
    public static int VIDEO_HEIGHT = 850;
    public static int VIDEO_WIDTH = 1280;
    public static boolean isBreak = false;
    public static boolean isStoryAdded = false;
    public static int frame = -1;
    public static float second = 1.0f;
    public static THEMES_Video_class selectedTheme = THEMES_Video_class.Shine;
    public static String Path;
    public static boolean app_open = true;
    public static MainApplication mainApplication;

    public static String appName;
    public static HashMap<String, ArrayList<Folder_Image_Album_Model>> allAlbum;
    public static ArrayList<String> allFolder;
    public static ArrayList<String> videoImages = new ArrayList();
    public static String selectedFolderId = "";
    private static MainApplication instance;
    public boolean isEditModeEnable = false;
    public int min_pos = Integer.MAX_VALUE;
    public int frameStickerPic = -1;
    public boolean isFromSdCardAudio = false;
    public int effectPos = -1;
    private OnProgressReceiver onProgressReceiver;
    private All_Music_Data_Model allMusicDataModel;
    private String API_key = "10fd8238-1c20-49af-95d6-f1b29120e1a1";

    public static MainApplication getInstance() {
        return instance;
    }

    public static boolean isMyServiceRunning(Context context, Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void onCreate() {
        Log.e("ag--12", "MainApplication onCreate : ");
        super.onCreate();
        instance = this;

        mainApplication = this;
        Path = getString(R.string.app_name);
        appName = getString(R.string.app_name);

    }

    public void addSelectedImage(Folder_Image_Album_Model folderImageAlbumModel) {
        if (!isStoryAdded) {
            this.selectedImages.add(folderImageAlbumModel);

        } else if (this.selectedImages.size() > 0) {
            ArrayList arrayList = this.selectedImages;
            arrayList.add(arrayList.size() - 1, folderImageAlbumModel);
        } else {
            this.selectedImages.add(folderImageAlbumModel);
        }
        folderImageAlbumModel._image_Count_++;
    }

    public void removeSelectedImage(int i) {
        if (i <= this.selectedImages.size()) {
            Folder_Image_Album_Model folderImageAlbumModel = (Folder_Image_Album_Model) this.selectedImages.remove(i);
            folderImageAlbumModel._image_Count_--;
        }
    }

    public HashMap<String, ArrayList<Folder_Image_Album_Model>> getAllAlbum() {
        return this.allAlbum;
    }

    public ArrayList<Folder_Image_Album_Model> getImageByAlbum(String str) {
        ArrayList<Folder_Image_Album_Model> arrayList = (ArrayList) getAllAlbum().get(str);
        return arrayList == null ? new ArrayList() : arrayList;
    }

    public void ReplaceSelectedImage(Folder_Image_Album_Model folderImageAlbumModel, int pos) {
        this.selectedImages.set(pos, folderImageAlbumModel);
    }

    public String getSelectedFolderId() {
        return this.selectedFolderId;
    }

    public void setSelectedFolderId(String str) {
        this.selectedFolderId = str;
    }

    public ArrayList<Folder_Image_Album_Model> getSelectedImages() {
        return this.selectedImages;
    }

    public String getCurrentTheme() {
        Log.e("ag--12", "getCurrentTheme : ");
        return getSharedPreferences("theme", 0).getString("current_theme", THEMES_Video_class.Shine.toString());
    }

    public void setCurrentTheme(String str) {
        SharedPreferences.Editor edit = getSharedPreferences("theme", 0).edit();
        edit.putString("current_theme", str);
        edit.commit();
    }

    public OnProgressReceiver getOnProgressReceiver() {
        Log.e("ag--12", "getOnProgressReceiver 1 : " + onProgressReceiver);
        return this.onProgressReceiver;
    }

    public void setOnProgressReceiver(OnProgressReceiver onProgressReceiver2) {
        Log.e("ag--12", "setOnProgressReceiver 2 : " + onProgressReceiver);

        this.onProgressReceiver = onProgressReceiver2;
        Log.e("ag--12", "onProgressReceiver 3 : " + onProgressReceiver);
    }

    public int getFrame() {
        return this.frame;
    }

    public void setFrame(int i) {
        this.frame = i;
    }

    public All_Music_Data_Model getMusicData() {
        return this.allMusicDataModel;
    }

    public void setMusicData(All_Music_Data_Model allMusicDataModel2) {
        this.isFromSdCardAudio = false;
        this.allMusicDataModel = allMusicDataModel2;
    }

    public float getSecond() {
        return this.second;
    }

    public void setSecond(float f) {
        this.second = f;
    }

    public void initArray() {
        this.videoImages = new ArrayList<>();
    }

    public int getEffectPos() {
        return this.effectPos;
    }

    public void setEffectPos(int i) {
        this.effectPos = i;
    }
}
