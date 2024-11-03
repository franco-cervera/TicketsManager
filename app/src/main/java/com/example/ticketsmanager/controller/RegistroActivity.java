package com.example.ticketsmanager.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter; // Importa el ArrayAdapter
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.dao.UsuarioDAO;
import com.example.ticketsmanager.model.Usuario;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText edtUsername;
    private Spinner spinner;
    private UsuarioDAO usuarioDAO;
    private boolean soloAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        TextInputLayout layoutUsername = findViewById(R.id.edtID_registro);

        edtUsername = (TextInputEditText) layoutUsername.getEditText();

        spinner = findViewById(R.id.spinner);
        Button btnRegister = findViewById(R.id.btnRegistro_registro);

        usuarioDAO = new UsuarioDAO(this);

        // Verificar si se permite solo registrar un administrador
        soloAdmin = getIntent().getBooleanExtra("solo_admin", false);

        setupSpinner();

        btnRegister.setOnClickListener(this::registerUser);
    }

    private void setupSpinner() {
        if (soloAdmin) {
            // Si solo se permite registrar como administrador, configurar el spinner
            String[] tiposUsuario = {"Administrador"}; // Solo una opción
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposUsuario);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setEnabled(false); // Deshabilitar el spinner
        } else {
            // Obtener los elementos del arreglo de strings desde resources
            String[] tiposUsuario = getResources().getStringArray(R.array.roles_array);

            // Crear un adaptador para el Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, tiposUsuario);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Establecer el layout para la lista desplegable

            // Asignar el adaptador al Spinner
            spinner.setAdapter(adapter);

        }
    }

    private void registerUser(View view) {
        String username = edtUsername.getText().toString().trim();
        String tipo = soloAdmin ? "Administrador" : spinner.getSelectedItem().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario newUser = new Usuario(username, "", tipo);

        // Verificar si el usuario ya existe
        if (usuarioDAO.existeUsuario(username)) {
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            return;
        }

        // Registrar el usuario en la base de datos
        usuarioDAO.crear(newUser);

        Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();

    }
}