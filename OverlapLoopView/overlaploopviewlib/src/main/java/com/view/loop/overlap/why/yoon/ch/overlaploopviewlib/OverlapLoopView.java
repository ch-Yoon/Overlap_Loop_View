package com.view.loop.overlap.why.yoon.ch.overlaploopviewlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.adapter.OverlapLoopViewAdapter;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.adapter.OverlapLoopViewAdapterDataObserver;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.helper.OnTopViewDetachStateListener;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.helper.OverlapLoopViewTurnHelper;

public class OverlapLoopView extends FrameLayout {

    private static final int CREATE_LIMIT_COUNT = 5;

    private OverlapLoopViewAdapter overlapLoopViewAdapter;

    public OverlapLoopView(@NonNull Context context) {
        super(context);

        setUpViewTurnHelper();
    }

    public OverlapLoopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setUpViewTurnHelper();
    }

    private void setUpViewTurnHelper() {
        OverlapLoopViewTurnHelper overlapLoopViewTurnHelper = new OverlapLoopViewTurnHelper(getContext());
        overlapLoopViewTurnHelper.setOverlapLoopView(this);
        overlapLoopViewTurnHelper.setonTopViewDetachListener(new OnTopViewDetachStateListener() {
            @Override
            public void onDetachTopView() {
                turnTopViewToBottom();
            }
        });
    }

    public void setOverlapLoopViewAdapter(@NonNull OverlapLoopViewAdapter overlapLoopViewAdapter) {
        this.overlapLoopViewAdapter = overlapLoopViewAdapter;
        this.overlapLoopViewAdapter.setOverlapLoopViewAdapterDataObserver(new OverlapLoopViewAdapterDataObserver() {
            @Override
            public void notifyDataSetChange() {
                initializeOverlapView();
            }
        });

        initializeOverlapView();
    }

    public int getTopViewIndex() {
        if(overlapLoopViewAdapter != null) {
            return overlapLoopViewAdapter.getTopViewIndex();
        } else {
            return -1;
        }
    }

    private void initializeOverlapView() {
        removeAllViews();
        createDefaultChildViews();
    }

    private void createDefaultChildViews() {
        if(hasAdapterData()) {
            int createViewCount = calculateCreateViewCount();
            for(int i=0; i<createViewCount; i++) {
                createViewHolderAndAddToBottom();
                preloadDataOfBottomView();
            }
        }
    }

    private boolean hasAdapterData() {
        return overlapLoopViewAdapter != null && 0 < overlapLoopViewAdapter.getItemCount();
    }

    private int calculateCreateViewCount() {
        return overlapLoopViewAdapter.getItemCount() > 1 ? CREATE_LIMIT_COUNT : 1;
    }

    private void createViewHolderAndAddToBottom() {
        OverlapLoopView.ViewHolder viewHolder = createViewHolderFromAdapter();
        setTagViewHolderToItemView(viewHolder);
        addViewToBottom(viewHolder.getItemView());
    }

    private OverlapLoopView.ViewHolder createViewHolderFromAdapter() {
        return overlapLoopViewAdapter.onCreateView(LayoutInflater.from(getContext()), this);
    }

    private void setTagViewHolderToItemView(OverlapLoopView.ViewHolder viewHolder) {
        View itemView = viewHolder.getItemView();
        itemView.setTag(viewHolder);
    }

    private void addViewToBottom(View itemView) {
        addView(itemView, 0);
    }

    private void preloadDataOfBottomView() {
        OverlapLoopView.ViewHolder viewHolder = getViewHolderFromBottomView();
        if(viewHolder == null) {
            removeViewAt(0);
            createViewHolderAndAddToBottom();
            viewHolder = getViewHolderFromBottomView();
        }

        int adapterItemPosition = calculateAdapterItemPosition();
        viewHolder.setItemPosition(adapterItemPosition);
        overlapLoopViewAdapter.onBindView(viewHolder);
    }

    private OverlapLoopView.ViewHolder getViewHolderFromBottomView() {
        View bottomView = getChildAt(0);
        return (OverlapLoopView.ViewHolder) bottomView.getTag();
    }

    private int calculateAdapterItemPosition() {
        return (overlapLoopViewAdapter.getTopViewIndex() + getChildCount() - 1) % overlapLoopViewAdapter.getItemCount();
    }

    private void turnTopViewToBottom() {
        moveTopViewPositionToNext();
        moveTopViewToBottom();
        preloadDataOfBottomView();
    }

    private void moveTopViewPositionToNext() {
        overlapLoopViewAdapter.moveTopViewIndexToNext();
    }

    private void moveTopViewToBottom() {
        View topView = removeTopView();
        addViewToBottom(topView);
    }

    private View removeTopView() {
        int topChildViewIndex = getChildCount() - 1;
        View topView = getChildAt(topChildViewIndex);
        removeView(topView);

        return topView;
    }

    public static abstract class ViewHolder {

        private View itemView;
        private int itemPosition;

        protected ViewHolder(@NonNull View itemView) {
            this.itemView = itemView;
        }

        void setItemPosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        public int getItemPosition() {
            return itemPosition;
        }

        @NonNull
        View getItemView() {
            return itemView;
        }

    }

}
