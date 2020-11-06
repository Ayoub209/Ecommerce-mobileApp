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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import Model.Utilisateur;
import Prevalent.Prevalent;
import io.paperdb.Book;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    EditText phone,pass;
    Button log;
    private ProgressDialog loding;
    private CheckBox chkb;
    String dbName="users";
    TextView admin,notadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phone=(EditText) findViewById(R.id.phone_number);
        pass=(EditText) findViewById(R.id.password_login);
        log=(Button) findViewById(R.id.login_btn);
        loding=new ProgressDialog(this);
        chkb=(CheckBox) findViewById(R.id.remember);
        admin=(TextView) findViewById(R.id.admin_link);
        notadmin=(TextView) findViewById(R.id.not_admin_link);
        Paper.init(this);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAccount();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              log.setText("Login Admin");
              admin.setVisibility(View.INVISIBLE);
              notadmin.setVisibility(View.VISIBLE);
              dbName="Admins";
            }
        });
        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.setText("Login");
                admin.setVisibility(View.VISIBLE);
                notadmin.setVisibility(View.INVISIBLE);
                dbName="users";
            }
        });
    }

    private void loginAccount() {
        String phon=phone.getText().toString();
        String password=pass.getText().toString();

        if(TextUtils.isEmpty((phon))){
            Toast.makeText(this, "Please enter your phone", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else if(!((TextUtils.isEmpty(phon)) || (TextUtils.isEmpty(password)))){
            loding.setTitle("Login Account");
            loding.setMessage("Please Wait, while we are checking");
            loding.setCanceledOnTouchOutside(false);
            loding.show();
            Accessuser(phon,password);

        }

    }

    private void Accessuser(final String phon, final String password) {

        if(chkb.isChecked()){
            Paper.book().write(Prevalent.phonekey,phon);
            Paper.book().write(Prevalent.passwordkey,password);
        }

        final DatabaseReference root;
        root= FirebaseDatabase.getInstance().getReference();

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(dbName).child(phon).exists()){
                    Utilisateur user=dataSnapshot.child(dbName).child(phon).getValue(Utilisateur.class);
                    if(user.getPhone().equals(phon)){
                        if(user.getPassword().equals(password)){
                            if(dbName.equals("Admins")){
                                Toast.makeText(LoginActivity.this, "Welcome Admin,You have been connect successfully", Toast.LENGTH_SHORT).show();
                                loding.dismiss();
                                Intent intent=new Intent(LoginActivity.this,AdminCategorieActivity.class);
                                startActivity(intent);
                            }
                            else if(dbName.equals("users")){
                                Toast.makeText(LoginActivity.this, "You have been connect successfully", Toast.LENGTH_SHORT).show();
                                loding.dismiss();
                                Intent intent=new Intent(LoginActivity.this,Homepage.class);
                                Prevalent.currentUser=user;
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "The password is incorrect,Please try again", Toast.LENGTH_SHORT).show();
                            loding.dismiss();
                        }
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, "Account whit this phone:"+phon+" does not exist", Toast.LENGTH_SHORT).show();
                    loding.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
