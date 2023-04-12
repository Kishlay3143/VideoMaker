package com.status.videomaker.Fragmentss;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.status.videomaker.Activitiess.Video_Maker_Act;
import com.status.videomaker.R;

public class Video_Music_Frag extends Fragment implements View.OnClickListener {
    private Video_Maker_Act activity_obj;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity_obj = (Video_Maker_Act) context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragmentt_editt, viewGroup, false);
        inflate.findViewById(R.id.iv_edit_image).setOnClickListener(this);
        inflate.findViewById(R.id.iv_edit_music).setOnClickListener(this);
        return inflate;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_edit_image:
                this.activity_obj.start_Activity_For_Default_Music_Method();
                return;
            case R.id.iv_edit_music:
                this.activity_obj.start_Activity_For_Music_Method();
                return;
            default:
                return;
        }
    }
}
