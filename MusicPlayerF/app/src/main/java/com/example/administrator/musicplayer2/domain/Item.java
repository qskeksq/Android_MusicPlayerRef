package com.example.administrator.musicplayer2.domain;


import android.net.Uri;

public class Item {

    public String id;
    public String title;
    public String album;
    public String albumTitle;

    public Uri musicUri;   // 음악 주소
    public String albumUri;   // 앨범 커버 사진 주소

    public boolean isClicked = false;


    @Override
    public int hashCode() {

        return id.hashCode();
    }

    // 만들어 놓으면 알아서 작동한다.

    @Override
    public boolean equals(Object item) {

        if(item == null) return false;
        if(!(item instanceof Item)) return false;

        // false 일 경우에만 추가 된다. true 인 경우는 같다는 말이니까 들어가지 않는다.
        return id.hashCode() == item.hashCode();
    }
}
