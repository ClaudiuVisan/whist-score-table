package com.example.whistscoretable;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import java.util.ArrayList;

public class AddPlayersActivity extends AppCompatActivity {

    private int noPlayers;
    private boolean noPlayersModified = false;
    private ArrayList<Player> playersList = new ArrayList<>();
    LinearLayout addPlayersLayout = null;
    boolean nameNotSet=false;
    private CurrentGame currentGame = new CurrentGame();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        Button startGame= findViewById(R.id.startGame);
        startGame.setEnabled(false);
    }

    public void onClickPlayersNo(View view){
        boolean checked=((RadioButton) view).isChecked();
        switch (view.getId()){
            case (R.id.threePlayers):
                if(checked) {
                    noPlayers = 3;
                    break;
                }
            case (R.id.fourPlayers):
                if(checked) {
                    noPlayers = 4;
                    break;
                }
            case (R.id.fivePlayers):
                if(checked) {
                    noPlayers = 5;
                    break;
                }
            case (R.id.sixPlayers):
                if(checked) {
                    noPlayers = 6;
                    break;
                }
        }
        addPlayers();
        Button startGame = findViewById(R.id.startGame);
        startGame.setEnabled(true);
    }

    public void onClickStartGame(View view){
        boolean fromLoad=false;
        Intent startGame = new Intent(AddPlayersActivity.this, ScoreTableActivity.class);
        startGame.putExtras(setGameStatus());
        startGame.putExtra("fromLoad",fromLoad);
        if(nameNotSet)
        {
            Toast.makeText(this, "Please enter players names", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(startGame);
       }
    }

    public void addPlayers(){
        if(noPlayersModified)
        {
            for(int i=1;i<=6;i++)
            {
                EditText removePlayerName = findViewById(R.id.playerName+i);
                addPlayersLayout.removeView(removePlayerName);
            }
        }
        for(int i=1;i<=noPlayers;i++)
        {
            addPlayersLayout = (LinearLayout) findViewById(R.id.myLayout);
            LinearLayout.LayoutParams myParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            EditText playerName = new EditText(AddPlayersActivity.this);
            playerName.setHint("Player "+i);
            playerName.setSingleLine(true);
            playerName.setId(R.id.playerName+i);
            addPlayersLayout.addView(playerName, myParams);
        }
        noPlayersModified = true;
    }

    public void setPlayerNames(){
        currentGame.getPlayersList().clear();
        for(int i=1;i<=noPlayers;i++)
        {
            Player newPlayer = new Player();
            EditText caset = findViewById(R.id.playerName+i);
            newPlayer.setName(caset.getText().toString());
            if(caset.getText().toString().trim().length() == 0)
            {
                nameNotSet=true;
            }
            else{
                nameNotSet=false;
            }
            playersList.add(newPlayer);
            currentGame.setPlayersList(playersList);
        }
    }

    public Bundle setGameStatus(){
        setPlayerNames();
        Bundle passCurrentGame = new Bundle();
        currentGame.setNoRounds(12+3*noPlayers);
        currentGame.setNoPlayers(noPlayers);
        //currentGame.createHands();

        passCurrentGame.putSerializable("currentGame",currentGame);
        return passCurrentGame;
    }

    @Override
    public void onBackPressed(){
        Intent back=new Intent(this,MenuActivity.class);
        startActivity(back);
    }
}
