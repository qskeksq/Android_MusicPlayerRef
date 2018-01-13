package com.example.administrator.musicplayer2.List;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.musicplayer2.domain.Item;
import com.example.administrator.musicplayer2.domain.MusicLab;

import java.util.List;


public class ListFragment extends Fragment implements ListAdapter.OnListFragmentInter {

    OnListFragmentInteraction newListener;  // 넘겨줄 자원

    List<Item> datas;                       // 사용하는 자원
   public ListAdapter adapter;
    ListFragmentView listView;

    // 멀티턴
    public static ListFragment newInstance(){

        ListFragment listFragment = new ListFragment();

        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 1.
        createView(inflater, container);

        // 2.
        loadData();

        // 3.
        createAdapter();

        // 4. 여기서 모든 것이 시작
        setAdapter();

        return listView.view;
    }

    // 뷰 객체 생성
    public void createView(LayoutInflater inflater, ViewGroup container){

        listView = new ListFragmentView(inflater, container);
    }

    // 데이터 가져오기
    public List<Item> loadData(){

        MusicLab music = MusicLab.getInstance(getContext());
        music.Loader();                                         // 로딩해 주지 않으면 데이터가 뜨지 않는다.
        datas = music.getDatas();
        return datas;
    }

    // 어댑터 생성
    public void createAdapter(){

        adapter = new ListAdapter(datas, newListener, this);

    }

    // 리스트뷰에 어댑터 설정
    public void setAdapter(){

        listView.setAdapter(adapter);
    }


    // 액티비티를 넘겨주기 위해 인터페이스로 타입 변환
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnListFragmentInteraction){

            newListener = (OnListFragmentInteraction) context;
        }
    }

    // 인터페이스
    public interface OnListFragmentInteraction {

        void onListFragmentInteraction(int position, String title);
    }









    @Override
    public void setListImage(String albumUri) {

        listView.setListImage(albumUri);
//        Log.e("ListFragment", albumUri+"");
    }

    @Override
    public void setListText(String title) {

        listView.setListText(title);
//        Log.e("ListFragment", "setListImage");
    }

    @Override
    public void setPlay() {

        listView.setPlay();
//        Log.e("ListFragment", "setPlay");
    }

    @Override
    public void setPause() {

        listView.setPause();
//        Log.e("ListFragment", "setPause");
    }

}

