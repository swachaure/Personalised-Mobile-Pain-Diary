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

public class Register extends AppCompatActivity {

    EditText name, email, password, confirmPassword;
    Button signupbtn;
    TextView signintxt;
    FirebaseAuth firebaseAuth;
    //boolean temp = true;
    //FirebaseFirestore fStore;
    //String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        signupbtn = findViewById(R.id.Signupbtn);
        signintxt = findViewById(R.id.signintext);

        firebaseAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();


        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailadd = email.getText().toString().trim();
                final String Name = name.getText().toString();
                String passwrd = password.getText().toString().trim();
                String confPassword = confirmPassword.getText().toString().trim();

                //validating email, name, password and confirm password bellow.
                if (TextUtils.isEmpty((CharSequence) Name)) {
                    name.setError("Name is missing.");
                    return;
//                    Toast.makeText(Register.this, " Name is missing.", Toast.LENGTH_SHORT).show();
//                    temp = false;
                }

                if (TextUtils.isEmpty((CharSequence) emailadd)) {
                    email.setError("email is missing.");
                    return;
//                    Toast.makeText(Register.this, "email is missing", Toast.LENGTH_SHORT).show();
//                    temp = false;
                }

                if (TextUtils.isEmpty((CharSequence) passwrd)) {
                    password.setError("password is missing.");
                    return;
//                    Toast.makeText(Register.this, "Password Can't be empty", Toast.LENGTH_SHORT).show();
//                    temp = false;
                }

                if (passwrd.length() < 8) {
                    password.setError("password must be 8 charctor long.");
                    return;
//                    Toast.makeText(Register.this, "password must be of atleast 8 charactor", Toast.LENGTH_SHORT).show();
//                    temp = false;
                }
                if (!passwrd.equals(confPassword)) {
                    confirmPassword.setError("Password does not match.");
                    return;
//                    Toast.makeText(Register.this, "email does not match", Toast.LENGTH_SHORT).show();
//                    temp = false;

                }



                // register the user in firebase

                firebaseAuth.createUserWithEmailAndPassword(emailadd, passwrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "New User Created.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Register.this, "User already exist or Error Occurred, Try again!!.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),Login.class));
                    }


                });
            }
        });

        signintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
