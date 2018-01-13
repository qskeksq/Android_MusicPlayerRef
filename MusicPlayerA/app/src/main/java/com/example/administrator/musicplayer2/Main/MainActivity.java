package com.example.administrator.musicplayer2.Main;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.musicplayer2.Const;
import com.example.administrator.musicplayer2.ContainerFragment.ContainerFragment;
import com.example.administrator.musicplayer2.Detail.DetailFragment;
import com.example.administrator.musicplayer2.List.ListFragment;
import com.example.administrator.musicplayer2.MyService;
import com.example.administrator.musicplayer2.R;
import com.example.administrator.musicplayer2.Util.CheckPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements CheckPermission.CallBack, ListFragment.OnListFragmentInteraction, DetailFragment.PlayerInterface {

    ListFragment listFragment;
    DetailFragment detailFragment;
    ContainerFragment containerFragment;

    Intent service;

    ViewPager viewPager;
    TabLayout tab;
    List<Fragment> datas;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = new Intent(this, MyService.class);

        // 볼륨 조절 버튼으로 미디어 음량만 조절하기 -- 이거 안하면 알림은, 벨소리 조정됨.
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        listFragment = ListFragment.newInstance();
        detailFragment = DetailFragment.newInstance();
        containerFragment = ContainerFragment.newInstance();


//        CheckPermission.checkVersion(this);

//        setPagerDatas();
//        setViews();
//        setTab();

//        adapter = new PagerAdapter(getSupportFragmentManager());

//        viewPager.setAdapter(adapter);

        CheckPermission.checkVersion(this);

//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
//        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    public void init(){

        // 생성자에 비즈니스 로직이 있는 것은 오류가 날 가능성이 매우 크다. 웬만하면 생성자에서는 생성만 하자
                                    //setFragment(ListFragment.newInstance());
//        setFragment(listFragment);  // 이렇듯 생성할 것은 가장 앞에서 미리 생성해 두고 나중에 가져다 쓰는 방식으로 하자,
                                    // 생성과 그 생성한 것의 자원 사용을 한 곳에서 하면 오류 찾기가 매우 힘들어짐.
//        viewPager.setAdapter(adapter);

        setFragment(containerFragment);
    }

//    public void setViews(){
//
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        tab = (TabLayout) findViewById(R.id.tab);
//    }
//
//    public void setTab() {
//
//        tab.addTab(tab.newTab().setText("전체"));
//        tab.addTab(tab.newTab().setText("앨범"));
//    }
//
//    public void setPagerDatas(){
//
//        datas = new ArrayList<>();
//
//        datas.add(listFragment);
//    }


    public void setFragment(Fragment fragment){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void addFragment(Fragment fragment){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        CheckPermission.onResult(requestCode, grantResults, this);
    }

    @Override
    public void callInit() {

        init();
    }

    @Override
    public void onListFragmentInteraction(int position, String title) {

        detailFragment.setAlbumTitle(title);
        detailFragment.setPosition(position);
        addFragment(detailFragment);

//        addFragment(DetailFragment.newInstance(position));
        Log.e("메인에서 포지션 확인 final", position+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }









    @Override
    public void initPlayer(){

    }

    @Override
    public void playPlayer(){

        service.setAction(Const.Action.PLAY);
        startService(service);
    }

    @Override
    public void pausePlayer(){

        service.setAction(Const.Action.PAUSE);
        startService(service);
    }

    @Override
    public void replayPlayer(){

        service.setAction(Const.Action.REPLAY);
        startService(service);
    }


    @Override
    public void stopPlayer(){

        service.setAction(Const.Action.STOP);
        startService(service);
    }


    @Override
    public void changePlayIcon() {

        listFragment.setPlay();
    }

    @Override
    public void changePauseIcon() {

        listFragment.setPause();
    }

    @Override
    public void setListImage(String albumUri) {

        listFragment.setListImage(albumUri);
    }

    @Override
    public void setListText(String title) {

        listFragment.setListText(title);
    }


//    class PagerAdapter extends FragmentStatePagerAdapter {
//
//
//        public PagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return datas.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return datas.size();
//        }
//
//    }

}
