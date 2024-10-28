package com.example.ticketsmanager.controller.trabajador;

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
    private int idTrabajador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ticket);


        ticketDAO = new TicketDAO(this);


        idTrabajador = getIntent().getIntExtra("id_trabajador", -1);

        editTextTitulo = findViewById(R.id.etTitulo);
        editTextDescripcion = findViewById(R.id.etDescripcion);
        Button btnAgregar = findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(view -> agregarTicket());
    }

    private void agregarTicket() {
        String titulo = editTextTitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        Ticket.EstadoTicket estado = Ticket.EstadoTicket.NO_ATENDIDO; // Usa el enum directamente

        if (titulo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (idTrabajador == -1) {
            Toast.makeText(this, "Error: IDs de trabajador o técnico no válidos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Ticket nuevoTicket = new Ticket(titulo,descripcion,idTrabajador,estado);
        ticketDAO.crear(nuevoTicket);

        Toast.makeText(this, "Ticket agregado exitosamente.", Toast.LENGTH_SHORT).show();


        setResult(RESULT_OK);
        finish();
    }


}
