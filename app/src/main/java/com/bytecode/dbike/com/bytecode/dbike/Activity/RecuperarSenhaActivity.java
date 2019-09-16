package com.bytecode.dbike.com.bytecode.dbike.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bytecode.dbike.R;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private EditText etSenha, etRepetirSenha;
    private Button btCadastarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        etSenha = findViewById(R.id.editTextSenha);
        etRepetirSenha = findViewById(R.id.editTextRepetirSenha);
        btCadastarSenha = findViewById(R.id.buttonCadastrarSenha);
    }


    public void CadastrarNovaSenha(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

}
