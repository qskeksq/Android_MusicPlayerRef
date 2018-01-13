package com.example.administrator.musicplayer2.ListAlbum;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.musicplayer2.R;
import com.example.administrator.musicplayer2.domain.Album;
import com.example.administrator.musicplayer2.domain.AlbumLab;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    RecyclerView recyclerView;

    List<Album> datas;

    AlbumAdapter albumAdapter;



    public static AlbumFragment newInstance(){ //

        AlbumFragment fragment = new AlbumFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_album, container, false);

        // 1.
        recyclerView = (RecyclerView) view.findViewById(R.id.albumRecycler);

        // 2.
        AlbumLab albumLab = AlbumLab.getInstance(container.getContext());
        albumLab.loader();
        datas = albumLab.getDatas();
//        MusicLab music = MusicLab.getInstance(getContext());
//        music.Loader();                                         // 로딩해 주지 않으면 데이터가 뜨지 않는다.
//        List<Item> datas = music.getDatas();

        // 3.
        albumAdapter = new AlbumAdapter(datas, getActivity().getSupportFragmentManager());

        // 4.
        recyclerView.setAdapter(albumAdapter);

        // 5.
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
