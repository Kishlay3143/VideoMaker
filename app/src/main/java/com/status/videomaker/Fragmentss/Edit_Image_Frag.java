package com.status.videomaker.Fragmentss;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.status.videomaker.Activitiess.Video_Maker_Act;
import com.status.videomaker.R;

public class Edit_Image_Frag extends Fragment {
    LinearLayout edit_click_LL;
    private Video_Maker_Act activity_OBJ;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity_OBJ = (Video_Maker_Act) context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.editt_imagee_fragmentt, viewGroup, false);

        edit_click_LL = inflate.findViewById(R.id.editImage);
        edit_click_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_OBJ.start_Image_Editing_Method();
            }
        });
        return inflate;
    }
}
