package mardero6.msu.chess.cloud.Model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "secretary")
public final class GameData {

    @Attribute
    private String msg;

    @Attribute(required=false)
    private String who;

    @Attribute
    private String status;

    @Attribute(required=false)
    private int player_num;

    @Attribute (required=false)
    private String gameover;

    @Attribute(required = false)
    private String winner;

    public GameData() {

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @ElementList(name = "board", inline = true, required = false, type = PieceData.class)
    private List<PieceData> items;

    public void GameData()
    {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTurn() {
        return who;
    }

    public void setTurn(String turn) {
        this.who = turn;
    }

    public int getPlayer_num() {
        return player_num;
    }

    public void setPlayer_num(int player_num) {
        this.player_num = player_num;
    }

    public List<PieceData> getItems() {
        return items;
    }

    public void setItems(List<PieceData> items) {
        this.items = items;
    }

    public String getGameOver() {
        return gameover;
    }

    public void setGameOver(String gameover) {
        this.gameover = gameover;
    }
}
