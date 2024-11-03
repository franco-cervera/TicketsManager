package com.example.ticketsmanager.controller.Admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketsmanager.dao.TicketDAO;
import com.example.ticketsmanager.model.Ticket;
import com.example.ticketsmanager.R;

public class ActualizarTicketActivity extends AppCompatActivity {

    private EditText etTitulo, etDescripcion;
    private TicketDAO ticketDAO;
    private Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_ticket);

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        Button btnActualizarTicket = findViewById(R.id.btnActualizarTicket);

        ticketDAO = new TicketDAO(this);
        int ticketId = getIntent().getIntExtra("TICKET_ID", -1);

        if (ticketId != -1) {

            ticket = ticketDAO.listar(ticketId);
            if (ticket != null) {
                etTitulo.setText(ticket.getTitulo());
                etDescripcion.setText(ticket.getDescripcion());
            } else {
                Toast.makeText(this, "Ticket no encontrado.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "ID de ticket no válido.", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnActualizarTicket.setOnClickListener(view -> {
            String titulo = etTitulo.getText().toString();
            String descripcion = etDescripcion.getText().toString();

            if (titulo.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(ActualizarTicketActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            ticket.setTitulo(titulo);
            ticket.setDescripcion(descripcion);

            ticketDAO.actualizar(ticket);

            Toast.makeText(ActualizarTicketActivity.this, "Ticket actualizado correctamente.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        });
    }
}
