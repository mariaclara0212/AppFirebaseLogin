package br.com.tech.appfirebaselogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {
    TextInputEditText editTextEmail;
    TextInputEditText editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivities(new Intent[]{intent});
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //REFERENCIOU O QUE ESTÁ NA TELA
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_inserir);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override //AO CLICAR NO TESTEVIEW, IRÁ DIRECIONAR PARA O LOGIN
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //REDIRECIONA PARA LOGIN
                startActivities(new Intent[]{intent});
                finish();
            }
        });

        //AO CLICAR NO BOTÃO
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE); //AO CLICAR IRÁ RODAR A PROGRESS BAR
                String email, password;
                email = String.valueOf(editTextEmail.getText()); //PEGANDO O EMAIL INSERIDO
                password = String.valueOf(editTextPassword.getText()); //PEGANDO A SENHA INSERIDA

                //VERIFICANDO SE OS CAMPOS ESTÃO VAZIOS
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Entre com o Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Entre com a Senha", Toast.LENGTH_LONG).show();
                    return;
                }

                //CRIANDO A CONTA
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override //ESTÁ USANDO O EMAIL E A SENHA
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Conta criada com sucesso.",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(RegisterActivity.this, "Erro ao criar conta.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}