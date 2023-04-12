package com.status.videomaker.Adapterss;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.status.videomaker.Activitiess._Creationn_Activityy;
import com.status.videomaker.Activitiess.Share_Actvty;
import com.status.videomaker.Modelss.Video_Model;
import com.status.videomaker.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Creationn_Adptr extends RecyclerView.Adapter<Creationn_Adptr.ViewHolder> {
    public static ArrayList<Video_Model> mList_AL;
    public static int pure_var;
    private final Activity mContext_act;
    private Dialog mDialog_obj;

    public Creationn_Adptr(Activity context, ArrayList<Video_Model> list) {
        this.mContext_act = context;
        this.mList_AL = list;
    }

    public static void confirm_delete_Method(int pure) {
        Log.i("pp--", "confirmDelete: " + pure);
        mList_AL.remove(pure);
        _Creationn_Activityy._creationnAdptr_.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.creationn_itemm, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        Glide.with(this.mContext_act).load(this.mList_AL.get(i).getFilePath()).into(holder.main_img);

        ((RequestBuilder) ((RequestBuilder) Glide.with(this.mContext_act).load(((Video_Model) this.mList_AL.get(i)).getFilePath())).centerCrop()).into(holder.main_img);

        Log.e("jfgfgfjgjhghj", "onBindViewHolder: "+mList_AL.get(i).getFileName() );
        holder.name.setText(mList_AL.get(i).getFileName());

        String path = mList_AL.get(i).getFilePath();
        Uri uri = Uri.parse(path);

        int duration = Integer.parseInt(mList_AL.get(i).getFileSize());
        String dur = time_Conversion_Method(duration);
        holder.duration.setText(dur);

        File file = new File(mList_AL.get(i).getFilePath());
        Date lastModDate = new Date(file.lastModified());

        SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM-dd");
        String formats1 = formatter5.format(lastModDate);
        holder.date.setText(formats1);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext_act, Share_Actvty.class);
                intent.putExtra("File_Path", path);
                mContext_act.startActivity(intent);
            }
        });

        Context wrapper = new ContextThemeWrapper(mContext_act, R.style.PopupMenu);

        PopupMenu popupMenu = new PopupMenu(wrapper, holder.menu);
        Field declaredField = null;
        try {
            declaredField = popupMenu.getClass().getDeclaredField("mPopup");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        declaredField.setAccessible(true);
        Object obj = null;
        try {
            obj = declaredField.get(popupMenu);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            obj.getClass().getDeclaredMethod("setForceShowIcon", new Class[]{Boolean.TYPE}).invoke(obj, new Object[]{true});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        popupMenu.inflate(R.menu.file_menu);

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                switch (itemId) {
                    case R.id.delete:

                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            Log.e("ag--12", "below 11 : ");
                            mDialog_obj = new Dialog(mContext_act, R.style.CustomDialog);
                            mDialog_obj.setContentView(R.layout.dialogg_deletee_videoo);
                            mDialog_obj.findViewById(R.id.txtDialogCancel).setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    mDialog_obj.dismiss();
                                }
                            });

                            mDialog_obj.findViewById(R.id.txtDialogConfirm).setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    File file = new File(mList_AL.get(i).getFilePath()).getAbsoluteFile();
                                    file.delete();
                                    mList_AL.remove(i);
                                    notifyDataSetChanged();
                                    Toast.makeText(mContext_act, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    mContext_act.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
                                    MediaScannerConnection.scanFile(mContext_act, new String[]{new File(file.toString()).getAbsolutePath()},
                                            new String[]{file.toString().endsWith(".mp4") ? "video/*" : "mp3/*"},
                                            new MediaScannerConnection.MediaScannerConnectionClient() {
                                                public void onMediaScannerConnected() {
                                                }

                                                public void onScanCompleted(String str, Uri uri) {
                                                }
                                            });
                                    mDialog_obj.dismiss();
                                }
                            });
                            mDialog_obj.show();
                            return true;
                        } else {
                            Log.e("ag--12", "11 and above : ");
                            delete_File_API_Method(mList_AL.get(i), i);
                            return true;
                        }

                    case R.id.share:
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        Uri uri2 = Uri.fromFile(new File(mList_AL.get(i).getFilePath().toString()));
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.putExtra(Intent.EXTRA_SUBJECT, mContext_act.getResources().getString(R.string.app_name));
                        String shareMessage = mContext_act.getString(R.string.share_app) + "\n\n" + "https://play.google.com/store/apps/details?id=" + mContext_act.getPackageName();
                        share.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        share.setType("video/*");
                        share.putExtra(Intent.EXTRA_STREAM, uri2);
                        mContext_act.startActivity(Intent.createChooser(share, "Share video File"));
                        return true;
                }
                return false;
            }
        });
    }

    public String time_Conversion_Method(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }

    private int get_Media_Duration_Method(Uri uri) {
        MediaPlayer create = MediaPlayer.create(mContext_act, uri);
        if (create == null || create.getDuration() < 0) {
            return 0;
        }
        int duration = create.getDuration();
        Log.d("Urlisss", uri.toString());
        return duration;
    }

    public void delete_File_API_Method(Video_Model data2, int adapterPosition) {
        pure_var = adapterPosition;
        File imgFile = new File(String.valueOf(data2.getFilePath()));

        MediaScannerConnection.MediaScannerConnectionClient mediaScannerClient = new
                MediaScannerConnection.MediaScannerConnectionClient() {
                    private MediaScannerConnection msc = null;

                    {
                        msc = new MediaScannerConnection(mContext_act, this);
                        msc.connect();
                    }

                    public void onMediaScannerConnected() {
                        msc.scanFile(String.valueOf(imgFile), null);
                    }

                    public void onScanCompleted(String path, Uri uri) {
                        Collection<Uri> urilist = new ArrayList<>();
                        urilist.add(uri);
                        PendingIntent pendingIntent = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                            pendingIntent = MediaStore.createDeleteRequest(mContext_act.getContentResolver(), urilist);
                        }
                        try {
                            mContext_act.startIntentSenderForResult(pendingIntent.getIntentSender(), 1233, null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        msc.disconnect();
                    }
                };
    }

    @Override
    public int getItemCount() {
        return mList_AL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView main_img, menu;
        TextView name;
        TextView date;
        TextView duration;
        RelativeLayout item;

        public ViewHolder(View view) {
            super(view);
            main_img = (ImageView) view.findViewById(R.id.main_img);
            menu = (ImageView) view.findViewById(R.id.menu);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            duration = (TextView) view.findViewById(R.id.duration);
            item = (RelativeLayout) view.findViewById(R.id.item);
        }
    }
}
