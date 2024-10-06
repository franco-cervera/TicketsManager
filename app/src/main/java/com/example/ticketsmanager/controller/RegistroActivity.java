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

    private TextInputEditText edtUsername; // Nombre de usuario
    private TextInputEditText edtPassword; // Contraseña
    private Spinner spinner; // Tipo de usuario
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar los componentes de la interfaz
        TextInputLayout layoutUsername = findViewById(R.id.edtID_registro);
        TextInputLayout layoutPassword = findViewById(R.id.edtPassword_registro);

        edtUsername = (TextInputEditText) layoutUsername.getEditText();
        edtPassword = (TextInputEditText) layoutPassword.getEditText();

        spinner = findViewById(R.id.spinner);
        Button btnRegister = findViewById(R.id.btnRegistro_registro);

        // Inicializar el DAO
        usuarioDAO = new UsuarioDAO(this);

        // Configurar el Spinner
        setupSpinner(); // Llamar al método para configurar el Spinner

        // Establecer el listener del botón de registro
        btnRegister.setOnClickListener(this::registerUser);
    }

    private void setupSpinner() {
        // Obtener los elementos del arreglo de strings desde resources
        String[] tiposUsuario = getResources().getStringArray(R.array.roles_array);

        // Crear un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposUsuario);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Establecer el layout para la lista desplegable

        // Asignar el adaptador al Spinner
        spinner.setAdapter(adapter);
    }

    private void registerUser(View view) {
        String username = edtUsername.getText().toString().trim(); // Obtener nombre de usuario
        String password = edtPassword.getText().toString().trim(); // Obtener contraseña
        String tipo = spinner.getSelectedItem().toString(); // Obtener tipo de usuario

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo usuario sin ID
        Usuario newUser = new Usuario(username, password, tipo); // Usa el constructor adecuado

        // Registrar el usuario en la base de datos
        usuarioDAO.crear(newUser);

        // Mostrar un mensaje de éxito y regresar a la actividad anterior
        Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
        finish(); // Regresar a la actividad anterior
    }
}
