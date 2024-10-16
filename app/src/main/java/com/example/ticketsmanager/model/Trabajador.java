package com.example.ticketsmanager.model;

import java.util.List;

public class Trabajador extends Usuario {

    public Trabajador(int id, String nombreUsuario, String contrasena) {
        super(id, nombreUsuario, contrasena, "trabajador");
    }

    // Método para crear un ticket
    public Ticket crearTicket(String titulo, String descripcion) {
        return new Ticket(titulo, descripcion, this.id, Ticket.EstadoTicket.NO_ATENDIDO); // Cambiando "No atendido" a EstadoTicket.NO_ATENDIDO
    }

    // Método para ver tickets propios
    public void verTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getIdTrabajador() == this.id && !ticket.getEstado().equals(Ticket.EstadoTicket.FINALIZADO)) { // Cambiando "Finalizado" a EstadoTicket.FINALIZADO
                System.out.println(ticket);
            }
        }
    }
}
