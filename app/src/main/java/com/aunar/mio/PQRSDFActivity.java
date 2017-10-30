package com.aunar.mio;

import android.content.Intent;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static javax.mail.internet.InternetAddress.*;


public class PQRSDFActivity extends AppCompatActivity {

    private EditText nameEditText, apellidosEditText, emailEditText, identificacionEditText;
    private TextInputLayout inputLayoutName, inputLayoutApellidos, inputLayoutEmail, inputLayoutIdentificacion;

    Spinner spinner_tipodoc, spinner_tippqrsf;
    String [] opdoc= new String[]{"Cédula de Ciudadanía","Pasaporte","Cédula de Estranjería"};
    String [] oppqrsf= new String[] {"Petición","Queja","Reclamo","Solicitud","Felicitación"};
    EditText descripcion, input_email, input_name, input_apellidos, input_identificacion;
    Button enviar;
    String correo, contraseña;
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pqrsdf);

        initializeWidgets();

        spinner_tipodoc = (Spinner) findViewById(R.id.spinner_tipodoc);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_tipo_doc,opdoc);
        spinner_tipodoc.setAdapter(adapter);

        spinner_tippqrsf = (Spinner) findViewById(R.id.spinner_tippqrsf);
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_tipo_pqrsf, oppqrsf);
        spinner_tippqrsf.setAdapter(adapter);

        correo="juliethf276@gmail.com";
        contraseña="98328715lukis";

        descripcion=(EditText) findViewById(R.id.descripcion);
        input_name=(EditText) findViewById(R.id.input_name);
        input_apellidos=(EditText) findViewById(R.id.input_apellidos);
        input_email=(EditText) findViewById(R.id.input_email);
        input_identificacion=(EditText) findViewById(R.id.input_identificacion);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StrictMode.ThreadPolicy policy =new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                Properties properties=new Properties();
                properties.put("mail.smtp.host","smtp.googlemail.com");
                properties.put("mail.smtp.socketFactory.port","465");
                properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.port","465");

                try{
                    session= Session.getDefaultInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correo,contraseña);
                        }
                    });
                    if (session!=null){
                        if(!signUp()) {
                            javax.mail.Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(correo));
                            message.setSubject(spinner_tippqrsf.getSelectedItem().toString());
                            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress("appmio2017@gmail.com"));
                            message.setContent("Nombres: " + input_name.getText().toString().trim() + " <br> "
                                    + "Apellidos: " + input_apellidos.getText().toString().trim() + " <br> "
                                    + "Email: " + input_email.getText().toString().trim() + " <br> "
                                    + "Tipo de documento: "+spinner_tipodoc.getSelectedItem().toString()+"<br>"
                                    + "Identificación: " + input_identificacion.getText().toString().trim() + " <br> "
                                    + "Descripción: " + descripcion.getText().toString().trim(), "text/html; charset=utf-8");
                            Transport.send(message);
                            Toast.makeText(PQRSDFActivity.this, R.string.enviar_success, Toast.LENGTH_SHORT).show();
                            input_name.setText("");
                            input_name.invalidate();
                            input_apellidos.setText("");
                            input_apellidos.invalidate();
                            input_email.setText("");
                            input_email.invalidate();
                            input_identificacion.setText("");
                            input_identificacion.invalidate();
                            descripcion.setText("");
                            descripcion.invalidate();

                        }else{
                            Toast.makeText(PQRSDFActivity.this, "Mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void initializeWidgets() {
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutApellidos = (TextInputLayout) findViewById(R.id.input_layout_apellidos);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutIdentificacion = (TextInputLayout) findViewById(R.id.input_layout_identificacion);

        nameEditText = (EditText) findViewById(R.id.input_name);
        apellidosEditText = (EditText) findViewById(R.id.input_apellidos);
        emailEditText = (EditText) findViewById(R.id.input_email);
        identificacionEditText = (EditText) findViewById(R.id.input_identificacion);

        enviar = (Button) findViewById(R.id.enviar);
    }

    private boolean signUp(){
        boolean isValid = false;
        if(nameEditText.getText().toString().isEmpty()){
            inputLayoutName.setError(getString(R.string.name_validation_msg));
            isValid = true;
        }else{
            inputLayoutName.setErrorEnabled(false);
        }
        if(apellidosEditText.getText().toString().isEmpty()){
            inputLayoutApellidos.setError(getString(R.string.apellidos_validation_msg));
            isValid = true;
        }else{
            inputLayoutApellidos.setErrorEnabled(false);
        }
        String email = emailEditText.getText().toString().trim();
        if(email.isEmpty() || !isValidEmail(email)){
            emailEditText.setError(getString(R.string.email_validation_msg));
            inputLayoutEmail.setError(getString(R.string.arrova_validation_msg));
            isValid = true;
        }else{
            inputLayoutEmail.setErrorEnabled(false);
        }
        if(identificacionEditText.getText().toString().isEmpty()){
            inputLayoutIdentificacion.setError(getString(R.string.identificacion_validation_msg));
            isValid = true;
        }else{
            inputLayoutIdentificacion.setErrorEnabled(false);
        }
        return isValid;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
