package com.example.administrator.mymusic.PlayObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-06-29.
 */

public class Controller {

    List<IController> list;

    private static Controller sController;

    public Controller() {

        list = new ArrayList<>();

    }

    public static Controller getInstance(){
        if(sController == null){
            sController = new Controller();
        }
        return sController;
    }

    public void addObserver(IController observer){
        list.add(observer);

    }

    public void initialize(String albumUri, String musicTitle){
        for(IController obs : list){
            obs.albumArt(albumUri);
            obs.musicTitle(musicTitle);
        }
    }

    public void playIcon(){
        for(IController obs : list){
            obs.playIcon();
        }
    }

    public void pauseIcon(){
        for(IController obs : list){
            obs.pauseIcon();
        }
    }

}
