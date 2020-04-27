package mardero6.msu.chess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.Serializable;


public class ChessPiece implements Serializable {
    final static float SCALE_IN_VIEW = 0.3f;
    final static float SNAP_DISTANCE = 0.05f;
    private Bitmap piece;
    public  float finalX;
    public float finalY;
    public float lastX;
    public  float lastY;
    public int id;
    private float scaleFactorP;
    public boolean first = true;
    private final Chess.PieceType type;

    public Chess.PieceType getType() {
        return type;
    }


    public ChessPiece(Context context, Chess.PieceType type, int id, float loctX, float loctY) {
        this.finalX = loctX;
        this.finalY = loctY;
        lastX = finalX;
        lastY = finalY;
        this.id = id;
        this.type = type;
        piece = BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * The Chess piece ID getter
     */
    public int getId() {
        return id;
    }

    /**
     * The Chess piece X setter
     */
    public void setX(float x) {
        this.finalX = x;
        lastX = x;
    }

    /**
     * The Chess piece Y setter
     */
    public void setY(float y) {
        this.finalY = y;
        lastY = y;
    }

    /**
     * The Chess piece X getter
     */
    public float getX() {
        return finalX;
    }

    /**
     * The Chess piece Y getter
     */
    public float getY() {
        return finalY;
    }

    public void draw(Canvas canvas, int marginX, int marginY, int chessSize, float width) {
        canvas.save();

        float Piecesize = (float) (chessSize*SCALE_IN_VIEW );
        scaleFactorP =  width*SCALE_IN_VIEW ;
        canvas.translate(marginX + finalX * chessSize, marginY + finalY* chessSize);
        canvas.scale(scaleFactorP, scaleFactorP);
        canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);
        canvas.drawBitmap(piece, 0, 0, null);
        canvas.restore();
    }
    public boolean hit(float testX, float testY,
                       int chessSize) {

        // Make relative to the location and size to the piece size
        int pX = (int) ((testX - finalX) * chessSize / scaleFactorP) +
                piece.getWidth() / 2;
        int pY = (int) ((testY - finalY) * chessSize / scaleFactorP) +
                piece.getHeight() / 2;

        if (pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }
    public void move(float dx, float dy) {
        finalX += dx;
        finalY += dy;
    }
    public boolean maybeSnap() {
        int xx =  (int)Math.round(finalX*7.96813-0.478088);
        int yx = xx -1;
        float postionx = (float)(0.18558*xx -0.06*yx);
        int xy = (int)Math.round(finalY*7.96813-0.478088);
        int yy = xy -1;
        float postiony = (float)(0.18558*xy -0.06*yy);
        if(Math.abs(finalX - postionx) < SNAP_DISTANCE &&
                Math.abs(finalY -postiony) < SNAP_DISTANCE) {

            finalX = round(postionx);
            finalY= round(postiony);
            return true;
        }
        finalX = lastX;
        finalY= lastY;
        return false;
    }
    public boolean isSnapped() {
        return this.maybeSnap();
    }
    float round (float num){
        float rus = (float) (Math.round(num * 1000.0) / 1000.0);
        return rus;
    }
}
