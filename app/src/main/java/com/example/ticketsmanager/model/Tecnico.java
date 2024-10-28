package com.example.ticketsmanager.model;

public class Tecnico extends Usuario {
    private int fallas;
    private int ticketsAtendidos;

    public Tecnico(int id, String nombreUsuario, String contrasena) {
        super(id, nombreUsuario, contrasena, "tecnico");
        this.fallas = 0;
        this.ticketsAtendidos = 0;
    }

    public int getFallas() {
        return fallas;
    }

    public void setFallas(int fallas) {
        this.fallas = fallas;
    }

    public int getTicketsAtendidos() {
        return ticketsAtendidos;
    }

    public void setTicketsAtendidos(int ticketsAtendidos) {
        this.ticketsAtendidos = ticketsAtendidos;
    }

    // Incrementar fallas
    public void incrementarFallas() {
        this.fallas++;
        if (this.fallas >= 3) {
            bloquear();
        }
    }

    // Limpiar una falla
    public void limpiarFalla() {
        if (this.fallas > 0) {
            this.fallas--;
        }
    }

    // Bloquear al técnico
    private void bloquear() {
        // Lógica para bloquear al técnico
        // Esto podría incluir marcarlo en la base de datos
    }
}
