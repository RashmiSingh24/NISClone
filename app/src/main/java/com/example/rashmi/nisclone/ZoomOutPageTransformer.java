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
public class ZoomOutPageTransformer extends ViewPager implements ViewPager.PageTransformer,ViewPager.OnTouchListener {
    private ViewPager mParentViewPager;
    private static float MIN_SCALE = 0.75f;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mTouchSlop;
    private boolean mVerticalDrag;
    private boolean mHorizontalDrag=false;


    public ZoomOutPageTransformer(Context context) {
        super(context);
        setOverScrollMode(OVER_SCROLL_NEVER);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    public void transformPage(View view, float position) {
       // int pageWidth = view.getWidth();


        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        float alpha = 0;
        if (0 <= position && position <= 1) {
            alpha = 1 - position;
        } else if (-1 < position && position < 0) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
            float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horizontalMargin - verticalMargin / 2);
            } else {
                view.setTranslationX(-horizontalMargin + verticalMargin / 2);
            }

            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            alpha = position + 1;
        }

        view.setAlpha(alpha);
        view.setTranslationX(view.getWidth() * -position);
        float yPosition = position * view.getHeight();
        view.setTranslationY(yPosition);
    }
        /*if (position < -1) {
            // This page is way off-screen to the left
            view.setAlpha(0);
        } else if (position <= 1) {
            view.setAlpha(1);

            // Counteract the default slide transition
            view.setTranslationX(view.getWidth() * -position);

            // set Y position to swipe in from top
            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);
        } else {
            // This page is way off screen to the right
            view.setAlpha(0);
        }*/




    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views

        return intercepted;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("ONTOUCH","INSIDE ONTOUCH");
        return false;
    }
}
