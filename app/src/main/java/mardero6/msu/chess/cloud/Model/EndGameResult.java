package mardero6.msu.chess.cloud.Model;

import androidx.annotation.AttrRes;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root( name = "secretary")
public class EndGameResult {
    @Attribute
    private String status;

    @Attribute
    private String msg;

    @Attribute(required = false)
    private String winner;

    @Attribute(required = false)
    private String loser;

    @Attribute(required = false)
    private String end_game;


    public EndGameResult() {}

    public String getEnd_game() {
        return end_game;
    }

    public void setEnd_game(String end_game) {
        this.end_game = end_game;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
