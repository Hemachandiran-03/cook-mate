package com.chanproject.cookmate.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.chanproject.cookmate.ClassObjects.RecipeClass;
import com.chanproject.cookmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Utils {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public Utils(Context context) {
        this.context = context;
        storageReference = FirebaseStorage.getInstance().getReference().child("images");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        sharedPreferences = context.getSharedPreferences(ContextCompat.getString(context, R.string.MY_SHARED_PREFERNCE),Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
    public StorageReference getStorageReference() {
        return storageReference;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        editor = sharedPreferences.edit();
        return editor;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    Context context;

    public void makeToast(String message){
        Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
