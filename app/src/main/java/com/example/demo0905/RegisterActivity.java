package com.example.demo0905;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private TextView tvSignIn;
    private EditText edtYourName, edtEmail, edtPass1, edtPass2;
    private Button butRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mAuth = FirebaseAuth.getInstance();

        edtYourName = findViewById(R.id.txtRName);
        edtEmail = findViewById(R.id.txtREmail);
        edtPass1 = findViewById(R.id.txtRPass);
        edtPass2 = findViewById(R.id.txtRCPass);
        butRegister = findViewById(R.id.btnRegis);




        butRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String email;
        String pass1, pass2, name;
        email = edtEmail.getText().toString();
        pass1 = edtPass1.getText().toString();
        pass2 = edtPass2.getText().toString();
        name = edtYourName.getText().toString();


        if(edtYourName.getText().length() == 0){
            edtYourName.setError("Làm ơn nhập tên của bạn!");
        }else if(edtEmail.getText().length() == 0){
            edtEmail.setError("Vui lòng nhập email của bạn!");
        }else if(edtPass1.getText().length() == 0){
            edtPass1.setError("Vui lòng nhập mật khẩu");
        }else if(edtPass2.getText().length() == 0){
            edtPass2.setError("Nhập lại mật khẩu lần 2");
        }else if(pass1.equals(pass2)){
            mAuth.createUserWithEmailAndPassword(email, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        User user = new User(name, email);
                        FirebaseDatabase.getInstance().getReference("User").
                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thành tài khoản thành công", Toast.LENGTH_SHORT).show();
                                    edtYourName.setText("");
                                    edtEmail.setText("");
                                    Intent intent = new Intent(RegisterActivity.this, Bussines.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(RegisterActivity.this, "Đăng kí thất bại!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(this, "Nhập lại mật khẩu lần 2 khớp với lần 1", Toast.LENGTH_SHORT).show();
        }

    }
}
