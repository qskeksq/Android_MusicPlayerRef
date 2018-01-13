package com.example.administrator.musicplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
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
import com.example.administrator.musicplayer.lib.NumberLib;

import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    int currentPosition;
    int PLAY_STATUS = Const.STAT_PLAY;

    private ViewPager viewPager;
    private ImageView play, next, prev;
    private TextView time, curTime;
    private TextView songTitle;
    private TextView songArtist;


    private MediaPlayer player;
    private SeekBarThread seekbarThread;
    private List<Song> songList;
    private ConstraintLayout playLayout;
    private ProgressBar progressBar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getWindow().setStatusBarColor(getResources().getColor(R.color.basic_background));
        initData();
        initView();
        setListener();
        initSongPager();
        setMedia();
        if (currentPosition == 0) {
            setPlayer();
        }
        play();


//        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_camera);
//        Bitmap blurredBitmap = BlurBuilder.blur(this, originalBitmap, 5);
//        playLayout.setBackground(new BitmapDrawable(getResources(), blurredBitmap));
    }

    /**
     * 1.재생할 음악 번호
     * 2.전체 목록
     */
    private void initData() {
        // 재생할 음악 번호 받아오기
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra(Const.PLAY_POSITION_KEY, 0);

        // 전체 음악 데이터
        songList = SongLab.getInstance().getSongList();
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
     * 리스너 설정
     */
    private void setListener() {
        play.setOnClickListener(this);
    }

    /**
     * 뷰페이저 설정
     */
    private void initSongPager() {
        PlayPagerAdapter adapter = new PlayPagerAdapter(this, songList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        // 처음 Play 페이지로 넘어올 때 설정할 페이지
        if (currentPosition > -1) {
            viewPager.setCurrentItem(currentPosition);
        }
    }

    /**
     * 뷰페이저 리스너
     */
    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
            setPlayer();
            if (PLAY_STATUS == Const.STAT_PLAY) {
                play();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 음악 관련 시스템 설정
     */
    private void setMedia() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    /**
     * 플레이어 초기화 & 설정
     */
    private void setPlayer() {

        // 1. player 를 release 하기 이전에 스레드가 player 를 더이상 참조하지 못하도록 막는다
        stopThread();

        // 2. 이전에 있던 음악 release
        if (player != null) {
            player.release();
            player = null;
        }
        // 3. 어떤 음악을 플레이 할지 설정
        Uri musicUri = songList.get(currentPosition).musicUri;
        player = MediaPlayer.create(this, musicUri);
        player.setLooping(false);

        // 4. 선택한 음원 정보 화면에 설정
        setPlayWindow();

        // 5. 음악이 실행되면서 음원 정보가 실시간으로 갱신되도록 스레드 시작
        startThread();
    }

    /**
     * 선택한 음원 정보 화면에 설정
     */
    private void setPlayWindow() {
        // 화면 세팅
        String duration = NumberLib.getInstance().miliToSec(player.getDuration());
        time.setText(duration);
        progressBar.setMax(player.getDuration());
        songTitle.setText(songList.get(currentPosition).title);
        songArtist.setText(songList.get(currentPosition).artist);
    }

    /**
     * 음악이 실행되면서 음원 정보가 실시간으로 갱신되도록 스레드 시작
     */
    private void startThread() {
        seekbarThread = new SeekBarThread(handler, player);
        seekbarThread.start();
    }

    /**
     * player 를 release 하기 이전에 스레드가 player 를 더이상 참조하지 못하도록 막는다
     */
    private void stopThread() {
        // play 이외의 seekBar, 시간 설정 스레드 멈춰주기
        if (seekbarThread != null) {
            seekbarThread.stopFlag();
        }
    }

    /**
     * 재생시 변경 사항 설정
     */
    private void play() {
        PLAY_STATUS = Const.STAT_PLAY;
        player.start();
        play.setImageResource(R.drawable.round_pause_button);
    }

    /**
     * 일시정지시 변경 사항 설정
     */
    private void pause() {
        PLAY_STATUS = Const.STAT_PAUSE;
        player.pause();
        play.setImageResource(R.drawable.play_rounded_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (PLAY_STATUS == Const.STAT_PLAY) {
                    pause();
                } else if (PLAY_STATUS == Const.STAT_PAUSE) {
                    play();
                }
                break;
            case R.id.prev:
                break;
            case R.id.next:
                break;
        }
    }

    /**
     * 스레드를 다룰 때는 생명주기가 매우 중요하다. 먼저 죽고 사는 순서에 따라 오류가 발생하기도 발생하지 않기도 한다
     */
    @Override
    protected void onDestroy() {
        // player 는 메인스레드 생명주기와 함께 죽기 때문에 메인이 죽기 전에 서브스레드를 먼저 멈춘다.
        if (seekbarThread != null) {
            seekbarThread.stopFlag();
        }
        // 음악 release
        if (player != null) {
            player.release();
        }
        super.onDestroy();
    }

    /**
     * 서브스레드에서 메인 화면 갱신하기 위한 핸들러
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (player != null) {
                // player 도 스레드에서 동작한다는 것을 안다면 핸들러에서 또다시 스레드를 다루는 것은 좋지 않다.
                // player 가 null 이 아닌 상황으로 이 안에 들어와서 getCurrentPosition()하는 도중 player.release()
                // 된다면 illegal 오류가 생긴다. 따라서 객체 자체를 조작하도록 넘기기보다는 값을 넘기는 것이 중요하다
                //int position = player.getCurrentPosition();
                int position = msg.what;
                progressBar.setProgress(position);
                curTime.setText(NumberLib.getInstance().miliToSec(position));
            }
        }
    };

    /**
     * 스레드 시크바
     */
    class SeekBarThread extends Thread {

        public boolean runFlag = true;
        private Handler handler;
        private MediaPlayer player;

        public SeekBarThread(Handler handler, MediaPlayer player) {
            this.handler = handler;
            this.player = player;
        }

        public void stopFlag() {
            runFlag = false;
        }

        @Override
        public void run() {
            while (runFlag) {
                //handler.sendEmptyMessage(0);
                // player 를 받아서 값만 넘겨주도록 한다.
                handler.sendEmptyMessage(player.getCurrentPosition());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


