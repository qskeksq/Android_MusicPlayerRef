package com.example.administrator.musicplayer2.Detail;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.musicplayer2.domain.Item;
import com.example.administrator.musicplayer2.domain.MusicLab;

import java.util.List;


public class DetailFragment extends Fragment {

    PlayerInterface mainActivity;

    int position;
    String albumTitle;

    DetailHolder viewHolder;
    DetailPagerAdapter adapter;

    // 포지션 값 받아옴

//    public static DetailFragment newInstance(int position){
//        DetailFragment fragment = new DetailFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("key", position);
//        fragment.setArguments(bundle);
//        Log.e("디테일에서 포지션 확인", position+"");
//        return fragment;
//    }

    public static DetailFragment newInstance(){ //

        DetailFragment fragment = new DetailFragment();

        return fragment;
    }

    public void setPosition(int position){  //

        this.position = position;
        Log.e("setPosition 확인 final", position+"");
    }

    public void setAlbumTitle(String albumTitle){

        this.albumTitle = albumTitle;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof PlayerInterface){
            mainActivity = (PlayerInterface) context;
        }
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        position = getArguments().getInt("key");
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        createHolder(inflater,container, mainActivity);

        createAdapter();

        return viewHolder.view;
    }

    /**
     * ************************ todo 여기 반드시 정복하고 넘어갈 것 ************************************
     */
    @Override
    public void onStart() {
        super.onStart();
        setAdapter();    // 디테일의 모든 것이 여기서 시작된다.
    }

    // 홀더(뷰) 생성
    public void createHolder(LayoutInflater inflater, ViewGroup container, PlayerInterface playerInterface){

        if(viewHolder == null) // NULL 값 처리 안 해주면 이렇게 메소드로 따로 뺴주는 이유가 전혀 없다.
            viewHolder = new DetailHolder(inflater, container, playerInterface);
    }

    // 어댑터 생성
    public void createAdapter() {

        if(adapter == null)  // NULL 값 처리 안 해주면 이렇게 메소드로 따로 뺴주는 이유가 전혀 없다.
            adapter = new DetailPagerAdapter(getDatas());
    }

    // 데이터 가져오기
    List<Item> datas;
    public List<Item> getDatas(){

        MusicLab music = MusicLab.getInstance(getContext());

        if(albumTitle.equals("")) {
            music.Loader();                                         // 이것을 왜 꼭 해야하는지 파악해야 한다!!!!
            datas = music.getDatas();
        } else {
            datas = music.itemLoader(albumTitle);
        }
        return datas;
    }

    // 리스트에 어댑터 설정
    public void setAdapter(){

        viewHolder.setAdapter(adapter, position);
        Log.e("디테일 뷰로 넘어가는 포지션", position+"");
    }


    public void setDestroy() {

        viewHolder.setDestroy();
    }





    public interface PlayerInterface {

        // 인터페이스
        void initPlayer();

        void playPlayer();

        void pausePlayer();

        void replayPlayer();

        void stopPlayer();

        void changePlayIcon();

        void changePauseIcon();

        void setListImage(String albumUri);

        void setListText(String title);
    }


    //    public static final int RUN_FLAG = 198;
//    Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 199:
//                    holder.setCurSeekBar(msg.arg1);
//                    holder.setCurTxt(milliToString(msg.arg1));
//                    break;
//                case RUN_FLAG:
//                    seekBarThread.setRunFlag(false);
//                    break;
//            }
//        }
//    };
//
//    SeekBarThread seekBarThread;
//
//    class SeekBarThread extends Thread {
//
//        boolean runFlag = true;
//
//        Handler handler;
//
//        public SeekBarThread(Handler handler) {
//            this.handler = handler;
//        }
//
//        public void setRunFlag(boolean flag){
//            runFlag = flag;
//        }
//
//        @Override
//        public void run() {
//            while(runFlag){
////                if(Player.status == Player.STOP){
////                    runFlag = false;
////                }
//                Message msg = new Message();
//                msg.what = 199;
//                msg.arg1 = Player.getCurDuration();
//                handler.sendMessage(msg);
//
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }



//--------------------------------------------------------------------------------------------------

}
