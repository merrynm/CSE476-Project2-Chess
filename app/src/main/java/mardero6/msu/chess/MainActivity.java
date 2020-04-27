package mardero6.msu.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText p1;
    EditText p2;

    private String player1;
    private String player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public String getPlayer1(){return this.player1;}
    public String getPlayer2(){return this.player2;}

    public void onStartChess(View view) {
        // store input value

        p1 = findViewById(R.id.editTextPlayer1);
        p2 = findViewById(R.id.editTextPlayer2);


        player1 = p1.getText().toString();
        player2 = p2.getText().toString();

        if (player1.matches(""))
        {
            player1 = "Player 1";
        }

        if (player2.matches(""))
        {
            player2 = "Player 2";
        }


        Intent intent = new Intent(this, ChessActivity.class);

        intent.putExtra("Player1", player1);
        intent.putExtra("Player2", player2);

        startActivity(intent);

        finish();

    }

    public void onInstructions(View view) {
        //LayoutInflater inflater= LayoutInflater.from(this);
        //View new_view =inflater.inflate(R.layout.instructions, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Instructions");
        alertDialog.setMessage(                "The rook usually looks like a small tower. It moves in a straight line horizontally or vertically for any number of squares.\n" +
                "The bishop moves in a straight line diagonally for any number of squares.\n" +
                "The queen, the most powerful piece in chess, can move any number of squares in a straight line horizontally, vertically or diagonally.\n" +
                "The king can also move in any direction, including diagonally, but it can only move one square at a time.\n" +
                "The knight, which usually looks like a horse, moves in an irregular, L-shaped pattern. From the center of the board, the knight can move to eight different squares. Though the knight can leap over other pieces, it doesn\'t capture pieces it jumps over; it only captures a piece on a square it lands on.\n" +
                "Pawns are the shortest and weakest pieces in chess. Pawns are also the only pieces that move one way, but capture in another fashion. Unlike other pieces, pawns can only move forward, not backward. A pawn can only move directly forward one square at a time unless it is still on the square on which it began the game; if it is the pawn\'s first move, it can move one or two squares directly forward.\n" +
                "A pawn cannot capture a piece directly in front of it.\n" +
                "Pawns can only capture a piece by moving one square forward diagonally.\n" +
                "\n" +
                "When a player\'s king is under attack and threatened with capture, you say that the king is in check. When in check, that player must take action to avoid having his king captured by moving the king, capturing the attacking piece, or–except in the case of a knight check–blocking the attack.\n" +
                "\n" +
                "You win the game via checkmate, where you attack your opponent\'s king in such a way that he cannot avoid being captured. To win the game, the victorious player doesn\\'t actually capture the enemy king; once capture is inevitable, checkmate has occurred and the game is over. A player who knows defeat is inevitable may also resign the game rather than wait to be checkmated.\n"
        );
        //alertDialog.setView(new_view);
        alertDialog.setPositiveButton(android.R.string.ok, null);
        AlertDialog alert = alertDialog.create();
        alert.show();


/*        AlertDialog.Builder builder =
                new AlertDialog.Builder(view.getContext());

        // Parameterize the builder
        builder.setTitle(R.string.instr_title);
        builder.setMessage(R.string.instructions);
        builder.setPositiveButton(android.R.string.ok, null);

        // Create the dialog box and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();*/
    }



}
