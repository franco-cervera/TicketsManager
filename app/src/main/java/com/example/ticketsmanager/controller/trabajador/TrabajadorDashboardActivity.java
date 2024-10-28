package com.example.ticketsmanager.controller.trabajador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.controller.Admin.GestionTicketsActivity;

public class TrabajadorDashboardActivity extends AppCompatActivity {

    private CardView cardGestionTickets;
    private int trabajadorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajador_dashboard);

        trabajadorId = getIntent().getIntExtra("id_trabajador", -1);

        cardGestionTickets = findViewById(R.id.cardGestionTickets);

        cardGestionTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrabajadorDashboardActivity.this, GestionTicketTrabajadorActivity.class);
                intent.putExtra("id_trabajador", trabajadorId); // Pasar ID del trabajador
                startActivity(intent);
            }
        });

    }
}