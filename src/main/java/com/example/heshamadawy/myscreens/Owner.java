package com.example.heshamadawy.myscreens;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Owner extends AppCompatActivity {
    private Uri uri1;
private Button selectImage;
  private StorageReference storageReference;
   private static final int GALARY_INTENT=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner);
storageReference= FirebaseStorage.getInstance().getReference();
        selectImage=(Button) findViewById(R.id.upload);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALARY_INTENT);

            }
        });
        final ImageView imageView=(ImageView)findViewById(R.id.img1);
        StorageReference pathReference = storageReference.child("flats").child("h");

        // Load the image using Glide
//        Log.e("hhhhhhhhh", String.valueOf(pathReference));
//        Glide
//                        .with(this)
//                        .load(pathReference.toString())
//                        .centerCrop()
//                        .crossFade()
//                        .into(imageView);
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide
                        .with(getApplicationContext())
                        .load(uri)
                        .centerCrop()
                        .crossFade()
                        .into(imageView);
}
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== GALARY_INTENT && resultCode==RESULT_OK ){
            final Uri uri =data.getData();
        StorageReference filepath =storageReference.child("flats").child("h");
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
