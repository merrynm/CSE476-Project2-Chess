package mardero6.msu.chess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

//import com.google.gson.Gson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mardero6.msu.chess.cloud.Model.PieceData;

import static java.security.AccessController.getContext;


public class Chess {
    private ChessView chessView;
        /**
     * The name of the bundle keys to save the pieces1 array
     */
    private final static String LOCATIONS = "Chess.locations";
    private final static String IDS = "Chess.ids";

    /**
     * The name of the bundle keys to save the pieces2 array
     */
    private final static String LOCATIONS2 = "Chess.locations2";
    private final static String IDS2 = "Chess.ids2";

    private final static String TURN = "Chess.turn";
    private final static String BLCKER = "Chess.blcker";

    private final static String MARGINX = "Chess.marginX";
    private final static String MARGINY = "Chess.marginY";
    private final static String CHESS_SIZE = "Chess.chessSize";

    private final static String LASTREALX = "Chess.lastRealX";
    private final static String LASTREALY = "Chess.lastRealX";
    private final static String SCALE_FACTOR = "Chess.scaleFactor";

    private final static String CHESS_ACTIVITY = "Chess.chessActivity";
    private final static String CONTEXT = "Chess.context";

    private final static String PIECES = "Chess.pieces";
    private final static String PIECES2 = "Chess.pieces2";

    private String promo_choice ="fale"; // name of the piece selected for promotion
    private ChessActivity chessActivity;
    private Bitmap chessboard;
    final static float SCALE_IN_VIEW = 0.97f;

    private boolean turn = true;
    private boolean blcker = true;

    private int marginY;
    private int marginX;
    private int chessSize;

    private float lastRelX;
    private float lastRelY;
    private float scaleFactor;

    public ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>();
    public ArrayList<ChessPiece> pieces2 = new ArrayList<ChessPiece>();
    private ChessPiece dragging = null;

    private Context context;
    private ChessPiece prom ;
    private View upview;


    public void setBlcker(boolean val){
        blcker = val;
    }

    /**
     * Setter for the current chess activity
     */
    public void setChessActivity (ChessActivity chessA)
    {
        chessActivity = chessA;
    }

    public Chess(Context context) {
        this.context = context;
        chessboard = BitmapFactory.decodeResource(context.getResources(), R.drawable.chess_board);
        // player1 first row
        pieces.add(new ChessPiece(context, PieceType.ROOK,   R.drawable.chess_rdt45, 0.06f, 0.06f));
        pieces.add(new ChessPiece(context, PieceType.KNIGHT, R.drawable.chess_ndt45, 0.186f, 0.06f));
        pieces.add(new ChessPiece(context, PieceType.BISHOP, R.drawable.chess_bdt45, 0.311f, 0.06f));
        pieces.add(new ChessPiece(context, PieceType.QUEEN,  R.drawable.chess_qdt45, 0.437f, 0.06f));
        pieces.add(new ChessPiece(context, PieceType.KING,   R.drawable.chess_kdt45, 0.562f, 0.06f));
        pieces.add(new ChessPiece(context, PieceType.BISHOP, R.drawable.chess_bdt45, 0.688f, 0.06f));
        pieces.add(new ChessPiece(context, PieceType.KNIGHT, R.drawable.chess_ndt45, 0.813f, 0.06f));
        pieces.add(new ChessPiece(context, PieceType.ROOK,   R.drawable.chess_rdt45, 0.939f, 0.06f));
        // player1 second row
        pieces.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_pdt45, 0.06f, 0.186f));
        pieces.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_pdt45, 0.186f, 0.186f));
        pieces.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_pdt45, 0.311f, 0.186f));
        pieces.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_pdt45, 0.437f, 0.186f));
        pieces.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_pdt45, 0.562f, 0.186f));
        pieces.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_pdt45, 0.688f, 0.186f));
        pieces.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_pdt45, 0.813f, 0.186f));
        pieces.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_pdt45, 0.939f, 0.186f));
        // player2 first row
        pieces2.add(new ChessPiece(context,PieceType.ROOK,   R.drawable.chess_rlt45, 0.06f, 0.939f));
        pieces2.add(new ChessPiece(context,PieceType.KNIGHT, R.drawable.chess_nlt45, 0.186f, 0.939f));
        pieces2.add(new ChessPiece(context,PieceType.BISHOP, R.drawable.chess_blt45, 0.311f, 0.939f));
        pieces2.add(new ChessPiece(context,PieceType.QUEEN,  R.drawable.chess_qlt45, 0.437f, 0.939f));
        pieces2.add(new ChessPiece(context,PieceType.KING,   R.drawable.chess_klt45, 0.562f, 0.939f));
        pieces2.add(new ChessPiece(context,PieceType.BISHOP, R.drawable.chess_blt45, 0.688f, 0.939f));
        pieces2.add(new ChessPiece(context,PieceType.KNIGHT, R.drawable.chess_nlt45, 0.813f, 0.939f));
        pieces2.add(new ChessPiece(context,PieceType.ROOK,   R.drawable.chess_rlt45, 0.939f, 0.939f));
        // player2 second row
        pieces2.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_plt45, 0.06f, 0.813f));
        pieces2.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_plt45, 0.186f, 0.813f));
        pieces2.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_plt45, 0.311f, 0.813f));
        pieces2.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_plt45, 0.437f, 0.813f));
        pieces2.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_plt45, 0.562f, 0.813f));
        pieces2.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_plt45, 0.688f, 0.813f));
        pieces2.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_plt45, 0.813f, 0.813f));
        pieces2.add(new ChessPiece(context, PieceType.PAWN, R.drawable.chess_plt45, 0.939f, 0.813f));

    }

    /**
     * Draws the chess board and all of its pieces
     */
    public void draw(Canvas canvas) {

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();
        int minDim = wid < hit ? wid : hit;
        chessSize = (int) (minDim * SCALE_IN_VIEW);
        marginX = (int) ((wid - chessSize) / 2f);
        marginY = (int) ((hit - chessSize) / 2f);
        scaleFactor = (float) chessSize / (float) chessboard.getWidth();
        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawBitmap(chessboard, 0, 0, null);
        canvas.restore();

        for (ChessPiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, chessSize, scaleFactor);
        }
        for (ChessPiece piece : pieces2) {
            piece.draw(canvas, marginX, marginY, chessSize, scaleFactor);
        }
    }

    /**
     * Hit tester
     */
    private boolean onTouched(float x, float y) {

        // Check each piece to see if it has been hit
        // We do this in reverse order so we find the pieces in front
        if (blcker != turn)
            return false;
        if (turn) {
            for (int p = pieces.size() - 1; p >= 0; p--) {
                if (pieces.get(p).hit(x, y, chessSize)) {
                    dragging = pieces.get(p);
                    pieces.remove(p);
                    pieces.add(dragging);
                    lastRelX = x;
                    lastRelY = y;

                    return true;
                }
            }
        } else {
            for (int p = pieces2.size() - 1; p >= 0; p--) {
                if (pieces2.get(p).hit(x, y, chessSize)) {
                    dragging = pieces2.get(p);
                    pieces2.remove(p);
                    pieces2.add(dragging);
                    lastRelX = x;
                    lastRelY = y;

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Handler for touch event
     */
    public boolean onTouchEvent(View view, MotionEvent event) {
        float relX = (event.getX() - marginX) / chessSize;
        float relY = (event.getY() - marginY) / chessSize;
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY);


            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // invalid move; move piece back to last location
                if (! onReleased(view, relX, relY)){
                    // invalid move popup
                    Toast.makeText(chessActivity, "Invalid Move!", Toast.LENGTH_LONG).show();
                    dragging.finalX = dragging.lastX;
                    dragging.finalY = dragging.lastY;
                    view.invalidate();
                }
                return onReleased(view, relX, relY);

            case MotionEvent.ACTION_MOVE:
                if (dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
                    view.invalidate();
                    return true;
                }
                break;
        }

        return false;
    }

    /**
     * Handler for release event
     */
    private boolean onReleased(View view, float x, float y) {
        if (dragging != null) {
            if (dragging.maybeSnap()) {
                // We have snapped into place
                if (dragging.id == R.drawable.chess_plt45 | dragging.id == R.drawable.chess_pdt45) {
                    if (!Pawn(dragging, dragging.finalX, dragging.finalY, dragging.lastX, dragging.lastY)) {
                        return false;
                    } else {
                        if (dragging.finalY >= 0.939 |dragging.finalY <=0.06) {
                            //change the piece
                            prom = dragging;
                            PromotionDlgBox(view);
                        }
                    }
                }
                if (dragging.id == R.drawable.chess_rdt45 | dragging.id == R.drawable.chess_rlt45) {
                    if (!Rook(dragging, dragging.finalX, dragging.finalY, dragging.lastX, dragging.lastY)) {
                        return false;
                    }
                }
                if (dragging.id == R.drawable.chess_ndt45 | dragging.id == R.drawable.chess_nlt45) {
                    if (!Horse(dragging, dragging.finalX, dragging.finalY, dragging.lastX, dragging.lastY)) {
                        return false;
                    }
                }
                if (dragging.id == R.drawable.chess_bdt45 | dragging.id == R.drawable.chess_blt45) {
                    if (!bishops(dragging, dragging.finalX, dragging.finalY, dragging.lastX, dragging.lastY)) {
                        return false;
                    }
                }
                if (dragging.id == R.drawable.chess_qdt45 | dragging.id == R.drawable.chess_qlt45) {
                    if (!queen(dragging, dragging.finalX, dragging.finalY, dragging.lastX, dragging.lastY)) {
                        return false;
                    }
                }
                if (dragging.id == R.drawable.chess_kdt45 | dragging.id == R.drawable.chess_klt45) {
                    if (!King(dragging, dragging.finalX, dragging.finalY, dragging.lastX, dragging.lastY)) {
                        return false;
                    }
                }
                // checking if the king has been captured
                boolean gameover = true;
                if (!turn) {
                    for (int p = pieces.size() - 1; p >= 0; p--) {
                        if (pieces.get(p).id == R.drawable.chess_kdt45) {
                            gameover = false;
                        }
                    }
                    if (gameover){
                        // player 1 win
                        chessActivity.gameOver(view);
                    }
                }
                else {
                    for (int p = pieces2.size() - 1; p >= 0; p--) {
                        if (pieces2.get(p).id == R.drawable.chess_klt45) {
                            gameover = false;
                        }
                    }
                    if (gameover){
                        chessActivity.gameOver(view);
                    }
                }

                if (Math.abs(dragging.lastX - dragging.finalX) > 0.01 | Math.abs(dragging.lastY - dragging.finalY) > 0.01) {
                    dragging.lastX = dragging.finalX;
                    dragging.lastY = dragging.finalY;
                    dragging.first = false;
                    blcker = !turn;
                }
            }
            dragging = null;
            view.invalidate();
            return true;
        }
        view.invalidate();
        return false;
    }

    /**
     * Getter for the game pieces
     */
    int getP(float x, float y) {
        for (int p = pieces.size() - 1; p >= 0; p--) {
            if (pieces.get(p).lastY == y & pieces.get(p).lastX == x) {
                return p;
            }
        }
        for (int p = pieces2.size() - 1; p >= 0; p--) {
            if (pieces2.get(p).lastY == y & pieces2.get(p).lastX == x) {
                return p;
            }
        }
        return -1;
    }

    /**
     * Getter for player 1's game pieces
     */
    int getP1(float x, float y) {
        for (int p = pieces.size() - 1; p >= 0; p--) {
            if (pieces.get(p).lastY == y & pieces.get(p).lastX == x) {
                return p;
            }
        }
        return -1;
    }

    /**
     * Getter for player 2's game pieces
     */
    int getP2(float x, float y) {
        for (int p = pieces2.size() - 1; p >= 0; p--) {
            if (pieces2.get(p).lastY == y & pieces2.get(p).lastX == x) {
                return p;
            }
        }
        return -1;
    }

    /**
     * Sets the turn to the current player
     */
    void setTurn(boolean turn1) {
        turn = turn1;
    }

    /**
     * Pawn rules implementation
     */
    private boolean Pawn(ChessPiece piece, float x, float y, float x2, float y2) {
        float Y= y*1000;
        float X= x*1000;
        float X2= x2*1000;
        float Y2= y2*1000;
        if (turn) {
            if (((Y - Y2) > 124 & (Y - Y2) < 128) & (Math.abs(X - X2) >124 & Math.abs(X - X2) < 128) ) {
                if (getP2(x, y) != -1) {
                    pieces2.remove(getP2(x, y));
                    return true;

                } else {
                    return false;
                }
            }
        } else {

            if (((Y2 - Y) > 124 &(Y2 - Y) < 128) & (Math.abs(X - X2) >124 & Math.abs(X - X2) < 128)) {
                if (getP1(x, y) != -1) {
                    pieces.remove(getP1(x, y));
                    return true;

                } else {
                    return false;
                }
            }
        }

        if (Math.abs(X - X2) == 0) {
            if (getP(x, y) != -1) {
                return false;
            }
            if (piece.first) {
                if (turn) {
                    if ((Y - Y2) <= 255) {
                        if (getP(x, (float) ((Y - 126)/1000)) != -1)
                            return false;
                        return true;
                    }
                } else {
                    if ((Y2 - Y) <= 255) {
                        if (getP(x, (float) ((Y + 126)/1000)) != -1)
                            return false;
                        return true;
                    }
                }
            }
            else {
                if (turn) {
                    if ((Y - Y2) > 127 | (y - y2) < 0)
                        return false;
                    return true;
                }
                else {
                    if ((Y2 - Y) > 127 | (y2 - y) < 0)
                        return false;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Getter for current player's turn
     */
    boolean getblk (){
        return turn == blcker;
    }

    /**
     * Rook rules implementation
     */
    private boolean Rook(ChessPiece piece, float x, float y, float x2, float y2) {
        float Y= y*1000;
        float X= x*1000;
        float X2= x2*1000;
        float Y2= y2*1000;
        if (X == X2){
            while  (Y2 > Y){
                Y = Y+ 125;
                if (Y== 687 | Y==436 | Y==185)
                    Y++;
                if (Y==Y2)
                    break;
                if (getP(x,(Y/1000)) != -1)
                    return false;
            }
            while  (Y2 < Y){
                Y= Y- 125;
                if (Y== 312 | Y==563 | Y==814)
                    Y--;
                if (Y==Y2)
                    break;
                if (getP(x, (Y/1000)) != -1)
                    return false;
            }
            if (getP(x, y) == -1)
                return true;
            else{
                if (turn & getP2(x, y) != -1) {
                    pieces2.remove(getP2(x, y));
                    return true;
                }
                else if (!turn & getP1(x, y) != -1) {
                    pieces.remove(getP1(x, y));
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        if (Y==Y2){
            while  (X2 > X){
                X= X+ 125;
                if (X== 687 | X==436 | X==185)
                    X++;
                if (X==X2)
                    break;
                if (getP(X/1000, y) != -1)
                    return false;
            }
            while  (X2 < X){
                X= X- 125;
                if (X== 312 | X==563 | X==814)
                    X--;
                if (X==X2)
                    break;
                if (getP(X/1000, y) != -1)
                    return false;
            }
            if (getP(x, y) == -1)
            return true;
            else {
                if (turn & getP2(x, y) != -1) {
                    pieces2.remove(getP2(x, y));
                    return true;
                } else if (!turn & getP1(x, y) != -1) {
                    pieces2.remove(getP1(x, y));
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Horse (Knight) rules implementation
     */
    private boolean Horse(ChessPiece piece, float x, float y, float x2, float y2) {
        float Y = y * 1000;
        float X = x * 1000;
        float X2 = x2 * 1000;
        float Y2 = y2 * 1000;
        if ((Math.abs(Y - Y2) > 248 & Math.abs(Y - Y2) < 256) & (Math.abs(X - X2) > 124 & Math.abs(X - X2) < 128)) {
            if (getP(x, y) != -1) {
                if (turn) {
                    if (getP2(x, y) != -1) {
                        pieces2.remove(getP2(x, y));
                        return true;
                    }
                } else {
                    if (getP1(x, y) != -1) {
                        pieces.remove(getP1(x, y));
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        if (getP(x, y) != -1) {
            return false;
        }
        if (X2==X & Math.abs(Y - Y2) <= 255){
            return true;
        }
        return false;
    }

    /**
     * Bishop rules implementation
     */
    private boolean bishops(ChessPiece piece, float x, float y, float x2, float y2){
        float Y= y*1000;
        float X= x*1000;
        float X2= x2*1000;
        float Y2= y2*1000;
        //float dY= Math.abs(Y - Y2);
        //float dX= Math.abs(X - X2);
        float dy= Y - Y2;
        float dx= X - X2;
        if(Math.abs(Y - Y2)-Math.abs(X - X2) >= -1 & Math.abs(Y - Y2)-Math.abs(X - X2) <= 1){
            if(dy<0 & dx<0){
                while  (Y2 > Y){
                    Y = Y+ 125;
                    X= X+ 125;
                    if (Y== 687 | Y==436 | Y==185)
                        Y++;
                    if (X== 687 | X==436 | X==185)
                        X++;
                    if (Y==Y2)
                        break;
                    if (getP(X/1000, Y/1000) != -1)
                        return false;
                }
            }
            if(dy>0 & dx<0){
                while  (Y2 < Y){
                    Y = Y- 125;
                    X= X+ 125;
                    if (Y== 312 | Y==563 | Y==814)
                        Y--;
                    if (X== 687 | X==436 | X==185)
                        X++;
                    if (Y==Y2)
                        break;
                    if (getP(X/1000, Y/1000) != -1)
                        return false;
                }
            }
            if(dy<0 & dx>0){
                while  (Y2 > Y){
                    Y = Y+ 125;
                    X= X- 125;
                    if (Y== 687 | Y==436 | Y==185)
                        Y++;
                    if (X== 312 | X==563 | X==814)
                        X--;
                    if (Y==Y2)
                        break;
                    if (getP(X/1000, Y/1000) != -1)
                        return false;
                }
            }
            if(dy>0 & dx>0){
                while  (Y2 < Y){
                    Y = Y- 125;
                    X= X- 125;
                    if (Y== 312 | Y==563 | Y==814)
                        Y--;
                    if (X== 312 | X==563 | X==814)
                        X--;
                    if (Y==Y2)
                        break;
                    if (getP(X/1000, Y/1000) != -1)
                        return false;
                }
            }
            if (getP(x, y) == -1)
                return true;
            else {
                if (turn & getP2(x, y) != -1) {
                    pieces2.remove(getP2(x, y));
                    return true;
                } else if (!turn & getP1(x, y) != -1) {
                    pieces.remove(getP1(x, y));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Queen rules implementation
     */
    private boolean queen(ChessPiece piece, float x, float y, float x2, float y2){
        if (Rook(piece, x, y, x2, y2) | bishops(piece, x, y, x2, y2))
            return true;
        return false;
    }

    /**
     * King rules implementation
     *
     */
    private boolean King(ChessPiece piece, float x, float y, float x2, float y2){
        float Y= y*1000;
        float X= x*1000;
        float X2= x2*1000;
        float Y2= y2*1000;
        if ( Math.abs(Y - Y2) < 128 & Math.abs(X - X2) < 128) {
            if (getP(x, y) != -1) {
                if (turn) {
                    if (getP2(x, y) != -1) {
                        pieces2.remove(getP2(x, y));
                        return true;
                    }
                } else {
                    if (getP1(x, y) != -1) {
                        pieces.remove(getP1(x, y));
                        return true;
                    }
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Dialog box handler for pawn promotion
     * @param view the current view
     */
    private void PromotionDlgBox (View view){
        final String[] choices = {"knight", "bishop", "rook", "queen"};


        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Select a piece to promote to:");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on choices[which]
                //switch the piece
                promo_choice = choices[which];
                pormotion ();
            }
        });
        upview = view;
        view.invalidate();
        builder.show();
    }

    /**
     * Switches pawn to promoted piece
     */
    void pormotion (){
        if (!turn){
            if (promo_choice.equals("knight"))
            {
                pieces2.add(new ChessPiece(context, PieceType.KNIGHT, R.drawable.chess_nlt45, prom.finalX, prom.finalY));
            }
            else if (promo_choice.equals("bishop"))
            {
                pieces2.add(new ChessPiece(context, PieceType.BISHOP, R.drawable.chess_blt45, prom.finalX, prom.finalY));
            }
            else if (promo_choice.equals("rook"))
            {
                pieces2.add(new ChessPiece(context, PieceType.ROOK, R.drawable.chess_rlt45, prom.finalX, prom.finalY));
            }
            else if (promo_choice.equals("queen"))
            {
                pieces2.add(new ChessPiece(context, PieceType.QUEEN, R.drawable.chess_qlt45, prom.finalX, prom.finalY));
            }
            pieces2.remove(prom);
        }
        else{
            if (promo_choice.equals("knight"))
            {
                pieces.add(new ChessPiece(context, PieceType.KNIGHT, R.drawable.chess_ndt45,prom.finalX, prom.finalY));
            }
            else if (promo_choice.equals("bishop"))
            {
                pieces.add(new ChessPiece(context, PieceType.BISHOP, R.drawable.chess_bdt45, prom.finalX, prom.finalY));
            }
            else if (promo_choice.equals("rook"))
            {
                pieces.add(new ChessPiece(context, PieceType.ROOK, R.drawable.chess_rdt45, prom.finalX, prom.finalY));
            }
            else if (promo_choice.equals("queen"))
            {
                pieces.add(new ChessPiece(context, PieceType.QUEEN, R.drawable.chess_qdt45, prom.finalX, prom.finalY));
            }
            pieces.remove(prom);
        }
        upview.invalidate();
    }

    /**
     * Read the Chess from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        float [] locations = bundle.getFloatArray(LOCATIONS);
        int [] ids = bundle.getIntArray(IDS);

        float [] locations2 = bundle.getFloatArray(LOCATIONS2);
        int [] ids2 = bundle.getIntArray(IDS2);

        turn = bundle.getBoolean(TURN);
        blcker = bundle.getBoolean(BLCKER);

        marginX = bundle.getInt(MARGINX);
        marginY = bundle.getInt(MARGINY);
        chessSize = bundle.getInt(CHESS_SIZE);

        pieces = (ArrayList<ChessPiece>) bundle.getSerializable(PIECES);
        pieces2 = (ArrayList<ChessPiece>) bundle.getSerializable(PIECES2);

        lastRelX = bundle.getFloat(LASTREALY);
//        lastRelY = bundle.getFloat(LASTREALX);
        scaleFactor = bundle.getFloat(SCALE_FACTOR);

        for(int i=0; i<ids.length-1; i++) {

            // Find the corresponding piece
            // We don't have to test if the piece is at i already,
            // since the loop below will fall out without it moving anything
            for(int j=i+1;  j<ids.length;  j++) {
                if(ids[i] == pieces.get(j).getId()) {
                    // We found it
                    // Yah...
                    // Swap the pieces
                    ChessPiece t = pieces.get(i);
                    pieces.set(i, pieces.get(j));
                    pieces.set(j, t);
                }
            }
        }

        for(int i=0; i<ids2.length-1; i++) {

            // Find the corresponding piece
            // We don't have to test if the piece is at i already,
            // since the loop below will fall out without it moving anything
            for(int j=i+1;  j<ids2.length;  j++) {
                if(ids2[i] == pieces2.get(j).getId()) {
                    // We found it
                    // Yah...
                    // Swap the pieces
                    ChessPiece t = pieces2.get(i);
                    pieces2.set(i, pieces2.get(j));
                    pieces2.set(j, t);
                }
            }
        }

        for(int i=0;  i<pieces.size(); i++) {
            ChessPiece piece = pieces.get(i);
            piece.setX(locations[i*2]);
            piece.setY(locations[i*2+1]);
        }

        for(int i=0;  i<pieces2.size(); i++) {
            ChessPiece piece = pieces2.get(i);
            piece.setX(locations2[i*2]);
            piece.setY(locations2[i*2+1]);
        }
    }

    /**
     * Save the chess to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        float [] locations = new float[pieces.size() * 2];
        int [] ids = new int[pieces.size()];

        float [] locations2 = new float[pieces2.size() * 2];
        int [] ids2 = new int[pieces2.size()];

        for(int i=0;  i<pieces.size(); i++) {
            ChessPiece piece = pieces.get(i);
            locations[i*2] = piece.getX();
            locations[i*2+1] = piece.getY();
            ids[i] = piece.getId();
        }

        for(int i=0;  i<pieces2.size(); i++) {
            ChessPiece piece = pieces2.get(i);
            locations2[i*2] = piece.getX();
            locations2[i*2+1] = piece.getY();
            ids2[i] = piece.getId();
        }

        bundle.putFloatArray(LOCATIONS, locations);
        bundle.putIntArray(IDS,  ids);

        bundle.putFloatArray(LOCATIONS2, locations2);
        bundle.putIntArray(IDS2,  ids2);

        bundle.putBoolean(TURN,turn);
        bundle.putBoolean(BLCKER,blcker);

        bundle.putInt(MARGINX,marginX);
        bundle.putInt(MARGINY,marginY);
        bundle.putInt(CHESS_SIZE,chessSize);

        bundle.putFloat(LASTREALX,lastRelX);
        bundle.putFloat(LASTREALY,lastRelY);
        bundle.putFloat(SCALE_FACTOR,scaleFactor);

        bundle.putSerializable(PIECES,(Serializable)pieces);
        bundle.putSerializable(PIECES2,(Serializable)pieces2);


    }

    public boolean getTurn() {
        return turn;
    }

    public enum PieceType {
        ROOK(0),
        KNIGHT(1),
        BISHOP(2),
        KING(3),
        QUEEN(4),
        PAWN(5),
        UNKNOWN(6);


        private final int value;

        private PieceType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        private static PieceType[] table = { ROOK, KNIGHT, BISHOP, KING, QUEEN, PAWN };

        public static PieceType decode(int i ) {
            if(i > 5 || i < 0) {
                return UNKNOWN;
            } else {
                return table[i];
            }
        }
    }

    public void saveXml(XmlSerializer xml) throws IOException {
        for(ChessPiece piece: pieces) {
            xml.startTag(null, "piece");
            xml.attribute(null, "player_color", "black");
            xml.attribute(null, "piece_type", String.valueOf(piece.getType()));
            xml.attribute(null, "board_row_x", String.valueOf(piece.finalX));
            xml.attribute(null, "board_col_y", String.valueOf(piece.finalY));
            xml.endTag(null, "piece");
        }
        for(ChessPiece piece: pieces2) {
            xml.startTag(null, "piece");
            xml.attribute(null, "player_color", "white");
            xml.attribute(null, "piece_type", String.valueOf(piece.getType()));
            xml.attribute(null, "board_row_x", String.valueOf(piece.finalX));
            xml.attribute(null, "board_col_y", String.valueOf(piece.finalY));
            xml.endTag(null, "piece");
        }
    }

    private int getImage(PieceType type, String color) {
        switch(type) {
            case KING:
                return color.equals("white") ? R.drawable.chess_klt45 : R.drawable.chess_kdt45;
            case PAWN:
                return color.equals("white") ? R.drawable.chess_plt45 : R.drawable.chess_pdt45;
            case QUEEN:
                return color.equals("white") ? R.drawable.chess_qlt45 : R.drawable.chess_qdt45;
            case BISHOP:
                return color.equals("white") ? R.drawable.chess_blt45 : R.drawable.chess_bdt45;
            case KNIGHT:
                return color.equals("white") ? R.drawable.chess_nlt45 : R.drawable.chess_ndt45;
            case ROOK:
                return color.equals("white") ? R.drawable.chess_rlt45 : R.drawable.chess_rdt45;
            default:
                return R.drawable.chess_bdt45;
        }
    }

    public void updateBoard(List<PieceData> ps) {
        pieces = new ArrayList<>();
        pieces2 = new ArrayList<>();

        for(PieceData p:ps){
            //check the color of p
            PieceType type = PieceType.valueOf(p.getPiece_type());
            if(p.getPlayer_color().equals("white")){
                //ChessPiece(Context context, Chess.PieceType type, int id, float loctX, float loctY) {
                pieces2.add(new ChessPiece(
                        context,
                        type,
                        getImage(type,p.getPlayer_color()),
                    p.getBoard_row_x(),
                    p.getBoard_col_y()));
            } else {
                pieces.add(new ChessPiece(
                        context,
                        type,
                        getImage(type,p.getPlayer_color()),
                        p.getBoard_row_x(),
                        p.getBoard_col_y()));
            }
        }

    }

}
