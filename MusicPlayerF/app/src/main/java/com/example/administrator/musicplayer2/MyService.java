package com.example.administrator.musicplayer2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.musicplayer2.Util.Controller;
import com.example.administrator.musicplayer2.Util.Player;
import com.example.administrator.musicplayer2.domain.MusicLab;

import static com.example.administrator.musicplayer2.Const.NOTIFICATION_ID;
import static com.example.administrator.musicplayer2.Const.NOTIFICATION_POSITION;

public class MyService extends Service implements IController {

    Controller controller;
    String title;
    Bitmap bitmap;

    public MyService() {

//        Controller.getInstance().addObserver(this);
//        controller = Controller.getInstance();
//        controller.addObserver(this);
        Log.e("MyService", "확인");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//
//        controller = Controller.getInstance();
//        controller.addObserver(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getExtras() != null){
            int position = intent.getExtras().getInt(NOTIFICATION_POSITION);
            title = MusicLab.getInstance(getApplicationContext()).getDatas().get(position).title;
            String album = MusicLab.getInstance(getApplicationContext()).getDatas().get(position).albumUri;
            bitmap = BitmapFactory.decodeFile(album);
//            try {
//                bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(album));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        String action = intent.getAction();

        switch (action){
            case Const.Action.PLAY:
                startPlayer();
                break;
            case Const.Action.PAUSE:
                pausePlayer();
                break;
            case Const.Action.REPLAY:
                Player.rePlay();
                break;
            case Const.Action.STOP:
//                stopService();
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void buildNotification(NotificationCompat.Action action){

        // 1.
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
        notification.setSmallIcon(R.mipmap.android4).setContentTitle(title).setContentText("가수");
        notification.setLargeIcon(bitmap);

        // 종료 인텐트
        Intent deleteIntent = new Intent(getApplicationContext(), MyService.class);
        deleteIntent.setAction(Const.Action.STOP);
        PendingIntent deletePending = PendingIntent.getService(getApplicationContext(), 1, deleteIntent, 0);
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
        startForeground(NOTIFICATION_ID, notification.build());
    }


    public NotificationCompat.Action makeAction(String action, int btnIcon, String btnTitle ){
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
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
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(NOTIFICATION_ID);

        // 서비스 종료
        Intent service = new Intent(getApplicationContext(), MyService.class);
        stopService(service);

    }



    public String musicTitle(){
        return title;
    }

    public Bitmap musicBitmap(){
        return bitmap;
    }




}
