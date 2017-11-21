package com.guap.guap_tinder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class Registr extends AppCompatActivity implements View.OnClickListener {
    Button ActTwo;
    EditText editText;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr);
        ActTwo=(Button)findViewById(R.id.button);
        ActTwo.setOnClickListener( this);
    }
    @Override
    public void onClick(View v){
            switch ((v.getId())) {
                case R.id.button:
                    editText= (EditText)findViewById(R.id.editText);
                    editText2= (EditText)findViewById(R.id.editText2);
                    Intent intent = new Intent(this, MapsActivity.class);
                    intent.putExtra("message",editText2.getText().toString()+"/"+editText.getText().toString());
                    startActivity(intent);
                    break;
                default:
                    break;
            }
    }
}
