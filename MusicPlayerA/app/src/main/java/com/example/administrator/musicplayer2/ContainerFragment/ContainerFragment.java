package com.example.administrator.musicplayer2.ContainerFragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.musicplayer2.List.ListFragment;
import com.example.administrator.musicplayer2.ListAlbum.AlbumFragment;
import com.example.administrator.musicplayer2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContainerFragment extends Fragment {


    View view;
    ViewPager viewPager;
    TabLayout tab;
    List<Fragment> datas;
    PagerAdapter adapter;

    ListFragment listFragment;
    AlbumFragment albumFragment;

    public static ContainerFragment newInstance(){ //

        ContainerFragment fragment = new ContainerFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_container_inter, container, false);

        listFragment = ListFragment.newInstance();
        albumFragment = AlbumFragment.newInstance();

        setViews();

        setTab();

        setPagerDatas();

        createAdapter();

        setAdapter();

        setListener();

        return view;
    }

    public void setViews(){

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tab = (TabLayout) view.findViewById(R.id.tab);
    }

    public void setTab() {

        tab.addTab(tab.newTab().setText("전체"));
        tab.addTab(tab.newTab().setText("앨범"));
    }

    public void setPagerDatas(){

        datas = new ArrayList<>();

        datas.add(listFragment);
        datas.add(albumFragment);
    }

    public void createAdapter(){

        adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
    }

    public void setAdapter(){

        viewPager.setAdapter(adapter);
    }

    public void setListener(){

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    class PagerAdapter extends FragmentStatePagerAdapter {


        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

    }

}
