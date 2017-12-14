package com.example.nahuel.share;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by nahuel on 14/12/2017.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] {"Todas las tareas", "Perfil"};
    final int PAGE_COUNT = tabTitles.length;
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

//    @Override
//    public Fragment getItem(int position) {
//        return PersonaFragment.newInstance(position + 1);
//    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return AllTasksFragment.newInstance(position + 1);
            case 1:
                return ProfileFragment.newInstance(position + 1);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}