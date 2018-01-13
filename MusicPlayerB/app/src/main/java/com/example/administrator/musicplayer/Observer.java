package com.example.administrator.musicplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-10-18.
 */

public class Observer {

    private static Observer sObserver;

    private Observer() {

    }

    public static Observer getInstance(){
        if(sObserver == null){
            sObserver = new Observer();
        }
        return sObserver;
    }

    List<IObserver> clients = new ArrayList<>();

    public void addObserver(IObserver observer){
        clients.add(observer);
    }

    public List<IObserver> getClients(){
        return clients;
    }


}
