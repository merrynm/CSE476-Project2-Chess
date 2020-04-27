package mardero6.msu.chess.cloud.Model;

import androidx.annotation.AttrRes;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "piece")
public class PieceData {
    @Attribute
    private String player_color;
    @Attribute
    private String piece_type;
    @Attribute
    private float board_row_x;
    @Attribute
    private float board_col_y;

    public PieceData()
    {}

    public String getPlayer_color() {
        return player_color;
    }

    public void setPlayer_color(String player_color) {
        this.player_color = player_color;
    }

    public String getPiece_type() {
        return piece_type;
    }

    public void setPiece_type(String piece_type) {
        this.piece_type = piece_type;
    }

    public float getBoard_row_x() {
        return board_row_x;
    }

    public void setBoard_row_x(float board_row_x) {
        this.board_row_x = board_row_x;
    }

    public float getBoard_col_y() {
        return board_col_y;
    }

    public void setBoard_col_y(float board_col_y) {
        this.board_col_y = board_col_y;
    }
}
