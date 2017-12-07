package com.guap.guap_tinder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Massage extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);
    }
    public void show_massage(View view){
        editText= (EditText)findViewById(R.id.send_massage);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("message",editText.getText().toString());
        startActivity(intent);
    }
}
