package com.example.administrator.mymusic.Player;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.example.administrator.mymusic.PlayObserver.Controller;

import static com.example.administrator.mymusic.Util.Const.Action.INIT;
import static com.example.administrator.mymusic.Util.Const.Action.NEXT;
import static com.example.administrator.mymusic.Util.Const.Action.PAUSE;
import static com.example.administrator.mymusic.Util.Const.Action.PLAY;
import static com.example.administrator.mymusic.Util.Const.Action.PREV;
import static com.example.administrator.mymusic.Util.Const.Action.STOP;
import static com.example.administrator.mymusic.Util.Const.Key.ALBUM_URI;
import static com.example.administrator.mymusic.Util.Const.Key.MUSIC_TITLE;
import static com.example.administrator.mymusic.Util.Const.Key.MUSIC_URI;

/**
 * Created by Administrator on 2017-06-29.
 */

public class PlayerService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 여기서 모든 음악에 대한 준비를 할 수 있도록 준비해야 한다.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();
        Uri musicUri = intent.getParcelableExtra(MUSIC_URI);
        String albumUri = intent.getStringExtra(ALBUM_URI);
        String musicTitle = intent.getStringExtra(MUSIC_TITLE);

        switch (action) {
            case INIT:
                init(musicUri, albumUri, musicTitle);
                break;
            case PREV:

                break;
            case STOP:

                break;
            case PLAY:
                play();
                break;
            case PAUSE:
                pause();
                break;
            case NEXT:

                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }


    public void init(Uri musicUri, String albumUri, String musicTitle){
        Player.init(getApplicationContext(), musicUri);
        Controller.getInstance().initialize(albumUri, musicTitle);

    }

    public void prev(){

    }

    public void stop(){

    }

    public void play(){
        Player.play();
        Controller.getInstance().pauseIcon();

    }

    public void pause(){
        Player.pause();
        Controller.getInstance().playIcon();

    }

    public void next(){

    }

}
