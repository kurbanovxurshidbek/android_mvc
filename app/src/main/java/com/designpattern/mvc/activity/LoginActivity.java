package com.designpattern.mvc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.designpattern.mvc.R;
import com.designpattern.mvc.model.Result;
import com.designpattern.mvc.network.AsyncHttp;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private Button b_login;
    private TextView tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    /*
     * Methods
     **/

    private void initViews() {
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        b_login = findViewById(R.id.b_login);
        tv_error = findViewById(R.id.tv_error);
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                apiUserLogin(email, password);
            }
        });
    }

    private void callMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Rest Api
     */

    private void apiUserLogin(final String email, final String password) {

        AsyncHttp.get(AsyncHttp.API_USER_LOGIN, AsyncHttp.paramsUserLogin(email, password), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("@@@","responseString "+responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("@@@","responseString "+responseString);
                Result result = new Gson().fromJson(responseString, Result.class);
                if (result != null) {
                    if (result.getStatus().equals("0")) {
                        callMainActivity();
                    } else {
                        tv_error.setText(result.getMessage());
                    }
                }
            }
        });
    }

}
