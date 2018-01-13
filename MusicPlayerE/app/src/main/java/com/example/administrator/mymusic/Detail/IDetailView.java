package com.example.administrator.mymusic.Detail;

import android.view.View;

/**
 * Created by Administrator on 2017-06-29.
 */

public interface IDetailView {

    View returnView();
    void setAdapter(DetailPagerAdapter adapter);
}
