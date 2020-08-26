package com.siae.siaecajamarca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class StarterActivity extends AppCompatActivity {
    private final int DURACION_SPLASH = 8000;
    ImageView btnLogin,imgInicio;
    SessionManager sessionManager;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_starter);

        sessionManager= new SessionManager(this);
        imgInicio = (ImageView) findViewById(R.id.imgInicio);
        btnLogin = (ImageView) findViewById(R.id.imgLogin);
        imgInicio.animate().scaleX(2).scaleY(2).setDuration(DURACION_SPLASH).start();

        HashMap<String, String> user=sessionManager.getUserDetail();
        String name=user.get(sessionManager.NOMBRES);
        if(!name.equals("no")){
            Intent a = new Intent(StarterActivity.this,MainActivity.class);
            startActivity(a);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(StarterActivity.this,LoginActivity.class);
                a.putExtra("celular","");

                startActivity(a);

            }
        });
    }


}
