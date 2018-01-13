package com.example.administrator.musicplayer2.ListAlbum.MusicList;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.musicplayer2.R;
import com.example.administrator.musicplayer2.domain.Item;
import com.example.administrator.musicplayer2.domain.MusicLab;

import java.util.List;

import static com.example.administrator.musicplayer2.Const.KEY_ALBUM;
import static com.example.administrator.musicplayer2.List.ListFragment.OnListFragmentInteraction;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumMusicListFragment extends Fragment {


    RecyclerView recyclerView;

    List<Item> data;

    AlbumMusicListAdapter adapter;

    String title;

    OnListFragmentInteraction mListener;

    public static AlbumMusicListFragment newInstance(String proj){
        AlbumMusicListFragment fragment = new AlbumMusicListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ALBUM , proj);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(KEY_ALBUM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_album_music_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.albumListRecycler);

        Log.e("앨범리스트에서 넘어온 타이틀", title);
        data = MusicLab.getInstance(getContext()).itemLoader(title);
        Log.e("쿼리가 몇개나 됬는지 확인", data.size()+"");

        adapter = new AlbumMusicListAdapter(data, mListener);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    // 최대 의문은 이 context 를 어디서 받아오는가이다.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = (OnListFragmentInteraction) context;
    }
}
