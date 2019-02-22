package com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.OverlapLoopView;

public abstract class OverlapLoopViewAdapter<VH extends OverlapLoopView.ViewHolder> {

    private OverlapLoopViewAdapterDataObserver overlapLoopViewAdapterDataObserver;

    private int topViewIndex = 0;

    public void setOverlapLoopViewAdapterDataObserver(@NonNull OverlapLoopViewAdapterDataObserver overlapLoopViewAdapterDataObserver) {
        this.overlapLoopViewAdapterDataObserver = overlapLoopViewAdapterDataObserver;
    }

    public void moveTopViewIndexToNext() {
        if(topViewIndex < getItemCount() - 1) {
            topViewIndex ++;
        } else {
            topViewIndex = 0;
        }
    }

    public int getTopViewIndex() {
        return topViewIndex;
    }

    protected void notifyDataSetChange() {
        if(overlapLoopViewAdapterDataObserver != null) {
            overlapLoopViewAdapterDataObserver.notifyDataSetChange();
        }
    }

    abstract public VH onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup);

    abstract public void onBindView(@NonNull VH viewHolder);

    abstract public int getItemCount();

}
