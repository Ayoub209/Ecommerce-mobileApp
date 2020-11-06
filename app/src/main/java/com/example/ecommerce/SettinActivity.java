package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettinActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private EditText fullName,userPhone,address;
    private TextView profilechange,closeBtn,saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settin);

        profileImage=(CircleImageView) findViewById(R.id.profil_image);
        fullName=(EditText) findViewById(R.id.settings_fullName);
        userPhone=(EditText) findViewById(R.id.settings_phone);
        address=(EditText) findViewById(R.id.settings_address);
        profilechange=(TextView) findViewById(R.id.change_image);
        closeBtn=(TextView) findViewById(R.id.close);
        saveBtn=(TextView) findViewById(R.id.update);


    }
}
