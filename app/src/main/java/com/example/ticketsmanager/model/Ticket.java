package com.example.ticketsmanager.model;

public class Ticket {
    private String titulo;
    private String descripcion;
    private int idTrabajador;
    private int idTecnico;
    private String estado; // No atendido, Atendido, Resuelto, Finalizado, Reabierto

    // Constructor
    public Ticket(String titulo, String descripcion, int idTrabajador, String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idTrabajador = idTrabajador;
        this.estado = estado;
        this.idTecnico = -1; // Inicialmente no tiene técnico asignado
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ticket: " + titulo + " | Estado: " + estado;
    }
}
