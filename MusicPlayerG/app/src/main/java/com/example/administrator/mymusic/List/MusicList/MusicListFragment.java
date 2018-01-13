package com.example.administrator.mymusic.List.MusicList;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mymusic.Main.IDetailInteraction;
import com.example.administrator.mymusic.domain.music.Music;
import com.example.administrator.mymusic.domain.music.MusicLab;

import java.util.List;


public class MusicListFragment extends Fragment {

    IDetailInteraction detailInteraction;

    IMusicListView musicListView;

    MusicListAdapter adapter;

    List<Music> data;



    public static MusicListFragment newInstance(){
        MusicListFragment fragment = new MusicListFragment();
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getMusicView(inflater, container);

        loadData();

        getAdapter(data, detailInteraction);

        setAdapter(adapter);

        return musicListView.returnView();

    }


    public void getMusicView(LayoutInflater inflater, ViewGroup container){

        musicListView = new MusicListView(inflater, container);

    }

    public void loadData(){
        MusicLab.getInstance(getContext()).loadData();
        data = MusicLab.getInstance(getContext()).getDatas();

    }

    public void getAdapter(List<Music> data, IDetailInteraction interaction){
        adapter = new MusicListAdapter(data, interaction);

    }

    public void setAdapter(MusicListAdapter adapter){
        musicListView.setAdapter(adapter);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IDetailInteraction){
            detailInteraction = (IDetailInteraction) context;
        }

    }


}
