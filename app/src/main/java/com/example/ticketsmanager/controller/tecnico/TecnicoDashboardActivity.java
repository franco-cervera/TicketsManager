package com.example.ticketsmanager.controller.tecnico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.controller.trabajador.GestionTicketTrabajadorActivity;
import com.example.ticketsmanager.controller.trabajador.TrabajadorDashboardActivity;

public class TecnicoDashboardActivity extends AppCompatActivity {

    private CardView cardGestionTickets;
    private int tecnicoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnico_dashboard);

        cardGestionTickets = findViewById(R.id.cardGestionTickets);

        tecnicoId = getIntent().getIntExtra("id_tecnico", -1);

        cardGestionTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TecnicoDashboardActivity.this, GestionTicketTecnicoActivity.class);
                intent.putExtra("id_tecnico", tecnicoId); // Pasar ID del Tecnico
                startActivity(intent);
            }
        });

    }
}