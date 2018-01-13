package com.example.administrator.musicplayer2.Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import static com.example.administrator.musicplayer2.Detail.DetailHolder.RUN_FLAG;

public class Player {

    public static final int STOP = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static int status = STOP;

    static MediaPlayer player;

    // 설정
    public static void init(Uri uri, Context context, final Handler handler){

        if(player != null){

            player.release();

            status = STOP;
        }

        // 1. 플레이어 가져오기
        player = MediaPlayer.create(context, uri);

        // 2. 플레이어 설정
        player.setLooping(false);

        // 3. 끝났을 경우 리스너 설정
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

//                 음악이 끝나면 일어나는 일을 해주자
                if(handler != null){

                    handler.sendEmptyMessage(RUN_FLAG);
                }

            }
        });
//        Log.e("확인1", status+"");

    }

    // 4. play
    public static void play(){

        if(player != null) {

            player.start();

            status = PLAY;
        }
//        Log.e("확인1", status+"");
    }

    // pause
    public static void pause(){

        if(player != null) {

            player.pause();

            status = PAUSE;
        }
//        Log.e("확인1", status+"");
    }

    // replay
    public static void rePlay(){

        // 따로 play 와 구분하는 이유는 play 의 경우 계속 처음부터 시작하게 된다.
        if(player != null) {

            player.start();

            status = PLAY;
        }
//        Log.e("확인1", status+"");
    }

    // 총 길이 구하기
    public static int getDuration(){

        // 항상 null 값 처리는 신경써줘야 한다
        if(player != null){

            return player.getDuration();
        } else {

            return 0;
        }

    }

    // 현재 위치 구하기
    public static int getCurPosition(){

        // 항상 null 값 처리는 신경써줘야 한다
        if(player != null){
            try {
                // player 가 충돌이 일어나는 경우가 있다. 한 곳에서는 release 하고 한 곳에서는 init 으로 불러오는데 너무 빨라서
                // 이게 서로 엉키는 경우가 생길 수 있다.
                return player.getCurrentPosition();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void setCurTime(int time){

        if(player != null) {

            player.seekTo(time);
        }
    }

    public static boolean isPlaying(){
        return player.isPlaying();
    }


}