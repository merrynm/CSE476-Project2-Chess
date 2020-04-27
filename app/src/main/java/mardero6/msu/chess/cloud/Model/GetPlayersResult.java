package mardero6.msu.chess.cloud.Model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name ="secretary")
public class GetPlayersResult {
    @Attribute
    private String status;

    @Attribute
    private String player1;

    @Attribute
    private String player2;

    @Attribute
    private String msg;

    GetPlayersResult() { }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }
}
