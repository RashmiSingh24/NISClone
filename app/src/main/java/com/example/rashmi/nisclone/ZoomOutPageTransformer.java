package com.example.rashmi.nisclone;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by rashmi on 19/12/16.
 */
public class ZoomOutPageTransformer extends ViewGroup implements ViewPager.PageTransformer,ViewPager.OnTouchListener {
    private ViewPager mParentViewPager;
    private static float MIN_SCALE = 0.75f;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mTouchSlop;
    private boolean mVerticalDrag;
    private boolean mHorizontalDrag=false;


    public ZoomOutPageTransformer(Context context) {
        super(context);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
/*
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            view.setAlpha(1);

            // Counteract the default slide transition
            view.setTranslationX(view.getWidth() * -position);

            //set Y position to swipe in from top
            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }*/

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);


        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            //view.setTranslationX(1);
            view.setScaleX(1);
            view.setScaleY(1);
            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);
            view.setTranslationX(-1 * view.getWidth() * position);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            view.setTranslationX(-1 * view.getWidth() * position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }





}
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views
        return intercepted;
    }*/

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        try {
            //initializeParent();
            final float x = ev.getX();
            final float y = ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    mLastMotionX = x;
                    mLastMotionY = y;
                    Log.d("TOUCH","DOWN");
                    if (!mParentViewPager.onTouchEvent(ev)) {
                        return false;
                    }
                    return verticalDrag(ev);
                }
                case MotionEvent.ACTION_MOVE: {
                    final float xDiff = Math.abs(x - mLastMotionX);
                    final float yDiff = Math.abs(y - mLastMotionY);
                    if (!mHorizontalDrag && !mVerticalDrag) {
                        Log.d("Swipe", "Inside 1");
                        if (xDiff > mTouchSlop && xDiff > yDiff) {
                            Log.d("Swipe", "Inside left/right");// Swiping left and right
                            mHorizontalDrag = false;
                        } else if (yDiff > mTouchSlop && yDiff > xDiff) {
                            Log.d("Swipe", "Inside up/down");
                            //Swiping up and down
                            mVerticalDrag = true;
                        }
                    }
                    if (mHorizontalDrag) {
                        return mParentViewPager.onTouchEvent(ev);
                    } else if (mVerticalDrag) {
                        return verticalDrag(ev);
                    }
                }
                case MotionEvent.ACTION_UP: {
                    if (mHorizontalDrag) {
                        mHorizontalDrag = false;
                        return mParentViewPager.onTouchEvent(ev);
                    }
                    if (mVerticalDrag) {
                        mVerticalDrag = false;
                        return verticalDrag(ev);
                    }
                }
            }
            // Set both flags to false in case user lifted finger in the parent view pager
            mHorizontalDrag = false;
            mVerticalDrag = false;
        } catch (Exception e) {
            // The mParentViewPager shouldn't be null, but just in case. If this happens,
            // app should not crash, instead just ignore the user swipe input
            // TODO: handle the exception gracefully
        }
        return false;
    }
    private boolean verticalDrag(MotionEvent ev) {

        final float x = ev.getX();
        final float y = ev.getY();
        ev.setLocation(y, x);
        Log.d("Swipe", "Inside verticaldrag");
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("ONTOUCH","INSIDE ONTOUCH");
        return false;
    }
}
