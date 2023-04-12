package com.status.videomaker.Servicess;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.InterFacess.OnProgressReceiver;
import com.status.videomaker.Maskss.Final_Mask_Bitmap_3D_Class;
import com.status.videomaker.Modelss.Folder_Image_Album_Model;
import com.status.videomaker.utilss.Scaling_Utilities_File_class;
import com.status.videomaker.videolib.libffmpeg.FileUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Imagee_Create_Service extends IntentService {
    public static final String ACTION_CREATE_NEW_THEME_IMAGES_STR = "ACTION_CREATE_NEW_THEME_IMAGES";
    public static final String ACTION_UPDATE_THEMEE_IMAGES_STR = "ACTION_UPDATE_THEME_IMAGES";
    public static final String EXTRAAA_SELECTED_THEME_STR = "selected_theme";
    public static final Object m_Lock_obj = new Object();
    public static boolean is_Image_Complate_bool;
    MainApplication application_obj;
    ArrayList arrayy_List;
    int total_Images_var;
    private Builder m_Builder_obj;
    private NotificationManager m_Notify_Manager;
    private String selected_Theme_str;

    public Imagee_Create_Service() {
        this(Imagee_Create_Service.class.getName());
    }

    public Imagee_Create_Service(String var1) {
        super(var1);
    }

    private void create_Images_method() {
        Log.e("ag--12", "createImages : ");
        this.total_Images_var = this.arrayy_List.size();
        Bitmap var1 = null;

        Bitmap var4;
        for (int var2 = 0; var2 < this.arrayy_List.size() - 1; var1 = var4) {
            StringBuilder var15;
            if (!this.is_Same_Theme_method() || MainApplication.isBreak) {
                var15 = new StringBuilder();
                var15.append(this.selected_Theme_str);
                var15.append(" break");
                Log.e("CreatorImageService", var15.toString());
                break;
            }

            File var3 = FileUtils.getImageDirectory(this.application_obj.selectedTheme.toString(), var2);
            Bitmap var5;
            if (var2 == 0) {
                var4 = Scaling_Utilities_File_class.check_Bitmap_method(((Folder_Image_Album_Model) this.arrayy_List.get(var2))._image_Path_);
                var5 = Scaling_Utilities_File_class.scale_Center_Crop_method(var4, MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT);
                var1 = Scaling_Utilities_File_class.Convert_Same_Size_New_method(var4, var5, MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT);
                var5.recycle();
                var4.recycle();
                System.gc();
            } else {
                label111:
                {
                    if (var1 != null) {
                        var4 = var1;
                        if (!var1.isRecycled()) {
                            break label111;
                        }
                    }

                    var5 = Scaling_Utilities_File_class.check_Bitmap_method(((Folder_Image_Album_Model) this.arrayy_List.get(var2))._image_Path_);
                    var1 = Scaling_Utilities_File_class.scale_Center_Crop_method(var5, MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT);
                    var4 = Scaling_Utilities_File_class.Convert_Same_Size_New_method(var5, var1, MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT);
                    var1.recycle();
                    var5.recycle();
                }

                var1 = var4;
            }

            Bitmap var6 = Scaling_Utilities_File_class.check_Bitmap_method(((Folder_Image_Album_Model) this.arrayy_List.get(var2 + 1))._image_Path_);
            var5 = Scaling_Utilities_File_class.scale_Center_Crop_method(var6, MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT);
            var4 = Scaling_Utilities_File_class.Convert_Same_Size_New_method(var6, var5, MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT);
            var5.recycle();
            var6.recycle();
            System.gc();
            Final_Mask_Bitmap_3D_Class.EFFECT var16 = (Final_Mask_Bitmap_3D_Class.EFFECT) this.application_obj.selectedTheme.getTheme().get(var2 % this.application_obj.selectedTheme.getTheme().size());
            var16.initBitmaps(var1, var4);
            int var7 = 0;

            int var9;
            while (true) {
                float var8 = (float) var7;
                var9 = var2;
                if (var8 >= Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_VAR) {
                    break;
                }

                if (this.is_Same_Theme_method() && !MainApplication.isBreak) {
                    Bitmap var10 = var16.getMask(var1, var4, var7);
                    if (!this.is_Same_Theme_method()) {
                        continue;
                    }

                    File var17 = new File(var3, String.format("img%02d.jpg", var7));

                    try {
                        if (var17.exists()) {
                            var17.delete();
                        }

                        FileOutputStream var11 = new FileOutputStream(var17);
                        var10.compress(Bitmap.CompressFormat.JPEG, 100, var11);
                        var11.close();
                    } catch (FileNotFoundException var13) {
                        var13.printStackTrace();
                    } catch (IOException var14) {
                        var14.printStackTrace();
                    }

                    boolean var18 = false;

                    while (this.application_obj.isEditModeEnable) {
                        Log.e("ag--12", "isEditModeEnable : ");

                        Log.e("CreatorImageService", "application.isEditModeEnable :");
                        if (this.application_obj.min_pos != Integer.MAX_VALUE) {
                            var2 = this.application_obj.min_pos;
                            var18 = true;
                        }

                        if (MainApplication.isBreak) {
                            Log.e("CreatorImageService", "application.isEditModeEnable Break:");
                            is_Image_Complate_bool = true;
                            this.stopSelf();
                            return;
                        }
                    }

                    if (var18) {
                        ArrayList var19 = new ArrayList();
                        var19.addAll(this.application_obj.videoImages);
                        this.application_obj.videoImages.clear();
                        int var12 = Math.min(var19.size(), Math.max(0, var2 - var2) * 30);

                        for (var9 = 0; var9 < var12; ++var9) {
                            this.application_obj.videoImages.add((String) var19.get(var9));
                        }

                        this.application_obj.min_pos = Integer.MAX_VALUE;
                    }

                    if (this.is_Same_Theme_method() && !MainApplication.isBreak) {
                        this.application_obj.videoImages.add(var17.getAbsolutePath());
                        this.update_Image_Progress_method();
                        if (var8 == Final_Mask_Bitmap_3D_Class._ANIMATED_FRAME_VAR - 1.0F) {
                            for (var9 = 0; var9 < 8 && this.is_Same_Theme_method() && !MainApplication.isBreak; ++var9) {
                                this.application_obj.videoImages.add(var17.getAbsolutePath());
                                this.update_Image_Progress_method();
                            }
                        }

                        ++var7;
                        continue;
                    }

                    var15 = new StringBuilder();
                    var15.append(this.selected_Theme_str);
                    var15.append(" :");
                    Log.i("CreatorImageService", var15.toString());
                    var9 = var2;
                    break;
                }

                var15 = new StringBuilder();
                var15.append(this.selected_Theme_str);
                var15.append(" break");
                Log.e("CreatorImageService", var15.toString());
                var9 = var2;
                break;
            }

            var2 = var9 + 1;
        }

        Glide.get(this).clearDiskCache();
        is_Image_Complate_bool = true;
        this.stopSelf();
        this.is_Same_Theme_method();
    }

    private boolean is_Same_Theme_method() {
        Log.e("ag--12", "isSameTheme : ");

        return this.selected_Theme_str.equals(this.application_obj.getCurrentTheme());
    }

    private void update_Image_Progress_method() {
        Log.e("ag--12", "updateImageProgress : ");

        final float size = (((float) this.application_obj.videoImages.size()) * 100.0f) / ((float) ((this.total_Images_var - 1) * 30));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                OnProgressReceiver onProgressReceiver = application_obj.getOnProgressReceiver();
                if (onProgressReceiver != null) {
                    onProgressReceiver.onImageProgressFrameUpdate(size);
                    Log.e("gt--", "size:------ " + size);
                }
            }
        });
    }

    private void update_Notification_method(int var1) {
        Log.e("ag--12", "updateNotification : ");

        this.m_Builder_obj.setProgress(100, (int) ((float) var1 * 25.0F / 100.0F), false);
        this.m_Notify_Manager.notify(1001, this.m_Builder_obj.build());
    }

    public IBinder onBind(Intent var1) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.application_obj = MainApplication.getInstance();
    }

    @SuppressLint({"WrongConstant", "ResourceType"})
    public void onHandleIntent(Intent var1) {
        Log.e("ag--12", "onHandleIntent : ");

        this.m_Notify_Manager = (NotificationManager) this.getSystemService("notification");
        Builder var2 = new Builder(this);
        this.m_Builder_obj = var2;
        var2.setContentTitle("Preparing Video").setContentText("Making in progress").setSmallIcon(2131230999);
        this.selected_Theme_str = var1.getStringExtra("selected_theme");
        this.arrayy_List = this.application_obj.getSelectedImages();
        this.application_obj.initArray();
        is_Image_Complate_bool = false;
        this.create_Images_method();
    }

    @Deprecated
    public void onStart(Intent var1, int var2) {
        Log.e("ag--12", "onStart : ");

        super.onStart(var1, var2);
    }
}
