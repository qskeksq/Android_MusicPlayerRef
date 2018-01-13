package com.example.administrator.mymusic.List.FolderList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mymusic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FolderListFragment extends Fragment {


    public static FolderListFragment newInstance(){

        FolderListFragment fragment = new FolderListFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder, container, false);
    }

}
