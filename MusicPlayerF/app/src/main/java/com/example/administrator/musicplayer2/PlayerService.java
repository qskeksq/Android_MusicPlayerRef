package com.example.administrator.musicplayer2;


import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.administrator.musicplayer2.Util.Player;

import static com.example.administrator.musicplayer2.Const.Action.NEXT;
import static com.example.administrator.musicplayer2.Const.Action.PAUSE;
import static com.example.administrator.musicplayer2.Const.Action.PLAY;
import static com.example.administrator.musicplayer2.Const.Action.PREV;
import static com.example.administrator.musicplayer2.Const.Action.STOP;

public class PlayerService extends Service {
    public PlayerService() {

    }

//     보통은 한번에 하나의 서비스만 사용하기 때문에 잘 사용하지는 않는다.
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null){
            String action = intent.getAction();
            switch (action){
                case PLAY:
                    playerStart();
                    break;
                case PAUSE:

                    break;
                case STOP:
                    // 서비스를 종료한다.
                    stopService();
                    break;
                case Const.Action.INIT:

                    break;
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }

    // 포어그라운드 서비스를 해 주지 않으면 2시간 지나면 서비스가 종료된다. 노티바랑 좀 구분할 필요가 있는데
    // 노티바는 단지 알림 기능만 하는 것이고, 포어그라운드는 서비스를 계속 유지시켜 준다.

    private void playerStart(){
        NotificationCompat.Action pauseAction = makeAction(android.R.drawable.ic_media_pause, "pause", PAUSE);
        buildNotification(pauseAction, PLAY);
        Player.play();
    }


    // 노티피케이션의 고유 ID
    public static final int NOTIFICATION_ID = 100;

    // 서비스를 종료하는 함수
    private void stopService(){

//        // 1. 노티바 제거
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.cancel(NOTIFICATION_ID);
//
//        // 2. 서비스 종료
//        Intent intent = new Intent(getApplicationContext(), PlayerService.class);
//        stopService(intent);
        stopForeground(true);
        stopSelf();
    }

    // 1. 포어그라운드 서비스를 생성하기 위해서 노티바를 생성하는 함수
    private void buildNotification(NotificationCompat.Action action, String flag){


        // 가. 노티바 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.android4).setContentTitle("음악 제목").setContentText("가수 이름");

        // 가.1 노티바 종료하는 intent 생성
        Intent deleteIntent = new Intent(getApplicationContext(), PlayerService.class);
        deleteIntent.setAction(STOP);
        PendingIntent pendingDeleteIntent = PendingIntent.getService(getApplicationContext(), 1, deleteIntent, 0);

        // 가.2 서비스 종료 intent 등록
        builder.setContentIntent(pendingDeleteIntent);

        // 가.3 노티바에서 사용할 액션 등록
        NotificationCompat.Action prevAction = makeAction(android.R.drawable.ic_media_previous, "prev", PREV);
        builder.addAction(prevAction);
//        NotificationCompat.Action playAction = makeAction(android.R.drawable.ic_media_play, "play", PLAY);
        builder.addAction(action);
        NotificationCompat.Action nextAction = makeAction(android.R.drawable.ic_media_next, "next", NEXT);
        builder.addAction(nextAction);

        // 나. 노티바 등록 -- 이것을 안 해주면 밀어서 삭제는 안 되는군. 다만 삭제 인텐트를 달아주었으므로 그렇게 삭제는 됨.
        startForeground(NOTIFICATION_ID, builder.build());
    }


    // 노티바에서 사용하는 버튼을 생성하는 함수
    private NotificationCompat.Action makeAction(int btnIcon, String btnTitle, String action){

        Intent intent = new Intent(getApplicationContext(), PlayerService.class);

        intent.setAction(action);

        // 인텐트를 서비스 밖 혹은 혹은 액티비티 밖에서 실행할 수 있도록 담아두는 주머니. 이미 시스템 자원을 사용해서 만들어진 상태로
        // 저장해 둠. 지금 당장 실행되지 않고 나중에 실행되는 것이고, 이미 자원을 갖고 만들어 졌기 때문에 따로 자원을 넣어주지 않아도 된다.
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);

        return new NotificationCompat.Action.Builder(btnIcon, btnTitle, pendingIntent).build();
    }

}
