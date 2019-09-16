package com.bytecode.dbike.com.bytecode.dbike.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.nio.file.Files;

public class LoginActivity extends AppCompatActivity {
    private EditText etNome, etSenha;
    private Button btEntrar, btRecuperar, btCadastrar;
    private Usuario usuario;
    private FirebaseAuth autentification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNome = findViewById(R.id.editTextNome);
        etSenha = findViewById(R.id.editTextSenha);
        btCadastrar = findViewById(R.id.buttonCadastrar);
        btEntrar = findViewById(R.id.buttonEntrar);


        final String nome = etNome.getText().toString();
        final String senha = etSenha.getText().toString();

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!nome.isEmpty()){
                    if (!senha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setSenha(senha);
                        validarUsuario();

                    }else{
                        Toast.makeText(LoginActivity.this,"preencha a senha", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"preencha a nome", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void validarUsuario() {

        autentification = ConfigFirebase.getFirebaseAutentification();
        autentification.signInWithEmailAndPassword(usuario.getNome(),usuario.getSenha()).
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
                        excessao = "Por favor digite um email valido!";
                    } catch (FirebaseAuthInvalidUserException e) {
                        excessao = "Usuario não cadatrado";
                    } catch (Exception e) {
                        excessao = "Erro ao cadastrar conta" + e.getMessage();
                        e.printStackTrace();
                    }


                    Toast.makeText(LoginActivity.this,"login com erro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void  abrirTelaPrincipal(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
