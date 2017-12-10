package com.guap.guap_tinder;
import android.content.Intent;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
public class SocketInputThread implements Runnable {
    private Socket socket = null;
    private Scanner in = null;
    private String inMessage = null;
    private HashMap<String,Integer> key_map=new HashMap<>();
    public SocketInputThread() { }
    @Override
    public void run() {
        try {
            this.socket=GLOBAL.socket;
            char c;
            in = new Scanner(socket.getInputStream());
            while(true){
                if(in.hasNext()){
                    inMessage = in.nextLine();
                    if(inMessage.charAt(0)=='1') {                         //ЕСЛИ ПРИШЛО СООБЩЕНИЕ СЧИТЫВАЮ СНАЧАЛА ОТПРАВТИЕЛЯ
                        StringBuilder massage= new StringBuilder();       //ЗАТЕМ ОТПРАВИТЕЛЯ В ГЛОБАЛЬНЫЙ КЛАСС
                        StringBuilder sender= new StringBuilder();
                        int i;
                        for (i=2;i<inMessage.length();i++) {
                            c=inMessage.charAt(i);
                            if(c=='/')
                                break;
                            sender.append(c);
                        }
                        GLOBAL.sender=sender.toString();
                        for(int j=i+1;j<inMessage.length();j++){
                            massage.append(inMessage.charAt(j));
                        }
                        GLOBAL.massage=massage.toString();
                    }
                    if(inMessage.charAt(0)=='2') {                          //Если пришли имена и координаты запонлняем наш мап
                        StringBuilder name = new StringBuilder();
                        StringBuilder number = new StringBuilder();
                        double first;
                        double second;
                        for (int i = 1; i < inMessage.length(); i++) {
                            for (; i < inMessage.length(); i++) {
                                c = inMessage.charAt(i);
                                if (c == '/')
                                    break;
                                name.append(c);
                            }
                            for (i = i + 1; i < inMessage.length(); i++) {
                                c = inMessage.charAt(i);
                                if (c == '/')
                                    break;
                                number.append(c);
                            }
                            first = Double.parseDouble(number.toString());
                            number.delete(0, number.length());
                            for (i = i + 1; i < inMessage.length(); i++) {
                                c = inMessage.charAt(i);
                                if (c == '/')
                                    break;
                                number.append(c);
                            }
                            second = Double.parseDouble(number.toString());
                            GLOBAL.mark.put(name.toString(), new LatLng(first, second));
                            name.delete(0, name.length());
                            number.delete(0, number.length());
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}