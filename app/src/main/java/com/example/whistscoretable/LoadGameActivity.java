package com.example.whistscoretable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LoadGameActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    //ArrayList<CurrentGame> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        List<CurrentGame> games = db.gameDao().getAllGames();
        /*for(int i=0;i<=15;i++)
        {
            CurrentGame xgame = new CurrentGame();
            xgame.setName("Game " + i);
            games.add(xgame);
        }*/
        adapter = new GameAdapter(games);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Intent back=new Intent(this,MenuActivity.class);
        startActivity(back);
    }
}