package com.example.administrator.mymusic.List.ListPager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.mymusic.PlayObserver.Controller;
import com.example.administrator.mymusic.PlayObserver.IController;
import com.example.administrator.mymusic.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017-06-27.
 */

public class ListPagerView implements IListPagerView, IController{

    View pagerLayout;
    Activity activity;

    TabLayout listPagerTab;
    ViewPager listPagerPager;

    ImageView toolbarImg;
    TextView toolbarTitle;
    Button toolbarBtn;

    public ListPagerView(LayoutInflater inflater, ViewGroup container) {

        this.activity = activity;

        Controller.getInstance().addObserver(this);

        inflateLayout(inflater, container);

        setViews();

        setTabs();

        setListeners();

    }

    public void inflateLayout(LayoutInflater inflater, ViewGroup container){
        pagerLayout = inflater.inflate(R.layout.fragment_pager, container, false);
    }


    public void setViews(){
        listPagerTab = (TabLayout) pagerLayout.findViewById(R.id.pagerTab);
        listPagerPager = (ViewPager) pagerLayout.findViewById(R.id.listPager);

        toolbarImg = (ImageView) pagerLayout.findViewById(R.id.toolbarImg);
        toolbarTitle = (TextView) pagerLayout.findViewById(R.id.toolbarTitle);
        toolbarBtn = (Button) pagerLayout.findViewById(R.id.toolbarPlay);
    }

    public void setTabs(){
        listPagerTab.addTab(listPagerTab.newTab().setText("전체"));
        listPagerTab.addTab(listPagerTab.newTab().setText("앨범"));
        listPagerTab.addTab(listPagerTab.newTab().setText("폴더"));
        listPagerTab.addTab(listPagerTab.newTab().setText("가수"));

    }

    public void setListeners(){
        listPagerPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(listPagerTab));
        listPagerTab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(listPagerPager));
        toolbarBtn.setOnClickListener(statusListener);

    }

    View.OnClickListener statusListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "확인", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void setPagerAdapter(ListPagerAdapter adapter){
        listPagerPager.setAdapter(adapter);

    }


    /**
     *  return inflated view
     */
    @Override
    public View returnLayout() {

        return pagerLayout;
    }


    @Override
    public void playIcon() {
        toolbarBtn.setBackgroundResource(R.drawable.play);

    }

    @Override
    public void pauseIcon() {
        toolbarBtn.setBackgroundResource(R.drawable.pause);

    }

    @Override
    public void albumArt(String albumUri) {
        Glide.with(activity).load(albumUri).bitmapTransform(new CropCircleTransformation(activity)).into(toolbarImg);

    }

    @Override
    public void musicTitle(String musicTitle) {
        toolbarTitle.setText(musicTitle);

    }
}
