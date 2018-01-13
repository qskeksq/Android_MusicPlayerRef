package com.example.administrator.mymusic.List.AlbumList;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mymusic.Main.IInteraction;
import com.example.administrator.mymusic.domain.album.Album;
import com.example.administrator.mymusic.domain.album.AlbumLab;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumListFragment extends Fragment {

    IInteraction interaction;

    IAlbumListView albumListView;

    AlbumListAdapter adapter;

    List<Album> data;



    public static AlbumListFragment newInstance(){
        AlbumListFragment fragment = new AlbumListFragment();
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getAlbumView(inflater, container);

        loadData();

        getAdapter(data, interaction);

        setAdapter(adapter);

        return albumListView.returnView();

    }


    public void getAlbumView(LayoutInflater inflater, ViewGroup container){
        if(albumListView == null)
        albumListView = new AlbumListView(inflater, container);

    }

    public void loadData(){
        AlbumLab.getInstance(getContext()).loadData();
        data = AlbumLab.getInstance(getContext()).getDatas();

    }

    public void getAdapter(List<Album> data, IInteraction iInteraction){
        if(adapter == null)
        adapter = new AlbumListAdapter(data, interaction);

    }

    public void setAdapter(AlbumListAdapter adapter){
        albumListView.setAdapter(adapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof IInteraction){

            interaction = (IInteraction) context;
        }
    }

}
