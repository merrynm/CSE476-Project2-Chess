package mardero6.msu.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mardero6.msu.chess.cloud.Cloud;
import mardero6.msu.chess.cloud.Model.Login;

public class GameOverActivity extends AppCompatActivity {
    TextView playerWin;
    TextView playerLose;
    String winner;
    String loser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playerWin = findViewById(R.id.textViewWinner);
        playerLose = findViewById(R.id.textViewLoser);
        winner = getIntent().getExtras().getString("theWinner");
        loser = getIntent().getExtras().getString("theLoser");

        playerWin.setText(winner);
        playerLose.setText(loser);
    }

    public void onClickMainMenu (View view)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                cloud.delete();
            }
        }).start();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
