package com.example.administrator.musicplayer2.Detail;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.musicplayer2.Util.Player;

import static com.example.administrator.musicplayer2.Detail.DetailHolder.SEEK_CURRENT;

/**
 * Created by Administrator on 2017-06-20.
 */

// post 를 사용하는 것보다 이렇게 분리해서 사용하는 것이 start 를 따로 분리해 낼 수도 있고
public class SeekBarThread extends Thread {

    Handler handler;        // 넘겨받은 자원

    boolean runFlag;


    public SeekBarThread(Handler handler){

        this.handler = handler;
    }

    public void setRunFlag(boolean flag){

        runFlag = flag;
    }

    @Override
    public void run() {

        runFlag = true;

        while(runFlag){

            Message msg = new Message();
            msg.what = SEEK_CURRENT;
            msg.arg1 = Player.getCurPosition();
            handler.sendMessage(msg);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





        // 핸들러로 처리해주면 .start() 와 생성을 분리할 수 있다. 그 말은 initialize 에서 생성하고 play 할 때 start() 해주는 방식으로 하는 것이다
        // 뿐만 아니라 핸들메시지를 통해서 여러 처리를 해 줄 수도 있다.
        // 사실 강사님께서 하신 것도 initialize 와 play 를 분리하셨는데, 이는 처음 들어왔을 때는 초기값 세팅만 해주고, play 버튼을 눌러야
        // 음원 재생, 스레드가 시작되도록 분리하신 것이다. 할 줄 알아야 하는데, 다만 방식을 다르게 한 것 일 뿐이다.


}
