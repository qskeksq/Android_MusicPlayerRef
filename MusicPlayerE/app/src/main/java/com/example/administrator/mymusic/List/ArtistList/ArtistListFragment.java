package com.example.administrator.mymusic.List.ArtistList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mymusic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistListFragment extends Fragment {


    public static ArtistListFragment newInstance(){
        ArtistListFragment fragment = new ArtistListFragment();
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

}
