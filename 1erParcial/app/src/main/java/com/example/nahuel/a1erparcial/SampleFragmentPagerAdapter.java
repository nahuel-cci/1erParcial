package com.example.nahuel.a1erparcial;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by nahuel on 07/10/2017.
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PersonaFragment.newInstance(position + 1);
    }

//    @Override
//    public Fragment getItem(int position) {
//
//        switch (position) {
//            case 0:
//                TabFragment1 tab1 = new TabFragment1();
//                return tab1;
//            case 1:
//                TabFragment2 tab2 = new TabFragment2();
//                return tab2;
//            case 2:
//                TabFragment3 tab3 = new TabFragment3();
//                return tab3;
//            default:
//                return null;
//        }


        @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}