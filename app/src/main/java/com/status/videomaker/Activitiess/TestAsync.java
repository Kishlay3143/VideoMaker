package com.status.videomaker.Activitiess;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import com.status.videomaker.Modelss.Folder_Image_Album_Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TestAsync extends AsyncTask<Void, Integer, String> {
    Activity activity;

    public TestAsync(Activity mainActivity) {
        activity = mainActivity;
    }

    public void setSelectedFolderId(String str) {
        MainApplication.selectedFolderId = str;
    }


    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("ag--12", "onPreExecute : ");
    }

    protected String doInBackground(Void... arg0) {
        MainApplication.allFolder = new ArrayList<>();
        MainApplication.allAlbum = new HashMap<>();
        Cursor query = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "bucket_display_name", "bucket_id", "date_modified"}, (String) null, (String[]) null, "date_modified DESC");
        if (query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("bucket_display_name");
            int columnIndex2 = query.getColumnIndex("bucket_id");
            int columnIndex3 = query.getColumnIndex("date_modified");
            setSelectedFolderId(query.getString(columnIndex2));
            do {
                Folder_Image_Album_Model photoData = new Folder_Image_Album_Model();
                photoData._image_Path_ = query.getString(query.getColumnIndex("_data"));

                File file = new File(photoData._image_Path_);
                if (!photoData._image_Path_.endsWith(".gif") && file.exists()) {
                    query.getString(columnIndex3);
                    String string = query.getString(columnIndex);
                    String string2 = query.getString(columnIndex2);
                    if (!MainApplication.allFolder.contains(string2)) {
                        MainApplication.allFolder.add(string2);
                    }
                    ArrayList arrayList = MainApplication.allAlbum.get(string2);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    photoData.folder_Name_str = string;
                    arrayList.add(photoData);
                    MainApplication.allAlbum.put(string2, arrayList);
                }
            } while (query.moveToNext());
        }
        return String.valueOf(MainApplication.allAlbum.size());
    }

    protected void onProgressUpdate(Integer... a) {
        super.onProgressUpdate(a);
        Log.e("ag--12", "onProgressUpdate : ");
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("ag--12", "onPostExecute : " + result);

        Message message = new Message();
        message.what = 10;
        Home_Actvty.m_Refresh_var.sendMessage(message);
    }
}
