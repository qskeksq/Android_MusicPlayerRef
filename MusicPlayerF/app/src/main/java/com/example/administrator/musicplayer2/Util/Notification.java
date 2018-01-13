package com.example.administrator.musicplayer2.Util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.musicplayer2.Const;
import com.example.administrator.musicplayer2.IController;
import com.example.administrator.musicplayer2.MyService;
import com.example.administrator.musicplayer2.R;

import static com.example.administrator.musicplayer2.Const.NOTIFICATION_ID;

/**
 * Created by Administrator on 2017-06-23.
 */


// 얘를 밖으로 안 것은 Service 를 addObserver 한 것이 문제가 아니라

public class Notification implements IController{

    Context context;
    MyService service;
    NotificationCompat.Builder notification;

    private static Notification sNoti;

    private Notification(Context context, MyService service) {
        this.context = context;
        this.service = service;
        Controller.getInstance().addObserver(this);
    }

    public static Notification getInstance(Context context, MyService service){
        if(sNoti == null){
            sNoti = new Notification(context, service);
        }
        return sNoti;
    }

//    public void NotificationBase(String title, Bitmap bitmap){
//
//        // 노티바의 기본 설정을 해준다.
//        notification = new NotificationCompat.Builder(context);
//        notification.setSmallIcon(R.mipmap.android4).setContentTitle(title).setContentText("가수").setLargeIcon(bitmap);
//
//        // 노티바 제거 설정
//        Intent deleteIntent = new Intent(context, MyService.class);
//        deleteIntent.setAction(Const.Action.STOP);
//        PendingIntent deletePending = PendingIntent.getService(context, 1, deleteIntent, 0);
//        notification.setContentIntent(deletePending);
//    }
//
//    public void setNotiAction(NotificationCompat.Action action){
//
//        // prev 버튼
//        notification.addAction(makeAction(Const.Action.PREV, R.drawable.ic_action_prev, "prev"));
//
//        // next 버튼
//        notification.addAction(makeAction(Const.Action.NEXT, R.drawable.ic_action_next, "next"));
//
//        // middle 버튼
//        notification.addAction(action);
//    }
//
//    public void startNotification(Service service){
//
//        service.startForeground(NOTIFICATION_ID, notification.build());
//    }

        public void buildNotification(NotificationCompat.Action action){

        // 1.
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        notification.setSmallIcon(R.mipmap.android4).setContentTitle(service.musicTitle()).setContentText("가수");
        notification.setLargeIcon(service.musicBitmap());

        // 종료 인텐트
        Intent deleteIntent = new Intent(context, MyService.class);
        deleteIntent.setAction(Const.Action.STOP);
        PendingIntent deletePending = PendingIntent.getService(context, 1, deleteIntent, 0);
        notification.setContentIntent(deletePending);

        // prev 버튼 추가
        NotificationCompat.Action prevAction = makeAction(Const.Action.PREV, R.drawable.ic_action_prev, "prev");
        notification.addAction(prevAction);

        // 가운데 버튼 -- 가운데 버튼은 상태에 따라 달라지기 때문에 Action 을 인자로 받는다.
//        NotificationCompat.Action pauseAction = makeAction(Const.Action.PAUSE, R.drawable.ic_action_pause, "pause");
        notification.addAction(action);

        // next 버튼 추가
        NotificationCompat.Action nextAction = makeAction(Const.Action.NEXT, R.drawable.ic_action_next, "next");
        notification.addAction(nextAction);

        // 마지막. 등록
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(NOTIFICATION_ID, notification.build());
        service.startForeground(NOTIFICATION_ID, notification.build());
    }


    public NotificationCompat.Action makeAction(String action, int btnIcon, String btnTitle ){
        Intent intent = new Intent(context, MyService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, 0);
        NotificationCompat.Action nextAction = new NotificationCompat.Action.Builder(btnIcon, btnTitle, pendingIntent).build();
        return nextAction;
    }


    @Override
    public void startPlayer(){
        Player.play();
        Controller.getInstance().pause();
        NotificationCompat.Action pauseAction = makeAction(Const.Action.PAUSE, R.drawable.ic_action_pause, "pause");
        buildNotification(pauseAction);
    }


    @Override
    public void pausePlayer(){
        Player.pause();
        Controller.getInstance().play();
        NotificationCompat.Action playAction = makeAction(Const.Action.PLAY, R.drawable.ic_action_play, "play");
        buildNotification(playAction);
    }


    public void stopService(){

        // 노티바 제거
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(NOTIFICATION_ID);

        // 서비스 종료
        Intent service = new Intent(context, MyService.class);
        context.stopService(service);
    }


}
