package com.guap.guap_tinder;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
public class SocketInputThread implements Runnable {
    private Socket s = null;
    private Scanner in = null;
    private String inMessage = null;
    private HashMap<String,Integer> key_map=new HashMap<>();
    public SocketInputThread(Socket s) {
        this.s = s;
    }
    @Override
    public void run() {
        try {
            in = new Scanner(s.getInputStream());
            while(true){
                if(in.hasNext()){
                    inMessage = in.nextLine();
                    if(inMessage.charAt(0)=='1') {
                        int key=inMessage.charAt(1)-48;
                        String prom=new String();
                        for (int i=2;i<inMessage.length();i++) {
                            prom=prom+inMessage.charAt(i);
                        }
                        key_map.put(prom,key);
                    }
                    if(inMessage.charAt(0)=='2') {
                        String prom=new String();
                        String Massage=new String();
                        char c;
                        int i=0;
                        for(i=1;i<inMessage.length();i++){
                            c=inMessage.charAt(i);
                            if(c==' ')
                                break;
                            prom=prom+c;
                        }
                        if(key_map.get(prom)!=null){
                            int key=key_map.get(prom);
                            for(i=i+1;i<inMessage.length();i++){
                                Massage=Massage+(char)(inMessage.charAt(i)-key);
                            }
                            System.out.print(prom+' '+Massage);
                            System.out.print('\n');
                        }
                    }
                    if(inMessage.charAt(0)=='3'){
                        if(inMessage.charAt(1)=='0') {
                            for (int i = 2; i < inMessage.length(); i++) {
                                System.out.print(inMessage.charAt(i));
                            }
                            System.out.print('\n');
                        }
                        if(inMessage.charAt(1)=='1') {
                            String prom=new String();
                            String Massage=new String();
                            char c;
                            int i=0;
                            for(i=2;i<inMessage.length();i++){
                                c=inMessage.charAt(i);
                                if(c==' ')
                                    break;
                                prom=prom+c;
                            }
                            if(key_map.get(prom)!=null){
                                int key=key_map.get(prom);
                                for(i=i+1;i<inMessage.length();i++){
                                    Massage=Massage+(char)(inMessage.charAt(i)-key);
                                }
                                System.out.print(prom+' '+Massage);
                                System.out.print('\n');
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}