package com.bytecode.dbike.com.bytecode.dbike.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bytecode.dbike.R;

public class InicialActivity extends AppCompatActivity {

    private ProgressBar barraProgesso;
    private Button btEntrar;
    private int progresso = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        barraProgesso = findViewById(R.id.progressBar);
        btEntrar = findViewById(R.id.buttonEntrar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    public void progressoBarra(View view){

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++){

                    final int progresso = i;


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            barraProgesso.setProgress(progresso);

                            if(progresso == 100){
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }
                        }
                    });

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
