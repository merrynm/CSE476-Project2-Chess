package mardero6.msu.chess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mardero6.msu.chess.cloud.Cloud;
import mardero6.msu.chess.cloud.Model.LoginResult;

public class LoginActivity extends AppCompatActivity {

    public final static String IS_NEW_GAME_MESSAGE = "IS_NEW_GAME";
    public final static String USERNAME = "USERNAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String REMEMBER_ME = "REMEMBER_ME";

    private SharedPreferences settings = null;
    String username;
    String password;

    EditText un;
    EditText pw;

    private boolean rememberMe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        rememberMe = settings.getBoolean(REMEMBER_ME, false);

        if(rememberMe) {
            username = settings.getString(USERNAME, "");
            password = settings.getString(PASSWORD, "");
            getCheckBox().setChecked(rememberMe);
            getUsernameField().setText(username);
            getUsernameField().invalidate();
            getPasswordField().setText(password);
            getPasswordField().invalidate();
        }
    }

    public void onNewUser(View view)
    {
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLogin(final View view)
    {
        un = findViewById(R.id.usernameText);
        pw = findViewById(R.id.passwordText);

        username = un.getText().toString();
        password = pw.getText().toString();

        final LoginActivity act = this;
        if (username.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "You must fill out both fields.", Toast.LENGTH_LONG).show();
        }
        else
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Cloud cloud = new Cloud();
                    final LoginResult res = cloud.getPostLogin(username, password);

                    if(res != null) {
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(act, DummyActivity.class);
                                intent.putExtra(IS_NEW_GAME_MESSAGE, res.isNewGame());
                                intent.putExtra(USERNAME, username);
                                intent.putExtra(PASSWORD, password);
                                if(getCheckBox().isChecked()) {
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putBoolean(REMEMBER_ME, true);
                                    editor.putString(USERNAME, username);
                                    editor.putString(PASSWORD, password);
                                    editor.apply();
                                } else {
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putBoolean(REMEMBER_ME, false);
                                    editor.putString(USERNAME, "");
                                    editor.putString(PASSWORD, "");
                                    editor.apply();
                                }
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(act,"Invalid username or password",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            }).start();
        }
    }

    public CheckBox getCheckBox() {
        return findViewById(R.id.checkBox);
    }

    public EditText getUsernameField() {
        return findViewById(R.id.usernameText);
    }

    public EditText getPasswordField() {
        return findViewById(R.id.passwordText);
    }
}
