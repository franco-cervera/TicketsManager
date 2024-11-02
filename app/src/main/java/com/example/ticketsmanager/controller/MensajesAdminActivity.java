package com.example.ticketsmanager.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketsmanager.database.DatabaseHelper;
import com.example.ticketsmanager.R;

public class MensajesAdminActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText edtIDTecnico, edtMensaje;
    private Spinner spinnerAsunto;
    private Button buttonEnviarMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes_admin); // Asegúrate de que el nombre del layout sea correcto

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(this);

        // Inicializar vistas
        edtIDTecnico = ((com.google.android.material.textfield.TextInputLayout) findViewById(R.id.edtID_tecnico)).getEditText();
        spinnerAsunto = findViewById(R.id.spinnerAsunto);
        edtMensaje = ((com.google.android.material.textfield.TextInputLayout) findViewById(R.id.edt_mensaje)).getEditText();
        buttonEnviarMensaje = findViewById(R.id.button_enviarMensaje);

        // Configurar el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.asunto_mensaje, R.layout.spinner_item_msj);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAsunto.setAdapter(adapter);


        // Configurar el botón para enviar el mensaje
        buttonEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje();
            }
        });
    }

    private void enviarMensaje() {
        String idTecnico = edtIDTecnico.getText().toString().trim();
        String asunto = spinnerAsunto.getSelectedItem().toString();
        String mensaje = edtMensaje.getText().toString().trim();

        if (idTecnico.isEmpty() || mensaje.isEmpty() || asunto.equals("Selecciona un asunto")) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objeto ContentValues para almacenar los datos
        ContentValues values = new ContentValues();
        values.put("id_tecnico", Integer.parseInt(idTecnico));
        values.put("asunto", asunto);
        values.put("mensaje", mensaje);
        values.put("estado", "no leído"); // Estado inicial

        // Obtener la base de datos y agregar el mensaje
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert("mensajes", null, values);

        if (id != -1) {
            Toast.makeText(this, "Mensaje enviado con éxito.", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Error al enviar el mensaje.", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        edtIDTecnico.setText("");
        spinnerAsunto.setSelection(0); // Volver a la opción por defecto
        edtMensaje.setText("");
    }
}
