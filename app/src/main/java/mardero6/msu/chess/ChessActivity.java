package mardero6.msu.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import mardero6.msu.chess.cloud.Cloud;
import mardero6.msu.chess.cloud.Model.GameData;
import mardero6.msu.chess.cloud.Model.GetPlayersResult;

public class ChessActivity extends AppCompatActivity {
    TextView playerTurn;
    Button doneButton;
    Button resignButton;
    String orig1;
    String orig2;
    String welcome1;
    String welcome2;
    String curr;
    String winner = "";
    String loser = "";
    String username;
    String password;
    String who;
    Integer turn;
    boolean color; //black is false
    public boolean endGame = false;
    Timer timer = new Timer();
    TimerTask timerTask;
    boolean first_move = true; //
    boolean networkThreadRunning = false;
    Thread networkThread;

    // this key is to store current player's turn
    private static final String KEY = "key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chess);

       playerTurn = findViewById(R.id.WelcomeMessage);
       doneButton = findViewById(R.id.Done_Button);
       resignButton = findViewById(R.id.ResignButton);
       orig1 = getIntent().getExtras().getString("Player1");
       orig2 = getIntent().getExtras().getString("Player2");
       who = getIntent().getExtras().getString("who");
       username = getIntent().getExtras().getString(LoginActivity.USERNAME);
       password = getIntent().getExtras().getString(LoginActivity.PASSWORD);

       //timerTask = new TimerTask() {
       //    @Override
       //    public void run() {
       //        Cloud cloud = new Cloud();
       //        final GameData data = cloud.pullGame(username, password);
       //        doneButton.setEnabled(false);
       //        getChessView().invalidate();
       //        if (data != null && data.getStatus().equals("yes") && data.getTurn().equals(username)) {
       //            runOnUiThread(new Runnable() {
       //                @Override
       //                public void run() {
       //                    doneButton.setEnabled(true);
       //                    checkTurn(data.getTurn());
       //                    getChessView().getChess().updateBoard(data.getItems());
       //                    getChessView().invalidate();
       //                }
       //            });
       //            timer.cancel();
       //            timer.purge();
       //        }
       //    }
       //};


        welcome1 = orig1+"\'s turn - White";
        welcome2 = orig2+"\'s turn - Black";

        checkTurn(who);

        if (!username.equals(who)) {
            //timer.schedule(timerTask, 3000, 3000);
            doneButton.setEnabled(false);
            resignButton.setEnabled(false);
            getChessView().invalidate();
            startPullingFromServer();
        }

        if(savedInstanceState != null){
            curr = savedInstanceState.getString(KEY);
            username = savedInstanceState.getString(LoginActivity.USERNAME);
            password = savedInstanceState.getString(LoginActivity.PASSWORD);
            getChessView().loadInstanceState(savedInstanceState);
        }else{
            curr = welcome1;
        }
//       curr = welcome1;
        playerTurn.setText(curr);
        getChessView().getChess().setChessActivity(this);
    }

    private void startPullingFromServer() {
//        if(networkThreadRunning) {
//            try {
//                networkThread.join();
//            } catch (InterruptedException e) {
//            }
//            networkThreadRunning = false;
//        }
//        networkThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (!networkThreadRunning) return;
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    // possible bug
//                    networkThreadRunning = false;
//                    return;
//                }
//                Cloud cloud = new Cloud();
//                while (true) {
//                    if (!networkThreadRunning) return;
//                    final GameData data = cloud.pullGame(username, password);
//                    if (!networkThreadRunning) return;
//                    if (data != null && data.getStatus().equals("yes") && data.getTurn().equals(username)) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                checkTurn(data.getTurn());
//                                if (data.getItems() != null) {
//                                    getChessView().getChess().updateBoard(data.getItems());
//                                }
//                                getChessView().invalidate();
//                            }
//                        });
//                        networkThreadRunning = false;
//                        return;
//                    }
//                    if (!networkThreadRunning) return;
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        // possible bug
//                        networkThreadRunning = false;
//                        return;
//                    }
//                }
//            }
//        });
//        networkThread.start();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final GameData data = cloud.pullGame(username, password);
                if(data != null && data.getStatus().equals("yes") && data.getGameOver() != null) {
                    final String winner = data.getGameOver();
                    final String loser;
                    if(winner.equals(orig1)){
                        loser = orig2;
                    } else {
                        loser = orig1;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startGameOverActivity(winner, loser);
                        }
                    });
                    timer.cancel();
                    timer.purge();
                }
                if (data != null && data.getStatus().equals("yes") && data.getTurn().equals(username)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            doneButton.setEnabled(true);
                            resignButton.setEnabled(true);
                            checkTurn(data.getTurn());
                            if (data.getItems() != null) {
                              getChessView().getChess().updateBoard(data.getItems());
                            }
                            getChessView().invalidate();
                        }
                    });
                    timer.cancel();
                    timer.purge();
                }
            }
        };
        timer.schedule(timerTask, 3000, 3000);
    }

    public void checkTurn(String whoseTurn){

        //is who player 1 or player 2
        if(whoseTurn.equals(orig1)){
            //player1
            String player = welcome1;
            playerTurn.setText(player);
            playerTurn.invalidate();
            if(username.equals(whoseTurn)){
                getChessView().getChess().setTurn(false);
                getChessView().getChess().setBlcker(false);
                color = false;
            }else{
                getChessView().getChess().setTurn(true);
                getChessView().getChess().setBlcker(true);
                color = true;
            }

        }else{
            //player 2
            String player = welcome2;
            playerTurn.setText(player);
            playerTurn.invalidate();
            if(username.equals(whoseTurn)){
                color = true;
                getChessView().getChess().setTurn(true);
                getChessView().getChess().setBlcker(true);

            }else{
                getChessView().getChess().setTurn(false);
                getChessView().getChess().setBlcker(false);
                color = false;
            }
        }

        getChessView().invalidate();

    }


    /**
     * Handler for pressing the "Done" button on the game page
     */
    public void onDone(View view) {
       boolean turn = getChessView().getChess().getTurn();
        if (turn == color) {
           // getChessView().getChess().setTurn(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Cloud cloud = new Cloud();
                    cloud.pushGameState(username, password, getChessView().getChess());
                }
            }).start();
            //change the turn
            doneButton.setEnabled(false);
            resignButton.setEnabled(false);
            getChessView().invalidate();

            checkTurn(getChessView().getChess().getTurn()? orig1 : orig2);
            //timer.schedule(timerTask, 3000, 3000);
            startPullingFromServer();
        }
    }


    private void triggerTimer() {
        timer.schedule(timerTask, 3000, 3000);
    }



    /**
     * Handler for pressing the "resign" button on the game page
     */
    public void onResign(View view)
    {
        final View view1 = view;
        final String[] options = {"YES", "NO"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to resign?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on option[which]
                if (options[which].equals("YES"))
                {
                    endGame = true;
                    endTheGame(view1);
                    view1.invalidate();
                }
            }
        });
        builder.show();

    }

    /**
     * Handles view switch if someone resigns
     */
    public void endTheGame(View view)
    {
        view.invalidate();
        if (endGame)
        {
            Intent intent = new Intent(this, GameOverActivity.class);

            final String loser1= username;
            final String winner1;
            if(username.equals(orig1)){
                winner1 = orig2;
            } else {
                winner1 = orig1;
            }

            new Thread( new Runnable() {
                @Override
                public void run() {
                    Cloud cloud = new Cloud();
                    cloud.end(username, password);
                }
            }).start();

            startGameOverActivity(winner1, loser1);
        }
        view.invalidate();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(KEY,curr);
        bundle.putString(LoginActivity.USERNAME, username);
        bundle.putString(LoginActivity.PASSWORD, password);
        ChessView view = (ChessView)this.findViewById(R.id.ChessView);
        view.saveInstanceState(bundle);
    }

    /**
     * Get the Chess view
     *
     * @return ChessView reference
     */
    private ChessView getChessView() {
        return (ChessView )this.findViewById(R.id.ChessView );
    }

    /**
     * Handles the view switch if someone wins the game by capturing opponent's king
     */
    public void gameOver(View view)
    {
        view.invalidate();

        Intent intent = new Intent(this, GameOverActivity.class);

        final String winner1 = username;
        final String loser1;
        if(username.equals(orig1)){
            loser1 = orig2;
        } else {
            loser1 = orig1;
        }

        new Thread( new Runnable() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                cloud.end(username, password);
            }
        }).start();

        startGameOverActivity(winner1, loser1);

        if(curr.matches(welcome1))
        {
            winner = orig1;
            loser = orig2;
        }
        else
        {
            winner = orig2;
            loser = orig1;
        }

        //intent.putExtra("theWinner", winner+" wins!");
        //intent.putExtra("theLoser", loser+" loses!");

        //startActivity(intent);

        //finish();

        view.invalidate();
    }


    public void startGameOverActivity(final String winner, final String loser) {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("theWinner", winner+" wins!");
        intent.putExtra("theLoser", loser+" loses!");
        startActivity(intent);
        finish();
    }

}
