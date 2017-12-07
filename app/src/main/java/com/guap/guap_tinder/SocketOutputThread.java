package com.guap.guap_tinder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
public class SocketOutputThread implements Runnable {
    private Socket s ;
    private String name1;
    private String name2;
    private int key;
    public SocketOutputThread(Socket s) {
        this.s = s;
        name1= "";
        name2= "";
    }
    @Override
    public void run() {
        try {
            int action;
            String prom;
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(s.getOutputStream());
            Scanner in=new Scanner(System.in);
            System.out.print("1 - all "+'\n'+"2 - one" + '\n'+"3 - get key" + '\n');
            while (true) {
                action = in.nextInt();
                String outMessage = null;
                if(action==1){
                    System.out.print("encrypt-1 or 0"+'\n');
                    int action1=in.nextInt();
                    if(action1==1){
                        outMessage = buffer.readLine();
                        String prom1=new String();
                        int c=0;
                        for(int i = 0; i< outMessage.length(); i++){
                            c= (outMessage.charAt(i) +key);
                            prom1=prom1+(char)c;
                        }
                        outMessage ="1"+"1"+name1+' '+prom1;
                        out.println(outMessage);
                        out.flush();
                    }
                    if(action1==0) {
                        outMessage = buffer.readLine();
                        prom = "1" +"0" + name1 + ' ' + outMessage + ' ';
                        out.println(prom);
                        out.flush();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}