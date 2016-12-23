package com.example.rashmi.nisclone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.animation.OvershootInterpolator;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class NIS_Clone extends AppCompatActivity implements NewsFragment.OnFragmentInteractionListener,ViewPager.OnTouchListener,GestureDetector.OnGestureListener {
    GestureDetectorCompat mDetector ;
    NewAdapter mAdapter;
    Toolbar toolbar;
    private static float MIN_SCALE = 0.75f;
    ViewPager vpPager;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mTouchSlop;
    private boolean mVerticalDrag;
    private boolean mHorizontalDrag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nis__clone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //mDetector=new GestureDetectorCompat(this, new MyGestureListener());
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        mAdapter=new NewAdapter(getSupportFragmentManager());
        vpPager.setAdapter(mAdapter);
        vpPager.setPageTransformer(true, new ZoomOutPageTransformer(getApplicationContext()));
        final ViewConfiguration configuration = ViewConfiguration.get(getApplicationContext());
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        
      //  vpPager.setOverScrollMode(OVER_SCROLL_NEVER);

      /* vpPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_DOWN:
                       return true;
                    case MotionEvent.ACTION_UP:
                            //toolbar.collapseActionView();


                }

                 return false;
            }
        });*/


    }

    /*class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
           /* try
            {
                float diffx=event2.getX()-event1.getX();
                float diffy=event2.getY()-event1.getY();

                if(Math.abs(diffx)>Math.abs(diffy))
                {
                    if(Math.abs(diffx)>GESTURE_THRESHOULD && Math.abs(velocityX)>GESTURE_VELOCITY_THRESHOULD)
                    {
                        if(diffx>0)
                        {
                            onSwipeRight();
                        }
                        else
                        {
                            onSwipeLeft();
                        }
                    }
                }
                else
                {
                    if(Math.abs(diffy)>GESTURE_THRESHOULD && Math.abs(velocityY)>GESTURE_VELOCITY_THRESHOULD)
                    {
                        if(diffy>0)
                        {
                            onSwipeBottom();
                        }
                        else
                        {
                            onSwipeTop();
                        }
                    }
                }
            }
            catch(Exception e)
            {
                Log.d(TAG, "" + e.getMessage());
            }
            return false;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nis__clone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":" + vpPager.getCurrentItem());
        //noinspection SimplifiableIfStatement
        Bundle args= page.getArguments();
        String headline=args.getString("headline");
        String description=args.getString("description");

        if (id == R.id.share) {
            int i=vpPager.getCurrentItem();

            //Log.d("PageNo",""+i);
            //Log.d("Page",""+num);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Check it out. Your message  goes here==>";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,""+shareBodyText);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, headline);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        try {
            //initializeParent();
            Log.d("ONTOUCH","INIDE ON TOUCH");
            final float x = ev.getX();
            final float y = ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN: {

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
                        return vpPager.onTouchEvent(ev);
                    } else if (mVerticalDrag) {
                        return verticalDrag(ev);
                    }
                }
                case MotionEvent.ACTION_UP: {
                    if (mHorizontalDrag) {
                        mHorizontalDrag = false;
                        return vpPager.onTouchEvent(ev);
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
            // The vpPager shouldn't be null, but just in case. If this happens,
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
    public boolean onDown(MotionEvent ev) {

        Log.d("TOUCH","DOWN");
        if (!vpPager.onTouchEvent(ev)) {
            return false;
        }
        return verticalDrag(ev);

    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final float x = e1.getX();
        final float y = e2.getY();
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
            return vpPager.onTouchEvent(e1);
        } else if (mVerticalDrag) {
            return verticalDrag(e1);
        }
        return false;
    }
}
