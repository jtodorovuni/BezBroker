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

public class RegisterActivity extends AppCompatActivity {

    EditText fNameET;
    EditText sNameET;
    EditText emailET;
    EditText passwordET;
    EditText repeatPasswordET;
    EditText phoneET;
    CheckBox sellerCB;

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

        // send data to the server and see the response

    }
}