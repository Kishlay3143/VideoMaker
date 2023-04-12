package com.status.videomaker.Adapterss;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.status.videomaker.Activitiess.Video_Maker_Act;
import com.status.videomaker.Fragmentss.Edit_Image_Frag;
import com.status.videomaker.Fragmentss.Video_Duration_Frag;
import com.status.videomaker.Fragmentss.Video_Filter_Frag;
import com.status.videomaker.Fragmentss.Video_Frame_Frag;
import com.status.videomaker.Fragmentss.Video_Music_Frag;
import com.status.videomaker.Fragmentss.Video_Text_Frag;
import com.status.videomaker.Fragmentss.Video_Transition_Frag;

public class Pager_Adpter extends FragmentStatePagerAdapter {
    private String[] tabs = {"THEME", "EDIT", "TIME", "FRAME", "TEXT", "EFFECT", "IMAGE"};

    public Pager_Adpter(@NonNull FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
    }

    @Override
    @NonNull
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Video_Maker_Act.disable_stickers_Method();
                return new Video_Transition_Frag();        //Transition
            case 1:
                Video_Maker_Act.disable_stickers_Method();
                return new Video_Music_Frag();     //music
            case 2:
                Video_Maker_Act.disable_stickers_Method();    //Filter
                return new Video_Filter_Frag();
            case 3:
                Video_Maker_Act.disable_stickers_Method();
                return new Video_Frame_Frag();    //frame
            case 4:
                Video_Maker_Act.disable_stickers_Method();
                return new Video_Text_Frag();     //text
            case 5:
                Video_Maker_Act.disable_stickers_Method();
                return new Video_Duration_Frag();     //duration
            case 6:
                Video_Maker_Act.disable_stickers_Method();
                return new Edit_Image_Frag();     //image
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tabs.length;
    }
}
