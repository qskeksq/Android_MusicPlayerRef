package com.example.administrator.mymusic.Util;

/**
 * Created by Administrator on 2017-06-29.
 */

public class Const {

    public class Action {

        public static final String PLAY = "com.example.administrator.musicplayer2.action.play";
        public static final String PAUSE = "com.example.administrator.musicplayer2.action.pause";
        public static final String STOP = "com.example.administrator.musicplayer2.action.stop";
        public static final String REPLAY = "com.example.administrator.musicplayer2.action.replay";
        public static final String INIT = "com.example.administrator.musicplayer2.action.init";

        public static final String PREV = "com.example.administrator.musicplayer2.action.prev";
        public static final String NEXT = "com.example.administrator.musicplayer2.action.next";
    }

    public class Player {

        // TODO
        public static final int STOP = 0;
        public static final int PLAY = 1;
        public static final int PAUSE = 2;

    }

    public class Key {

        public static final String MUSIC_URI = "musicuri";
        public static final String ALBUM_URI = "albumuri";
        public static final String MUSIC_TITLE = "musictitle";
        public static final String DETAIL_POSITION = "detailposition";
    }

    public class Category{

        public static final int ALBUM = 999;
        public static final int ARTIST = 998;
        public static final int FOLDER = 997;
    }



}
