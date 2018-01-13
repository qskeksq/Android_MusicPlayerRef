package com.example.administrator.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.musicplayer.adapter.PlayPagerAdapter;
import com.example.administrator.musicplayer.domain.Song;
import com.example.administrator.musicplayer.domain.SongLab;
import com.example.administrator.musicplayer.lib.Const;

import java.util.List;

public class Play2Activity extends AppCompatActivity implements IObserver {

    private int currentPosition;
    private List<Song> songList;
    private Song currentSong;


    private ViewPager viewPager;
    private ImageView play, next, prev;
    private TextView time, curTime;
    private TextView songTitle;
    private TextView songArtist;
    private ProgressBar progressBar;
    private ConstraintLayout playLayout;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // 1. 데이터 세팅
        initData();
        // 2. 뷰 세팅
        initView();
        setListener();
        // 3. 뷰페이저 세팅
        setSongPager();
        // 4. 초기 미디어 플레이어 세팅
        setPlayer();
        startPlayer();
        // 5. 화면 설정
        setWindowInfo();

        Observer.getInstance().addObserver(this);
    }

    /**
     * 데이터 세팅
     */
    private void initData(){
        currentPosition = getIntent().getIntExtra(Const.PLAY_POSITION_KEY, 0);
        songList = SongLab.getInstance().getSongList();
        currentSong = songList.get(currentPosition);
    }

    /**
     * 뷰 초기화
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        play = (ImageView) findViewById(R.id.play);
        next = (ImageView) findViewById(R.id.next);
        prev = (ImageView) findViewById(R.id.prev);
        time = (TextView) findViewById(R.id.time);
        curTime = (TextView) findViewById(R.id.curTime);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        playLayout = (ConstraintLayout) findViewById(R.id.play_layout);
        songTitle = (TextView) findViewById(R.id.songTitle);
        songArtist = (TextView) findViewById(R.id.songArtist);
    }

    /**
     * 리스너 세팅
     */
    private void setListener(){
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Player.getInstance().isPlaying()){
                    pausePlayer();
                } else {
                    startPlayer();
                }
            }
        });
    }

    /**
     * 뷰페이저 세팅
     */
    private void setSongPager(){
        PlayPagerAdapter adapter = new PlayPagerAdapter(this, songList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                viewPager.setCurrentItem(currentPosition);
                setCurrentSong();
                setPlayer();
                startPlayer();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 미디어 플레이어 세팅
     */
    private void setPlayer(){
        Intent service = new Intent(this, PlayerService.class);
        service.setAction(Const.ACTION_SET);
        service.putExtra(Const.SERVICE_PLAY_POSITION_KEY, currentPosition);
        startService(service);
    }

    /**
     * 미디어 플레이어 재생
     */
    private void startPlayer(){
        play.setImageResource(R.drawable.round_pause_button);
        Intent service = new Intent(this, PlayerService.class);
        service.setAction(Const.ACTION_PLAY);
        startService(service);
    }

    /**
     * 미디어 플레이어 일시정지
     */
    private void pausePlayer(){
        Intent service = new Intent(this, PlayerService.class);
        service.setAction(Const.ACTION_PAUSE);
        startService(service);
    }

    /**
     * 현재 곡 설정
     */
    private void setCurrentSong(){
        currentSong = songList.get(currentPosition);
    }

    /**
     * 화면 세팅
     */
    private void setWindowInfo(){

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void setPlayButton() {
        play.setImageResource(R.drawable.play_rounded_button);
    }

    @Override
    public void setPauseButton() {
        play.setImageResource(R.drawable.round_pause_button);
    }

    @Override
    public void setProgress() {

    }

    @Override
    public void setCurrentTime() {

    }
}
