package com.guap.guap_tinder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.Socket;

public class Registr extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        Button actTwo = (Button) findViewById(R.id.button);
        actTwo.setOnClickListener( this);
    }
    @Override
    public void onClick(View v){
        try {
            GLOBAL.socket = new Socket("5.19.136.111", 4897);
            Thread threadIn = new Thread(new SocketInputThread());// создание отдельного потока на считывание даных от сервера
            threadIn.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditText username = (EditText) findViewById(R.id.username);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("registration", username.getText().toString());
        startActivity(intent);
    }
}
