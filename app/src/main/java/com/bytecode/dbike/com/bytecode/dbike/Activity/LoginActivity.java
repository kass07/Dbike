package com.bytecode.dbike.com.bytecode.dbike.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bytecode.dbike.R;
import com.bytecode.dbike.com.bytecode.dbike.Config.ConfigFirebase;
import com.bytecode.dbike.com.bytecode.dbike.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etSenha;
    private TextView btRecuperar;
    private Button btEntrar, btCadastrar;
    private Usuario usuario;
    private FirebaseAuth autentification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editTextEmail);
        etSenha = findViewById(R.id.editTextSenha);
        btCadastrar = findViewById(R.id.buttonCadastro);
        btEntrar = findViewById(R.id.buttonEntrar);

        btRecuperar = findViewById(R.id.buttonRecuperar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();

                if (!email.isEmpty()){
                    if (!senha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);
                        validarUsuario();

                    }else{
                        Toast.makeText(LoginActivity.this,"preencha a senha", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"preencha o Email", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RecuperarSenhaActivity.class));
            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CadastroActivity.class));
            }
        });

    }



    private void validarUsuario() {

        autentification = ConfigFirebase.getFirebaseAutentification();
        autentification.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                }else{

                    String excessao = "";
                    try {
                        throw task.getException();
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excessao = "Email e senha não correspondem";
                    } catch (FirebaseAuthInvalidUserException e) {
                        excessao = "Usuario não cadatrado";
                    } catch (Exception e) {
                        excessao = "Erro ao realizar login" + e.getMessage();
                        e.printStackTrace();
                    }


                    Toast.makeText(LoginActivity.this,excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void  abrirTelaPrincipal(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
