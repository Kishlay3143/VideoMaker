package com.status.videomaker.Adapterss;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.status.videomaker.R;

public class Defaultt_Songg_Adptr extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] title;

    public Defaultt_Songg_Adptr(Activity context, String[] maintitle) {
        super(context, R.layout.default_song_item, maintitle);
        this.context = context;
        this.title = maintitle;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.default_song_item, null, true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        titleText.setText(title[position]);
        return rowView;
    }
}
