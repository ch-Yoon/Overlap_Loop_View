package com.view.loop.overlap.why.yoon.ch.sample;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.OverlapLoopView;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {

    private OverlapLoopView overlapLoopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        ArrayList<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem(ContextCompat.getColor(this, R.color.sampleColorBlue)));
        sampleItems.add(new SampleItem(ContextCompat.getColor(this, R.color.sampleColorRed)));
        sampleItems.add(new SampleItem(ContextCompat.getColor(this, R.color.sampleColorGreen)));
        sampleItems.add(new SampleItem(ContextCompat.getColor(this, R.color.sampleColorYellow)));

        SampleAdapter sampleAdapter = new SampleAdapter();
        sampleAdapter.setItemList(sampleItems);

        overlapLoopView = findViewById(R.id.sampleOverlapLoopView);
        overlapLoopView.setOverlapLoopViewAdapter(sampleAdapter);


        Button sampleIndexCheckButton = findViewById(R.id.sampleButton);
        sampleIndexCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(overlapLoopView != null) {
                    Toast.makeText(SampleActivity.this, overlapLoopView.getTopViewIndex() + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
