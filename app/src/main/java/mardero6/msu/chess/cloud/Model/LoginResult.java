package mardero6.msu.chess.cloud.Model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "secretary")
public class LoginResult {
    @Attribute(required  = false)
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Attribute
    private String status;

    @Attribute(name="new_game", required = false)
    private Boolean newGame;

    public Boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(Boolean state) {
        newGame = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginResult() {} // this is required by RetroFit

    public LoginResult(String message, String status, boolean newGame) {
        this.msg = message;
        this.status = status;
        this.newGame = newGame;
    }
}


