package com.example.administrator.mymusic.List.ListPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017-06-27.
 */

public class ListPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> data;

    public ListPagerAdapter(FragmentManager manager, List<Fragment> data) {
        super(manager);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
