package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategorieActivity extends AppCompatActivity {
    private ImageView shirts,sports,female,sweather;
    private ImageView glasses,purse,hat,shoes;
    private ImageView headphone,laptop,watche,mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categorie);

        shirts=(ImageView) findViewById(R.id.t_shirts);
        sports=(ImageView) findViewById(R.id.t_sports);
        female=(ImageView) findViewById(R.id.females);
        sweather=(ImageView) findViewById(R.id.t_sweather);

        glasses=(ImageView) findViewById(R.id.glasses);
        purse=(ImageView) findViewById(R.id.purses);
        hat=(ImageView) findViewById(R.id.hats);
        shoes=(ImageView) findViewById(R.id.shoess);

        headphone=(ImageView) findViewById(R.id.headphones);
        laptop=(ImageView) findViewById(R.id.laptops);
        watche=(ImageView) findViewById(R.id.watches);
        mobiles=(ImageView) findViewById(R.id.mobile);

        shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","shirts");
                startActivity(intent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","sports");
                startActivity(intent);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","female_dresses");
                startActivity(intent);
            }
        });
        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","sweathers");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","glasses");
                startActivity(intent);
            }
        });
        purse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","purses_bags");
                startActivity(intent);
            }
        });
        hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","hats");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","shoess");
                startActivity(intent);
            }
        });
        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","headphones");
                startActivity(intent);
            }
        });
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","laptops");
                startActivity(intent);
            }
        });
        watche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","watches");
                startActivity(intent);
            }
        });
        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,Adminpage.class);
                intent.putExtra("categorie","mobiles_phones");
                startActivity(intent);
            }
        });


    }
}
