package com.example.mario_flores_jr.test_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Button button1,button2,button3;
    public Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 =(Button)findViewById(R.id.Tomar_Foto);
        button2 = (Button)findViewById(R.id.Tomar_Audio);
        button3 = (Button)findViewById(R.id.Mostrar_Mapa);

        t = Toast.makeText(this,"HOLA",Toast.LENGTH_SHORT);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Camera.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Audio.class);
                startActivity(intent);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(in);
            }
        });


    };
}