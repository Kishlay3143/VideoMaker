package com.status.videomaker.utilss;

import android.os.Environment;

import com.status.videomaker.view.StickerImage;

import java.io.File;
import java.util.ArrayList;

public class Utils_class {
    public static ArrayList<StickerImage> Stickers_list = new ArrayList<>();
    public static ArrayList<Integer> seekbar_progress_AL = new ArrayList<>();
    public static String temp_File_STR = "";
    public static int view_id = 0;
    private static File _folder_Path = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Download/BsSlideShow/");
    static File temp_Path_obj = new File(_folder_Path, ".temp");
    private static final File temp_video_Path_obj = new File(temp_Path_obj, ".temp_vid");

    static {
        if (!temp_Path_obj.exists()) {
            temp_Path_obj.mkdirs();
        }
        if (!temp_video_Path_obj.exists()) {
            temp_video_Path_obj.mkdirs();
        }
    }


}
