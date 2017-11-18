package com.guap.guap_tinder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class Registr extends AppCompatActivity implements View.OnClickListener {
    Button ActTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        ActTwo=(Button)findViewById(R.id.button);
        ActTwo.setOnClickListener( this);
    }
    @Override
    public void onClick(View v){
        switch ((v.getId())){
            case R.id.button:
                Intent intent= new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
