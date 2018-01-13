package com.example.administrator.musicplayer;

import android.media.MediaPlayer;
import android.net.Uri;

import com.example.administrator.musicplayer.domain.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-10-13.
 */
public class Player {

    // 움원 재생 가능
    MediaPlayer mp;
    // 음원데이터
    List<Song> data;
    // 현재 플레이 되고 있는 음원 정보
    int currentPosition = -1;

    // 리스너 저장소
    List<Listener> listeners = new ArrayList<>();

    // 리스너를 등록하는 명령어
    public void addListener(Listener listener){
        listeners.add(listener);
    }

    // 리스너 인터페이스
    public interface Listener{
        void setProgrss();
    }

    // 구조체 또는 인터페이스
    public interface Music {
        Uri getMusicUri();
        int getDuration();
    }

}
