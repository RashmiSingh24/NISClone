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


public class NIS_Clone extends AppCompatActivity implements NewsFragment.OnFragmentInteractionListener,ViewPager.PageTransformer{
    NewAdapter mAdapter;
    Toolbar toolbar;
    private static float MIN_SCALE = 0.75f;

   VerticalViewPager viewPager;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mTouchSlop;
    private boolean mVerticalDrag;
    private boolean mHorizontalDrag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nis__clone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //mDetector=new GestureDetectorCompat(this, new MyGestureListener());
        //vpPager = (ViewPager) findViewById(R.id.vpPager);
        mAdapter = new NewAdapter(getSupportFragmentManager());
        // vpPager.setAdapter(mAdapter);
        //vpPager.setPageTransformer(false, new ZoomOutPageTransformer(getApplicationContext()));


        //VerticalViewPager viewPager=new VerticalViewPager(getApplicationContext());
           viewPager = (VerticalViewPager) findViewById(R.id.vpPager);

       // CustomViewPager cvp=new CustomViewPager(getApplicationContext());

        viewPager.setAdapter(mAdapter);
      //  viewPager.setPageTransformer(false, new ZoomOutPageTransformer(getApplicationContext()));

        //  vpPager.setOverScrollMode(OVER_SCROLL_NEVER);


    }


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

        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":" + viewPager.getCurrentItem());
        //noinspection SimplifiableIfStatemenP
        Bundle args = page.getArguments();
        String headline = args.getString("headline");
        String description = args.getString("description");

        if (id == R.id.share) {
            int i = viewPager.getCurrentItem();

            //Log.d("PageNo",""+i);
            //Log.d("Page",""+num);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Check it out. Your message  goes here==>";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "" + shareBodyText);
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
    public void transformPage(View page, float position) {

    }
}
