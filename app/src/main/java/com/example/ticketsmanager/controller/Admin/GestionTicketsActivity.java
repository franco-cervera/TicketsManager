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
import com.example.ticketsmanager.controller.Admin.TicketAdapterAdmin;
import com.example.ticketsmanager.dao.TicketDAO;
import com.example.ticketsmanager.dao.UsuarioDAO;
import com.example.ticketsmanager.model.Ticket;

import java.util.List;

public class GestionTicketsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TicketAdapterAdmin ticketAdapter;
    private List<Ticket> ticketList;
    private Button btnReabrirTicket;
    private Ticket ticketSeleccionado;
    private TicketDAO ticketDAO;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_tickets);

        recyclerView = findViewById(R.id.recyclerViewTickets);
        btnReabrirTicket = findViewById(R.id.btnReabrirTicket);

        // Inicializa tu lista de tickets
        ticketDAO = new TicketDAO(this);
        ticketList = ticketDAO.listarTodos();
        usuarioDAO = new UsuarioDAO(this);

        // Configurar el RecyclerView
        ticketAdapter = new TicketAdapterAdmin(ticketList);
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Establecer el listener para la selección de ticket
        ticketAdapter.setOnTicketClickListener(ticket -> {
            ticketSeleccionado = ticket;
            Toast.makeText(this, "Ticket seleccionado: " + ticket.getTitulo(), Toast.LENGTH_SHORT).show();
        });

        // Manejo de clic para reabrir ticket
        btnReabrirTicket.setOnClickListener(v -> {
            if (ticketSeleccionado == null) {
                Toast.makeText(this, "Por favor, selecciona un ticket primero", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener el ID del técnico que resolvió el ticket
            int tecnicoId = ticketSeleccionado.getIdTecnico();

            // Incrementar la marca del técnico
            usuarioDAO.gestionarRetornoTicket(tecnicoId, this);

            // Cambiar el estado del ticket a REABIERTO
            ticketSeleccionado.setEstado(Ticket.EstadoTicket.REABIERTO);
            ticketDAO.actualizar(ticketSeleccionado);

            actualizarListaTickets();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            ticketList = ticketDAO.listarTodos();
            ticketAdapter.updateData(ticketList);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            ticketList = ticketDAO.listarTodos();
            ticketAdapter.updateData(ticketList);
        }
    }

    private void actualizarListaTickets() {
        ticketList.clear();
        ticketList.addAll(ticketDAO.listarNoFinalizados());
        ticketAdapter.notifyDataSetChanged();
    }
}
