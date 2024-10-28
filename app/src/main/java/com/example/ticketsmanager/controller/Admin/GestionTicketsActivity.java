package com.example.ticketsmanager.controller.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.controller.trabajador.AgregarTicketActivity;
import com.example.ticketsmanager.controller.trabajador.TicketAdapterTrabajador;
import com.example.ticketsmanager.dao.TicketDAO;
import com.example.ticketsmanager.model.Ticket;

import java.util.List;

public class GestionTicketsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TicketAdapterTrabajador ticketAdapter;
    private List<Ticket> ticketList;
    private Button btnAgregarTicket, btnActualizarTicket, btnEliminarTicket;
    private Ticket ticketSeleccionado; // Mantiene referencia al ticket seleccionado
    private TicketDAO ticketDAO; // Inicializa el DAO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_tickets);

        recyclerView = findViewById(R.id.recyclerViewTickets);
        btnAgregarTicket = findViewById(R.id.btnAgregarTicket);
        btnActualizarTicket = findViewById(R.id.btnActualizarTicket);
        btnEliminarTicket = findViewById(R.id.btnEliminarTicket);

        // Inicializa tu lista de tickets
        ticketDAO = new TicketDAO(this);
        ticketList = ticketDAO.listarTodos();

        // Configurar el RecyclerView
        ticketAdapter = new TicketAdapterTrabajador(ticketList);
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Establecer el listener para la selección de ticket
        ticketAdapter.setOnTicketClickListener(ticket -> {
            ticketSeleccionado = ticket;
            Toast.makeText(this, "Ticket seleccionado: " + ticket.getTitulo(), Toast.LENGTH_SHORT).show();
        });

        // Manejo de clic para agregar ticket
        btnAgregarTicket.setOnClickListener(v -> {
            Intent intent = new Intent(GestionTicketsActivity.this, AgregarTicketActivity.class);
            startActivityForResult(intent, 1);
        });

        // Manejo de clic para actualizar ticket
        btnActualizarTicket.setOnClickListener(v -> {
            if (ticketSeleccionado != null) {
                Intent intent = new Intent(GestionTicketsActivity.this, ActualizarTicketActivity.class);
                intent.putExtra("TICKET_ID", ticketSeleccionado.getId()); // Pasar el ID del ticket
                startActivityForResult(intent, 2); // Código de solicitud para actualización
            } else {
                Toast.makeText(this, "Selecciona un ticket para actualizar", Toast.LENGTH_SHORT).show();
            }
        });


        // Manejo de clic para eliminar ticket
        btnEliminarTicket.setOnClickListener(v -> {
            if (ticketSeleccionado != null) {
                int ticketId = ticketSeleccionado.getId(); // Obtener el ID del ticket
                // Eliminar el ticket de la base de datos
                ticketDAO.eliminar(ticketId);
                // Eliminar de la lista
                ticketList.remove(ticketSeleccionado); // Eliminar el ticket seleccionado de la lista
                // Actualizar el adaptador
                ticketAdapter.updateData(ticketList); // Actualizar el adaptador
                // Mostrar mensaje de éxito
                Toast.makeText(this, "Ticket eliminado", Toast.LENGTH_SHORT).show();
                ticketSeleccionado = null; // Limpiar selección
            } else {
                Toast.makeText(this, "Selecciona un ticket para eliminar", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Volver a cargar la lista de tickets
            ticketList = ticketDAO.listarTodos();
            ticketAdapter.updateData(ticketList);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            // Al volver de la actualización, recarga la lista de tickets
            ticketList = ticketDAO.listarTodos();
            ticketAdapter.updateData(ticketList);
        }
    }
}
