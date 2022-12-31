package com.example.tugas_akhir.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tugas_akhir.R;
import com.example.tugas_akhir.User.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText edtRegEmail, edtRegPassword, edtRegUsername;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtRegUsername = findViewById(R.id.edtRegUser);
        edtRegEmail = findViewById(R.id.edRegEmail);
        edtRegPassword = findViewById(R.id.edRegPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void Register(View v) {
        String email, nama, password;
        email = edtRegEmail.getText().toString();
        nama = edtRegUsername.getText().toString();
        password = edtRegPassword.getText().toString();

        if(nama.length() < 3) {
            Toast.makeText(RegisterActivity.this, "Username harus lebih dari 3", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Harap cek kembali atau ganti Email dan Password", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
        }
    }
}