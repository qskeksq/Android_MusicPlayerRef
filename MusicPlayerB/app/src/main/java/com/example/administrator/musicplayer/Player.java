package com.example.administrator.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.List;

/**
 * Created by Administrator on 2017-10-18.
 */

public class Player {

    MediaPlayer mediaPlayer;

    private static Player sPlayer;
    private Observer observer;
    private List<IObserver> clients;

    private Player() {
        observer = Observer.getInstance();
        clients = observer.getClients();

    }

    public static Player getInstance(){
        if(sPlayer == null){
            sPlayer = new Player();
        }
        return sPlayer;
    }

    public void set(Context context, Uri musicUri){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, musicUri);
        mediaPlayer.setLooping(false);
    }

    public void play(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).setPauseButton();
            }
        }
    }

    public boolean isPlaying(){
        if(mediaPlayer != null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public void pause(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).setPlayButton();
            }
        }
    }
}
