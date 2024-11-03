package com.example.ticketsmanager.model;

public class Ticket {
    protected int id;
    protected String titulo;
    protected String descripcion;
    protected int idTrabajador;
    protected int idTecnico;
    protected boolean reabierto;
    protected int idTecnicoAnterior;
    protected EstadoTicket estado;

    public enum EstadoTicket {
        NO_ATENDIDO,
        ATENDIDO,
        ATENDIDO_REABIERTO,
        RESUELTO,
        FINALIZADO,
        REABIERTO,
        RESUELTO_REABIERTO,
        FINALIZADO_REABIERTO;

        @Override
        public String toString() {
            switch (this) {
                case NO_ATENDIDO:
                    return "No Atendido";
                case ATENDIDO:
                    return "Atendido";
                case ATENDIDO_REABIERTO:
                    return "Atendido-Reabierto";
                case RESUELTO:
                    return "Resuelto";
                case RESUELTO_REABIERTO:
                    return "Resuelto-Reabierto";
                case FINALIZADO:
                    return "Finalizado";
                case FINALIZADO_REABIERTO:
                    return "Finalizado-Reabierto";
                case REABIERTO:
                    return "Reabierto";

                default:
                    return super.toString();
            }
        }
    }

    // Constructor con id
    public Ticket(int id,String titulo, String descripcion, int idTrabajador, int idTecnico, EstadoTicket estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idTrabajador = idTrabajador;
        this.estado = estado;
        this.idTecnico = idTecnico;
        this.reabierto = false;
        this.idTecnicoAnterior = -1;
    }
    // Constructor sin id
    public Ticket(String titulo, String descripcion, int idTrabajador, EstadoTicket estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idTrabajador = idTrabajador;
        this.estado = estado;
        this.idTecnico = idTecnico;
        this.reabierto = false;
        this.idTecnicoAnterior = -1;
    }


    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titutlo) {
        this.titulo = titutlo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion;}

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public boolean isReabierto() {
        return reabierto;
    }

    public void setReabierto(boolean reabierto) {
        this.reabierto = reabierto;
    }

    public int getIdTecnicoAnterior() {
        return idTecnicoAnterior;
    }

    public void setIdTecnicoAnterior(int idTecnicoAnterior) {
        this.idTecnicoAnterior = idTecnicoAnterior;
    }

    @Override
    public String toString() {
        return "Ticket: " + titulo + " | Estado: " + estado;
    }
}
