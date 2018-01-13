package com.example.administrator.musicplayer.player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.administrator.musicplayer.R;
import com.example.administrator.musicplayer.domain.Song;
import com.example.administrator.musicplayer.domain.SongLab;
import com.example.administrator.musicplayer.lib.Const;
import com.example.administrator.musicplayer.player.Controller.Controller;
import com.example.administrator.musicplayer.player.Controller.IController;

import java.io.IOException;
import java.util.List;

/**
 * 음원 관련 스트리밍의 경우 서브스레드에서 할 경우 일정 시간이 지나면
 * 시스템에서 해제를 시켜버린다. 따라서 foreground 서비스에서 따로 관리를 해 준다.
 */
public class PlayerService extends Service implements IController {

    private Player player;
    private List<Song> songList;
    private int itemPosition;
    private String title, artist;
    private Uri albumUri;

    public PlayerService() {

    }

    /**
     * 서비스는 생명주기가 먼저 있고 생성자에 값을 채워넣는 형식이므로 생명주기에서 자원을 초기화 해준다.
     *
     * 앱을 강제 종료할 경우 '서비스만' 다시 호출된다. 즉, 다른 것은 다 죽고, 서비스만 살아 있는 상황에서
     * player, songlist 를 초기화 할 경우 null 값으로 설정이 되어 있고, 다시 앱을 켰을 때는 이미 서비스가
     * 생성이 되어 있기 때문에 따로 onCreate()가 호출되지 않는다. 따라서 NullPointerException 이 생기는 것.
     * 서비스는 강제 종료될 경우에 대비해서 예외처리를 해 줘야 한다.
     *
     * 생성자에서 옵저버 등록록     */
    @Override
    public void onCreate() {
        super.onCreate();
//        player = Player.getInstance(getBaseContext());
//        songList = SongLab.getInstance().getSongList();
        Controller.getInstance().addObserver(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 계속해서 호출하기 때문에 서비스를 호출할 때마다 호출되는 이곳에서 메소드를 관리해준다.
     * @param intent 액션과 데이터 값 넘어옴
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            switch (intent.getAction()) {
                case Const.ACTION_SET:
                    itemPosition = intent.getIntExtra(Const.CURRENT_POS, 0);
                    title = intent.getStringExtra(Const.CURRENT_TITLE);
                    artist = intent.getStringExtra(Const.CURRENT_ARTIST);
                    setPlayer(itemPosition);
                    break;
                case Const.ACTION_START:
                    startPlayer();
                    break;
                case Const.ACTION_PAUSE:
                    pausePlayer();
                    break;
                case Const.ACTION_STOP:

                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setPlayer(int position){
        if(player == null){
            Log.e("호출 확인", "==========player");
            player = Player.getInstance(getBaseContext());
        }
        if(songList == null){
            Log.e("호출 확인", "==========songList");
            songList = SongLab.getInstance().getSongList();

        }
        Uri musicUri = songList.get(position).musicUri;
        albumUri = songList.get(position).albumUri;
        player.setPlayer(musicUri);
        player.setTitle(title);
        player.setArtist(artist);
        player.setCurrentPosition(itemPosition);
    }


    /**
     * 노티피케이션 구현
     * @param action
     */
    public void buildNotification(NotificationCompat.Action action){

        // 1.
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
        notification.setSmallIcon(R.mipmap.ic_launcher_round).setContentTitle(title).setContentText("가수").setPriority(Notification.PRIORITY_MAX);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), albumUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        notification.setLargeIcon(bitmap);

        // 종료 인텐트
        Intent deleteIntent = new Intent(getApplicationContext(), PlayerService.class);
        deleteIntent.setAction(Const.ACTION_STOP);
        PendingIntent deletePending = PendingIntent.getService(getApplicationContext(), 1, deleteIntent, 0);
        notification.setContentIntent(deletePending);

        // prev 버튼 추가
        NotificationCompat.Action prevAction = makeAction(Const.ACTION_PREV, R.drawable.back, "prev");
        notification.addAction(prevAction);

        // 가운데 버튼 -- 가운데 버튼은 상태에 따라 달라지기 때문에 Action 을 인자로 받는다.
        notification.addAction(action);

        // next 버튼 추가
        NotificationCompat.Action nextAction = makeAction(Const.ACTION_NEXT, R.drawable.next, "next");
        notification.addAction(nextAction);

        // 마지막. 등록
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(NOTIFICATION_ID, notification.build());
        startForeground(Const.NOTIFICATION_ID, notification.build());
    }

    public NotificationCompat.Action makeAction(String action, int btnIcon, String btnTitle ){
        Intent intent = new Intent(getApplicationContext(), PlayerService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
        NotificationCompat.Action nextAction = new NotificationCompat.Action.Builder(btnIcon, btnTitle, pendingIntent).build();
        return nextAction;
    }

    private void startPlayer(){
        player.start();
    }

    private void pausePlayer(){
        player.pause();
    }

    private void stopPlayer(){
        player.stop();
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }












    @Override
    public void setProgress() {

    }

    @Override
    public void setTime() {

    }

    @Override
    public void setPlayButton() {
        NotificationCompat.Action pauseAction = makeAction(Const.ACTION_START, R.drawable.play_rounded_button, "pause");
        buildNotification(pauseAction);
    }

    @Override
    public void setPauseButton() {
        NotificationCompat.Action playAction = makeAction(Const.ACTION_PAUSE, R.drawable.round_pause_button, "play");
        buildNotification(playAction);
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void setArtist() {

    }
}
