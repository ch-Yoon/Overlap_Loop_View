package com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.model;

public class DetachState {

    private boolean isDetach;
    private float xDragRatio;
    private float yDragRatio;

    public DetachState(boolean isDetach, float xDragRatio, float yDragRatio) {
        this.isDetach = isDetach;
        this.xDragRatio = xDragRatio;
        this.yDragRatio = yDragRatio;
    }

    public boolean isDetach() {
        return isDetach;
    }

    public float getXDragRatio() {
        return xDragRatio;
    }

    public float getYDragRatio() {
        return yDragRatio;
    }

}
