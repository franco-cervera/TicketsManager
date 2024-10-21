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
import com.example.ticketsmanager.controller.Admin.AdminDashboardActivity;
import com.example.ticketsmanager.dao.UsuarioDAO; // Asegúrate de importar UsuarioDAO
import com.example.ticketsmanager.model.Usuario; // Asegúrate de importar Usuario
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView txtUsuario, txtContraseña;
    private TextInputEditText edtID, edtPassword;
    private Button btnLogin;
    private String userType;
    private Button btnCambiarPassword;
    private UsuarioDAO usuarioDAO; // Agrega una variable para UsuarioDAO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Cargar el layout del login.

        usuarioDAO = new UsuarioDAO(this);

        // Recibir el tipo de usuario desde el Intent
        userType = getIntent().getStringExtra("userType");

        if (userType != null && userType.equals("Administrador")) {
            verificarAdministrador();
        }

        inicializarLogin();

        if (userType != null) {
            Toast.makeText(this, "Tipo de usuario: " + userType, Toast.LENGTH_SHORT).show();
        }
    }

    private void verificarAdministrador() {
        Usuario usuario = usuarioDAO.listarPorTipo("Administrador");
        if (usuario == null) {
            // No hay administrador registrado, redirigir a RegistroActivity
            Toast.makeText(this, "No se encontró un Administrador. Registrar..", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            intent.putExtra("solo_admin", true); // Agregar extra para indicar que solo se puede registrar como administrador
            startActivity(intent);
            finish();
        } else {
            // Hay un administrador registrado
            Toast.makeText(this, "Administrador encontrado: " + usuario.getNombreUsuario(), Toast.LENGTH_SHORT).show();
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
        btnCambiarPassword = findViewById(R.id.btnChangePassword);

        btnLogin.setOnClickListener(v -> validarUsuario(edtID, edtPassword));
        btnCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CambiarPassword.class);
                startActivity(intent);
            }
        });
    }

    private void validarUsuario(TextInputEditText edtID, TextInputEditText edtPassword) {
        String idUsuario = edtID.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();


        if (TextUtils.isEmpty(idUsuario)) {
            edtID.setError("Por favor ingresa tu nombre de usuario");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Por favor ingresa tu contraseña");
            return;
        }



        // Validar credenciales usando el DAO
        Usuario usuario = usuarioDAO.validarCredenciales(idUsuario, password, userType);

        if (usuario != null) {
            // Login exitoso, tipo de usuario coincide
            Toast.makeText(this, "Login exitoso como " + userType, Toast.LENGTH_SHORT).show();

            // Redirigir a la actividad correspondiente según el tipo de usuario
            if (userType.equals("Trabajador")) {
                // Redirigir a la actividad del Trabajador
                // Intent intent = new Intent(LoginActivity.this, TrabajadorActivity.class);
            } else if (userType.equals("Tecnico")) {
                // Redirigir a la actividad del Técnico
                // Intent intent = new Intent(LoginActivity.this, TecnicoActivity.class);
            } else if (userType.equals("Administrador")) {
                // Redirigir a AdminDashboardActivity
                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish(); // Cierra LoginActivity
            }
        } else {
            // Si las credenciales o el tipo de usuario son incorrectos
            Toast.makeText(this, "Usuario, contraseña o tipo incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

}
