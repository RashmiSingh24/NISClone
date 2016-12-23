package com.example.rashmi.nisclone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rashmi on 19/12/16.
 */
public class NewAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 5;
    public NewAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);}

    String message1="NEVER FEEL GUILTY FOR WANTING MORE OUT OF LIFE THAN AVERAGE";
    String message2="MAYBE YOU HAVE TO KNOW THE DARKNESS BEFORE YOU CAN APPRECIATE THE LIGGHT";
    String messsage3="REMIND YOURSELF THAT IT'S OKAY NOT TO BE PERFECT";
    String message4="OUR PRIME PURPOSE IN THIS IS TO HELP OTHERS & IF YOU CANT HELP THEM,AT LEAST DONT HURT THEM";
    String message5="YOU HAVE TWO EARS AND ONE MOUTH.FOLLOW THAT RATIO,LISTEN MORE,TALK LESS";
    String description="The world always seems brighter when you've just made something that wasn't there before.\n" +
            "Tomorrow may be hell, but today was a good writing day, and on the good writing days nothing else matters.\n" ;
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewsFragment.newInstance(message1,description, R.drawable.image1,"https://www.facebook.com/login/");
            case 1:
                return NewsFragment.newInstance(message2,description, R.drawable.image2,"https://www.facebook.com/login/");
            case 2:
                return NewsFragment.newInstance(messsage3, description,R.drawable.image3,"https://www.facebook.com/login/");
            case 3:
                return NewsFragment.newInstance(
                        message4,description,R.drawable.image4,"https://www.facebook.com/login/");
            case 4:
                return NewsFragment.newInstance(message5,description,R.drawable.image5,"https://www.facebook.com/login/");
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
