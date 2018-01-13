package com.example.administrator.mymusic.List.AlbumList;

import android.view.View;

/**
 * Created by Administrator on 2017-06-29.
 */

public interface IAlbumListView{

    View returnView();

    void setAdapter(AlbumListAdapter adapter);

}
