package com.example.administrator.musicplayer2.Main;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.administrator.musicplayer2.R;


public class MainView {

    ViewPager viewPager;
    TabLayout tab;
    Activity activity;

    public MainView(Activity activity){

        this.activity = activity;
    }

    public void setViews(){

        viewPager = (ViewPager) activity.findViewById(R.id.viewPager);
        tab = (TabLayout) activity.findViewById(R.id.tab);
    }

    public void setTab(){
        tab.addTab(tab.newTab().setText("전체"));
        tab.addTab(tab.newTab().setText("앨범"));
        tab.addTab(tab.newTab().setText("폴더"));
    }

//    class PagerAdapter extends FragmentStatePagerAdapter {
//
//
//        public PagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return datas.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return datas.size();
//        }
//
//    }

}
