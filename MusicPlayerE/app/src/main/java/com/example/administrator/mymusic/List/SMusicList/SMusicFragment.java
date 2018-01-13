package com.example.administrator.mymusic.List.SMusicList;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mymusic.Main.IInteraction;
import com.example.administrator.mymusic.domain.music.Music;
import com.example.administrator.mymusic.domain.music.MusicLab;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SMusicFragment extends Fragment {


    IInteraction detailInteraction;

    ISMusicListView musicListView;

    SMusicListAdapter adapter;

    List<Music> data;

    String identifier;
    int category;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.e("카테고리", category+"999는 앨범");
        Log.e("식별자", identifier);

        getMusicView(inflater, container);

        // todo loadData setAdapter 에서 문제가 생길 것이다. onStart 로 가지 않으면 왜 그렇게 되는지 파악해보자
        loadData();

        getAdapter(data, detailInteraction);

        setAdapter(adapter);

        return musicListView.returnView();

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public static SMusicFragment newInstance(){
        SMusicFragment fragment = new SMusicFragment();
        return fragment;

    }


    public void getMusicView(LayoutInflater inflater, ViewGroup container){
        if(musicListView == null)
            musicListView = new SMusicListView(inflater, container);

    }

    public void loadData(){
        MusicLab lab = MusicLab.getInstance(getContext());
        data = lab.loadData(category , identifier);
        Log.e("넘어가는 데이터 크기", data.size()+"");

    }

    public void getAdapter(List<Music> data, IInteraction interaction){
        if(adapter == null);
        adapter = new SMusicListAdapter(data, interaction);

    }

    public void setAdapter(SMusicListAdapter adapter){
        musicListView.setAdapter(adapter);

    }


    // 아래 두 set 메소드가 가장 먼저 호출된다.
    public void setCategory(int category){
        this.category = category;

    }

    public void setIdentifier(String identifier){
        this.identifier = identifier;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IInteraction){
            detailInteraction = (IInteraction) context;
        }

    }

}
