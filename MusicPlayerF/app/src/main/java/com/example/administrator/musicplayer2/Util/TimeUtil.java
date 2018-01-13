package com.example.administrator.musicplayer2.Util;

/**
 * Created by Administrator on 2017-06-20.
 */

public class TimeUtil {

    // 시간으로 바꿔주기
    public static String milliToString(int mSecond){

        long min = mSecond / 1000 / 60;
        long sec = mSecond / 1000 % 60;

        return String.format("%2d", min) + ":" + String.format("%02d", sec);
    }

}
