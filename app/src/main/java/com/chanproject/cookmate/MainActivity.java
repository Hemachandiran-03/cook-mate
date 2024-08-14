package com.chanproject.cookmate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chanproject.cookmate.Utils.Utils;
import com.chanproject.cookmate.login.LoginActivity;
import com.chanproject.cookmate.login.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            return insets;
        });

        Utils utils = new Utils(this);

        Boolean isLogged = utils.getSharedPreferences().getBoolean(ContextCompat.getString(this,R.string.IS_LOGGED),false);
        Intent intent;
        if (isLogged){
            intent = new Intent(this, HomeActivity.class);
        }else {
            intent = new Intent(this, LoginActivity.class);

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },1500);


    }
}