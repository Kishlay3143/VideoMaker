package com.status.videomaker.videolib.libffmpeg;

import static com.status.videomaker.Activitiess.Gallery_Act.path_dir_var;

import android.content.Context;
import android.os.Environment;

import com.status.videomaker.Activitiess.Gallery_Act;
import com.status.videomaker.Activitiess.MainApplication;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;

public class FileUtils {

    public static final File DirDefaultAudio = new File(path_dir_var, "Default");
    public static final File TEMP_DIRECTORY;
    public static final File TEMP_DIRECTORY_AUDIO = new File(path_dir_var, "temp_audio");
    public static final File TEMP_VID_DIRECTORY;
    public static final File frameFile = new File(path_dir_var, "frame.png");
    public static final File effectFile = new File(path_dir_var, "effect.png");
    static final String ffmpegFileName = "ffmpeg";
    public static File mSdCard = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
    public static File APP_DIRECTORY = new File(mSdCard, MainApplication.Path);
    public static long mDeleteFileCount = 0;

    static {
        File file = new File(path_dir_var, "temp");
        File file11 = new File(String.valueOf(APP_DIRECTORY));
        TEMP_DIRECTORY = file;
        File file2 = new File(file, "temp_vid");
        TEMP_VID_DIRECTORY = file2;
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file11.exists()) {
            file.mkdirs();
        }
        if (!file2.exists()) {
            file2.mkdirs();
        }
    }

    public FileUtils() {
        mDeleteFileCount = 0;
    }

    public static File getImageDirectory(String str) {
        File file = new File(TEMP_DIRECTORY, str);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getImageDirectory(String str, int i) {
        File file = new File(getImageDirectory(str), String.format("IMG_%03d", new Object[]{Integer.valueOf(i)}));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static boolean deleteThemeDir(String str) {
        return deleteFile(getImageDirectory(str));
    }

    public static void deleteTempDir() {
        for (final File file : TEMP_DIRECTORY.listFiles()) {
            new Thread() {
                public void run() {
                    FileUtils.deleteFile(file);
                }
            }.start();
        }
    }

    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file2 : listFiles) {
                    mDeleteFileCount += file2.length();
                    deleteFile(file2);
                }
            }
            mDeleteFileCount += file.length();
            return file.delete();
        }
        mDeleteFileCount += file.length();
        return file.delete();
    }

    static boolean copyBinaryFromAssetsToData(Context context, String str, String str2) {
        File filesDirectory = getFilesDirectory(context);
        try {
            InputStream open = context.getAssets().open(str);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filesDirectory, str2));
            byte[] bArr = new byte[4096];
            while (true) {
                int read = open.read(bArr);
                if (-1 == read) {
                    Util.close((OutputStream) fileOutputStream);
                    Util.close(open);
                    return true;
                }
                fileOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e) {
            return false;
        }
    }

    static File getFilesDirectory(Context context) {
        return context.getFilesDir();
    }

    public static String getFFmpeg(Context context) {
        return String.valueOf(getFilesDirectory(context).getAbsolutePath()) + File.separator + ffmpegFileName;
    }

    static String getFFmpeg(Context context, Map<String, String> map) {
        String str = "";
        if (map != null) {
            for (Map.Entry next : map.entrySet()) {
                str = String.valueOf(str) + ((String) next.getKey()) + "=" + ((String) next.getValue()) + " ";
            }
        }
        return String.valueOf(str) + getFFmpeg(context);
    }

    static String SHA1(String str) {
        BufferedInputStream bufferedInputStream;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
            try {
                String SHA1 = SHA1((InputStream) bufferedInputStream);
                Util.close((InputStream) bufferedInputStream);
                return SHA1;
            } catch (Throwable unused) {
                Util.close((InputStream) bufferedInputStream);
                return null;
            }
        } catch (Throwable unused2) {
            bufferedInputStream = null;
            Util.close((InputStream) bufferedInputStream);
            return null;
        }
    }

    static String SHA1(InputStream inputStream) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                instance.update(bArr, 0, read);
            }
            Formatter formatter = new Formatter();
            byte[] digest = instance.digest();
            int length = digest.length;
            for (int i = 0; i < length; i++) {
                formatter.format("%02x", new Object[]{Byte.valueOf(digest[i])});
            }
            String formatter2 = formatter.toString();
            Util.close(inputStream);
            return formatter2;
        } catch (NoSuchAlgorithmException e) {
            Log.e(e);
        } catch (IOException e2) {
            Log.e(e2);
        } catch (Throwable th) {
            Util.close(inputStream);
            throw th;
        }
        Util.close(inputStream);
        return null;
    }

    public File createParentDirectory(Context context) {
        File file = new File(Gallery_Act.getUploadVideoPathMethod(context), "temp_video");
        if (file.exists()) {
            android.util.Log.d("Folder", "Not Created");
        } else if (file.mkdirs()) {
            android.util.Log.d("Folder", "Created");
        }
        return file;
    }

    public File createParentDirectory2(Context context) {
        File file = new File("/storage/emulated/0/Download/Status Video Maker/Status Video ");
        if (file.exists()) {
            android.util.Log.d("Folder", "Not Created");
        } else if (file.mkdirs()) {
            android.util.Log.d("Folder", "Created");
        }
        return file;
    }
}
