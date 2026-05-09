package com.example.bezbroker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText fNameET;
    EditText sNameET;
    EditText emailET;
    EditText passwordET;
    EditText repeatPasswordET;
    EditText phoneET;
    CheckBox sellerCB;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        fNameET = findViewById(R.id.registerFNameET);
        sNameET = findViewById(R.id.registerSNameET);
        emailET = findViewById(R.id.registerEmailET);
        passwordET = findViewById(R.id.registerPasswordET);
        repeatPasswordET = findViewById(R.id.registerRepeatPasswordET);
        phoneET = findViewById(R.id.registerPhoneET);
        sellerCB = findViewById(R.id.registerCB);
        session = new SessionManager(this);
    }

    public void onCancelClick(View view){
        finish();
    }

    public void onRegisterClick(View view){

        if(emailET.getText().length() == 0 || passwordET.getText().length() == 0){
            Toast.makeText(this, R.string.error_message_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }else if(!passwordET.getText().toString().equals(repeatPasswordET.getText().toString())){
            Toast.makeText(this, R.string.error_password_missmatch, Toast.LENGTH_SHORT).show();
            return;
        }

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String firstName = fNameET.getText().toString();
        String lastName = sNameET.getText().toString();
        String phone = phoneET.getText().toString();
        boolean isSeller = sellerCB.isChecked();

        JSONObject body = new JSONObject();

        try {
            body.put("email", email);
            body.put("password", password);
            body.put("firstName", firstName);
            body.put("lastName", lastName);
            body.put("phone", phone);
            body.put("isSeller", isSeller);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        ApiClient.post("/api/register", body, null, new ApiClient.Callback() {
            @Override
            public void onSuccess(JSONObject body) {
                String token = body.optString("token");
                JSONObject user = body.optJSONObject("user");

                if(token == null || user == null){
                    Toast.makeText(RegisterActivity.this, "Something went kapalda", Toast.LENGTH_SHORT).show();
                    return;
                }

                session.save(token, user.toString());

                finish();
            }

            @Override
            public void onError(int httpCode, String message) {
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });


    }
}