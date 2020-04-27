package mardero6.msu.chess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.simpleframework.xml.Version;

import java.util.Timer;
import java.util.TimerTask;

import mardero6.msu.chess.cloud.Cloud;
import mardero6.msu.chess.cloud.Model.GameData;
import mardero6.msu.chess.cloud.Model.GetPlayersResult;

public class DummyActivity extends AppCompatActivity {

    boolean joinState = false;
    String username;
    String password;
    final Timer timer = new Timer();
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        joinState = getIntent().getExtras().getBoolean(LoginActivity.IS_NEW_GAME_MESSAGE);
        username = getIntent().getExtras().getString(LoginActivity.USERNAME);
        password = getIntent().getExtras().getString(LoginActivity.PASSWORD);
        final DummyActivity activity = this;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final GameData data = cloud.pullGame(username, password);
                if (data != null && data.getStatus().equals("yes") && (joinState && data.getTurn().equals(username)) || !joinState) {
                    final GetPlayersResult res = cloud.getPlayers(username, password);
                    if(res != null && res.getStatus().equals("yes")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(activity, ChessActivity.class);
                                intent.putExtra(LoginActivity.USERNAME, username);
                                intent.putExtra(LoginActivity.PASSWORD, password);
                                intent.putExtra("Player1", res.getPlayer1());
                                intent.putExtra("Player2", res.getPlayer2());
                                intent.putExtra("who", data.getTurn());

                                startActivity(intent);
                                finish();
                            }
                        });
                        timer.cancel();
                        timer.purge();
                    }
                }
            }
        };
        triggerTimer();
    }

    @Override
    protected void onStop() {
        timer.cancel();
        timer.purge();
        super.onStop();
    }


    private void triggerTimer() {
        timer.schedule(timerTask, 3000, 3000);
    }

    private void startGame() {
        Intent intent = new Intent(this, ChessActivity.class);
       // startActivity(intent);
    }
}
