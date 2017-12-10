package com.guap.guap_tinder;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class GLOBAL implements Runnable{
    public static Socket socket;
    public static String massage;
    public static String sender;
    public static Map<String,LatLng> mark;//ОШИБКА
    public GLOBAL(){
        socket=null;
        massage=null;
        sender=null;
    }
    @Override
    public void run() {
        try {
            socket = new Socket("5.19.136.111", 4897);
            Thread threadIn = new Thread(new SocketInputThread());// создание отдельного потока на считывание даных от сервера
            threadIn.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
