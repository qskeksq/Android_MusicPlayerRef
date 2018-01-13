package com.example.administrator.musicplayer2.Detail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.musicplayer2.Const;
import com.example.administrator.musicplayer2.IController;
import com.example.administrator.musicplayer2.MyService;
import com.example.administrator.musicplayer2.R;
import com.example.administrator.musicplayer2.Util.Controller;
import com.example.administrator.musicplayer2.Util.Player;
import com.example.administrator.musicplayer2.domain.Item;
import com.example.administrator.musicplayer2.domain.MusicLab;

import java.util.List;

import static com.example.administrator.musicplayer2.Const.NOTIFICATION_POSITION;
import static com.example.administrator.musicplayer2.Detail.DetailFragment.PlayerInterface;
import static com.example.administrator.musicplayer2.Util.TimeUtil.milliToString;


public class DetailHolder implements View.OnClickListener, IController{

    public static final int SEEK_CURRENT = 999;
    public static final int RUN_FLAG = 998;


    View view;                                      // 받아온 자원
    Context context;
    PlayerInterface playerInterface;

    SeekBarThread seekBarThread;

    SeekBar seekBar;                                // 사용할 위젯
    ViewPager viewPager;
    TextView current, duration;
    ImageButton btnPlay, btnNext, btnPrev, dialog;


    public DetailHolder(LayoutInflater inflater, ViewGroup container, PlayerInterface playerInterface){
        this.context = container.getContext();
        this.playerInterface = playerInterface;

        view = inflater.inflate(R.layout.fragment_detail, container, false);
        init();

        Controller.getInstance().addObserver(this);
    }

    public void init(){
        Log.d("DetailHolder","바보바보바보~~~~~나단");
        setViews();
        setListener();
    }


    // findViewById
    public void setViews(){

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        btnPlay = (ImageButton) view.findViewById(R.id.btnPlay);
        btnNext = (ImageButton) view.findViewById(R.id.btnNext);
        btnPrev = (ImageButton) view.findViewById(R.id.btnPrevious);
        dialog = (ImageButton) view.findViewById(R.id.dialogButton);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        current = (TextView) view.findViewById(R.id.txtElapsed);
        duration = (TextView) view.findViewById(R.id.txtTime);
    }

    // 리스너 set
    public void setListener(){

        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        dialog.setOnClickListener(this);
        viewPager.addOnPageChangeListener(pageChangeListener);
        seekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    // 리사이클러뷰에 어댑터 set
    public void setAdapter(DetailPagerAdapter adapter/*,List<Item> datas*/ , int position){

//        DetailPagerAdapter adapter = new DetailPagerAdapter(datas);
        Log.e("디테일 뷰 setAdapter", position+"");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        if(position == 0){
            initializePagerPage(position);
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnPlay:
                play();
                break;
            case R.id.btnNext:
                next();
                break;
            case R.id.btnPrevious:
                prev();
                break;
            case R.id.dialogButton:
                View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
                new AlertDialog.Builder(context).setView(dialogView).show();
                break;
        }

    }
    ViewPager.OnPageChangeListener pageChangeListener =  new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            Log.e("pageChangeListener", position+"");
            initializePagerPage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            if(fromUser){

                Player.setCurTime(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

//    // 페이저 초기값 함수 -- 페이저가 넘어가거나 처음 들어갈 때 실행되어야 하는 것들
//    public void initializePage(int position){
//        //프레젠터에 있는 이유는 일련의 함수의 모음이기 때문 뿐만 아니라 추가적인 함수 처리가 필요할 수 있기 때문이다.
//
////        if(Player.status == Player.PLAY)
//
////        Player.play(context, getDatas().get(position).musicUri, mHandler);
//        setMax(Player.getDuration());
////        setPlayPause();
//        setLength(getDuration());
////        setElapsed();
//        seekBarThread = new SeekBarThread(mHandler);
//        seekBarThread.start();
////        Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
//    }

    public void initializePagerPage(int position){


        Log.e("initializePagerPage", position + "");
//        playerInterface.onItemClicked(position);

        Player.init(getDatas().get(position).musicUri, context, mHandler);  // 초기화

        setMax(Player.getDuration());                                       // 음원 길이 시크바 설정

        setLengthTxt(getDuration());                                        // 음원 길이 텍스트 설정

        seekBarThread = new SeekBarThread(mHandler);                        // 재생 전까지 0으로 계속 조사

        seekBarThread.start();

//        playerInterface.playPlayer();
        setIntent(position);

        setPlayBtnPause();

//        playerInterface.changePauseIcon();
        Controller.getInstance().pause();


//        playerInterface.setListImage(getDatas().get(position).albumUri);

//        playerInterface.setListText(getDatas().get(position).title);
    }

    Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SEEK_CURRENT:
                    setCurSeekBar(msg.arg1);
                    setCurTxt(milliToString(msg.arg1));
                    break;
                case RUN_FLAG:
                    seekBarThread.setRunFlag(false);
                    break;
            }
        }
    };


    // 재생 버튼 누를 때 함수
    public void play(){

        switch (Player.status){

            case Player.PLAY:
                playerInterface.pausePlayer();
                setPlayBtnPlay();
//                playerInterface.changePlayIcon();
                Controller.getInstance().play();
                break;
            case Player.PAUSE:
                playerInterface.replayPlayer();
                setPlayBtnPause();
                Controller.getInstance().pause();
//                playerInterface.changePauseIcon();
                break;
        }
    }

    public void next(){

        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    public void prev(){

        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }


    public void setIntent(int position){
        Intent service = new Intent(context, MyService.class);
        service.setAction(Const.Action.PLAY);
        service.putExtra(NOTIFICATION_POSITION ,position);
        context.startService(service);
    }

    // 데이터 가져오기
    public List<Item> getDatas(){

        MusicLab music = MusicLab.getInstance(context);
        music.Loader();                                         // 이것을 왜 꼭 해야하는지 파악해야 한다!!!!
        return music.getDatas();
    }

    // 전체 길이 가져오기
    public String getDuration(){

        String duration = milliToString(Player.getDuration());

        return duration;
    }


    // 음악 길이 set
    public void setLengthTxt(String length){

        duration.setText(length+"");
    }

    // 시크바 set -- 최대값
    public void setMax(int duration){

        seekBar.setMax(duration);
    }

    // 시크바 set -- 현재값
    public void setCurSeekBar(int curPosition){

        seekBar.setProgress(curPosition);
    }

    // 음악 현재 시간 set
    public void setCurTxt(String curTime){

        current.setText(curTime);
    }

    // 플레이 버튼 set -- 플레이
    public void setPlayBtnPlay(){

        btnPlay.setImageResource(R.drawable.ic_action_play);
    }

    // 플레이 버튼 set -- 일시정지
    public void setPlayBtnPause(){

        btnPlay.setImageResource(R.drawable.ic_action_pause);
    }

    //    // 재생시 시크바 움직이기
//    boolean runFlag;
//
//    Handler handler = new Handler();
//
//    public void setElapsed(){
//        // 핸들러로 처리해주면 .start() 와 생성을 분리할 수 있다. 그 말은 initialize 에서 생성하고 play 할 때 start() 해주는 방식으로 하는 것이다
//        // 뿐만 아니라 핸들메시지를 통해서 여러 처리를 해 줄 수도 있다.
//        // 사실 강사님께서 하신 것도 initialize 와 play 를 분리하셨는데, 이는 처음 들어왔을 때는 초기값 세팅만 해주고, play 버튼을 눌러야
//        // 음원 재생, 스레드가 시작되도록 분리하신 것이다. 할 줄 알아야 하는데, 다만 방식을 다르게 한 것 일 뿐이다.
//        runFlag = true;
//        new Thread(){
//            @Override
//            public void run() {
//                while(runFlag){
//                    if(Player.status == Player.STOP){
//                        runFlag = false;
//                    }
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            // 여기에서는 계속해서 변하는 값만 있어야 한다.
////                            holder.setMax(Player.getDuration());
//                            setCurSeekBar(Player.getCurDuration());
//                            setCurTxt(milliToString(Player.getCurDuration()));
////                            Player.complete();      // 끝나면 status 를 STOP 으로 바꿔준다
//                        }
//                    });
//
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        }.start();
//    }

    public void setDestroy(){

        seekBarThread.setRunFlag(false);
        seekBarThread.interrupt();
    }


    @Override
    public void startPlayer() {

        setPlayBtnPlay();
    }

    @Override
    public void pausePlayer() {

        setPlayBtnPause();
    }
}
