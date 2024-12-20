package com.example.ticketsmanager.controller.trabajador;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.dao.TicketDAO;
import com.example.ticketsmanager.dao.UsuarioDAO;
import com.example.ticketsmanager.model.Ticket;

import java.util.List;

public class GestionTicketTrabajadorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TicketAdapterTrabajador ticketAdapter;
    private List<Ticket> ticketList;
    private Button btnAgregarTicket, btnReabrirTicket, btnTicketResuelto;
    private Ticket ticketSeleccionado;
    private TicketDAO ticketDAO;
    private UsuarioDAO usuarioDAO;
    private int trabajadorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_ticket_trabajador);

        trabajadorId = getIntent().getIntExtra("id_trabajador", -1);
        Log.d("GestionTicketTrabajador", "ID Trabajador: " + trabajadorId);

        recyclerView = findViewById(R.id.recyclerViewTickets);
        btnAgregarTicket = findViewById(R.id.btnAgregarTicket);
        btnReabrirTicket = findViewById(R.id.btnReabrirTicket);
        btnTicketResuelto = findViewById(R.id.btnTicketResuelto);

        ticketDAO = new TicketDAO(this);
        usuarioDAO = new UsuarioDAO(this);

        ticketList = ticketDAO.listarNoFinalizados();

        ticketAdapter = new TicketAdapterTrabajador(ticketList);
        recyclerView.setAdapter(ticketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ticketAdapter.setOnTicketClickListener(ticket -> {
            ticketSeleccionado = ticket;
            Toast.makeText(this, "Ticket seleccionado: " + ticket.getTitulo(), Toast.LENGTH_SHORT).show();
        });

        btnAgregarTicket.setOnClickListener(v -> {
            agregarTicket();
            actualizarListaTickets();
        });

        btnReabrirTicket.setOnClickListener(v -> {
            if (ticketSeleccionado == null) {
                Toast.makeText(this, "Por favor, selecciona un ticket primero", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!ticketSeleccionado.getEstado().equals(Ticket.EstadoTicket.RESUELTO)) {
                Toast.makeText(this, "Solo puedes reabrir tickets resueltos", Toast.LENGTH_SHORT).show();
                return;
            }

            int tecnicoId = ticketSeleccionado.getIdTecnico();

            usuarioDAO.incrementarFallasTecnico(tecnicoId);

            ticketSeleccionado.setEstado(Ticket.EstadoTicket.REABIERTO);
            ticketDAO.actualizar(ticketSeleccionado);

            Toast.makeText(this, "El ticket ha sido reabierto y se ha registrado una falla al técnico.", Toast.LENGTH_SHORT).show();
            actualizarListaTickets();
        });


        btnTicketResuelto.setOnClickListener(v -> {
            if (ticketSeleccionado == null) {
                Toast.makeText(this, "Por favor, selecciona un ticket primero", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!ticketSeleccionado.getEstado().equals(Ticket.EstadoTicket.RESUELTO) &&
                    !ticketSeleccionado.getEstado().equals(Ticket.EstadoTicket.RESUELTO_REABIERTO)) {
                Toast.makeText(this, "Solo puedes marcar como finalizados los tickets en estado 'Resuelto' o 'Resuelto Reabierto'", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("FinalizarTicket", "Estado del ticket antes de finalizar: " + ticketSeleccionado.getEstado());

            if (ticketSeleccionado.getEstado().equals(Ticket.EstadoTicket.RESUELTO_REABIERTO)) {
                usuarioDAO.gestionarResolucionTicketReabierto(ticketSeleccionado.getIdTecnico(), getApplicationContext()); // Llama al método para gestionar la resolución
                Log.d("FinalizarTicket", "ID del técnico: " + ticketSeleccionado.getIdTecnico());

            }

            ticketSeleccionado.setEstado(Ticket.EstadoTicket.FINALIZADO);
            ticketDAO.actualizar(ticketSeleccionado);
            Toast.makeText(this, "El ticket ha sido marcado como finalizado", Toast.LENGTH_SHORT).show();
            actualizarListaTickets();
        });


    }

    private void agregarTicket() {
        Intent intent = new Intent(this, AgregarTicketActivity.class);
        intent.putExtra("id_trabajador", trabajadorId);
        startActivityForResult(intent, 1);
    }

    private void actualizarListaTickets() {
        ticketList.clear();
        ticketList.addAll(ticketDAO.listarNoFinalizados());
        ticketAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ticketList = ticketDAO.listarNoFinalizados();
            ticketAdapter = new TicketAdapterTrabajador(ticketList);
            recyclerView.setAdapter(ticketAdapter);

            ticketAdapter.setOnTicketClickListener(ticket -> {
                Toast.makeText(this, "Ticket seleccionado: " + ticket.getTitulo(), Toast.LENGTH_SHORT).show();
            });
        }
    }


}
