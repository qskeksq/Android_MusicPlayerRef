package com.example.administrator.mymusic.List.MusicList;

import android.view.View;

/**
 * Created by Administrator on 2017-06-28.
 */

public interface IMusicListView {

    View returnView();

    void setAdapter(MusicListAdapter adapter);

}
