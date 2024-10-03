package com.example.ticketsmanager.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketsmanager.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView txtUsuario, txtContraseña;
    private TextInputEditText edtID, edtPassword;
    private Button btnLogin;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Cargar el layout del login.

        // Recibir el tipo de usuario desde el Intent
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");

        // Inicializar los elementos del login
        inicializarLogin();

        // Muestra el tipo de usuario en el login (opcional)
        if (userType != null) {
            Toast.makeText(this, "Tipo de usuario: " + userType, Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarLogin() {
        logo = findViewById(R.id.imageView);
        txtUsuario = findViewById(R.id.textView_usuario);
        txtContraseña = findViewById(R.id.textView_password);

        TextInputLayout inputLayoutID = findViewById(R.id.edtID);
        TextInputLayout inputLayoutPassword = findViewById(R.id.edtPassword);

        edtID = (TextInputEditText) inputLayoutID.getEditText(); // Obtiene el TextInputEditText del TextInputLayout
        edtPassword = (TextInputEditText) inputLayoutPassword.getEditText(); // Obtiene el TextInputEditText del TextInputLayout

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> validarUsuario(edtID, edtPassword));
    }

    private void validarUsuario(TextInputEditText edtID, TextInputEditText edtPassword) {
        String id = edtID.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(id)) {
            edtID.setError("Por favor ingresa tu ID");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Por favor ingresa tu contraseña");
            return;
        }

        // Validaciones específicas basadas en el tipo de usuario (opcional)
        if (userType != null) {
            if (userType.equals("Trabajador")) {
                // Aquí puedes añadir lógica específica para Trabajadores
                if (id.equals("trabajador") && password.equals("trabajador")) {
                    Toast.makeText(this, "Login exitoso como Trabajador", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "ID o contraseña incorrectos para Trabajador", Toast.LENGTH_SHORT).show();
                }
            } else if (userType.equals("Tecnico")) {
                // Aquí puedes añadir lógica específica para Técnicos
                if (id.equals("tecnico") && password.equals("tecnico")) {
                    Toast.makeText(this, "Login exitoso como Técnico", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "ID o contraseña incorrectos para Técnico", Toast.LENGTH_SHORT).show();
                }
            } else if (userType.equals("Administrador")) {
                // Aquí puedes añadir lógica específica para Administradores
                if (id.equals("admin") && password.equals("admin")) {
                    Toast.makeText(this, "Login exitoso como Administrador", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "ID o contraseña incorrectos para Administrador", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
