package mardero6.msu.chess.cloud;


import org.simpleframework.xml.Attribute;

import mardero6.msu.chess.cloud.Model.EndGameResult;
import mardero6.msu.chess.cloud.Model.GameData;
import mardero6.msu.chess.cloud.Model.GetPlayersResult;
import mardero6.msu.chess.cloud.Model.Login;
import mardero6.msu.chess.cloud.Model.LoginResult;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static mardero6.msu.chess.cloud.Cloud.GAME_PATH;
import static mardero6.msu.chess.cloud.Cloud.GET_PLAYERS;

public interface ChessService {

    @FormUrlEncoded
    @POST(Cloud.LOGIN_PATH)
    Call<LoginResult> postLogin(@Field("xml") String xmlData );

    @FormUrlEncoded
    @POST(Cloud.NEW_USER_PATH)
    Call<LoginResult> postNewUser( @Field("xml") String xmlData );


    @GET(GAME_PATH)
    Call<GameData> pull (
            @Query("name") String name,
            @Query("password") String password
    );

    @GET(GET_PLAYERS)
    Call<GetPlayersResult> getPlayers (
        @Query("name") String name,
        @Query("password") String password
    );

    @FormUrlEncoded
    @POST(Cloud.SAVE_CHESS)
    Call<LoginResult> push(@Field("xml") String xmlData);

    @GET(Cloud.END)
    Call<EndGameResult> surrender(
            @Query("name") String name,
            @Query("password") String password
    );
    @GET(Cloud.DELETE)
    Call<LoginResult> deleteGame();
}


