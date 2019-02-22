package com.view.loop.overlap.why.yoon.ch.sample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.adapter.OverlapLoopViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends OverlapLoopViewAdapter<SampleViewHolder> {

    private List<SampleItem> itemList = new ArrayList<>();

    @Override
    public SampleViewHolder onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View menuItemView = layoutInflater.inflate(R.layout.item_sample_layout, viewGroup, false);
        return new SampleViewHolder(menuItemView);
    }

    @Override
    public void onBindView(@NonNull SampleViewHolder viewHolder) {
        int itemPosition = viewHolder.getItemPosition();
        viewHolder.setItem(itemList.get(itemPosition));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(@NonNull List<SampleItem> itemList) {
        this.itemList = itemList;
        notifyDataSetChange();
    }

    @Nullable
    public SampleItem getTopViewData() {
        if(hasData()) {
            return itemList.get(getTopViewIndex());
        } else {
            return null;
        }
    }

    private boolean hasData() {
        return 0 < itemList.size();
    }

}
