package mardero6.msu.chess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mardero6.msu.chess.cloud.Cloud;

public class NewUserActivity extends AppCompatActivity {
    String new_username;
    String new_password;

    EditText new_un;
    EditText new_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
    }

    public void onCreateUser(final View view)
    {
        new_un = findViewById(R.id.newUserText);
        new_pw = findViewById(R.id.newPasswordText);

        new_username = new_un.getText().toString();
        new_password = new_pw.getText().toString();

        final NewUserActivity act = this;
        if (new_username.isEmpty() || new_password.isEmpty())
        {
            Toast.makeText(this, "You must fill out both fields.", Toast.LENGTH_LONG).show();
        }
        else
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Cloud cloud = new Cloud();
                    if(cloud.newUser(new_username, new_password)) {
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(act, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(act,"cannot create account",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            }).start();
        }
    }
}
