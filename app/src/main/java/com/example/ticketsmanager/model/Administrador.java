package com.example.ticketsmanager.model;

public class Administrador extends Usuario {

    public Administrador(int id, String contrasena) {
        super(id, contrasena, "administrador");
    }

    // Método para agregar un nuevo usuario
    public Usuario agregarUsuario(int id, String tipo) {
        return tipo.equals("trabajador") ? new Trabajador(id, String.valueOf(id)) :
                tipo.equals("tecnico") ? new Tecnico(id, String.valueOf(id)) :
                        new Administrador(id, String.valueOf(id));
    }

    // Método para bloquear un usuario (modifica una bandera en la base de datos)
    public void bloquearUsuario(Usuario usuario) {
        System.out.println("Usuario con ID " + usuario.getId() + " bloqueado.");
        // Aquí deberías modificar el estado del usuario en la base de datos
    }

    // Método para reabrir un ticket
    public void reabrirTicket(Ticket ticket, Tecnico tecnico) {
        ticket.setEstado("Reabierto");
        tecnico.setTicketsAtendidos(tecnico.getTicketsAtendidos() - 1);
        System.out.println("El ticket ha sido reabierto.");
    }
}
