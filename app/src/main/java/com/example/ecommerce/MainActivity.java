package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Utilisateur;
import Prevalent.Prevalent;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button join,login;
    private ProgressDialog loding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        join=(Button) findViewById(R.id.main_join_btn);
        login=(Button) findViewById(R.id.main_login_btn);
        Paper.init(this);
        loding=new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        String userPhone=Paper.book().read(Prevalent.phonekey);
        String userPassword=Paper.book().read(Prevalent.passwordkey);

        if(userPhone!="" && userPassword!=""){
            if(!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPassword)){
                loding.setTitle("Already logged");
                loding.setMessage("Please Wait, while we are checking");
                loding.setCanceledOnTouchOutside(false);
                loding.show();
                Allowaccess(userPhone,userPassword);
            }
        }


    }

    private void Allowaccess(final String phon,final String password) {
        final DatabaseReference root;
        root= FirebaseDatabase.getInstance().getReference();

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("users").child(phon).exists()){
                    Utilisateur user=dataSnapshot.child("users").child(phon).getValue(Utilisateur.class);
                    if(user.getPhone().equals(phon)){
                        if(user.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this, "Please wait,you are already logged", Toast.LENGTH_SHORT).show();
                            loding.dismiss();
                            Intent intent=new Intent(MainActivity.this,Homepage.class);
                            Prevalent.currentUser=user;
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "The password is incorrect,Please try again", Toast.LENGTH_SHORT).show();
                            loding.dismiss();
                        }
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "Account whit this phone:"+phon+" does not exist", Toast.LENGTH_SHORT).show();
                    loding.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
