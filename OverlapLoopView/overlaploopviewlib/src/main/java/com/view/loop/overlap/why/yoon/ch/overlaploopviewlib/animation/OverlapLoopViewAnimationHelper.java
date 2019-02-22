package com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.animation;

import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;

public interface OverlapLoopViewAnimationHelper {

    void executeDetachAnimation(@NonNull View topView,
                                @NonNull RectF firstCoordinatesOfView,
                                @NonNull RectF lastCoordinatesOfView,
                                float xDragRatio,
                                float yDragRatio,
                                @NonNull OnAnimationStateListener onAnimationStateListener);

    void executeNotDetachAnimation(@NonNull View topView,
                                   @NonNull RectF firstCoordinatesOfView,
                                   @NonNull RectF lastCoordinatesOfView,
                                   @NonNull OnAnimationStateListener onAnimationStateListener);

}
