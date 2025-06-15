package com.s23010255.intergratedapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editUsername, editPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDb = new DatabaseHelper(this);

        editUsername = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = myDb.insertData(username, password);

                    if (isInserted) {
                        Toast.makeText(LoginActivity.this, "Login successful and data saved!", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                        editPassword.setText("");

                        // Navigate to MapActivity only if login is successful
                        startActivity(new Intent(LoginActivity.this, MapActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}




