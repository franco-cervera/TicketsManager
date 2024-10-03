package com.example.ticketsmanager.model;

import java.util.List;

public class Trabajador extends Usuario {

    public Trabajador(int id, String contrasena) {
        super(id, contrasena, "trabajador");
    }

    // Método para crear un ticket
    public Ticket crearTicket(String titulo, String descripcion) {
        return new Ticket(titulo, descripcion, this.id, "No atendido");
    }

    // Método para ver tickets propios
    public void verTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getIdTrabajador() == this.id && !ticket.getEstado().equals("Finalizado")) {
                System.out.println(ticket);
            }
        }
    }
}
