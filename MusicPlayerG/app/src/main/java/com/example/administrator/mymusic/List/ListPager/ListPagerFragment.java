package com.example.administrator.mymusic.List.ListPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mymusic.List.AlbumList.AlbumListFragment;
import com.example.administrator.mymusic.List.ArtistList.ArtistListFragment;
import com.example.administrator.mymusic.List.FolderList.FolderListFragment;
import com.example.administrator.mymusic.List.MusicList.MusicListFragment;

import java.util.ArrayList;
import java.util.List;


public class ListPagerFragment extends Fragment {

    IListPagerView pagerView;

    MusicListFragment musicListFragment;
    AlbumListFragment albumListFragment;
    FolderListFragment folderListFragment;
    ArtistListFragment artistListFragment;
    ListPagerAdapter adapter;

    List<Fragment> data;

    public static ListPagerFragment newInstance(){

        ListPagerFragment fragment = new ListPagerFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflating fragment view layout, as so, should be called ahead of all other methods
        pagerView = new ListPagerView(inflater, container);

        // setting pager
        newFragment();

        addData();

        newAdapter();

        setAdapter();

        return pagerView.returnLayout();
    }


    public void newFragment(){
        musicListFragment = MusicListFragment.newInstance();
        albumListFragment = AlbumListFragment.newInstance();
        folderListFragment = FolderListFragment.newInstance();
        artistListFragment = ArtistListFragment.newInstance();
    }


    public void addData(){
        data = new ArrayList<>();

        data.add(musicListFragment);
        data.add(albumListFragment);
        data.add(folderListFragment);
        data.add(artistListFragment);
    }


    public void newAdapter(){
        adapter = new ListPagerAdapter(getActivity().getSupportFragmentManager(), data);
    }


    /**
     * interface interaction
     */
    public void setAdapter(){
        pagerView.setPagerAdapter(adapter);
    }





}
