package com.example.administrator.mymusic.List.SMusicList;

import android.view.View;

/**
 * Created by Administrator on 2017-06-28.
 */

public interface ISMusicListView {

    View returnView();

    void setAdapter(SMusicListAdapter adapter);

}
