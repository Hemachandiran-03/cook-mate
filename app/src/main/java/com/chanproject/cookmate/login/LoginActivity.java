package com.chanproject.cookmate.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chanproject.cookmate.HomeActivity;
import com.chanproject.cookmate.R;
import com.chanproject.cookmate.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    private TextView signupTextBtn;
    private EditText loginEmail,loginPassword;
    private Button loginBtn;
    private ConstraintLayout loading;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        utils = new Utils(this);

        signupTextBtn = findViewById(R.id.signuptxtid);
        loginEmail = findViewById(R.id.emailLoginEdittext);
        loginPassword = findViewById(R.id.passwordLoginEdittext);
        loginBtn = findViewById(R.id.loginBtnID);
        loading = findViewById(R.id.loadingLogin);

        signupTextBtn.setOnClickListener(view -> {

            Intent signupIntent = new Intent(this, SignUpActivity.class);
            startActivity(signupIntent);

        });

        loginBtn.setOnClickListener(view -> {

            utils.hideKeyboard(loginPassword);
            loading.setVisibility(View.VISIBLE);
            loading.bringToFront();

            String email = String.valueOf(loginEmail.getText());
            String password = String.valueOf(loginPassword.getText());

            if(!(email.isEmpty()&&password.isEmpty())){

                utils.getmAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {

                                    String uId = task.getResult().getUser().getUid();

                                    SharedPreferences.Editor editor =  utils.getEditor();

                                    editor.putBoolean(ContextCompat.getString(getApplicationContext(),R.string.IS_LOGGED),true);
                                    editor.putString(ContextCompat.getString(getApplicationContext(),R.string.UID),uId);
                                    editor.apply();

                                    utils.makeToast("User Logged in Successfully");
                                    Log.v("LOGIN","LOGGED IN : UID"+uId);
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.v("LOGIN",task.getException().getMessage().toString());
                                    utils.makeToast(task.getException().getMessage().toString());

                                }

                                loading.setVisibility(View.GONE);

                            }
                        });

            }else {
                utils.makeToast("Invalid inputs!!!");
                loading.setVisibility(View.GONE);
            }

        });



    }
}