package com.example.ticketsmanager.controller.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketsmanager.dao.TicketDAO;
import com.example.ticketsmanager.model.Ticket;
import com.example.ticketsmanager.R;

public class AgregarTicketActivity extends AppCompatActivity {

    private EditText editTextTitulo;
    private EditText editTextDescripcion;
    private TicketDAO ticketDAO;
    private int idTrabajador; // Almacena el ID del trabajador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ticket);

        // Inicializa el TicketDAO
        ticketDAO = new TicketDAO(this);

        // Obtén el ID del trabajador
        idTrabajador = getIntent().getIntExtra("ID_TRABAJADOR", -1); // -1 es el valor predeterminado si no se pasa nada

        // Referencias a los EditText y al botón
        editTextTitulo = findViewById(R.id.etTitulo);
        editTextDescripcion = findViewById(R.id.etDescripcion);
        Button btnAgregar = findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarTicket();
            }
        });
    }

    private void agregarTicket() {
        String titulo = editTextTitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        Ticket.EstadoTicket estado = Ticket.EstadoTicket.NO_ATENDIDO; // Usa el enum directamente

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Ticket nuevoTicket = new Ticket(titulo, descripcion, idTrabajador, estado);
        ticketDAO.crear(nuevoTicket);

        Toast.makeText(this, "Ticket agregado exitosamente.", Toast.LENGTH_SHORT).show();

        // Envía el resultado de éxito antes de cerrar la actividad
        setResult(RESULT_OK);
        finish(); // Cierra la actividad y regresa a la anterior
    }


}