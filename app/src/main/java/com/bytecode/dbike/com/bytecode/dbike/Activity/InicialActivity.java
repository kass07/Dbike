package com.bytecode.dbike.com.bytecode.dbike.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.bytecode.dbike.R;

public class InicialActivity extends AppCompatActivity {

    private ProgressBar barraProgesso;
    private int progresso = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        barraProgesso = findViewById(R.id.progressBar);
        progressoBarra(barraProgesso);
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
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
