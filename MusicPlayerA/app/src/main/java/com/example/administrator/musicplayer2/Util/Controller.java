package com.example.administrator.musicplayer2.Util;

import com.example.administrator.musicplayer2.IController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-06-23.
 */

public class Controller {


    List<IController> list;

    private static Controller sController;

    private Controller(){

        list = new ArrayList<>();
    }

    public static Controller getInstance(){

        if(sController == null){
            sController = new Controller();
        }

        return sController;
    }

    public void addObserver(IController controller){

        list.add(controller);
    }

    public void play(){

        for(IController controller : list){
            controller.startPlayer();
        }
    }

    public void pause(){

        for(IController controller : list){
            controller.pausePlayer();
        }
    }


}
