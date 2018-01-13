package com.example.administrator.mymusic.domain.music;

import android.net.Uri;

/**
 * Created by Administrator on 2017-06-27.
 */

// TODO 왜 중복 처리를안 해줬는데도 계속 하나만 나올까
public class Music {

    public String musicId;
    public String musicTitle;
    public String albumTitle;


    public Uri musicUri;
    public String albumArtUri;


    @Override
    public int hashCode() {
        return musicId.hashCode();
    }

    @Override
    public boolean equals(Object music) {

        if(music == null) return false;

        if(!(music instanceof Music)) return false;

        return musicId.hashCode() == music.hashCode();
    }
}
