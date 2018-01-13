package com.example.administrator.mymusic.Player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import static com.example.administrator.mymusic.Util.Const.Player.PAUSE;
import static com.example.administrator.mymusic.Util.Const.Player.PLAY;
import static com.example.administrator.mymusic.Util.Const.Player.STOP;

/**
 * Created by Administrator on 2017-06-29.
 */

public class Player {

    private static MediaPlayer player;

    public static int PLAY_STATUS = STOP;

    public static void init(Context context, Uri musicUri){

        if(player != null){
            player.release();
        }

        player = MediaPlayer.create(context, musicUri);

        player.setLooping(false);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // todo 스레드 멈춰줘야 함
                // todo 다음 곡으로 넘어가거나 반복하기
            }
        });
    }

    public static void play(){

        player.start();

        PLAY_STATUS = PLAY;
    }

    public static void pause(){

        player.pause();

        PLAY_STATUS = PAUSE;

    }

    public static void stop(){

        player.stop();

        PLAY_STATUS = STOP;

    }

    public static int getMusicLength(){

        return player.getDuration();
    }

    public static int getCurPosition(){

        return player.getCurrentPosition();
    }


}
