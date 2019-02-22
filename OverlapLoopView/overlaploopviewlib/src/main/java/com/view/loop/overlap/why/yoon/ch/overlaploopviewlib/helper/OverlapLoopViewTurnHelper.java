package com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.OverlapLoopView;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.animation.OnAnimationStateListener;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.animation.OverlapLoopViewAnimationHelper;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.animation.OverlapLoopViewDefaultAnimation;
import com.view.loop.overlap.why.yoon.ch.overlaploopviewlib.model.DetachState;

public class OverlapLoopViewTurnHelper {

    enum ViewTouchArea {
        TOP, BOTTOM, NONE
    }

    private static final float ROTATE_SENSITIVITY = 0.03f;
    private static final int DRAG_CALCULATE_UNIT_MS = 100;

    private OverlapLoopViewAnimationHelper overlapLoopViewAnimationHelper;
    private OnTopViewDetachStateListener onTopViewDetachStateListener;

    private OverlapLoopView overlapLoopView;
    private RectF oldTopViewRect;
    private RectF newTopViewRect;
    private PointF downTouchCoordinates;
    private PointF moveTouchCoordinates;
    private ViewTouchArea viewTouchArea;

    private boolean isTouchingTopView = false;
    private boolean isAnimationPlaying = false;

    private DetachStateCalculator detachStateCalculator;
    private VelocityTracker velocityTracker;
    private float xSpeedWithBasedPX;
    private float ySpeedWithBasedPX;


    public OverlapLoopViewTurnHelper(Context context) {
        setUp(context);
    }

    private void setUp(Context context) {
        overlapLoopViewAnimationHelper = new OverlapLoopViewDefaultAnimation();
        oldTopViewRect = new RectF();
        newTopViewRect = new RectF();
        downTouchCoordinates = new PointF();
        moveTouchCoordinates = new PointF();
        viewTouchArea = ViewTouchArea.NONE;
        detachStateCalculator = new DetachStateCalculator(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOverlapLoopView(@NonNull OverlapLoopView overlapLoopView) {
        this.overlapLoopView = overlapLoopView;
        this.overlapLoopView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!isAnimationPlaying) {
                    View topView = getTopView();
                    if(topView != null) {
                        return handlingTopViewTouch(topView, event);
                    }
                }

                return false;
            }
        });
    }

    public void setonTopViewDetachListener(@NonNull OnTopViewDetachStateListener onTopViewDetachStateListener) {
        this.onTopViewDetachStateListener = onTopViewDetachStateListener;
    }

    private View getTopView() {
        int topViewIndex = overlapLoopView.getChildCount() - 1;
        if(0 <= topViewIndex) {
            return overlapLoopView.getChildAt(topViewIndex);
        } else {
            return null;
        }
    }

    private boolean handlingTopViewTouch(View topView, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchingTopView = isTouchTopView(topView, event);
                if(isTouchingTopView) {
                    obtainVelocityTracker();
                    velocityTracker.addMovement(event);

                    recordCoordinatesOfTouchDown(event);
                    recordCoordinatesOfOldTopView(topView);
                    recordViewTouchArea();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchingTopView) {
                    velocityTracker.addMovement(event);

                    recordCoordinatesOfTouchMove(event);
                    PointF coordinatesToMove = calculateCoordinatesToMove();
                    moveTopViewToCoordinates(topView, coordinatesToMove);
                    rotateTopView(topView);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isTouchingTopView) {
                    isTouchingTopView = false;

                    recordDragSpeed();
                    recyclerVelocityTracker();

                    recordCoordinatesOfNewTopView(topView);
                    DetachState detachState = calculateDetachState();
                    if(detachState.isDetach() && isMoreChildView()) {
                        requestDetachAnimationToHelper(topView, detachState);
                    } else {
                        requestNotDetachAnimationToHelper(topView);
                    }
                }

                break;
        }

        return true;
    }

    private void obtainVelocityTracker() {
        if(velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }

    private void recyclerVelocityTracker() {
        if(velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    private void recordDragSpeed() {
        velocityTracker.computeCurrentVelocity(DRAG_CALCULATE_UNIT_MS);
        xSpeedWithBasedPX = velocityTracker.getXVelocity();
        ySpeedWithBasedPX = velocityTracker.getYVelocity();
    }

    private void recordCoordinatesOfTouchDown(MotionEvent event) {
        downTouchCoordinates.set(event.getX(), event.getY());
    }

    private void recordCoordinatesOfTouchMove(MotionEvent event) {
        moveTouchCoordinates.set(event.getX(), event.getY());
    }

    private void recordViewTouchArea() {
        if(downTouchCoordinates.y <= oldTopViewRect.centerY()) {
            viewTouchArea = ViewTouchArea.TOP;
        } else {
            viewTouchArea = ViewTouchArea.BOTTOM;
        }
    }

    private boolean isTouchTopView(View topView, MotionEvent event) {
        RectF topViewRect = getNowTopViewRect(topView);
        float x = event.getX();
        float y = event.getY();

        return topViewRect.left <= x && x <= topViewRect.right
                && topViewRect.top <= y && y <= topViewRect.bottom;
    }

    private void recordCoordinatesOfOldTopView(View topView) {
        oldTopViewRect.set(getNowTopViewRect(topView));
    }

    private void recordCoordinatesOfNewTopView(View topView) {
        newTopViewRect.set(getNowTopViewRect(topView));
    }

    private RectF getNowTopViewRect(View topView) {
        float left = topView.getX();
        float top = topView.getY();
        float right = left + topView.getWidth();
        float bottom = top + topView.getHeight();

        return new RectF(left, top, right, bottom);
    }

    private PointF calculateCoordinatesToMove() {
        float movingDistanceX = moveTouchCoordinates.x - downTouchCoordinates.x;
        float movingDistanceY = moveTouchCoordinates.y - downTouchCoordinates.y;
        float moveCoordinatesX = oldTopViewRect.left + movingDistanceX;
        float moveCoordinatesY = oldTopViewRect.top + movingDistanceY;

        return new PointF(moveCoordinatesX, moveCoordinatesY);
    }

    private void moveTopViewToCoordinates(View topView, PointF moveTouchCoordinates) {
        topView.setX(moveTouchCoordinates.x);
        topView.setY(moveTouchCoordinates.y);
    }

    private void rotateTopView(View topView) {
        float rotationAngle = (downTouchCoordinates.x - moveTouchCoordinates.x) * ROTATE_SENSITIVITY;
        switch (viewTouchArea) {
            case TOP:
                topView.setRotation(rotationAngle * -1);
                break;
            case BOTTOM:
                topView.setRotation(rotationAngle);
                break;
        }
    }

    private void requestDetachAnimationToHelper(View topView, DetachState detachState) {
        overlapLoopViewAnimationHelper.executeDetachAnimation(topView,
                oldTopViewRect,
                newTopViewRect,
                detachState.getXDragRatio(),
                detachState.getYDragRatio(),
                new OnAnimationStateListener() {
                    @Override
                    public void onAnimationStart() {
                        isAnimationPlaying = true;
                    }

                    @Override
                    public void onAnimationEnd() {
                        isAnimationPlaying = false;
                        if(onTopViewDetachStateListener != null) {
                            onTopViewDetachStateListener.onDetachTopView();
                        }
                    }
                }
        );
    }

    private void requestNotDetachAnimationToHelper(View topView) {
        overlapLoopViewAnimationHelper.executeNotDetachAnimation(topView,
                oldTopViewRect,
                newTopViewRect,
                new OnAnimationStateListener() {
                    @Override
                    public void onAnimationStart() {
                        isAnimationPlaying = true;
                    }

                    @Override
                    public void onAnimationEnd() {
                        isAnimationPlaying = false;
                    }
                }
        );
    }

    private DetachState calculateDetachState() {
        return detachStateCalculator.calculateDetachState(oldTopViewRect,
                newTopViewRect,
                xSpeedWithBasedPX,
                ySpeedWithBasedPX);
    }

    private boolean isMoreChildView() {
        return 1 < overlapLoopView.getChildCount();
    }

}
