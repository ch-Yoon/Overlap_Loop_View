package com.view.loop.overlap.why.yoon.ch.overlaploopview;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.OverlapLoopView;

public class SampleViewHolder extends OverlapLoopView.ViewHolder {

    private ImageView sampleImageView;
    private TextView sampleTextView;

    public SampleViewHolder(@NonNull View itemView) {
        super(itemView);

        sampleImageView = itemView.findViewById(R.id.sampleImageView);
        sampleTextView = itemView.findViewById(R.id.sampleTextView);
    }

    public void setItem(@NonNull SampleItem sampleItem) {
        sampleImageView.setBackgroundColor(sampleItem.getBackgroundColor());
        sampleTextView.setText(String.valueOf(getItemPosition()));
    }

}
