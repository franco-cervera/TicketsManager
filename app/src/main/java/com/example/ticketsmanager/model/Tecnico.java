package com.example.ticketsmanager.model;

public class Tecnico extends Usuario {
    private int fallas;
    private int ticketsAtendidos;

    public Tecnico(int id, String contrasena) {
        super(id, contrasena, "tecnico");
        this.fallas = 0;
        this.ticketsAtendidos = 0;
    }

    // Método para atender un ticket
    public boolean atenderTicket(Ticket ticket) {
        if (ticketsAtendidos < 3 && ticket.getEstado().equals("No atendido")) {
            ticket.setEstado("Atendido");
            ticket.setIdTecnico(this.id);
            ticketsAtendidos++;
            return true;
        } else {
            System.out.println("No puedes atender más tickets.");
            return false;
        }
    }

    // Método para marcar un ticket como resuelto
    public void resolverTicket(Ticket ticket) {
        if (ticket.getIdTecnico() == this.id && ticket.getEstado().equals("Atendido")) {
            ticket.setEstado("Resuelto");
        }
    }

    // Método para manejar fallas
    public void recibirFalla() {
        fallas++;
        if (fallas >= 3) {
            System.out.println("Acceso bloqueado. Debes pedir al administrador que te habilite.");
        }
    }

    // Otros métodos para obtener y setear fallas y tickets atendidos
    public int getFallas() {
        return fallas;
    }

    public int getTicketsAtendidos() {
        return ticketsAtendidos;
    }

    public void setTicketsAtendidos(int ticketsAtendidos) {
        this.ticketsAtendidos = ticketsAtendidos;
    }
}
