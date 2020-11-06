package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Adminpage extends AppCompatActivity {
      private String categorieName,description,price,name,saveDate,saveTime;
      private Button btn_product;
      private EditText proName,prodes,propri;
      private ImageView impro;
      private static final int GalleryPick=1;
      private Uri imageUri;
      private String productKey,downloadUrl;
      private StorageReference productRef;
      private DatabaseReference proRef;
      private ProgressDialog loding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);

        categorieName=getIntent().getExtras().get("categorie").toString();
        btn_product=(Button) findViewById(R.id.add_product);
        proName=(EditText) findViewById(R.id.product_name) ;
        prodes=(EditText) findViewById(R.id.product_description);
        propri=(EditText) findViewById(R.id.product_price);
        impro=(ImageView) findViewById(R.id.product_image);
        productRef= FirebaseStorage.getInstance().getReference().child("product images");
        proRef= FirebaseDatabase.getInstance().getReference().child("products");
        loding=new ProgressDialog(this);

        impro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
       btn_product.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               validateProduct();
           }
       });

    }


    private void openGallery() {
        Intent gallery=new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            impro.setImageURI(imageUri);
        }
    }
    private void validateProduct() {
        description=prodes.getText().toString();
        price=propri.getText().toString();
        name=proName.getText().toString();

        if(imageUri==null){
            Toast.makeText(this, "Product Image is required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter the name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(this, "Please enter the description", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(price)){
            Toast.makeText(this, "Please enter the price", Toast.LENGTH_SHORT).show();
        }
        else{
            storeProduct();
        }

    }

    private void storeProduct() {
        loding.setTitle("Add new product");
        loding.setMessage("Please wait,while we are adding the new product");
        loding.setCanceledOnTouchOutside(false);
        loding.show();

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat date=new SimpleDateFormat("MMM dd,yyyy");
        saveDate=date.format(calendar.getTime());
        SimpleDateFormat time=new SimpleDateFormat("HH:mm:ss a");
        saveTime=time.format(calendar.getTime());

        productKey=saveDate+saveTime;
        final StorageReference file=productRef.child(imageUri.getLastPathSegment()+productKey);
        final UploadTask uploadTask=file.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(Adminpage.this, "Error"+message, Toast.LENGTH_SHORT).show();
                loding.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Adminpage.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadUrl=file.getDownloadUrl().toString();
                        return file.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadUrl=task.getResult().toString();
                            Toast.makeText(Adminpage.this, "Got the product image Url successfully", Toast.LENGTH_SHORT).show();
                            saveProduct();
                        }
                    }
                });
            }
        });
    }

    private void saveProduct() {
        HashMap<String,Object> prod=new HashMap<>();
        prod.put("pid",productKey);
        prod.put("date",saveDate);
        prod.put("time",saveTime);
        prod.put("name",name);
        prod.put("description",description);
        prod.put("price",price);
        prod.put("image",downloadUrl);
        prod.put("categorie",categorieName);
        proRef.child(productKey).updateChildren(prod).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(Adminpage.this,AdminCategorieActivity.class);
                    startActivity(intent);

                    loding.dismiss();
                    Toast.makeText(Adminpage.this, "The product is added successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    loding.dismiss();
                    String message=task.getException().toString();
                    Toast.makeText(Adminpage.this, "Error:"+message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
