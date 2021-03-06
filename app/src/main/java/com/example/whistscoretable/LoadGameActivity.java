package com.example.whistscoretable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import java.util.List;

public class LoadGameActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private CurrentGame currentGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        currentGame = (CurrentGame) getIntent().getSerializableExtra("currentGame");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        List<CurrentGame> games = db.gameDao().getAllGames();
        adapter = new GameAdapter(games, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Bundle passCurrentGame = new Bundle();
        Intent back=new Intent(this,MenuActivity.class);
        back.putExtras(passCurrentGame);
        startActivity(back);
    }
}

