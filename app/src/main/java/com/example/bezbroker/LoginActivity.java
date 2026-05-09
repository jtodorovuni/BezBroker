package com.example.bezbroker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;
    TextView registerTV;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.loginEmailET);
        passwordET = findViewById(R.id.loginPassowordET);
        registerTV = findViewById(R.id.registerTV);

        session = new SessionManager(this);

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        if(session.isLoggedIn()){
            autoLogin();
        }
    }

    private void autoLogin() {
        ApiClient.get("/api/me", session.getToken(), new ApiClient.Callback() {
            @Override
            public void onSuccess(JSONObject body) {
                JSONObject user = body.optJSONObject("user");
                String userJson = user.toString();
                session.save(session.getToken(), userJson);
                goToHome();
            }

            @Override
            public void onError(int httpCode, String message) {
                if(httpCode == 401){
                    session.clear();
                }
            }
        });
    }


    public void onLoginClick(View view){
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if(email.length() == 0 || password.length() == 0){
            Toast.makeText(this, R.string.error_message_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject body = new JSONObject();

        try {
            body.put("email" , email);
            body.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        ApiClient.post("/api/login", body, null, new ApiClient.Callback() {
            @Override
            public void onSuccess(JSONObject body) {
                String token = body.optString("token");
                JSONObject user = body.optJSONObject("user");

                if(token == null || user == null){
                    Toast.makeText(LoginActivity.this, R.string.error_login_failed, Toast.LENGTH_SHORT).show();
                    return;
                }

                session.save(token, user.toString());
                goToHome();
            }

            @Override
            public void onError(int httpCode, String message) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message", "Welcome dear user");
        startActivity(intent);
    }

}