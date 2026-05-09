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

public class LoginActivity extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;
    TextView registerTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailET = findViewById(R.id.loginEmailET);
        passwordET = findViewById(R.id.loginPassowordET);
        registerTV = findViewById(R.id.registerTV);

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
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
        //request to the server
    }
}