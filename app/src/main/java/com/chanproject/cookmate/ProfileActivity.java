package com.chanproject.cookmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chanproject.cookmate.Utils.Utils;
import com.chanproject.cookmate.login.LoginActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {


    private String avatarUrl,username,bio,email,contact,uId;

    private boolean isChangesMade=false;
    private Uri imageUri;
    private Utils utils;


    private EditText usernameEt,bioEt,emailEt,contactEt;
    private Button saveChangesBtn,logoutBtn;
    private ImageView avatarImgView;
    private CardView editAvatarBtn;
    private ConstraintLayout loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        utils = new Utils(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        usernameEt=findViewById(R.id.profileUsername);
        bioEt=findViewById(R.id.profileBio);
        emailEt=findViewById(R.id.profileEmail);
        contactEt=findViewById(R.id.profilePhone);
        saveChangesBtn=findViewById(R.id.profileSaveBtn);
        logoutBtn=findViewById(R.id.profileLogoutBtn);
        avatarImgView=findViewById(R.id.profileImg);
        editAvatarBtn=findViewById(R.id.profileImgEditBtn);
        loading=findViewById(R.id.loadingProfile);


        uId = utils.getSharedPreferences().getString(ContextCompat.getString(this,R.string.UID),"");

        Log.v("PROFILE",uId);

        if(!uId.isEmpty()){

            utils.getDatabaseReference().child(uId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()){

                        if(snapshot.child("username").exists()){
                            username = snapshot.child("username").getValue().toString();
                            usernameEt.setText(username);
                        }
                        if(snapshot.child("bio").exists()){
                            bio = snapshot.child("bio").getValue().toString();
                            bioEt.setText(bio);
                        }
                        if(snapshot.child("email").exists()){
                            email = snapshot.child("email").getValue().toString();
                            emailEt.setText(email);
                        }
                        if(snapshot.child("contact").exists()){
                            contact = snapshot.child("contact").getValue().toString();
                            contactEt.setText(contact);
                        }
                        if(snapshot.child("avatar").exists()){
                            avatarUrl = snapshot.child("avatar").getValue().toString();
                            Picasso.get().load(avatarUrl).fit().centerCrop().into(avatarImgView);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        saveChangesBtn.setOnClickListener(view -> {

            loading.setVisibility(View.VISIBLE);
            loading.bringToFront();

            username = usernameEt.getText().toString();
            bio = bioEt.getText().toString();
            email = emailEt.getText().toString();
            contact = contactEt.getText().toString();

            DatabaseReference mRef = utils.getDatabaseReference().child(uId);

            mRef.child("username").setValue(username);
            mRef.child("bio").setValue(bio);
            mRef.child("contact").setValue(contact);
            mRef.child("email").setValue(email);
            mRef.child("avatar").setValue(avatarUrl);

            loading.setVisibility(View.GONE);
            utils.makeToast("Details Updated Successfully");

        });

        logoutBtn.setOnClickListener(view -> {
            SharedPreferences.Editor editor =  utils.getEditor();

            editor.clear();
            editor.apply();

            utils.makeToast("User Profile Logged out Successfully");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        editAvatarBtn.setOnClickListener(view -> {

            choosePicture();

        });

    }


    private void choosePicture(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    private void uploadFile(){


        loading.setVisibility(View.VISIBLE);
        loading.bringToFront();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference myRef = utils.getStorageReference().child(randomKey);

         myRef.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
             @Override
             public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                 if (!task.isSuccessful()) {
                     throw task.getException();
                 }

                 // Continue with the task to get the download URL
                 return myRef.getDownloadUrl();
             }
         }).addOnCompleteListener(new OnCompleteListener<Uri>() {
             @Override
             public void onComplete(@NonNull Task<Uri> task) {
                 if (task.isSuccessful()) {

                     Uri downloadUri = task.getResult();
                     Log.v("PROFILE","IMAGE URL : "+downloadUri.toString());

                     avatarUrl = downloadUri.toString();
                     if(!avatarUrl.isEmpty()){
                        Picasso.get().load(avatarUrl).fit().centerCrop().into(avatarImgView);
                     }

                     loading.setVisibility(View.GONE);

                 } else {
                     // Handle failures
                     // ...
                 }
             }
         });

//        myRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Log.v("PROFILE","IMAGE URL : "+taskSnapshot.getMetadata().getReference().getDownloadUrl());
//                    }
//                });
//
////                if(!avatarUrl.isEmpty()){
////                    Picasso.get().load(avatarUrl).fit().into(avatarImgView);
////                }
//                loading.setVisibility(View.GONE);
//
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            imageUri = data.getData();
            if(imageUri!=null){
                uploadFile();
            }
        }

    }
}