package com.example.administrator.mymusic.Detail;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.mymusic.PlayObserver.Controller;
import com.example.administrator.mymusic.PlayObserver.IController;
import com.example.administrator.mymusic.R;

/**
 * Created by Administrator on 2017-06-29.
 */

public class DetailView implements IController, IDetailView, View.OnClickListener {

    View layout;

    ImageView prev, play, next;
    ViewPager viewPager;
    TextView curPosition, length, title;
    ProgressBar progressBar;

    public DetailView(LayoutInflater inflater, ViewGroup container) {

        Controller.getInstance().addObserver(this);

        inflateLayout(inflater, container);
        setViews();
        setListener();

    }

    public void inflateLayout(LayoutInflater inflater, ViewGroup container){
        layout = inflater.inflate(R.layout.fragment_detail, container, false);

    }

    public void setViews(){
        viewPager = (ViewPager) layout.findViewById(R.id.detailPager);
        prev = (ImageView) layout.findViewById(R.id.detailPrev);
        play = (ImageView) layout.findViewById(R.id.detailPlay);
        next = (ImageView) layout.findViewById(R.id.detailNext);
        curPosition = (TextView) layout.findViewById(R.id.curTime);
        length = (TextView) layout.findViewById(R.id.length);
        title = (TextView) layout.findViewById(R.id.detailTitle);
        progressBar = (ProgressBar) layout.findViewById(R.id.detailProgress);

    }

    public void setListener(){
        prev.setOnClickListener(this);
        play.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View returnView() {
        return layout;
    }

    @Override
    public void setAdapter(DetailPagerAdapter adapter) {
        viewPager.setAdapter(adapter);

    }

    @Override
    public void playIcon() {

    }

    @Override
    public void pauseIcon() {

    }

    @Override
    public void albumArt(String albumUri) {

    }

    @Override
    public void musicTitle(String musicTitle) {

    }
}
