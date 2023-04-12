package com.status.videomaker.Servicess;

import static com.status.videomaker.Activitiess.Gallery_Act.path_dir_var;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.status.videomaker.Activitiess.MainApplication;
import com.status.videomaker.Command.Editor;
import com.status.videomaker.Command.OnEditorListener;
import com.status.videomaker.InterFacess.OnProgressReceiver;
import com.status.videomaker.R;
import com.status.videomaker.utilss.Scaling_Utilities_File_class;
import com.status.videomaker.videolib.libffmpeg.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Videoo_Createe_Service extends IntentService {
    public static String path_str;
    public float total_seconds_var;
    String CHANNEL_ID_STR;
    MainApplication application_OBJ;
    File audio_ip_file;
    int last_var;
    String time_str;
    File save_Path_file;
    private File audio_file_obj;

    public Videoo_Createe_Service() {
        this(Videoo_Createe_Service.class.getName());
    }

    public Videoo_Createe_Service(String str) {
        super(str);
        this.CHANNEL_ID_STR = "createvideo";
        this.time_str = "\\btime=\\b\\d\\d:\\d\\d:\\d\\d.\\d\\d";
        this.last_var = 0;
        Log.e("ag--12", "VideoCreateService : ");

    }

    public static void app_end_Video_Log(String str) {
        Log.e("ag--12", "appendVideoLog : ");

        if (!FileUtils.TEMP_DIRECTORY.exists()) {
            FileUtils.TEMP_DIRECTORY.mkdirs();
        }
        File file = new File(FileUtils.TEMP_DIRECTORY, "video.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(str);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void app_end_Audio_Log(String str) {
        if (!FileUtils.TEMP_DIRECTORY.exists()) {
            FileUtils.TEMP_DIRECTORY.mkdirs();
        }
        File file = new File(FileUtils.TEMP_DIRECTORY, "audio.txt");
        Log.e("ag--12", "joinAudio 66600 ==file="+file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.append(str);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void onHandleIntent(Intent intent) {
        Log.e("ag--12", "onHandleIntent : ");

        this.application_OBJ = MainApplication.getInstance();
        this.total_seconds_var = (this.application_OBJ.getSecond() * ((float) this.application_OBJ.getSelectedImages().size())) - 1.0f;

        try {
            Thread.sleep(000);
        } catch (InterruptedException unused) {
        }
        join_Audio_method();
    }

    public void create_Video_method() {
        Log.e("ag--12", "createVideo : ");

        do {
        } while (!Imagee_Create_Service.is_Image_Complate_bool);
        File file = new File(FileUtils.TEMP_DIRECTORY, "video.txt");

        file.delete();

        for (int i = 0; i < this.application_OBJ.videoImages.size(); i++) {
            app_end_Video_Log(String.format("file '%s'", new Object[]{this.application_OBJ.videoImages.get(i)}));
        }

        save_Path_file = new FileUtils().createParentDirectory2(this);

        final String absolutePath = save_Path_file + get_Video_Name_method();
        String[] mFinalCmd = new String[0];
        if (this.application_OBJ.getMusicData() == null) {
            mFinalCmd = new String[]{"ffmpeg", "-i", file.getAbsolutePath(), "-r", "30", "-c:v", "libx264", "-preset", "ultrafast", "-pix_fmt", "yuv420p", absolutePath};
        }

        if (MainApplication.frame_sticker_pic != null) {

            try {
                Bitmap bitmap = MainApplication.frame_sticker_pic;
                if (!(bitmap.getWidth() == MainApplication.VIDEO_WIDTH && bitmap.getHeight() == MainApplication.VIDEO_HEIGHT)) {
                    bitmap = Scaling_Utilities_File_class.scale_Center_Crop_method(bitmap, MainApplication.VIDEO_WIDTH, MainApplication.VIDEO_HEIGHT);
                }

                if (FileUtils.frameFile.exists()) {
                    FileUtils.frameFile.delete();
                }

                FileOutputStream fileOutputStream = new FileOutputStream(FileUtils.frameFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                bitmap.recycle();
                System.gc();
            } catch (Exception unused) {
            }
        }

        float tFloat = (30.0f / this.application_OBJ.getSecond());

        mFinalCmd = new String[]{"ffmpeg", "-r", String.valueOf(tFloat), "-f", "concat", "-safe", "0", "-i", file.getAbsolutePath(),
                "-i", FileUtils.frameFile.getAbsolutePath(), "-stream_loop", "-1", "-i", this.audio_file_obj.getAbsolutePath(),
                "-filter_complex", "overlay=0:0", "-strict", "experimental", "-r", String.valueOf((30.0f / this.application_OBJ.getSecond())),
                "-t", String.valueOf(this.total_seconds_var), "-c:v", "libx264", "-preset", "ultrafast", "-pix_fmt", "yuv420p", "-ac", "2", absolutePath};

        final OnProgressReceiver onProgressReceiver = this.application_OBJ.getOnProgressReceiver();
        Editor.execCmd(mFinalCmd, ((long) this.total_seconds_var) * 1000 * 1000, (OnEditorListener) new OnEditorListener() {
            public void onSuccess() {
                Log.e("ag--12", "joinAudio onSuccess 1 : ");

                try {
                    long length = new File(absolutePath).length();
                    String string = Videoo_Createe_Service.this.getResources().getString(R.string.artist_name);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_data", absolutePath);
                    contentValues.put("_size", Long.valueOf(length));
                    contentValues.put("mime_type", "video/mp4");
                    contentValues.put("artist", string);
                    contentValues.put("duration", Float.valueOf(Videoo_Createe_Service.this.total_seconds_var * 1000.0f));
                    Videoo_Createe_Service.this.getContentResolver().insert(MediaStore.Audio.Media.getContentUriForPath(absolutePath), contentValues);
                    Toast.makeText(application_OBJ, "" + absolutePath, Toast.LENGTH_SHORT).show();

                    Log.e("ag--12", "2 String onSuccess 1 : " + length);
                    Log.e("ag--12", "2 String onSuccess 2 : " + absolutePath);
                    Log.e("ag--12", "2 String onSuccess 3 : " + Long.valueOf(length));
                    Log.e("ag--12", "2 String onSuccess 4 : " + Float.valueOf(Videoo_Createe_Service.this.total_seconds_var * 1000.0f));

                } catch (Exception e) {

                    e.printStackTrace();
                }
                try {
                    Videoo_Createe_Service.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(absolutePath))));
                } catch (Exception e2) {

                    e2.printStackTrace();
                }
                path_str = absolutePath;
                if (onProgressReceiver != null) {
                    onProgressReceiver.onProgressFinish(absolutePath);
                }
                FileUtils.deleteTempDir();
                Videoo_Createe_Service.this.stopSelf();
            }

            public void onFailure() {
                Log.e("ag--12", "joinAudio onFailure 1 : ");
                Videoo_Createe_Service.this.stopSelf();
            }

            public void onProgress(float f) {
                Log.e("ag--12", "joinAudio onProgress 1 : ");

                if (onProgressReceiver != null) {
                    onProgressReceiver.onVideoProgressFrameUpdate(f * 100.0f);
                }
            }
        });
    }

    private void join_Audio_method() {
        Log.e("ag--12", "joinAudio 1 : ");

        try {
            this.audio_ip_file = new File(FileUtils.TEMP_DIRECTORY, "audio.txt");
            File file = new File(path_dir_var, "audio.mp3");

            Log.e("TAG", "joinAudio:----------------- "+file );
            this.audio_file_obj = file;
            this.audio_ip_file.delete();
            file.delete();
            int i = 0;
            Log.e("ag--12", "joinAudio 66600 ==="+ application_OBJ.getMusicData().trackk_data_str);

            while (true) {
                app_end_Audio_Log(String.format("file '%s'", new Object[]{this.application_OBJ.getMusicData().trackk_data_str}));
                StringBuilder sb = new StringBuilder(String.valueOf(i));
                sb.append(" is D  ");
                sb.append(Videoo_Createe_Service.this.total_seconds_var * 1000.0f);
                sb.append("___");
                long j = (long) i;
                sb.append(this.application_OBJ.getMusicData().trackk_duration_var * j);
                if (Videoo_Createe_Service.this.total_seconds_var * 1000.0f <= ((float) (this.application_OBJ.getMusicData().trackk_duration_var * j))) {
                    Log.e("ag--12", "track_duration : " + this.application_OBJ.getMusicData().trackk_duration_var);

                    Log.e("ag--12", "joinAudio 2 ");
                    break;
                }
                i++;
            }
            String[] mFinalCmd = {"ffmpeg", "-f", "concat", "-safe", "0", "-i", this.audio_ip_file.getAbsolutePath(), "-c", "copy", "-preset", "ultrafast", "-ac", "2", this.audio_file_obj.getAbsolutePath()};

            Editor.execCmd(mFinalCmd, 0, (OnEditorListener) new OnEditorListener() {
                public void onSuccess() {
                    Log.e("ag--12", "onSuccess ");

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public final void run() {
                            onSuccess_CreateVideoService();
                        }
                    });
                }

                public void onSuccess_CreateVideoService() {
                    Log.e("ag--12", "onSuccess_CreateVideoService ");

                    Videoo_Createe_Service.this.create_Video_method();
                }

                public void onFailure() {
                    Log.e("ag--12", "onFailure ");

                }

                public void onProgress(float f) {
                    Log.e("ag--12", "onProgress : ");

                    OnProgressReceiver onProgressReceiver = Videoo_Createe_Service.this.application_OBJ.getOnProgressReceiver();
                    Log.e("ag--12", "onProgressReceiver : " + onProgressReceiver);

                    if (onProgressReceiver != null) {
                        onProgressReceiver.onVideoProgressFrameUpdate(f);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    private String get_Video_Name_method() {
        return new SimpleDateFormat("yyyyMMMdd_HHmmss", Locale.ENGLISH).format(new Date()) + ".mp4";
    }
}
