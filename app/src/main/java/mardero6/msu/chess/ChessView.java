package mardero6.msu.chess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.security.Policy;


/**
 * Custom view class for our Puzzle.
 */
public class ChessView extends View {

    private Chess Chess;

    public ChessView(Context context) {
        super(context);
        init(null, 0);
    }

    public ChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ChessView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        Chess = new Chess(getContext());
        //Chess.SetChessView(this);

    }

    /**
     * Load the chess from a bundle
     * @param bundle The bundle we save to
     */
    public void loadInstanceState(Bundle bundle) {
        Chess.loadInstanceState(bundle);
    }

    /**
     * Save the chess to a bundle
     * @param bundle The bundle we save to
     */
    public void saveInstanceState(Bundle bundle) {
        Chess.saveInstanceState(bundle);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return Chess.onTouchEvent(this, event);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Chess.draw(canvas);

    }
    public Chess getChess() {
        return Chess;
    }


    public void loadXml(XmlPullParser xml) throws IOException, XmlPullParserException {
        // Create a new set of parameters
        // process xml
        // xml should be a board filled pieces
        // foreach piece, float x, float y, int color, int type
        // <board>
        //   <piece x="" y="" color="" type="" />
        //   ...
        //   <piece x="" y="" color="" type="" />
        // </board>
        //    ...
        //

        // Load into it
//        newParams.imageUri = xml.getAttributeValue(null, "uri");
//        newParams.hatX = Float.parseFloat(xml.getAttributeValue(null, "x"));
//        newParams.hatY = Float.parseFloat(xml.getAttributeValue(null, "y"));
//        newParams.hatAngle = Float.parseFloat(xml.getAttributeValue(null, "angle"));
//        newParams.hatScale = Float.parseFloat(xml.getAttributeValue(null, "scale"));
//        newParams.color = Integer.parseInt(xml.getAttributeValue(null, "color"));
//        newParams.hat = Integer.parseInt(xml.getAttributeValue(null, "type"));
//        newParams.feather = xml.getAttributeValue(null, "feather").equals("yes");

//        post(new Runnable() {
//
//            @Override
//            public void run() {
//                params = newParams;
//
//                // Ensure the options are all set
//                setColor(params.color);
//                setImageUri(params.imageUri);
//                setHat(params.hat);
//                setFeather(params.feather);
//
//            }
//
//        });

    }

//    public void saveXml(String name, XmlSerializer xml) throws IOException {
//        xml.startTag(null, "hatting");
//
//        xml.attribute(null, "name", name);
//        xml.attribute(null, "uri", params.imageUri != null ? params.imageUri : "");
//        xml.attribute(null, "x", Float.toString(params.hatX));
//        xml.attribute(null, "y", Float.toString(params.hatY));
//        xml.attribute(null, "angle", Float.toString(params.hatAngle));
//        xml.attribute(null, "scale", Float.toString(params.hatScale));
//        xml.attribute(null,  "color", Integer.toString(params.color));
//        xml.attribute(null, "hat", Integer.toString(params.hat));
//        xml.attribute(null, "feather", params.feather ? "yes" : "no");
//
//        xml.endTag(null,  "hatting");
//    }
//


}