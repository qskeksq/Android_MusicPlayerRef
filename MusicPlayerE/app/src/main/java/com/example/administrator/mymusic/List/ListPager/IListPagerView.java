package com.example.administrator.mymusic.List.ListPager;

import android.view.View;

/**
 * Created by Administrator on 2017-06-27.
 */

public interface IListPagerView {

    View returnLayout();
    void setPagerAdapter(ListPagerAdapter adapter);

}
