package com.aunar.mio.login;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yankarloriosmartinez on 22/10/17.
 */
@IgnoreExtraProperties
public class User {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String password;
    private String email;
    private String identificacion;
    private String nombre;
    private String direccion;
    private String numeroTarjeta;
    private String tipoId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email,String password,String identificacion, String nombre, String direccion, String numeroTarjeta, String tipoId) {
        this.password = password;
        this.email = email;
        this.tipoId = tipoId;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numeroTarjeta = numeroTarjeta;
    }

    public void writeNewUser(String userId) {
        Map user = toMap(password,email,tipoId,identificacion,nombre,direccion,numeroTarjeta);
        database.child("user").child(userId).setValue(user);


    }

    @Exclude
    public Map<String, Object> toMap(String password, String email, String tipoId, String identificacion, String nombre, String direccion, String numeroTarjeta) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("password", password);
        result.put("tipoId", tipoId);
        result.put("identificacion", identificacion);
        result.put("nombre", nombre);
        result.put("direccion", direccion);
        result.put("numeroTarjeta", numeroTarjeta);

        return result;
    }




}



