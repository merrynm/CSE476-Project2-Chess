package mardero6.msu.chess.cloud;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;

import mardero6.msu.chess.Chess;
import mardero6.msu.chess.cloud.Model.EndGameResult;
import mardero6.msu.chess.cloud.Model.GameData;
import mardero6.msu.chess.cloud.Model.GetPlayersResult;
import mardero6.msu.chess.cloud.Model.Login;
import mardero6.msu.chess.cloud.Model.LoginResult;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Cloud {
    public static final String SAVE_URL = "https://webdev.cse.msu.edu/~binsfel4/cse476/project2/saveChess.php";
    public static final String DELETE = "deleteChess.php";
    public static final String UTF8 = "UTF-8";
    //public static final String LOGIN_PATH = "game-login.php";
    public static final String LOGIN_PATH = "login_2.php";
    public static final String NEW_USER_PATH = "new-user.php";
    public static final String GAME_PATH = "pull2.php";
    public static final String GET_PLAYERS = "get_player.php";
    //public static final String SAVE_CHESS = "saveChess2.php";
    public static final String SAVE_CHESS = "push2.php";
    //public static final String END = "end.php";
    public static final String END = "end2.php";
    public static final String BASE_URL = "https://webdev.cse.msu.edu/~schwagl3/cse476/project2/";

    private GameData gameData = new GameData();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL) // Tells RetroFit where to send the HTTP requests
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    public boolean newUser(String username, String password) {
        ChessService service = retrofit.create(ChessService.class);
        XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {

            xml.setOutput(writer);
            xml.startDocument("UTF-8", true);
            xml.startTag(null, "secretary");
            xml.attribute(null, "username", username);
            xml.attribute(null, "password", password);
            xml.endTag(null, "secretary");
            xml.endDocument();

            Response<LoginResult> response = service.postNewUser(writer.toString()).execute();
            if(!response.isSuccessful()) {
                return false;
            } else {
                LoginResult result = response.body();
                if (result.getStatus().equals("yes")) {
                    return true;
                }
            }
        }
        catch (IOException e) {
            return false;
        }

        return false;

    }

    public LoginResult getPostLogin(String username, String password)
    {
        ChessService service = retrofit.create(ChessService.class);
        XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            xml.setOutput(writer);
            xml.startDocument("UTF-8", true);
            xml.startTag(null, "secretary");

            xml.attribute(null, "username", username);
            xml.attribute(null, "password", password);

            xml.endTag(null, "secretary");

            xml.endDocument();

            Response<LoginResult> response = service.postLogin(writer.toString()).execute();
            if(!response.isSuccessful()) {
                return null;
            } else {
                LoginResult result = response.body();
                if (result.getStatus().equals("yes")) {
                    return result;
                }
            }
        }
        catch (IOException e) {
            return null;
        }

        return null;
    }

    public GameData pullGame(String username, String password)
    {
        ChessService service = retrofit.create(ChessService.class);

        try {
            // Request test = service.pull2("perry", "wilson").request();
            //Log.i("url", test.url().encodedQuery());
            //Log.i("log response", test.body().string());


            Response<GameData> response = service.pull(username, password).execute();
            if(!response.isSuccessful()) {
                //TODO
                return null;
            } else {
                GameData result = response.body();
                return result;

            }
        }
        catch (IOException e) {
            return null;
        }
    }

    public GetPlayersResult getPlayers(String username, String password) {
        ChessService service = retrofit.create(ChessService.class);
        try {
            Response<GetPlayersResult> response = service.getPlayers(username, password).execute();

            if(!response.isSuccessful()) {
                //TODO
                return null;
            } else {
                GetPlayersResult result = response.body();
                return result;
            }
        }
        catch (IOException e) {
            return null;
        }
    }

    public boolean pushGameState(String username, String password, Chess game) {
        ChessService service = retrofit.create(ChessService.class);
        XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            xml.setOutput(writer);
            xml.startDocument("UTF-8", true);
            xml.startTag(null, "secretary");

            xml.attribute(null, "username", username);
            xml.attribute(null, "password", password);

            game.saveXml(xml);

            xml.endTag(null, "secretary");

            xml.endDocument();
            Response<LoginResult> response = service.push(writer.toString()).execute();
            if(!response.isSuccessful()) {
                return false;
            } else {
                LoginResult result = response.body();
                if (result.getStatus().equals("yes")) {
                    return true;
                }
            }
        }
        catch (IOException e) {
            return false;
        }

        return false;

    }

    public boolean end(String username, String password) {
        ChessService service = retrofit.create(ChessService.class);
        try {
            Response<EndGameResult> response = service.surrender(username, password).execute();

            if(!response.isSuccessful()) {
                //TODO
                return false;
            } else {
                EndGameResult result = response.body();
                return result.getStatus().equals("yes");
            }
        }
        catch (IOException e) {
            return false;
        }
    }

    public boolean delete() {
        ChessService service = retrofit.create(ChessService.class);
        try {
            Response<LoginResult> response = service.deleteGame().execute();
            if(!response.isSuccessful()) {
                //TODO
                return false;
            } else {
                LoginResult result = response.body();
                return result.getStatus().equals("yes");
            }
        }
        catch (IOException e) {
            return false;
        }
    }

}

