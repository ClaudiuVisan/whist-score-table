package com.example.whistscoretable;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickNewGame(View view){
        Intent intent = new Intent(this,add_players.class);
        startActivity(intent);

    }
    public void onClickLoadGame(View view){
        // Implementare activity load game
    }
}