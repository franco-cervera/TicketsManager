package com.example.ticketsmanager.controller.tecnico;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.controller.trabajador.TicketAdapterTrabajador;
import com.example.ticketsmanager.dao.TicketDAO;
import com.example.ticketsmanager.dao.UsuarioDAO;
import com.example.ticketsmanager.model.Ticket;

import java.util.List;

public class GestionTicketTecnicoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TicketAdapterTrabajador ticketAdapter;
    private List<Ticket> ticketList;
    private Button btnTomarTicket, btnFinalizarTicket;
    private Ticket ticketSeleccionado;
    private TicketDAO ticketDAO;
    private int ticketsAtendidosCount, tecnicoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_ticket_tecnico);

        tecnicoId = getIntent().getIntExtra("id_tecnico", -1);
        Log.d("GestionTicketTecnico", "ID Tecnico: " + tecnicoId);

        recyclerView = findViewById(R.id.recyclerViewTickets);
        btnTomarTicket = findViewById(R.id.btnTomarTicket);
        btnFinalizarTicket = findViewById(R.id.btnFinalizarTicket);

        ticketDAO = new TicketDAO(this);

        ticketList = ticketDAO.listarNoFinalizados();

        ticketAdapter = new TicketAdapterTrabajador(ticketList);
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ticketAdapter.setOnTicketClickListener(ticket -> {
            ticketSeleccionado = ticket;
            Toast.makeText(this, "Ticket seleccionado: " + ticket.getTitulo(), Toast.LENGTH_SHORT).show();
        });

        ticketsAtendidosCount = ticketDAO.contarTicketsAtendidosPorTecnico(tecnicoId);

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        btnTomarTicket.setOnClickListener(v -> {
            if (ticketSeleccionado != null) {
                if (ticketsAtendidosCount < 3
                        && (ticketSeleccionado.getEstado() == Ticket.EstadoTicket.NO_ATENDIDO
                        || ticketSeleccionado.getEstado() == Ticket.EstadoTicket.REABIERTO)) {


                    if (ticketSeleccionado.getEstado() == Ticket.EstadoTicket.REABIERTO) {
                        ticketSeleccionado.setEstado(Ticket.EstadoTicket.ATENDIDO_REABIERTO);
                        usuarioDAO.incrementarFallasTecnico(tecnicoId); // Incrementar falla si estaba reabierto
                    } else {
                        ticketSeleccionado.setEstado(Ticket.EstadoTicket.ATENDIDO);
                    }

                    ticketSeleccionado.setIdTecnico(tecnicoId);
                    ticketDAO.actualizar(ticketSeleccionado);
                    ticketsAtendidosCount++;
                    actualizarListaTickets();
                    Toast.makeText(this, "Ticket tomado: " + ticketSeleccionado.getTitulo(), Toast.LENGTH_SHORT).show();
                } else if (ticketsAtendidosCount >= 3) {
                    Toast.makeText(this, "No puedes tomar más de 3 tickets a la vez", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Este ticket no está disponible para ser tomado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Por favor, selecciona un ticket", Toast.LENGTH_SHORT).show();
            }
        });

        btnFinalizarTicket.setOnClickListener(v -> {
            if (ticketSeleccionado != null &&
                    (ticketSeleccionado.getEstado() == Ticket.EstadoTicket.ATENDIDO || ticketSeleccionado.getEstado() == Ticket.EstadoTicket.ATENDIDO_REABIERTO) &&
                    tecnicoId == ticketSeleccionado.getIdTecnico()) {

                if (ticketSeleccionado.getEstado() == Ticket.EstadoTicket.ATENDIDO_REABIERTO) {
                    ticketSeleccionado.setEstado(Ticket.EstadoTicket.RESUELTO_REABIERTO);
                } else {
                    ticketSeleccionado.setEstado(Ticket.EstadoTicket.RESUELTO);
                }

                ticketDAO.actualizar(ticketSeleccionado);
                ticketsAtendidosCount--;
                actualizarListaTickets();
                Toast.makeText(this, "Ticket Finalizado: " + ticketSeleccionado.getTitulo(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No puedes finalizar este ticket", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarListaTickets() {
        ticketList = ticketDAO.listarNoFinalizados();
        ticketAdapter.notifyDataSetChanged();
    }

}
