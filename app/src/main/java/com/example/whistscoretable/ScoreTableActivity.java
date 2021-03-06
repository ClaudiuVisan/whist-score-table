package com.example.whistscoretable;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ScoreTableActivity extends AppCompatActivity {
    private CurrentGame currentGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        currentGame = (CurrentGame) getIntent().getSerializableExtra("currentGame");
        currentGame.createHands();
        setScoreTable();
        Button placeBets = findViewById(R.id.placeBets);
        placeBets.setOnClickListener(v -> {
            if (currentGame.getRound() > 0 && currentGame.isNeedRotate()) {
                currentGame.rotatePlayers();
            }
            checkActivity();
        });
        ImageButton imageButton = findViewById(R.id.saveButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveGame = new Intent(ScoreTableActivity.this, SaveGameActivity.class);
                Bundle passCurrentGame = new Bundle();
                passCurrentGame.putSerializable("currentGame", currentGame);
                saveGame.putExtras(passCurrentGame);
                startActivity(saveGame);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent back;
        boolean fromLoad=getIntent().getExtras().getBoolean("fromLoad");
        if (currentGame.isBackPressed()) {
            revertPlayersScore();
            back = new Intent(this, InputResultsActivity.class);
            Bundle passCurrentGame = new Bundle();
            passCurrentGame.putSerializable("currentGame", currentGame);
            back.putExtras(passCurrentGame);
        } else if(fromLoad==true){
            boolean onSave=false;
            back = new Intent(this, LoadGameActivity.class);
            back.putExtra("fromSave",onSave);
        }else{
            back= new Intent(this, AddPlayersActivity.class);
        }
        startActivity(back);
    }

    public void setScoreTable() {
        TableLayout scoreTable = findViewById(R.id.tabel);
        scoreTable.setVerticalGravity(Gravity.CENTER_VERTICAL);
        scoreTable.setVerticalGravity(Gravity.START);
        for (int i = 1; i <= currentGame.getNoPlayers(); i++) {
            TableRow rand = new TableRow(this);
            TableRow.LayoutParams myParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
            rand.setLayoutParams(myParams);
            TextView casetNume = new TextView(this);
            TextView casetScor = new TextView(this);
            casetNume.setText(currentGame.getPlayersList().get(i - 1).getName());
            casetScor.setText(String.valueOf(currentGame.getPlayersList().get(i - 1).getScore()));
            casetNume.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            casetNume.setWidth(TableLayout.LayoutParams.WRAP_CONTENT);
            casetScor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            casetScor.setGravity(Gravity.END);
            rand.addView(casetNume, myParams);
            rand.addView(casetScor, myParams);
            scoreTable.addView(rand, myParams);
        }
    }

    public void checkActivity() {
        Intent bets = new Intent(ScoreTableActivity.this, BetsHandsActivity.class);
        Bundle passCurrentGame = new Bundle();
        passCurrentGame.putSerializable("currentGame", currentGame);
        bets.putExtras(passCurrentGame);
        startActivity(bets);
    }

    public void revertPlayersScore() {
        for (int i = 0; i < currentGame.getNoPlayers(); i++) {
            {
                if (currentGame.getPlayersList().get(i).getBet() == currentGame.getPlayersList().get(i).getResult()) {
                    currentGame.getPlayersList().get(i).setScore(currentGame.getPlayersList().get(i).getScore() - 5 - currentGame.getPlayersList().get(i).getBet());
                } else {
                    currentGame.getPlayersList().get(i).setScore(currentGame.getPlayersList().get(i).getScore() + Math.abs(currentGame.getPlayersList().get(i).getBet() - currentGame.getPlayersList().get(i).getResult()));
                }
            }
        }
    }
}

