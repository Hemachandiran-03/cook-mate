package com.chanproject.cookmate.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {


    private TextView loginTextBtn,passLengthCheckTv,passNumCheckTv;
    private EditText emailEt,passwordEt,usernameET;
    private LinearLayout passwordCheckerLayout;
    private Button signUpBtn;
    private ConstraintLayout loading;
    private boolean validPassword=false;
    private Utils utils;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        utils = new Utils(this);


        loginTextBtn = findViewById(R.id.logintxtid);
        passLengthCheckTv = findViewById(R.id.passLengthCheckTvId);
        passNumCheckTv = findViewById(R.id.passNumCheckTvId);
        emailEt = findViewById(R.id.emailSignupEdittext);
        usernameET = findViewById(R.id.usernameSignupEdittext);
        passwordEt = findViewById(R.id.passwordSignupEdittext);
        passwordCheckerLayout = findViewById(R.id.passwordchecklayout);
        signUpBtn = findViewById(R.id.signupBtnID);
        loading =findViewById(R.id.loadingSignUp);




        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                boolean isNum = false;
                boolean isLength = false;

                if(charSequence.length()>0){

                    passwordCheckerLayout.setVisibility(View.VISIBLE);

                    String s = charSequence.toString();

                    for(char c : s.toCharArray()){
                        if(Character.isDigit(c)){
                            isNum=true;
                        }
                    }

                    isLength = charSequence.length()>=6;

                    if(isLength){
                        passLengthCheckTv.setTextColor(ContextCompat.getColor(passLengthCheckTv.getContext(),R.color.black));
                        passLengthCheckTv.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(passLengthCheckTv.getContext(), R.color.green), PorterDuff.Mode.SRC_IN));
                    }else{
                        passLengthCheckTv.setTextColor(ContextCompat.getColor(passLengthCheckTv.getContext(),R.color.grey));
                        passLengthCheckTv.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(passLengthCheckTv.getContext(), R.color.grey), PorterDuff.Mode.SRC_IN));
                    }

                    if(isNum){
                        passNumCheckTv.setTextColor(ContextCompat.getColor(passLengthCheckTv.getContext(),R.color.black));
                        passNumCheckTv.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(passNumCheckTv.getContext(), R.color.green), PorterDuff.Mode.SRC_IN));
                    }else{
                        passNumCheckTv.setTextColor(ContextCompat.getColor(passLengthCheckTv.getContext(),R.color.grey));
                        passNumCheckTv.getCompoundDrawables()[0].setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(passNumCheckTv.getContext(), R.color.grey), PorterDuff.Mode.SRC_IN));
                    }



                }else{
                    passwordCheckerLayout.setVisibility(View.INVISIBLE);
                }

                validPassword = isLength && isNum;

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        signUpBtn.setOnClickListener(view-> {
            if(validPassword && !TextUtils.isEmpty(emailEt.getText())&& !TextUtils.isEmpty(usernameET.getText())){

                utils.hideKeyboard(passwordEt);
                loading.setVisibility(View.VISIBLE);
                loading.bringToFront();

                String email = String.valueOf(emailEt.getText());
                String password = String.valueOf(passwordEt.getText());
                String username = String.valueOf(usernameET.getText());

                utils.getmAuth().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {

                                    String uId = task.getResult().getUser().getUid();



                                    utils.getDatabaseReference().child(uId).child("username").setValue(username);
                                    utils.getDatabaseReference().child(uId).child("email").setValue(email);



                                    SharedPreferences.Editor editor =  utils.getEditor();

                                    editor.putBoolean(ContextCompat.getString(getApplicationContext(),R.string.IS_LOGGED),true);
                                    editor.putString(ContextCompat.getString(getApplicationContext(),R.string.UID),uId);
                                    editor.apply();

                                    utils.makeToast("User Profile Created Successfully");
                                    Log.v("SIGNUP","CREATED : UID"+uId);
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.v("SIGNUP",task.getException().getMessage().toString());
                                    utils.makeToast(task.getException().getMessage().toString());

                                }

                                loading.setVisibility(View.GONE);

                            }
                        });
            }
        });

        loginTextBtn.setOnClickListener(view->{
            onBackPressed();
        });


    }
}