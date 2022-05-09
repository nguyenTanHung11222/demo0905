package com.example.demo0905;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText ttEmail,ttPassword ;
    private Button butLogin;
    private FirebaseAuth mAuth;



   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);

       mAuth = FirebaseAuth.getInstance();

       ttEmail = findViewById(R.id.txtEmail);
       ttPassword = findViewById(R.id.txtPassword);
       butLogin = findViewById(R.id.btnLogin);


       butLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               login();
           }
       });
   }


    private void login() {
        String email, password;
        email = ttEmail.getText().toString();
        password = ttPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Vui lòng nhập email !!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Vui lòng nhập password!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");

                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(LoginActivity.this, Bussines.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


