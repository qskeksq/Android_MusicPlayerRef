package com.example.administrator.mymusic.List.SMusicList;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mymusic.R;

/**
 * Created by Administrator on 2017-06-28.
 */

public class SMusicListView implements ISMusicListView{

    View layout;                      // 사용 자원
    Context context;

    RecyclerView recyclerView;  // 사용 위젯


    public SMusicListView(LayoutInflater inflater, ViewGroup container) {

        this.context = container.getContext();

        inflateLayout(inflater, container);  // 1. 순위 바뀌면 안 됨
        setViews();
        setRecycler();

    }

    public void inflateLayout(LayoutInflater inflater, ViewGroup container){
        layout = inflater.inflate(R.layout.fragment_smusic, container, false);

    }

    public void setViews(){
        recyclerView = (RecyclerView) layout.findViewById(R.id.sListView);

    }


    public void setRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    @Override
    public View returnView(){
        return layout;

    }

    @Override
    public void setAdapter(SMusicListAdapter adapter){
        recyclerView.setAdapter(adapter);

    }




}
