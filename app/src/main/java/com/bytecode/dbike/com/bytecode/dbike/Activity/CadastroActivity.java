package com.bytecode.dbike.com.bytecode.dbike.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etMail, etPhone, etPassword;
    private Button btCadastrar;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth autentificacao = FirebaseAuth.getInstance();
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = findViewById(R.id.editTextNome);
        etMail = findViewById(R.id.editTextMail);
        etPhone = findViewById(R.id.editTextPhone);
        etPassword = findViewById(R.id.editTextPassword);
        btCadastrar = findViewById(R.id.buttonCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = etNome.getText().toString();
                String email = etMail.getText().toString();
                String fone = etPhone.getText().toString();
                String senha = etPassword.getText().toString();


                if (!nome.isEmpty()){
                    if (!email.isEmpty()){
                        if (!fone.isEmpty()){
                            if (!senha.isEmpty()){
                                usuario = new Usuario();
                                usuario.setNome(nome);
                                usuario.setEmail(email);
                                usuario.setPhone(fone);
                                usuario.setSenha(senha);

                            }else{
                                Toast.makeText(CadastroActivity.this,"preencha a senha", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CadastroActivity.this,"preencha o telefone", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroActivity.this,"preencha o Email", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this,"preencha o Nome", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void cadastrarUsuario(View view){
        autentificacao = ConfigFirebase.getFirebaseAutentification();
        autentificacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CadastroActivity.this,"Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        }else{
                            String excessao = "";
                            try {
                                throw task.getException();
                            }catch(FirebaseAuthInvalidCredentialsException e){
                                excessao = "Por favor digite um email valido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excessao = "Email já existente por favor digite outra conta";
                            } catch (Exception e) {
                                excessao = "Erro ao cadastrar conta" + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this,excessao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
