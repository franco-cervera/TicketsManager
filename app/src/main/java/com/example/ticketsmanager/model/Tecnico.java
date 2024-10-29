package com.example.ticketsmanager.model;

public class Tecnico extends Usuario {

    private int ticketsAtendidos;

    public Tecnico(int id, String nombreUsuario, String contrasena) {
        super(id, nombreUsuario, contrasena, "tecnico");
        this.ticketsAtendidos = 0;
    }


    public int getTicketsAtendidos() {
        return ticketsAtendidos;
    }

    public void setTicketsAtendidos(int ticketsAtendidos) {
        this.ticketsAtendidos = ticketsAtendidos;
    }

}
