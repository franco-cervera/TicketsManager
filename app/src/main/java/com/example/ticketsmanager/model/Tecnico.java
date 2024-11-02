package com.example.ticketsmanager.model;

public class Tecnico extends Usuario {

    private int ticketsAtendidos;

    public Tecnico(int id, String nombreUsuario, String contrasena, int fallas, int marcas) {
        super(id, nombreUsuario, contrasena, "tecnico", fallas, marcas);
        this.ticketsAtendidos = 0;
    }


    public int getTicketsAtendidos() {
        return ticketsAtendidos;
    }

    public void setTicketsAtendidos(int ticketsAtendidos) {
        this.ticketsAtendidos = ticketsAtendidos;
    }

}
