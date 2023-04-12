package com.status.videomaker.Modelss;

import java.io.Serializable;

public class Video_Model implements Serializable {

    private String _id = "";
    private String _m_Date = "";
    private String _m_File_Name = "";
    private String _m_File_Path = "";
    private String _m_File_Size = "";
    private String _m_Status_Type = "";

    public void set_id(String id) {
        this._id = id;
    }

    public String getFileSize() {
        return this._m_File_Size;
    }

    public void setFileSize(String str) {
        this._m_File_Size = str;
    }

    public String getFilePath() {
        return this._m_File_Path;
    }

    public void setFilePath(String str) {
        this._m_File_Path = str;
    }

    public String getFileName() {
        return this._m_File_Name;
    }

    public void setFileName(String str) {
        this._m_File_Name = str;
    }

    public void setDate(String str) {
        this._m_Date = str;
    }

    public void setStatusType(String str) {
        this._m_Status_Type = str;
    }

}
