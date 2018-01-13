package com.example.administrator.mymusic.Detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mymusic.domain.music.Music;
import com.example.administrator.mymusic.domain.music.MusicLab;

import java.util.List;

import static com.example.administrator.mymusic.Util.Const.Key.DETAIL_POSITION;


public class DetailFragment extends Fragment {

    private int position;

    IDetailView detailView;
    DetailPagerAdapter adapter;

    List<Music> list;

    public static DetailFragment newInstance(int position){

        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DETAIL_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(DETAIL_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDetailView(inflater, container);

        getData();

        getPagerAdapter(list);

        detailView.setAdapter(adapter);

        return detailView.returnView();
    }


    public void getDetailView(LayoutInflater inflater, ViewGroup container){

        if(detailView == null)
        detailView = new DetailView(inflater, container);
    }

    // todo 이거 데이터 처리 어떻게 해줄까
    public void getData(){
        list = MusicLab.getInstance(getContext()).getDatas();

    }

    public void getPagerAdapter(List<Music> list){
        if(adapter == null)
            adapter = new DetailPagerAdapter(list);

    }








}
