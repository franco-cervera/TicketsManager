package com.example.ticketsmanager.model;

public class Administrador extends Usuario {

    // Constructor de la clase Administrador
    public Administrador(int id, String nombreUsuario, String contrasena) {
        super(id, nombreUsuario, contrasena, "administrador");
    }

    // Método para agregar un nuevo usuario
    public Usuario agregarUsuario(int id, String nombreUsuario, String contrasena, String tipo) {
        // Asegúrate de que el tipo sea válido antes de crear un nuevo usuario
        switch (tipo.toLowerCase()) {
            case "trabajador":
                return new Trabajador(id, nombreUsuario, contrasena);
            case "tecnico":
                return new Tecnico(id, nombreUsuario, contrasena);
            case "administrador":
                return new Administrador(id, nombreUsuario, contrasena);
            default:
                throw new IllegalArgumentException("Tipo de usuario no válido: " + tipo);
        }
    }

    // Método para bloquear un usuario (modifica una bandera en la base de datos)
    public void bloquearUsuario(Usuario usuario) {
        System.out.println("Usuario con ID " + usuario.getId() + " bloqueado.");
        // Aquí deberías modificar el estado del usuario en la base de datos
    }

    // Método para reabrir un ticket
    public void reabrirTicket(Ticket ticket, Tecnico tecnico) {
        ticket.setEstado(Ticket.EstadoTicket.valueOf("Reabierto"));
        tecnico.setTicketsAtendidos(tecnico.getTicketsAtendidos() - 1);
        System.out.println("El ticket ha sido reabierto.");
    }
}
