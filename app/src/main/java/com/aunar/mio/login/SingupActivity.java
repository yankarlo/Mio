package com.aunar.mio.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.aunar.mio.MainActivity;
import com.aunar.mio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputIdentificacion, inputNombre, inputDireccion, inputNumeroTarjeta;     //hit option + enter if you on mac , for windows hit ctrl + enter
    private Button btnRegister;
    private Spinner tipoIdentificacion;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnRegister = (Button) findViewById(R.id.register_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputIdentificacion = (EditText) findViewById(R.id.identificacion);
        inputNombre = (EditText) findViewById(R.id.nombre);
        inputDireccion = (EditText) findViewById(R.id.direccion);
        inputNumeroTarjeta = (EditText) findViewById(R.id.numero_tarjeta);
        tipoIdentificacion = (Spinner) findViewById(R.id.tipo_documento);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        Spinner spinner = (Spinner) findViewById(R.id.tipo_documento);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_documentos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /*btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingupActivity.this, ResetPasswordActivity.class));
            }
        });*/

        /*btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String identificacion = inputIdentificacion.getText().toString().trim();
                final String nombre = inputNombre.getText().toString().trim();
                final String direccion = inputDireccion.getText().toString().trim();
                final String numeroTarjeta = inputNumeroTarjeta.getText().toString().trim();
                final String tipoId = tipoIdentificacion.getSelectedItem().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SingupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SingupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SingupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = user.getUid();
                                    User data = new User(email, password, identificacion, nombre, direccion, numeroTarjeta, tipoId);
                                    data.writeNewUser(uid);
                                    startActivity(new Intent(SingupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }
}
