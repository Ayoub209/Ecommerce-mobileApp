package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button create;
    EditText inputName,inputPhone,inputPassword;
    private ProgressDialog loding;
    FirebaseAuth nFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        create=(Button) findViewById(R.id.register_btn);
        inputName=(EditText) findViewById(R.id.user_name);
        inputPhone=(EditText) findViewById(R.id.register_phone_number);
        inputPassword=(EditText) findViewById(R.id.password_register);
        loding=new ProgressDialog(this);
        nFirebaseAuth=FirebaseAuth.getInstance();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();

            }

        });


    }

    private void createAccount() {
        String name=inputName.getText().toString();
        String phone=inputPhone.getText().toString();
        String pass=inputPassword.getText().toString();
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Write your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Please Write your phone", Toast.LENGTH_SHORT).show();
            }
        else if (TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Please Write your password", Toast.LENGTH_SHORT).show();
            }

        else if (!(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(pass))){
            loding.setTitle("Create Account");
            loding.setMessage("Please Wait, while we are checking");
            loding.setCanceledOnTouchOutside(false);
            loding.show();

            validatePhone(name,phone,pass);


        }

    }

    private void validatePhone(final String name, final String phone, final String pass) {
        final DatabaseReference root;
        root= FirebaseDatabase.getInstance().getReference();
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("users").child(phone).exists())){

                    HashMap<String,Object> userdata=new HashMap<>();
                    userdata.put("phone",phone);
                    userdata.put("name",name);
                    userdata.put("password",pass);
                    root.child("users").child(phone).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loding.dismiss();
                                Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);

                            }
                            else{
                                loding.dismiss();
                                Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    loding.dismiss();
                    Toast.makeText(RegisterActivity.this, ""+phone+":Already exist,Please Try again using another phone", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

}
