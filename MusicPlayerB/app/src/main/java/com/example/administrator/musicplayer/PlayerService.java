package com.example.administrator.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.administrator.musicplayer.domain.Song;
import com.example.administrator.musicplayer.domain.SongLab;
import com.example.administrator.musicplayer.lib.Const;

import java.util.List;

public class PlayerService extends Service {

    private Player player;
    private int currentPosition;
    private List<Song> songList;


    public PlayerService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = Player.getInstance();
        songList = SongLab.getInstance().getSongList();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        currentPosition = intent.getIntExtra(Const.SERVICE_PLAY_POSITION_KEY, 0);
        switch (action) {
            case Const.ACTION_SET:
                setPlayer();
                break;
            case Const.ACTION_PLAY:
                startPlayer();
                break;
            case Const.ACTION_PAUSE:
                pausePlayer();
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setPlayer(){
        if (player != null) {
            player.set(this, songList.get(currentPosition).musicUri);
        }
    }

    public void startPlayer(){
        if(player != null){
            player.play();
        }
    }

    public void pausePlayer(){
        if(player != null){
            player.pause();
        }
    }


}
