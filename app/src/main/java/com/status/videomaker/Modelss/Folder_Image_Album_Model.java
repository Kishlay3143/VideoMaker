package com.status.videomaker.Modelss;

import android.text.TextUtils;

public class Folder_Image_Album_Model {
    public String folder_Name_str;
    public int _image_Count_;
    public String _image_Path_;
    public boolean _is_Supported_;

    public Folder_Image_Album_Model() {
        this._image_Count_ = 0;
        this._is_Supported_ = true;
        this._image_Count_ = 0;
        this._is_Supported_ = true;
    }

    public String get_image_Path_() {
        return _image_Path_;
    }

    public void set_image_Path_(String str) {
        this._image_Path_ = str;
    }

    public String toString() {
        if (TextUtils.isEmpty(this._image_Path_)) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FolderImageAlbum { imagePath=");
        stringBuilder.append(this._image_Path_);
        stringBuilder.append(",folderName=");
        stringBuilder.append(this.folder_Name_str);
        stringBuilder.append(",imageCount=");
        stringBuilder.append(this._image_Count_);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
