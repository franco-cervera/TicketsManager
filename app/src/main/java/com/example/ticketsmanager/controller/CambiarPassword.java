package com.example.ticketsmanager.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.database.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CambiarPassword extends AppCompatActivity {

    private EditText edtID, edtChangePass, edtNewPass, edtConfirmPass;
    private Button button_changePass;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cambiar_password);


        button_changePass = findViewById(R.id.button_changePass);

        TextInputLayout inputLayoutID = findViewById(R.id.edtID_ChangePass);
        TextInputLayout inputLayoutPassword1 = findViewById(R.id.edtPass_ChangePass);
        TextInputLayout inputLayoutPassword2 = findViewById(R.id.edt_newPass);
        TextInputLayout inputLayoutPassword3 = findViewById(R.id.edt_ConfirmPass);


        edtID = inputLayoutID.getEditText();
        edtChangePass = inputLayoutPassword1.getEditText();
        edtNewPass = inputLayoutPassword2.getEditText();
        edtConfirmPass = inputLayoutPassword3.getEditText();



        db = new DatabaseHelper(this);

        button_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idUsuario = edtID.getText().toString().trim();
                String passActual = edtChangePass.getText().toString().trim();
                String newPass = edtNewPass.getText().toString().trim();
                String confirmPass = edtConfirmPass.getText().toString().trim();

                if (idUsuario.isEmpty() || passActual.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(CambiarPassword.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar que el ID sea un número
                int idUsuarioInt;
                try {
                    idUsuarioInt = Integer.parseInt(idUsuario);
                } catch (NumberFormatException e) {
                    Toast.makeText(CambiarPassword.this, "ID de usuario inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si el usuario existe
                String passwordAlmacenada = db.obtenerPasswordPorId(idUsuarioInt); // Método que debes implementar en DatabaseHelper
                if (passwordAlmacenada == null) {
                    Toast.makeText(CambiarPassword.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar que la contraseña actual sea correcta
                if (!passwordAlmacenada.equals(passActual)) {
                    Toast.makeText(CambiarPassword.this, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar que la nueva contraseña y la confirmación coincidan
                if (!newPass.equals(confirmPass)) {
                    Toast.makeText(CambiarPassword.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Actualizar la contraseña en la base de datos
                boolean isPasswordUpdated = db.actualizarPassword(idUsuarioInt, newPass); // Método que debes implementar en DatabaseHelper
                if (isPasswordUpdated) {
                    Toast.makeText(CambiarPassword.this, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CambiarPassword.this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
