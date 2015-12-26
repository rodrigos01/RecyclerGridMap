package com.example.rodrigo.recyclergrid;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rodrigo on 26/12/15.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String mTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
