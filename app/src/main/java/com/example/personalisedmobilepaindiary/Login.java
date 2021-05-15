package com.example.personalisedmobilepaindiary;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
public class Login extends AppCompatActivity {

    EditText email,password;
    Button SignInbtn;
    TextView Signuptext;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignInbtn = findViewById(R.id.SignInbtn);
        Signuptext = findViewById(R.id.Signuptext);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        firebaseAuth = FirebaseAuth.getInstance();

        SignInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailadd = email.getText().toString().trim();
                String passwrd = password.getText().toString().trim();

                if (TextUtils.isEmpty((CharSequence) emailadd)) {
                    email.setError("email is missing.");
                    return;
                }

                if (TextUtils.isEmpty((CharSequence) passwrd)) {
                    password.setError("password is missing.");
                    return;
                }

                if (passwrd.length() < 8) {
                    password.setError("password must be 8 charctor long.");
                    return;
                }

                // authenticate the user

                firebaseAuth.signInWithEmailAndPassword(emailadd,passwrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

        Signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}