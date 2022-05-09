package com.example.demo0905;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bussines extends AppCompatActivity {
    EditText editThu, editChi;
    Button butPush;
    Button butGet, butDelete, butUp;
    TextView tvView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bussines_activity);

        editThu = findViewById(R.id.txtThuNhap);
        editChi = findViewById(R.id.txtChiTieu);
        butPush = findViewById(R.id.btnPush);
        butGet = findViewById(R.id.btnGET);
        butDelete = findViewById(R.id.btnDelete);
        butUp = findViewById(R.id.btnUpdate);

        butPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPush();
            }
        });

        butGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getDataBase();
            }
        });
    }
    private void onClickPush() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue(editThu.getText().toString().trim());
    }
    private void onClickGet() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue(editChi.getText().toString().trim());
    }
    private  void getDataBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                tvView.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
